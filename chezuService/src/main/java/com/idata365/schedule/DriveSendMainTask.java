package com.idata365.schedule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.DriveDataMain;
import com.idata365.entity.TaskKeyLog;
import com.idata365.service.SynDriveDataService;
import com.idata365.service.TaskKeyLogService;



/**
 * 
    * @ClassName: DriveSendMainTask
    * @Description: TODO(同步行程任务给业务层)
    * @author LanYeYe
    * @date 2017年12月28日
    *
 */
public class DriveSendMainTask extends TimerTask { 
	private static Logger log = Logger.getLogger(DriveSendMainTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    SynDriveDataService dataService;
    @Autowired
    TaskKeyLogService taskKeyLogService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("DriveSendMainTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
		
			long taskFlag=System.currentTimeMillis();
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("CalFamilyMonthOrderTask");
		    int hadKey=	taskKeyLogService.insertColKey(key);
			if(hadKey==0) { pd=true;return;}
//			List<Map<String,Object>> postList=new ArrayList<Map<String,Object>>();
			DriveDataMain drive=new DriveDataMain();
			drive.setTaskFlag(taskFlag);
			try {
				List<DriveDataMain> list=dataService.getSendDriveTask(drive);
				log.info("DriveSendMainTask do--list.size="+list.size());
				boolean b=dataService.recieveDrive(list);
				if(b) {
					dataService.addCalTask(list);
					dataService.updateSuccSendDriveTask(drive);
				}else {
					dataService.updateFailSendDriveTask(drive);
				}
			}catch(Exception e) {
				log.error(e);
				dataService.updateFailSendDriveTask(drive);
			}
			pd=true;
		}
			
		}
		log.info("DriveSendMainTask end--");
	}  
}