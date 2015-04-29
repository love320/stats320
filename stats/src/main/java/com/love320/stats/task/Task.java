package com.love320.stats.task;


import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.love320.stats.core.Config;
import com.love320.stats.storage.IDataBase;
import com.love320.stats.storage.IStorage;
import com.love320.stats.utils.ConcatUtil;


/**
 * 定时回收统计信息并初始化统计的任务
 * @author z{user}d
 *
 */
public class Task  implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(Task.class);
	
	/**
	 * 配置信息
	 */
	private Config config = null;
	
	/**
	 * 存储计算
	 */
	private IStorage storage = null;
	
	/**
	 * 持久化保存
	 */
	private IDataBase dataBase = null;
	
	private void exe(){
		
		config.setMaster(!config.isMaster());//更换数据库
		
		//程序休息一下.等待完成更换数据库成功.
		try { 
			Thread.sleep (config.getSleep()) ; 
		} catch (InterruptedException ie){}
		
		String[] keys = storage.keys(ConcatUtil.undbkey(config));
		for(String sing:keys){
			int value = 0;
			if(config.isIsize() == false) value = storage.getInt(ConcatUtil.undbkey(config), sing);//返回以整数统计信息值
			if(config.isIsize() == true) value = storage.getStringSize(ConcatUtil.undbkey(config), sing);//返回以字符串统计总数的值
			dataBase.write(config.getTable(),ConcatUtil.keyToMap(sing),value);//持久化保存
		}
		
		//任务完成,清空非活动数据库
		storage.clean(ConcatUtil.undbkey(config));
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobdataMap = context.getJobDetail().getJobDataMap();
		if(config == null) config = (Config) jobdataMap.get("config");
		if(storage == null) storage = (IStorage) jobdataMap.get("storage");
		if(dataBase == null) dataBase = (IDataBase) jobdataMap.get("database");
		exe();//执行任务
	}


	
}
