/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ResultHandler.java
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
@XStreamAlias("resultHandler")
public class ResultHandler extends ClassHandler {

	@XStreamAsAttribute
	private String target;
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
    
    
}
