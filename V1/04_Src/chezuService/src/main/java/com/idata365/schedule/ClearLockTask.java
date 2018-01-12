package com.idata365.schedule;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.service.AddUserDayStatService;
import com.idata365.service.CalFamilyDayOrderService;
import com.idata365.service.CalFamilyMonthOrderService;
import com.idata365.service.CalFamilyPkService;
import com.idata365.service.CalScoreFamilyDayService;
import com.idata365.service.CalScoreService;
import com.idata365.service.SynDriveDataService;




public class ClearLockTask extends TimerTask { 
	private static Logger log = Logger.getLogger(ClearLockTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    SynDriveDataService dataService;
    @Autowired
    CalScoreService calScoreService;
    @Autowired
    AddUserDayStatService addUserDayStatService;
    
    @Autowired
    CalScoreFamilyDayService calScoreFamilyDayService;
    @Autowired
    CalFamilyPkService calFamilyPkService;
    @Autowired
    CalFamilyDayOrderService calFamilyDayOrderService;
    @Autowired
    CalFamilyMonthOrderService calFamilyMonthOrderService;
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
				calScoreService.clearLockTask();
				addUserDayStatService.clearLockTask();
				calFamilyPkService.clearLockTask();
				calFamilyDayOrderService.clearLockTask();
				calFamilyMonthOrderService.clearLockTask();
				calScoreFamilyDayService.clearLockTask();
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