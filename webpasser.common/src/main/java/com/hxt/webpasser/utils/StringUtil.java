/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: StringUtil.java
 *   
 */
package com.hxt.webpasser.utils;

import java.util.List;
import java.util.Random;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-14 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class StringUtil {

	public static String cutContainStartAndEnd(String con,String start,String end)
	{
		int startNum=con.indexOf(start);
		if(startNum==-1)
		{
			return "";
		}
		int endNum=con.indexOf(end, startNum+start.length());
		return con.substring(startNum, endNum+end.length());
	}
	
	public static String cutNotContainStartAndEnd(String con,String start,String end)
	{
		int startNum=0;
		if(!"".equals(start)){
			
			startNum=con.indexOf(start);
		}
		
		if(startNum==-1)
		{
			return "";
		}
		int endNum=con.indexOf(end, startNum+start.length());
		if("".equals(end)){
			return con.substring(startNum+start.length());
		}else if(endNum==-1)
		{
			return con.substring(startNum+start.length());
		}
		return con.substring(startNum+start.length(), endNum);
	}
	
	public static String cutByEndToStart(String con,String start,String end)
	{
		int endPositon=con.indexOf(end);
		if(endPositon!=-1)
		{
			String startStr=con.substring(0, endPositon);
			int startPosition=startStr.lastIndexOf(start);
			if(startPosition!=-1)
			{
				String cutStr=startStr.substring(startPosition+start.length());
				return cutStr;
			}
			
			
		}
		return "";
	}
	
	public static String[] ListToStringArray(List<String> list)
	{
		String[] strArray=new String[list.size()];
		for(int i=0;i<list.size();i++)
		{
			strArray[i]=list.get(i);
		}
		return strArray;
	}
	
	public static String getRandNum(int len)
	{
		StringBuilder str=new StringBuilder();
		Random d=new Random(2);
		for(int i=0;i<len;i++)
		{
			//System.out.println(d.nextInt(10));
			str.append(d.nextInt(10)+"");
		}
		return str.toString();
	}
	
	public static boolean isEmpty(String str)
	{
		if(str!=null&&!"".equals(str))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 获得不是null的值
	 * @param con
	 * @return
	 */
	public static String getNonBlankString(String con)
	{
		if(con==null)
		{
			return "";
		}
		return con;
	}
	
}
