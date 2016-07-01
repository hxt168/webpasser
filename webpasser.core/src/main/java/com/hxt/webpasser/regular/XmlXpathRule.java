/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: XpathRule.java
 *   
 */
package com.hxt.webpasser.regular;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Node;


import com.hxt.webpasser.exception.SpiderException;
import com.hxt.webpasser.parser.common.XmlXpathSelector;
import com.hxt.webpasser.transport.xml.Rule;
import com.hxt.webpasser.utils.PatternUtils;

/**
 * 功能说明: 解析xml的xpath <br>
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
public class XmlXpathRule implements DecideRule{

	private XmlXpathSelector xpathSelector;
	
	public XmlXpathRule(String content)
	{
		try {
			xpathSelector=new XmlXpathSelector(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Object> handle(Rule rule, List<Object> contentList,Map valueMap) {

		String xpath=rule.getValue();
		String attr=rule.getAttr();
		List<Object> contentNewList=new ArrayList<Object>();
	/*	if(contentList!=null)
		{
			
			for(String con:contentList)
			{*/
	
		List<Node> tagNodeList;
				try {
					tagNodeList = xpathSelector.evaluateListXPath(xpath);
					if(tagNodeList!=null)
					{
						
						if(attr!=null)
						{
							for (int i = 0; i < tagNodeList.size(); i++) {
								Node node = tagNodeList.get(i); 	
								String attrText=XmlXpathSelector.getAttribute(node, attr);
								if(attrText!=null)
								{
									contentNewList.add(attrText);
								}
				
							}
						}else{  //默认text()
							String exp=rule.getExp();
							for (int i = 0; i < tagNodeList.size(); i++) {
								Node node = tagNodeList.get(i); 	
								String text=null;
								if("xml()".equals(exp))
								{
									text=node.asXML();
								}else //if("xml()".equals(exp))
								{
									if(node.getText()!=null)
									{
										text=node.getText();
									}

								}
							
								if(text!=null)
								{
									contentNewList.add(text);
								}
				
							}	
						}
						
					}
					return contentNewList;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
	/*		}
				
		}*/
		
		return contentList;
	}
	
	
	

}
