/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: Tasks.java
 *   
 */
package com.hxt.webpasser.transport.xml;

import java.util.Vector;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
@XStreamAlias("tasks")
public class Tasks {
	 @XStreamImplicit(itemFieldName="task")
	private  Vector<TaskItem> taskItems;

	public Vector<TaskItem> getTaskItems() {
		return taskItems;
	}

	public void setTaskItems(Vector<TaskItem> taskItems) {
		this.taskItems = taskItems;
	}


	
}
