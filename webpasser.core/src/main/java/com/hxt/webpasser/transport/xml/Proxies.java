/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: Proxies.java
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
@XStreamAlias("proxies")
public class Proxies {
	@XStreamAsAttribute
	private String path;
	

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
