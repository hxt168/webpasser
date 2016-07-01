/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderLogs.java
 *   
 */
package com.hxt.webpasser.log;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;



/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-14 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderLogs {

	public static Log getLog(Class classes)
	{
		
		return LogFactory.getLog(classes);
		
	}
	
	/**
	 * 
	 * @param name
	 * @param logFilePath
	 * @return
	 */
	public static Logger getUrlLogByLayoutPattern(String name,String logFilePath,String layoutPattern){
		
	
		Logger loger = Logger.getLogger(name); 
		Layout layout = new PatternLayout(layoutPattern);  
		
		Appender appender1;
		try {
			appender1 = new FileAppender(layout, logFilePath);
			
			loger.addAppender(appender1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return loger;
	}
	
	/**
	 * 
	 * @param name
	 * @param logFilePath
	 * @return
	 */
	public static Logger getUrlLog(String name,String logFilePath){
		
	
		/*Logger loger = Logger.getLogger(name); 
		Layout layout = new PatternLayout("[%d{yyyy-MM-dd hh:mm:ss}] [%t] - %m%n");  
		
		Appender appender1;
		try {
			appender1 = new FileAppender(layout, logFilePath);
			
			loger.addAppender(appender1);
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		return getUrlLogByLayoutPattern(name, logFilePath, "[%d{yyyy-MM-dd HH:mm:ss}] [%t] - %m%n");
	}
	
	/* 
	   '.'yyyy-MM    每月
	   '.'yyyy-ww    每周  
	   '.'yyyy-MM-dd    每天 
	   '.'yyyy-MM-dd-a   每天两次 
	   '.'yyyy-MM-dd-HH       每小时 
	   '.'yyyy-MM-dd-HH-mm     每分钟
	
	*/
	/**
	 * 
	 * 
	 * @param name
	 * @param logFilePath
	 * @param datePattern
	 * @return
	 */
	public static Logger getDailyRollingLog(String name,String logFilePath, String datePattern){
		
		
		Logger loger = Logger.getLogger(name); 
		Layout layout = new PatternLayout("[%d{yyyy-MM-dd HH:mm:ss}] [%t] - %m%n");  
		
		Appender appender1;
		try {
			appender1 = new DailyRollingFileAppender(layout, logFilePath,datePattern);
			loger.addAppender(appender1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return loger;
	}
	
	public static Logger getRollingFileLog(String name,String logFilePath){
		
		return getRollingFileLog(name, logFilePath, null, 0);
	}
	
	/**
	 * 
	 * @param name
	 * @param logFilePath
	 * @param maxFileSize   100KB   后缀可以是KB, MB 或者是 GB
	 * @param maxBackupIndex  z最大文件个数
	 * @return
	 */
	public static Logger getRollingFileLog(String name,String logFilePath, String maxFileSize,int maxBackupIndex){
		
		Logger loger = Logger.getLogger(name); 
		Layout layout = new PatternLayout("[%d{yyyy-MM-dd HH:mm:ss}] [%t] - %m%n");  
		
		RollingFileAppender appender1;
		try {
			appender1 = new RollingFileAppender(layout, logFilePath);
			if(maxFileSize!=null){
				appender1.setMaxFileSize(maxFileSize);
			}
			if(maxBackupIndex!=0){
				appender1.setMaxBackupIndex(maxBackupIndex);
			}
			loger.addAppender(appender1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return loger;
	}
	
	
	public static void main(String[] args) {
		
		Logger lo=getUrlLog("wwww","xxx.log");
		lo.info("33333333");
		lo.info("4444");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lo.removeAllAppenders();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	
}
