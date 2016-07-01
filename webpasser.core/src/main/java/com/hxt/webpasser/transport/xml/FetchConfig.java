/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: FetchConfig.java
 *   
 */
package com.hxt.webpasser.transport.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-9-17 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class FetchConfig {
	@XStreamAsAttribute
	private String charset;
	@XStreamAsAttribute
	private String timeOutSecond;
	@XStreamAsAttribute
	private String errorRetry;
	@XStreamAsAttribute	
	private String errorDelayTime;
	@XStreamAsAttribute
	private String runThreadNum;
	
	@XStreamAsAttribute
	private String fetchPrepareDelayTime;
	
	private String userAgent;
	
	private List<Header> headers;
	
	private List<Cookie> cookies;
	
	private Proxies proxies;
	/**
	 * 有proxy时proxies失效
	 */
	private Proxy proxy;

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getTimeOutSecond() {
		return timeOutSecond;
	}

	public void setTimeOutSecond(String timeOutSecond) {
		this.timeOutSecond = timeOutSecond;
	}

	public String getErrorRetry() {
		return errorRetry;
	}

	public void setErrorRetry(String errorRetry) {
		this.errorRetry = errorRetry;
	}


	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public List<Header> getHeaders() {
		return headers;
	}

	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	public String getErrorDelayTime() {
		return errorDelayTime;
	}

	public void setErrorDelayTime(String errorDelayTime) {
		this.errorDelayTime = errorDelayTime;
	}

	public String getFetchPrepareDelayTime() {
		return fetchPrepareDelayTime;
	}

	public void setFetchPrepareDelayTime(String fetchPrepareDelayTime) {
		this.fetchPrepareDelayTime = fetchPrepareDelayTime;
	}

	public Proxies getProxies() {
		return proxies;
	}

	public void setProxies(Proxies proxies) {
		this.proxies = proxies;
	}

	public String getRunThreadNum() {
		return runThreadNum;
	}

	public void setRunThreadNum(String runThreadNum) {
		this.runThreadNum = runThreadNum;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	
	
	
	
}
