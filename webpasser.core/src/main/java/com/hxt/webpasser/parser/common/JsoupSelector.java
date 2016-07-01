/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: JsoupSelector.java
 *   
 */
package com.hxt.webpasser.parser.common;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hxt.webpasser.utils.StringUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-31 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class JsoupSelector {
 
	private Document rootDocument;
	
	public JsoupSelector(String content)
	{
		rootDocument = Jsoup.parse(content);

	}
	
	
	public Element selectOne(String cssQuery)
	{
		Elements elements = rootDocument.select(cssQuery); //带有href属性的a元素
		if(elements!=null&&elements.size()>0)
		{
			
			return elements.first();
		}
		return null;
	}
	
	
    /**
     * 从html转text
     * @param html
     * @return
     */
    public static String getTextFromHtml(String html){
    	if(StringUtil.isEmpty(html)){
    		return "";
    	}
    	
    	Document rootDocument2 = Jsoup.parse(html);
		return rootDocument2.text();
    }
	
	public Elements selectList(String cssQuery)
	{
		Elements elements = rootDocument.select(cssQuery); //带有href属性的a元素
		
		return elements;
	}
	
	public static void main(String[] args) throws IOException {
		
		String html="<html><a href='#'>aaaa</a>dd <img src=\" 你好.png\" /><div class=\"eee\"><img src=\" 23.png\" /><img src=\" 25.png\" /></div> <div class=\"masthead\"><img src=\" 22.png\" /><img src=\" 24.png\" /></div> <html>";
		Document doc = Jsoup.parse(html);
		
/*		Elements links = doc.select("a[href]"); //带有href属性的a元素
		Elements pngs = doc.select("img[src$=.png]");
		  //扩展名为.png的图片

		Element masthead = doc.select("div[class=\"masthead\"]").first();
		  //class等于masthead的div标签
		System.out.println(links.html());
		System.out.println(pngs.attr("src"));
		System.out.println(masthead.html());*/
		Elements resultLinks = doc.select("div > img:eq(1) "); //在h3元素之后的a元素
		for(Element ele:resultLinks){
			
			System.out.println(ele);
			
		}
		

	}
	
}
