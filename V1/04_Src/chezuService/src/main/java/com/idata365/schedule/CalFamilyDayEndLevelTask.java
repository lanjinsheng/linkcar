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
import com.idata365.service.CalFamilyDayOrderService;
import com.idata365.service.CalFamilyMonthAvgOrderService;
import com.idata365.service.CalFamilyMonthOrderService;
import com.idata365.service.CalFamilyPkService;
import com.idata365.service.CalScoreFamilyDayService;
import com.idata365.service.ConfigSystemTaskService;
import com.idata365.service.TaskFamilyDayEndService;
import com.idata365.service.TaskKeyLogService;



/**
 * 
    * @ClassName: CalFamilyDayEndLevelTask
    * @Description: TODO(等级更新处理,V2作废)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalFamilyDayEndLevelTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalFamilyDayEndLevelTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
   TaskFamilyDayEndService taskFamilyDayEndService;
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
		log.info("CalFamilyDayEndLevelTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
			List<TaskSystemScoreFlag> taskList=configSystemTaskService.getUnFinishFamilyLevelDayEnd();
			for(TaskSystemScoreFlag tf:taskList) {
			long taskFlag=System.currentTimeMillis();
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("CalFamilyDayEndLevelTask");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0){ pd=true;return;}
			
			
			TaskFamilyDayEnd task=new TaskFamilyDayEnd();
			task.setDaystamp(tf.getDaystamp());
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskFamilyDayEnd> list=taskFamilyDayEndService.getFamilyLevelDayEndTask(task);
			int level1=0,level2=0,level3=0;
			if(list.size()==0) {//无任务
				configSystemTaskService.finishFamilyLevelDayEndTask(tf);
				continue;
			}else {
				int max=taskFamilyDayEndService.maxOrderNo(tf);
				level1=(int)(max*0.1);
				level2=(int)(max*0.3);
				level3=max;
			}
			log.info("CalFamilyDayEndLevelTask do--list.size="+list.size());
			
				for(TaskFamilyDayEnd taskDayEnd:list) {
					try {
						boolean result=taskFamilyDayEndService.calLevel(taskDayEnd, level1, level2, level3);
						 
					if(result) {
						taskFamilyDayEndService.updateSuccFamilyDayEndTask(taskDayEnd);
					}else {
						taskFamilyDayEndService.updateFailFamilyDayEndTask(taskDayEnd);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						taskFamilyDayEndService.updateFailFamilyDayEndTask(taskDayEnd);
					}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalFamilyDayEndLevelTask 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalFamilyDayEndLevelTask end--");
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