package com.love320.stats.task;


import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
			Thread.sleep (1000) ; 
		} catch (InterruptedException ie){}
		
		String[] keys = storage.keys(ConcatUtil.undbkey(config));
		for(String sing:keys){
			System.out.println("key:"+sing);
			if(config.isIsize() == false) System.out.println("vp.value:"+storage.getInt(ConcatUtil.undbkey(config), sing));
			if(config.isIsize() == true) System.out.println("up.value:"+storage.getStringSize(ConcatUtil.undbkey(config), sing));
			dataBase.write("stats", null);
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
