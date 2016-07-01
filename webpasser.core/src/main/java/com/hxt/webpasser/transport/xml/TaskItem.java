/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: TaskItem.java
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
@XStreamAlias("task")
public class TaskItem {

	@XStreamAsAttribute
	private String taskName;
	@XStreamAsAttribute
	private String taskPath;
	
	private Scheduler scheduler;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskPath() {
		return taskPath;
	}
	public void setTaskPath(String taskPath) {
		this.taskPath = taskPath;
	}
	public Scheduler getScheduler() {
		return scheduler;
	}
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
}
