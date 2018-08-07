package com.idata365.schedule;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.entity.Statistics;
import com.idata365.service.StatisticsService;

 



/**
 * 
    * @ClassName: ClearYesterdayTask
    * @Description: TODO(昨日业务清理)
    * @author LanYeYe
    * @date 2017年12月31日
    *
 */
public class ScanLogsTask extends TimerTask { 
	private static Logger log = Logger.getLogger(ScanLogsTask.class);
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
//	public String filePath="D:\\usr\\local\\chezu\\zuul\\logs\\%s\\event.log";
	public String filePath="/usr/local/chezu/zuul/logs/%s/event.log";
	
	@Override  
	public void run() {  
		log.info("ScanLogsTask start--");
		if(!pd){
			return;
		}
		synchronized (lock){
			if(pd){
				pd=false;
				try {
					String day=getYesterdayDateStr();
					String fileName=String.format(filePath, day);
					log.info(fileName);
					File file = new File(fileName);   
					if(file.exists()) {
						statisticsService.insertRecord(fileName);
						//修改名称lock
						File file2 = new File(fileName+".lock");   
						boolean b=file.renameTo(file2);
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				pd=true;
			}
		}
		log.info("ScanLogsTask end--");
	}  
	public static void main(String []args) {
		try {
			String filePath="D:\\usr\\local\\chezu\\zuul\\logs\\%s\\event.log";
			Date curDate = Calendar.getInstance().getTime();
			Date yesterdayDate = DateUtils.addDays(curDate, -1);
			String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, "yyyy-MM-dd");
			String day="2018-08-07";
			String fileName=String.format(filePath, day);
			File file = new File(fileName);   
			if(file.exists()) {
				BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));    
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),5*1024*1024);// 用5M的缓冲读取文本文件  
				String line = "";
				List<Statistics> list=new ArrayList<>();
				while((line = reader.readLine()) != null){
				   //TODO: write your business
					String eventTime=line.substring(0, 23);
					String otherLog=line.substring(64, line.length());
					String []logs=otherLog.split("=");
					Long userId=Long.valueOf(logs[0]);
					String action=logs[1];
					System.out.println("eventTime:"+eventTime);
					System.out.println("otherLog:"+otherLog);
					System.out.println("userId:"+userId);
					System.out.println("action:"+action);
				}
				
				System.out.println("ok do-----");
				//修改名称lock
				File file2 = new File(fileName+".lock");   
				boolean b=file.renameTo(file2);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	 
}