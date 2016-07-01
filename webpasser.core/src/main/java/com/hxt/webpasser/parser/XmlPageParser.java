/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: XmlPageParser.java
 *   
 */
package com.hxt.webpasser.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.parser.filter.LinkFilter;
import com.hxt.webpasser.regular.AcceptDecideRuleHandler;
import com.hxt.webpasser.spider.common.CrawlUrlUtil;
import com.hxt.webpasser.transport.xml.DigLink;
import com.hxt.webpasser.transport.xml.Field;
import com.hxt.webpasser.transport.xml.Page;
import com.hxt.webpasser.transport.xml.Rule;
import com.hxt.webpasser.transport.xml.XmlTask;

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
public class XmlPageParser implements HtmlParser {

	private XmlTask xmlTask;
	
	
	public XmlPageParser( XmlTask xmlTask)
	{
		
		this.xmlTask=xmlTask;
		
	}
	
	
	
	public Set<String> extracLinks(PageResult pageResult, LinkFilter filter) {
		Set<String> urlSet=new HashSet<String>();
		 List<Page> xmlPages=xmlTask.getPages();
		 if(xmlPages!=null)
		 {
			 Map valueMap=new HashMap<String,Object>();
			 valueMap.put("fetchUrl", pageResult.getUrl());
			 valueMap.put("taskName", xmlTask.getName()); //返回结果中默认有的字段值
			 for(int i=0;i<xmlPages.size();i++)
			 {
				 Page xmlPage=xmlPages.get(i);
				 AcceptDecideRuleHandler acceptDecideRuleHandler=new AcceptDecideRuleHandler();
				 List<Rule> scopeRules=xmlPage.getScope();
				 //满足条件
				 if(AcceptDecideRuleHandler.isAccept(scopeRules, pageResult.getUrl()))
				 {
					 
					 putParseDigLink(xmlPage.getDigLinks(), urlSet, acceptDecideRuleHandler, pageResult.getContent(),valueMap);
					 List<Field> fields= xmlPage.getFields();
					 if(pageResult.getPersistentPageInt()==null&&fields!=null) //将对应page的name放入
					 {
						 pageResult.setPersistentPageInt(i);
					 }
				 }
				 
			 }
		 }
		
		return urlSet;
	}
	
	/**
	 * 提取可抓取的链接
	 * @param xmlPage
	 * @param digUrlSet
	 * @param acceptDecideRuleHandler
	 * @param pageResult
	 */
	public void putParseDigLink(List<DigLink> digLinks,Set<String> digUrlSet, AcceptDecideRuleHandler acceptDecideRuleHandler,String content,Map valueMap){
		
		 if(digLinks!=null)
		 {
			 for(DigLink digLink :digLinks)
			 {
				List<String> digList=acceptDecideRuleHandler.handleRulesForDigLink(digLink.getRules(), content,valueMap);
				//digUrlSet.addAll(CrawlUrlUtil.getCrawlUrlSetByUrls(digList));
				digUrlSet.addAll(digList);
			 }
		 }
		
	}

}
