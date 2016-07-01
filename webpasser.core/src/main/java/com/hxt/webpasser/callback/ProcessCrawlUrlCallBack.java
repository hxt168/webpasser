/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ProcessCrawlUrlCallBack.java
 *   
 */
package com.hxt.webpasser.callback;

import java.util.Map;
import java.util.Set;

import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public interface ProcessCrawlUrlCallBack {

	public void handlePageResult(PageResult pageResult);
	
	/**
	 * 获得解析出来的链接，进行另外处理
	 */
	public void handleExtracLinks(Set<String> crawlUrls);
	
	/**
	 * 得到已经解析的数据，进行另外处理
	 */
	public void handleReturnMapValue(Map<String,Object> returnValue);
	
	
}
