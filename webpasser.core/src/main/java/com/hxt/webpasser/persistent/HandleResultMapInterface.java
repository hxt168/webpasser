/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: HandleResultMapInterface.java
 *   
 */
package com.hxt.webpasser.persistent;

import java.util.Map;

/**
 * 功能说明: 解析后的数据持久化接口 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public interface HandleResultMapInterface {

	
	public void handle(Map<String,Object> resultMap);
	
	public void close();
	
}
