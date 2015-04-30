package com.love320.stats.storage;

import java.util.Map;

import com.love320.stats.core.Config;

public interface IAfter {
	
	public boolean processor(long millis, Config config,Map<String, Object> data,int value);
	
	public void addSrcIndex(String index);

}