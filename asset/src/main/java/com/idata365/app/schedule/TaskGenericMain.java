package com.idata365.app.schedule;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.app.service.TaskGenericService;
import com.netflix.discovery.converters.Auto;



/**
 * 
    * @ClassName: AddUserDayStatMainTask
    * @Description: TODO(增加违章次数)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class TaskGenericMain extends TimerTask { 
	private static Logger log = Logger.getLogger(TaskGenericMain.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	@Autowired
	TaskGenericService taskGenericService;
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  

	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("TaskGenericMain start--");
 
		if(!pd){
			return;
		}
		if(threadPool.getActiveCount()==3) {
			return;
		}
		
		synchronized (lock){
		if(pd){
			pd=false;
			try {
				String taskFlag=UUID.randomUUID().toString().replaceAll("-", "");
				taskGenericService.lockTask(taskFlag);
				threadPool.execute(new TaskGenericThread(taskFlag));
			}catch(Exception e) {
				e.printStackTrace();
				log.info("TaskGenericMain 异常");
			}
			pd=true;
		}
			
		}
		log.info("TaskGenericMain end--");
	}  
	
	public static void main(String []args) {
		for(int i=0;i<1000;i++) {
			System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}
}