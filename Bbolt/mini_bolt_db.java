package com.example.hotel.bbolt;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * MiniBoltDB - a **very** simplified Bolt-like key-value store in Java.
 *
 * Features implemented (minimal prototype):
 * - Two meta pages for atomic meta switch (meta 0 and meta 1)
 * - Append-only records for key/value
 * - Serialized index (Map<String, IndexEntry>) stored as a blob; updated on commit
 * - Read transactions see a consistent snapshot (index at meta's indexOffset)
 * - Single writer (write transaction) with copy-on-write semantics for the index
 * - Uses RandomAccessFile/FileChannel for durability and fsync via channel.force(true)
 *
 * Limitations (important):
 * - Not a B+ tree. Index is an in-memory HashMap serialized to disk (not disk-structured)
 * - Not optimized for large datasets (index size grows with keys)
 * - No compaction (old records remain until you implement GC)
 * - Simple atomicity model (meta swap ensures crash-safety of committed index)
 * - Minimal error handling for clarity
 */
class MiniBoltDB implements Closeable {
    private final RandomAccessFile raf;
    private final FileChannel channel;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private static final int PAGE_SIZE = 4096; // meta page size
    private static final long META0_OFFSET = 0;
    private static final long META1_OFFSET = PAGE_SIZE;
    private static final long DATA_START = PAGE_SIZE * 2L;

    private static final long MAGIC = 0x4D494E49424F4C54L; // "MINIBOLT" fake magic

    // In-memory cache of the index for fast reads. On any committed write, reloads to new index.
    private volatile Map<String, IndexEntry> cachedIndex = new HashMap<>();
    private volatile long cachedIndexOffset = 0;
    private volatile int cachedIndexSize = 0;
    private volatile long cachedTxid = 0;

    public MiniBoltDB(File file) throws IOException {
        boolean exists = file.exists();
        raf = new RandomAccessFile(file, "rw");
        channel = raf.getChannel();
        if (!exists || channel.size() < DATA_START) {
            // initialize file with two empty meta pages
            raf.setLength(DATA_START);
            writeEmptyMeta(0, 0, 0);
            writeEmptyMeta(1, 0, 0);
            channel.force(true);
        }
        loadLatestMetaAndIndex();
    }

    private void writeEmptyMeta(int slot, long indexOffset, int indexSize) throws IOException {
        long offset = (slot == 0) ? META0_OFFSET : META1_OFFSET;
        raf.seek(offset);
        ByteBuffer buf = ByteBuffer.allocate(PAGE_SIZE);
        buf.putLong(MAGIC);
        buf.putLong(indexOffset);
        buf.putInt(indexSize);
        buf.putLong(0L); // txid
        // rest zeros
        buf.position(PAGE_SIZE);
        buf.flip();
        channel.write(buf, offset);
    }

    private Meta readMetaAtSlot(int slot) throws IOException {
        long offset = (slot == 0) ? META0_OFFSET : META1_OFFSET;
        ByteBuffer buf = ByteBuffer.allocate(32);
        channel.read(buf, offset);
        buf.flip();
        long magic = buf.getLong();
        if (magic != MAGIC) return new Meta(0,0,0,slot); // empty/invalid
        long idxOff = buf.getLong();
        int idxSize = buf.getInt();
        long txid = buf.getLong();
        return new Meta(magic, idxOff, idxSize, txid);
    }

    private void loadLatestMetaAndIndex() throws IOException {
        rwLock.writeLock().lock();
        try {
            Meta m0 = readMetaAtSlot(0);
            Meta m1 = readMetaAtSlot(1);
            Meta chosen = m0.txid >= m1.txid ? m0 : m1;
            if (chosen.txid == 0 || chosen.indexOffset == 0) {
                // empty DB
                cachedIndex = new HashMap<>();
                cachedIndexOffset = 0;
                cachedIndexSize = 0;
                cachedTxid = 0;
                return;
            }
            // read index blob
            byte[] blob = new byte[chosen.indexSize];
            raf.seek(chosen.indexOffset);
            raf.readFully(blob);
            Map<String, IndexEntry> idx = deserializeIndex(blob);
            cachedIndex = idx;
            cachedIndexOffset = chosen.indexOffset;
            cachedIndexSize = chosen.indexSize;
            cachedTxid = chosen.txid;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    // ---------------------- Public API ----------------------

    public ReadTransaction beginRead() {
        rwLock.readLock().lock();
        try {
            // snapshot the current index and txid references
            Map<String, IndexEntry> snap = new HashMap<>(cachedIndex);
            long txid = cachedTxid;
            return new ReadTransaction(snap, txid);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public WriteTransaction beginWrite() throws IOException {
        // single writer: acquire write lock
        rwLock.writeLock().lock();
        // provide a copy of current index to mutate
        Map<String, IndexEntry> workingCopy = new HashMap<>(cachedIndex);
        return new WriteTransaction(workingCopy);
    }

    @Override
    public void close() throws IOException {
        channel.close();
        raf.close();
    }

    // ---------------------- Internal helpers ----------------------

    private long appendRecord(byte[] key, byte[] value) throws IOException {
        long pos = channel.size();
        raf.seek(pos);
        // record layout: keyLen(int) keyBytes valueLen(int) valueBytes
        raf.writeInt(key.length);
        raf.write(key);
        raf.writeInt(value.length);
        raf.write(value);
        channel.force(true);
        return pos; // record start
    }

    private long writeIndexAndReturnOffset(Map<String, IndexEntry> idx) throws IOException {
        byte[] blob = serializeIndex(idx);
        long pos = channel.size();
        raf.seek(pos);
        raf.write(blob);
        channel.force(true);
        return pos;
    }

    private void writeMetaSlot(int slot, long indexOffset, int indexSize, long txid) throws IOException {
        long offset = (slot == 0) ? META0_OFFSET : META1_OFFSET;
        ByteBuffer buf = ByteBuffer.allocate(PAGE_SIZE);
        buf.putLong(MAGIC);
        buf.putLong(indexOffset);
        buf.putInt(indexSize);
        buf.putLong(txid);
        // fill rest zeros
        buf.position(PAGE_SIZE);
        buf.flip();
        channel.write(buf, offset);
        channel.force(true); // ensure meta persisted
    }

    private int chooseNextMetaSlot() throws IOException {
        Meta m0 = readMetaAtSlot(0);
        Meta m1 = readMetaAtSlot(1);
        return m0.txid >= m1.txid ? 1 : 0; // write to the other slot
    }

    // ---------------------- Serialization ----------------------

    private static class IndexEntry implements Serializable {
        long recordOffset;
        int valueSize;

        IndexEntry(long recordOffset, int valueSize) {
            this.recordOffset = recordOffset;
            this.valueSize = valueSize;
        }
    }

    private byte[] serializeIndex(Map<String, IndexEntry> idx) throws IOException {
        // format: count(int) { keyLen(int) keyBytes recordOffset(long) valueSize(int) }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(idx.size());
        for (Map.Entry<String, IndexEntry> e : idx.entrySet()) {
            byte[] k = e.getKey().getBytes(StandardCharsets.UTF_8);
            dos.writeInt(k.length);
            dos.write(k);
            dos.writeLong(e.getValue().recordOffset);
            dos.writeInt(e.getValue().valueSize);
        }
        dos.flush();
        return baos.toByteArray();
    }

    private Map<String, IndexEntry> deserializeIndex(byte[] blob) throws IOException {
        Map<String, IndexEntry> idx = new HashMap<>();
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(blob));
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            int klen = dis.readInt();
            byte[] kb = new byte[klen];
            dis.readFully(kb);
            long recOff = dis.readLong();
            int vsize = dis.readInt();
            idx.put(new String(kb, StandardCharsets.UTF_8), new IndexEntry(recOff, vsize));
        }
        return idx;
    }

    private byte[] readValueAt(IndexEntry e) throws IOException {
        raf.seek(e.recordOffset);
        int klen = raf.readInt();
        raf.skipBytes(klen);
        int vlen = raf.readInt();
        byte[] vb = new byte[vlen];
        raf.readFully(vb);
        return vb;
    }

    // ---------------------- Transaction classes ----------------------

    public class ReadTransaction implements Closeable {
        private final Map<String, IndexEntry> snapshotIndex;
        private final long txid;
        private boolean closed = false;

        ReadTransaction(Map<String, IndexEntry> snapshotIndex, long txid) {
            this.snapshotIndex = snapshotIndex;
            this.txid = txid;
        }

        public byte[] get(String key) throws IOException {
            if (closed) throw new IllegalStateException("tx closed");
            IndexEntry e = snapshotIndex.get(key);
            if (e == null) return null;
            return readValueAt(e);
        }

        public boolean contains(String key) {
            if (closed) throw new IllegalStateException("tx closed");
            return snapshotIndex.containsKey(key);
        }

        @Override
        public void close() {
            closed = true;
        }

        public long txid() { return txid; }
    }

    public class WriteTransaction implements Closeable {
        private final Map<String, IndexEntry> workingIndex;
        private boolean committed = false;
        private boolean closed = false;

        WriteTransaction(Map<String, IndexEntry> workingIndex) {
            this.workingIndex = workingIndex;
        }

        public void put(String key, byte[] value) throws IOException {
            if (closed) throw new IllegalStateException("tx closed");
            byte[] keyb = key.getBytes(StandardCharsets.UTF_8);
            long recOff = appendRecord(keyb, value);
            // store entry pointing to record start and value size
            workingIndex.put(key, new IndexEntry(recOff, value.length));
        }

        public byte[] get(String key) throws IOException {
            if (closed) throw new IllegalStateException("tx closed");
            IndexEntry e = workingIndex.get(key);
            if (e == null) return null;
            return readValueAt(e);
        }

        public void commit() throws IOException {
            if (closed) throw new IllegalStateException("tx closed");
            if (committed) throw new IllegalStateException("already committed");
            // 1) write new index blob at file end
            long newIndexOffset = writeIndexAndReturnOffset(workingIndex);
            byte[] blob = serializeIndex(workingIndex);
            int newIndexSize = blob.length;
            // 2) decide which meta slot to write
            int slot = chooseNextMetaSlot();
            long newTxid = cachedTxid + 1;
            writeMetaSlot(slot, newIndexOffset, newIndexSize, newTxid);
            // 3) update in-memory cache
            cachedIndex = new HashMap<>(workingIndex);
            cachedIndexOffset = newIndexOffset;
            cachedIndexSize = newIndexSize;
            cachedTxid = newTxid;
            committed = true;
        }

        @Override
        public void close() {
            closed = true;
            // release writer lock held by DB caller
            rwLock.writeLock().unlock();
        }
    }

    // ---------------------- Meta helper ----------------------
    private static class Meta {
        long magic;
        long indexOffset;
        int indexSize;
        long txid;

        Meta(long magic, long indexOffset, int indexSize, long txid) {
            this.magic = magic;
            this.indexOffset = indexOffset;
            this.indexSize = indexSize;
            this.txid = txid;
        }
    }

    // ---------------------- Example usage ----------------------
    public static void main(String[] args) throws Exception {
        File f = new File("mini_bolt.db");
        try (MiniBoltDB db = new MiniBoltDB(f)) {
            // Writer example
            try (MiniBoltDB.WriteTransaction wt = db.beginWrite()) {
                wt.put("Alice", "Engineer".getBytes(StandardCharsets.UTF_8));
                wt.put("Bob", "Designer".getBytes(StandardCharsets.UTF_8));
                wt.commit();
            }

            // Reader example
            try (MiniBoltDB.ReadTransaction rt = db.beginRead()) {
                byte[] v = rt.get("Alice");
                System.out.println("Alice => " + new String(v, StandardCharsets.UTF_8));
            }

            // Another writer
            try (MiniBoltDB.WriteTransaction wt = db.beginWrite()) {
                wt.put("Alice", "Senior Engineer".getBytes(StandardCharsets.UTF_8));
                wt.commit();
            }

            // Reader sees new value only after commit
            try (MiniBoltDB.ReadTransaction rt = db.beginRead()) {
                byte[] v = rt.get("Alice");
                System.out.println("Alice => " + new String(v, StandardCharsets.UTF_8));
            }
        }
    }
}
