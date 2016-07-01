/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ClassHandler.java
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
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class ClassHandler {

	@XStreamAsAttribute
	@XStreamAlias("classPath")
	private String classPath;
	
	private Constructor constructor;
	@XStreamImplicit
    private List<Property> properties;


	public Constructor getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor constructor) {
		this.constructor = constructor;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	
	
}
