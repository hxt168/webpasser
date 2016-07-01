/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: XmlTask.java
 *   
 */
package com.hxt.webpasser.transport.xml;

import java.io.IOException;
import java.util.List;

import com.hxt.webpasser.transport.util.XStreamWireFormat;
import com.hxt.webpasser.utils.FileUtil;
import com.hxt.webpasser.utils.ResourceUtil;
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
@XStreamAlias("task")
public class XmlTask extends XmlItem{
	

	
	private List<Seed> seeds;
	
	private List<Trigger> triggers;
	
	private List<LimitHost> scope;
	@XStreamImplicit
	private List<Page> pages;
	
	private FetchConfig fetchConfig;
	@XStreamImplicit
	private List<ResultHandler> resultHandlers;
/*	//初始化
	public void endInit()
	{
		if(pages!=null)
		{
			for(int i=0;i<pages.size();i++) //page的name默认赋值
			{
				Page page=pages.get(i);
				if(page.getName()==null||"".equals(page.getName()))
				{
					page.setName("#"+i);
				}
			}
		}
		
	}*/
	


	public List<Seed> getSeeds() {
		return seeds;
	}

	public void setSeeds(List<Seed> seeds) {
		this.seeds = seeds;
	}
	
	
	
	
	@Override
	public String toString() {
		return "XmlTask [headers=" + ", seeds=" + seeds + "]";
	}

	public static void main(String[] args) {
		
		String path=ResourceUtil.getResourcePath()+"catch/test.xml";
		String con=FileUtil.readTxtFile(path);
		XmlTask ob=XStreamWireFormat.unmarshalText(con,XmlTask.class);
		System.out.println(ob.toString());
		try {
			String str =XStreamWireFormat.marshalText(ob);
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<LimitHost> getScope() {
		return scope;
	}

	public void setScope(List<LimitHost> scope) {
		this.scope = scope;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public FetchConfig getFetchConfig() {
		return fetchConfig;
	}

	public void setFetchConfig(FetchConfig fetchConfig) {
		this.fetchConfig = fetchConfig;
	}

	public List<ResultHandler> getResultHandlers() {
		return resultHandlers;
	}

	public void setResultHandlers(List<ResultHandler> resultHandlers) {
		this.resultHandlers = resultHandlers;
	}

	public List<Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}


	
}
