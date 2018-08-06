package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.TaskFamilyDayScore;
import com.idata365.entity.TaskKeyLog;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.service.CalScoreFamilyDayService;
import com.idata365.service.CalScoreFamilyDayServiceV2;
import com.idata365.service.ConfigSystemTaskService;
import com.idata365.service.TaskKeyLogService;



/**
 * 
    * @ClassName: CalFamilyDayScoreTask
    * @Description: TODO(俱乐部日分统计)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalFamilyDayScoreTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalFamilyDayScoreTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
	
//    @Autowired
//    CalScoreFamilyDayService calScoreFamilyDayService;
    @Autowired
    CalScoreFamilyDayServiceV2 calScoreFamilyDayService;
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
		log.info("CalFamilyDayScoreTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
			List<TaskSystemScoreFlag> taskList=configSystemTaskService.getUnFinishFamilyDayScore();
			for(TaskSystemScoreFlag tf:taskList) {
				String timestamp=tf.getDaystamp();
//				String yyyy=timestamp.substring(0, 4);
//				String mm=timestamp.substring(4, 6);
//				String dd=timestamp.substring(6, 8);
			long taskFlag=System.currentTimeMillis();
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("CalFamilyDayScoreTask");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0) { pd=true;return;}
			TaskFamilyDayScore task=new TaskFamilyDayScore();
			task.setDaystamp(timestamp);
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskFamilyDayScore> list=calScoreFamilyDayService.getFamilyScoreDayTask(task);
			if(list.size()==0) {//无任务
				configSystemTaskService.finishConfigSystemFamilyScoreTask(tf);
			}
			log.info("CalFamilyDayScoreTask do--list.size="+list.size());
				for(TaskFamilyDayScore taskFamilyDayScore:list) {
					try {
						boolean result=calScoreFamilyDayService.calScoreFamilyDay(taskFamilyDayScore,tf.getStartDay(),tf.getEndDay());
					if(result) {
						calScoreFamilyDayService.updateSuccFamilyScoreDayTask(taskFamilyDayScore);
						 
					}else {
						calScoreFamilyDayService.updateFailFamilyScoreDayTask(taskFamilyDayScore);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						
						calScoreFamilyDayService.updateFailFamilyScoreDayTask(taskFamilyDayScore);
					}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalFamilyDayScoreTask 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalFamilyDayScoreTask end--");
	}  
	
	public static void main(String []args) {
		String timestamp="20180125";
		String yyyy=timestamp.substring(0, 4);
		String mm=timestamp.substring(4, 6);
		String dd=timestamp.substring(6, 8);
		System.out.println(yyyy+"-"+mm+"-"+dd);
	}
}