package com.love320.stats.storage;


/**
 * 存储数据
 * @author z{user}d
 *
 */
public interface IStorage {
	

	public boolean setInt(String database,String key,int value);
	
	public int getInt(String database,String key);
	
	public boolean setString(String database,String key,String value);
	
	public int getStringSize(String database,String key);
	
	public boolean clean(String database);
	
	
}
