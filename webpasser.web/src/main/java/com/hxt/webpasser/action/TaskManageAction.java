/*
 * 系统名称: 
 * 模块名称: webpasser.web
 * 类 名 称: TaskManageAction.java
 *   
 */
package com.hxt.webpasser.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.hxt.webpasser.callback.ProcessCrawlUrlCallBack;
import com.hxt.webpasser.callback.impl.ProcessCrawlUrlOneTestCallBackImpl;
import com.hxt.webpasser.module.TaskInfo;
import com.hxt.webpasser.spider.SpiderTaskFactory;
import com.hxt.webpasser.spider.monitor.TaskManager;
import com.hxt.webpasser.transport.xml.Scheduler;
import com.hxt.webpasser.utils.Base64Util;
import com.hxt.webpasser.utils.StringUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class TaskManageAction {

	private HttpServletRequest request = ServletActionContext.getRequest();
	private Map<String, Object> jsonData;
	
	/**
	 * 抓取任务列表页面
	 * @return
	 */
	public String taskListManagePage(){
		
		return "taskListManagePage";
	}
	/**
	 * 获取task列表
	 * @return
	 */
	public String getTaskList(){
		

		List<TaskInfo> list=TaskManager.getInstance().getTaskInfoList();
		List<Map> listInfos=new ArrayList<Map>();
		if(list!=null&&list.size()>0){
			 Collections.sort(list,new Comparator<TaskInfo>(){  
		            public int compare(TaskInfo arg0, TaskInfo arg1) { 
		            	if(arg0.getTaskName()==null){
		            		return 0;
		            	}
		                return arg0.getTaskName().compareTo(arg1.getTaskName());  
		            }  
		        });  
			
			for(TaskInfo taskInfo:list){
				Map<String,Object> info=new HashMap<String,Object>();
				info.put("taskName", taskInfo.getTaskName());
				info.put("taskPath", taskInfo.getTaskPath());
				listInfos.add(info);
			}
			
		}
	
		request.setAttribute("base", request.getContextPath());
		jsonData=new HashMap<String, Object>();
		jsonData.put("list", listInfos);
		return "jsonData";
	}
	
	
	/**
	 * 任务信息页面
	 * @return
	 */
	public String taskInfoManagePage(){
		
		String taskName=(String) request.getParameter("taskName");
		request.setAttribute("base", request.getContextPath());
		request.setAttribute("taskName", taskName);
		
		return "taskInfoManagePage";
	}
	
	
	public String readFileRecord(){
		
		String type=(String) request.getParameter("type");
		String taskName=(String) request.getParameter("taskName");
		String lastNumReadStr=(String) request.getParameter("lastNumRead");  //读取的最后多少行
		int numRead=10000;
		if(!StringUtil.isEmpty(lastNumReadStr)){
			numRead=Integer.parseInt(lastNumReadStr);	
		}
		if(TaskManager.getInstance().getSpiderTask(taskName)==null){
			
			return null;
		}
		String filePath="";
		if("errorUrl".equals(type)){
			
			filePath=TaskManager.getInstance().getSpiderTask(taskName).getSpiderController().getSpiderTaskState().getErrorUrlLogFIlePath();
		}else if("successUrl".equals(type)){
			
			filePath=TaskManager.getInstance().getSpiderTask(taskName).getSpiderController().getSpiderTaskState().getSuccessCountPageLoggerFilePath();
		}
		HttpServletResponse response=ServletActionContext.getResponse();
		//response.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
		OutputStream out=null;
		try {
			out = response.getOutputStream();
			readOutLastLine(filePath, out, numRead);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			
		}
		
	/*	BufferedInputStream br=null;
		OutputStream out=null;
		try {
			br=new BufferedInputStream(new FileInputStream(filePath));
			byte[]buf =new byte[1024];
			int len=0;
			response.reset();
			 out=response.getOutputStream();
			while((len=br.read(buf))>0)
				out.write(buf,0,len);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			
		}*/
		
		return null;
	}
	
	/**
	 * 读取最后几行
	 */
	private void readOutLastLine(String filePath,OutputStream out,int numRead){
		 //行数统计
        long count = 0;
        File file=new File(filePath);
        // 排除不可读状态
        if (!file.exists() || file.isDirectory() || !file.canRead())
        {
            return ;
        }
        
        // 使用随机读取
        RandomAccessFile fileRead = null;
        try
        {
            //使用读模式
            fileRead = new RandomAccessFile(file, "r");
            //读取文件长度
            long length = fileRead.length();
            //如果是0，代表是空文件，直接返回空结果
            if (length == 0L)
            {
                return ;
            }
            else
            {
                //初始化游标
                long pos = length - 1;
                while (pos > 0)
                {
                    pos--;
                    //开始读取
                    fileRead.seek(pos);
                    //如果读取到\n代表是读取到一行
                    if (fileRead.readByte() == '\n')
                    {
                        //使用readLine获取当前行
                        String line = fileRead.readLine()+"\n";
                        //保存结果
                        //result.add(line);
                        out.write(line.getBytes());
                        
                        //行数统计，如果到达了numRead指定的行数，就跳出循环
                        count++;
                        if (count == numRead)
                        {
                            break;
                        }
                    }
                }
                if (pos == 0)
                {
                    fileRead.seek(0);
                    out.write(fileRead.readLine().getBytes());
                }
                out.flush();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fileRead != null)
            {
                try
                {
                    //关闭资源
                    fileRead.close();
                }
                catch (Exception e)
                {
                }
            }
        }
        
		
	}
	
	/**
	 * 获得任务信息
	 * @return
	 */
	public String getTaskInfo(){
		
		String taskName=(String) request.getParameter("taskName");
		jsonData=new HashMap<String, Object>();
		
		try {
			Map infoMap=TaskManager.getInstance().monitorSpiderTaskInfo(taskName);
			jsonData.put("data", infoMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "jsonData";
	}
	
	
	public String getTaskXmlContent(){
		String taskName=(String) request.getParameter("taskName");
		jsonData=new HashMap<String, Object>();
		
		try {
			TaskInfo taskInfo=SpiderTaskFactory.getInstance().getTaskByName(taskName);
			String xmlContent=taskInfo.readXmlContent();
			xmlContent=Base64Util.encode(xmlContent.getBytes("utf-8"));
			//xmlContent=xmlContent.replaceAll("/", "#");
			//System.out.println(xmlContent);
			jsonData.put("data", xmlContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "jsonData";
	}
	
	public String addOrEditTask() {
		String taskName=(String) request.getParameter("taskName");
		String xml=(String) request.getParameter("xml");
		Integer isAdd=Integer.parseInt(request.getParameter("isAdd"));  // 1:add 0:edit
		jsonData=new HashMap<String, Object>();
		jsonData.put("result", "success");

		try {
			if(isAdd==1){
				SpiderTaskFactory.getInstance().addTask(taskName,xml);
			}else{
				SpiderTaskFactory.getInstance().editTask(taskName, xml);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonData.put("result","fail");
			jsonData.put("msg", e.getMessage());
		}
		
	
		return "jsonData";
		
	}
	
	/**
	 * 编辑task xml的页面
	 * @return
	 */
	public String editTaskXmlPage(){
		String taskName=(String) request.getParameter("taskName");
		request.setAttribute("base", request.getContextPath());
		request.setAttribute("taskName", taskName);
/*		if(!StringUtil.isEmpty(taskName)){
			TaskInfo taskInfo=SpiderTaskFactory.getInstance().getTaskByName(taskName);
			String xmlContent=taskInfo.readXmlContent();
			request.setAttribute("xmlContent", xmlContent);
		}*/
	
		return "editTaskXmlPage";
	}
	
	
	public String reloadTask()
	{
		String taskName=(String) request.getParameter("taskName");
		jsonData=new HashMap<String, Object>();
		jsonData.put("result", "success");

		try {
			TaskManager.getInstance().reloadTask(taskName);
		} catch (Exception e) {
			e.printStackTrace();
			jsonData.put("result","fail");
			jsonData.put("msg", e.getMessage());
		}
		
	
		return "jsonData";
	}
	
	/**
	 * 对任务进行操作，启动、暂停，恢复、停止
	 * @return
	 */
	public String operateTask()
	{
		String taskName=(String) request.getParameter("taskName");
		String op=(String) request.getParameter("op");
		jsonData=new HashMap<String, Object>();
		jsonData.put("result", "success");
		
		if(op!=null&&taskName!=null)
		{
			try {
				TaskManager.getInstance().operateTask(taskName, Integer.parseInt(op));
			} catch (Exception e) {
				e.printStackTrace();
				jsonData.put("result","fail");
				jsonData.put("msg", e.getMessage());
			}
		}
	
		return "jsonData";
	}
	
	/**
	 * 测试一个页面抓取解析情况
	 * @return
	 */
	public String testOneUrlCatch(){
		String taskName=(String) request.getParameter("taskName");
		String testUrl=(String) request.getParameter("url");
		jsonData=new HashMap<String, Object>();
		ProcessCrawlUrlOneTestCallBackImpl processCrawlUrlCallBack=new ProcessCrawlUrlOneTestCallBackImpl();
		try {
			TaskManager.getInstance().testOneFetchAndParse(taskName, testUrl, processCrawlUrlCallBack);
			Map<String, Object> data=new HashMap<String, Object>();
			data.put("crawlLinks", processCrawlUrlCallBack.getParseredLinks());
			data.put("valueMap", processCrawlUrlCallBack.getParseredValueMap());
			jsonData.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			jsonData.put("msg", e.getMessage());
		}
		
		
		return "jsonData";
	}
	
	
	
	public String addOrChangeScheduler(){
		jsonData=new HashMap<String, Object>();
		jsonData.put("result", "success");
		String taskName=(String) request.getParameter("taskName");
		try {
			Integer op=Integer.parseInt(request.getParameter("op"));  // 1:添加  2:修改
			String cronExpression=(String) request.getParameter("cronExpression");
			Integer taskCircle=Integer.parseInt(request.getParameter("taskCircle"));
			String timeString=(String) request.getParameter("timeString");
			
			Scheduler scheduler=new Scheduler();
			scheduler.setCronExpression(cronExpression);
			scheduler.setTaskCircle(taskCircle);
			scheduler.setTimeString(timeString);
			boolean isAdd=true;
			if(op==2){  //修改
				isAdd=false;
			}else{
				scheduler.setIsRun(1);
			}
			
			TaskManager.getInstance().addOrChangeScheduler(taskName, scheduler, isAdd);
		} catch (Exception e) {
			e.printStackTrace();
			jsonData.put("result","fail");
			jsonData.put("msg", e.getMessage());
		}

		return "jsonData";
	}

	
	public String startOrStopScheduler(){
		jsonData=new HashMap<String, Object>();
		jsonData.put("result", "success");
		String taskName=(String) request.getParameter("taskName");
		try {
			Integer op=Integer.parseInt(request.getParameter("op"));  // 1:开启  2:停止
			boolean isStart=true;
			if(op==2){  //停止
				isStart=false;
			}
			TaskManager.getInstance().startOrStopScheduler(taskName, isStart);
		} catch (Exception e) {
			e.printStackTrace();
			jsonData.put("result","fail");
			jsonData.put("msg", e.getMessage());
		}
		
		return "jsonData";
	}
	
	public Map<String, Object> getJsonData() {
		return jsonData;
	}
	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
	
	
}
