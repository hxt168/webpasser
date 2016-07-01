/*
 * 系统名称: eden 1.0
 * 模块名称: hxt.spider.core
 * 类 名 称: UUIDGenerator.java
 * 软件版权: 浙江榕基信息技术有限公司
 *   
 */
package com.hxt.webpasser.utils;

import java.util.UUID;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-1-17 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class UUIDGenerator {

	 public static String getUUID() {
		  UUID uuid = UUID.randomUUID();
		  String str = uuid.toString();
		  // 去掉"-"符号
		  String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
		  //return str+","+temp;
		  return temp;
		 }
	
}
