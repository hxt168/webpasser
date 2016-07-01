/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: Page.java
 *   
 */
package com.hxt.webpasser.transport.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
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
@XStreamAlias("page")
public class Page extends XmlItem{

	@XStreamImplicit
	private List<Param> params;
	
	private List<Rule> scope;
	@XStreamImplicit
	private List<DigLink> digLinks;
	@XStreamImplicit
	private List<Field> fields;
	public List<Param> getParams() {
		return params;
	}
	public void setParams(List<Param> params) {
		this.params = params;
	}
	public List<Rule> getScope() {
		return scope;
	}
	public void setScope(List<Rule> scope) {
		this.scope = scope;
	}
	public List<DigLink> getDigLinks() {
		return digLinks;
	}
	public void setDigLinks(List<DigLink> digLinks) {
		this.digLinks = digLinks;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
}
