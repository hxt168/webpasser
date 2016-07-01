/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: DigLink.java
 *   
 */
package com.hxt.webpasser.transport.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

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
@XStreamAlias("digLink")
public class DigLink extends XmlItem{

	private List<Rule> rules;

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
}
