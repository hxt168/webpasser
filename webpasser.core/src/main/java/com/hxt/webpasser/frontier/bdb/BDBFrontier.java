/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: BDBFrontier.java
 *   
 */
package com.hxt.webpasser.frontier.bdb;

import java.io.File;
import java.io.Serializable;

import com.hxt.webpasser.exception.SpiderException;
import com.hxt.webpasser.frontier.AbstractFrontier;
import com.hxt.webpasser.frontier.Frontier;
import com.hxt.webpasser.frontier.VisitedIntState;
import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.utils.FileUtil;
import com.sleepycat.je.Database;

/**
 * 功能说明: 待抓取队列 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-10 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
@SuppressWarnings("serial")
public class BDBFrontier extends AbstractFrontier  implements
Serializable,Frontier{
	
	private String dirPath;
	
	private boolean isClose=false;
	BDBFactory bdbFactory;
	
	BdbPendingQueue<CrawlUrl> bdbPendingQueue;
	
	BdbVisitedQueue bdbVisitedQueue;  //保存着 待抓取的 和已抓取的url
	
	public BDBFrontier(String dbName)
	{
		String path=BDBFrontier.class.getResource("/").getPath();
		dirPath=path+File.separator+dbName;
		clear(); //先清理
		
		bdbFactory=new BDBFactory(dirPath);
		Database bdbPendingDb=bdbFactory.createAndBindDatabase(dbName+"_pending");
		bdbPendingQueue=new BdbPendingQueue<CrawlUrl>(bdbPendingDb, CrawlUrl.class,bdbFactory.getClassCatalog());
		
		Database bdbVisitedDb=bdbFactory.createAndBindDatabase(dbName+"_visited");
		bdbVisitedQueue=new BdbVisitedQueue(bdbVisitedDb);
		
	}

	public CrawlUrl getNext() throws Exception {

		return bdbPendingQueue.poll();
	
	}

	/**
	 * 判断是否在Pending或visited
	 */
	public synchronized void putUrlToPending(CrawlUrl crawlUrl) throws Exception {

		String key=crawlUrl.getUrlKeyHash();
		Integer visitState=bdbVisitedQueue.get(key);
		if(isCanPending(visitState)) //可以添加
		{
			
			bdbPendingQueue.offer(crawlUrl);
			bdbVisitedQueue.put(key, VisitedIntState.PENDING_STATE);
			
		}
		
	}

	
	
	public void visited(CrawlUrl crawlUrl) throws Exception{

		String key=crawlUrl.getUrlKeyHash();
		if(key!=null)
		{
			bdbVisitedQueue.put(key, VisitedIntState.VISITED_STATE);
		}else{
			throw new SpiderException(crawlUrl.getUrl()+" visited错误！");
		}
	
		
		
	}

	public boolean isPendingQueueEmpty() {
		return bdbPendingQueue.isEmpty();
	}

	public int getPendingCount() {
		return bdbPendingQueue.size();
	}
	/**
	 * 得到本次抓取的页数
	 */
	public int getVisitedCount() {
		return bdbVisitedQueue.getHasVisitedCount();
	}
	/**
	 * 得到本任务整个抓取的页数
	 * @return
	 */
/*	public int getAllVisitedCount() {
		return bdbVisitedQueue.getSize()-bdbPendingQueue.size();
	}*/
	

	public synchronized void close() {
		if(!isClose)
		{
			isClose=true;
			bdbPendingQueue.close();
			bdbVisitedQueue.close();
			bdbFactory.close();
			System.out.println("关闭dataset");
		
		}
	
		
	}


	public void clear(){
	/*	if(isClose)
		{*/
		System.out.println(dirPath);
		FileUtil.deleteDir(dirPath);
	/*	}*/
		
		
	}

	public BDBFactory getBdbFactory() {
		return bdbFactory;
	}
	

	

}
