package com.idata365.app.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.TaskGeneric;
import com.idata365.app.enums.TaskGenericEnum;
import com.idata365.app.mapper.TaskGenericMapper;
import com.idata365.app.util.DateTools;

@Service
public class TaskAutoAddService {

	@Autowired
   TaskGenericMapper taskGenericMapper;
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	
	public String getDateStr2(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyyMMdd");
		return dayStr;
	}
	
	public String getDay(String format)
	{
		Date curDate = Calendar.getInstance().getTime();
		String dayStr = DateFormatUtils.format(curDate, format);
		return dayStr;
		
	} 
	private static String jsonValue1="{\"tableName\":\"userPower%s\"}";
	private static String jsonValue2="{\"season\":\"%s\"}";
	private static String jsonValue3="{\"season\":\"%s\",\"gameDayNum\":%s}";
	
	 
	
	/**
	 * 
	    * @Title: snapShotTaskAdd
	    * @Description: TODO(添加用户power快照任务)
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void snapShotTaskAdd(){
		long now=System.currentTimeMillis();
		String dayStr=getDay("yyyy-MM-dd");
		long curDay=DateTools.getDateTimeOfLong(dayStr+" 00:00:00");
//		int i=300*1000;
		long i=3600*1*10000;
		if((now-curDay)>0 && (now-curDay)<i) {
			String dayStr2=getDateStr2(-1);
			String taskKey=dayStr2+"_"+TaskGenericEnum.UserDayPowerSnapshot;
			TaskGeneric task=new TaskGeneric();
			task.setGenericKey(taskKey);
			task.setTaskType(TaskGenericEnum.UserDayPowerSnapshot);
			task.setPriority(10);
			task.setJsonValue(String.format(jsonValue1,dayStr2));
			taskGenericMapper.insertTask(task);
		}
		
	}
	@Transactional
	public void initUserDayReward(){
		long now=System.currentTimeMillis();
		String dayStr=getDay("yyyy-MM-dd");
		long curDay=DateTools.getDateTimeOfLong(dayStr+" 00:00:00");
//		int i=1800*1000;
		long i=3600*1*10000;
		if((now-curDay)>0 && (now-curDay)<i) {
			String dayStr2=getDateStr2(-1);
			String snapKey=dayStr2+"_"+TaskGenericEnum.UserDayPowerSnapshot;
			TaskGeneric task=taskGenericMapper.getByGenericKey(snapKey);
			if(task!=null) {
			//快照任务已经完成,可以进行power任务下发
				String powerKey=dayStr2+"_"+TaskGenericEnum.InitUserDayReward;
				TaskGeneric taskPower=new TaskGeneric();
				taskPower.setGenericKey(powerKey);
				taskPower.setTaskType(TaskGenericEnum.InitUserDayReward);
				taskPower.setPriority(10);
				taskPower.setJsonValue(String.format(jsonValue1,dayStr2));
				taskGenericMapper.insertTask(taskPower);
			}
		}
		
	}
	/**
	 * 
	    * @Title: syncFamilyGameEndAdd
	    * @Description: TODO(在远程任务里进行该方法调用)
	    * @param @param season    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void syncFamilyGameEndAdd(String season){
//		    String dayStr=getDay("yyyy-MM-dd");
			String dayStr2=season.replaceAll("-", "");
			String taskKey=dayStr2+"_"+TaskGenericEnum.InitFamilyDayReward;
			TaskGeneric task=new TaskGeneric();
			task.setGenericKey(taskKey);
			task.setTaskType(TaskGenericEnum.InitFamilyDayReward);
			task.setPriority(10);
			task.setJsonValue(String.format(jsonValue2,season));
			taskGenericMapper.insertTask(task);
		
	}
 
	@Transactional
	public void syncFamilySeasonEndAdd(String season,String gameDayNum){
//		    String dayStr=getDay("yyyy-MM-dd");
			String dayStr2=season.replaceAll("-", "");
			String taskKey=dayStr2+"_"+TaskGenericEnum.InitFamilySeasonReward;
			TaskGeneric task=new TaskGeneric();
			task.setGenericKey(taskKey);
			task.setTaskType(TaskGenericEnum.InitFamilySeasonReward);
			task.setPriority(10);
			task.setJsonValue(String.format(jsonValue3,season,gameDayNum));
			taskGenericMapper.insertTask(task);
		
	}
}
