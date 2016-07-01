/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: HandleClassLoader.java
 *   
 */
package com.hxt.webpasser.spider;

import java.util.ArrayList;
import java.util.List;

import com.hxt.webpasser.transport.xml.Arg;
import com.hxt.webpasser.transport.xml.ClassHandler;
import com.hxt.webpasser.transport.xml.Constructor;
import com.hxt.webpasser.transport.xml.Property;
import com.hxt.webpasser.transport.xml.ResultHandler;
import com.hxt.webpasser.transport.xml.Trigger;
import com.hxt.webpasser.utils.ReflectUtils;
import com.hxt.webpasser.utils.StringUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class HandleClassLoader {

	private SpiderController spiderController;
	
	
	public HandleClassLoader(SpiderController spiderController){
		this.spiderController=spiderController;
		
	}
	
	public void loadOneHandler(ResultHandler resultHandler) throws Exception{
		
		if(resultHandler!=null){
			
			String target=resultHandler.getTarget();
			Object handlerOb=instanceNewObject(resultHandler);
			if(handlerOb!=null){
				
				ReflectUtils.setPropertyValue(spiderController, target, handlerOb);
			}
			
		}
		
		
	}
	
	
	public Object instanceNewObject(ClassHandler classHandler) throws Exception{
		Object handlerOb=null;
		String classPath=classHandler.getClassPath();

		Object[] constructorArgs=null;
		Constructor constructor =classHandler.getConstructor();
		if(constructor!=null){
			List<Arg> args=constructor.getArgs();
			if(args!=null&&args.size()>0){
				
				constructorArgs=new Object[args.size()];
				for(int i=0;i<args.size();i++){
					String ref=args.get(i).getRef();
					String value=args.get(i).getValue();
					Object pram=null;
					if(StringUtil.isEmpty(value)){ //没有value属性值，则从ref取对象
						pram=ReflectUtils.getPropertyByName(spiderController, ref);
					}else{
						pram=value;
					}
					constructorArgs[i]=pram;	
				}
				
			}
			
		}
		if(constructorArgs!=null){
			
			handlerOb=ReflectUtils.instanceClassByConstructor(classPath, constructorArgs);
		}else{
			handlerOb=ReflectUtils.instanceClassNoConstructor(classPath);
		}
		
		List<Property> properties=classHandler.getProperties();
		if(properties!=null){
			
			for(Property property:properties){
				String propertyName=property.getName();
				String ref=property.getRef();
				String value=property.getValue();
				Object pramOb=null;
				if(StringUtil.isEmpty(value)){ //没有value属性值，则从ref取对象
					pramOb=ReflectUtils.getPropertyByName(spiderController, ref);
				}else{
					pramOb=value;
				}
				ReflectUtils.setPropertyValue(handlerOb, propertyName, pramOb);
			}
			
		}
		
		return handlerOb;
	}
	

	
	
	
	
	
	
}
