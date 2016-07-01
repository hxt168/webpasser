/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SplitRule.java
 *   
 */
package com.hxt.webpasser.regular;

import java.util.List;
import java.util.Map;

import com.hxt.webpasser.transport.xml.Rule;

/**
 * 功能说明: 拆分rule <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SplitRule implements DecideRule{

	public List<Object> handle(Rule rule, List<Object> contentList, Map valueMap) {

		if(rule.getParams()!=null&&rule.getParams().size()>0)
		{
			String splitMark=rule.getParams().get(0).getValue();
			for(int i=0;i<contentList.size();i++)
			{
				String con=(String) contentList.get(i);
				String[] conArr=con.split(splitMark);
				contentList.set(i, conArr);
			}
			
		}
		return contentList;
	}

}
