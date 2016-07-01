/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: Constructor.java
 *   
 */
package com.hxt.webpasser.transport.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
@XStreamAlias("constructor")
public class Constructor {
	@XStreamImplicit
	List<Arg> args;

	public List<Arg> getArgs() {
		return args;
	}

	public void setArgs(List<Arg> args) {
		this.args = args;
	}


	
	
	
}
