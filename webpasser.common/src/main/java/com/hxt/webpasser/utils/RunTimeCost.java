/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: RunTimeCost.java
 *   
 */
package com.hxt.webpasser.utils;
/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-9-16 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class RunTimeCost {
	private long startTime;
	
	private long endTime;
	
	public RunTimeCost()
	{
		startTime();
	}
	
	/**
	 * 计时开始
	 */
	public void startTime()
	{
		startTime=System.currentTimeMillis();
	}
	
	public void endTime()
	{
		endTime=System.currentTimeMillis();
	}
	
	/**
	 * 用时  微秒
	 * @return
	 */
	public long getCostTimeMicrosecond()
	{
		
		long costTime=endTime-startTime;
		return costTime;
	}
	
	/**
	 *   秒
	 * @return
	 */
	public long getCostTimeSecond(){
		
		return getCostTimeMicrosecond()/1000;
	}
	
/*	*//**
	 * 运行到此处花费的时间，微秒
	 * @return
	 *//*
	public long toNowCostTimeMicrosecond()
	{
		long endTime=System.currentTimeMillis();
		
		long costTime=endTime-startTime;
		return costTime;
	}
	
	*//**
	 * 运行到此处花费的时间，秒
	 * @return
	 *//*
	public long toNowCostTimeSecond()
	{
	
		return (toNowCostTimeMicrosecond()/1000);
	}*/
	
}
