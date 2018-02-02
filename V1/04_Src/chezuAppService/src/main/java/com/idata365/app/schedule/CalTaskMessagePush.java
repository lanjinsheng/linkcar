package com.idata365.app.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.app.entity.TaskAchieveAddValue;
import com.idata365.app.entity.TaskKeyLog;
import com.idata365.app.entity.TaskMessagePush;
import com.idata365.app.service.TaskAchieveAddValueService;
import com.idata365.app.service.TaskKeyLogService;
import com.idata365.app.service.TaskMessagePushService;

 



/**
 * 
    * @ClassName: CalTaskMessagePush
    * @Description: TODO(平均数)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalTaskMessagePush extends TimerTask { 
	private static Logger log = Logger.getLogger(CalTaskMessagePush.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
 
	@Autowired
	TaskMessagePushService taskMessagePushService;
    @Autowired
    TaskKeyLogService taskKeyLogService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("CalTaskMessagePush start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
				
			long taskFlag=System.currentTimeMillis();
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("CalTaskMessagePush");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0){ pd=true;return;}
			
			
			TaskMessagePush task=new TaskMessagePush();
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskMessagePush> list=taskMessagePushService.getMessagePushTask(task);
			log.info("CalTaskMessagePush do--list.size="+list.size());
			
				for(TaskMessagePush taskMessagePush:list) {
					try {
						boolean result=taskMessagePushService.pushMessage(taskMessagePush);
						 
					if(result) {
						taskMessagePushService.updateSuccMessagePushTask(taskMessagePush);
					}else {
						taskMessagePushService.updateFailMessagePushTask(taskMessagePush);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						taskMessagePushService.updateFailMessagePushTask(taskMessagePush);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalTaskMessagePush 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalTaskMessagePush end--");
	}  
	
	public static void main(String []args) {
		 
	}
}