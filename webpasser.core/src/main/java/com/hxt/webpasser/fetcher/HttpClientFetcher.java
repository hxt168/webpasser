/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: HttpClientFetcher.java
 *   
 */
package com.hxt.webpasser.fetcher;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.proxy.ProxyIp;
import com.hxt.webpasser.spider.SpiderController;
import com.hxt.webpasser.transport.xml.Cookie;
import com.hxt.webpasser.transport.xml.FetchConfig;
import com.hxt.webpasser.transport.xml.Header;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-14 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class HttpClientFetcher extends DownLoadFile {

	public HttpClientFetcher(FetchConfig fetchConfig,SpiderController spiderController) {
		super(fetchConfig,spiderController);
	}


	
	@Override
	public PageResult downloadFile(CrawlUrl crawlUrl) throws Exception {
		PageResult pageResult=new PageResult();
		pageResult.setUrl(crawlUrl.getUrl());
		pageResult.setUrlHash(crawlUrl.getUrlKeyHash());
		pageResult.setCharSet(crawlUrl.getCharSet());
		HttpClientWrapper httpClientWrapper=new HttpClientWrapper(fetchConfig);
		if(spiderController.getProxyManager()!=null){
			ProxyIp proxyIp=spiderController.getProxyManager().getRandProxyIp();
			 httpClientWrapper.setProxyIp(proxyIp);
			 crawlUrl.setProxyIp(proxyIp);
		 }
		String content=httpClientWrapper.get(crawlUrl.getUrl());
		pageResult.setContent(content);
		pageResult.setLinkParamValueMap(crawlUrl.getLinkParamValueMap());
		pageResult.setLinkPageName(crawlUrl.getLinkPageName());
		return pageResult;
	}  
	
/*	 public String get(String url) throws Exception { 
		 
		 HttpClientWrapper httpClientWrapper=new HttpClientWrapper(fetchConfig);
		 if(spiderController.getProxyManager()!=null){
			 httpClientWrapper.setProxyIp(spiderController.getProxyManager().getRandProxyIp());
		 }
		 return httpClientWrapper.get(url); 
	 }
	 */
	 

/*	public CloseableHttpClient getHttpClient()
	{
	    RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(timeOutSecond * 1000)
	            .setConnectTimeout(timeOutSecond * 1000).build();
	        // Create an HttpClient with the given custom dependencies and
	        // configuration.
	    
	    HttpClientBuilder httpClientBuilder = HttpClients.custom().setUserAgent(userAgent)//.setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE)
	            .setDefaultRequestConfig(defaultRequestConfig);
	    
	    //设置cookie
	    if(fetchConfig.getCookies()!=null&&fetchConfig.getCookies().size()>0)
	    {
		    BasicCookieStore cookieStore = new BasicCookieStore();
		    
		    for(Cookie xmlCookie:fetchConfig.getCookies())
		    {
		    	 BasicClientCookie cookie = new BasicClientCookie(xmlCookie.getName(),
		    			 xmlCookie.getValue());
		    		if(xmlCookie.getHost()!=null)
		    		{
		    		    cookie.setDomain(xmlCookie.getHost());
		    		}
		    		if(xmlCookie.getPath()!=null)
		    		{
		    		    cookie.setPath(xmlCookie.getPath());
		    		}
		    		cookieStore.addCookie(cookie);
		    	
		    }
		    httpClientBuilder.setDefaultCookieStore(cookieStore);
	    }
	    
	    CloseableHttpClient httpClient =httpClientBuilder.build();
		
		return httpClient;
	}
	  *//** 
     * 发送 get请求 
	 * @throws IOException 
     *//*  
    public String get(String url) throws Exception {  
           CloseableHttpClient httpClient = getHttpClient();  
         
            HttpGet httpGet = new HttpGet(url);
            if(fetchConfig.getHeaders()!=null)
            {
            	for(Header header:fetchConfig.getHeaders())
            	{
                    httpGet.setHeader(header.getName(), header.getValue());
            	}
            }
    

            return  httpClient.execute(httpGet,responseHandler);
     
            
    }
    

	// 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	private  ResponseHandler responseHandler = new ResponseHandler() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response)	throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String entityCharset=EntityUtils.getContentCharSet(entity);
				entityCharset = entityCharset == null ? charset : entityCharset;
				return new String(EntityUtils.toByteArray(entity), entityCharset);
			} else {
				return null;
			}
		}
	};*/
	
	
}
