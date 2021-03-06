package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.TaskKeyLog;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.entity.UserScoreDayStat;
import com.idata365.service.CalScoreUserDayService;
import com.idata365.service.CalScoreUserDayServiceV2;
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
public class CalUserDayScoreTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalUserDayScoreTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
//    @Autowired
//    CalScoreUserDayService calScoreUserDayService;
    @Autowired
    CalScoreUserDayServiceV2 calScoreUserDayService;
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
		log.info("CalUserDayScoreTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
			List<TaskSystemScoreFlag> taskList=configSystemTaskService.getUnFinishUserDayScore();
			for(TaskSystemScoreFlag tf:taskList) {
				String timestamp=tf.getDaystamp();
//				String yyyy=timestamp.substring(0, 4);
//				String mm=timestamp.substring(4, 6);
//				String dd=timestamp.substring(6, 8);
			long taskFlag=System.currentTimeMillis();
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("CalUserDayScoreTask");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0) { pd=true;return;}
			UserScoreDayStat task=new UserScoreDayStat();
			task.setDaystamp(timestamp);
			task.setTaskFlag(String.valueOf(taskFlag));
			List<UserScoreDayStat> list=calScoreUserDayService.getUserScoreDayTask(task);
			if(list.size()==0) {//无任务
				configSystemTaskService.finishConfigSystemUserScoreTask(tf);
			}
			log.info("CalUserDayScoreTask do--list.size="+list.size());
				for(UserScoreDayStat userScoreDayStat:list) {
					try {
						boolean result=calScoreUserDayService.calScoreUserDay(userScoreDayStat);
					if(result) {
						calScoreUserDayService.updateUserDayScore(userScoreDayStat);
						calScoreUserDayService.updateSuccUserScoreDayTask(userScoreDayStat);
						 
					}else {
						if(userScoreDayStat.getTaskFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							userScoreDayStat.setTaskStatus(2);
						}else {
							userScoreDayStat.setTaskStatus(0);
						}
						calScoreUserDayService.updateFailUserScoreDayTask(userScoreDayStat);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						if(userScoreDayStat.getTaskFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							userScoreDayStat.setTaskStatus(2);
						}else {
							userScoreDayStat.setTaskStatus(0);
						}
						calScoreUserDayService.updateFailUserScoreDayTask(userScoreDayStat);
					}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalUserDayScoreTask 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalUserDayScoreTask end--");
	}  
	
	public static void main(String []args) {
		String timestamp="20180125";
		String yyyy=timestamp.substring(0, 4);
		String mm=timestamp.substring(4, 6);
		String dd=timestamp.substring(6, 8);
		System.out.println(yyyy+"-"+mm+"-"+dd);
	}
}