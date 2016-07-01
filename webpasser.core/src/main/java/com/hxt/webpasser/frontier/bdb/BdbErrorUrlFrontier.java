/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: BdbErrorUrlFrontier.java
 *   
 */
package com.hxt.webpasser.frontier.bdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.hxt.webpasser.frontier.ErrorUrlFrontier;
import com.hxt.webpasser.module.ErrorCrawlUrlInfo;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class BdbErrorUrlFrontier implements ErrorUrlFrontier{

	public Database db = null;
	
	public BdbErrorUrlFrontier(	BDBFactory bdbFactory,String dbName){
		
		db=bdbFactory.createAndBindDatabase(dbName+"_error_url");
	}
	
	public synchronized void putErrorCrawlUrlInfo(ErrorCrawlUrlInfo errorCrawlUrlInfo)
			throws Exception {
		if(errorCrawlUrlInfo==null||errorCrawlUrlInfo.getUrlHash()==null||errorCrawlUrlInfo.getUrl()==null){
			return;
		}
		
		DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry valueEntry = new DatabaseEntry();
        keyEntry.setData(errorCrawlUrlInfo.getUrlHash().getBytes());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(errorCrawlUrlInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        valueEntry.setData(baos.toByteArray());
        db.put(null, keyEntry, valueEntry);
	}

	public List<ErrorCrawlUrlInfo> getAll() {
		List<ErrorCrawlUrlInfo> list=new ArrayList<ErrorCrawlUrlInfo>();
		Cursor cursor = db.openCursor(null, null);
		DatabaseEntry foundKey = new DatabaseEntry();
		DatabaseEntry foundValue = new DatabaseEntry();
		// cursor.getPrev()与cursor.getNext()的区别：一个是从前往后读取，一个是从后往前读取
		// 这里讲访问遍历数据库全部数据while循环噶为if判断，则就只读取第一条数据
		while (cursor.getNext(foundKey, foundValue, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
			//String theKey = new String(foundKey.getData(), "UTF-8");
			
			 ByteArrayInputStream bais = new ByteArrayInputStream(foundValue.getData());
	            try {
	                ObjectInputStream ois = new ObjectInputStream(bais);
	                ErrorCrawlUrlInfo errorCrawlUrlInfo = (ErrorCrawlUrlInfo) ois.readObject();
	                list.add(errorCrawlUrlInfo);
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (ClassNotFoundException e) {
	                e.printStackTrace();
	            }
			
	            
		}
		cursor.close();
		
		return list;
	}

	public void delete(String key) throws Exception {
		if(key==null) {
			return;
		}
		// TODO Auto-generated method stub
		DatabaseEntry theKey = new DatabaseEntry(key.getBytes());
		db.delete(null, theKey);
		
	}

}
