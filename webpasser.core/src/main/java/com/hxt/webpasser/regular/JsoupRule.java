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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hxt.webpasser.exception.SpiderException;
import com.hxt.webpasser.parser.common.JsoupSelector;
import com.hxt.webpasser.transport.xml.Rule;
import com.hxt.webpasser.utils.PatternUtils;

/**
 * 功能说明:  <br>
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
public class JsoupRule implements DecideRule{

	private JsoupSelector jsoupSelector;
	
	public JsoupRule(String content)
	{
	
			jsoupSelector=new JsoupSelector(content);
	
	}
	
	public List<Object> handle(Rule rule, List<Object> contentList,Map valueMap) {

		String cssQuery=rule.getValue();
		String attr=rule.getAttr();
		List<Object> contentNewList=new ArrayList<Object>();
	/*	if(contentList!=null)
		{
			
			for(String con:contentList)
			{*/
	
		Elements elements;
				try {
					elements = jsoupSelector.selectList(cssQuery);
					if(elements!=null)
					{
						
						if(attr!=null)
						{
							for (int i = 0; i < elements.size(); i++) {
								Element element = elements.get(i);
								String attrText=element.attr(attr);
								if(attrText!=null)
								{
									contentNewList.add(attrText);
								}
				
							}
						}else{  //默认html()
							String exp=rule.getExp();
							for (int i = 0; i < elements.size(); i++) {
								Element element = elements.get(i);	
								String text=null;
								if("text()".equals(exp))
								{
									text=element.text();

								}else //if("html()".equals(exp))
								{
									text=element.html();

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
