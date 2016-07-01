/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: CutRule.java
 *   
 */
package com.hxt.webpasser.regular;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class CutRule  implements DecideRule{

	public List<Object> handle(Rule rule, List<Object> contentList,Map valueMap) {
		
		String pre=rule.getPre();
		String end=rule.getEnd();
		if(contentList!=null)
		{

			for(int i=0;i<contentList.size();i++)
			{
				String newCon=StringUtil.cutNotContainStartAndEnd((String) contentList.get(i), pre, end);
				contentList.set(i, newCon);
			}
				
		}

		return contentList;
	}

}
