package com.love320.stats.storage.impl;

import java.util.ArrayList;
import java.util.List;
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
     * 缓存池pool String类型
     */
    private final ConcurrentLinkedQueue<ConcurrentHashMap<String,String>> cacheStrPool = new ConcurrentLinkedQueue<ConcurrentHashMap<String,String>>();

    /**
     *缓存池pool Integer类型
     */
    private final ConcurrentLinkedQueue<ConcurrentLinkedQueue<Integer>> cacheIntPool = new ConcurrentLinkedQueue<ConcurrentLinkedQueue<Integer>>();

    /**
     * 从池中取对象
     * @return 对象
     */
    private ConcurrentHashMap<String,String> newCHM(){
        synchronized(cacheStrPool){
            if(!cacheStrPool.isEmpty()) return cacheStrPool.poll();
        }
        return new ConcurrentHashMap<String,String>();
    }

    /**
     * 从池中取对象
     * @return 对象
     */
    private ConcurrentLinkedQueue<Integer> newCLQ(){
        synchronized(cacheIntPool){
            if(!cacheIntPool.isEmpty()) return cacheIntPool.poll();
        }
        return new ConcurrentLinkedQueue<Integer>();
    }

	public boolean initDB(String database) {
		return initDatabase(database) == null;
	}
	
	/**
	 * 初始数据库
	 * @param index 索引
	 * @return 对象
	 */
	private synchronized ConcurrentHashMap<String,Object> initDatabase(String index){

        ConcurrentHashMap<String,Object> data = (ConcurrentHashMap<String, Object>) database.get(index);
        if(data != null) return data;//返回

        //初始化数据库
		ConcurrentHashMap<String,Object> newDatabase = new ConcurrentHashMap<String,Object>();
		database.put(index, newDatabase);
        logger.info("初始数据库:"+index);
		return newDatabase;
	}
	
	/**
	 * 获取数据库
	 * @param index 索引
	 * @return 对象
	 */
	private ConcurrentHashMap<String,Object> getDatabase(String index){
		ConcurrentHashMap<String,Object> data = (ConcurrentHashMap<String, Object>) database.get(index);
		if(data == null) return initDatabase(index);//初始化
		return data;
	}
	
	/**
	 * 从指定数据库对象中查找对应的key的对象
	 * @param data 数据库
	 * @param key 索引
	 * @return 查找队列
	 */
	private ConcurrentLinkedQueue<Integer> getIntValue(ConcurrentHashMap<String,Object> data,String key){
		ConcurrentLinkedQueue<Integer> link = (ConcurrentLinkedQueue<Integer>) data.get(key);
		if(link == null){
			link = newCLQ();
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
        link.offer(num);//从队列中提取数据合并后,把结果再写回.
		return num;
	}
	
	private ConcurrentHashMap<String,String> getStringValue(ConcurrentHashMap<String,Object> data,String key){
		ConcurrentHashMap<String,String> hashMap = (ConcurrentHashMap<String,String>) data.get(key);
		if(hashMap == null){
            hashMap = newCHM();
			data.put(key, hashMap);
		}
		return hashMap;
	}

	/**
	 * 
	 */
	public boolean setString(String database, String key, String value) {
		ConcurrentHashMap<String,Object> data = getDatabase(database);
		ConcurrentHashMap<String,String> hashmap = getStringValue(data,key);
		String sing = hashmap.get(value);
		if(sing == null ) hashmap.put(value, value);
		return true;
	}

	public int getStringSize(String database, String key) {
		ConcurrentHashMap<String,Object> data = getDatabase(database);
		ConcurrentHashMap<String,String> hashmap = getStringValue(data,key);
		return hashmap.size();
	}

	public boolean clean(String database) {
		ConcurrentHashMap<String,Object> data = getDatabase(database);//获取被清理缓存数据对象
        //锁定数据库
        synchronized(data){
            //遍例map所有key-obj
            for (Entry<String, Object> entry : data.entrySet()){
                Object obj = data.get(entry.getKey());
                //判断为ConcurrentHashMap对象
                if(obj instanceof ConcurrentHashMap){
                    ConcurrentHashMap<String,String> temp = (ConcurrentHashMap<String,String>)obj;
                    temp.clear();//清空,初始化
                    cacheStrPool.offer(temp);//加入池队列
                }
                //判断为ConcurrentLinkedQueue对象
                if(obj instanceof ConcurrentLinkedQueue){
                    ConcurrentLinkedQueue<Integer> temp = (ConcurrentLinkedQueue<Integer>)obj;
                    temp.clear();//清空,初始化
                    cacheIntPool.offer(temp);//加入池队列
                }
            }
            //清空数据
            data.clear();
        }
		return true;
	}

    /**
     * 获取指定数据库的所有key
     * @param database 数据库的索引
     * @return String[] 返回所有keys
     */
	public String[] keys(String database){
		ConcurrentHashMap<String,Object> data = getDatabase(database);
        List<String> list = new ArrayList<String>();
		for (Entry<String, Object> entry : data.entrySet())  list.add(entry.getKey());
		return list.toArray(new String[list.size()]);
	}

}
