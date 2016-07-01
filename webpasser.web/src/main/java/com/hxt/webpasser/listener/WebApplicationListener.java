/*
 * 系统名称: 
 * 模块名称: webpasser.web
 * 类 名 称: WebApplicationListener.java
 *   
 */
package com.hxt.webpasser.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hxt.webpasser.quartz.SpiderQuartzManager;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class WebApplicationListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {

		SpiderQuartzManager.startJobs();
		
	}

	public void contextDestroyed(ServletContextEvent sce) {

		SpiderQuartzManager.shutdownJobs();
		
	}

}
