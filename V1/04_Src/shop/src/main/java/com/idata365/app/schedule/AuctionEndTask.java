package com.idata365.app.schedule;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.app.entity.AuctionGoods;
import com.idata365.app.service.AuctionTaskService;
import com.idata365.app.util.DateTools;

 

/**
 * 
    * @ClassName: CalFamilyDayScoreTask
    * @Description: TODO(家族日分统计)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class AuctionEndTask extends TimerTask { 
	private static Logger log = Logger.getLogger(AuctionEndTask.class);
	private static Object lock = new Object();
	public static boolean pd=true;
	
  //注入ThreadPoolTaskExecutor 到主线程中  
	private ThreadPoolTaskExecutor threadPool;  
	
//    @Autowired
//    CalScoreFamilyDayService calScoreFamilyDayService;
    @Autowired
    AuctionTaskService auctionTaskService;
   
	public void setThreadPool(ThreadPoolTaskExecutor threadPool){  
//		System.out.println(new Date().getTime());
	 this.threadPool = threadPool;  
	}  

	//在主线程中执行任务线程.....    
	@Override  
	public void run() {  
		log.info("AuctionEndTask start--");
 
		if(!pd){
			return;
		}
		synchronized (lock){
		if(pd){
			pd=false;
			try {
				AuctionGoods auctionGoods=new AuctionGoods();
				auctionGoods.setTaskFlag(String.valueOf(System.currentTimeMillis()));
//				auctionGoods.setAuctionEndTime(DateTools.getCurDateAddSecond(5));
			List<AuctionGoods> taskList=auctionTaskService.getAuctionTask(auctionGoods);
			for(AuctionGoods task:taskList) {
				 
					try {
						boolean result=auctionTaskService.doEndAuction(task);
					if(result) {
						auctionTaskService.updateSuccTask(task);
						 
					}else {
						auctionTaskService.updateFailTask(task);
					}
					}catch(Exception e) {
						e.printStackTrace();
						log.error(e);
						auctionTaskService.updateFailTask(task);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				log.info("CalFamilyDayScoreTask 异常");
			}
			pd=true;
		}
			
		}
		log.info("AuctionEndTask end--");
	}  
	
	public static void main(String []args) {
		String timestamp="20180125";
		String yyyy=timestamp.substring(0, 4);
		String mm=timestamp.substring(4, 6);
		String dd=timestamp.substring(6, 8);
		System.out.println(yyyy+"-"+mm+"-"+dd);
	}
}