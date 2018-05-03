package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.TaskGameEnd;
import com.idata365.entity.TaskKeyLog;
import com.idata365.entity.TaskPowerLogs;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.service.CalGameEndService;
import com.idata365.service.ConfigSystemTaskService;
import com.idata365.service.PowerService;
import com.idata365.service.TaskKeyLogService;



/**
 * 
    * @ClassName: CalPowerTask
    * @Description: TODO(增加违章次数)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalPowerTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalPowerTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
 
    @Autowired
    PowerService powerService;
    @Autowired
    TaskKeyLogService taskKeyLogService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("CalPowerTask start--");
 
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
				key.setTaskName("CalPowerTask");
			    int hadKey=	taskKeyLogService.insertAppKey(key);
				if(hadKey==0) { pd=true;return;}//线程重复
				TaskPowerLogs task=new TaskPowerLogs();
				task.setTaskFlag(String.valueOf(taskFlag));
				List<TaskPowerLogs> list=powerService.getPowerLogsTask(task);
				log.info("CalPowerTask do--list.size="+list.size());
					for(TaskPowerLogs taskPowerLog:list) {
						try {
							boolean result=powerService.calPower(taskPowerLog);
						if(result) {
							powerService.updateSuccPowerLogsTask(taskPowerLog);
							 
						}else {
							powerService.updateFailPowerLogsTask(taskPowerLog);
						}
						}catch(Exception e) {
							e.printStackTrace();
							log.error(e);
							powerService.updateFailPowerLogsTask(taskPowerLog);
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
					log.info("CalPowerTask 异常");
				}
			pd=true;
		}
			
		}
		log.info("CalPowerTask end--");
	}  
	
	public static void main(String []args) {
		String timestamp="20180125";
		String yyyy=timestamp.substring(0, 4);
		String mm=timestamp.substring(4, 6);
		String dd=timestamp.substring(6, 8);
		System.out.println(yyyy+"-"+mm+"-"+dd);
	}
}