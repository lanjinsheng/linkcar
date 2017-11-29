package com.idata365.col.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.col.entity.UploadDataStatus;
import com.idata365.col.service.DataService;



public class DriveUploadScanTask extends TimerTask { 
	private static Logger log = Logger.getLogger(DriveUploadScanTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    DataService dataService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("DriveUploadScanTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			long taskFlag=System.currentTimeMillis();
			UploadDataStatus status=new UploadDataStatus();
			status.setTaskFlag(taskFlag);
			long createTimeSS=30*60*1000+taskFlag;
			status.setCreateTimeSS(createTimeSS);
			List<UploadDataStatus> list=dataService.getUploadDataStatusTask(status);
			log.info("DriveUploadScanTask do--list.size="+list.size());
			for(UploadDataStatus us:list) {
				threadPool.execute(new DatasDealTask(us.getUserId(),us.getHabitId(),us.getTaskFlag(),us.getHadSensorData(),us.getId()));
			}
			pd=true;
		}
			
		}
		log.info("DriveUploadScanTask end--");
	}  
}