/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: HtmlParserTool.java
 *   
 */
package com.hxt.webpasser.parser;

import java.util.Set;

import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.parser.filter.LinkFilter;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-11 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public  interface HtmlParser {

	// 获取一个网站上的链接,filter 用来过滤链接
	public  Set<String> extracLinks(PageResult pageResult, LinkFilter filter) ;
	
	
	
	
}
