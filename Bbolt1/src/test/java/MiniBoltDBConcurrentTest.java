import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MiniBoltDBConcurrentTest {
    private static final String TEST_DB = "test_concurrent.db";
    private MiniBoltDB db;

    @BeforeEach
    void setUp() throws IOException {
        // Delete existing test file if any
        new File(TEST_DB).delete();
        db = new MiniBoltDB(new File(TEST_DB));
    }

    @AfterEach
    void tearDown() throws IOException {
        if (db != null) {
            db.close();
        }
        // Clean up test file
        new File(TEST_DB).delete();
    }

    @Test
    void testConcurrentWrites() throws Exception {
        int numThreads = 10;
        int writesPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        AtomicInteger successCount = new AtomicInteger(0);

        // Create and start writer threads
        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < writesPerThread; j++) {
                        String key = String.format("user_%d_%d", threadId, j);
                        String value = String.format("value_%d_%d", threadId, j);

                        try (MiniBoltDB.WriteTransaction tx = db.beginWrite()) {
                            tx.put(key, value.getBytes(StandardCharsets.UTF_8));
                            // Small delay to increase chance of interleaving
                            Thread.sleep(1);
                            tx.commit();
                            successCount.incrementAndGet();
                        } catch (Exception e) {
                            // Transaction might fail due to concurrent access
                            System.err.println("Write failed: " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all threads to complete
        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        // Verify all writes were successful and consistent
        try (MiniBoltDB.ReadTransaction tx = db.beginRead()) {
            // Check that we have the expected number of successful writes
            int expectedWrites = successCount.get();

            // Count unique keys in the database
            int actualCount = 0;
            for (int i = 0; i < numThreads; i++) {
                for (int j = 0; j < writesPerThread; j++) {
                    String key = String.format("user_%d_%d", i, j);
                    byte[] value = tx.get(key);
                    if (value != null) {
                        actualCount++;
                        String expectedValue = String.format("value_%d_%d", i, j);
                        assertEquals(expectedValue, new String(value, StandardCharsets.UTF_8));
                    }
                }
            }

            assertEquals(expectedWrites, actualCount, "Number of successful writes should match actual records");
            System.out.println("Successfully wrote " + actualCount + " records with " +
                    (numThreads * writesPerThread - actualCount) + " conflicts");
        }
    }

    @Test
    void testSequentialWrites() throws Exception {
        int numWrites = 1000;

        // Perform sequential writes
        for (int i = 0; i < numWrites; i++) {
            try (MiniBoltDB.WriteTransaction tx = db.beginWrite()) {
                String key = "key_" + i;
                String value = "value_" + i;
                tx.put(key, value.getBytes(StandardCharsets.UTF_8));
                tx.commit();
            }
        }

        // Verify all writes were successful
        try (MiniBoltDB.ReadTransaction tx = db.beginRead()) {
            for (int i = 0; i < numWrites; i++) {
                String key = "key_" + i;
                String expectedValue = "value_" + i;
                byte[] value = tx.get(key);
                assertNotNull(value, "Value should exist for key: " + key);
                assertEquals(expectedValue, new String(value, StandardCharsets.UTF_8));
            }
        }
    }
}
