package com.ljs.task;

import java.util.HashMap;
import java.util.Map;

public class TaskMethods {
	private static Map <String,Object>methodsAllowMap=new HashMap<String, Object>();
	public TaskMethods(String methodsAllow){
		if(methodsAllow!=null && !methodsAllow.equals("")){
			String []methods=methodsAllow.split(",");
			for(int i=0;i<methods.length;i++){
				methodsAllowMap.put(methods[i], 1);
			}
		}
	}
	public static boolean  hasMethod(String key){
		if(methodsAllowMap.get(key)!=null)
			return true;
		return false;
	}
}
