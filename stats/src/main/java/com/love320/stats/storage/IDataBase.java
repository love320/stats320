package com.love320.stats.storage;

import java.util.Map;

/**
 * 统计数据持久
 * @author z{user}d
 *
 */
public interface IDataBase {
	
	public boolean write(String table,Map<String,Object> data,int value);

}
