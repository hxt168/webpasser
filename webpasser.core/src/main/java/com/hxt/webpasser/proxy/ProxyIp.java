/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ProxyIp.java
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
public class ProxyIp {

	
	private String ip;
	private int port;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "ProxyIp [ip=" + ip + ", port=" + port + "]";
	}
	
	
	
}
