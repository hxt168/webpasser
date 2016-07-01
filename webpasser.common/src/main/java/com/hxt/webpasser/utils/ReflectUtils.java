/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ReflectUtils.java
 *   
 */
package com.hxt.webpasser.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明: 反射工具 <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class ReflectUtils {

	/**
	 * 根据属性名得到属性对象
	 * @param beanOb
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public static Object getPropertyByName(Object beanOb,String propertyName) throws Exception{
		
		 Class beanCla = beanOb.getClass();
		 /* 
	        * 得到类中的所有属性集合 
	        */  
	     Field[] fs = beanCla.getDeclaredFields();  
	     for(int i = 0 ; i < fs.length; i++){  
	           Field f = fs[i];  
	           f.setAccessible(true); //设置些属性是可以访问的  
	           
	           if(propertyName.equals(f.getName())){
	        	   Object val = f.get(beanOb);//得到此属性的值     
	        	   return val;
	           }
/*	           System.out.println("name:"+f.getName()+"\t value = "+val);  
	            
	           String type = f.getType().toString();//得到此属性的类型  
	           if (type.endsWith("String")) {  
	              //System.out.println(f.getType()+"\t是String");  
	              //f.set(bean,"12") ;        //给属性设值  
	           }else if(type.endsWith("int") || type.endsWith("Integer")){  
	             // System.out.println(f.getType()+"\t是int");  
	              //f.set(bean,12) ;       //给属性设值  
	           }else{  
	             
	           }  */
	            
	       }

		 return null;
	}
	
	/**
	 * 用构造方法实例化对象
	 * @param classPath
	 * @param argObjects
	 * @return
	 * @throws Exception
	 */
	public static Object instanceClassByConstructor(String classPath,Object[] argObjects) throws Exception{
		
		Class c = Class.forName(classPath);  
		
		Class[] argClasses=new Class[argObjects.length];
		for(int i=0;i<argObjects.length;i++){
			argClasses[i]=argObjects[i].getClass();
			
		}
		java.lang.reflect.Constructor constructor = c.getConstructor(argClasses);  
		
		Object newOb=constructor.newInstance(argObjects);
		
		return newOb;
	}
	
	/**
	 * 直接实例化对象
	 * @param classPath
	 * @return
	 * @throws Exception
	 */
	public static Object instanceClassNoConstructor(String classPath) throws Exception{
		
		Class c = Class.forName(classPath);  
		Object newOb=c.newInstance();
		return newOb;
	}
	
	/**
	 * 设置属性值
	 * @param beanOb
	 * @param propertyName
	 * @param propertyOb
	 * @throws Exception
	 */
	public static void setPropertyValue(Object beanOb,String propertyName,Object propertyOb) throws Exception{

/*		String propertyNameUpper = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1); // 将属性的首字符大写，方便构造get，set方法
		
		////...........
		Method setMethod=beanOb.getClass().getMethod("set" + propertyNameUpper,propertyOb.getClass());
		//method.setAccessible(true);
		setMethod.invoke(beanOb, propertyOb);
		*/
		
		 Class beanCla = beanOb.getClass();
		 /* 
	        * 得到类中的所有属性集合 
	        */  
	     Field[] fs = beanCla.getDeclaredFields();  
	     for(int i = 0 ; i < fs.length; i++){  
	           Field f = fs[i];  
	          
	           
	           if(propertyName.equals(f.getName())){
	        	   f.setAccessible(true); //设置些属性是可以访问的  
	        	 /*  PropertyDescriptor pd = new PropertyDescriptor(f.getName(), beanCla);
	        	   Method method = pd.getWriteMethod();
	               method.invoke(beanOb, propertyOb);*/
	        	   f.set(beanOb, propertyOb);
	           }
	           
	      }
		
	}
	
	
	public static Map objectToMap(Object beanOb)
	  {
	    Map map = new HashMap();
		 Class beanCla = beanOb.getClass();
		 try {
			/* 
			    * 得到类中的所有属性集合 
			    */  
			 Field[] fs = beanCla.getDeclaredFields();  
			 for(int i = 0 ; i < fs.length; i++){  
			       Field f = fs[i];  
			       f.setAccessible(true); //设置些属性是可以访问的  
			       Object val = f.get(beanOb);//得到此属性的值     
			       map.put(f.getName(), val);
			       
/*	           System.out.println("name:"+f.getName()+"\t value = "+val);  
			        
			       String type = f.getType().toString();//得到此属性的类型  
			       if (type.endsWith("String")) {  
			          //System.out.println(f.getType()+"\t是String");  
			          //f.set(bean,"12") ;        //给属性设值  
			       }else if(type.endsWith("int") || type.endsWith("Integer")){  
			         // System.out.println(f.getType()+"\t是int");  
			          //f.set(bean,12) ;       //给属性设值  
			       }else{  
			         
			       }  */
			        
			   }
		} catch (Exception e) {
			e.printStackTrace();
		} 

	    return map;
	 }
	
	
	
	public static Object mapToObject(Map map,Class clazz)
	  {
	
	    Object beanOb=null;
		try {
			beanOb = clazz.newInstance();
			  Field[] fs = clazz.getDeclaredFields();  
			     for(int i = 0 ; i < fs.length; i++){  
			           Field f = fs[i];  
			           f.setAccessible(true); //设置些属性是可以访问的  
			           Object value=map.get(f.getName());
			           if(value!=null){
				           f.set(beanOb, value);
			           }

			        
			     
			           
			      }
		} catch (Exception e) {
			e.printStackTrace();
		} 
	   
	    return beanOb;
	 }
	
	
}
