package com.love320.stats.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import com.love320.stats.filter.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love320.stats.handler.Handler;
import com.love320.stats.handler.HandlerService;
import com.love320.stats.storage.impl.AfterService;
import com.love320.stats.storage.impl.LocalJVMData;
import com.love320.stats.storage.impl.MysqlDataBase;
import com.love320.stats.task.TaskService;

/**
 * 初始化配置信息
 * 
 * @author love320
 * 
 */
@Service
public class InitApp {
	
	private static Logger logger = LoggerFactory.getLogger(InitApp.class);
	
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

			handler.setStorage(localJVMData);
			
			handler.setFilterService(filterService);
			filterService.add(sing, filters(sing));//加入过滤器
			handlerService.add(handler);//加入处理器
			taskService.add(sing, localJVMData,mysqlDataBase,afterService);//统计结果持久化
			
			//检查是否有后处理
			if(!StringUtils.isBlank(sing.getSrcIndex())) afterService.addSrcIndex(sing.getSrcIndex());
		}

        goThread(afterService);
        goThread(mysqlDataBase);

		return true;
	}

	
	/**
	 * 初始过滤实体
	 * @param config 配置
	 * @return 过滤对象
	 */
	private CopyOnWriteArrayList<ZBase> filters(Config config){
		CopyOnWriteArrayList<ZBase> zbase = new CopyOnWriteArrayList<ZBase>();
		List<String> filters = config.getFilters();
		for(String sing:filters){
			String[] strS = StringUtils.split(sing,Constant.COLONS);
			if(strS.length == 2){
				if(strS[0].equals("S")) zbase.add(new ZString(strS[1]));
				if(strS[0].equals("I")) zbase.add(new ZInteger(strS[1]));
                if(strS[0].equals("N")) zbase.add(new ZNull(strS[1]));
			}
		}
		return zbase;
	}

    /**
     * 注册使用线程
     */
    private boolean goThread(Runnable run){
        Thread thread = new Thread(run);
        thread.start();
        return true;
    }
	
	/**
	 * 获取配置文件列表
	 * @return 配置列表
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
	 * 统计pv 15秒
	 * @return 配置对象
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
	 * 统计pv 50秒
	 * @return 配置对象
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

    /**
     * 统计pv 2分钟
     * @return 配置对象
     */
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
	 * 统计uv 20秒
	 * @return 配置对象
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
