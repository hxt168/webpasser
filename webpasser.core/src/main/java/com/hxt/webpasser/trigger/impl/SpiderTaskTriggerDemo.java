/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderTaskTriggerDemo.java
 *   
 */
package com.hxt.webpasser.trigger.impl;

import java.util.Map;

import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.trigger.SpiderTaskTrigger;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderTaskTriggerDemo implements SpiderTaskTrigger{

	private String taskName;
	
	public SpiderTaskTriggerDemo(String taskName){
		this.taskName=taskName;
	}
	
	public void beforeDownload(CrawlUrl crawlUrl) {

		
		System.out.println("taskName:"+taskName+" beforeDownload url:"+crawlUrl.getUrl());
	}

	public void successDownloadAfter(CrawlUrl crawlUrl, PageResult pageResult) {
		// TODO Auto-generated method stub
		
	}

	public void tryOneFailDownload(CrawlUrl crawlUrl) {
		// TODO Auto-generated method stub
		
	}

	public void reTryAllFailDownload(CrawlUrl crawlUrl) {
		// TODO Auto-generated method stub
		
	}

	public void afterParseToMap(Map<String, Object> returnValue) {
		// TODO Auto-generated method stub
		
	}

	public void afterPersistent(Map<String, Object> returnValue) {
		// TODO Auto-generated method stub
		
	}

}
