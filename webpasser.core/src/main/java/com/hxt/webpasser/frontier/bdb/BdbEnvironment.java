/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: BdbEnvironment.java
 *   
 */
package com.hxt.webpasser.frontier.bdb;
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


import java.io.File;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
/**
 * BDB数据库环境,可以缓存StoredClassCatalog并共享
 * 
 * @contributor guoyun
 */
public class BdbEnvironment extends Environment {
    StoredClassCatalog classCatalog; 
    Database classCatalogDB;
    
    /**
     * Constructor
     * 
     * @param envHome 数据库环境目录
     * @param envConfig config options  数据库换纪念馆配置
     * @throws DatabaseException
     */
    public BdbEnvironment(File envHome, EnvironmentConfig envConfig) throws DatabaseException {
        super(envHome, envConfig);
    }

    /**
     * 返回StoredClassCatalog
     * @return the cached class catalog
     */
    public StoredClassCatalog getClassCatalog() {
        if(classCatalog == null) {
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
            try {
                classCatalogDB = openDatabase(null, "classCatalog", dbConfig);
                classCatalog = new StoredClassCatalog(classCatalogDB);
            } catch (DatabaseException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException(e);
            }
        }
        return classCatalog;
    }

    @Override
    public synchronized void close() throws DatabaseException {
        if(classCatalogDB!=null) {
            classCatalogDB.close();
        }
        super.close();
    }

}