/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: FillParamValueUtil.java
 *   
 */
package com.hxt.webpasser.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class FillParamValueUtil {

	public static String fillParamValue(String con,String reqval,Map valueMap){
		String orgCon=con;
		List<String> reqFields= getReqFields( reqval);
		for(String reqField:reqFields)
		{
			if("this".equals(reqField))  //替换上一步的内容
			{
				reqval=reqval.replace("[$this]", orgCon);
			}else if(valueMap!=null&&valueMap.containsKey(reqField))
			{
				reqval=reqval.replace("[$"+reqField+"]", valueMap.get(reqField)+"");
			}
		}
		return reqval;
	}
	
	/**
	 * 提取需要赋值的字段
	 * @param relateContent
	 * @return
	 */
	protected static List<String> getReqFields(String relateContent)
	{
		List<String> reqItems=new ArrayList<String>();
		if(relateContent!=null)
		{
			String req="\\[\\$([a-zA-Z0-9_]*)\\]";
			List<String> list=PatternUtils.patternListOne(relateContent, req);
			Iterator<String> it=list.iterator();
			while(it.hasNext())
			{
				
				reqItems.add(it.next());
			}
		}
	
		return reqItems;
	}
}
