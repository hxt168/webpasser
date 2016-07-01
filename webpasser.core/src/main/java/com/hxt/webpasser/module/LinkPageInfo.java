/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: LinkPageInfo.java
 *   
 */
package com.hxt.webpasser.module;

import java.util.Set;

/**
 * 功能说明: 关联其他page的信息 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class LinkPageInfo {

	private String linkPageName;
	
	private Set<CrawlUrl> digCrawlUrlSet;



	public Set<CrawlUrl> getDigCrawlUrlSet() {
		return digCrawlUrlSet;
	}

	public void setDigCrawlUrlSet(Set<CrawlUrl> digCrawlUrlSet) {
		this.digCrawlUrlSet = digCrawlUrlSet;
	}

	public String getLinkPageName() {
		return linkPageName;
	}

	public void setLinkPageName(String linkPageName) {
		this.linkPageName = linkPageName;
	}
	
	
	
	
}
