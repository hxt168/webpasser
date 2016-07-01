/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: VerifyValueUtil.java
 *   
 */
package com.hxt.webpasser.utils;
/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class PropertyValueUtil {

	
	/**
	 * 字符串转数字
	 * @param str
	 * @return
	 */
	public static Integer getIntNumByStr(String str)
	{
		if(StringUtil.isEmpty(str))
		{
			return null;
		}

		 try {
			Integer num=Integer.parseInt(str);
			return num;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		 return null;
	}
	
    /**
     * 获得配置的int值，如果对应没有设值，则为默认值
     * @param propertValue
     * @param defaultValue
     * @return
     */
    public static Integer getPropertyIntegerValue(String propertValue,Integer defaultValue)
    {
    	Integer num=getIntNumByStr(propertValue);
    	if(num==null)
    	{
    		return defaultValue;
    	}
    	
    	return num;
    }
    
	
    /**
     * 获得配置的值，如果对应没有设值，则为默认值
     * @param propertValue
     * @param defaultValue
     * @return
     */
    public static String getPropertyStringValue(String propertValue,String defaultValue)
    {
    	if(StringUtil.isEmpty(propertValue))
    	{
    		return defaultValue;
    	}
    	
    	return propertValue;
    }
	
}
