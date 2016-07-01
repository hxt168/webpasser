/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: DownLoadFile.java
 *   
 */
package com.hxt.webpasser.fetcher;

import org.apache.commons.logging.Log;

import com.hxt.webpasser.log.SpiderLogs;
import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.proxy.ProxyManager;
import com.hxt.webpasser.proxy.ProxyManagerImpl;
import com.hxt.webpasser.proxy.ProxyRemotePollManagerImpl;
import com.hxt.webpasser.spider.SpiderController;
import com.hxt.webpasser.transport.xml.FetchConfig;
import com.hxt.webpasser.utils.PropertyValueUtil;
import com.hxt.webpasser.utils.RunTimeCost;

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
public abstract class DownLoadFile {

	 protected Log logger= SpiderLogs.getLog(DownLoadFile.class);
	
	 protected String charset = "GBK";
		
	 protected String userAgent = "Mozilla/5.0 Firefox/26.0";
		
	// protected String referer="http://www.soso.com";
		
	 protected int timeOutSecond=10;  //超时时间

	 protected int errorRetry=3;  //抓取失败重连次数
	 
	 protected int errorDelayTime=5;  //抓取失败后延迟多少秒再抓取
	 
	 protected int fetchPrepareDelayTime=3;  //抓取前延迟时间

	 
	 
	 protected int POOL_SIZE = 5;  //抓取时所用线程数，暂无用
	 
	 protected FetchConfig fetchConfig;
	 
	 protected SpiderController spiderController;
	 
	 public DownLoadFile(FetchConfig fetchConfig,SpiderController spiderController)
	 {
		 
		 charset= PropertyValueUtil.getPropertyStringValue(fetchConfig.getCharset(), charset);
		 userAgent= PropertyValueUtil.getPropertyStringValue(fetchConfig.getUserAgent(), userAgent);
		 timeOutSecond=PropertyValueUtil.getPropertyIntegerValue(fetchConfig.getTimeOutSecond(), timeOutSecond);
		 errorRetry=PropertyValueUtil.getPropertyIntegerValue(fetchConfig.getErrorRetry(), errorRetry);
		 errorDelayTime=PropertyValueUtil.getPropertyIntegerValue(fetchConfig.getErrorDelayTime(), errorDelayTime);
		 fetchPrepareDelayTime=PropertyValueUtil.getPropertyIntegerValue(fetchConfig.getFetchPrepareDelayTime(), fetchPrepareDelayTime);
		this.fetchConfig=fetchConfig;
		this.spiderController=spiderController;
		
		if(fetchConfig.getProxy()!=null){
			String remoteUrl=fetchConfig.getProxy().getPollUrl();
			ProxyManager proxyManager=new ProxyRemotePollManagerImpl(remoteUrl);
			spiderController.setProxyManager(proxyManager);
		}else if(fetchConfig.getProxies()!=null){
			String path=fetchConfig.getProxies().getPath();
			ProxyManager proxyManager=new ProxyManagerImpl(path);
			proxyManager.init();
			spiderController.setProxyManager(proxyManager);
		}
		
	 }
	
	 
	public PageResult fetchPageResult(CrawlUrl crawlUrl)
	{    
		if(fetchPrepareDelayTime!=0) //延迟
		{
			try {
				Thread.sleep(fetchPrepareDelayTime*1000); //延迟时间
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		RunTimeCost runTimeCost=new RunTimeCost();
		String errorMsg="";
		int retry=errorRetry;
		while(retry>0)
		{
			try {
				PageResult pageResult=downloadFile(crawlUrl);
				runTimeCost.endTime();
				pageResult.setCatchTimeCost(runTimeCost);
				logger.info("抓取时间："+runTimeCost.getCostTimeMicrosecond()+"毫秒");
				spiderController.getSpiderTaskTriggerChain().successDownloadAfter(crawlUrl, pageResult);
				return pageResult;
			} catch (Exception e) {
				e.printStackTrace();
				errorMsg=e.getMessage();
			}
			retry--;
			logger.warn("等待"+errorDelayTime+"秒 重新抓取 "+crawlUrl.getUrl()+" ！");
			try {
				Thread.sleep(errorDelayTime*1000); //等待后重新抓取
			} catch (InterruptedException e) {
				e.printStackTrace();
				spiderController.getSpiderTaskTriggerChain().tryOneFailDownload(crawlUrl);
			}
		}
		runTimeCost.endTime();
		logger.error(crawlUrl.getUrl()+" 抓取失败！");
		spiderController.putErrorUrlLogInfo(crawlUrl.getUrl(), crawlUrl.getUrlKeyHash(), "重试"+errorRetry+"次请求，下载网页失败！信息："+errorMsg,runTimeCost);
		spiderController.getSpiderTaskTriggerChain().reTryAllFailDownload(crawlUrl);
		return null;
	}
	
	protected abstract PageResult downloadFile(CrawlUrl crawlUrl) throws Exception;
	
}
