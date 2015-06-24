package com.love320.stats.storage;

import java.util.Map;

/**
 * 统计数据持久
 * @author zd
 */
public interface IDataBase {
	
	public boolean write(long millis,String table,Map<String,Object> data,int value);

}
