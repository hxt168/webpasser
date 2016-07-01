/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderTaskFactory.java
 *   
 */
package com.hxt.webpasser.spider;


import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.quartz.SchedulerException;

import com.hxt.webpasser.module.SpiderScheduler;
import com.hxt.webpasser.module.TaskInfo;
import com.hxt.webpasser.module.TaskInfos;
import com.hxt.webpasser.quartz.SpiderQuartzManager;
import com.hxt.webpasser.transport.util.XStreamWireFormat;
import com.hxt.webpasser.transport.xml.Scheduler;
import com.hxt.webpasser.transport.xml.TaskItem;
import com.hxt.webpasser.transport.xml.Tasks;
import com.hxt.webpasser.utils.FileUtil;
import com.hxt.webpasser.utils.ResourceUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderTaskFactory {

	private static SpiderTaskFactory spiderTaskFactory=new SpiderTaskFactory();
	//public static Map<String,TaskInfo> taskInfoMap=new Hashtable<String, TaskInfo>();
	private  TaskInfos taskInfos;
	private  Tasks tasks;
	
	private SpiderTaskFactory(){
		
	}
	
	public static SpiderTaskFactory getInstance(){
		if(spiderTaskFactory.taskInfos==null)
		{
			spiderTaskFactory.init();
		}
		
		return spiderTaskFactory;
	}
	
	public synchronized  void init(){
		if(taskInfos==null)
		{
			taskInfos=new TaskInfos();
			String spiderTasksXmlPath=ResourceUtil.getResourcePath()+"spiderTasks.xml";
			String con=FileUtil.readTxtFile(spiderTasksXmlPath);
			tasks=XStreamWireFormat.unmarshalText(con,Tasks.class);
			if(tasks.getTaskItems()!=null)
			{
				 Vector<TaskInfo> taskis=new Vector<TaskInfo>();
				for(TaskItem taskItem:tasks.getTaskItems()){
					TaskInfo taskInfo=taskItem2TaskInfo( taskItem,true);
					taskis.add(taskInfo);
				}
				taskInfos.setTaskInfos(taskis);
			}
			
		}

	}
	
	public   List<TaskInfo> getTaskInfos(){
		
		return taskInfos.getTaskInfos();
	}
	
	
	/**
	 * 根据taskName获取TaskInfo
	 * @param taskName
	 * @return
	 */
	public  TaskInfo getTaskByName(String taskName)	{
	/*	if(taskInfos==null)
		{
			init();
			
		}*/
		for(TaskInfo taskInfo:taskInfos.getTaskInfos()){
			if(taskName.equals(taskInfo.getTaskName())){
				return taskInfo;
			}
		}
	
		return null;
	}
	
	public  TaskItem getTaskItemByName(String taskName)	{
	
		for(TaskItem taskItem:tasks.getTaskItems()){
			if(taskName.equals(taskItem.getTaskName())){
				return taskItem;
			}
		}
	
		return null;
	}
	
	
	public void addTask(String taskName,String taskXml) throws Exception{
		
		TaskItem taskItem=new TaskItem();
		taskItem.setTaskName(taskName);
		String taskPath=SpiderGlobalProperties.DEFAULT_TASK_XML_FOLDER+"/"+taskName+".xml";
		taskItem.setTaskPath(taskPath);
		TaskInfo sel=getTaskByName(taskName);
		if(sel==null){
			TaskInfo taskInfo=taskItem2TaskInfo( taskItem,false);
			
			if(tasks.getTaskItems()==null&&taskInfos.getTaskInfos()==null){
				tasks.setTaskItems(new Vector<TaskItem>());
				taskInfos.setTaskInfos(new Vector<TaskInfo>());
			}
			taskInfo.writeXmlContent(taskXml);
			tasks.getTaskItems().add(taskItem);
			taskInfos.getTaskInfos().add(taskInfo);
			writeTasksXml();
		}else{// 已存在此任务名
			throw new Exception("已存在此任务名！");
		}
		
	}
	
	
	public void editTask(String taskName,String taskXml) throws Exception{
		
	/*	TaskItem taskItem=new TaskItem();
		taskItem.setTaskName(taskName);
		String taskPath=SpiderGlobalProperties.DEFAULT_TASK_XML_FOLDER+"/"+taskName+".xml";
		taskItem.setTaskPath(taskPath);*/
		TaskInfo taskInfo=getTaskByName(taskName);
		TaskItem taskItem=getTaskItemByName(taskName);
		if(taskInfo!=null&&taskItem!=null){
			TaskInfo newTaskInfo=taskItem2TaskInfo( taskItem,false);
			if(taskInfo.getSpiderTask()!=null){
				Map<String,Object> info=taskInfo.getSpiderTask().monitorSpiderTaskInfo();
				Integer runState=(Integer) info.get("runState");
				if(runState==SpiderController.RUNING_STATE||runState==SpiderController.PAUSE_STATE){
					throw new Exception("当前任务是运行状态，不能修改！");
				}
			}
			taskInfo.writeXmlContent(taskXml);
			taskInfos.getTaskInfos().remove(taskInfo);
			taskInfos.getTaskInfos().add(newTaskInfo);
		}else{// 已存在此任务名
			throw new Exception("不存在此任务名！");
		}
		
	}
	
	public void addOrChangeScheduler(String taskName,Scheduler scheduler,boolean isAdd) throws Exception{
		TaskItem taskItem=getTaskItemByName(taskName);
		TaskInfo taskInfo=getTaskByName( taskName);
		if(taskItem!=null&&taskInfo!=null){
			if(taskItem.getScheduler()!=null){
				scheduler.setIsRun(taskItem.getScheduler().getIsRun());
			}
			SpiderScheduler spiderScheduler= schedulerXml2SpiderScheduler( scheduler,taskItem.getTaskName(),isAdd,true);
			taskInfo.setSpiderScheduler(spiderScheduler);
			taskItem.setScheduler(scheduler);
			writeTasksXml();
		}else{
			throw new Exception("任务不存在！");
		}
		
	}
	
	/**
	 * 开启定时
	 * @param taskName
	 * @throws Exception 
	 */
	public void startScheduler(String taskName) throws Exception{
		TaskItem taskItem=getTaskItemByName(taskName);
		TaskInfo taskInfo=getTaskByName( taskName);
		if(taskItem!=null&&taskInfo!=null&&taskItem.getScheduler()!=null&&taskInfo.getSpiderScheduler()!=null){
			if(taskInfo.getSpiderScheduler().getIsRun()==1){ 
				throw new Exception("此任务已开启定时！");
			}
			SpiderQuartzManager.addJob(taskName, taskInfo.getSpiderScheduler().getCronExpression());
			taskItem.getScheduler().setIsRun(1);
			taskInfo.getSpiderScheduler().setIsRun(1);
			writeTasksXml();
		}else{
			throw new Exception("任务不存在或未设置定时！");
		}
		
	}
	
	/**
	 * 关闭定时
	 * @param taskName
	 * @throws Exception
	 */
	public void stopScheduler(String taskName) throws Exception{
		TaskItem taskItem=getTaskItemByName(taskName);
		TaskInfo taskInfo=getTaskByName( taskName);
		if(taskItem!=null&&taskInfo!=null&&taskItem.getScheduler()!=null&&taskInfo.getSpiderScheduler()!=null){
			if(taskInfo.getSpiderScheduler().getIsRun()!=1){ 
				throw new Exception("此任务已关闭定时！");
			}
			SpiderQuartzManager.removeJob(taskName);
			taskItem.getScheduler().setIsRun(0);
			taskInfo.getSpiderScheduler().setIsRun(0);
			writeTasksXml();
		}else{
			throw new Exception("任务不存在或未设置定时！");
		}
		
	}
	
	
	private TaskInfo taskItem2TaskInfo(TaskItem taskItem,boolean isOpScheduler){
		if(taskItem!=null){
			TaskInfo taskInfo=new TaskInfo();
			taskInfo.setTaskName(taskItem.getTaskName());
			taskInfo.setTaskPath(taskItem.getTaskPath());
			if(taskItem.getScheduler()!=null){
			
				SpiderScheduler spiderScheduler= schedulerXml2SpiderScheduler( taskItem.getScheduler(),taskItem.getTaskName(),true,isOpScheduler);
				taskInfo.setSpiderScheduler(spiderScheduler);
			}
			return taskInfo;
		}
		return null;
	}
	
	private SpiderScheduler schedulerXml2SpiderScheduler(Scheduler scheduler,String taskName,boolean isAdd,boolean isOpScheduler){
		if(scheduler!=null){
			SpiderScheduler spiderScheduler=new SpiderScheduler();
			spiderScheduler.setCronExpression(scheduler.getCronExpression());
			if(scheduler.getTaskCircle()!=null){
				spiderScheduler.setTaskCircle(scheduler.getTaskCircle());
			}
			spiderScheduler.setTimeString(scheduler.getTimeString());
			if(scheduler.getIsRun()!=null){
				spiderScheduler.setIsRun(scheduler.getIsRun());
				if(1==spiderScheduler.getIsRun()&&isOpScheduler){  //进行启动定时
					try {
						// 加入定时
						if(isAdd){
							SpiderQuartzManager.addJob(taskName, spiderScheduler.getCronExpression());
						}else{
							SpiderQuartzManager.modifyJobTime(taskName, spiderScheduler.getCronExpression());
						}
					
					} catch (SchedulerException e) {
						e.printStackTrace();
					}
				}
				
			}
			return spiderScheduler;
		}
		return null;
	}
	
	/**
	 * 将tasks转成xml序列化到本地
	 * @throws Exception 
	 */
	private void writeTasksXml() throws Exception{
		if(tasks!=null&&tasks.getTaskItems()!=null){
			String spiderTasksXmlPath=ResourceUtil.getResourcePath()+"spiderTasks.xml";
			String tasksXml=XStreamWireFormat.marshalText(tasks);
			FileUtil.createFile(spiderTasksXmlPath, tasksXml,"utf-8");
		}
		
		
	}
	
	
	
}
