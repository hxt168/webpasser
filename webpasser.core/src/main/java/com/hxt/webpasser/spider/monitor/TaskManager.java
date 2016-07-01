/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: TaskManage.java
 *   
 */
package com.hxt.webpasser.spider.monitor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hxt.webpasser.callback.ProcessCrawlUrlCallBack;
import com.hxt.webpasser.exception.SpiderException;
import com.hxt.webpasser.module.TaskInfo;
import com.hxt.webpasser.spider.SpiderController;
import com.hxt.webpasser.spider.SpiderTask;
import com.hxt.webpasser.spider.SpiderTaskFactory;
import com.hxt.webpasser.transport.xml.Scheduler;
import com.hxt.webpasser.utils.ResourceUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */

public class TaskManager {

	private TaskManager(){
		
		
	}
	private static TaskManager taskManager=new TaskManager();
	public static TaskManager getInstance()
	{
		return taskManager;
	}
	public List<TaskInfo> getTaskInfoList() {
		
/*		List<TaskInfo> tasks=new ArrayList<TaskInfo>();
		String xmlDirPath=ResourceUtil.getResourcePath()+"catch";
		putXmlFiles( xmlDirPath,tasks);
		for(TaskInfo taskInfo:tasks)
		{
			taskInfo.load();
		}*/
		
		
		return SpiderTaskFactory.getInstance().getTaskInfos();
	}
	
	/**
	 * 得到任务运行信息
	 * @param taskName
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> monitorSpiderTaskInfo(String taskName) throws Exception
	{
		TaskInfo taskInfo=SpiderTaskFactory.getInstance().getTaskByName(taskName);
		//checkTaskEmpty(taskInfo);
		if(taskInfo.getSpiderTask()==null) //未开始
		{
			Map<String,Object> taskInfoMap=new HashMap<String, Object>();
			taskInfoMap.put("taskName", taskName);
			taskInfoMap.put("runState", 0);
			taskInfoMap.put("spiderScheduler", taskInfo.getSpiderScheduler());
			return taskInfoMap;
		}else{
			Map<String,Object> info=taskInfo.getSpiderTask().monitorSpiderTaskInfo();
			info.put("spiderScheduler", taskInfo.getSpiderScheduler());
			return info;
		}
	
	}
	
	public SpiderTask getSpiderTask(String taskName){
		
		TaskInfo taskInfo=SpiderTaskFactory.getInstance().getTaskByName(taskName);
		//checkTaskEmpty(taskInfo);
		if(taskInfo.getSpiderTask()!=null) //未开始
		{
			return taskInfo.getSpiderTask();
		}
		return null;
	}
	
	/**
	 * 对任务进行操作，启动、暂停，恢复、停止
	 * @param taskName
	 * @param opRun 操作类型    1：启动   2：暂停  3 恢复  4  停止
	 * @throws Exception
	 */
	public void operateTask(String taskName,int opRun) throws Exception
	{
		TaskInfo taskInfo=SpiderTaskFactory.getInstance().getTaskByName(taskName);
		//checkTaskEmpty(taskInfo);
		switch (opRun) {   //操作类型
			case SpiderController.OPERATE_START_ACTION:  

				try {
					taskInfo.load();
					taskInfo.getSpiderTask().start(); //启动
				} catch (Exception e) {
					e.printStackTrace();
					taskInfo.getSpiderTask().stop();
				}
				break;
			case SpiderController.OPERATE_PAUSE_ACTION:  //暂停状态 
				taskInfo.getSpiderTask().pause();
				break;
			case SpiderController.OPERATE_RESTART_ACTION:  //恢复状态 
				taskInfo.getSpiderTask().reStart();
				break;
			case SpiderController.OPERATE_STOP_ACTION:  //停止状态 
				taskInfo.getSpiderTask().stop();
				break;
			default:
				break;
		}

		
	}
	
	/**
	 * 一次url抓取测试
	 * @param taskName
	 * @param testUrl
	 * @param processCrawlUrlCallBack
	 * @throws Exception
	 */
	public void testOneFetchAndParse(String taskName,String testUrl,ProcessCrawlUrlCallBack processCrawlUrlCallBack) throws Exception{
		TaskInfo taskInfo=SpiderTaskFactory.getInstance().getTaskByName(taskName);
		if(taskInfo.getSpiderTask()!=null&&taskInfo.getSpiderTask().checkIsRunOrPauseState()){  //正在运行中，不能执行
			throw new Exception("任务正在运行中，不能执行！");
		}
		reloadTask( taskName);
		taskInfo.getSpiderTask().testOneFetchAndParse(testUrl, processCrawlUrlCallBack);
		
	}
	
	
	public void reloadTask(String taskName) throws Exception
	{
		TaskInfo taskInfo=SpiderTaskFactory.getInstance().getTaskByName(taskName);
		taskInfo.reload();
		
	}
	
	
	public void addOrChangeScheduler(String taskName,Scheduler scheduler,boolean isAdd) throws Exception{
		SpiderTaskFactory.getInstance().addOrChangeScheduler(taskName, scheduler, isAdd);
		
	}
	
	/**
	 * 开启或关闭定时
	 * @param taskName
	 * @param isStart  true 开启  false 关闭
	 * @throws Exception
	 */
	public void startOrStopScheduler(String taskName,boolean isStart) throws Exception{
		if(isStart){
			SpiderTaskFactory.getInstance().startScheduler(taskName);
		}else{
			SpiderTaskFactory.getInstance().stopScheduler(taskName);
		}
		
	}
	
	/**
	 * 判断task是否加载
	 * @param taskInfo
	 */
	private void checkTaskEmpty(TaskInfo taskInfo) throws Exception
	{
		if(taskInfo==null||taskInfo.getSpiderTask()==null)
		{
			throw new SpiderException("task任务不存在或加载失败！");
		}

	}
	
	 /*
	  * 通过递归得到某一路径下所有的目录及其文件
	  */
/*	 public void putXmlFiles(String filePath,List<TaskInfo> tasks){
	  File root = new File(filePath);
	    File[] files = root.listFiles();
	    for(File file:files){     
	     if(file.isDirectory()){
	      
	       * 递归调用
	       
	    	 putXmlFiles(file.getAbsolutePath(),tasks);
	    	 
	    	 System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
	     }else{
	    	 TaskInfo taskInfo=new TaskInfo();
	    	 taskInfo.setTaskPath(file.getAbsolutePath());
	    	 tasks.add(taskInfo);
	     }     
	    }
	 }
*/
	public static void main(String[] args) {
		
		
		
	}
	
}
