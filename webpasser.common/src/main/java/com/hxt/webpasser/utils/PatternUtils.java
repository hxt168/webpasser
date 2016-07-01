
package com.hxt.webpasser.utils;
/**
 * 功能说明: 正则工具类 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong<br>
 * 开发时间: 2015-1-16 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatternUtils {

	/**
	 * 正则表达 ，包含外面
	 * @param con
	 * @param reg
	 * @return
	 */
	public static String patternOneContainOut(String con,String reg)
	{
		Pattern pa=Pattern.compile(reg);
		Matcher m= pa.matcher(con);
		if(m.find())
		{
			return m.group(0).trim();
		}
		return "";
	}
	
	/**
	 * 正则表达 ，不包含外面
	 * @param con
	 * @param reg
	 * @return
	 */
	public static String patternOneNotContain(String con,String reg)
	{
		Pattern pa=Pattern.compile(reg);
		Matcher m= pa.matcher(con);
		if(m.find())
		{
			return m.group(1).trim();
		}
		return "";
	}
	
	public static List<String> patternListOne(String con,String reg)
	{
		Pattern pa=Pattern.compile(reg);
		Matcher m= pa.matcher(con);
		List<String> list=new ArrayList<String>();
		while(m.find())
		{
			list.add(m.group(1).trim());
		}
		return list;
	}
	
	public static List<String> patternListOneContainOut(String con,String reg)
	{
		Pattern pa=Pattern.compile(reg);
		Matcher m= pa.matcher(con);
		List<String> list=new ArrayList<String>();
		while(m.find())
		{
			if(m.group(0)!=null)
			{
				list.add(m.group(0).trim());
			}
		
		}
		return list;
	}
	
	public static void main(String[] args) {
		
		/*String con="<dataset sql=\" select * from oa_send_document where wf_id='param(\"wf_id\")'\"></dataset>"+
		"<dataset sql=\" select * from oa_send_document where wf_id='param(\"ids\")'\"></dataset>";
		
		String req="param\\(\"([a-zA-Z0-9_]*)\"\\)";
		List<String> list=patternListOne(con, req);
		Iterator<String> it=list.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}*/
		
		String con="[年份]sdds[序列4d]dsds";
		String reg="\\[序列(\\d)d\\]";
/*		String s=patternOneNotContain( con,reg);
		System.out.println(s);*/
		
		List<String> list=patternListOneContainOut(con, reg);
		Iterator<String> it=list.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
	}
}
