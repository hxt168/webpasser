/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: Scheduler.java
 *   
 */
package com.hxt.webpasser.transport.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
@XStreamAlias("scheduler")
public class Scheduler {
	@XStreamAsAttribute
	private String cronExpression;
	
	/**
	 * 执行周期： 0:每隔  1:每天  2:每周  3:每月 4:自定义
	 */
	@XStreamAsAttribute
	private Integer taskCircle;
	
	/**
	 * 本系统的时间语法
	 */
	@XStreamAsAttribute
	private String timeString;
	/**
	 * 是否启动定时
	 */
	@XStreamAsAttribute
	private Integer isRun;

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getTaskCircle() {
		return taskCircle;
	}

	public void setTaskCircle(Integer taskCircle) {
		this.taskCircle = taskCircle;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public Integer getIsRun() {
		return isRun;
	}

	public void setIsRun(Integer isRun) {
		this.isRun = isRun;
	}

	
	
	
	
}
