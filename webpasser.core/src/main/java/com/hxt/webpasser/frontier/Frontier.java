/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: Frontier.java
 *   
 */
package com.hxt.webpasser.frontier;

import com.hxt.webpasser.module.CrawlUrl;


/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-9 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public interface Frontier {

	 public CrawlUrl getNext()throws Exception;  
     public void putUrlToPending(CrawlUrl crawlUrl) throws Exception;  
     
     public void visited(CrawlUrl crawlUrl) throws Exception;    //visited
	
     public boolean isPendingQueueEmpty();
     
   /*  public void finished(CrawlUrl url) throws Exception;   */
     public int getPendingCount();
     /**
      * 本任务本次抓取的数量
      * @return
      */
     public int getVisitedCount();
     /**
      * 本任务未清除之前整个抓取的数量
      * @return
      */
   //  public int getAllVisitedCount();    
     
     public void close();
}
