package com.love320.stats.handler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import com.love320.stats.filter.ZBase;
import com.love320.stats.utils.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.love320.stats.core.Config;
import com.love320.stats.filter.FilterService;
import com.love320.stats.storage.IStorage;

public class Handler {
	
	private static final Logger logger = LoggerFactory.getLogger(Handler.class);
	
	/**
	 * 唯一标注索引
	 */
	private final String index = UUID.randomUUID().toString();
	
	/**
	 * 配置信息
	 */
	private Config config;
	
	/**
	 * 过滤服务
	 */
	private FilterService filterService;
	
	/**
	 * 存储计算
	 */
	private IStorage storage;
	
	public Handler(Config config){
		this.config = config;
	}
	
	/**
	 * 处理
	 * @param map 数据
	 * @return 真 假
	 */
	public boolean exe(Map<String,Object> map){
		CopyOnWriteArrayList<ZBase> filters = filterService.filtersByIndex(config);//过滤
		for(ZBase sing:filters) if(sing.isValid(map.get(sing.name()))) return false;//执行过滤
		String key = KeyUtil.key(config, map);//生成key
		if(config.isWayFT()) storage.setString(KeyUtil.dbKey(config), key, String.valueOf(map.get(config.getValue()).toString()));
		if(!config.isWayFT()) storage.setInt(KeyUtil.dbKey(config), key, Integer.valueOf(map.get(config.getValue()).toString()));
		return true;
	}


	public String getIndex() {
		return index;
	}


	public void setFilterService(FilterService filterService) {
		this.filterService = filterService;
	}

	public void setStorage(IStorage storage) {
		this.storage = storage;
	}

	
	
	

}
