/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ErrorFrontier.java
 *   
 */
package com.hxt.webpasser.frontier;

import java.util.List;

import com.hxt.webpasser.module.ErrorCrawlUrlInfo;

/**
 * 功能说明: 存储发生错误的链接  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public interface ErrorUrlFrontier {

	public void putErrorCrawlUrlInfo(ErrorCrawlUrlInfo errorCrawlUrlInfo)throws Exception;  
	
	public List<ErrorCrawlUrlInfo> getAll();
	
	public void delete(String key) throws Exception;  
}
