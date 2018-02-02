package com.idata365.app.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.app.entity.AwardBean;
import com.idata365.app.entity.TaskAchieveAddValue;
import com.idata365.app.entity.TaskAwardInfoPush;
import com.idata365.app.entity.TaskKeyLog;
import com.idata365.app.entity.TaskMessagePush;
import com.idata365.app.service.TaskAchieveAddValueService;
import com.idata365.app.service.TaskAwardInfoPushService;
import com.idata365.app.service.TaskKeyLogService;
import com.idata365.app.service.TaskMessagePushService;

 



/**
 * 
    * @ClassName: CalTaskAwardInfoPush
    * @Description: TODO(平均数)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalTaskAwardInfoPush extends TimerTask { 
	private static Logger log = Logger.getLogger(CalTaskAwardInfoPush.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
 
	@Autowired
	TaskAwardInfoPushService taskAwardInfoPushService;
    @Autowired
    TaskKeyLogService taskKeyLogService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("CalTaskAwardInfoPush start--");
 
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
			key.setTaskName("CalTaskAwardInfoPush");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0){ pd=true;return;}
			
			List<AwardBean> noSendMessageAwards=taskAwardInfoPushService.getNoSendMessageAwards();
			if(noSendMessageAwards!=null && noSendMessageAwards.size()>0) {
				for(AwardBean awardBean:noSendMessageAwards) {
					taskAwardInfoPushService.initTask(awardBean);
					taskAwardInfoPushService.updateAwardInfoSendType(awardBean.getId());
				}
			}
			
			TaskAwardInfoPush task=new TaskAwardInfoPush();
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskAwardInfoPush> taskAwardList=taskAwardInfoPushService.getAwardInfoPushTask(task);
			log.info("CalTaskAwardInfoPush do--list.size="+taskAwardList.size());
			
				for(TaskAwardInfoPush taskAwardInfoPush:taskAwardList) {
					try {
						boolean result=taskAwardInfoPushService.pushMessage(taskAwardInfoPush);
						 
					if(result) {
						taskAwardInfoPushService.updateSuccAwardInfoPushTask(taskAwardInfoPush);
					}else {
						taskAwardInfoPushService.updateFailAwardInfoPushTask(taskAwardInfoPush);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						taskAwardInfoPushService.updateFailAwardInfoPushTask(taskAwardInfoPush);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalTaskAwardInfoPush 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalTaskAwardInfoPush end--");
	}  
	
	public static void main(String []args) {
		 
	}
}