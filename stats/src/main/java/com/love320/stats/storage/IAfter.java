package com.love320.stats.storage;

import java.util.Map;

import com.love320.stats.core.Config;

/**
 * 后处理接口
 */
public interface IAfter {
	
	public boolean processor(long millis, Config config,Map<String, Object> data,int value);
	
	public void addSrcIndex(String index);

}