/*
 * 系统名称: 
 * 模块名称: webpasser.common
 * 类 名 称: HtmlUtil.java
 *   
 */
package com.hxt.webpasser.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class HtmlUtil {

	public static String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式      
	
	public static String getTextContent(String content){
		if(content!=null){  
		 
		   Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);      
		   Matcher m_html = p_html.matcher(content);      
		   content = m_html.replaceAll(""); // 过滤html标签      
	        
	       return content;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
		String htmlStr = "<html>deddddfff<div>2222</div><script src=\"2333\">dddddd</script><html>"; // 含html标签的字符串  
		String con=getTextContent(htmlStr);
		System.out.println(con);
		
	}
	
}
