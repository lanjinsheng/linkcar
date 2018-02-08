package com.idata365.app.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.app.entity.TaskAchieveAddValue;
import com.idata365.app.entity.TaskKeyLog;
import com.idata365.app.entity.TaskMessagePush;
import com.idata365.app.entity.TaskUserLottery;
import com.idata365.app.service.TaskAchieveAddValueService;
import com.idata365.app.service.TaskKeyLogService;
import com.idata365.app.service.TaskMessagePushService;
import com.idata365.app.service.TaskUserLotteryService;

 



/**
 * 
    * @ClassName: CalTaskUserLottery
    * @Description: TODO(平均数)
    * @author LanYeYe
    * @date 2018年2月31日
    *
 */
public class CalTaskUserLottery extends TimerTask { 
	private static Logger log = Logger.getLogger(CalTaskUserLottery.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
 
	@Autowired
	TaskUserLotteryService taskUserLotteryService;
    @Autowired
    TaskKeyLogService taskKeyLogService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("CalTaskUserLottery start--");
 
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
			key.setTaskName("CalTaskUserLottery");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0){ pd=true;return;}
			
			
			TaskUserLottery task=new TaskUserLottery();
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskUserLottery> list=taskUserLotteryService.geUserLotteryTask(task);
			log.info("CalTaskUserLottery do--list.size="+list.size());
			
				for(TaskUserLottery taskUserLottery:list) {
					try {
						boolean result=taskUserLotteryService.calUserTotteryTask(taskUserLottery);
						 
					if(result) {
						taskUserLotteryService.updateSuccUserLotteryTask(taskUserLottery);
					}else {
						taskUserLotteryService.updateFailUserLotteryTask(taskUserLottery);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						taskUserLotteryService.updateFailUserLotteryTask(taskUserLottery);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalTaskUserLottery 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalTaskMessagePush end--");
	}  
	
	public static void main(String []args) {
		 
	}
}