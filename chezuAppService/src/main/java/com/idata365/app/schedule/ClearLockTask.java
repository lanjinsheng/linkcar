package com.idata365.app.schedule;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.app.entity.TaskAwardInfoPush;
import com.idata365.app.service.TaskAchieveAddValueService;
import com.idata365.app.service.TaskAwardInfoPushService;
import com.idata365.app.service.TaskGiveUserAchieveService;
import com.idata365.app.service.TaskMessagePushService;
import com.idata365.app.service.TaskUserLotteryService;


public class ClearLockTask extends TimerTask { 
	private static Logger log = Logger.getLogger(ClearLockTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    TaskAchieveAddValueService taskAchieveAddValueService;
    @Autowired
    TaskGiveUserAchieveService taskGiveUserAchieveService;
    @Autowired
    TaskMessagePushService taskMessagePushService;
    @Autowired
    TaskAwardInfoPushService taskAwardInfoPushService;
    @Autowired
    TaskUserLotteryService taskUserLotteryService;
    
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
				taskAchieveAddValueService.clearLockTask();
				taskGiveUserAchieveService.clearLockTask();
				taskMessagePushService.clearLockTask();
				taskAwardInfoPushService.clearLockTask();
				taskUserLotteryService.clearLockTask();
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