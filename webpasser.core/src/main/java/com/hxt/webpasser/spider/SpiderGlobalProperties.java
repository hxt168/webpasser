/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderPr.java
 *   
 */
package com.hxt.webpasser.spider;
/**
 * 功能说明: 常量 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderGlobalProperties {

	
	/**
	 * 任务状态为 初始化
	 */
	public static final Integer TASK_INIT_STATE=0;   
	/**
	 * 任务状态为 运行中
	 */
	public static final Integer TASK_RUN_STATE=1;   
	/**
	 * 任务状态为 停止
	 */
	public static final Integer TASK_STOP_STATE=2;
	/**
	 * 任务状态为 暂停
	 */
	public static final Integer TASK_PAUSE_STATE=3; 
	
	/**
	 * 默认单个任务的xml配置的前一个文件夹名
	 */
	public static final String DEFAULT_TASK_XML_FOLDER="catch";
	
	
}
