/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderTaskProxyFeedbackTrigger.java
 *   
 */
package com.hxt.webpasser.trigger.impl;

import java.util.Map;

import com.hxt.webpasser.fetcher.HttpClientWrapper;
import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.transport.xml.XmlTask;
import com.hxt.webpasser.trigger.SpiderTaskTrigger;

/**
 * 功能说明: 代理获取后反馈 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderTaskProxyFeedbackTrigger  implements SpiderTaskTrigger{


	private String taskName;  
	
	private String feedBackUrl;  
	
	public SpiderTaskProxyFeedbackTrigger(String taskName,XmlTask xmlTask){
		this.taskName=taskName;
		if(xmlTask.getFetchConfig().getProxy()!=null){
			this.feedBackUrl=xmlTask.getFetchConfig().getProxy().getFeedBackUrl();
		}
		
	}

	
	public void beforeDownload(CrawlUrl crawlUrl) {
		// TODO Auto-generated method stub
		
	}

	public void successDownloadAfter(CrawlUrl crawlUrl, PageResult pageResult) {
		// TODO Auto-generated method stub
		
	}

	public void tryOneFailDownload(CrawlUrl crawlUrl) {

		if(crawlUrl.getProxyIp()!=null){
		
			try {
				String errorFeedBackUrl=feedBackUrl+"?op=error&taskName="+taskName+"&ip="+crawlUrl.getProxyIp().getIp();
				HttpClientWrapper httpClientWrapper=new HttpClientWrapper();
				httpClientWrapper.get(errorFeedBackUrl);
				System.out.println("proxy error feedBack:"+errorFeedBackUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


}
