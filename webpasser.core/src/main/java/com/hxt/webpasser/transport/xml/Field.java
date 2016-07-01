/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: Field.java
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
@XStreamAlias("field")
public class Field extends XmlItem {
	@XStreamAsAttribute
	private Integer isList;
	@XStreamAsAttribute
	private String joinMark;
	
	private List<Rule> rules;
	
	private  List<Field> list;
	@XStreamImplicit
	private List<Field> fields;
	
	private  LinkPage linkPage;
	
	public Integer getIsList() {
		return isList;
	}

	public void setIsList(Integer isList) {
		this.isList = isList;
	}

	public String getJoinMark() {
		return joinMark;
	}

	public void setJoinMark(String joinMark) {
		this.joinMark = joinMark;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}



	public LinkPage getLinkPage() {
		return linkPage;
	}

	public void setLinkPage(LinkPage linkPage) {
		this.linkPage = linkPage;
	}

	public List<Field> getList() {
		return list;
	}

	public void setList(List<Field> list) {
		this.list = list;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}


	
	
	
}
