package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.TaskGameEnd;
import com.idata365.entity.TaskKeyLog;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.entity.UserScoreDayStat;
import com.idata365.service.CalGameEndService;
import com.idata365.service.CalScoreUserDayService;
import com.idata365.service.ConfigSystemTaskService;
import com.idata365.service.TaskKeyLogService;



/**
 * 
    * @ClassName: AddUserDayStatMainTask
    * @Description: TODO(增加违章次数)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalGameEndTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalGameEndTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    CalGameEndService calGameEndService;
    @Autowired
    ConfigSystemTaskService configSystemTaskService;
    @Autowired
    TaskKeyLogService taskKeyLogService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("CalGameEndTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
			List<TaskSystemScoreFlag> taskList=configSystemTaskService.getUnFinishGameEnd();
			for(TaskSystemScoreFlag tf:taskList) {
			long taskFlag=System.currentTimeMillis();
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("CalGameEndTask");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0) { pd=true;return;}//线程重复
		 
			TaskGameEnd task=new TaskGameEnd();
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskGameEnd> list=calGameEndService.geGameEndTask(task);
			if(list.size()==0) {//无任务
				configSystemTaskService.finishConfigSystemGameEndTask(tf);
			}
			log.info("CalGameEndTask do--list.size="+list.size());
				for(TaskGameEnd taskGameEnd:list) {
					try {
						boolean result=calGameEndService.calGameEnd(taskGameEnd);
					if(result) {
						calGameEndService.updateSuccGameEndTask(taskGameEnd);
						 
					}else {
						calGameEndService.updateGameEndFailTask(taskGameEnd);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						calGameEndService.updateGameEndFailTask(taskGameEnd);
					}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalGameEndTask 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalGameEndTask end--");
	}  
	
	public static void main(String []args) {
		String timestamp="20180125";
		String yyyy=timestamp.substring(0, 4);
		String mm=timestamp.substring(4, 6);
		String dd=timestamp.substring(6, 8);
		System.out.println(yyyy+"-"+mm+"-"+dd);
	}
}