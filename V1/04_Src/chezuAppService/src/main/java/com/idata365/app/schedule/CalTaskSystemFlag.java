package com.idata365.app.schedule;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.app.service.TaskKeyLogService;
import com.idata365.app.service.TaskSystemFlagService;




/**
 * 
    * @ClassName: DriveSendMainTask
    * @Description: TODO(每日任务执行控制器)
    * @author LanYeYe
    * @date 2017年12月28日
    *
 */
public class CalTaskSystemFlag extends TimerTask { 
	private static Logger log = Logger.getLogger(CalTaskSystemFlag.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	@Autowired
	TaskSystemFlagService configSystemTaskService;
    @Autowired
    TaskKeyLogService taskKeyLogService;
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
   
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("ConfigInitTask start--");
 
		if(!pd){
			log.info("ConfigInitTask in working return--");
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			configSystemTaskService.configSystemTask();
			pd=true;
		}
			
		}
		log.info("ConfigInitTask end--");
	}  
}