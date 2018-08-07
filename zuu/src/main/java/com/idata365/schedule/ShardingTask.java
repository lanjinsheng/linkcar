package com.idata365.schedule;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.service.StatisticsService;

 



/**
 * 
    * @ClassName: ClearYesterdayTask
    * @Description: TODO(昨日业务清理)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class ShardingTask extends TimerTask { 
	private static Logger log = Logger.getLogger(ShardingTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
  
    @Autowired
    StatisticsService statisticsService;
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  
	public String getYesterdayDateStr() {
		Date curDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(curDate, -1);
		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, "yyyy-MM-dd");
		return yesterdayDateStr;
	}
	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("ShardingTask start--");
		if(!pd){
			return;
		}
		synchronized (lock){
			if(pd){
				pd=false;
				try {
					String day=getYesterdayDateStr();
					statisticsService.copyRecord(day);
				}catch(Exception e) {
					e.printStackTrace();
				}
				pd=true;
			}
		}
		log.info("ShardingTask end--");
	}  
	
	 
}