/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: BDBFactory.java
 *   
 */
package com.hxt.webpasser.frontier.bdb;

import java.io.File;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseExistsException;
import com.sleepycat.je.DatabaseNotFoundException;
import com.sleepycat.je.EnvironmentConfig;

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
public class BDBFactory {

	
    private transient BdbEnvironment dbEnv;            // 数据库环境,无需序列化
	
    
    public BDBFactory (String dbDir)
    {
    	
    	 init( dbDir);
    	
    }
    
    public void init(String dbDir)
    {
		File envFile = new File(dbDir);
		if (!envFile.exists())
			envFile.mkdir();
        // 数据库位置
  
        // 数据库环境配置
        EnvironmentConfig envConfig= new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        envConfig.setTransactional(true);
        // 创建环境
        dbEnv = new BdbEnvironment(envFile, envConfig);
        
    }
    
    public  StoredClassCatalog getClassCatalog() {
    	
    	return dbEnv.getClassCatalog();
    }
    
	 /**
     * 创建以及绑定数据库
     * 
     * @param dbDir
     * @param dbName
     * @param valueClass
     * @throws DatabaseNotFoundException
     * @throws DatabaseExistsException
     * @throws DatabaseException
     * @throws IllegalArgumentException
     */
    @SuppressWarnings("unused")
	public Database createAndBindDatabase( String dbName) {
  
   
        DatabaseConfig dbConfig = null;
        Database db=null;

        try {
       
            // 数据库配置
            dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
          //  dbConfig.setTransactional(false);
            dbConfig.setDeferredWrite(true);
            
            // 打开数据库
            db = dbEnv.openDatabase(null, dbName, dbConfig);
            System.out.println("初始化 db "+dbName);
            return db;
        } catch (DatabaseNotFoundException e) {
            throw e;
        } catch (DatabaseExistsException e) {
            throw e;
        } catch (DatabaseException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
  
    
    }
    
    
   public void close()
   {
	   
	   if (dbEnv != null) {
		   dbEnv.sync();
		   dbEnv.close();
       }
	   
   }
	
	
}
