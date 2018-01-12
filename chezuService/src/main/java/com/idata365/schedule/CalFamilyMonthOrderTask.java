package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.TaskFamilyDayScore;
import com.idata365.entity.TaskFamilyMonthOrder;
import com.idata365.entity.TaskFamilyDayOrder;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.service.CalFamilyDayOrderService;
import com.idata365.service.CalFamilyMonthOrderService;
import com.idata365.service.CalFamilyPkService;
import com.idata365.service.CalScoreFamilyDayService;
import com.idata365.service.ConfigSystemTaskService;



/**
 * 
    * @ClassName: AddUserDayStatMainTask
    * @Description: TODO(增加违章次数)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalFamilyMonthOrderTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalFamilyMonthOrderTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
   CalFamilyMonthOrderService calFamilyMonthOrderService;
    @Autowired
    ConfigSystemTaskService configSystemTaskService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("CalFamilyMonthOrderTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
			List<TaskSystemScoreFlag> taskList=configSystemTaskService.getUnFinishFamilyMonthOrder();
			for(TaskSystemScoreFlag tf:taskList) {
				String timestamp=tf.getDaystamp();
//				String yyyy=timestamp.substring(0, 4);
//				String mm=timestamp.substring(4, 6);
//				String dd=timestamp.substring(6, 8);
			long taskFlag=System.currentTimeMillis();
			TaskFamilyMonthOrder task=new TaskFamilyMonthOrder();
			task.setMonth(tf.getDaystamp().replaceAll("-", "").substring(0,6));
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskFamilyMonthOrder> list=calFamilyMonthOrderService.getFamilyMonthOrderTask(task);
			TaskFamilyMonthOrder preOrder=null;
			if(list.size()==0) {//无任务
				configSystemTaskService.finishConfigSystemFamilyMonthOrderTask(tf);
				continue;
			}else {
				preOrder=calFamilyMonthOrderService.getPre(list.get(0));
				if(preOrder!=null && preOrder.getTaskStatus()!=1) {
					continue;
				}
			}
			log.info("CalFamilyMonthOrderTask do--list.size="+list.size());
			
				for(TaskFamilyMonthOrder taskFamilyOrder:list) {
					try {
						boolean result=calFamilyMonthOrderService.calFamilyMonthOrder(preOrder,taskFamilyOrder);
						preOrder=taskFamilyOrder;
					if(result) {
						calFamilyMonthOrderService.updateSuccFamilyMonthOrderTask(taskFamilyOrder);
						 
					}else {
						if(taskFamilyOrder.getFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							taskFamilyOrder.setTaskStatus(2);
						}
						calFamilyMonthOrderService.updateFailFamilyMonthOrderTask(taskFamilyOrder);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						if(taskFamilyOrder.getFailTimes()>100) {
							//状态置为2，代表计算次数已经极限
							taskFamilyOrder.setTaskStatus(2);
						}
						calFamilyMonthOrderService.updateFailFamilyMonthOrderTask(taskFamilyOrder);
					}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalFamilyMonthOrderTask 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalFamilyMonthOrderTask end--");
	}  
	
	public static void main(String []args) {
		String timestamp="20180125";
		String yyyy=timestamp.substring(0, 4);
		String mm=timestamp.substring(4, 6);
		String dd=timestamp.substring(6, 8);
		System.out.println(yyyy+"-"+mm+"-"+dd);
	}
}