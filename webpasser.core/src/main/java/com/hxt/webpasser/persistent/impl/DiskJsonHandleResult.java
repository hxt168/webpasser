/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: DiskJsonHandleResult.java
 *   
 */
package com.hxt.webpasser.persistent.impl;

import java.io.File;
import java.util.Map;

import com.hxt.webpasser.persistent.HandleResultMapInterface;
import com.hxt.webpasser.spider.common.Constant;
import com.hxt.webpasser.utils.FileUtil;
import com.hxt.webpasser.utils.JsonUtil;
import com.hxt.webpasser.utils.ResourceUtil;

/**
 * 功能说明: 解析好的json数据保存到硬盘 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class DiskJsonHandleResult implements HandleResultMapInterface{

	public String rootDir=null;
	
	public String charSet="gbk";
	
	public DiskJsonHandleResult(){
		
	}
	
	public DiskJsonHandleResult(String rootDir,String charSet){
		
		this.rootDir=rootDir;
		this.charSet=charSet;
	}
	
	public void handle(Map<String, Object> resultMap) {

		if(resultMap!=null){
			String url=(String) resultMap.get(Constant.JSON_MAP_FETCH_URL_KEY);
			String fileName=getFileName(url);
			if(rootDir==null){
				rootDir=ResourceUtil.getResourcePath();
			}
			String absoluteFilename=rootDir+File.separator+fileName;
			try {
				FileUtil.createFile(absoluteFilename,JsonUtil.toJSONString(resultMap), charSet);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	
	public String getFileName(String url)
	{
		url = url.substring(7);
		  // text/html类型
		//  if (contentType.indexOf("html") != -1) {
		   url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".json";
		   return url;
		 // }
	}
	
}
