/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: TaskInfo.java
 *   
 */
package com.hxt.webpasser.module;

import com.hxt.webpasser.spider.SpiderGlobalProperties;
import com.hxt.webpasser.spider.SpiderTask;
import com.hxt.webpasser.utils.FileUtil;
import com.hxt.webpasser.utils.MyDateUtil;
import com.hxt.webpasser.utils.ResourceUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 功能说明: 抓取任务信息 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
//@XStreamAlias("task")
public class TaskInfo {

	
	//@XStreamAsAttribute
	private String taskName;
	//@XStreamAsAttribute
	private String taskPath;
	
	private SpiderTask spiderTask;
	
	private SpiderScheduler spiderScheduler;
	
	public void load() {
	/*	if(spiderTask==null)
		{*/
			String xmlDirPath=ResourceUtil.getResourcePath()+"/"+taskPath;
			spiderTask=new SpiderTask(taskName,xmlDirPath);
			taskName=spiderTask.getTaskName();
	//	}
		
	}
	
	public String readXmlContent(){
		String xmlDirPath=ResourceUtil.getResourcePath()+"/"+taskPath;
		String con=FileUtil.readTxtFile(xmlDirPath);
		return con;
	}
	
	
	public void writeXmlContent(String xmlContent) throws Exception{
		String xmlDirPath=ResourceUtil.getResourcePath()+"/"+taskPath;
		if(FileUtil.checkIsExist(xmlDirPath)){  //已存在，备份
			String backPath=ResourceUtil.getResourcePath()+"/"+SpiderGlobalProperties.DEFAULT_TASK_XML_FOLDER+"/backup/"+taskName+".xml."+MyDateUtil.getCurrentTime("yyyyMMDDHHmmss");
			FileUtil.moveFile(xmlDirPath, backPath);
		}
		
		FileUtil.createFile(xmlDirPath, xmlContent,"utf-8");
	}
	
	public void reload() {
		spiderTask=null;
		load();
	}

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

	public SpiderTask getSpiderTask() {
		return spiderTask;
	}

	public void setSpiderTask(SpiderTask spiderTask) {
		this.spiderTask = spiderTask;
	}

	public SpiderScheduler getSpiderScheduler() {
		return spiderScheduler;
	}

	public void setSpiderScheduler(SpiderScheduler spiderScheduler) {
		this.spiderScheduler = spiderScheduler;
	}
	
	
	
	
}
