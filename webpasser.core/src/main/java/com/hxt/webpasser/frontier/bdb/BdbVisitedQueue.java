/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: BdbVisitedQueue.java
 *   
 */
package com.hxt.webpasser.frontier.bdb;

import com.hxt.webpasser.frontier.VisitedIntState;
import com.hxt.webpasser.utils.CommonUtil;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-10 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class BdbVisitedQueue {

	public Database visitedDb = null;
	
//	private int allHasVisitedCount=0;  //已经抓取过的计数，包含 pending的数量,以及上次的数量
	
	private int hasVisitedCount=0;  //已经抓取过的计数，不包含 pending的数量
	
	public BdbVisitedQueue(Database visitedDb)
	{
		this.visitedDb=visitedDb;
	/*	if(visitedDb!=null){
			allHasVisitedCount=(int) visitedDb.count();
		}*/
	}
	
	
	  public boolean delete(String key) throws Exception {
	        byte[] theKey = key.getBytes();
	        OperationStatus status = visitedDb.delete(null, new DatabaseEntry(theKey));
	        if (status == OperationStatus.SUCCESS) {
	            return true;
	        }
	        return false;
	    }
	  
	  
	  /**
	   * 
	   * @param key
	   * @return  0: Pending 1:visited
	   * @throws Exception
	   */
	    public Integer get(String key) throws Exception {
	        byte[] theKey = key.getBytes();
	        DatabaseEntry queryKey = new DatabaseEntry(theKey);
	        DatabaseEntry value = new DatabaseEntry();
	 
	        OperationStatus status = visitedDb
	                .get(null, queryKey, value, LockMode.DEFAULT);
	        if (status == OperationStatus.SUCCESS) {
	            return CommonUtil.byteArray2Int(value.getData());
	        }
	        return null;
	    }
	 
	    /**
	     * 0: Pending 1:visited
	     * @param key
	     * @param value
	     * @return
	     * @throws Exception
	     */
	    public synchronized boolean put(String key, Integer value) throws Exception {
	        byte[] updateKey = key.getBytes();
	        
	        byte[] updateValue =CommonUtil.int2ByteArray(value);
	       
	        OperationStatus status = visitedDb.put(null, new DatabaseEntry(updateKey),
	                new DatabaseEntry(updateValue));
	        if (status == OperationStatus.SUCCESS) {
	        	if(value==VisitedIntState.VISITED_STATE)
	        	{
	        		hasVisitedCount++;
	        	}
	        	//allHasVisitedCount++;
	            return true;
	        }
	        return false;
	    }
	    
	
	    
	    /**
	     * 关闭,也就是关闭所是用的BDB数据库但不关闭数据库环境
	     */
	    public void close(){
	        try {
	            if(visitedDb!=null){
	            	visitedDb.sync();
	            	visitedDb.close();
	            }
	        } catch (DatabaseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (UnsupportedOperationException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }


		public int getHasVisitedCount() {
			return hasVisitedCount;
		}
	    
/*		public int getSize(){
			return allHasVisitedCount;
		}*/
		
}
