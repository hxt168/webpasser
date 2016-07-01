/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: DefaultPersistentPageResult.java
 *   
 */
package com.hxt.webpasser.persistent;

import com.hxt.webpasser.callback.ProcessCrawlUrlCallBack;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.utils.FileUtil;

/**
 * 功能说明:  <br>
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
public class DiskPersistentPageResult implements PersistentPageResult{

	public String rootDir="H:\\temp\\pachong\\";
	
	public void persistent(PageResult pageResult,ProcessCrawlUrlCallBack processCrawlUrlCallBack) {
		String url=pageResult.getUrl();
		String fileName=getFileName(url);
		String absoluteFilename=rootDir+fileName;
		try {
			FileUtil.createFile(absoluteFilename, pageResult.getContent(), pageResult.getCharSet());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public String getFileName(String url)
	{
		url = url.substring(7);
		  // text/html类型
		//  if (contentType.indexOf("html") != -1) {
		   url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
		   return url;
		 // }
	}



	

}
