/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ErrorCrawlUrlInfo.java
 *   
 */
package com.hxt.webpasser.module;

import java.io.Serializable;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class ErrorCrawlUrlInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String url;
	
	private String urlHash;
	
	private String errorInfo;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlHash() {
		return urlHash;
	}

	public void setUrlHash(String urlHash) {
		this.urlHash = urlHash;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	
	
}
