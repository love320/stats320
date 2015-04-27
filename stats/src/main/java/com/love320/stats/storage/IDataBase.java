package com.love320.stats.storage;

import java.util.Map;

public interface IDataBase {
	
	public boolean write(String table,Map<String,Object> data);

}
