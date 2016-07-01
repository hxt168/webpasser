/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: RegexRule.java
 *   
 */
package com.hxt.webpasser.regular;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class RegexRule implements DecideRule,AcceptRule{

	public List<Object> handle(Rule rule, List<Object> contentList,Map valueMap) {
		
		String reg=rule.getValue();
		if(contentList!=null)
		{
			List<Object> contentNewList=new ArrayList<Object>();
			for(Object con:contentList)
			{
				List<String> contentL=null;
				if("inner".equals(rule.getExp())){
					contentL=PatternUtils.patternListOne((String) con, reg);
					
					
				}else{
					contentL=PatternUtils.patternListOneContainOut((String) con, reg);
					
				}
				contentNewList.addAll(contentL);
				return contentNewList;
			}
				
		}

		return contentList;
	}

	public boolean isAccept(Rule rule, String url) {

		String reg=rule.getValue();
		Pattern pa=Pattern.compile(reg);
		Matcher m= pa.matcher(url);

		if(m.find())
		{
			return true;
		}
		
		return false;
	}

	
	
	
}
