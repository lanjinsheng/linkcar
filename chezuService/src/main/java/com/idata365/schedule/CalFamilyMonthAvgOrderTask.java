package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.TaskFamilyDayScore;
import com.idata365.entity.TaskFamilyMonthAvgOrder;
import com.idata365.entity.TaskFamilyMonthOrder;
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
import com.idata365.service.TaskKeyLogService;



/**
 * 
    * @ClassName: CalFamilyMonthAvgOrderTask
    * @Description: TODO(平均数)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalFamilyMonthAvgOrderTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalFamilyMonthAvgOrderTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
   CalFamilyMonthAvgOrderService calFamilyMonthAvgOrderService;
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
		log.info("CalFamilyMonthAvgOrderTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
			List<TaskSystemScoreFlag> taskList=configSystemTaskService.getUnFinishFamilyMonthAvgOrder();
			for(TaskSystemScoreFlag tf:taskList) {
				String timestamp=tf.getDaystamp();
//				String yyyy=timestamp.substring(0, 4);
//				String mm=timestamp.substring(4, 6);
//				String dd=timestamp.substring(6, 8);
			long taskFlag=System.currentTimeMillis();
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("CalFamilyMonthAvgOrderTask");
		    int hadKey=	taskKeyLogService.insertAppKey(key);
			if(hadKey==0){ pd=true;return;}
			TaskFamilyMonthAvgOrder task=new TaskFamilyMonthAvgOrder();
			task.setMonth(tf.getDaystamp().replaceAll("-", "").substring(0,6));
			task.setTaskFlag(String.valueOf(taskFlag));
			task.setStartDay(tf.getStartDay());
			task.setEndDay(tf.getEndDay());
			List<TaskFamilyMonthAvgOrder> list=calFamilyMonthAvgOrderService.getFamilyMonthAvgOrderTask(task);
			TaskFamilyMonthAvgOrder preOrder=null;
			if(list.size()==0) {//无任务
				configSystemTaskService.finishConfigSystemFamilyMonthAvgOrderTask(tf);
				continue;
			}else {
				preOrder=calFamilyMonthAvgOrderService.getAvgPre(list.get(0));
				if(preOrder!=null && preOrder.getTaskStatus()!=1) {
					continue;
				}
			}
			log.info("CalFamilyMonthAvgOrderTask do--list.size="+list.size());
			
				for(TaskFamilyMonthAvgOrder taskFamilyMonthAvgOrder:list) {
					try {
						boolean result=calFamilyMonthAvgOrderService.calFamilyMonthAvgOrder(preOrder,taskFamilyMonthAvgOrder);
						preOrder=taskFamilyMonthAvgOrder;
					if(result) {
						calFamilyMonthAvgOrderService.updateSuccFamilyMonthAvgOrderTask(taskFamilyMonthAvgOrder);
						 
					}else {
						if(taskFamilyMonthAvgOrder.getFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							taskFamilyMonthAvgOrder.setTaskStatus(2);
						}else {
							taskFamilyMonthAvgOrder.setTaskStatus(0);
						}
						calFamilyMonthAvgOrderService.updateFailFamilyMonthAvgOrderTask(taskFamilyMonthAvgOrder);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						if(taskFamilyMonthAvgOrder.getFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							taskFamilyMonthAvgOrder.setTaskStatus(2);
						}else {
							taskFamilyMonthAvgOrder.setTaskStatus(0);
						}
						calFamilyMonthAvgOrderService.updateFailFamilyMonthAvgOrderTask(taskFamilyMonthAvgOrder);
					}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalFamilyMonthAvgOrderTask 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalFamilyMonthAvgOrderTask end--");
	}  
	
	public static void main(String []args) {
		String timestamp="20180125";
		String yyyy=timestamp.substring(0, 4);
		String mm=timestamp.substring(4, 6);
		String dd=timestamp.substring(6, 8);
		System.out.println(yyyy+"-"+mm+"-"+dd);
	}
}