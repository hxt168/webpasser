/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: QuartzManager.java
 *   
 */
package com.hxt.webpasser.quartz;


import java.util.HashMap;
import java.util.Map;


import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import com.hxt.webpasser.quartz.job.SpiderTaskJob;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderQuartzManager {

	  private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	  private static String JOB_GROUP_NAME = "SPIDER_JOBGROUP_NAME";
	 // private static String TRIGGER_GROUP_NAME = "SPIDER_TRIGGERGROUP_NAME";

	  /**
	   * 添加一个定时任务
	   * 
	   * @throws SchedulerException 
	   */
	  public static void addJob(String taskName, String cronExpression) throws SchedulerException {
		  Scheduler sched = gSchedulerFactory.getScheduler();
		  Map<String,Object> jobClassParam=new HashMap<String, Object>();
		  jobClassParam.put("taskName", taskName);
		  //同步或异步
		  QuartzScheduleUtils.createScheduleJob(sched, taskName, JOB_GROUP_NAME, cronExpression, SpiderTaskJob.class, jobClassParam);
 
	  }


	  public static void modifyJobTime(String taskName, String cronExpression) throws SchedulerException {
		  Scheduler sched = gSchedulerFactory.getScheduler();
		  QuartzScheduleUtils.updateScheduleJob(sched, taskName, JOB_GROUP_NAME, cronExpression);
		  
	  }


	  /**
	   * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	   * 
	   * @param jobName
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:49:51
	   * @version V2.0
	   */
	  public static void removeJob(String taskName) {
	    try {
	      Scheduler sched = gSchedulerFactory.getScheduler();
	      QuartzScheduleUtils.resumeJob(sched, taskName, JOB_GROUP_NAME);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }


	  /**
	   * @Description:启动所有定时任务
	   * 
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:50:18
	   * @version V2.0
	   */
	  public static void startJobs() {
	    try {
	      Scheduler sched = gSchedulerFactory.getScheduler();
	      sched.start();
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }

	  /**
	   * @Description:关闭所有定时任务
	   * 
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:50:26
	   * @version V2.0
	   */
	  public static void shutdownJobs() {
	    try {
	      Scheduler sched = gSchedulerFactory.getScheduler();
	      if (!sched.isShutdown()) {
	        sched.shutdown();
	      }
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }
	
	public static void main(String[] args) throws SchedulerException, InterruptedException {
		
		startJobs();
		addJob("hhh", "*/10 * * * * ?");
		//Thread.sleep(3000);
		//addJob("hxt", "*/10 * * * * ?");
	}
	
}
