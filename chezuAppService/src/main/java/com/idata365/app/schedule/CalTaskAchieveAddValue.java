package com.idata365.app.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.app.entity.TaskAchieveAddValue;
import com.idata365.app.entity.TaskKeyLog;
import com.idata365.app.service.TaskAchieveAddValueService;
import com.idata365.app.service.TaskKeyLogService;

 



/**
 * 
    * @ClassName: CalFamilyMonthAvgOrderTask
    * @Description: TODO(平均数)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalTaskAchieveAddValue extends TimerTask { 
	private static Logger log = Logger.getLogger(CalTaskAchieveAddValue.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
 
	@Autowired
	TaskAchieveAddValueService taskAchieveAddValueService;
    @Autowired
    TaskKeyLogService taskKeyLogService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("CalTaskAchieveAddValue start--");
 
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
			key.setTaskName("CalTaskAchieveAddValue");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0){ pd=true;return;}
			
			
			TaskAchieveAddValue task=new TaskAchieveAddValue();
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskAchieveAddValue> list=taskAchieveAddValueService.getAchieveAddValueTask(task);
			log.info("CalTaskAchieveAddValue do--list.size="+list.size());
			
				for(TaskAchieveAddValue taskAchieveAdd:list) {
					try {
						boolean result=taskAchieveAddValueService.calAchieve(taskAchieveAdd);
						 
					if(result) {
						taskAchieveAddValueService.updateSuccAchieveAddValueTask(taskAchieveAdd);
					}else {
						taskAchieveAddValueService.updateFailAchieveAddValueTask(taskAchieveAdd);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						taskAchieveAddValueService.updateFailAchieveAddValueTask(taskAchieveAdd);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalTaskAchieveAddValue 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalTaskAchieveAddValue end--");
	}  
	
	public static void main(String []args) {
		 
	}
}