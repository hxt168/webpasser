/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: CrawlUrlUtil.java
 *   
 */
package com.hxt.webpasser.spider.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.transport.xml.LimitHost;
import com.hxt.webpasser.utils.StringUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class CrawlUrlUtil {

	/**
	 * 根据url获取CrawlUrl
	 * @param url
	 * @return
	 */
	public static CrawlUrl getCrawlUrlByUrl(String url)
	{
		CrawlUrl crawllink=new CrawlUrl();
		crawllink.setUrl(filterUrl(url));
		return crawllink;
	}
	
	/**
	 * 根据url list获取 Set<CrawlUrl>
	 * @param urlSet
	 * @return
	 */
	public static Set<CrawlUrl>  getCrawlUrlSetByUrls(List<String> urlSet,List<LimitHost> scopes)
	{
		Set<CrawlUrl> crawlUrlSet=new HashSet<CrawlUrl>();
		if(urlSet!=null)
		{
			for(String url:urlSet)
			{
				CrawlUrl crawllink=new CrawlUrl();
				crawllink.setUrl(filterUrl(url));
				crawlUrlSet.add(crawllink);
			}
		}
		return crawlUrlSet;
	}
	
	
	public static Set<CrawlUrl>  getCrawlUrlSetByUrlSet(Set<String> urlSet,List<LimitHost> scopes)
	{
		Set<CrawlUrl> crawlUrlSet=new HashSet<CrawlUrl>();
		if(urlSet!=null)
		{
			for(String url:urlSet)
			{
				url=checkUrl(url, scopes);
				if(url==null){
					continue;
				}
				CrawlUrl crawllink=new CrawlUrl();
				crawllink.setUrl(filterUrl(url));
				crawlUrlSet.add(crawllink);
			}
		}
		return crawlUrlSet;
	}
	
	
	public static String checkUrl(String url,List<LimitHost> scopes){
		url=url.trim();
		if(!url.startsWith("http")){
			return null;
		}
		String host=StringUtil.cutNotContainStartAndEnd(url, "//", "/");
		if(scopes!=null&&scopes.size()>0){
			boolean hasIn=false;
			for(LimitHost limitHost:scopes){
				if(host.indexOf(limitHost.getValue())>-1){
					hasIn=true;
				}
			}
			if(!hasIn){
				return null;
			}
		}
		return url;
	}
	
	/**
	 * 去掉 url中 #字符 后面的
	 * @param url
	 */
	public static String filterUrl(String url){
		if(url.indexOf("#")>-1){
			String[] arr=url.split("#");
			return arr[0];
		}
		return url;
	}
	
}
