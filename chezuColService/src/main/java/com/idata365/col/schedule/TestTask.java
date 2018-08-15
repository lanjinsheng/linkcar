package com.idata365.col.schedule;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.col.remote.ChezuDriveService;
import com.idata365.col.service.DataService;



public class TestTask extends TimerTask { 
	private static Logger log = Logger.getLogger(TestTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    DataService dataService;
    @Autowired
    ChezuDriveService chezuDriveService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("debug start--");
		 while(true) {
			  System.out.println(UUID.randomUUID().toString()+UUID.randomUUID().toString()+UUID.randomUUID().toString());
		  }
	}  
}