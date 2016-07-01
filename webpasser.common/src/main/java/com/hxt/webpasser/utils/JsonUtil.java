/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: JsonUtil.java
 *   
 */
package com.hxt.webpasser.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-9-14 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class JsonUtil {

	
	public static String toJSONString(Object object)
	{
		return JSON.toJSONString(object,true);
	}
	
	/**
	 *  用Gson转换     缺点：  int类型会变为double
	 * @param content
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObjectGson(String content,Class<T> clazz)
	{
		 Gson gson = new Gson();
		 return gson.fromJson(content, clazz);
		//return JSON.parseObject(content, clazz);
	}
	
	/**
	 *  用Fastjson转换，缺点：列表会无顺序
	 * @param content
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObjectFastjson(String content,Class<T> clazz)
	{
		 return JSON.parseObject(content, clazz);
	}
	
	
	
	
}
