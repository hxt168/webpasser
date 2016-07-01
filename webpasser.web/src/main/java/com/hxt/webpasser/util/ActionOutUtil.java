/*
 * 系统名称: eden 1.0
 * 模块名称: eden.oa
 * 类 名 称: ActionOutUtil.java
 * 软件版权: 浙江榕基信息技术有限公司
 *   
 */
package com.hxt.webpasser.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 功能说明: action里直接显示内容工具类 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-1-27 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class ActionOutUtil {

	
	/**
	 * 输出
	 * @param outStr
	 */
	public static  void out(String outStr,HttpServletResponse response)
	{
		
		HttpServletResponse res=response;
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out=res.getWriter();
			out.write(outStr);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 重定向
	 * @param outStr
	 * @param response
	 */
	public static  void sendRedirect(String link,HttpServletResponse response)
	{
		
		try {
			 response.sendRedirect(link);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out("",response);
		
	}
	
	
	
}
