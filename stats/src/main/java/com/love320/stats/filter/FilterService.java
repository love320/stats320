package com.love320.stats.filter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;
import com.love320.stats.core.Config;

@Service
public class FilterService {
	
	/**
	 * 存储过滤组信息,使用map存放
	 */
	private ConcurrentHashMap<String,CopyOnWriteArrayList<ZBase>> filterMap = new ConcurrentHashMap<String,CopyOnWriteArrayList<ZBase>>();
	
	
	/**
	 * 从指定的配置文件索引中获取对应的过滤器组
	 * @param config
	 * @return
	 */
	public CopyOnWriteArrayList<ZBase> filtersByIndex(Config config){
		return filterMap.get(config.getIndex());
	}
	
	/**
	 * 添加
	 * @param config
	 * @param filters
	 * @return
	 */
	public boolean add(Config config,CopyOnWriteArrayList<ZBase> filters){
		filterMap.put(config.getIndex(), filters);
		return true;
	}
	
	/**
	 * 移除
	 * @param config
	 * @return
	 */
	public boolean remove(Config config){
		filterMap.remove(config.getIndex());
		return true;
	}
	
	
}
