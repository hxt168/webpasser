/*
 * 系统名称: 
 * 模块名称: webpasser.common
 * 类 名 称: TrimUtil.java
 *   
 */
package com.hxt.webpasser.utils;

import java.util.List;
import java.util.Map;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class TrimUtil {

	public static void trimMap(Map<String,Object> returnValue){
		if(returnValue!=null){
			for(Map.Entry<String,Object> entry:returnValue.entrySet()){
				Object value=entry.getValue();
				if(value!=null){
					if(value instanceof String){
						String valueString=(String) value;
						String tempValue=trimStr(valueString);
						if(valueString.length()!=tempValue.length()){
							returnValue.put(entry.getKey(), tempValue);
						}
					}
					else if(value instanceof List){
						trimList((List)value);
					}
					else if(value instanceof Map){
						trimMap((Map)value);
					}
					
				}
				
				
			}
			
		}
		
	}
	
	public static void trimList(List returnList){
		if(returnList!=null&&returnList.size()>0){
			for(int i=0;i<returnList.size();i++){
				Object value=returnList.get(i);
				if(value!=null){
					if(value instanceof String){
						String valueString=(String) value;
						String tempValue=trimStr(valueString);
						if(valueString.length()!=tempValue.length()){
							returnList.set(i, tempValue);
						}
					}
					else if(value instanceof List){
						trimList((List)value);
					}
					else if(value instanceof Map){
						trimMap((Map)value);
					}
				}
				
				
			}
			
		}
		
	}
	
	public static String trimStr(String str){
		if(StringUtil.isEmpty(str)){
			return "";
		}
		String temp=str.trim();
		if(StringUtil.isEmpty(temp)){
			return "";
		}
		temp=trim(temp,'　');
		return temp;
	}
	
	
	public static String trim(String source, char c){
		if(source==null||source.length()==0){
			return source;
		}
        String beTrim = String.valueOf(c);
        source = source.trim(); // 循环去掉字符串首的beTrim字符 
        if(source.length()==0){
        	return source;
        }
        String beginChar = source.substring(0, 1);  
        while (beginChar.equalsIgnoreCase(beTrim)) {  
            source = source.substring(1, source.length()); 
            if(source.length()==0){
            	return source;
            }
            beginChar = source.substring(0, 1);  
        }  
        if(source.length()==0){
        	return source;
        }
       // 循环去掉字符串尾的beTrim字符  
       String endChar = source.substring(source.length() - 1, source.length());  
       while (endChar.equalsIgnoreCase(beTrim)) {  
            source = source.substring(0, source.length() - 1);  
            endChar = source.substring(source.length() - 1, source.length());  
       }  
       return source;  
	}
	
	
	public static void main(String[] args) {
		
		String nameStr="　　　　　　查德·迈克尔·墨瑞 Chad Michael Murray";
		String s=trimStr(nameStr);
		System.out.println(s);
		
	}
	
}
