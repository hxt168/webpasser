/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: DefaultHtmlParser.java
 *   
 */
package com.hxt.webpasser.parser;

import java.util.List;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import com.hxt.webpasser.log.SpiderLogs;

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
public class VulgarHtmlParser {

	private static Log log = SpiderLogs.getLog(VulgarHtmlParser.class);
	
    //public URLObject object=null;

    public List parseLinksInDocument(URL sourceURL, String textContent)
    {
        return parseAsHTML(sourceURL, textContent);
    }

    private List parseAsHTML(URL sourceURL, String textContent)
    {
    	// log.debug("调用方法parseAsHTML()");
        ArrayList newURLs = new ArrayList();
        HashSet newURLSet = new HashSet();
       
        //对图形文件进行屏蔽
        //extractAttributesFromTags("img", "src", sourceURL, newURLs, newURLSet, textContent);
        
        extractAttributesFromTags("a", "href", sourceURL, newURLs, newURLSet, textContent);
        
        //对body标签中的背静图象进行屏蔽
        // extractAttributesFromTags("body", "background", sourceURL, newURLs, newURLSet, textContent);
        
        extractAttributesFromTags("frame", "src", sourceURL, newURLs, newURLSet, textContent);
       
        //对图形文件的下载进行屏蔽
        //extractAttributesFromTags("IMG", "SRC", sourceURL, newURLs, newURLSet, textContent);
       
        extractAttributesFromTags("A", "HREF", sourceURL, newURLs, newURLSet, textContent);
        
        //对body标签中的背静图象进行屏蔽
        //extractAttributesFromTags("BODY", "BACKGROUND", sourceURL, newURLs, newURLSet, textContent);
       
        //对框架标签的源地址属性进行提取
        extractAttributesFromTags("FRAME", "SRC", sourceURL, newURLs, newURLSet, textContent);

        if(newURLs.size() == 0)
        {
        //	log.debug("得到了0个URL地址，请核实网页文件是否有问题\n" + textContent);
        }
      //  log.debug("得到地址列表长度 " + newURLs.size() + " 从网页中得到的");
        return newURLs;
    }

    private void extractAttributesFromTags(String tag, String attr, URL sourceURL, List newURLs, Set newURLSet, String input)
    {
    	log.debug("调用方法：extractAttributesFromTags(" + tag + ", " + attr + ", ...)");

        int startPos = 0;
        //记得后面的空格标签的格式一定要正确
        String startTag = "<" + tag + " ";
        //对于连接属性的定义注意这里是有一个转义的字符"\"
        String attrStr = attr + "=\"";
        while(true)
        {	
        	//确定标签开始的位置
            int tagPos = input.indexOf(startTag, startPos);
            if(tagPos < 0)
            {
                return;
            }
            //判断此连接是否有属性标签
            int attrPos = input.indexOf(attrStr, tagPos + 1);
            if(attrPos < 0)
            {
                startPos = tagPos + 1; 
                continue;
            }
            
            int nextClosePos = input.indexOf(">", tagPos + 1);       
            if(attrPos < nextClosePos)
            {
                // 如果是这样的情况那就表示找到了一的链接标签，这对于爬虫来说是个很好的消息，注意其中的转移字符\"
                int closeQuotePos = input.indexOf("\"", attrPos + attrStr.length() + 1);
                if(closeQuotePos > 0)
                {
                    String urlStr = input.substring(attrPos + attrStr.length(), closeQuotePos);
                   
                    //添加一个
                    if(urlStr.indexOf('#') != -1)
                    {
                        urlStr = urlStr.substring(0, urlStr.indexOf('#'));
                    }
                    
                    //第二次屏蔽掉图片,好象是多余的步骤 下面已经过滤了不想要的地址信息了
                    
                    if(urlStr.indexOf(".zip")!=-1||urlStr.indexOf(".fla")!=-1
                    		||urlStr.indexOf(".mpeg")!=-1
                    		||urlStr.indexOf(".gif")!=-1
                    		||urlStr.indexOf(".jpg")!=-1
                    		)
                    {
                    	startPos = closeQuotePos+4;
                    	continue;
                    }
                    
                    //当下载的网页没有后缀名的时候就在起后加上.html的后缀
                     
                    if(!urlStr.endsWith(".html") && 
                    		!urlStr.endsWith(".htm") &&
                    		!urlStr.endsWith(".shtml") &&                 		
                            !urlStr.endsWith(".xml") 
                            &&!urlStr.endsWith(".jsp")
                            &&!urlStr.endsWith(".asp")
                            &&!urlStr.endsWith("/"))
                         {
                           startPos = closeQuotePos+4;
                           continue;
                          }
                    
                    
                    
                    //如果不是下面3种文件格式就跳过去不进行下载
                   /* 
                   if(urlStr.indexOf(".html")==0||urlStr.indexOf("htm")==0
                      ||urlStr.indexOf("shtml")==0||urlStr.indexOf("jsp")==0
                      ||urlStr.indexOf("xml")==0)
                   {
                    	continue;
                    }
					*/
                    if(isMailTo(urlStr))
                    {
                      //  logMailURL(urlStr);
                    }
                    else
                    {
                        try
                        {
                        	/*
                        	 * *****************************************************************************************
                        	 * URL(URL context,String spec)
							 * throws MalformedURLException通过在指定的上下文中对给定的 spec 进行解析创建
							 *URL。 新的 URL 从给定的上下文 URL 和 spec参数创建，这在以下文档中进行了描述：
							 *RFC2396 "Uniform Resource Identifiers :Generic * Syntax":<scheme>://<authority>
							 *<path>?<query>#<fragment>
							 * 该引用被解析为方案、授权、路径、查询和片段部分。如果路径部分为空，方案、授权和
							 *查询部分未定义，则新的 URL 为对当前文档的引用。否则，新的 URL 中将使用 spec
							 *中出现的片段和查询部分。如果给定的 spec 中定义了方案部分，但与上下文的方案不匹配，
							 *则只根据 spec创建新的绝对 URL。否则，方案部分从上下文 URL 继承。如果 spec 中有授
							 *权部分，则将该 spec 视为绝对的，并用 spec的授权和路径替换上下文授权和路径。如果 
							 *spec 中没有授权部分，则新的 URL的授权将从上下文继承。如果 spec 的路径部分以斜线
							 *字符 "/" 开始，则将该路径视为绝对的，并用 spec的路径替换上下文路径。否则，如 RFC2396
							 *中所述，该路径将被视为相对路径，并被添加到上下文路径中。此外，在这种情况下，还将
							 *通过删除由 ".." 和 "." 产生的目录更改对路径进行规范化处理。有关 URL 解析的更详细的描述,
							 *请参考 RFC2396。
                        	 * 
                        	 * 
                        	 * 这个就URL类可以自动判断是不是继承上下问的关系。
                        	 * *************************************************************************************
                        	 * */
                            URL u = new URL(sourceURL, urlStr);
                           
                            if(newURLSet.contains(u))
                            {
                            	log.debug("已经存在的URL地址: " + u);
                            }
                            else
                            {
                                newURLs.add(u);
                                newURLSet.add(u);
                                log.debug("发现新的URL地址: " + u);
                            }
                        }
                        catch(MalformedURLException murle)
                        {
                        }
                    }
                }
                startPos = tagPos + 1;
                continue;
            }
            else
            {
                startPos = tagPos + 1;
                continue;
            }
        }
    }

/*    private void logMailURL(String url)
    {
    	log.debug("存入邮件地址信息");

        try
        {
            FileWriter appendedFile = new FileWriter(config.getMailtoLogFile().toString(), true);
            PrintWriter pW = new PrintWriter(appendedFile);
            pW.println(url);
            pW.flush();
            pW.close();
        }
        catch(IOException ioe)
        {
        	log.debug("写邮件地址信息发生异常:" + ioe.getMessage());
        }
    }
*/
   
    private boolean isMailTo(String url)
    {
        if(url == null)
        {
            return false;
        }

        url = url.toUpperCase();
        return (url.indexOf("MAILTO:") != -1);
    }
	
	
	
}
