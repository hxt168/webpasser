/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: HttpClientWrapper.java
 *   
 */
package com.hxt.webpasser.fetcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hxt.webpasser.proxy.ProxyIp;
import com.hxt.webpasser.transport.xml.Cookie;
import com.hxt.webpasser.transport.xml.FetchConfig;
import com.hxt.webpasser.transport.xml.Header;
import com.hxt.webpasser.utils.PropertyValueUtil;
import com.hxt.webpasser.utils.StringUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class HttpClientWrapper {

	
	 private String charset = "GBK";
		
	 private String userAgent = "Mozilla/5.0 Firefox/26.0";
		
	// protected String referer="http://www.soso.com";
		
	protected int timeOutSecond=10;  //超时时间
	  
	private List<Header> headers;
	
	private List<Cookie> cookies;
	
	private ProxyIp proxyIp;
	
	
	public HttpClientWrapper(){
		
		
	}
	
	public HttpClientWrapper(FetchConfig fetchConfig){
		if(fetchConfig!=null){
			charset= PropertyValueUtil.getPropertyStringValue(fetchConfig.getCharset(), charset);
			userAgent= PropertyValueUtil.getPropertyStringValue(fetchConfig.getUserAgent(), userAgent);
			timeOutSecond=PropertyValueUtil.getPropertyIntegerValue(fetchConfig.getTimeOutSecond(), timeOutSecond);
			headers=fetchConfig.getHeaders();
			cookies=fetchConfig.getCookies();
			
		}
		
		
	}
	
	
	
	
	public CloseableHttpClient getHttpClient()
	{
		 Builder  builder =RequestConfig.custom().setSocketTimeout(timeOutSecond * 1000)
        .setConnectTimeout(timeOutSecond * 1000);
		 if(proxyIp!=null){
			 HttpHost proxyHost=new HttpHost(proxyIp.getIp(), proxyIp.getPort());
			 builder.setProxy(proxyHost);
		 }
	    RequestConfig defaultRequestConfig = builder.build();
	        // Create an HttpClient with the given custom dependencies and
	        // configuration.
	    
	    HttpClientBuilder httpClientBuilder = HttpClients.custom().setUserAgent(userAgent)//.setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE)
	            .setDefaultRequestConfig(defaultRequestConfig);
	    
	    //设置cookie
	    if(cookies!=null&&cookies.size()>0)
	    {
		    BasicCookieStore cookieStore = new BasicCookieStore();
		    
		    for(Cookie xmlCookie:cookies)
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
	  /** 
     * 发送 get请求 
	 * @throws IOException 
     */  
    public String get(String url) throws Exception {  
           CloseableHttpClient httpClient = getHttpClient();  
         
            HttpGet httpGet = new HttpGet(url);
            if(headers!=null)
            {
            	for(Header header:headers)
            	{
                    httpGet.setHeader(header.getName(), header.getValue());
            	}
            }
    
  /*      	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOutSecond *1000).setConnectTimeout(timeOutSecond*1000).build();//设置请求和传输超时时间
    		httpGet.setConfig(requestConfig);*/
            return  httpClient.execute(httpGet,getResponseHandler(charset));
           /* CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();

                if (response.getStatusLine().getStatusCode() >= 400) {
                  throw new IOException("Got bad response, error code = " + response.getStatusLine().getStatusCode()
                      + " Url: " + url);
                }
                if (entity != null) {
                  InputStream input = entity.getContent();

                  BufferedReader br = new BufferedReader(new InputStreamReader(input));
                  String line = null;
                  StringBuilder sb = new StringBuilder();
                  while((line = br.readLine())!=null){
                      sb.append(line);
                      sb.append(line+"\n");
                  }
                  return sb.toString();
                }
              } finally {
                response.close();

         	}*/
            
    }
    
	  /** 
     * 发送 get请求 
	 * @throws IOException 
     */  
    @SuppressWarnings("unchecked")
	public String post(String url,Map<String,String> paramMap) throws Exception {  
           CloseableHttpClient httpClient = getHttpClient();  
         
           HttpPost httppost = new HttpPost(url);
           List<NameValuePair> paramsPair = new ArrayList<NameValuePair>();
           for(Entry<String,String> entry: paramMap.entrySet()){
        	   paramsPair.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
           }
           httppost.setEntity(new UrlEncodedFormEntity(paramsPair,charset));
    
            if(headers!=null)
            {
            	for(Header header:headers)
            	{
            		httppost.setHeader(header.getName(), header.getValue());
            	}
            }

            return httpClient.execute(httppost,getResponseHandler(charset));
    }
    
	
	// 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	public static ResponseHandler getResponseHandler(final String charset){
		 ResponseHandler responseHandler = new ResponseHandler() {
				// 自定义响应处理
				public String handleResponse(HttpResponse response)	throws ClientProtocolException, IOException {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						String entityCharset=EntityUtils.getContentCharSet(entity);
						byte[] bytes=EntityUtils.toByteArray(entity);
						entityCharset = entityCharset == null ? charset : entityCharset;
						if("auto".equals(charset)){
							entityCharset=getCharSetByBody(new String(bytes));
							if(entityCharset==null){
								entityCharset="GBK";
							}
						}
						return new String(bytes, entityCharset);
					} else {
						return null;
					}
				}
			};
		return responseHandler;
	}
	
	
	
	/**
	 * 根据页面body获取字符编码
	 * @param html
	 * @param charset
	 * @return
	 */
	private static String getCharSetByBody(String html){
	/*	Document document = parseJSoupDocumentFromHtml(html, Constants.parseBaseUri);
		Elements elements = document.select("meta");
		for(Element metaElement : elements){
			if(metaElement!=null && StringUtil.isNotBlank(metaElement.attr("http-equiv")) && metaElement.attr("http-equiv").toLowerCase().equals("content-type")){
				String content = metaElement.attr("content");
				charset = getCharSet(content);
				break;
			}
		}*/
		String charset=null;
		String headStr=StringUtil.cutNotContainStartAndEnd(html, "<head>", "</head>");
		if(!StringUtil.isEmpty(headStr)){  // html页面
			 charset=getCharSet("charset",headStr);
		}else{
			
			String xmlHead=StringUtil.cutNotContainStartAndEnd(html, "<?xml", "?>");
			if(!StringUtil.isEmpty(xmlHead)){ // xml
				charset=getCharSet("encoding",xmlHead);
			}
		}

		return charset;
	}
	
	/**
	 * 正则获取字符编码
	 * @param content
	 * @return
	 */
	private static String getCharSet(String att,String content){
		String regex = ".*"+att+"=\"*([^\"]*).*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		if(matcher.find())
			return matcher.group(1);
		else
			return null;
	}
	

	public ProxyIp getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(ProxyIp proxyIp) {
		this.proxyIp = proxyIp;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public int getTimeOutSecond() {
		return timeOutSecond;
	}

	public void setTimeOutSecond(int timeOutSecond) {
		this.timeOutSecond = timeOutSecond;
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
	
	public void addCookie(String name,String value,String host,String path){
		if(cookies==null){
			cookies=new ArrayList<Cookie>();
			
		}
		Cookie cookie=new Cookie();
		cookie.setName(name);
		cookie.setValue(value);
		cookie.setHost(host);
		cookie.setPath(path);
		cookies.add(cookie);
	}

	public void addHeader(String name,String value){
		if(headers==null){
			headers=new ArrayList<Header>();
			
		}
		Header header=new Header();
		header.setName(name);
		header.setValue(value);
		headers.add(header);
	}
	
	
}
