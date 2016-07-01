/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: XpathSelector.java
 *   
 */
package com.hxt.webpasser.parser.common;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;


/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-9-1 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class XmlXpathSelector {

	//private XPath xPath ;
	private Document rootDocument;
	
	//根节点 
	private Element root = null; 
	
	//private boolean isHtml=true;  // 否则是xml
	
	//private String encode="gb2312";
	
	public XmlXpathSelector(String content) throws Exception
	{
		/* SAXReader reader = new SAXReader();
		 DocumentHelper.parseText(content); */
		rootDocument= DocumentHelper.parseText(content); 
		//xPath=XPathFactory.newInstance().newXPath();
		root = rootDocument.getRootElement(); 
	}
	

	public Node evaluateXPath(String xpathExp) throws Exception
	{
	/*
		NodeList nodeList = evaluateListXPath(xpathExp);
		if(nodeList.getLength()>0)
		{
			return nodeList.item(0);
		}*/
		Node node= root.selectSingleNode(xpathExp); 

	   return node;
	}
	

	public List<Node> evaluateListXPath(String xpathExp) throws Exception
	{
		/*Object result= xPath.evaluate(xpathExp, rootDocument, XPathConstants.NODESET);
		
		if (result instanceof NodeList) {
			NodeList nodeList = (NodeList) result;
			return nodeList;
		}*/
	   return root.selectNodes(xpathExp); 
	}
	
	



	
	
	public static String getAttribute(Node node, final String attrName) {
		String result = "";
		if ((node != null) && (node instanceof Node)) {
		
			return node.valueOf("@"+attrName);
		}
		return result; 
	}
	
	public static void main(String[] args) throws Exception {
		
		
			

	}
	
	
	
}
