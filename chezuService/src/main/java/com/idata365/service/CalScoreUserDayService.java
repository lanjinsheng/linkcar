package com.idata365.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.DriveScore;
import com.idata365.entity.UserScoreDayStat;
import com.idata365.mapper.app.UserScoreDayStatMapper;
import com.idata365.mapper.col.DriveScoreMapper;

@Service
public class CalScoreUserDayService  extends BaseService<CalScoreUserDayService>{
	@Autowired
	UserScoreDayStatMapper userScoreDayStatMapper;
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
		
		Double score=0D;
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
			score+=10;//增加角色分
			userScoreDayStat.setScore(score);
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
			score=5d;//增加角色分
			userScoreDayStat.setScore(score);
		}
		//增加其他加分项目:贴条等，待定
		
		return true;
	}
	
	public List<UserScoreDayStat> getUserScoreDayTask(UserScoreDayStat userScoreDayStat){
		//先锁定任务
		userScoreDayStatMapper.lockUserScoreDayTask(userScoreDayStat);
		//返回任务列表
		return userScoreDayStatMapper.getUserScoreDayTask(userScoreDayStat);
	}
	
	public	void updateSuccUserScoreDayTask(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserScoreDaySuccTask(userScoreDayStat);
	}
//	
	public void updateFailUserScoreDayTask(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserScoreDayFailTask(userScoreDayStat);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		userScoreDayStatMapper.clearLockTask(compareTimes);
	}
//	
}
