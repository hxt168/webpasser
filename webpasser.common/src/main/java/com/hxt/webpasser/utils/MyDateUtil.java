package com.hxt.webpasser.utils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtil {
	
	public static String getCurrentTime(String pattern)
	{
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		
		return sdf.format(new Date());
	}
	
	public static String getTimeString(String pattern,long time)
	{
		if(time==0){
			return "";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		
		return sdf.format(new Date(time));
	}
	
	public static String getCurrentTime()
	{
		return getCurrentTime("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getTimeString(long time)
	{
		return getTimeString("yyyy-MM-dd HH:mm:ss",time);
	}
	
	//这种形式变为分钟的  01:53:21
	public static int getTimeToMinutes(String time)
	{
		int minu=0;
		String[] minuArr=time.split(":");
		if(minuArr.length>1)
		{
			minu=Integer.parseInt(minuArr[0])*60+Integer.parseInt(minuArr[1]);
		}
		return minu;
	}
	
	public static void main(String[] args) {
		//new Data
		//new Date().getTime()
		String pattern="yyyyMMdd";
		String pat="20140212";
		SimpleDateFormat dd=new SimpleDateFormat(pattern);
		try {
			Date sss=dd.parse(pat);
			System.out.println(sss);
			System.out.println(sss.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(new Date().getTime());
		long now=new Date().getTime();
		String str="1392105975387";
		BigInteger big=new BigInteger(str);
		BigInteger big2=new BigInteger("923230792");
		BigInteger add=big.add(big2);
		System.out.println(add);
		//long s=(long)(1392105975387)+(long)(923230792);
		
		
		String all="1393029206179";
		BigInteger bigAll=new BigInteger(all);
		Long da=new Date().getTime();
		
		BigInteger nowtime=new BigInteger(da.toString());
		System.out.println("------\n"+nowtime);
		BigInteger sub=bigAll.subtract(nowtime);
		System.out.println(sub);
		
	}
}
