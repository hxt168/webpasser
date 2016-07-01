/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: AcceptDecideRuleManager.java
 *   
 */
package com.hxt.webpasser.regular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hxt.webpasser.transport.xml.Rule;

/**
 * 功能说明: rule处理 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-9-12 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class AcceptDecideRuleHandler {
	/**
	 * 所有抓取共享的rule
	 */
	private static Map<String,DecideRule> ruleMap=new HashMap<String, DecideRule>();
	private static Map<String,AcceptRule> ruleAcceptMap=new HashMap<String, AcceptRule>();
	
	private static Map<String,Integer> shareRuleNameMaps=new HashMap<String, Integer>();
	
/*	private HtmlXpathRule xpathRule;
	private JsoupRule jsoupRule;*/
	/**
	 * 本次抓取共享的rule数据
	 */
	private Map<String,DecideRule> shareDecideRuleMap;
	

	
	static{
		
		//ruleMap.put("xpath", new XpathRule(null));  //
		//ruleMap.put("jsoup", new JsoupRule(null)); 
		// ruleMap.put("xmlxpath", new XmlXpathRule(null)); 
		shareRuleNameMaps.put("xpath", 1);
		shareRuleNameMaps.put("jsoup", 1);
		shareRuleNameMaps.put("xmlxpath", 1);
		shareRuleNameMaps.put("jsonpath", 1);
		
		ruleMap.put("regex", new RegexRule());
		ruleMap.put("cut", new CutRule());
		ruleMap.put("replace", new ReplaceRule());
		ruleMap.put("tagDel", new TagDelRule());
		ruleMap.put("combine", new CombineRule());
		ruleMap.put("split", new SplitRule());
		ruleMap.put("other", new OtherRule());
		ruleMap.put("toText", new ToTextRule());
		ruleMap.put("relativeToFullUrl", new RelativeToFullUrlRule());
		ruleAcceptMap.put("regex", new RegexRule());
		
	}
	
	public AcceptDecideRuleHandler()
	{

	}
	
	/**
	 * 获得DecideRule
	 * @param ruleName
	 * @param htmlContent
	 * @param rulesIndex  在某个rules中排在第几个
	 * @return
	 */
	private DecideRule getDecideRuleByName(String ruleName,String htmlContent,int rulesIndex)
	{
	/*	if("xpath".equals(ruleName))   
		{
			if(rulesIndex!=0){  //排在不是第一个
				return new HtmlXpathRule(htmlContent);
			}
			
			if(xpathRule==null)  // xpath rule在第一个 ， 对一个页面的解析进行复用 ,  
			{
				xpathRule=new HtmlXpathRule(htmlContent);

			}
			return xpathRule;	
		}else if("jsoup".equals(ruleName))   
		{
			if(rulesIndex!=0){  //排在不是第一个
				return new JsoupRule(htmlContent);
			}
			
			if(jsoupRule==null)  // jsoup rule在第一个 ， 对一个页面的解析进行复用 ,  
			{
				jsoupRule=new JsoupRule(htmlContent);

			}
			return jsoupRule;	
		}*/
		if(shareRuleNameMaps.containsKey(ruleName)){
			return getShareOrNewDecideRule(ruleName, htmlContent, rulesIndex);
		}else{
			return getStaticDecideRuleByName(ruleName);
		}
			
	}
	
	
	private DecideRule getShareOrNewDecideRule(String ruleName,String htmlContent,int rulesIndex){
		
		if(rulesIndex!=0){  //排在不是第一个
			return getNewDecideRule( ruleName, htmlContent);
		}
		
		return getShareDecideRule(ruleName, htmlContent);	
		
	}
	
	private DecideRule getShareDecideRule(String ruleName,String htmlContent){
		if(shareDecideRuleMap==null){
			shareDecideRuleMap=new HashMap<String, DecideRule>();
		}
		if(!shareDecideRuleMap.containsKey(ruleName)){
			shareDecideRuleMap.put(ruleName, getNewDecideRule(ruleName, htmlContent));
		}
		
		return shareDecideRuleMap.get(ruleName);
	}
	
	
	private DecideRule getNewDecideRule(String ruleName,String htmlContent){
		
		if("xpath".equals(ruleName)){
			return new HtmlXpathRule(htmlContent);
		}else if("jsoup".equals(ruleName)){
			return new JsoupRule(htmlContent);
		}else if("xmlxpath".equals(ruleName)){
			 return new XmlXpathRule(htmlContent);
		}else if("jsonpath".equals(ruleName)){
			 return new JsonPathRule(htmlContent);
		}
		
		return null;
	}
	
	/**
	 * 全获取局的DecideRule
	 * @param ruleName
	 * @param htmlContent
	 * @return
	 */
	private  DecideRule getStaticDecideRuleByName(String ruleName)
	{
		
		return ruleMap.get(ruleName);
	
			
	}
	
	/**
	 * 得到AcceptRule
	 * @param ruleName
	 * @param htmlContent
	 * @return
	 */
	private static AcceptRule getAcceptRuleByName(String ruleName,String htmlContent)
	{

		return ruleAcceptMap.get(ruleName);
	
	}
	/**
	 * 对rule进行处理，得到符合rule的数据
	 * @param rules
	 * @param content
	 * @return
	 */
	public  List<String> handleRulesForDigLink(List<Rule> rules, String content,Map valueMap)
	{

/*		List<String> contentList=new ArrayList<String>();
		contentList.add(content);
		if(rules!=null)
		{
			for(Rule rule:rules)
			{
				DecideRule decideRule=getStaticDecideRuleByName(rule.getType());
				contentList=decideRule.handle(rule, contentList,null);
			}
		}
		return contentList;*/
		return (List)handleRulesForPersistent(rules,content,valueMap);
	}
	
	/**
	 * 对rule进行处理，得到符合rule的数据，在XmlPersistentPageResult用到
	 * @param rules
	 * @param content
	 * @param valueMap 
	 * @return
	 */
	public List<Object> handleRulesForPersistent(List<Rule> rules, String content,Map valueMap)
	{
		List<Object> contentList=new ArrayList<Object>();
		contentList.add(content);
		if(rules!=null)
		{
			for(int i=0;i<rules.size();i++)
			{
				Rule rule=rules.get(i);
				DecideRule decideRule=getDecideRuleByName(rule.getType(),content,i);
				contentList=decideRule.handle(rule, contentList,valueMap);
				if(contentList!=null&&contentList.size()==1){
					Object conOb=contentList.get(0);
					if(conOb!=null&&conOb instanceof String){
						content=(String) contentList.get(0);
					}
	
				}
			}
		}
		return contentList;
	}
	
	public static boolean isAccept(List<Rule> rules, String url)
	{
		
		if(rules!=null)
		{
			for(Rule rule:rules)
			{
				AcceptRule acceptRule=getAcceptRuleByName(rule.getType(),url);
				boolean isAccept=acceptRule.isAccept(rule, url);
				if(!isAccept)
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
