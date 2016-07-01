/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderTaskJob.java
 *   
 */
package com.hxt.webpasser.quartz.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hxt.webpasser.module.TaskInfo;
import com.hxt.webpasser.spider.SpiderTaskFactory;
import com.hxt.webpasser.utils.RunTimeCost;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */

//前一个job还在运行，则不运行
@DisallowConcurrentExecution
public class SpiderTaskJob implements Job {

	private static Logger logger = LoggerFactory.getLogger(SpiderTaskJob.class);
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		String taskName = (String) context.getJobDetail().getJobDataMap().get("taskName");
		if(taskName!=null){
			logger.info("定时任务  taskName:" + taskName+"开始");
			
			RunTimeCost timeCost=new RunTimeCost();
			TaskInfo taskInfo=SpiderTaskFactory.getInstance().getTaskByName(taskName);
			if(taskInfo.getSpiderTask()==null||!taskInfo.getSpiderTask().checkIsRunOrPauseState()){
				try {
					taskInfo.load();
					taskInfo.getSpiderTask().start(); //启动
				} catch (Exception e) {
					e.printStackTrace();
					taskInfo.getSpiderTask().stop();
				}
				timeCost.endTime();
				logger.info("定时任务  taskName:" + taskName+"结束,耗时"+timeCost.getCostTimeSecond()+"秒");
			}else{
				logger.info("任务  taskName:" + taskName+"正在运行，不能执行。此次定时任务结束！");
			}
			
	
			
		}

		
		
	}

	
	
	
}
