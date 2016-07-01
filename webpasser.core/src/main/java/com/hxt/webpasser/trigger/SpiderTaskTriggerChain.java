/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: SpiderTaskTriggerChain.java
 *   
 */
package com.hxt.webpasser.trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderTaskTriggerChain {

	List<SpiderTaskTrigger> triggers;
	
	
	public void addSpiderTaskTrigger(SpiderTaskTrigger spiderTaskTrigger){
		if(triggers==null){
			triggers=new ArrayList<SpiderTaskTrigger>();
		}
		triggers.add(spiderTaskTrigger);
		
	}
	
	/**
	 * 准备抓取数据
	 * @param crawlUrl
	 */
	public void beforeDownload(CrawlUrl crawlUrl){
		if(triggers!=null){
			for(SpiderTaskTrigger spiderTaskTrigger:triggers){
				spiderTaskTrigger.beforeDownload(crawlUrl);
			}
		}
	}
	
	/**
	 * 抓取数据成功
	 * @param crawlUrl
	 * @param pageResult
	 */
	public void successDownloadAfter(CrawlUrl crawlUrl,PageResult pageResult){
		if(triggers!=null){
			for(SpiderTaskTrigger spiderTaskTrigger:triggers){
				spiderTaskTrigger.successDownloadAfter(crawlUrl,pageResult);
			}
		}
	}
	/**
	 * 抓取数据一次失败，准备重试
	 * @param crawlUrl
	 */
	public void tryOneFailDownload(CrawlUrl crawlUrl){
		if(triggers!=null){
			for(SpiderTaskTrigger spiderTaskTrigger:triggers){
				spiderTaskTrigger.tryOneFailDownload(crawlUrl);
			}
		}
	}
	/**
	 * 重复抓取几次数据都失败后
	 * @param crawlUrl
	 */
	public void reTryAllFailDownload(CrawlUrl crawlUrl){
		if(triggers!=null){
			for(SpiderTaskTrigger spiderTaskTrigger:triggers){
				spiderTaskTrigger.tryOneFailDownload(crawlUrl);
			}
		}
	}
	
	
	/**
	 * 将抓取来的网页内容解析成map后
	 * @param returnValue
	 */
	public void afterParseToMap(Map<String,Object> returnValue){
		if(triggers!=null){
			for(SpiderTaskTrigger spiderTaskTrigger:triggers){
				spiderTaskTrigger.afterParseToMap(returnValue);
			}
		}
	}
	
	/**
	 * 最后进行存储后
	 * @param returnValue
	 */
	public void afterPersistent(Map<String,Object> returnValue){
		if(triggers!=null){
			for(SpiderTaskTrigger spiderTaskTrigger:triggers){
				spiderTaskTrigger.afterPersistent(returnValue);
			}
		}
	}
	
	
	
}
