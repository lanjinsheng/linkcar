package com.ljs.task;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ljs.service.DataService;
import com.ljs.util.SpringContextUtil;

public class MainExecutor extends TimerTask {  
    
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
	
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  
	
	
	  //在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		System.out.println(threadPool.toString());
		//查找设备
	}  
}