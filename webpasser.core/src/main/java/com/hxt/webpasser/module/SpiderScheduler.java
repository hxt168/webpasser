/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderScheduler.java
 *   
 */
package com.hxt.webpasser.module;

import org.quartz.Scheduler;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderScheduler {

	//private Scheduler scheduler;
	
	private String cronExpression;
	
	/**
	 * 执行周期： 0:每隔  1:每天  2:每周  3:每月 4:自定义
	 */
	private int taskCircle;
	/**
	 * 是否启动定时
	 */
	private int isRun;
	
	/**
	 * 本系统的时间语法
	 */
	private String timeString;

/*	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}*/

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public int getTaskCircle() {
		return taskCircle;
	}

	public void setTaskCircle(int taskCircle) {
		this.taskCircle = taskCircle;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public int getIsRun() {
		return isRun;
	}

	public void setIsRun(int isRun) {
		this.isRun = isRun;
	}
	
	
	
	
}
