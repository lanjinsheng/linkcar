package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.UserScoreDayStat;
import com.idata365.entity.UserTravelHistory;
import com.idata365.service.AddUserDayStatService;
import com.idata365.service.CalScoreUserDayService;



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
    @Autowired
    CalScoreUserDayService calScoreUserDayService;
 
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
			long taskFlag=System.currentTimeMillis();
			UserScoreDayStat task=new UserScoreDayStat();
			task.setTaskFlag(String.valueOf(taskFlag));
			List<UserScoreDayStat> list=calScoreUserDayService.getUserScoreDayTask(task);
			log.info("CalUserDayScoreTask do--list.size="+list.size());
				for(UserScoreDayStat userScoreDayStat:list) {
					try {
						boolean result=calScoreUserDayService.calScoreUserDay(userScoreDayStat);
					if(result) {
						calScoreUserDayService.updateSuccUserScoreDayTask(userScoreDayStat);
						 
					}else {
						if(userScoreDayStat.getTaskFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							userScoreDayStat.setTaskStatus(2);
						}
						calScoreUserDayService.updateFailUserScoreDayTask(userScoreDayStat);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						if(userScoreDayStat.getTaskFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							userScoreDayStat.setTaskStatus(2);
						}
						calScoreUserDayService.updateFailUserScoreDayTask(userScoreDayStat);
					}
				}
			pd=true;
		}
			
		}
		log.info("CalUserDayScoreTask end--");
	}  
}