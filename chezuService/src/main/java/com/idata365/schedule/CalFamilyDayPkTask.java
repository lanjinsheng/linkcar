package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.TaskFamilyDayScore;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.entity.TaskKeyLog;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.service.CalFamilyPkService;
import com.idata365.service.CalScoreFamilyDayService;
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
public class CalFamilyDayPkTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalFamilyDayPkTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    CalFamilyPkService calFamilyPkService;
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
		log.info("CalFamilyDayPkTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
			List<TaskSystemScoreFlag> taskList=configSystemTaskService.getUnFinishFamilyPk();
			for(TaskSystemScoreFlag tf:taskList) {
				String timestamp=tf.getDaystamp();
//				String yyyy=timestamp.substring(0, 4);
//				String mm=timestamp.substring(4, 6);
//				String dd=timestamp.substring(6, 8);
			long taskFlag=System.currentTimeMillis();
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("CalFamilyDayPkTask");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0) { pd=true;return;}
			TaskFamilyPk task=new TaskFamilyPk();
			task.setDaystamp(timestamp);
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskFamilyPk> list=calFamilyPkService.getFamilyPkTask(task);
			if(list.size()==0) {//无任务
				configSystemTaskService.finishConfigSystemFamilyPkTask(tf);
			}
			log.info("CalFamilyDayPkTask do--list.size="+list.size());
				for(TaskFamilyPk taskFamilyPk:list) {
					try {
						boolean result=calFamilyPkService.calFamilyPk(taskFamilyPk);
					if(result) {
						calFamilyPkService.updateSuccFamilyPkTask(taskFamilyPk);
						 
					}else {
						if(taskFamilyPk.getFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							taskFamilyPk.setTaskStatus(2);
						}
						calFamilyPkService.updateFailFamilyPkTask(taskFamilyPk);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						if(taskFamilyPk.getFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							taskFamilyPk.setTaskStatus(2);
						}
						calFamilyPkService.updateFailFamilyPkTask(taskFamilyPk);
					}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalFamilyDayPkTask 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalFamilyDayPkTask end--");
	}  
	
	public static void main(String []args) {
		String timestamp="20180125";
		String yyyy=timestamp.substring(0, 4);
		String mm=timestamp.substring(4, 6);
		String dd=timestamp.substring(6, 8);
		System.out.println(yyyy+"-"+mm+"-"+dd);
	}
}