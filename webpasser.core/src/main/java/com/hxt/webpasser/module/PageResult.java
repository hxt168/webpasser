/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: PageResult.java
 *   
 */
package com.hxt.webpasser.module;

import java.util.Map;

import com.hxt.webpasser.utils.RunTimeCost;

/**
 * 功能说明: 页面下载下来的内容 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-18 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class PageResult {

	private String url;
	
	private String urlHash;
	
	private String charSet; 
	
	private String content;
	
	private Integer persistentPageInt;  //xml配置中对应持久化的page 位置
	
    private String linkPageName;  //从其他网页关联到此page的name
	
    private Map<String,Object> linkParamValueMap;  //从其他网页关联过来的

    private LinkPageInfo linkPageInfo;  //本页面去关联其他的 链接信息
    
    private RunTimeCost catchTimeCost;  //抓取的用时时间
    
    //private RunTimeCost parserTimeCost;  //解析的用时时间
    
    private RunTimeCost persistentTimeCost;  //存储时用时时间
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlHash() {
		return urlHash;
	}

	public void setUrlHash(String urlHash) {
		this.urlHash = urlHash;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPersistentPageInt() {
		return persistentPageInt;
	}

	public void setPersistentPageInt(Integer persistentPageInt) {
		this.persistentPageInt = persistentPageInt;
	}

	public Map<String,Object> getLinkParamValueMap() {
		return linkParamValueMap;
	}

	public void setLinkParamValueMap(Map<String,Object> linkParamValueMap) {
		this.linkParamValueMap = linkParamValueMap;
	}

	public LinkPageInfo getLinkPageInfo() {
		return linkPageInfo;
	}

	public void setLinkPageInfo(LinkPageInfo linkPageInfo) {
		this.linkPageInfo = linkPageInfo;
	}

	public String getLinkPageName() {
		return linkPageName;
	}

	public void setLinkPageName(String linkPageName) {
		this.linkPageName = linkPageName;
	}

	public RunTimeCost getCatchTimeCost() {
		return catchTimeCost;
	}

	public void setCatchTimeCost(RunTimeCost catchTimeCost) {
		this.catchTimeCost = catchTimeCost;
	}

	public RunTimeCost getPersistentTimeCost() {
		return persistentTimeCost;
	}

	public void setPersistentTimeCost(RunTimeCost persistentTimeCost) {
		this.persistentTimeCost = persistentTimeCost;
	}

/*	public RunTimeCost getParserTimeCost() {
		return parserTimeCost;
	}

	public void setParserTimeCost(RunTimeCost parserTimeCost) {
		this.parserTimeCost = parserTimeCost;
	}*/


	
	
	
	
	
}
