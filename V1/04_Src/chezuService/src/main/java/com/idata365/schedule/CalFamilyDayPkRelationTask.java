package com.idata365.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.TaskFamilyPkRelation;
import com.idata365.entity.TaskKeyLog;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.service.CalFamilyPkRelationService;
import com.idata365.service.ConfigSystemTaskService;
import com.idata365.service.TaskKeyLogService;
import com.idata365.util.DateTools;



/**
 * 
    * @ClassName: CalFamilyDayPkRelationTask
    * @Description: TODO(暂停使用)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class CalFamilyDayPkRelationTask extends TimerTask { 
	private static Logger log = Logger.getLogger(CalFamilyDayPkRelationTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
    @Autowired
    CalFamilyPkRelationService calFamilyPkRelationService;
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
		log.info("CalFamilyDayPkRelationTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
				String day=DateTools.getCurDate("yyyy-MM-dd");
				List<TaskSystemScoreFlag> taskList=configSystemTaskService.getUnFinishPKRelation();
			for(TaskSystemScoreFlag tf:taskList) {
				String timestamp=tf.getDaystamp();
				long taskFlag=System.currentTimeMillis();
				TaskKeyLog key=new TaskKeyLog();
				key.setTaskFlag(String.valueOf(taskFlag));
				key.setTaskName("CalFamilyDayPkRelationTask");
			    int hadKey=	taskKeyLogService.insertAppKey(key);
				if(hadKey==0) { pd=true;return;}
				TaskFamilyPkRelation task=new TaskFamilyPkRelation();
				task.setDaystamp(timestamp);
				task.setTaskFlag(String.valueOf(taskFlag));
				List<TaskFamilyPkRelation> list=calFamilyPkRelationService.getFamilyPkRelationTask(task);
				if(list.size()==0) {//无任务
					configSystemTaskService.finishPKRelation(tf);
				}else {
				        log.info("CalFamilyDayPkRelationTask do--list.size="+list.size());
						try {
							boolean result=calFamilyPkRelationService.calFamilyPkRelation(list,day);
							if(result) {
								calFamilyPkRelationService.updateSuccFamilyPkRelationTask(task);
								 
							}else {
								//状态置为2，代表计算次数已经极限
								task.setTaskStatus(2);
								calFamilyPkRelationService.updateFailFamilyPkRelationTask(task);
							}
						}catch(Exception e) {
							e.printStackTrace();
							log.error(e);
							calFamilyPkRelationService.updateFailFamilyPkRelationTask(task);
						}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalFamilyDayPkRelationTask 异常");
			}
			pd=true;
		}
			
		}
		log.info("CalFamilyDayPkRelationTask end--");
	}  
	
	public static void main(String []args) {
		String timestamp="20180125";
		String yyyy=timestamp.substring(0, 4);
		String mm=timestamp.substring(4, 6);
		String dd=timestamp.substring(6, 8);
		System.out.println(yyyy+"-"+mm+"-"+dd);
	}
}