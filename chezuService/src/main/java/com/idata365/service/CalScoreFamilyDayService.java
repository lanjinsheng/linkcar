package com.idata365.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.FamilyDriveDayStat;
import com.idata365.entity.FamilyScore;
import com.idata365.entity.TaskFamilyDayScore;
import com.idata365.entity.UserFamilyRoleLog;
import com.idata365.entity.UserScoreDayStat;
import com.idata365.mapper.app.TaskFamilyDayScoreMapper;
import com.idata365.mapper.app.UserFamilyLogsMapper;
import com.idata365.mapper.app.UserScoreDayStatMapper;
import com.idata365.util.DateTools;
@Service
public class CalScoreFamilyDayService {

	@Autowired
   TaskFamilyDayScoreMapper taskFamilyDayScoreMapper;
	@Autowired
	UserFamilyLogsMapper  userFamilyLogsMapper;
	@Autowired
	UserScoreDayStatMapper userScoreDayStatMapper;
	
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	@Transactional
	public boolean calScoreFamilyDay(TaskFamilyDayScore taskFamilyDayScore,String startDay,String endDay) {
		FamilyDriveDayStat fscore=new FamilyDriveDayStat();
		Long familyId=taskFamilyDayScore.getFamilyId();
		String daystamp=taskFamilyDayScore.getDaystamp();
		Map<String,Object> roleMapParam=new HashMap<String,Object>();
		roleMapParam.put("familyId", familyId);
		roleMapParam.put("startTime", daystamp+" 00:00:00");
		roleMapParam.put("endTime", daystamp+" 23:59:59");
		List<UserFamilyRoleLog> users=userFamilyLogsMapper.getUsersByFamilyId(roleMapParam);
		int familyPersonNum=users.size();
//		int familyRoleNum=0;
		int activeNum=0;
	
//		Map<Integer,Object> tempMap=new HashMap<Integer,Object>();
		
	   Integer useHoldNum=0;
	   Double score=0d;
	   Double scoreComm=0d;
	   Double scoreAdd=0d;
	   Integer speedTimes=0;
	   Integer  speedPenalTime=0;
	   
	   
	   String speedTimesUpdateTime=null;
	   Long  speedTimesUpdateTimeLong=0L;
	   
	   
	   Integer brakeTimes=0;
	   Integer brakePenalTimes=0;
	   String brakeTimesUpdateTime=null;
	   Long  brakeTimesUpdateTimeLong=0L;
	   
		  Integer turnTimes=0;
		  Integer turnPenalTimes=0;
		  String turnTimesUpdateTime=null;
		  Long  turnTimesUpdateTimeLong=0L;
		  
		 Integer overspeedTimes=0;
		 Integer overspeedPenalTimes=0;
		 String overspeedTimesUpdateTime=null;
		 Long  overspeedTimesUpdateTimeLong=0L;
		 
		 Integer nightDriveTimes=0;
		 Integer nightDrivePenalTimes=0;
		 String nightDriveTimesUpdateTime=null;	
		 Long  nightDriveTimesUpdateTimeLong=0L;
		 
		Integer tiredDriveTimes=0;
		Integer tiredDrivePenalTimes=0;
		String tiredDriveTimesUpdateTime=null;	
		Long  tiredDriveUpdateTimeLong=0L;
		
		Integer illegalStopTimes=0;
		Integer illegalStopPenalTimes=0;
		String illegalStopUpdateTime=null;	
		Long  illegalStopUpdateTimeLong=0L;
		  Double mileage=0d;
		  Double time=0d;
		Integer usePhoneTimes=0;
		Double maxspeed=0d;
		
		
		for(UserFamilyRoleLog user:users) {
//			tempMap.put(userRole.getRole(), 1);
			if(user.getActiveFlag()==1) {
				activeNum++;
			}
			user.setDaystamp(daystamp);
			UserScoreDayStat userDayScore=userScoreDayStatMapper.getUserDayScoreByUserFamily(user);
			if(userDayScore!=null) {
				mileage+=userDayScore.getMileage();
				time+=userDayScore.getTime();
				usePhoneTimes+=userDayScore.getPhoneTimes();
				if(userDayScore.getMaxspeed()>maxspeed) {
					maxspeed=userDayScore.getMaxspeed();
				}
				useHoldNum+=userDayScore.getUseHoldNum();
				scoreComm+=userDayScore.getScore();
				
				speedTimes+=userDayScore.getSpeedTimes();
				speedPenalTime+=userDayScore.getSpeedPenalTimes();
				Long tempSpeedTimesUpdateTimeLong=DateTools.getDateTimeOfLong(userDayScore.getSpeedTimesUpdateTime());
				if(tempSpeedTimesUpdateTimeLong>speedTimesUpdateTimeLong) {
					speedTimesUpdateTimeLong=tempSpeedTimesUpdateTimeLong;
					speedTimesUpdateTime=userDayScore.getSpeedTimesUpdateTime();
				}
				
				brakeTimes+=userDayScore.getBrakeTimes();
				brakePenalTimes+=userDayScore.getBrakePenalTimes();
				Long tempBrakeTimesUpdateTimeLong=DateTools.getDateTimeOfLong(userDayScore.getBrakeTimesUpdateTime());
				if(tempBrakeTimesUpdateTimeLong>brakeTimesUpdateTimeLong) {
					brakeTimesUpdateTimeLong=tempBrakeTimesUpdateTimeLong;
					brakeTimesUpdateTime=userDayScore.getBrakeTimesUpdateTime();
				}
				
				turnTimes+=userDayScore.getTurnTimes();
				turnPenalTimes+=userDayScore.getTurnPenalTimes();
				Long tempTurnTimesUpdateTimeLong=DateTools.getDateTimeOfLong(userDayScore.getTurnTimesUpdateTime());
				if(tempTurnTimesUpdateTimeLong>turnTimesUpdateTimeLong) {
					turnTimesUpdateTimeLong=tempTurnTimesUpdateTimeLong;
					turnTimesUpdateTime=userDayScore.getTurnTimesUpdateTime();
				}
				
				overspeedTimes+=userDayScore.getOverspeedTimes();
				overspeedPenalTimes+=userDayScore.getOverspeedPenalTimes();
				Long tempOverspeedUpdateTimesLong=DateTools.getDateTimeOfLong(userDayScore.getOverspeedTimesUpdateTime());
				if(tempOverspeedUpdateTimesLong>overspeedTimesUpdateTimeLong) {
					overspeedTimesUpdateTimeLong=tempOverspeedUpdateTimesLong;
					overspeedTimesUpdateTime=userDayScore.getOverspeedTimesUpdateTime();
				}
				
				
				nightDriveTimes+=userDayScore.getNightDriveTimes();
				nightDrivePenalTimes+=userDayScore.getNightDrivePenalTimes();
				Long tempNightDriveTimesUpdateTimeLong=DateTools.getDateTimeOfLong(userDayScore.getNightDriveTimesUpdateTime());
				if(tempNightDriveTimesUpdateTimeLong>nightDriveTimesUpdateTimeLong) {
					nightDriveTimesUpdateTimeLong=tempNightDriveTimesUpdateTimeLong;
					nightDriveTimesUpdateTime=userDayScore.getNightDriveTimesUpdateTime();
				}
				
				tiredDriveTimes+=userDayScore.getTiredDriveTimes();
				tiredDrivePenalTimes+=userDayScore.getTiredDrivePenalTimes();
				Long tempTiredDriveUpdateTimeLong=DateTools.getDateTimeOfLong(userDayScore.getTiredDriveTimesUpdateTime());
				if(tempTiredDriveUpdateTimeLong>tiredDriveUpdateTimeLong) {
					tiredDriveUpdateTimeLong=tempTiredDriveUpdateTimeLong;
					tiredDriveTimesUpdateTime=userDayScore.getTiredDriveTimesUpdateTime();
				}
				
				
				illegalStopTimes+=userDayScore.getIllegalStopTimes();
				illegalStopPenalTimes+=userDayScore.getIllegalStopPenalTimes();
				if(userDayScore.getIllegalStopUpdateTime()!=null && !userDayScore.getIllegalStopUpdateTime().equals(""))
				{
					Long tempIllegalStopUpdateTimeLong=DateTools.getDateTimeOfLong(userDayScore.getIllegalStopUpdateTime());
					if(tempIllegalStopUpdateTimeLong>illegalStopUpdateTimeLong) {
						illegalStopUpdateTimeLong=tempIllegalStopUpdateTimeLong;
						illegalStopUpdateTime=userDayScore.getIllegalStopUpdateTime();
					}
				}
			}
		}
//		familyRoleNum=tempMap.size();
		
		fscore.setFamilyId(familyId);
		fscore.setDaystamp(daystamp);
		fscore.setUseHoldNum(useHoldNum);

		fscore.setScoreAdd(scoreAdd);
		fscore.setSpeedTimes(speedTimes);
		fscore.setSpeedPenalTimes(speedPenalTime);
		fscore.setSpeedTimesUpdateTime(speedTimesUpdateTime);
		fscore.setBrakeTimes(brakeTimes);
		fscore.setBrakePenalTimes(brakePenalTimes);
		fscore.setBrakeTimesUpdateTime(brakeTimesUpdateTime);
		fscore.setTurnTimes(turnTimes);
		fscore.setTurnPenalTimes(turnPenalTimes);
		fscore.setTurnTimesUpdateTime(turnTimesUpdateTime);
		fscore.setOverspeedTimes(overspeedTimes);
		fscore.setOverspeedPenalTimes(overspeedPenalTimes);
		fscore.setOverspeedTimesUpdateTime(overspeedTimesUpdateTime);
		fscore.setNightDriveTimes(nightDriveTimes);
		fscore.setNightDrivePenalTimes(nightDrivePenalTimes);
		fscore.setNightDriveTimesUpdateTime(nightDriveTimesUpdateTime);
		fscore.setTiredDriveTimes(tiredDriveTimes);
		fscore.setTiredDrivePenalTimes(tiredDrivePenalTimes);
		fscore.setTiredDriveTimesUpdateTime(tiredDriveTimesUpdateTime);
 
		fscore.setIllegalStopTimes(illegalStopTimes);
		fscore.setIllegalStopPenalTimes(illegalStopPenalTimes);
		fscore.setIllegalStopUpdateTime(illegalStopUpdateTime);

		fscore.setMileage(mileage);
		fscore.setTime(time);
		fscore.setUsePhoneTimes(usePhoneTimes);
		fscore.setMaxspeed(maxspeed);
		
		if(familyPersonNum==1) {
			fscore.setFamilyNumFactor(0.5d);
		}else if(familyPersonNum==2) {
			fscore.setFamilyNumFactor(0.8d);
		}else if(familyPersonNum==3) {
			fscore.setFamilyNumFactor(1.1d);
		}else if(familyPersonNum==4) {
			fscore.setFamilyNumFactor(1.3d);
		}else if(familyPersonNum==5) {
			fscore.setFamilyNumFactor(1.4d);
		}else if(familyPersonNum==6) {
			fscore.setFamilyNumFactor(1.5d);
		}else if(familyPersonNum==7) {
			fscore.setFamilyNumFactor(1.5d);
		}else if(familyPersonNum>=8) {
			fscore.setFamilyNumFactor(1.5d);
		}else {
			fscore.setFamilyNumFactor(0.5d);
		}
		
		//去除角色影响
		fscore.setRoleFactor(1d);
//		if(familyRoleNum==1) {
//			fscore.setRoleFactor(0.75d);
//		}else if(familyRoleNum==2) {
//			fscore.setRoleFactor(0.8d);
//		}else if(familyRoleNum==3) {
//			fscore.setRoleFactor(0.85d);
//		}else if(familyRoleNum==4) {
//			fscore.setRoleFactor(0.9d);
//		}else if(familyRoleNum==5) {
//			fscore.setRoleFactor(0.95d);
//		}else if(familyRoleNum==6) {
//			fscore.setRoleFactor(1d);
//		}else if(familyRoleNum==7) {
//			fscore.setRoleFactor(1.05d);
//		}else if(familyRoleNum==8) {
//			fscore.setRoleFactor(1.1d);
//		}else {
//			fscore.setRoleFactor(0.75d);	
//		}
		
		if(activeNum==0) {
			fscore.setMemberFactor(0.8d);
		}else if(activeNum==1) {
			fscore.setMemberFactor(0.9d);
		}else if(activeNum==2) {
			fscore.setMemberFactor(0.95d);
		}else if(activeNum==3) {
			fscore.setMemberFactor(1.0d);
		}else if(activeNum==4) {
			fscore.setMemberFactor(1.0d);
		}else if(activeNum==5) {
			fscore.setMemberFactor(1.0d);
		}else if(activeNum==6) {
			fscore.setMemberFactor(1.0d);
		}else if(activeNum==7) {
			fscore.setMemberFactor(1.05d);
		}else if(activeNum==8) {
			fscore.setMemberFactor(1.1d);
		}else {
			fscore.setMemberFactor(0.8d);
		}
		if(familyPersonNum==0) {
			scoreComm=0d;
		}else {
		scoreComm=BigDecimal.valueOf(scoreComm).divide(BigDecimal.valueOf(familyPersonNum),2,RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(fscore.getRoleFactor()))
				.multiply(BigDecimal.valueOf(fscore.getFamilyNumFactor())).doubleValue();
		}
		score=scoreComm;
		fscore.setScore(score);
		fscore.setScoreComm(scoreComm);
		taskFamilyDayScoreMapper.insertFamilyDriveDayStat(fscore);
		FamilyScore fs=new FamilyScore();
		fs.setFamilyId(familyId);
		fs.setMonth(taskFamilyDayScore.getDaystamp().replaceAll("-", "").substring(0,6));
		fs.setScore(score);
		//插入俱乐部赛季得分
		fs.setDayTimes(1);
		fs.setStartDay(startDay);
		fs.setEndDay(endDay);
		taskFamilyDayScoreMapper.insertFamilyScore(fs);
		return true;
	}
	
	
	@Transactional
	public List<TaskFamilyDayScore> getFamilyScoreDayTask(TaskFamilyDayScore taskFamilyDayScore){
		//先锁定任务
		taskFamilyDayScoreMapper.lockFamilyDayScoreTask(taskFamilyDayScore);
		//返回任务列表
		return taskFamilyDayScoreMapper.getFamilyDayScoreTask(taskFamilyDayScore);
	}
	@Transactional
	public	void updateSuccFamilyScoreDayTask(TaskFamilyDayScore taskFamilyDayScore) {
		taskFamilyDayScoreMapper.updateFamilyDayScoreSuccTask(taskFamilyDayScore);
	}
//	
	@Transactional
	public void updateFailFamilyScoreDayTask(TaskFamilyDayScore taskFamilyDayScore) {
		taskFamilyDayScoreMapper.updateFamilyDayScoreFailTask(taskFamilyDayScore);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskFamilyDayScoreMapper.clearLockTask(compareTimes);
	}
	
}
