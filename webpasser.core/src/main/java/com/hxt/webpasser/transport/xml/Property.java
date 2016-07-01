/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: Property.java
 *   
 */
package com.hxt.webpasser.transport.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
@XStreamAlias("property")
public class Property {

	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private String ref;
	
	@XStreamAsAttribute
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
