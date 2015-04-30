package com.love320.stats.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love320.stats.filter.FilterService;
import com.love320.stats.filter.ZInteger;
import com.love320.stats.filter.ZString;
import com.love320.stats.filter.Zbase;
import com.love320.stats.handler.Handler;
import com.love320.stats.handler.HandlerService;
import com.love320.stats.storage.impl.AfterService;
import com.love320.stats.storage.impl.LocalJVMData;
import com.love320.stats.storage.impl.MysqlDataBase;
import com.love320.stats.task.TaskService;
import com.love320.stats.utils.ConcatUtil;

/**
 * 初始化配置信息
 * 
 * @author love320
 * 
 */
@Service
public class InitApp {
	
	private static final Logger logger = LoggerFactory.getLogger(InitApp.class);
	
	@Autowired
	private FilterService filterService;
	
	@Autowired 
	private HandlerService handlerService;
	
	@Autowired
	private LocalJVMData localJVMData;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private MysqlDataBase mysqlDataBase;
	
	@Autowired
	private AfterService afterService;

	@PostConstruct
	public boolean initConfig() {
		logger.info("初始化配置信息...");
		
		// 获取配置信息
		List<Config> configs = configList();
		
		for(Config sing:configs){
			Handler handler = new Handler(sing);
			
			localJVMData.initDB(ConcatUtil.undbkey(sing));
			localJVMData.initDB(ConcatUtil.dbkey(sing));
			handler.setStorage(localJVMData);
			
			handler.setFilterService(filterService);
			filterService.add(sing, filters(sing));//加入过滤器
			handlerService.add(handler);//加入处理器
			taskService.add(sing, localJVMData,mysqlDataBase,afterService);//统计结果持久化
			
			//检查是否有后处理
			if(!StringUtils.isBlank(sing.getSrcIndex())) afterService.addSrcIndex(sing.getSrcIndex());
		}
		
		return true;
	}

	
	/**
	 * 初始过滤实体
	 * @param config
	 * @return
	 */
	private CopyOnWriteArrayList<Zbase> filters(Config config){
		CopyOnWriteArrayList<Zbase> zbase = new CopyOnWriteArrayList<Zbase>();
		List<String> filters = config.getFilters();
		for(String sing:filters){
			String[] strs = StringUtils.split(sing,Constant.COLONS);
			if(strs.length == 2){
				if(strs[0].equals("S")) zbase.add(new ZString(strs[1]));
				if(strs[0].equals("I")) zbase.add(new ZInteger(strs[1]));
			}
		}
		return zbase;
	}
	
	/**
	 * 获取配置文件列表
	 * @return
	 */
	private List<Config> configList() {
		List<Config> cs = new ArrayList<Config>();
		cs.add(configPv10());// 统计pv 10分钟
		cs.add(configPvSrc10());// 叠加统计configPv10
		cs.add(configPvSrcMin());
		cs.add(configUv30());// 统计uv 30分钟
		return cs;
	}

	/**
	 * 统计pv 10分钟
	 * @return
	 */
	private Config configPv10() {
		Config config = new Config();
		config.setIndex("jWhn48iO");
		String[] filters = { "S:mid", "S:version", "S:appId", "I:calcCount" };// 过滤字段
		config.setFilters(Arrays.asList(filters));
		config.setIsize(false);
		config.setValue("calcCount");
		String[] columns = { "S:channel", "S:version", "S:appId" };
		config.setColumns(Arrays.asList(columns));
		config.setCron("0/15 * * * * ?");
		config.setSleep(3000);
		config.setTable("statsTables_PV");
		return config;
	}
	
	/**
	 * 统计pv 10分钟
	 * @return
	 */
	private Config configPvSrc10() {
		Config config = new Config();
		config.setIndex("BPqlPV4N");
		config.setSrcIndex("jWhn48iO");
		String[] filters = { "S:jWhn48iO"};// 过滤字段
		config.setFilters(Arrays.asList(filters));
		config.setIsize(false);
		config.setValue("value");
		String[] columns = { "S:channel", "S:version", "S:appId" };
		config.setColumns(Arrays.asList(columns));
		config.setCron("0/50 * * * * ?");
		config.setSleep(3000);
		config.setTable("statsTables_PV_hour");
		return config;
	}
	
	private Config configPvSrcMin() {
		Config config = new Config();
		config.setIndex("VHX9Nr22");
		config.setSrcIndex("BPqlPV4N");
		String[] filters = { "S:BPqlPV4N"};// 过滤字段
		config.setFilters(Arrays.asList(filters));
		config.setIsize(false);
		config.setValue("value");
		String[] columns = { "S:channel", "S:version", "S:appId" };
		config.setColumns(Arrays.asList(columns));
		config.setCron("2 0/2 * * * ?");
		config.setSleep(3000);
		config.setTable("statsTables_PV_Min");
		return config;
	}
	
	/**
	 * 统计uv 30分钟
	 * @return
	 */
	private Config configUv30() {
		Config config = new Config();
		config.setIndex("i5ZjweSq");
		String[] filters = { "S:mid", "S:appId" };// 过滤字段
		config.setFilters(Arrays.asList(filters));
		config.setIsize(true);
		config.setValue("mid");
		String[] columns = { "S:channel", "S:appId" };
		config.setColumns(Arrays.asList(columns));
		config.setCron("0/20 * * * * ?");
		config.setSleep(3000);
		config.setTable("statsTables_UV");
		return config;
	}
	
	

}
