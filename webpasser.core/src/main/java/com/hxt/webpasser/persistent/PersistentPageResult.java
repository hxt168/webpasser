/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: PersistentPageResult.java
 *   
 */
package com.hxt.webpasser.persistent;

import com.hxt.webpasser.callback.ProcessCrawlUrlCallBack;
import com.hxt.webpasser.module.PageResult;

/**
 * 功能说明: 持久化网页数据 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-29 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public interface PersistentPageResult {

	public void persistent(PageResult pageResult,ProcessCrawlUrlCallBack processCrawlUrlCallBack) ; //对数据进行持久化
	
}
