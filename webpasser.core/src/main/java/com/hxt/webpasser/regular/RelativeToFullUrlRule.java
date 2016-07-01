/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: RelativeToFullUrlRule.java
 *   
 */
package com.hxt.webpasser.regular;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.hxt.webpasser.transport.xml.Rule;
import com.hxt.webpasser.utils.FillParamValueUtil;

/**
 * 功能说明: 相对路径转全路径url，对于如 index.html 转成   http://www.baidu.com/index.html <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class RelativeToFullUrlRule implements DecideRule{

	public List<Object> handle(Rule rule, List<Object> contentList, Map valueMap) {

		String val=rule.getValue();
		if(contentList!=null)
		{
			try {
				for(int i=0;i<contentList.size();i++)
				{
					String relativeUrl=(String) contentList.get(i);
					if(relativeUrl.indexOf("javascript")>-1){
						continue;
					}
					String fetchUrl=(String) valueMap.get("fetchUrl");
					URL absoluteUrl = new URL(fetchUrl); 
					URL parseUrl = new URL(absoluteUrl ,relativeUrl );  
					contentList.set(i, parseUrl.toString());
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}	
		}

		return contentList;
	}

	
	
	
}
