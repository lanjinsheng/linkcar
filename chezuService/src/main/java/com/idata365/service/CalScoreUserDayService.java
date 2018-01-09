package com.idata365.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.DriveScore;
import com.idata365.entity.UserFamilyRoleLog;
import com.idata365.entity.UserScoreDayStat;
import com.idata365.mapper.app.UserFamilyScoreMapper;
import com.idata365.mapper.app.UserScoreDayStatMapper;
import com.idata365.mapper.col.DriveScoreMapper;

@Service
public class CalScoreUserDayService  extends BaseService<CalScoreUserDayService>{
	@Autowired
	UserScoreDayStatMapper userScoreDayStatMapper;
	@Autowired
	UserFamilyScoreMapper userFamilyScoreMapper;
	@Autowired
	DriveScoreMapper driveScoreMapper;
	public boolean calScoreUserDay(UserScoreDayStat userScoreDayStat) {
		DriveScore driveScore=new DriveScore();
		int role=0;
		driveScore.setUserId(userScoreDayStat.getUserId());
		driveScore.setUserFamilyRoleLogId(userScoreDayStat.getUserFamilyScoreId());
		List<DriveScore> driveScores=driveScoreMapper.getDriveScoreByUR(driveScore);
//		score   mileageScore  timeScore(无) brakeTimesScore   turnTimesScore  speedTimesScore 
//		 overspeedTimesScore   maxspeedScore   tiredDriveScore  phoneTimesScore  nightDriveScore
		UserFamilyRoleLog userRoleLog=userFamilyScoreMapper.getUserRoleById(userScoreDayStat.getUserFamilyScoreId());
		role=userRoleLog.getRole();
		Double score=0D;
		Double roleScore=0D;
		Double mileageScore=0D;
		Double timeScore=0D;
		Double brakeTimesScore=0D;
		Double turnTimesScore=0D;
		Double speedTimesScore=0D;
		Double overspeedTimesScore=0D;
		Double maxspeedScore=0D;
		Double tiredDriveScore=0D;
		Double phoneTimesScore=0D;
		Double nightDriveScore=0D;
		if(role>=1 && role<=6) {
			roleScore=10d;
		}else {
			roleScore=5d;
		}
		
		
		for(DriveScore ds:driveScores) {
			role=ds.getRole();
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
			speedTimesScore=BigDecimal.valueOf(speedTimesScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			overspeedTimesScore=BigDecimal.valueOf(overspeedTimesScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			maxspeedScore=BigDecimal.valueOf(maxspeedScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			tiredDriveScore=BigDecimal.valueOf(tiredDriveScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			nightDriveScore=BigDecimal.valueOf(nightDriveScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
			phoneTimesScore=BigDecimal.valueOf(phoneTimesScore).divide(BigDecimal.valueOf(size),2,RoundingMode.HALF_UP).doubleValue();
		}
		if(role>=1 && role<=6) {
			userScoreDayStat.setScore(score+roleScore);
			userScoreDayStat.setMileageScore(mileageScore);
			userScoreDayStat.setBrakeTimesScore(brakeTimesScore);
			userScoreDayStat.setTurnTimesScore(turnTimesScore);
			userScoreDayStat.setSpeedTimesScore(overspeedTimesScore);
			userScoreDayStat.setOverspeedTimesScore(overspeedTimesScore);
			userScoreDayStat.setMaxspeedScore(maxspeedScore);
			userScoreDayStat.setTiredDriveScore(tiredDriveScore);
			userScoreDayStat.setNightDriveScore(nightDriveScore);
			userScoreDayStat.setTimeScore(timeScore);
		}else {
			userScoreDayStat.setScore(score+roleScore);
			userScoreDayStat.setMileageScore(0d);
			userScoreDayStat.setBrakeTimesScore(0d);
			userScoreDayStat.setTurnTimesScore(0d);
			userScoreDayStat.setSpeedTimesScore(0d);
			userScoreDayStat.setOverspeedTimesScore(0d);
			userScoreDayStat.setMaxspeedScore(0d);
			userScoreDayStat.setTiredDriveScore(0d);
			userScoreDayStat.setNightDriveScore(0d);
			userScoreDayStat.setTimeScore(0d);
		}
		//增加其他加分项目:贴条等，待定
		
		return true;
	}
	
	public void updateUserDayScore(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserScoreDayById(userScoreDayStat);
	}
	
	public List<UserScoreDayStat> getUserScoreDayTask(UserScoreDayStat userScoreDayStat){
		//先锁定任务
		userScoreDayStatMapper.lockUserDayScoreTask(userScoreDayStat);
		//返回任务列表
		return userScoreDayStatMapper.getUserDayScoreTask(userScoreDayStat);
	}
	
	public	void updateSuccUserScoreDayTask(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserDayScoreSuccTask(userScoreDayStat);
	}
//	
	public void updateFailUserScoreDayTask(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserDayScoreFailTask(userScoreDayStat);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		userScoreDayStatMapper.clearLockTask(compareTimes);
	}
//	
}
