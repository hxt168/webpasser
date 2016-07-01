/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ProxyRemotePollManagerImpl.java
 *   
 */
package com.hxt.webpasser.proxy;

import com.hxt.webpasser.fetcher.HttpClientWrapper;
import com.hxt.webpasser.utils.StringUtil;

/**
 * 功能说明: 单独从代理中心获取 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class ProxyRemotePollManagerImpl implements ProxyManager{

	/**
	 * 获取单个代理信息的url
	 */
	private String remoteUrl;
	
	
	public ProxyRemotePollManagerImpl(String remoteUrl){
		
		this.remoteUrl=remoteUrl;
	}
	
	public ProxyIp getRandProxyIp() {
		if(remoteUrl!=null){
			HttpClientWrapper httpClientWrapper=new HttpClientWrapper();
			try {
				String con=httpClientWrapper.get(remoteUrl);
				if(!StringUtil.isEmpty(con)){
					String[] ipArr=con.trim().split(":");
					ProxyIp proxyIp=new ProxyIp();
					proxyIp.setIp(ipArr[0]);
					proxyIp.setPort(Integer.parseInt(ipArr[1]));
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void checkProxyIps() {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
