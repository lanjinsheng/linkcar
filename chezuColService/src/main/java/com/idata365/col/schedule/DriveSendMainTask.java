package com.idata365.col.schedule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.col.entity.DriveDataEvent;
import com.idata365.col.entity.DriveDataMain;
import com.idata365.col.entity.UploadDataStatus;
import com.idata365.col.remote.ChezuDriveService;
import com.idata365.col.service.DataService;



public class DriveSendMainTask extends TimerTask { 
	private static Logger log = Logger.getLogger(DriveSendMainTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    DataService dataService;
    @Autowired
    ChezuDriveService chezuDriveService;
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
			List<Map<String,Object>> postList=new ArrayList<Map<String,Object>>();
			DriveDataMain drive=new DriveDataMain();
			drive.setTaskFlag(taskFlag);
			try {
				List<DriveDataMain> list=dataService.getSendDriveTask(drive);
				log.info("DriveSendMainTask do--list.size="+list.size());
				for(DriveDataMain dm:list) {
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("drive", dm);
					List<DriveDataEvent> events=dataService.listDriveEventByMainId(dm);
					map.put("events", events);
					postList.add(map);
					
				}
				boolean b=chezuDriveService.recieveDrive(postList);
				if(b) {
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