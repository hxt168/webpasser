/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: TextRule.java
 *   
 */
package com.hxt.webpasser.regular;

import java.util.List;
import java.util.Map;

import com.hxt.webpasser.parser.common.JsoupSelector;
import com.hxt.webpasser.transport.xml.Rule;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class ToTextRule implements DecideRule{

	public List<Object> handle(Rule rule, List<Object> contentList, Map valueMap) {
		//String val=rule.getValue();
		if(contentList!=null)
		{
			for(int i=0;i<contentList.size();i++)
			{
				String con=(String) contentList.get(i);
				con=JsoupSelector.getTextFromHtml(con);
				contentList.set(i, con);
			}	
		}

		return contentList;
	}

}
