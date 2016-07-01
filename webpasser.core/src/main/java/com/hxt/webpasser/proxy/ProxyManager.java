/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ProxyFactory.java
 *   
 */
package com.hxt.webpasser.proxy;


/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public interface ProxyManager {

	public ProxyIp getRandProxyIp();
	public void init();
	
	public void checkProxyIps();
}
