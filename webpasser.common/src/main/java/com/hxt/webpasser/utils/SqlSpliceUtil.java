package com.hxt.webpasser.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SqlSpliceUtil {

	
	/**
	 * sql参数转换，得到sql所需value值，如字符串要两边加',int字符不用加
	 * @param val
	 * @return
	 */
	public static String getSqlValueByType(Object val)
	{
		if(val==null)
			return null;
		if(val instanceof Integer)
		{
			return ""+val;
		}else if(val instanceof Timestamp )
		{
			Timestamp time= (Timestamp) val;
			 Date date = new Date(time.getTime());
			  
			return "'"+date2StrDefault(date)+"'";
			
		}else if(val instanceof Date )
		{
			Date date =(Date) val;
			return "'"+date2StrDefault(date)+"'";
		}else if(val instanceof byte[] )
		{
			byte[] bytes=(byte[])val;
			String str=new String(bytes);
			return "'"+str+"'";
		}else{
			
			String tran=transactSQLInjection(""+val);
			return "'"+tran+"'";
		}
		
		
	}
	
	/**
	 * 过滤sql的 ' ; --
	 * @param str
	 * @return
	 */
	public static String transactSQLInjection(String str)
	{
		if(str==null)
		{
			return null;
		}
		
	    return str.replaceAll(".*([';]+|(--)+).*", " ");
	}
	
	
	
	/**
	 * date转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		  if (null == date) {
		   return null;
		  }
		  SimpleDateFormat sdf = new SimpleDateFormat(format);
		  return sdf.format(date);
	}
	
	/**
	 * date转字符串，默认格式yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String date2StrDefault(Date date)
	{
		String format="yyyy-MM-dd HH:mm:ss";	
		return date2Str(date,format);
	}
	
}
