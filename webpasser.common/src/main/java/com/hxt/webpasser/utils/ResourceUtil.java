
package com.hxt.webpasser.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-2-27 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class ResourceUtil {

	
	private static String rootPath=null;
	/**
	 * 获取资源路径
	 */
	public static String getResourcePath()
	{
		
		if(rootPath==null)
		{
			try {
				URL url=ResourceUtil.class.getResource("/");
				
				rootPath=url.getPath();
				System.out.println("获得资源路径："+rootPath);
			} catch (Exception e) {
				
				System.out.println("获取资源路径发送错误！使用resources文件夹");
				//e.printStackTrace();
				rootPath="resources/";
				
			}
		}
		
		return rootPath;
		
	}
	
	/**
	 * 
	 * @param resourceName  要加 /
	 * @return
	 */
	public static InputStream getResourceStream(String resourceName)
	{
		
		InputStream in=null;
		try {
			in = ResourceUtil.class.getResourceAsStream(resourceName);
		} catch (NullPointerException e) {
			String path=getResourcePath()+resourceName;
			
			try {
				in=new FileInputStream(new File(path));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return in;
	}
	
}
