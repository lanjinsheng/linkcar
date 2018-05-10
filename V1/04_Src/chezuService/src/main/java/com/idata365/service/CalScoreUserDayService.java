package com.idata365.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.DriveScore;
import com.idata365.entity.UserScoreDayStat;
import com.idata365.mapper.app.DriveScoreMapper;
import com.idata365.mapper.app.FamilyInfoMapper;
import com.idata365.mapper.app.TaskAchieveAddValueMapper;
import com.idata365.mapper.app.UserFamilyLogsMapper;
import com.idata365.mapper.app.UserScoreDayStatMapper;


@Service
public class CalScoreUserDayService  extends BaseService<CalScoreUserDayService>{
	@Autowired
	UserScoreDayStatMapper userScoreDayStatMapper;
	@Autowired
	UserFamilyLogsMapper userFamilyScoreMapper;
	@Autowired
	DriveScoreMapper driveScoreMapper;
	@Autowired
	TaskAchieveAddValueMapper taskAchieveAddValueMapper;
	@Autowired
	FamilyInfoMapper familyInfoMapper;
	@Transactional
	public boolean calScoreUserDay(UserScoreDayStat userScoreDayStat) {
		DriveScore driveScore=new DriveScore();
//		int role=0;
		driveScore.setUserId(userScoreDayStat.getUserId());
		driveScore.setUserFamilyRoleLogId(userScoreDayStat.getUserFamilyScoreId());
		List<DriveScore> driveScores=driveScoreMapper.getDriveScoreByUR(driveScore);
//		score   mileageScore  timeScore(无) brakeTimesScore   turnTimesScore  speedTimesScore 
//		 overspeedTimesScore   maxspeedScore   tiredDriveScore  phoneTimesScore  nightDriveScore
//		UserFamilyRoleLog userRoleLog=userFamilyScoreMapper.getUserRoleById(userScoreDayStat.getUserFamilyScoreId());
//		role=userRoleLog.getRole();
		double score=0d;
		double roleScore=0d;
		double mileageScore=0d;
		double timeScore=0d;
		double brakeTimesScore=0d;
		double turnTimesScore=0d;
		double speedTimesScore=0d;
		double overspeedTimesScore=0d;
		double maxspeedScore=0d;
		double tiredDriveScore=0d;
		double phoneTimesScore=0d;
		double nightDriveScore=0d;
		double mazaScore=0d;
		int penalTimes=0;
		
		roleScore=10d;
//		if(role>=1 && role<=6) {
//			roleScore=10d;
//		}else {
//			roleScore=5d;
//		}
		
		
		for(DriveScore ds:driveScores) {

			mileageScore+=ds.getDistanceScore().doubleValue();
			brakeTimesScore+=ds.getBrakeScore().doubleValue();
			turnTimesScore+=ds.getTurnScore().doubleValue();
			speedTimesScore+=ds.getSpeedUpScore().doubleValue();
			overspeedTimesScore+=ds.getOverSpeedScore().doubleValue();
			maxspeedScore+=ds.getMaxSpeedScore().doubleValue();
			tiredDriveScore+=ds.getTiredDrivingScore().doubleValue();
			nightDriveScore+=ds.getNightDrivingScore().doubleValue();
			phoneTimesScore+=ds.getPhonePlayScore().doubleValue();
		}
		//驾驶得分
		if(driveScores!=null && driveScores.size()>0) {
			int size=driveScores.size();
			score+=mileageScore;
			score+=brakeTimesScore;
			score+=turnTimesScore;
			score+=speedTimesScore;
			score+=overspeedTimesScore;
			score+=maxspeedScore;
			score+=tiredDriveScore;
			score+=nightDriveScore;
			score+=phoneTimesScore;
			score=BigDecimal.valueOf(score).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			mileageScore=BigDecimal.valueOf(mileageScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			brakeTimesScore=BigDecimal.valueOf(brakeTimesScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			turnTimesScore=BigDecimal.valueOf(turnTimesScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
//			speedTimesScore=BigDecimal.valueOf(speedTimesScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			speedTimesScore=0d;//超速用0计算
			overspeedTimesScore=BigDecimal.valueOf(overspeedTimesScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			maxspeedScore=BigDecimal.valueOf(maxspeedScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			tiredDriveScore=BigDecimal.valueOf(tiredDriveScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			nightDriveScore=BigDecimal.valueOf(nightDriveScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			phoneTimesScore=BigDecimal.valueOf(phoneTimesScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
		}
		//角色得分

			userScoreDayStat.setScore(score+roleScore);
			userScoreDayStat.setMileageScore(mileageScore);
			userScoreDayStat.setBrakeTimesScore(brakeTimesScore);
			userScoreDayStat.setTurnTimesScore(turnTimesScore);
			userScoreDayStat.setSpeedTimesScore(speedTimesScore);
			userScoreDayStat.setOverspeedTimesScore(overspeedTimesScore);
			userScoreDayStat.setMaxspeedScore(maxspeedScore);
			userScoreDayStat.setTiredDriveScore(tiredDriveScore);
			userScoreDayStat.setNightDriveScore(nightDriveScore);
			userScoreDayStat.setTimeScore(timeScore);
			userScoreDayStat.setIllegalStopTimesScore(Double.valueOf(-userScoreDayStat.getIllegalStopTimes()));
			userScoreDayStat.setPhoneTimesScore(phoneTimesScore);
		
		
			//摊位得分
			int useMaza=userScoreDayStat.getUseMazha();
			
			if(useMaza<=0) {
				
			}else if(useMaza>=1 && useMaza<=3) {
				mazaScore+=30;
			}else if(useMaza>=4 && useMaza<=6) {
				mazaScore+=35;
			}else if(useMaza>=7 && useMaza<=9) {
				mazaScore+=40;
			}else if(useMaza>=10) {
				mazaScore+=50;
			}
		
			
		userScoreDayStat.setMazhaScore(mazaScore);
		//增加其他加分项目:贴条等，待定
		penalTimes+=userScoreDayStat.getBrakePenalTimes();
		penalTimes+=userScoreDayStat.getTurnPenalTimes();
		penalTimes+=userScoreDayStat.getSpeedPenalTimes();
		penalTimes+=userScoreDayStat.getOverspeedPenalTimes();
		penalTimes+=userScoreDayStat.getTiredDrivePenalTimes();
		penalTimes+=userScoreDayStat.getNightDrivePenalTimes();
		penalTimes+=userScoreDayStat.getIllegalStopPenalTimes();
		if(userScoreDayStat.getTravelNum()>0) {
			int tietiaoFen=0;
			
			if(penalTimes>=7) {
				tietiaoFen=-20;	
			}else {
				tietiaoFen=-penalTimes*3;
				
			}
			Double illegalScore=BigDecimal.valueOf(tietiaoFen).divide(BigDecimal.valueOf(userScoreDayStat.getTravelNum()),2,RoundingMode.HALF_UP).doubleValue();
			userScoreDayStat.setIllegalStopTimesScore(illegalScore);
		}else {
			userScoreDayStat.setIllegalStopTimesScore(0d);
		}
		
		double endScore=userScoreDayStat.getScore().doubleValue()
				+userScoreDayStat.getIllegalStopTimesScore().doubleValue()
				+userScoreDayStat.getTietiaoTakeOffScore().doubleValue()
				+userScoreDayStat.getExtraPlusScore();
		//缺少个人成就分
		if(endScore<0) {
			userScoreDayStat.setScore(0d);
		}else {
			userScoreDayStat.setScore(endScore);
		}
		//计算完分数,进行家族行程热度增加
//		long familyId=userRoleLog.getFamilyId();
//		familyInfoMapper.updateFamilyDriveFlag(familyId);
//		familyInfoMapper.updateFamilyActiveLevel(familyId);
		return true;
	}
//	public boolean addAchieve(long keyId,Double value,AchieveEnum type) {
//		TaskAchieveAddValue taskAchieveAddValue=new TaskAchieveAddValue();
//		taskAchieveAddValue.setAchieveType(type);
//		taskAchieveAddValue.setKeyId(keyId);
//		taskAchieveAddValue.setAddValue(value);
//		taskAchieveAddValueMapper.insertTaskAchieveAddValue(taskAchieveAddValue);
//		return true;
//	}
	@Transactional
	public void updateUserDayScore(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserScoreDayById(userScoreDayStat);
	}
	@Transactional
	public List<UserScoreDayStat> getUserScoreDayTask(UserScoreDayStat userScoreDayStat){
		//先锁定任务
		userScoreDayStatMapper.lockUserDayScoreTask(userScoreDayStat);
		//返回任务列表
		return userScoreDayStatMapper.getUserDayScoreTask(userScoreDayStat);
	}
	@Transactional
	public	void updateSuccUserScoreDayTask(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserDayScoreSuccTask(userScoreDayStat);
	}
//	
	@Transactional
	public void updateFailUserScoreDayTask(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserDayScoreFailTask(userScoreDayStat);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		userScoreDayStatMapper.clearLockTask(compareTimes);
	}
//	
}
