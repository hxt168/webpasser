/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: AbstractFrontier.java
 *   
 */
package com.hxt.webpasser.frontier;
/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-9 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public abstract class AbstractFrontier {

/*	   //put方法  
    protected abstract void put(Object key,Object value);  
    //get方法  
    protected abstract Object get(Object key);  
    //delete方法  
    protected abstract Object delete(Object key);  */
/*	 protected abstract void push(CrawlUrl value);  
	
	 protected abstract CrawlUrl poll();  
	 
	 protected abstract CrawlUrl get(String key);
	 */
	
	
	
	/**
	 * 是否能添加到Pending队列
	 * @param visitState
	 * @return
	 */
	protected boolean isCanPending(Integer visitState)
	{
		if(visitState==null)
		{
			return true;
		}
		
		return false;
	}
	
}
