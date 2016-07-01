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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hxt.webpasser.exception.SpiderException;
import com.hxt.webpasser.transport.xml.Rule;
import com.hxt.webpasser.utils.FillParamValueUtil;
import com.hxt.webpasser.utils.PatternUtils;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

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
public class JsonPathRule implements DecideRule{

	private DocumentContext documentContext;
	
	public JsonPathRule(String content)
	{
		documentContext=JsonPath.parse(content);
	}
	
	public List<Object> handle(Rule rule, List<Object> contentList,Map valueMap) {

		String val=rule.getValue();
		String attr=rule.getAttr();
		//List<Object> contentNewList=new ArrayList<Object>();
	/*	if(contentList!=null)
		{List<String> authors = JsonPath.read(json,xpath);
			
			for(String con:contentList)
			{*/
		//List<String> authors = JsonPath.read(json,xpath);
		if(contentList!=null&&val!=null&&contentList.size()==1)
		{
			/*for(int i=0;i<contentList.size();i++)
			{
				String con=(String) contentList.get(i);
				
				contentList.set(i, authors);
			}	*/
			try {
				Object cons = documentContext.read(val);
				contentList.set(0, cons);
			} catch (Exception e) {
				e.printStackTrace();
				contentList.set(0, null);
			}
		}
		
		return contentList;
	}
	
	public static void main(String[] args) {
		String content="{\"store\":{\"book\":\"7666\",\"list\":[\"title\",\"ffff\"]},\"dd\":88}";
		System.out.println(content);
		 String val="$.store.list";
		 DocumentContext documentContext=JsonPath.parse(content);
		 System.out.println(documentContext.read(val));
		 List<Object> cons = documentContext.read(val);
		 System.out.println(cons.get(0));
		 
		
	}
	

}
