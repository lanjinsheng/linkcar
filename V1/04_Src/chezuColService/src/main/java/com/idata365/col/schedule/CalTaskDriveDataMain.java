package com.idata365.col.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.col.entity.TaskDriveDataMain;
import com.idata365.col.service.TaskDriveDataMainService;



public class CalTaskDriveDataMain extends TimerTask { 
	private static Logger log = Logger.getLogger(CalTaskDriveDataMain.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    TaskDriveDataMainService taskDriveDataMainService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("CalTaskDriveDataMain start--");
 
		if(!pd || threadPool.getActiveCount()==3){
			return;
		}
		synchronized (lock){
		
		if(pd){
			pd=false;
			long taskFlag=System.currentTimeMillis();
			TaskDriveDataMain task=new TaskDriveDataMain();
			task.setTaskFlag(taskFlag);
			List<TaskDriveDataMain> list=taskDriveDataMainService.getDataMainTask(task);
			log.info("DriveUploadScanTask do--list.size="+list.size());
			for(TaskDriveDataMain ts:list) {
				try {
					if(taskDriveDataMainService.doTaskDriveDataMain(ts)) {
						taskDriveDataMainService.updateDriveDataMainSuccTask(ts);
					}else {
						taskDriveDataMainService.updateDriveDataMainFailTask(ts);
					}
				}catch(Exception e) {
					e.printStackTrace();
					log.error(e);
				 
					taskDriveDataMainService.updateDriveDataMainFailTask(ts);
				}
			}
			pd=true;
		}
			
		}
		log.info("CalTaskDriveDataMain end--");
	}  
}