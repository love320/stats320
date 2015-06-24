package com.love320.stats.storage;


/**
 * 存储数据
 * @author zd
 *
 */
public interface IStorage {

    /**
     * 初始化数据库
     * @param database 索引
     * @return 真 假
     */
	public boolean initDB(String database);

	/**
	 * 设置整形的对象
	 * @param database 库索引
	 * @param key 索引
	 * @param value 值
	 * @return 真 假
	 */
	public boolean setInt(String database,String key,Integer value);
	
	/**
	 * 获取整形的对象
	 * @param database 库索引
	 * @param key 索引
	 * @return 真 假
	 */
	public int getInt(String database,String key);
	
	/**
	 * 设置字符串的对象
	 * @param database 库索引
	 * @param key 索引
	 * @param value 值
	 * @return 真 假
	 */
	public boolean setString(String database,String key,String value);
	
	/**
	 * 获取字符串的集合数
	 * @param database 库索引
	 * @param key 索引
	 * @return 真 假
	 */
	public int getStringSize(String database,String key);
	
	/**
	 * 清空数据数据库
	 * @param database 库索引
	 * @return 真 假
	 */
	public boolean clean(String database);
	
	/**
	 * 获取指定数据库的所有keys
	 * @param database 库索引
	 * @return 真 假
	 */
	public String[] keys(String database);
	
	
}
