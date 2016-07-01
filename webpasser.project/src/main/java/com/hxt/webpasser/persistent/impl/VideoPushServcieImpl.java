/*
 * 系统名称: 
 * 模块名称: webpasser.project
 * 类 名 称: VideoPushServcieImpl.java
 *   
 */
package com.hxt.webpasser.persistent.impl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;


import com.hxt.webpasser.fetcher.HttpClientWrapper;
import com.hxt.webpasser.log.SpiderLogs;
import com.hxt.webpasser.persistent.HandleResultMapInterface;
import com.hxt.webpasser.utils.JsonUtil;
import com.hxt.webpasser.utils.ResourceUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class VideoPushServcieImpl implements HandleResultMapInterface{

	private String pushUrl;
	private HttpClientWrapper httpClientWrapper;
	private Logger logger ;
	
	public VideoPushServcieImpl(String taskName){
		
		URL path = VideoPushServcieImpl.class.getClassLoader().getResource(
				"config.properties");
		Properties properties = new Properties();
		try {
			//pro.load(SqliteBaseDAO.class.getResourceAsStream("/db.properties"));
			properties.load(ResourceUtil.getResourceStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		pushUrl=properties.getProperty("pushUrl");
		httpClientWrapper=new HttpClientWrapper();
		httpClientWrapper.setCharset("utf-8");
		//httpClientWrapper.addHeader("Content-Type","text/json; charset=UTF-8");
		
		String logPath="log/"+taskName+"/";
		logger=SpiderLogs.getUrlLog(taskName+"_push",logPath+"_push_error.log");
	}
	
	public void handle(Map<String, Object> resultMap) {

		Map<String, String> paramMap=new HashMap<String, String>();
		String json=JsonUtil.toJSONString(resultMap);
		paramMap.put("json", json);
		boolean isFail=true;
		try {
			String con=httpClientWrapper.post(pushUrl, paramMap);
			if(con.indexOf("success")>-1){
				isFail=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			isFail=true;
		}
		if(isFail){
			logger.error(resultMap.get("fetchUrl")+" push error!");
		}
		
	}

	public void close() {

		logger.removeAllAppenders();
	}

}
