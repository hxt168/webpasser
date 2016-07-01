/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: TagDelRule.java
 *   
 */
package com.hxt.webpasser.regular;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.hxt.webpasser.transport.xml.Rule;
import com.hxt.webpasser.utils.StringUtil;

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
public class TagDelRule implements DecideRule {

	public List<Object> handle(Rule rule, List<Object> contentList,Map valueMap) {
		List<String> tags=rule.getTags();
		if(contentList!=null)
		{

			for(int i=0;i<contentList.size();i++)
			{
				String con=(String) contentList.get(i);
				for(String tag:tags)
				{
					  String regTag = "<"+tag+"[^>]*?>[\\s\\S]*?<\\/"+tag+">";  // 定义tag的正则表达式
				      java.util.regex.Pattern pattern=Pattern.compile(regTag, Pattern.CASE_INSENSITIVE);      
				      java.util.regex.Matcher mTag  = pattern.matcher(con);      
				      con = mTag.replaceAll(""); // 过滤script标签      
					
				}
				contentList.set(i, con);
			}
				
		}

		return contentList;
	}

	
	public static void main(String[] args) {
		String htmlStr = "<html>deddddfff<div>2222</div><script src=\"2333\">dddddd</script><html>"; // 含html标签的字符串  
	    String textStr = "";      
        java.util.regex.Pattern p_script;      
        java.util.regex.Matcher m_script;      
        java.util.regex.Pattern p_style;      
        java.util.regex.Matcher m_style;      
        java.util.regex.Pattern p_html;      
        java.util.regex.Matcher m_html;      
    
        java.util.regex.Pattern p_html1;      
        java.util.regex.Matcher m_html1; 
	    String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>      
        String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>      
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式      
        String regEx_html1 = "<[^>]+";   
        
        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);      
        m_script = p_script.matcher(htmlStr);      
        htmlStr = m_script.replaceAll(""); // 过滤script标签      

        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);      
        m_style = p_style.matcher(htmlStr);      
        htmlStr = m_style.replaceAll(""); // 过滤style标签      

        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);      
        m_html = p_html.matcher(htmlStr);      
        htmlStr = m_html.replaceAll(""); // 过滤html标签      

        p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);      
        m_html1 = p_html1.matcher(htmlStr);      
        htmlStr = m_html1.replaceAll(""); // 过滤html标签      

        textStr = htmlStr;      
        
        System.out.println(textStr);
		
		
	}
	
	
	
	
	
}
