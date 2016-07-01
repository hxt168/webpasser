/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderTask.java
 *   
 */
package com.hxt.webpasser.spider;

import java.util.HashMap;
import java.util.Map;

import com.hxt.webpasser.callback.ProcessCrawlUrlCallBack;
import com.hxt.webpasser.fetcher.DownLoadFile;
import com.hxt.webpasser.frontier.Frontier;
import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.parser.HtmlParser;
import com.hxt.webpasser.parser.filter.LinkFilter;
import com.hxt.webpasser.utils.ArchiveUtils;
import com.hxt.webpasser.utils.MyDateUtil;
import com.hxt.webpasser.utils.ResourceUtil;

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
public class SpiderTask {

	SpiderController spiderController;
	SpiderThreadManager spiderThreadManager;

	
	public SpiderTask(String taskName,String taskXmlPath)
	{
		spiderController=new SpiderController(taskName,taskXmlPath);
		
		spiderThreadManager=new SpiderThreadManager(spiderController);
	
		
	}

	
	public  Map<String,Object> monitorSpiderTaskInfo(){
		Map<String,Object> taskInfoMap=new HashMap<String, Object>();
		taskInfoMap.put("taskName", getTaskName());
		taskInfoMap.put("runState", spiderController.getRunState());
		if(spiderController.getRunState()!=spiderController.INIT_STATE) //不是初始化
		{
			taskInfoMap.put("runThreadNum", spiderThreadManager.getRuningThreadNum());
			taskInfoMap.put("pendingCount", spiderController.getFrontier().getPendingCount());
			taskInfoMap.put("visitedCount", spiderController.getFrontier().getVisitedCount());
		//	//taskInfoMap.put("allVisitedCount", spiderController.getFrontier().getAllVisitedCount());
			taskInfoMap.put("startTime", MyDateUtil.getTimeString(spiderController.getSpiderTaskState().getStartTime()));
			taskInfoMap.put("endTime", MyDateUtil.getTimeString(spiderController.getSpiderTaskState().getEndTime()));
			taskInfoMap.put("successCount", spiderController.getSpiderTaskState().getSuccessCount());
			taskInfoMap.put("errorCount", spiderController.getSpiderTaskState().getErrorCount());
			taskInfoMap.put("downSize", ArchiveUtils.formatBytesForDisplay(spiderController.getSpiderTaskState().getDownloadSize()));
			/*taskInfoMap.put("errorUrls", spiderController.getErrorUrlFrontier().getAll());*/
		}

		return taskInfoMap;
	}
	
	public String getTaskName() {
		
		
		return spiderController.getTaskName();
	}
	
	/**
	 * 开始执行task
	 * @throws Exception
	 */
	public void start() throws Exception
	{
		spiderController.changStartState();
		//设置成启动状态
		//spiderController.setRunState(SpiderController.RUNING_STATE);
		spiderThreadManager.start();
		spiderController.getSpiderTaskState().start();
	}
	
	/**
	 * 暂停
	 */
	public void pause()
	{
		//设置成暂停状态
		//spiderController.setRunState(SpiderController.PAUSE_STATE);
		spiderController.changPauseState();
	}
	
	/**
	 * 重新启动，从暂停变为启动
	 */
	public void reStart() throws Exception
	{
		//设置成暂停状态
		spiderController.changReStartState();
		//spiderController.setRunState(SpiderController.RUNING_STATE);
		spiderThreadManager.processNotifyThreads();
	}
	
	/**
	 * 停止
	 */
	public void stop()
	{
		if(spiderController.getRunState()!=SpiderController.STOP_STATE){
			
			//设置成停止状态
			spiderController.changStopState();
			//spiderController.setRunState(SpiderController.STOP_STATE);
			spiderThreadManager.processNotifyThreads();
			//spiderController.getSpiderTaskState().end();
			
		}
		
	}
	
	/**
	 * 判断是否是运行或暂停状态
	 * @return
	 */
	public boolean checkIsRunOrPauseState(){
		if(spiderController.getRunState()==SpiderController.RUNING_STATE||spiderController.getRunState()==SpiderController.PAUSE_STATE){
			return true;
		}
		return false;
	}
	
	/**
	 * 进行一次抓取的测试
	 * @param url
	 * @throws Exception 
	 */
	public void testOneFetchAndParse(String url,ProcessCrawlUrlCallBack processCrawlUrlCallBack) throws Exception{
		spiderController.changStartState();
		spiderController.init();
		spiderController.setHandleResultMapInterface(null);
		
		spiderController.getSpiderTaskState().start();
		
		CrawlUrl crawlUrl=new CrawlUrl();
		crawlUrl.setUrl(url);
		crawlUrl.setCharSet(spiderController.getCharSet());
		spiderThreadManager.processCrawlUrlAndDown(crawlUrl, processCrawlUrlCallBack);
		
		spiderController.changStopState();
	}
	
/*	*//**
	 * 抓取过程
	 * @return
	 * @param seeds
	 * @throws Exception 
	 *//*
	protected void crawling(String[] seeds) throws Exception
	{  
		
	
		//初始化 URL 队列
		initCrawlerWithSeeds(seeds);
		
		//循环条件：待抓取的链接不空且抓取的网页不多于1000
		while(!frontier.isPendingQueueEmpty())
		{
			//队头URL出队列
			CrawlUrl crawlUrl=(CrawlUrl)frontier.getNext();
			if(crawlUrl==null)
				continue;
			DownLoadFile downLoader=spiderController.getDownLoadFile();
			System.out.println("下载："+crawlUrl.getUrl());
			//下载网页
			PageResult pageResult=downLoader.downloadFile(crawlUrl);
			//crawlUrl.setAbstractText(content);
			//该 url 放入到已访问的 URL 中
			frontier.visited(crawlUrl);
			//提取出下载网页中的 URL
			
			
			HtmlParser htmlParser=spiderController.getHtmlParser();
			Set<String> links=htmlParser.extracLinks(pageResult,spiderController.getLinkFilter());
			
			//新的未访问的 URL 入队
			for(String link:links)
			{
				System.out.println("入队："+link);
				CrawlUrl crawllink=new CrawlUrl();
				crawllink.setUrl(link);
				crawllink.setCharSet(spiderController.getCharSet());
				try {
					frontier.putUrlToPending(crawllink);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			spiderController.getPersistentPageResult().persistent(pageResult); //持久化
		}
		
		
		
	}*/
	
	public void setDownLoadFile(DownLoadFile downLoadFile) {
		spiderController.setDownLoadFile(downLoadFile) ;
	}


	public void setHtmlParser(HtmlParser htmlParser) {
		spiderController.setHtmlParser(htmlParser);
	}


	public void setFrontier(Frontier frontier) {
		spiderController.setFrontier(frontier);
	}


	public void setLinkFilter(LinkFilter linkFilter) {
		spiderController.setLinkFilter(linkFilter);
	}
	
	
	
	
	public SpiderController getSpiderController() {
		return spiderController;
	}


	public static void main(String[] args) {
		
		//定义过滤器，提取以http://www.lietu.com开头的链接
/*		LinkFilter filter = new LinkFilter(){
			public boolean accept(String url) {
				if(url.startsWith("http://www.youku.com/show_page/id")||url.startsWith("http://www.youku.com/v_olist/"))
					return true;
				else
					return false;
			}
		};*/
		
/*		String[] seeds=new String[]{"http://www.youku.com/v_olist/c_97_a_%E5%A4%A7%E9%99%86_s_1_d_1.html"};*/
		String taskXmlPath="catch/test.xml";
		taskXmlPath=ResourceUtil.getResourcePath()+taskXmlPath;
		SpiderTask spiderTask=new SpiderTask("testTask",taskXmlPath);

		try {
			spiderTask.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
