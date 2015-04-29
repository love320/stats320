package com.love320.stats.storage.impl;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.love320.stats.storage.IStorage;

@Service
public class LocalJVMData implements IStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(LocalJVMData.class);
	
	private ConcurrentHashMap<String,Object> database = new ConcurrentHashMap<String,Object>();
	
	/**
	 * 初始数据库
	 * @param index
	 * @return
	 */
	private ConcurrentHashMap<String,Object> initDatabase(String index){
		ConcurrentHashMap<String,Object> newDatabase = new ConcurrentHashMap<String,Object>();
		database.put(index, newDatabase);
		return newDatabase;
	}
	
	/**
	 * 获取数据库
	 * @param index
	 * @return
	 */
	private ConcurrentHashMap<String,Object> getDatabase(String index){
		ConcurrentHashMap<String,Object> data = (ConcurrentHashMap<String, Object>) database.get(index);
		if(data == null) return initDatabase(index);//初始化
		return data;
	}
	
	/**
	 * 从指定数据库对象中查找对应的key的对象
	 * @param data
	 * @param key
	 * @return
	 */
	private ConcurrentLinkedQueue<Integer> getIntValue(ConcurrentHashMap<String,Object> data,String key){
		ConcurrentLinkedQueue<Integer> link = (ConcurrentLinkedQueue<Integer>) data.get(key);
		if(link == null){
			link = new ConcurrentLinkedQueue<Integer>();
			data.put(key, link);
		}
		return link;
	}
	
	/**
	 * 设置指定数据库的key的对应值
	 */
	public boolean setInt(String database, String key, Integer value) {
		ConcurrentHashMap<String,Object> data = getDatabase(database);
		ConcurrentLinkedQueue<Integer> link = getIntValue(data,key);
		link.offer(value);
		return false;
	}

	/**
	 * 获取指定数据库的key的对应值
	 */
	public int getInt(String database, String key) {
		ConcurrentHashMap<String,Object> data = getDatabase(database);
		ConcurrentLinkedQueue<Integer> link = getIntValue(data,key);
		Integer num = 0;
		synchronized(link){
			while (!link.isEmpty()) {
	            num +=link.poll();
	        }
		}
		return num.intValue();
	}
	
	private ConcurrentHashMap<String,String> getStringValue(ConcurrentHashMap<String,Object> data,String key){
		ConcurrentHashMap<String,String> hashmap = (ConcurrentHashMap<String,String>) data.get(key);
		if(hashmap == null){
			hashmap = new ConcurrentHashMap<String,String>();
			data.put(key, hashmap);
		}
		return hashmap;
	}

	/**
	 * 
	 */
	public boolean setString(String database, String key, String value) {
		ConcurrentHashMap<String,Object> data = getDatabase(database);
		ConcurrentHashMap<String,String> hashmap = getStringValue(data,key);
		String sing = hashmap.get(value);
		if(sing == null ) {
			synchronized(hashmap){
				hashmap.put(value, value);
			}
		}
		
		return true;
	}

	public int getStringSize(String database, String key) {
		ConcurrentHashMap<String,Object> data = getDatabase(database);
		ConcurrentHashMap<String,String> hashmap = getStringValue(data,key);
		return hashmap.size();
	}

	public boolean clean(String database) {
		ConcurrentHashMap<String,Object> data = getDatabase(database);
		data.clear();
		return true;
	}

	public String[] keys(String database){
		ConcurrentHashMap<String,Object> data = getDatabase(database);
		String[] keys = new String[data.size()];
		int i = 0;
		for (Entry<String, Object> entry : data.entrySet()){
			keys[i] = entry.getKey();
			i++;
		}
		return keys;
	}

}
