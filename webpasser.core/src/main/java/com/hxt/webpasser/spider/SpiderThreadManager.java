/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderThreadManager.java
 *   
 */
package com.hxt.webpasser.spider;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hxt.webpasser.callback.ProcessCrawlUrlCallBack;
import com.hxt.webpasser.fetcher.DownLoadFile;
import com.hxt.webpasser.frontier.Frontier;
import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.parser.HtmlParser;
import com.hxt.webpasser.spider.common.CrawlUrlUtil;
import com.hxt.webpasser.transport.xml.LimitHost;
import com.hxt.webpasser.transport.xml.Seed;
import com.hxt.webpasser.utils.RunTimeCost;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-30 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderThreadManager implements Runnable{

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Frontier frontier;
	SpiderController spiderController;
	private int runingThreadNum=0;  //正在运行
	private  List<Seed> seeds;
	private List<LimitHost> scopes;
	public SpiderThreadManager(SpiderController spiderController)
	{
		this.spiderController=spiderController;
		this.scopes=spiderController.getXmlTask().getScope();
		this.seeds=spiderController.getXmlTask().getSeeds();
	}
	
	
	
	public void start() throws Exception
	{
		logger.info("启动线程！");
		spiderController.init();
		frontier=spiderController.getFrontier();
		
		initCrawlerWithSeeds(seeds);
		
		for (int i = 0; i < spiderController.getSpiderThreads(); i++) {
		
			Thread t = new Thread(this, "spiderThread" + (i + 1));
			t.start();
			runingThreadNum++;
		}
		
	}
	
	/**
	 * 判断当前状态，如果是暂停或停止状态，进行相应操作
	 * @return true 退出   false：继续
	 */
	public boolean checkRunState()
	{
		int runState=spiderController.getRunState();
		if(runState==SpiderController.PAUSE_STATE)  //暂停状态
		{
			try {
				this.wait();  //等待
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(runState==SpiderController.STOP_STATE)  //如果变成了停止状态，进行停止
			{
					return true;
			}
		}else if(runState==SpiderController.STOP_STATE)  //停止状态
		{
			return true;
		}
		
		return false;
/*		switch (runState) {   //暂停状态
			case SpiderController.PAUSE_STATE:   
				
				
				
				break;
			case SpiderController.STOP_STATE:  //停止状态 
				
				
				
				break;
			default:
				break;
		}*/
		
	}
	
	/**
	 * 使用种子初始化 URL 队列
	 * @return
	 * @param seeds 种子URL
	 */ 
	protected void initCrawlerWithSeeds(List<Seed> seeds)
	{
		for(Seed seed:seeds)
		{
			
			CrawlUrl crawlUrl=new CrawlUrl();
			crawlUrl.setUrl(seed.getUrl());
			crawlUrl.setCharSet(spiderController.getCharSet());
			try {
				frontier.putUrlToPending(crawlUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}	
 
	/**
	 * 判断是否不退出
	 * @return true 不退出  false 退出
	 * @throws InterruptedException 
	 */
	public synchronized boolean checkIsNotExit() 
	{
		if(checkRunState())
		{
			//如果停止状态，进行退出
			return false;
		}
		
		if(!frontier.isPendingQueueEmpty()) //待抓取队列中有值
		{
			//
			if(spiderController.getSpiderThreads()!=runingThreadNum)
			{
				processNotifyThreads();
			}
			return true;
		
		}else{
			runingThreadNum--;
			logger.info("runingThreadNum:"+runingThreadNum);
			
			if(runingThreadNum>0) //其他线程在活动
			{
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} //等待
				runingThreadNum++;
				return true;
			}else{
				runingThreadNum++;
				 checkRunState(); //再判断
				this.notifyAll();  //都结束
				 System.out.println("all end "+runingThreadNum);
				return false;
			}
			
		}
	
	}
	
	public synchronized void processNotifyThreads()
	{
		logger.info(Thread.currentThread().getName()+ " endOneProcess notifyAll");
		this.notifyAll();  //
		
	}
	/**
	 * 执行下载解析
	 * @param crawlUrl
	 * @throws Exception
	 */
	public void processCrawlUrlAndDown(CrawlUrl crawlUrl,ProcessCrawlUrlCallBack processCrawlUrlCallBack) throws Exception
	{
		
		DownLoadFile downLoader=spiderController.getDownLoadFile();
		logger.info(" 下载："+crawlUrl.getUrl());
		spiderController.getSpiderTaskTriggerChain().beforeDownload(crawlUrl);
		//下载网页
		PageResult pageResult=downLoader.fetchPageResult(crawlUrl);
		//crawlUrl.setAbstractText(content);
		//该 url 放入到已访问的 URL 中
		if(frontier!=null){
			frontier.visited(crawlUrl);
		}


		//没有抓取到
		if(pageResult==null){
			return ;
		}
		if(processCrawlUrlCallBack!=null){
			processCrawlUrlCallBack.handlePageResult(pageResult);
		}

		try {
			processParse(pageResult,processCrawlUrlCallBack);
			spiderController.putSuccessUrlLogInfo(pageResult);
		} catch (Exception e) {
			e.printStackTrace();
			spiderController.putErrorUrlLogInfo(pageResult, " 解析或存储失败:"+e.getMessage());
		}
		
		if(pageResult.getLinkPageInfo()!=null)
		{	
			//将挖掘的链接入队,此CrawlUrl里有关联其他页面的信息
			putCrawlUrlToPending(pageResult.getLinkPageInfo().getDigCrawlUrlSet());
		}
	}
	
	private void processParse(PageResult pageResult,ProcessCrawlUrlCallBack processCrawlUrlCallBack) throws Exception {
		RunTimeCost runTimeCost=new RunTimeCost();
		try {
			//提取出下载网页中的 URL
			HtmlParser htmlParser=spiderController.getHtmlParser();
			Set<String> crawlUrls=htmlParser.extracLinks(pageResult,spiderController.getLinkFilter());
			if(processCrawlUrlCallBack!=null){
				processCrawlUrlCallBack.handleExtracLinks(crawlUrls);
			}
			putCrawlLinkToPending(crawlUrls);
			//解析存储
			spiderController.getPersistentPageResult().persistent(pageResult,processCrawlUrlCallBack); //持久化
		} catch (Exception e) {
			throw e;
		}finally{
			 runTimeCost.endTime();
			 pageResult.setPersistentTimeCost(runTimeCost);
		}
	}
	
	/**
	 * 将挖掘的链接入队
	 * @param crawlUrls
	 */
	private void putCrawlUrlToPending(Set<CrawlUrl> crawlUrls)
	{
		if(frontier!=null){
			//新的未访问的 URL 入队
			for(CrawlUrl crawllink:crawlUrls)
			{
				//CrawlUrl crawllink = CrawlUrlUtil.getCrawlUrlByUrl(link);
				//System.out.println("入队："+crawllink.getUrl());
				crawllink.setCharSet(spiderController.getCharSet());
				try {
					frontier.putUrlToPending(crawllink);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//break;
			}
		}	
	}
	
	/**
	 * 将挖掘的链接入队
	 * @param crawlUrls
	 */
	private void putCrawlLinkToPending(Set<String> crawlLinks)
	{

		Set<CrawlUrl> crawlUrls=CrawlUrlUtil.getCrawlUrlSetByUrlSet(crawlLinks,scopes);
		putCrawlUrlToPending(crawlUrls);
	}
	
	

	public int getRuningThreadNum() {
		return runingThreadNum;
	}


	public void run() {

		
		
			while(checkIsNotExit())
			{
				try {
					//队头URL出队列
					CrawlUrl crawlUrl=(CrawlUrl)frontier.getNext();
					if(crawlUrl==null)
						continue;
					
					processCrawlUrlAndDown( crawlUrl,null);
				
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		/*	if(runingThreadNum==0) //所有的都退出了
			{
				spiderController.setRunState(SpiderController.STOP_STATE);//停止状态
				frontier.close();
			}*/
			
			runingThreadNum--;
		System.out.println(Thread.currentThread().getName()+"退出了！runingThreadNum："+runingThreadNum);
		if(runingThreadNum==0){
			frontier.close();
			spiderController.changStopState();
			spiderController.close();
			
		}
	}
	
	
	
	
}
