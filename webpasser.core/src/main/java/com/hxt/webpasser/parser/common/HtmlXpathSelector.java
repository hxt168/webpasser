/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: XpathSelector.java
 *   
 */
package com.hxt.webpasser.parser.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;  
import org.htmlcleaner.TagNode;  
import org.htmlcleaner.XPatherException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-9-1 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class HtmlXpathSelector {

	private XPath xPath ;
	private Document rootDocument;
	
	//private boolean isHtml=true;  // 否则是xml
	
	private String encode="gb2312"; 
	
	public HtmlXpathSelector(String content) throws ParserConfigurationException, SAXException, IOException
	{

		HtmlCleaner htmlCleaner = new HtmlCleaner();  
		TagNode rootTagNode = htmlCleaner.clean(content);  
		rootDocument = new DomSerializer(new CleanerProperties()).createDOM(rootTagNode);
		xPath=XPathFactory.newInstance().newXPath();
		
	}
	

	public Node evaluateXPath(String xpathExp) throws XPatherException, XPathExpressionException
	{
	
		NodeList nodeList = evaluateListXPath(xpathExp);
		if(nodeList.getLength()>0)
		{
			return nodeList.item(0);
		}

	   return null;
	}
	

	public NodeList evaluateListXPath(String xpathExp) throws XPatherException, XPathExpressionException
	{
		Object result= xPath.evaluate(xpathExp, rootDocument, XPathConstants.NODESET);
		
		if (result instanceof NodeList) {
			NodeList nodeList = (NodeList) result;
			return nodeList;
		}
	   return null;
	}
	
	
	   /**  
 
  /**'
   * 获取一个Transformer对象，由于使用时都做相同的初始化，所以提取出来作为公共方法。  
   * @param encode gbk gb2312 utf-8
   * @param method  xml  html  text
   * @return
   */
    public static Transformer newTransformer(String encode,String method) {  
        try {  
            Transformer transformer = TransformerFactory.newInstance()  
                    .newTransformer();  
            Properties properties = transformer.getOutputProperties();  
           // properties.setProperty(OutputKeys.ENCODING, encode);
            properties.setProperty(OutputKeys.METHOD, method);  
 /*           properties.setProperty(OutputKeys.VERSION, "1.0");  
            properties.setProperty(OutputKeys.INDENT, "no");  */
            transformer.setOutputProperties(properties);  
            return transformer;  
        } catch (TransformerConfigurationException tce) {  
            throw new RuntimeException(tce.getMessage());  
        }  
    }  
    

	
	public String getHtml(Node node) throws TransformerException
	{
		DOMSource source = new DOMSource(); 
		source.setNode(node); 
		
		 StringWriter strWtr = new StringWriter();  
		StreamResult results = new StreamResult(strWtr); 
		String method="html";
	/*	if(!isHtml)
		{
			method="xml";
		}*/
	     Transformer transformer = newTransformer(encode, method);
	     transformer.transform(source, results);
		return strWtr.toString();
		
	}
	
	
	public static String getAttribute(final Object node, final String attrName) {
		String result = "";
		if ((node != null) && (node instanceof Node)) {
			if (((Node) node).getNodeType() == Node.ELEMENT_NODE) {
				result = ((Element) node).getAttribute(attrName);
			} else {
				// 遍历整个xml某节点指定的属性
				NamedNodeMap attrs = ((Node) node).getAttributes();
				if ((attrs.getLength() > 0) && (attrs != null)) {
					Node attr = attrs.getNamedItem(attrName);
					result = attr.getNodeValue();
				}
			}
		}
		return result; 
	}
	
	public static void main(String[] args) throws XPatherException, XPathExpressionException, ParserConfigurationException, TransformerException, SAXException, IOException {
		
		 //String contents ="<html><a href='#'>aaaa</a>dd <img src=\" 你好.png\" /> <div class=\"masthead\" value=\"22\">eeeee<a>33223</a></div> <html>";  
		 String con="<html><head><title>劫匪在线观看-劫匪迅雷下载-动作片-迅播影院</title><meta name=\"keywords\" content=\"劫匪全集,劫匪迅雷下载\" /><meta name=\"description\" content=\"迅播影院提供《劫匪》在线点播与下载，我们提供《劫匪》的迅雷看看点播及百度影音播放，如果您觉得动作片劫匪还不错就分享给您身边的每一位朋友吧。\" /><link href=\"http://statics.joy3g.com/2tu/css/style.css?v201506\" type=\"text/css\" rel=\"stylesheet\"><meta http-equiv=\"x-ua-compatible\" content=\"ie=7\" /><script>var sitePath=''</script><script type=\"text/javascript\" src=\"http://statics.joy3g.com/2tu/static/js/jquery-1.7.1.min.js?v201406\"></script><script type=\"text/javascript\" src=\"http://statics.joy3g.com/2tu/static/js/common.js?v201406\"></script><script type=\"text/javascript\" src=\"http://statics.joy3g.com/2tu/common.js?v201406\"></script><script src=\"http://statics.joy3g.com/2tu/function.js?v20140602\"></script><script type=\"text/javascript\" src=\"http://statics.joy3g.com/2tu/function-1.0.3.js?v20140614\"></script></head><body class=\"channel1\"><div id=\"header\"><div class=\"head\"><div class=\"top\"><span>欢迎来到迅播影院，我们为大家免费提供好看的电影</span><p><a href=\"javascript:void(0);\" onClick=\"setHome(this,'http://www.2tu.cc')\">设为首页</a> - <a href=\"javascript:void(0)\" onClick=\"addFavorite('http://www.2tu.cc','迅播影院');\">加入收藏</a> - <a href=\"/gbook.asp\" target=\"_blank\" class=\"wp\">留言求片</a> - <a href=\"/rss.xml\" target=\"_blank\">RSS订阅</a> - <a href=\"/shorturl.asp\" target=\"_blank\" class=\"desk\">将迅播放到桌面</a></p></div><div class=\"logo\"><a href=\"http://www.xiamp4.com\" title=\"首页\">首页</a></div><p class=\"plus\"><a href=\"/top/toplist.html\" class=\"ph\">排行榜</a><a href=\"/search.asp\" class=\"dq\">影视大全</a></p><div id=\"search\"><div class=\"ser\"><form name=\"formsearch\" id=\"formsearch\" action='/search.asp' method=\"post\" target=\"_blank\"><input type=\"text\" id=\"kw\" name=\"searchword\" class=\"search-input\" value=\"请在此处输入影片片名或演员名称。\" onFocus=\"if(this.value=='请在此处输入影片片名或演员名称。'){this.value='';}\" onBlur=\"if(this.value==''){this.value='请在此处输入影片片名或演员名称。';};\"/><input type=\"submit\" name=\"submit\" class=\"sub\" value=\"搜 索\" /></form></div><p><a href=\"/search.asp?searchword=%C9%B1%C6%C6%C0%C72\">杀破狼2</a><a href=\"/search.asp?searchword=%C5%AE%BC%E4%B5%FD\">女间谍</a><a href=\"/search.asp?searchword=%D9%AA%C2%DE%BC%CD%CA%C0%BD%E7\">侏罗纪世界</a><a href=\"/search.asp?searchword=%B7%B4%BB%F7\">反击</a><a href=\"/search.asp?searchword=%C4%A9%CA%C0%D6%AE%D6%DB\">末世之舟</a><a href=\"/search.asp?searchword=%B9%ED%CD%AC%C4%E3OT\">鬼同你OT</a></p></div><div class=\"history\"><a href=\"#\" class=\"gk\">播放记录</a><div class=\"drop-box\"><div class=\"lookedlist\"><p><a class=\"closehis\" href=\"javascript:;\">关闭</a><a href=\"javascript:void(0);\" id=\"emptybt\" onClick=\"javascript:setEmpty()\">清空全部播放记录</a></p><ul class=\"highlight\" id=\"playhistory\"><li>您的观看历史为空。</li></ul></div><script type=\"text/javascript\" language=\"javascript\">ingetCookie();</script></div></div><div id=\"menu\"><div class=\"m\"><p><a href=\"http://www.xiamp4.com\" class=\"curr\">首页</a><a href=\"/GvodHtml/dy.html\" class=\"menu15\">电影</a><a href=\"/GvodHtml/tv.html\" class=\"menu16\">电视</a><a href=\"/GvodHtml/7.html\" class=\"menu7\">动画片</a><a href=\"/GvodHtml/8.html\" class=\"menu8\">综艺片</a><a href=\"/GvodHtml/21.html\" class=\"menu21\">3D电影</a><a href=\"/GvodHtml/18.html\" class=\"menu18\">影视预告</a></p><span><a href=\"http://www.qucai.com/\" target=\"_blank\">彩票购买</a><a href=\"/top/toplist.html\">排行榜</a><a href=\"/top/lastupdate.html\">最近更新</a></span></div><p class=\"s\">热门分类： <a href=\"/GvodHtml/1.html\">动作片</a><a href=\"/GvodHtml/2.html\">喜剧片</a><a href=\"/GvodHtml/3.html\">爱情片</a><a href=\"/GvodHtml/4.html\">科幻片</a><a href=\"/GvodHtml/5.html\">恐怖片</a><a href=\"/GvodHtml/6.html\">剧情片</a><a href=\"/GvodHtml/13.html\">战争片</a><a href=\"/GvodHtml/14.html\">其它片</a><a href=\"/GvodHtml/9.html\">国产剧</a><a href=\"/GvodHtml/10.html\">港台剧</a><a href=\"/GvodHtml/11.html\">欧美剧</a><a href=\"/GvodHtml/12.html\">日韩剧</a><a href=\"/GvodHtml/17.html\">新马泰</a></p></div></div></div><!--/header--><!--/header--><script src=\"http://statics.joy3g.com/xflib2.0.js\" type=\"text/javascript\" language=\"javascript\"></script><script src=\"http://s.miwifi.com/d2r/js/base64.js\"></script><div id=\"main\"><div class=\"banner\"><script type=\"text/javascript\" language=\"javascript\" src=\"http://statics.joy3g.com/2tu/ads/content1.js\"></script></div><div class=\"view\"><div class=\"wz\">当前位置: <a href='/'>首页</a>&nbsp;&nbsp;&raquo;&nbsp;&nbsp;<a href='/GvodHtml/15.html' >电影</a>&nbsp;&nbsp;&raquo;&nbsp;&nbsp;<a href='/GvodHtml/1.html'>动作片</a>&nbsp;&nbsp;&raquo;&nbsp;&nbsp;劫匪 </div><div class=\"pic\"><img src=\"http://tu.joy3g.com/20150916165604327.jpg\" alt=\"劫匪\" /><p><a href=\"#kan\">立即播放</a></p></div><div class=\"info\"><h1>劫匪1280超清迅雷下载</h1><ul><li><span>上映年代：</span>2010&#160;&#160;<span>状态：</span>全集</li><li><span>类型：</span><a href=\"/search.asp?k=犯罪&t=1\" target=\"_blank\">犯罪</a> <a href=\"/search.asp?k=惊悚&t=1\" target=\"_blank\">惊悚</a> <a href=\"/search.asp?k=动作&t=1\" target=\"_blank\">动作</a> </li><li><span>主演：</span><a href=\"/search.asp?searchword=%D2%C1%B5%C2%C8%F0%CB%B9%A1%A4%B0%AC%B6%FB%B0%CD\">伊德瑞斯·艾尔巴</a>&nbsp;&nbsp;<a href=\"/search.asp?searchword=%B1%A3%C2%DE%A1%A4%CE%D6%BF%CB\">保罗·沃克</a>&nbsp;&nbsp;<a href=\"/search.asp?searchword=%C2%ED%CC%D8%A1%A4%B5%D2%C1%FA\">马特·狄龙</a>&nbsp;&nbsp;<a href=\"/search.asp?searchword=%BA%A3%B5%C7%A1%A4%BF%CB%C0%EF%CB%B9%EB%F8%C9%AD\">海登·克里斯滕森</a>&nbsp;&nbsp;</li><li><span>地区：</span>欧美 </li><li><div class=\"fl\"><span>更新日期：</span>2015-9-16 16:56:07&#160;&#160;</div> <a class=\"fr\" href=\"#pl\" style=\"text-align:right;\"><img src=\"/template/m2014/images/90x22.gif\" alt=\"查看评论\" width=\"90\" height=\"22\" /></a></li></ul><div class=\"textdesc\"><span>剧情：</span>　　以戈登(伊德瑞斯艾尔巴饰)为首的一群劫匪,近日来成为整个城市里最知名的人物,因为他们连续抢劫了多间银行,每次[<a href=\"#desc\">详细</a>]</div><div class=\"pfen\"><p><a href=\"http://www.tvlele.com/e/search/?searchget=1&tbname=movie&tempid=1&show=title,keyboard&keyboard=劫匪\" title=\"查看更多介绍\" target=\"_blank\">影片评价</a></p><div class=\"starscore\"><input type=\"hidden\" id=\"MARK_B1\" name=\"MARK_B1\" value=\"0\" /><input type=\"hidden\" id=\"MARK_B2\" name=\"MARK_B2\" value=\"0\" /><input type=\"hidden\" id=\"MARK_B3\" name=\"MARK_B3\" value=\"0\" /><div class=\"starA fl\" id=\"filmStar\"><div class=\"starB s0\" id=\"start\"></div><div class=\"starC\" id=\"starTC\"> <a href=\"javascript:;\" onclick=\"OnStar(8369,1)\" onmouseout=\"kaifach()\" onmousemove=\"startm(1)\"></a> <a href=\"javascript:;\" onclick=\"OnStar(8369,2)\" onmouseout=\"kaifach()\" onmousemove=\"startm(2)\"></a> <a href=\"javascript:;\" onclick=\"OnStar(8369,3)\" onmouseout=\"kaifach()\" onmousemove=\"startm(3)\"></a> <a href=\"javascript:;\" onclick=\"OnStar(8369,4)\" onmouseout=\"kaifach()\" onmousemove=\"startm(4)\"></a> <a href=\"javascript:;\" onclick=\"OnStar(8369,5)\" onmouseout=\"kaifach()\" onmousemove=\"startm(5)\"></a> <a href=\"javascript:;\" onclick=\"OnStar(8369,6)\" onmouseout=\"kaifach()\" onmousemove=\"startm(6)\"></a> <a href=\"javascript:;\" onclick=\"OnStar(8369,7)\" onmouseout=\"kaifach()\" onmousemove=\"startm(7)\"></a> <a href=\"javascript:;\" onclick=\"OnStar(8369,8)\" onmouseout=\"kaifach()\" onmousemove=\"startm(8)\"></a> <a href=\"javascript:;\" onclick=\"OnStar(8369,9)\" onmouseout=\"kaifach()\" onmousemove=\"startm(9)\"></a> <a href=\"javascript:;\" onclick=\"OnStar(8369,10)\" onmouseout=\"kaifach()\" onmousemove=\"startm(10)\"></a> </div></div><span class=\"no c1\" id=\"filmStarScore\">0<i>.0</i></span></div><div class=\"fen\" id=\"filmStarScoreTip\"></div></div><script type=\"text/javascript\">markVideo(8369,374,0,3148,5,0);</script></div><div class=\"updatetps clearfix\">更新小提示：旋风：http://urlxf.qq.com/?bmEre2b度盘：http://pan.baidu.com/s/1mgMUozA 密码：vdp4 </div></div><div class=\"infoad\"><script type=\"text/javascript\" language=\"javascript\" src=\"http://statics.joy3g.com/2tu/ads/content_250_1.js\"></script><div class=\"skydrive\"><p><span>友情提示：</span>欢迎大家使用网盘连接下载！</p><ul><li class=wxf><a href=http://urlxf.qq.com/?bmEre2b target=_blank>旋风分享</a></li><li class=wbaidu><a href=http://pan.baidu.com/s/1mgMUozA target=_blank>密码：vdp4</a></li></ul></div></div><div class=\"cr\"></div><div class=\"banner mt10\"><script type=\"text/javascript\" language=\"javascript\" src=\"http://statics.joy3g.com/2tu/ads/content2.js\"></script></div><div class=\"endpage clearfix\"><div class=\"ulike\"><div class=\"title\"><span>猜你喜欢</span></div><ul class=\"img-list imglist clearfix\"><li><a class=\"play-img\" href=\"/Html/GP382.html\" title=\"银行抢劫案/银行大劫案\" target=\"_blank\"><img src=\"http://tu.joy3g.com/newpic/382.jpg\" alt=\"银行抢劫案/银行大劫案\"/><i></i><em>1280超清</em></a><h5><a href=\"/Html/GP382.html\" title=\"银行抢劫案/银行大劫案\" target=\"_blank\">银行抢劫案/银..</a></h5></li><li><a class=\"play-img\" href=\"/Html/GP14069.html\" title=\"游侠红牡丹\" target=\"_blank\"><img src=\"http://tu1.joy3g.com/20121221123925975.jpg\" alt=\"游侠红牡丹\"/><i></i><em>1280高清</em></a><h5><a href=\"/Html/GP14069.html\" title=\"游侠红牡丹\" target=\"_blank\">游侠红牡丹</a></h5></li><li><a class=\"play-img\" href=\"/Html/GP18159.html\" title=\"绝命航班\" target=\"_blank\"><img src=\"http://tu.joy3g.com/20140407210712489.jpg\" alt=\"绝命航班\"/><i></i><em>1280超清</em></a><h5><a href=\"/Html/GP18159.html\" title=\"绝命航班\" target=\"_blank\">绝命航班</a></h5></li><li><a class=\"play-img\" href=\"/Html/GP6523.html\" title=\"诸神之战/世纪对神榜\" target=\"_blank\"><img src=\"http://tu.joy3g.com/newpic/6523.jpg\" alt=\"诸神之战/世纪对神榜\"/><i></i><em>1280高清</em></a><h5><a href=\"/Html/GP6523.html\" title=\"诸神之战/世纪对神榜\" target=\"_blank\">诸神之战/世纪..</a></h5></li><li><a class=\"play-img\" href=\"/Html/GP2335.html\" title=\"神奇两女侠\" target=\"_blank\"><img src=\"http://tu.joy3g.com/newpic/2335.jpg\" alt=\"神奇两女侠\"/><i></i><em>国语DVD</em></a><h5><a href=\"/Html/GP2335.html\" title=\"神奇两女侠\" target=\"_blank\">神奇两女侠</a></h5></li><li><a class=\"play-img\" href=\"/Html/GP7237.html\" title=\"烈血暹士2\" target=\"_blank\"><img src=\"http://tu.joy3g.com/newpic/7237.jpg\" alt=\"烈血暹士2\"/><i></i><em>英语DVD</em></a><h5><a href=\"/Html/GP7237.html\" title=\"烈血暹士2\" target=\"_blank\">烈血暹士2</a></h5></li><li><a class=\"play-img\" href=\"/Html/GP17623.html\" title=\"佐州自救兄弟/执勤\" target=\"_blank\"><img src=\"http://tu1.joy3g.com/20140131174516768.jpg\" alt=\"佐州自救兄弟/执勤\"/><i></i><em>1280超清</em></a><h5><a href=\"/Html/GP17623.html\" title=\"佐州自救兄弟/执勤\" target=\"_blank\">佐州自救兄弟/..</a></h5></li><li><a class=\"play-img\" href=\"/Html/GP7249.html\" title=\"天龙特攻队\" target=\"_blank\"><img src=\"http://tu1.joy3g.com/20150611161704002.jpg\" alt=\"天龙特攻队\"/><i></i><em>1280超清</em></a><h5><a href=\"/Html/GP7249.html\" title=\"天龙特攻队\" target=\"_blank\">天龙特攻队</a></h5></li></ul></div><a id=\"kan\"></a><div class=\"mox\"><div class=\"title\"><span>xigua</span><p><a href=\"http://s1.xiguaplayer.com/xigua_Install.exe\" target=\"_blank\">下载西瓜影音</a></p></div><div class=\"play-list\"><a title='1280高清国英双语中英双字版' href='/play/play8369-0-0.html' target=\"_blank\">1280高清国英双语中英双字版</a></div></div><!--/mox--><div class=\"mox\"><div class=\"title\"><span>非凡影音</span><p><a href=\"http://down.ffplay.net/download/FFPlaySetup.exe\" target=\"_blank\">下载非凡影音</a></p></div><div class=\"play-list\"><a title='1280超清国英双语中英双字版' href='/play/play8369-1-0.html' target=\"_blank\">1280超清国英双语中英双字版</a><a title='1280高清国英双语中英双字版' href='/play/play8369-1-1.html' target=\"_blank\">1280高清国英双语中英双字版</a></div></div><!--/mox--><div class=\"mox\"><div class=\"title\"><span>泥巴影音</span><p><a href=\"http://dn.nbplay.cn/NBPlayerSetup.exe\" target=\"_blank\">下载泥巴影音</a></p></div><div class=\"play-list\"><a title='1280超清国英双语中英双字版' href='/play/play8369-2-0.html' target=\"_blank\">1280超清国英双语中英双字版</a><a title='1280高清国英双语中英双字版' href='/play/play8369-2-1.html' target=\"_blank\">1280高清国英双语中英双字版</a></div></div><!--/mox--><div class=\"mox\"><div class=\"title\"><span><img src=\"/template/m2014/images/playlogo/xbhd.gif\" /> 由于迅雷原因，此处仅提供看看点播，下载可能会出错 请尽量选择下方框架地址</span><p><a href=\"http://mov.4567.tv/gvod(www.2tu.cc).exe\" target=\"_blank\">下载迅播播放器</a></p></div><div class=\"hdownlist\"><ul><li><p class=\"name\">1280超清国英双语中英双字版</p><p class=\"size\">2.24Gb</p><span><a title='1280超清国英双语中英双字版' href='/play/play8369-3-0.html' target=\"_blank\">迅播观看</a> <a title='1280超清国英双语中英双字版' href=\"javascript:;\" onclick=\"start('gvod://d3.4567.tv:8089/C732A63906E5610D88226DFECB16EC9BA8796330/2401002636/劫匪.BD1280超清国英双语中英双字.mp4')\">迅雷看看</a><input type=\"hidden\" name=\"xcheckbox\" value=\"cid=C732A63906E5610D88226DFECB16EC9BA8796330&mc=劫匪.BD1280超清国英双语中英双字.mp4\"/><a href=\"javascript:;\" onclick=\"return OnDownloadClick_Simple(this,1)\"  oncontextmenu=\"return ThunderNetwork_SetHref(this)\">迅雷备用</a> <script>document.write(\"<a href=https://d.miwifi.com/d2r/?url=\"+Base64.encodeURI(ThunderEncode('http://bt.2tu.cc/C732A63906E5610D88226DFECB16EC9BA8796330/劫匪.BD1280超清国英双语中英双字.mp4'))+\"&src=xunbo&name=\"+encodeURIComponent('1280超清国英双语中英双字版')+\" target=_blank>小米路由</a>\");</script></span></li><li><p class=\"name\">1280高清国英双语中英双字版</p><p class=\"size\">1.17Gb</p><span><a title='1280高清国英双语中英双字版' href='/play/play8369-3-1.html' target=\"_blank\">迅播观看</a> <a title='1280高清国英双语中英双字版' href=\"javascript:;\" onclick=\"start('gvod://d3.4567.tv:8089/EDC98912C00B0169BFF576FF362BB41A576E4D3A/1260660793/劫匪.BD1280高清国英双语中英双字.mp4')\">迅雷看看</a><input type=\"hidden\" name=\"xcheckbox\" value=\"cid=EDC98912C00B0169BFF576FF362BB41A576E4D3A&mc=劫匪.BD1280高清国英双语中英双字.mp4\"/><a href=\"javascript:;\" onclick=\"return OnDownloadClick_Simple(this,1)\"  oncontextmenu=\"return ThunderNetwork_SetHref(this)\">迅雷备用</a> <script>document.write(\"<a href=https://d.miwifi.com/d2r/?url=\"+Base64.encodeURI(ThunderEncode('http://bt.2tu.cc/EDC98912C00B0169BFF576FF362BB41A576E4D3A/劫匪.BD1280高清国英双语中英双字.mp4'))+\"&src=xunbo&name=\"+encodeURIComponent('1280高清国英双语中英双字版')+\" target=_blank>小米路由</a>\");</script></span></li></ul></div></div><!--/mox--><div class=\"mox\"><a id=\"down\"></a><div class=\"title\"><span><img src=\"/template/m2014/images/playlogo/xzdd.gif\" />下载地址1</span><p> 请不要点上面迅雷备用下载 可能会下错影片 请选择此处迅雷下载地址 </p></div><div class=\"ndownlist\"><script src=\"http://statics.joy3g.com/2tu/xl/xunlei_3.js?v20140608\"></script><script>var GvodUrls = \"ed2k://|file|%E5%8A%AB%E5%8C%AA.BD1280%E8%B6%85%E6%B8%85%E5%9B%BD%E8%8B%B1%E5%8F%8C%E8%AF%AD%E4%B8%AD%E8%8B%B1%E5%8F%8C%E5%AD%97.mp4|2401002636|BAC2F505BFEAB87251AD6B586E935759|h=RRRT3TFRP3JV2IZKCGHLRXU44I6FJ3LF|/###ed2k://|file|%E5%8A%AB%E5%8C%AA.BD1280%E9%AB%98%E6%B8%85%E5%9B%BD%E8%8B%B1%E5%8F%8C%E8%AF%AD%E4%B8%AD%E8%8B%B1%E5%8F%8C%E5%AD%97.mp4|1260660793|C37B5610646295BBCEB3C82996863E2C|h=SAF23Y4UCSDEEQDSB7EQVIG3CAUJXGNC|/\";</script><script src=\"http://statics.joy3g.com/2tu/xl/check.js\"></script><ul><script>var mvName=\"\";var j=0;for(var i=0;i<GvodUrlLen;i++){mvName = get_movie_name(GvodUrlArray[i], '/');j++;if(j==1){uclass=\"class=post2\";j=-1; }else{ uclass=\"\"; } xmhref=\"https://d.miwifi.com/d2r/?url=\" + Base64.encodeURI(ThunderEncode(GvodUrlArray[i])) + \"&src=xunbo\";document.writeln('<li><i><input type=\"checkbox\" value=\"'+GvodUrlArray[i]+'\" name=\"CopyAddr1\" /></i><p><a oncontextmenu=ThunderNetwork_SetHref_b(this) onclick=\"return xunbotask(this)\" href=\"javascript:void(0)\" thunderResTitle=\"\" thunderType=\"\" thunderPid=\"20369\" thunderHref=\"'+ThunderEncode(GvodUrlArray[i])+'\">'+getSubstr(GvodUrlArray[i])+'</a></p><span><a class=d5 href=\"'+ThunderEncode(GvodUrlArray[i])+'\" target=_blank>迅雷</a><a class=d1 href=\"http://lixian.vip.xunlei.com/lixian_login.html?referfrom=union&ucid=20369&furl='+encodeURIComponent(ThunderEncode(GvodUrlArray[i])) +'\" target=_blank>离线</a><a class=d2 href=\"http://vod.lixian.xunlei.com/share.html?from=un_20369&url='+encodeURIComponent(ThunderEncode(GvodUrlArray[i])) +'\" target=_blank>云播</a><a href=\"###\" qhref=\"'+GvodUrlArray[i]+'\" onclick=\"XFLIB.startDownload(this,event,21590)\" oncontextmenu = \"OnContextClick(this, event)\" class=d3>旋风</a><a href=\"'+xmhref+'\" target=_blank class=d4>小米</a></span></li>'); }</script><li class=\"ckbox\"><i><input id=\"allcheck1\" onclick=\"CheckAll(1)\" type=\"checkbox\"  name=\"checkall\" /></i><p><a href=\"javascript:void(0);\" onclick=\"CopyToClip(1)\">复制链接到剪贴板</a><a href=\"javascript:void(0);\" onclick=\"thunderBatchTask(1)\">迅雷批量下载</a><a href=\"javascript:void(0);\" onclick=\"onlinexuan(1)\">旋风批量下载</a><a href=\"javascript:void(0);\" onclick=\"unlinexuan(1)\">旋风批量离线下载</a></p></li></ul></div></div><!--/mox--><div class=\"mox juqing\"><a id=\"desc\"></a><div class=\"title\"><span>剧情介绍</span></div><div class=\"endtext\"><p>　　以戈登(伊德瑞斯&middot;艾尔巴饰)为首的一群劫匪,近日来成为整个城市里最知名的人物,因为他们连续抢劫了多间银行,每次都能在警察赶来之前迅速脱身。这群劫匪手法专业熟练,对于他们来说,银行监控和保安都等于形同虚设,而警方更头疼的是,他们作案多次,却连丝毫线索也抓不到。戈登计划趁在被警方抓获之前,干最后一票然后收手。而警方也请来了经验丰富的老辣警官杰克探长(马特&middot;狄龙饰),他意外得悉了戈登的计划,因此精心准备了天罗地网,等待着戈登一伙的行动&hellip;&hellip;</p></div></div><!--/mox--><div class=\"cr\"></div><a id=\"pl\"></a><div class=\"comment\"><!-- Duoshuo Comment BEGIN --><div class=\"ds-thread\" data-thread-key=\"8369\" data-title=\"劫匪\"></div><script type=\"text/javascript\"> </script><!-- Duoshuo Comment END --></div><div class=\"cr\"></div></div></div><!--/main--><!--版权信息--><div id=\"footer\"><p>免责声明:如果侵犯了你的权益，请发邮件至：xiamp4com@gmail.com，我们会及时删除侵权内容，谢谢合作！</p><p>Copyright &#169; 2014 2tu.cc. All Rights Reserved.<script language=javascript type=text/javascript src=/ad/tongji.js></script></p></div><script>getVideoHit('8369')</script><script type=\"text/javascript\" language=\"javascript\" src=\"http://statics.joy3g.com/2tu/ads/content_cpm.js\"></script></body></html>";
		 //	       String xpath = "//div[not(@class)]"; 
	       String xpathExp = "//div[@class='ndownlist']"; 

		 //test1
	/*        HtmlCleaner htmlCleaner = new HtmlCleaner();  
	        TagNode tn = htmlCleaner.clean(contents);  
	 
	        //String xpath = "//div[@class='masthead']//table[@class='wikitable + sortable']//tbody//tr[@align='right']";  
	        Object[] objarr = tn.evaluateXPath(xpath);
	        if (objarr != null && objarr.length > 0)
	        {
	        	TagNode tagNode=(TagNode) objarr[0];
	        	String c=htmlCleaner.getInnerHtml(tagNode);
	        	System.out.println(c);
	        }*/
	        	
	/*        if (objarr != null && objarr.length > 0) {  
	            for (Object obj : objarr) {  
	                TagNode tntr = (TagNode) obj;  
	                String xptr = "//td[@align='left']//a";  
	                Object[] objarrtr = null;  
	                objarrtr = tntr.evaluateXPath(xptr);  
	  
	                if (objarrtr != null && objarrtr.length > 0) {  
	                    for (Object obja : objarrtr) {  
	                        TagNode tna = (TagNode) obja;  
	                        String str = tna.getText().toString();  
	              
	  
	                    }  
	  
	                }  
	  
	            }  
	  
	        }  
		*/
		 
		 HtmlCleaner hc = new HtmlCleaner();
			TagNode tn = hc.clean(con);
			Document dom = new DomSerializer(new CleanerProperties()).createDOM(tn);
		
			
	/*       DOMParser parser = new DOMParser();  
	     //解析HTML文件  
	     parser.parse("http://www.xiamp4.com/Html/GP8369.html");  
	     //获取解析后的DOM树  
	     Document document = parser.getDocument();  */
			
	     
	   //通过getElementsByTagName获取Node  
/*	     NodeList nodeList1 = document.getElementsByTagName("a");  
	     for (int i = 0; i < nodeList1.getLength(); i++) {  
	         Element e = (Element)nodeList1.item(i);  
	         System.out.print(e.getAttribute("href") + "\t");  
	         System.out.println(e.getTextContent());  
	     }  */
	     
	     
		XPath xPath = XPathFactory.newInstance().newXPath();
		Object result=null;
		result = xPath.evaluate(xpathExp, dom, XPathConstants.NODESET);
	/*	try {
				HtmlXpathSelector xpathSelector=new HtmlXpathSelector(con, "gbk");
				result=xpathSelector.evaluateListXPath(xpathExp);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
			if (result instanceof NodeList) {
				NodeList nodeList = (NodeList) result;
				System.out.println(nodeList.getLength());
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i); 
					DOMSource source = new DOMSource(); 
					source.setNode(node); 
					
					 StringWriter strWtr = new StringWriter();  
					StreamResult results = new StreamResult(strWtr); 
					
					  TransformerFactory tff = TransformerFactory.newInstance();
				        Transformer tf = tff.newTransformer();
				        tf.setOutputProperty(OutputKeys.METHOD, "html");
				        tf.transform(source, results);
				        
				        System.out.println(strWtr.toString());
				//	System.out.println(node.getNodeValue() == null ? node.getTextContent() : node.getNodeValue());
					
			
				}
			}
			

	}
	
	
	
}
