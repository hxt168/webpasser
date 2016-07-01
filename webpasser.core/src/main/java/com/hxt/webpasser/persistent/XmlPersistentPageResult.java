/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: XmlPersistentPageResult.java
 *   
 */
package com.hxt.webpasser.persistent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.hxt.webpasser.callback.ProcessCrawlUrlCallBack;
import com.hxt.webpasser.exception.SpiderException;
import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.LinkPageInfo;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.parser.XmlPageParser;
import com.hxt.webpasser.regular.AcceptDecideRuleHandler;
import com.hxt.webpasser.spider.SpiderController;
import com.hxt.webpasser.spider.common.CrawlUrlUtil;
import com.hxt.webpasser.transport.xml.Field;
import com.hxt.webpasser.transport.xml.Page;
import com.hxt.webpasser.transport.xml.Param;
import com.hxt.webpasser.transport.xml.XmlTask;
import com.hxt.webpasser.utils.FillParamValueUtil;
import com.hxt.webpasser.utils.TrimUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-9-14 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class XmlPersistentPageResult  implements PersistentPageResult{

	//protected static Log logger= SpiderLogs.getLog(XmlPersistentPageResult.class);
	private XmlTask xmlTask;
	private SpiderController spiderController;
	private XmlPageParser xmlPageParser;
	
	public XmlPersistentPageResult( XmlTask xmlTask,SpiderController spiderController)
	{
		this.xmlTask=xmlTask;
		this.spiderController=spiderController;
		if(spiderController.getHtmlParser() instanceof XmlPageParser){
			xmlPageParser=(XmlPageParser)spiderController.getHtmlParser();
			
		}else{
			throw new SpiderException("使用XmlPersistentPageResult了，则HtmlParser必须是或继承  XmlPageParser");
		}
	}
	
	public void persistent(PageResult pageResult,ProcessCrawlUrlCallBack processCrawlUrlCallBack) {
		
		if(pageResult.getPersistentPageInt()!=null)
		{
			Map<String,Object> returnValue=new HashMap<String, Object>();
			returnValue.put("fetchUrl", pageResult.getUrl());
			returnValue.put("taskName", xmlTask.getName()); //返回结果中默认有的字段值
			 Page xmlPage=xmlTask.getPages().get(pageResult.getPersistentPageInt());
			if(pageResult.getLinkParamValueMap()!=null&&xmlPage.getName().equals(pageResult.getLinkPageName()))
			{
				//从其他网页关联过来的赋值
				for(Map.Entry<String,Object> entry:pageResult.getLinkParamValueMap().entrySet())
				{
					returnValue.put(entry.getKey(), entry.getValue());
				}
			}
			 List<Field> fields=xmlPage.getFields();
			 String content=pageResult.getContent();
			 handleXmlFields(fields, content, pageResult, returnValue);
			 spiderController.getSpiderTaskTriggerChain().afterParseToMap(returnValue);
			 TrimUtil.trimMap(returnValue);
			 if(spiderController.getHandleResultMapInterface()!=null){
				 spiderController.getHandleResultMapInterface().handle(returnValue);
			 }else{
				 System.out.println(pageResult.getUrl()+" 没有handle处理！");
			 }
			 if(processCrawlUrlCallBack!=null){
				 processCrawlUrlCallBack.handleReturnMapValue(returnValue);
			 }
			 spiderController.getSpiderTaskTriggerChain().afterPersistent(returnValue);
			//logger.info("存储解析时间："+runTimeCost.getCostTimeMicrosecond()+"毫秒");
		}
		
	}
	

	
	/**
	 * 处理解析出XmlFields的数据
	 * @param fields
	 * @param pageResult
	 * @param returnValue
	 */
	private void handleXmlFields(List<Field> fields,String content, PageResult pageResult,Map<String,Object> returnValue)
	{
		//String content=pageResult.getContent();
		 AcceptDecideRuleHandler acceptDecideRuleHandler=new AcceptDecideRuleHandler();
		 for(Field field:fields)
		 {
			 List<Object> valList=acceptDecideRuleHandler.handleRulesForPersistent(field.getRules(), content,returnValue);
			 
			 handleFieldValue(  field,content, valList,pageResult,returnValue);
			 
			 if(field.getLinkPage()!=null)
			 {
				 Map<String,Object> linkParamValueMap=new HashMap<String, Object>();
				 if(field.getLinkPage().getParams()!=null)  //将本page的设定需填入的值赋入
				 {
					 for(Param param:field.getLinkPage().getParams())
					 {
						 linkParamValueMap.put(param.getName(),FillParamValueUtil.fillParamValue("", param.getValue(), returnValue));
					 }
				 }
				 

				 Set<String> linksUrlSet=new HashSet<String>();
				 if(field.getLinkPage().getDigLinks()!=null)
				 {
					 xmlPageParser.putParseDigLink(field.getLinkPage().getDigLinks(), linksUrlSet, acceptDecideRuleHandler, content,returnValue);
				 }
				 Set<CrawlUrl> digUrlSet=CrawlUrlUtil.getCrawlUrlSetByUrlSet(linksUrlSet,xmlTask.getScope());
				 for(CrawlUrl crawlUrl:digUrlSet){
					 crawlUrl.setLinkParamValueMap(linkParamValueMap);
					 crawlUrl.setLinkPageName(field.getLinkPage().getName());
				 }
				 LinkPageInfo linkPageInfo=new LinkPageInfo();
				 linkPageInfo.setLinkPageName(field.getLinkPage().getName());
				 linkPageInfo.setDigCrawlUrlSet(digUrlSet);
				 pageResult.setLinkPageInfo(linkPageInfo);
				 
			 }
		 }
		
	}
	
	/**
	 * 处理field中再包含field map集合 或 没有包含的情况
	 * @param fields
	 * @param valList
	 * @param itemMapReturnValue
	 */
	private void handleFieldValue( Field field,String content,List<Object> valList, PageResult pageResult,Map<String,Object> returnValue)
	{
		 if(field.getFields()!=null) //有field子项集，则把当前作为一个map
		 {
		
			 if(field.getIsList()!=null&&1==field.getIsList())  //作为列表
			 {
				List<Map> itemList=new ArrayList<Map>();
				for(Object valOb:valList)
				{
					String val=(String)valOb;
					 Map<String,Object> itemMapReturnValue=new HashMap<String, Object>(); 
					 handleXmlFields( field.getFields(),val,pageResult,itemMapReturnValue); //递归
					 itemList.add(itemMapReturnValue);
				}
				 returnValue.put(field.getName(), itemList);
			 }else{
				 Map<String,Object> itemMapReturnValue=new HashMap<String, Object>(); 
				 handleXmlFields( field.getFields(),(String)valList.get(0),pageResult,itemMapReturnValue);
				 returnValue.put(field.getName(), itemMapReturnValue);
			 }

		 }else{  //没有子项
			 
			 putFieldValue( field,valList,returnValue);
		 }

		 
	}
	
	
	/**
	 * 将field解析出来的值放入returnValue map
	 * @param field
	 * @param valList
	 * @param returnValue
	 */
	private void putFieldValue(Field field,List<Object> valList,Map<String,Object> returnValue)
	{
		 if(field.getIsList()!=null&&1==field.getIsList())  //作为列表
		 {
	
			if(field.getJoinMark()!=null) //拼接成一个字符串
			{
				StringBuffer valBuf=new StringBuffer();
				for(int i=0;i<valList.size();i++)
				{
					String val=(String) valList.get(i);
					valBuf.append(val);
					if(i!=valList.size()-1)
					{
						valBuf.append(field.getJoinMark());
					}
					
				}
				returnValue.put(field.getName(), valBuf.toString());
			}else{ //列表
				returnValue.put(field.getName(), valList);
			}
		 }else{
			 if(valList!=null&&valList.size()>0)
			 {
				 returnValue.put(field.getName(), valList.get(0));
			 }
			
		 }
		
	}
}
