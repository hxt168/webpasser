/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: HandleResultServcieDemo.java
 *   
 */
package com.hxt.webpasser.persistent.impl;

import java.util.Map;

import com.hxt.webpasser.persistent.HandleResultMapInterface;
import com.hxt.webpasser.utils.JsonUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class HandleResultServcieDemo implements HandleResultMapInterface{

	private String taskName="";
	
	public HandleResultServcieDemo(String taskName){
		this.taskName= taskName;
	}
	
	public void handle(Map<String, Object> resultMap) {

		
		System.out.println(taskName+"抓取解析后数据："+JsonUtil.toJSONString(resultMap));
		
		
	}

	public void close() {
		
	}

}
