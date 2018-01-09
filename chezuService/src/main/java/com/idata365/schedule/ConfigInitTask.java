package com.idata365.schedule;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.service.ConfigSystemTaskService;



/**
 * 
    * @ClassName: DriveSendMainTask
    * @Description: TODO(同步行程任务给业务层)
    * @author LanYeYe
    * @date 2017年12月28日
    *
 */
public class ConfigInitTask extends TimerTask { 
	private static Logger log = Logger.getLogger(ConfigInitTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	@Autowired
	ConfigSystemTaskService configSystemTaskService;
	
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