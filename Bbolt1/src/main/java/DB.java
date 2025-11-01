
/*
*  first we need to write key value in a file
* 
*
*/

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

class DB{
	private String dbName;
	private File file;
	private HashMap<String,String>cache;
	DB(String dbName){
		this.dbName = dbName;
		this.file = new File(dbName);

		this.cache = new HashMap<>();

		populateCache();
	}

	boolean setKey(String key,String value){
		try(FileOutputStream fileOutputStream = new FileOutputStream(file,true)){
			String g = key+ "=" +value+"\n";
			fileOutputStream.write(g.getBytes());
			cache.put(key,value);
		}catch (Exception e){
			System.out.println(e);
			return false;
		}
		return true;
	}
	String getKey(String key){
		if(cache.containsKey(key))
			return cache.get(key);
		return null;

	}

	void populateCache(){
		try(FileInputStream fileInputStream = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))
		){
			String line =null;
			String res= null;
			while((line=bufferedReader.readLine())!=null){
				String[] part = line.split("=");
				if (part.length == 2) {
					String key1 = part[0];
					String value = part[1];
					cache.put(key1,value);
				}
			}
		}catch (Exception e){
			e.getStackTrace();
		}
	}
	
	
	public static void main(String args[]){
		
		DB db = new DB("key-value");

		Scanner sc = new Scanner(System.in);

		db.setKey("1","Deepak");
		System.out.println(db.getKey("1"));
		db.setKey("2","gsdgsgsg");
		System.out.println(db.getKey("2"));
		db.setKey("1","PINK");
		System.out.println(db.getKey("1"));

		
	}
}