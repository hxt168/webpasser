/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderTaskInterceptor.java
 *   
 */
package com.hxt.webpasser.trigger;

import java.util.Map;

import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;

/**
 * 功能说明: 在抓取过程中的触发器 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public interface SpiderTaskTrigger {

	/**
	 * 准备抓取数据
	 * @param crawlUrl
	 */
	public void beforeDownload(CrawlUrl crawlUrl);
	
	/**
	 * 抓取数据成功
	 * @param crawlUrl
	 * @param pageResult
	 */
	public void successDownloadAfter(CrawlUrl crawlUrl,PageResult pageResult);
	/**
	 * 抓取数据一次失败，准备重试
	 * @param crawlUrl
	 */
	public void tryOneFailDownload(CrawlUrl crawlUrl);
	/**
	 * 重复抓取几次数据都失败后
	 * @param crawlUrl
	 */
	public void reTryAllFailDownload(CrawlUrl crawlUrl);
	
	
	/**
	 * 将抓取来的网页内容解析成map后
	 * @param returnValue
	 */
	public void afterParseToMap(Map<String,Object> returnValue);
	
	/**
	 * 最后进行存储后
	 * @param returnValue
	 */
	public void afterPersistent(Map<String,Object> returnValue);
	
	
}
