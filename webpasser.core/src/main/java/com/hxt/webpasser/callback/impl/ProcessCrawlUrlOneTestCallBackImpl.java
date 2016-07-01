/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ProcessCrawlUrlOneTestCallBack.java
 *   
 */
package com.hxt.webpasser.callback.impl;

import java.util.Map;
import java.util.Set;

import com.hxt.webpasser.callback.ProcessCrawlUrlCallBack;
import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;

/**
 * 功能说明: 进行一次抓取测试的回调类 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class ProcessCrawlUrlOneTestCallBackImpl implements ProcessCrawlUrlCallBack{

	private Set<String> parseredLinks;
	
	private Map<String, Object>  parseredValueMap;
	
	public void handlePageResult(PageResult pageResult) {
		// TODO Auto-generated method stub
		
	}
	
	public void handleExtracLinks(Set<String> crawlLinks) {

		setParseredLinks(crawlLinks);
	}

	public void handleReturnMapValue(Map<String, Object> returnValue) {

		setParseredValueMap(returnValue);
	}

	

	public Map<String, Object> getParseredValueMap() {
		return parseredValueMap;
	}

	public void setParseredValueMap(Map<String, Object> parseredValueMap) {
		this.parseredValueMap = parseredValueMap;
	}

	public Set<String> getParseredLinks() {
		return parseredLinks;
	}

	public void setParseredLinks(Set<String> parseredLinks) {
		this.parseredLinks = parseredLinks;
	}



}
