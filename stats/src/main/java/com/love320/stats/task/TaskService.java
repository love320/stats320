package com.love320.stats.task;


import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import com.love320.stats.core.Config;
import com.love320.stats.storage.IAfter;
import com.love320.stats.storage.IDataBase;
import com.love320.stats.storage.IStorage;


/**
 * 时间任务调试服务
 * @author z{user}d
 *
 */
@Service
public class TaskService {
	
	private Scheduler scheduler;
	
	/**
	 * 初始化时间任务
	 * @return 真 假
	 */
	@PostConstruct
	public boolean initQuartz() {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();  
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	public boolean add(Config config,IStorage storage,IDataBase database,IAfter after){
		JobDataMap jobdataMap = new JobDataMap();
		jobdataMap.put("config", config);
		jobdataMap.put("storage", storage);
		jobdataMap.put("database",database);
		jobdataMap.put("after",after);
		JobDetail job = JobBuilder.newJob(Task.class)
				.withIdentity(String.format("%s-Job",config.getIndex()), "groupJob")
				.usingJobData(jobdataMap)
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				  .withIdentity(String.format("%s-Trigger",config.getIndex()), "groupTrigger")
			      .withSchedule(CronScheduleBuilder.cronSchedule(config.getCron()))
			      .build();  
		
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean remove(Config config){
		try {
            scheduler.pauseTrigger(new TriggerKey(String.format("%s-Trigger",config.getIndex()), "groupTrigger"));//停止
			scheduler.unscheduleJob(new TriggerKey(String.format("%s-Trigger",config.getIndex()), "groupTrigger"));//移除触发器
			scheduler.deleteJob(new JobKey(String.format("%s-Job",config.getIndex()), "groupJob")); //删除任务
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
}
