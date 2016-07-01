/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderController.java
 *   
 */
package com.hxt.webpasser.spider;

import com.hxt.webpasser.exception.SpiderException;
import com.hxt.webpasser.fetcher.DownLoadFile;
import com.hxt.webpasser.fetcher.HttpClientFetcher;
import com.hxt.webpasser.frontier.Frontier;
import com.hxt.webpasser.frontier.bdb.BDBFrontier;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.parser.HtmlParser;
import com.hxt.webpasser.parser.XmlPageParser;
import com.hxt.webpasser.parser.filter.DefaultLinkFilter;
import com.hxt.webpasser.parser.filter.LinkFilter;
import com.hxt.webpasser.persistent.HandleResultMapInterface;
import com.hxt.webpasser.persistent.PersistentPageResult;
import com.hxt.webpasser.persistent.XmlPersistentPageResult;
import com.hxt.webpasser.proxy.ProxyManager;
import com.hxt.webpasser.transport.util.XStreamWireFormat;
import com.hxt.webpasser.transport.xml.ResultHandler;
import com.hxt.webpasser.transport.xml.Trigger;
import com.hxt.webpasser.transport.xml.XmlTask;
import com.hxt.webpasser.trigger.SpiderTaskTrigger;
import com.hxt.webpasser.trigger.SpiderTaskTriggerChain;
import com.hxt.webpasser.utils.FileUtil;
import com.hxt.webpasser.utils.RunTimeCost;
import com.hxt.webpasser.utils.StringUtil;

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
public class SpiderController {

	private String charSet="utf-8";  //网页编码
	private int spiderThreads=4;  //线程
	
	private Frontier frontier;
	private DownLoadFile downLoadFile;
	private HtmlParser htmlParser;
	private LinkFilter linkFilter;
	private PersistentPageResult persistentPageResult;
	//private ErrorUrlFrontier errorUrlFrontier;
	private ProxyManager proxyManager;
	/**
	 * 处理结果
	 */
	private HandleResultMapInterface handleResultMapInterface;
	/**
	 * 触发器链
	 */
	private SpiderTaskTriggerChain spiderTaskTriggerChain;
	
	private SpiderTaskState spiderTaskState;
	
	public final static int  OPERATE_START_ACTION=1;  //进行启动
	public final static int  OPERATE_PAUSE_ACTION=2;  //进行暂停
	public final static int  OPERATE_RESTART_ACTION=3;  //暂停后恢复启动
	public final static int  OPERATE_STOP_ACTION=4;  //进行停止
	
	
	public final static int INIT_STATE=0;  //初始化状态
	public final static int RUNING_STATE=1;  //正在运行状态
	public final static int PAUSE_STATE=2;  //暂停状态
	public final static int STOP_STATE=3;   //停止状态
	
	
	private int runState;   //运行状态  0：未启动   1：运行   2：停止  3：暂停
	
	private XmlTask xmlTask;
	
	private String taskName;
	
	public SpiderController(String taskName,String taskXmlPath)
	{
		this.taskName=taskName;
		parseXmlTask( taskXmlPath);
		
	}

	/**
	 * 生成xmlTask
	 * @param taskXmlPath
	 */
	private void parseXmlTask(String taskXmlPath)
	{
	
		//String xmlPath=ResourceUtil.getResourcePath()+taskXmlPath;
		if(FileUtil.checkIsExist(taskXmlPath))
		{
			String con=FileUtil.readTxtFile(taskXmlPath);
			try {
				setXmlTask(XStreamWireFormat.unmarshalText(con,XmlTask.class));
			} catch (Exception e) {
				throw new SpiderException(taskXmlPath+"配置不正确！");
			}
		
			 prepareTask( taskXmlPath);
			
		}else{
			throw new SpiderException(taskXmlPath+" 文件不存在！无法创建task");
		}
	}
	
	private void prepareTask(String taskXmlPath)
	{
		if(xmlTask!=null)
		{
			//taskName=xmlTask.getName();
			xmlTask.setName(taskName);
			if(!StringUtil.isEmpty(xmlTask.getFetchConfig().getRunThreadNum())){
				spiderThreads=Integer.parseInt(xmlTask.getFetchConfig().getRunThreadNum());
			}
			
			if(taskName==null||"".equals(taskName))
			{
				throw new SpiderException(taskXmlPath+" taskName不能为空！");
			}
			
			
			
			
		}
		
	}
	
	public void init() throws Exception
	{

		if(downLoadFile==null)
		{
			this.setDownLoadFile(new HttpClientFetcher(xmlTask.getFetchConfig(),this));
		}
		if(htmlParser==null)
		{
			this.setHtmlParser(new XmlPageParser(xmlTask));
		}
		if(frontier==null)
		{
			BDBFrontier bDBFrontier=new BDBFrontier(taskName);
			this.setFrontier(bDBFrontier);
	/*		if(errorUrlFrontier==null){
				errorUrlFrontier=new BdbErrorUrlFrontier(bDBFrontier.getBdbFactory(), taskName);
			}*/
		}

		if(linkFilter==null)
		{
			this.setLinkFilter(new DefaultLinkFilter());
		}
		if(persistentPageResult==null)
		{
			persistentPageResult=new XmlPersistentPageResult(xmlTask,this);
		}
	/*	if(errorUrlFrontier==null){
			errorUrlFrontier=new LogErrorUrlFrontier(taskName);
		}*/
		spiderTaskState=new SpiderTaskState(taskName);
		
		HandleClassLoader handleClassLoader=new HandleClassLoader(this);
		if(xmlTask!=null&&xmlTask.getResultHandlers()!=null)
		{
			
			for(ResultHandler resultHandler:xmlTask.getResultHandlers()){
				
				handleClassLoader.loadOneHandler(resultHandler);
			}
			
		}
		// 初始化触发器链
		spiderTaskTriggerChain=new SpiderTaskTriggerChain();
		if(xmlTask.getTriggers()!=null){
			for(Trigger trigger:xmlTask.getTriggers()){
				//String classPath=trigger.getClazz();
				Object newOb=handleClassLoader.instanceNewObject(trigger);
				if(newOb!=null&&newOb instanceof SpiderTaskTrigger){
					SpiderTaskTrigger spiderTaskTrigger=(SpiderTaskTrigger)newOb;
					spiderTaskTriggerChain.addSpiderTaskTrigger(spiderTaskTrigger);
				}else{
					
					throw new SpiderException("加载"+taskName+" xml时。生成trigger触发器类失败！！");
				}
				
			}
			
		}
	}
	
	
	/**
	 * 开始执行task
	 * @throws Exception
	 */
	public void changStartState() throws Exception
	{
		//设置成启动状态
		setRunState(SpiderController.RUNING_STATE);
		//spiderThreadManager.start();
		//getSpiderTaskState().start();
	}
	
	/**
	 * 暂停
	 */
	public void changPauseState()
	{
		//设置成暂停状态
		setRunState(SpiderController.PAUSE_STATE);
		
	}
	
	/**
	 * 重新启动，从暂停变为启动
	 */
	public void changReStartState() throws Exception
	{
		//设置成暂停状态
		setRunState(SpiderController.RUNING_STATE);
		//spiderThreadManager.processNotifyThreads();
	}
	
	/**
	 * 停止
	 */
	public void changStopState()
	{	
		//设置成停止状态
		setRunState(SpiderController.STOP_STATE);
		//spiderThreadManager.processNotifyThreads();
		getSpiderTaskState().end();
			
		
		
	}
	
	
	public void close(){
		
		getSpiderTaskState().closeLoger();
		handleResultMapInterface.close();
	}
	
	public void putSuccessUrlLogInfo(PageResult pageResult){
		/*ErrorCrawlUrlInfo errorCrawlUrlInfo=new ErrorCrawlUrlInfo();
		errorCrawlUrlInfo.setUrl(url);
		errorCrawlUrlInfo.setUrlHash(urlHash);
		errorCrawlUrlInfo.setErrorInfo(errorInfo);
		try {
			errorUrlFrontier.putErrorCrawlUrlInfo(errorCrawlUrlInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		String url=pageResult.getUrl();
		long downCostTime=pageResult.getCatchTimeCost().getCostTimeMicrosecond();
		long persistentCostTime=pageResult.getPersistentTimeCost().getCostTimeMicrosecond();
		long downByteSize=pageResult.getContent()==null?0:pageResult.getContent().getBytes().length;
		spiderTaskState.addDownLoadSize(downByteSize);
		spiderTaskState.putSuccessUrlInfo(url+" downCost:"+downCostTime+"ms persistentCost:"+persistentCostTime+"ms downKb:"+downByteSize+"b ");
	}
	
	public void putErrorUrlLogInfo(PageResult pageResult,String errorInfo){
		/*ErrorCrawlUrlInfo errorCrawlUrlInfo=new ErrorCrawlUrlInfo();
		errorCrawlUrlInfo.setUrl(url);
		errorCrawlUrlInfo.setUrlHash(urlHash);
		errorCrawlUrlInfo.setErrorInfo(errorInfo);
		try {
			errorUrlFrontier.putErrorCrawlUrlInfo(errorCrawlUrlInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		String url=pageResult.getUrl();
		long downCostTime=0;
		if(pageResult.getCatchTimeCost()!=null){
			downCostTime=pageResult.getCatchTimeCost().getCostTimeMicrosecond();
		}
		long persistentCostTime=0;
		if(pageResult.getPersistentTimeCost()!=null){
			persistentCostTime=pageResult.getPersistentTimeCost().getCostTimeMicrosecond();
		}
		long downByteSize=pageResult.getContent()==null?0:pageResult.getContent().getBytes().length;
		if(downByteSize!=0){
			spiderTaskState.addDownLoadSize(downByteSize);
		}
		spiderTaskState.putErrorUrlInfo(url+" downCost:"+downCostTime+"ms persistentCost:"+persistentCostTime+"ms downKb:"+downByteSize+"b error:"+errorInfo);
	}
	
	public void putErrorUrlLogInfo(String url,String urlHash,String errorInfo,RunTimeCost catchTimeCost){
		/*ErrorCrawlUrlInfo errorCrawlUrlInfo=new ErrorCrawlUrlInfo();
		errorCrawlUrlInfo.setUrl(url);
		errorCrawlUrlInfo.setUrlHash(urlHash);
		errorCrawlUrlInfo.setErrorInfo(errorInfo);
		try {
			errorUrlFrontier.putErrorCrawlUrlInfo(errorCrawlUrlInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		long downCostTime=0;
		if(catchTimeCost!=null){
			downCostTime=catchTimeCost.getCostTimeMicrosecond();
		}

		spiderTaskState.putErrorUrlInfo(url+" downCost:"+downCostTime+"ms parserCost:0ms downKb:0b error:"+errorInfo);
	}
	
	public DownLoadFile getDownLoadFile() {
		return downLoadFile;
	}


	public void setDownLoadFile(DownLoadFile downLoadFile) {
		this.downLoadFile = downLoadFile;
	}





	public HtmlParser getHtmlParser() {
		return htmlParser;
	}





	public void setHtmlParser(HtmlParser htmlParser) {
		this.htmlParser = htmlParser;
	}





	public Frontier getFrontier() {
		return frontier;
	}





	public void setFrontier(Frontier frontier) {
		this.frontier = frontier;
	}





	public String getTaskName() {
		return taskName;
	}





	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public LinkFilter getLinkFilter() {
		return linkFilter;
	}


	public void setLinkFilter(LinkFilter linkFilter) {
		this.linkFilter = linkFilter;
	}


	public String getCharSet() {
		return charSet;
	}


	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}


	public PersistentPageResult getPersistentPageResult() {
		return persistentPageResult;
	}


	public void setPersistentPageResult(PersistentPageResult persistentPageResult) {
		this.persistentPageResult = persistentPageResult;
	}


	public int getSpiderThreads() {
		return spiderThreads;
	}


	public void setSpiderThreads(int spiderThreads) {
		this.spiderThreads = spiderThreads;
	}


	public XmlTask getXmlTask() {
		return xmlTask;
	}


	public void setXmlTask(XmlTask xmlTask) {
		this.xmlTask = xmlTask;
	}

	public int getRunState() {
		return runState;
	}

	public void setRunState(int runState) {
		this.runState = runState;
	}

/*	public ErrorUrlFrontier getErrorUrlFrontier() {
		return errorUrlFrontier;
	}

	public void setErrorUrlFrontier(ErrorUrlFrontier errorUrlFrontier) {
		this.errorUrlFrontier = errorUrlFrontier;
	}*/

	public SpiderTaskState getSpiderTaskState() {
		return spiderTaskState;
	}

	public void setSpiderTaskState(SpiderTaskState spiderTaskState) {
		this.spiderTaskState = spiderTaskState;
	}

	public ProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}

	public HandleResultMapInterface getHandleResultMapInterface() {
		return handleResultMapInterface;
	}

	public void setHandleResultMapInterface(HandleResultMapInterface handleResultMapInterface) {
		this.handleResultMapInterface = handleResultMapInterface;
	}

	public SpiderTaskTriggerChain getSpiderTaskTriggerChain() {
		return spiderTaskTriggerChain;
	}

	public void setSpiderTaskTriggerChain(SpiderTaskTriggerChain spiderTaskTriggerChain) {
		this.spiderTaskTriggerChain = spiderTaskTriggerChain;
	}
	
	
	
	
}
