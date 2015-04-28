package com.love320.stats.storage;


/**
 * 存储数据
 * @author z{user}d
 *
 */
public interface IStorage {
	

	/**
	 * 设置整形的对象
	 * @param database
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setInt(String database,String key,Integer value);
	
	/**
	 * 获取整形的对象
	 * @param database
	 * @param key
	 * @return
	 */
	public int getInt(String database,String key);
	
	/**
	 * 设置字符串的对象
	 * @param database
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setString(String database,String key,String value);
	
	/**
	 * 获取字符串的集合数
	 * @param database
	 * @param key
	 * @return
	 */
	public int getStringSize(String database,String key);
	
	/**
	 * 清空数据数据库
	 * @param database
	 * @return
	 */
	public boolean clean(String database);
	
	/**
	 * 获取指定数据库的所有keys
	 * @param database
	 * @return
	 */
	public String[] keys(String database);
	
	
}
