package com.idata365.app.schedule;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.app.service.TaskAutoAddService;

public class TaskAutoAdd extends TimerTask { 
	private static Logger log = Logger.getLogger(TaskAutoAdd.class);
	@Autowired
	TaskAutoAddService taskAutoAddService;
	
	private static Object lock = new Object();
	public static boolean pd=true;
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool=null;  
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
		this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("TaskAutoAdd start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
				//扫描进行快照任务
				taskAutoAddService.snapShotTaskAdd();
				//扫描进行power发放任务
				taskAutoAddService.initUserDayReward();
			}catch(Exception e) {
				e.printStackTrace();
			}
			pd=true;
		}
			
		}
		log.info("TaskAutoAdd end--");
	}  
}