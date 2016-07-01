/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: Rule.java
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
@XStreamAlias("rule")
public class Rule {

	@XStreamAsAttribute
	private String type;
	@XStreamAsAttribute
	private String value;
	@XStreamAsAttribute
	private String attr;
	
	@XStreamAsAttribute
	private String exp;
	
	@XStreamAsAttribute
	private String classPath;
	
	
	private String pre;
	private String end;
	
	private String oldChars;
	private String newChars;
	
	@XStreamImplicit(itemFieldName="tag")
	private List<String> tags;
	 @XStreamImplicit(itemFieldName="param")
	private List<Param> params;
	 
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public String getPre() {
		return pre;
	}
	public void setPre(String pre) {
		this.pre = pre;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getOldChars() {
		return oldChars;
	}
	public void setOldChars(String oldChars) {
		this.oldChars = oldChars;
	}
	public String getNewChars() {
		return newChars;
	}
	public void setNewChars(String newChars) {
		this.newChars = newChars;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public List<Param> getParams() {
		return params;
	}
	public void setParams(List<Param> params) {
		this.params = params;
	}
	
	
	
}
