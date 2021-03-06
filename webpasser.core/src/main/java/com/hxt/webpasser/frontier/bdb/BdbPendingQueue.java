/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: BdbPersistentQueue.java
 *   
 */
package com.hxt.webpasser.frontier.bdb;
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

import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
/**
 * 持久化队列,基于BDB实现,也继承Queue,以及可以序列化.但不等同于Queue的时,不再使用后需要关闭
 * 相比一般的内存Queue,插入和获取值需要多消耗一定的时间
 * 这里为什么是继承AbstractQueue而不是实现Queue接口,是因为只要实现offer,peek,poll几个方法即可,
 * 其他如remove,addAll,AbstractQueue会基于这几个方法去实现
 * 
 * @contributor
 * @param <E>
 */
public class BdbPendingQueue<E extends Serializable> extends AbstractQueue<E> implements
        Serializable {
    private static final long serialVersionUID = 3427799316155220967L;

    private transient Database queueDb;             // 数据库,用于保存值,使得支持队列持久化,无需序列化
    private transient StoredSortedMap<Long,E> queueMap;   // 持久化Map,Key为指针位置,Value为值,无需序列化
/*    private transient String dbDir;                 // 数据库所在目录 */ 
    private transient String dbName;				// 数据库名字
   private AtomicLong headIndex;                   // 头部指针
    private AtomicLong tailIndex;                   // 尾部指针
    private transient E peekItem=null;              // 当前获取的值
    
    /**
     * 构造函数,传入BDB数据库
     * 
     * @param db
     * @param valueClass
     * @param classCatalog
     */
    public BdbPendingQueue(Database db,Class<E> valueClass,StoredClassCatalog classCatalog){
        this.queueDb=db;
        this.dbName=db.getDatabaseName();
        bindDatabase(queueDb,valueClass,classCatalog);
        if(!queueMap.isEmpty()){
        	System.out.println("BdbPendingQueue 已有数据，firstKey："+queueMap.firstKey()+" lastKey:"+queueMap.lastKey());
            headIndex=new AtomicLong(queueMap.firstKey());
            tailIndex=new AtomicLong(queueMap.lastKey()+1);
        }else{
            headIndex=new AtomicLong(0);
            tailIndex=new AtomicLong(0);
        }

    }
 /*   *//**
     * 构造函数,传入BDB数据库位置和名字,自己创建数据库
     * 
     * @param dbDir
     * @param dbName
     * @param valueClass
     *//*
    public BdbPersistentQueue(String dbDir,String dbName,Class<E> valueClass){
        headIndex=new AtomicLong(0);
        tailIndex=new AtomicLong(0);
        this.dbDir=dbDir;
        this.dbName=dbName;
        createAndBindDatabase(dbDir,dbName,valueClass);
        
        
    }*/
    /**
     * 绑定数据库
     * 
     * @param db
     * @param valueClass
     * @param classCatalog
     */
    protected void bindDatabase(Database db, Class<E> valueClass, StoredClassCatalog classCatalog){
        EntryBinding<E> valueBinding = TupleBinding.getPrimitiveBinding(valueClass);
        if(valueBinding == null) {
            valueBinding = new SerialBinding<E>(classCatalog, valueClass);   // 序列化绑定
        }
        queueDb = db;
        queueMap = new StoredSortedMap<Long,E>(
                db,                                             // db
                TupleBinding.getPrimitiveBinding(Long.class),   //Key
                valueBinding,                                   // Value
                true);                                          // allow write
    }
   
    /**
     * 值遍历器
     */
    @Override
    public Iterator<E> iterator() {
        return queueMap.values().iterator();
    }
    /**
     * 大小
     */
    @Override
    public int size() {
        synchronized(tailIndex){
            synchronized(headIndex){
                return (int)(tailIndex.get()-headIndex.get());
            }
        }
    }
    
    /**
     * 插入值
     */
    public boolean offer(E e) {
        synchronized(tailIndex){
            queueMap.put(tailIndex.getAndIncrement(), e);   // 从尾部插入
            //System.out.println("offer  tailIndex:"+tailIndex.get()+" firstKey："+queueMap.firstKey()+" lastKey:"+queueMap.lastKey());
        }
        return true;
    }

    
    /**
     * 获取值,从头部获取
     */
    public E peek() {
        synchronized(headIndex){
            if(peekItem!=null){
                return peekItem;
            }
            E headItem=null;
            while(headItem==null&&headIndex.get()<tailIndex.get()){ // 没有超出范围
                headItem=queueMap.get(headIndex.get());
                if(headItem!=null){
                    peekItem=headItem;
                    continue;
                } 
                headIndex.incrementAndGet();    // 头部指针后移
            }
            return headItem;
        }
    }
    
    
    /**
     * 获取值
     */
    public E get(Object key) {
    	
    	return queueMap.get(key);
    }
    
    /**
     * 移出元素,移出头部元素
     */
    public E poll() {
        synchronized(headIndex){
            E headItem=peek();
            if(headItem!=null){
                queueMap.remove(headIndex.getAndIncrement());
                peekItem=null;
                //System.out.println("poll   headIndex:"+headIndex.get()+" firstKey："+queueMap.firstKey()+" lastKey:"+queueMap.lastKey());
                return headItem;
            }
        }
        return null;
    }
    
    /**
     * 关闭,也就是关闭所是用的BDB数据库但不关闭数据库环境
     */
    public void close(){
        try {
            if(queueDb!=null){
                queueDb.sync();
                queueDb.close();
            }
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    /**
     * 清理,会清空数据库,并且删掉数据库所在目录,慎用.如果想保留数据,请调用close()
     */
/*    @Override
    public void clear() {
       try {
    	   close();
    	   if(dbEnv!=null&&queueDb!=null){
				dbEnv.removeDatabase(null, dbName==null?queueDb.getDatabaseName():dbName); 
                dbEnv.close();
           }
	    } catch (DatabaseNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (DatabaseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } finally{
	    	try {
	    		if(this.dbDir!=null){
	    			FileUtils.deleteDirectory(new File(this.dbDir));
	    		}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
    }*/
	
    
}

