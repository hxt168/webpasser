/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ProxyManagerImpl.java
 *   
 */
package com.hxt.webpasser.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import com.hxt.webpasser.fetcher.HttpClientWrapper;
import com.hxt.webpasser.log.SpiderLogs;
import com.hxt.webpasser.utils.FileUtil;
import com.hxt.webpasser.utils.ResourceUtil;
import com.hxt.webpasser.utils.RunTimeCost;
import com.hxt.webpasser.utils.StringUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class ProxyManagerImpl implements ProxyManager{

	private static Log log=SpiderLogs.getLog(ProxyManagerImpl.class);
	private String path;
	Logger proxtLog;
	private String checkUrl="http://ip.qq.com/";
	
	public ProxyManagerImpl(String path){
		this.path=path;
		 proxtLog=SpiderLogs.getUrlLogByLayoutPattern("proxy", "can_proxy.log","%m%n");
		 
	}

	public  List<ProxyIp> proxyIps;
	
	
	public void init(){
		
		proxyIps=new ArrayList<ProxyIp>();
		String ipsCon=FileUtil.readTxtFile(ResourceUtil.getResourcePath()+path);
		if(!StringUtil.isEmpty(ipsCon)){
			String[] lines=ipsCon.split("\n");
			for(String line:lines){
				if(!StringUtil.isEmpty(ipsCon)){
					String[] ipArr=line.split(":");
					String ip=ipArr[0].trim();
					int port=Integer.parseInt(ipArr[1]);
					ProxyIp proxyIp=new ProxyIp();
					proxyIp.setIp(ip);
					proxyIp.setPort(port);
					proxyIps.add(proxyIp);
					
				}
			}
			
		}
		
	}
	
	
	public ProxyIp getRandProxyIp(){
		if(proxyIps==null||proxyIps.size()==0){
			return null;
		}
		
		Random random = new Random();
		int rand=Math.abs(random.nextInt())%proxyIps.size();
		System.out.println(rand);
		return proxyIps.get(rand);
	}
	


	public void checkProxyIps() {

		if(proxyIps!=null){
			for(ProxyIp proxyIp:proxyIps){
				RunTimeCost runTimeCost=new RunTimeCost();
				 HttpClientWrapper httpClientWrapper=new HttpClientWrapper();
				 httpClientWrapper.setProxyIp(proxyIp);
				 String con=null;
				try {
					con = httpClientWrapper.get(checkUrl);
					System.out.println(con);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				if(con!=null&&con.indexOf("html")>0){
					runTimeCost.endTime();
					log.info("proxy ip"+proxyIp.getIp()+" can use! use time:"+runTimeCost.getCostTimeMicrosecond()+"ms");
					proxtLog.info(proxyIp.getIp()+":"+proxyIp.getPort());
				}else{
					log.info("proxy ip"+proxyIp.getIp()+" can can not use!");
				}
				 
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		ProxyManager proxyFactory=new ProxyManagerImpl("ip.txt");
		proxyFactory.init();
		System.out.println(proxyFactory.getRandProxyIp().toString());
		proxyFactory.checkProxyIps();
	}
	
}
