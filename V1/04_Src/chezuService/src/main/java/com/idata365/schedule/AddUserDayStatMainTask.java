package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.CalDriveTask;
import com.idata365.entity.DriveScore;
import com.idata365.entity.UserTravelHistory;
import com.idata365.service.AddUserDayStatService;
import com.idata365.service.CalScoreService;



/**
 * 
    * @ClassName: AddUserDayStatMainTask
    * @Description: TODO(增加违章次数)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class AddUserDayStatMainTask extends TimerTask { 
	private static Logger log = Logger.getLogger(AddUserDayStatMainTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    AddUserDayStatService addUserDayStatService;
 
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("AddUserDayStatMainTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			long taskFlag=System.currentTimeMillis();
			UserTravelHistory task=new UserTravelHistory();
			task.setTaskFlag(String.valueOf(taskFlag));
			List<UserTravelHistory> list=addUserDayStatService.getTravelTask(task);
			log.info("AddUserDayStatMainTask do--list.size="+list.size());
				for(UserTravelHistory userTravelHistory:list) {
					try {
						boolean result=addUserDayStatService.addUserDayStat(userTravelHistory);
					if(result) {
						addUserDayStatService.updateSuccAddUserDayStatTask(userTravelHistory);
						 
					}else {
						if(userTravelHistory.getFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							userTravelHistory.setAddDayStatFlag(2);
						}
						addUserDayStatService.updateFailAddUserDayStatTask(userTravelHistory);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						if(userTravelHistory.getFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							userTravelHistory.setAddDayStatFlag(2);
						}
						addUserDayStatService.updateFailAddUserDayStatTask(userTravelHistory);
					}
				}
			pd=true;
		}
			
		}
		log.info("AddUserDayStatMainTask end--");
	}  
}