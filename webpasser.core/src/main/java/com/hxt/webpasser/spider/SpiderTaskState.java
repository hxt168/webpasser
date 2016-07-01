/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderTaskState.java
 *   
 */
package com.hxt.webpasser.spider;

import org.apache.log4j.Logger;

import com.hxt.webpasser.log.SpiderLogs;
import com.hxt.webpasser.utils.MyDateUtil;

/**
 * 功能说明: 任务状态信息 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderTaskState {

	private String taskName;
	
	private long startTime;  //开始时间
	
	private long endTime;  
	
	private long downloadSize;  //总下载量   单位： B
	
	private int errorCount;    //错误页数
	
	private int successCount;   //成功页数
	
	private Logger successCountPageLogger;
	private String successCountPageLoggerFilePath;
	
	private Logger errorPageLogger;
	private String errorPageLoggerFilePath;
	
	public SpiderTaskState(String taskName){
		this.taskName=taskName;
		start();
	
	}
	
	public void start(){
		startTime=System.currentTimeMillis();
		endTime=0;
		
		String rePath="log/"+taskName+"/"+MyDateUtil.getCurrentTime("yyyyMMdd/HHmmss_");
		errorPageLoggerFilePath=rePath+"error.log";
		if(errorPageLogger!=null){
			errorPageLogger.removeAllAppenders();
		}
		errorPageLogger=SpiderLogs.getUrlLog(taskName+"error"+startTime,errorPageLoggerFilePath);
		
		successCountPageLoggerFilePath=rePath+"success.log";
		if(successCountPageLogger!=null){
			successCountPageLogger.removeAllAppenders();
		}
		successCountPageLogger=SpiderLogs.getUrlLog(taskName+"success"+startTime,successCountPageLoggerFilePath);
		downloadSize=0;
		errorCount=0;
		successCount=0;
	}
	
	public void end(){
		endTime=System.currentTimeMillis();
		
	}
	
	public void closeLoger(){
		successCountPageLogger.removeAllAppenders();
		errorPageLogger.removeAllAppenders();
		
	}
	
	/**
	 * 得到用时
	 * @return
	 */
	public long getCostTime(){
		if(startTime==0){
			return 0;
		}
		if(endTime==0){
			return System.currentTimeMillis()-startTime;
		}
		return endTime-startTime;
	}
	
	public void addDownLoadSize(long inc){	
		downloadSize+=inc;
	}
	
	
	public void incErrorCount(){	
		errorCount++;
	}
	
	public void incSuccessCount(){	
		successCount++;
	}
	
	public void putErrorUrlInfo(String url,String urlHash,String errorInfo){
		errorPageLogger.info(url+" "+errorInfo);
		incErrorCount();
	}
	
	public void putErrorUrlInfo(String info){
		errorPageLogger.info(info);
		incErrorCount();
	}
	
	public void putSuccessUrlInfo(String url){
		successCountPageLogger.info(url);
		incSuccessCount();
	}
	
	public String getErrorUrlLogFIlePath(){
		
		return errorPageLoggerFilePath;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public long getDownloadSize() {
		return downloadSize;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public String getSuccessCountPageLoggerFilePath() {
		return successCountPageLoggerFilePath;
	}
	
	
	
}
