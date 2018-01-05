package com.idata365.schedule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.CalDriveTask;
import com.idata365.entity.DriveDataMain;
import com.idata365.entity.DriveScore;
import com.idata365.service.CalScoreService;
import com.idata365.service.SynDriveDataService;



/**
 * 
    * @ClassName: DriveSendMainTask
    * @Description: TODO(同步行程任务给业务层)
    * @author LanYeYe
    * @date 2017年12月28日
    *
 */
public class CalScoreMainTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalScoreMainTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    CalScoreService calScoreService;
 
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("CalScoreMainTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
		
			long taskFlag=System.currentTimeMillis();
			CalDriveTask task=new CalDriveTask();
			task.setTaskFlag(taskFlag);
			List<CalDriveTask> list=calScoreService.getCalScoreTask(task);
			log.info("CalDriveTask do--list.size="+list.size());
				for(CalDriveTask calDriveTask:list) {
					try {
						List<DriveScore> scores=calScoreService.calScoreByUHInsertDb(calDriveTask.getUserId(), calDriveTask.getHabitId());
					if(scores!=null) {
						calScoreService.updateSuccCalScoreTask(calDriveTask);
					}else {
						if(calDriveTask.getCalFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							calDriveTask.setCalStatus(2);
						}
						calScoreService.updateFailCalScoreTask(calDriveTask);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						if(calDriveTask.getCalFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							calDriveTask.setCalStatus(2);
						}
						calScoreService.updateFailCalScoreTask(calDriveTask);
					}
					}
			pd=true;
		}
			
		}
		log.info("CalScoreMainTask end--");
	}  
}