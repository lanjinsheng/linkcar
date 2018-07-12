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
import com.idata365.service.CarpoolService;
import com.idata365.service.ConfigSystemTaskService;
import com.idata365.service.TaskFamilyDayEndService;
import com.idata365.service.TaskKeyLogService;



/**
 * 
    * @ClassName: ClearYesterdayTask
    * @Description: TODO(昨日业务清理)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class ClearYesterdayTask extends TimerTask { 
	private static Logger log = Logger.getLogger(ClearYesterdayTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    ConfigSystemTaskService configSystemTaskService;
    @Autowired
    TaskKeyLogService taskKeyLogService;
    @Autowired
    CarpoolService carpoolService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("ClearYesterdayTask start--");
		if(!pd){
			return;
		}
		synchronized (lock){
			if(pd){
				pd=false;
				try {
				List<TaskSystemScoreFlag> taskList=configSystemTaskService.getUnFinishClearYesterday();
				for(TaskSystemScoreFlag tf:taskList) {
					carpoolService.clearTask(tf.getDaystamp());
					configSystemTaskService.finishClearYesterdayTask(tf);
				}
				}catch(Exception e) {
					e.printStackTrace();
				}
				pd=true;
			}
		}
		log.info("ClearYesterdayTask end--");
	}  
	
	 
}