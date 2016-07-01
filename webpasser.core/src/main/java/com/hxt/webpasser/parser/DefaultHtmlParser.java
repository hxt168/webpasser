/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: DefaultHtmlParser.java
 *   
 */
package com.hxt.webpasser.parser;
import java.util.HashSet;
import java.util.Set;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.parser.filter.LinkFilter;
import com.hxt.webpasser.spider.common.CrawlUrlUtil;
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
public class DefaultHtmlParser implements HtmlParser {

	// 获取一个网站上的链接,filter 用来过滤链接
		public Set<String> extracLinks(PageResult pageResult, LinkFilter filter) {
			//String url=crawlUrl.getOriUrl();
			Set<String> links = new HashSet<String>();
			try {
				Parser parser = new Parser(pageResult.getContent());
				
				parser.setEncoding(pageResult.getCharSet());
				
				// 过滤 <frame >标签的 filter，用来提取 frame 标签里的 src 属性所表示的链接
				NodeFilter frameFilter = new NodeFilter() {
					public boolean accept(Node node) {
						if (node.getText().startsWith("frame src=")) {
							return true;
						} else {
							return false;
						}
					}
				};
				// OrFilter 来设置过滤 <a> 标签，和 <frame> 标签
				OrFilter linkFilter = new OrFilter(new NodeClassFilter(
						LinkTag.class), frameFilter);
				// 得到所有经过过滤的标签
				NodeList list = parser.extractAllNodesThatMatch(linkFilter);
				for (int i = 0; i < list.size(); i++) {
					Node tag = list.elementAt(i);
					if (tag instanceof LinkTag)// <a> 标签
					{
						LinkTag link = (LinkTag) tag;
						String linkUrl = link.getLink();// url
						if (filter.accept(linkUrl))
						{
							//CrawlUrl crawlUrl=CrawlUrlUtil.getCrawlUrlByUrl(linkUrl);
							links.add(linkUrl);
						}
							
					} else// <frame> 标签
					{
						// 提取 frame 里 src 属性的链接如 <frame src="test.html"/>
						String frame = tag.getText();
						int start = frame.indexOf("src=");
						frame = frame.substring(start);
						int end = frame.indexOf(" ");
						if (end == -1)
							end = frame.indexOf(">");
						String frameUrl = frame.substring(5, end - 1);
						if (filter.accept(frameUrl))
						{
							//CrawlUrl crawlUrl=CrawlUrlUtil.getCrawlUrlByUrl(frameUrl);
							links.add(frameUrl);
						}
							
					}
				}
			} catch (ParserException e) {
				e.printStackTrace();
			}
			return links;
		}
	
		
}
