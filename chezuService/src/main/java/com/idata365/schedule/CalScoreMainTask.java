package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.CalDriveTask;
import com.idata365.entity.DriveDataMain;
import com.idata365.entity.DriveScore;
import com.idata365.entity.TaskKeyLog;
import com.idata365.mapper.col.DriveDataMainMapper;
import com.idata365.service.CalScoreService;
import com.idata365.service.DriveMainColService;
import com.idata365.service.TaskKeyLogService;



/**
 * 
    * @ClassName: CalScoreMainTask
    * @Description: TODO(通过DriveSendMain将采集层驾驶数据同步给用户后,进行驾驶数据业务计分)
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
    @Autowired
    DriveMainColService driveMainColService;
    @Autowired
    TaskKeyLogService taskKeyLogService;
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
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("CalScoreMainTask");
		    int hadKey=	taskKeyLogService.insertColKey(key);
			if(hadKey==0) { pd=true;return;}
			CalDriveTask task=new CalDriveTask();
			task.setTaskFlag(taskFlag);
			List<CalDriveTask> list=calScoreService.getCalScoreTask(task);
			log.info("CalDriveTask do--list.size="+list.size());
				for(CalDriveTask calDriveTask:list) {
					try {
						DriveDataMain driveDatas=  driveMainColService.getDriveMain(calDriveTask.getUserId(), calDriveTask.getHabitId());
						List<DriveScore> scores=calScoreService.calScoreByUHInsertDb(calDriveTask.getUserId(), calDriveTask.getHabitId(),driveDatas);
					if(scores!=null) {
						calScoreService.updateSuccCalScoreTask(calDriveTask);
					}else {
					 
						calScoreService.updateFailCalScoreTask(calDriveTask);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						calScoreService.updateFailCalScoreTask(calDriveTask);
					}
					}
			pd=true;
		}
			
		}
		log.info("CalScoreMainTask end--");
	}  
}