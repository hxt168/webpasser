/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: LinkPage.java
 *   
 */
package com.hxt.webpasser.transport.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-9-9 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
@XStreamAlias("linkPage")
public class LinkPage extends XmlItem{
	@XStreamImplicit
	private List<Param> params;
	@XStreamImplicit
	private List<DigLink> digLinks;
	public List<Param> getParams() {
		return params;
	}
	public void setParams(List<Param> params) {
		this.params = params;
	}
	public List<DigLink> getDigLinks() {
		return digLinks;
	}
	public void setDigLinks(List<DigLink> digLinks) {
		this.digLinks = digLinks;
	}
}
