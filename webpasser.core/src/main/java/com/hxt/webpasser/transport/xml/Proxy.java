/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: Proxy.java
 *   
 */
package com.hxt.webpasser.transport.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
@XStreamAlias("proxy")
public class Proxy {
	@XStreamAsAttribute
	private String pollUrl;
	
	/**
	 * 需与SpiderTaskProxyFeedbackTrigger并用
	 */
	@XStreamAsAttribute
	private String feedBackUrl;

	public String getPollUrl() {
		return pollUrl;
	}

	public void setPollUrl(String pollUrl) {
		this.pollUrl = pollUrl;
	}

	public String getFeedBackUrl() {
		return feedBackUrl;
	}

	public void setFeedBackUrl(String feedBackUrl) {
		this.feedBackUrl = feedBackUrl;
	}
	
}
