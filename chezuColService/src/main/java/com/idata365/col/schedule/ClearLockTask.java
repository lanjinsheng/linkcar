package com.idata365.col.schedule;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.col.remote.ChezuDriveService;
import com.idata365.col.service.DataService;



public class ClearLockTask extends TimerTask { 
	private static Logger log = Logger.getLogger(ClearLockTask.class);
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
		log.info("ClearLockTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
		
			try {
				dataService.clearLockTask();
			}catch(Exception e) {
				e.printStackTrace();
				log.error(e);
				
			}
			pd=true;
		}
			
		}
		log.info("ClearLockTask end--");
	}  
}