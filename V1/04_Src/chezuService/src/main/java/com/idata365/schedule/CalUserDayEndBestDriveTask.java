package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.TaskFamilyDayScore;
import com.idata365.entity.TaskFamilyMonthAvgOrder;
import com.idata365.entity.TaskFamilyMonthOrder;
import com.idata365.entity.TaskFamilyDayEnd;
import com.idata365.entity.TaskFamilyDayOrder;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.entity.TaskKeyLog;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.entity.TaskUserDayEnd;
import com.idata365.service.CalFamilyDayOrderService;
import com.idata365.service.CalFamilyMonthAvgOrderService;
import com.idata365.service.CalFamilyMonthOrderService;
import com.idata365.service.CalFamilyPkService;
import com.idata365.service.CalScoreFamilyDayService;
import com.idata365.service.ConfigSystemTaskService;
import com.idata365.service.TaskFamilyDayEndService;
import com.idata365.service.TaskKeyLogService;
import com.idata365.service.TaskUserDayEndService;



/**
 * 
    * @ClassName: CalUserDayEndBestDriveTask
    * @Description: TODO(最佳驾驶 成就作废)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalUserDayEndBestDriveTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalUserDayEndBestDriveTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    TaskUserDayEndService taskUserDayEndService;
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
		log.info("CalUserDayEndBestDriveTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
			List<TaskSystemScoreFlag> taskList=configSystemTaskService.getUnFinishUserBestDriveDayEnd();
			for(TaskSystemScoreFlag tf:taskList) {
			long taskFlag=System.currentTimeMillis();
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("CalUserDayEndBestDriveTask");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0){ pd=true;return;}
			
			
			TaskUserDayEnd task=new TaskUserDayEnd();
			task.setDaystamp(tf.getDaystamp());
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskUserDayEnd> list=taskUserDayEndService.getUserBestDriveDayEndTask(task);
			if(list.size()==0) {//无任务
				configSystemTaskService.finishUserBestDriveDayEndTask(tf);
				continue;
			}else {
				 
			}
			log.info("CalUserDayEndBestDriveTask do--list.size="+list.size());
			
				for(TaskUserDayEnd taskDayEnd:list) {
					try {
						boolean result=taskUserDayEndService.calBestDrive(taskDayEnd);
						 
					if(result) {
						taskUserDayEndService.updateSuccUserDayEndTask(taskDayEnd);
					}else {
						taskUserDayEndService.updateFailUserDayEndTask(taskDayEnd);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						taskUserDayEndService.updateFailUserDayEndTask(taskDayEnd);
					}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalUserDayEndBestDriveTask 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalUserDayEndBestDriveTask end--");
	}  
	
	public static void main(String []args) {
		int level1=0,level2=0,level3=0;
		int max=10;
		level1=(int)(max*0.1);
		level2=(int)(max*0.3);
		level3=max;
		System.out.println(level1+"-"+level2+"-"+level3);
	}
}