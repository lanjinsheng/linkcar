package com.idata365.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.ParkStation;
import com.idata365.entity.UserFamilyRoleLog;
import com.idata365.entity.UserScoreDayStat;
import com.idata365.entity.UserTravelHistory;
import com.idata365.mapper.app.ParkStationMapper;
import com.idata365.mapper.app.UserFamilyScoreMapper;
import com.idata365.mapper.app.UserScoreDayStatMapper;
import com.idata365.mapper.app.UserTravelHistoryMapper;

@Service
public class AddUserDayStatService extends BaseService<AddUserDayStatService>{
	private final static Logger LOG = LoggerFactory.getLogger(AddUserDayStatService.class);
 
	@Autowired
	UserTravelHistoryMapper userTravelHistoryMapper;
	@Autowired
	UserFamilyScoreMapper userFamilyScoreMapper;
	@Autowired
	UserScoreDayStatMapper userScoreDayStatMapper;
	@Autowired
	ParkStationMapper parkStationMapper;
 //任务执行
//	void lockCalScoreTask(CalDriveTask driveScore);
	
	public List<UserTravelHistory> getTravelTask(UserTravelHistory userTravelHistory){
		//先锁定任务
		userTravelHistoryMapper.lockTravelTask(userTravelHistory);
		//返回任务列表
		return userTravelHistoryMapper.getTravelTask(userTravelHistory);
	}
	
	public	void updateSuccAddUserDayStatTask(UserTravelHistory userTravelHistory) {
		userTravelHistoryMapper.updateTravelSuccTask(userTravelHistory);
	}
//	
	public void updateFailAddUserDayStatTask(UserTravelHistory userTravelHistory) {
		userTravelHistoryMapper.updateTravelFailTask(userTravelHistory);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		userTravelHistoryMapper.clearLockTask(compareTimes);
	}
//	
	
	@Transactional
   public boolean addUserDayStat(UserTravelHistory uth) {
	   String driveEndTime=uth.getEndTime().substring(0,19);
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("userId", uth.getUserId());
		m.put("driveEndTime", driveEndTime);
		List<UserFamilyRoleLog> roles= userFamilyScoreMapper.getUserRoles(m);
		if(roles==null || roles.size()==0){//角色没创建，不处理。建议此处进行预警
			return false;
		}
		for(UserFamilyRoleLog role:roles) {
			if(role.getRole()==7) {//煎饼侠，行程不纳入
				continue;
			}
			UserScoreDayStat userScoreDayStat=new UserScoreDayStat();
			userScoreDayStat.setUserId(uth.getUserId());
			userScoreDayStat.setUserFamilyScoreId(role.getId());
			userScoreDayStat.setDaystamp(driveEndTime.substring(0,10));
			userScoreDayStat.setBrakeTimes(uth.getBrakeTimes()-uth.getBrakeTimesOffset());
			if(userScoreDayStat.getBrakeTimes()>0) {
				userScoreDayStat.setBrakeTimesUpdateTime(driveEndTime);
			}
			userScoreDayStat.setTurnTimes(uth.getTurnTimes()-uth.getTurnTimesOffset());
			if(userScoreDayStat.getTurnTimes()>0) {
				userScoreDayStat.setTurnTimesUpdateTime(driveEndTime);
			}
			userScoreDayStat.setSpeedTimes(uth.getSpeedTimes()-uth.getSpeedTimesOffset());
			if(userScoreDayStat.getSpeedTimes()>0) {
				userScoreDayStat.setSpeedTimesUpdateTime(driveEndTime);
			}
			
			int overSpeed=(uth.getOverspeedTimes()-uth.getOverspeedTimesOffset())>=60?1:0;
			userScoreDayStat.setOverspeedTimes(overSpeed);
			if(overSpeed>0) {
				userScoreDayStat.setOverspeedTimesUpdateTime(driveEndTime);
			}
			int tiredDriveTimes=(uth.getTiredDrive()-uth.getTiredDriveOffset())>=120?1:0;
			long tireTime =Double.valueOf(uth.getTiredDrive()-uth.getTiredDriveOffset()).longValue();
			userScoreDayStat.setTiredDrive(tireTime);
			userScoreDayStat.setTiredDriveTimes(tiredDriveTimes);
			if(tiredDriveTimes>0) {
				userScoreDayStat.setTiredDriveTimesUpdateTime(driveEndTime);
			}
			int nightDrive=(uth.getNightDrive()-uth.getNightDriveOffset())>=180?1:0;
			userScoreDayStat.setNightDriveTimes(nightDrive);
			if(nightDrive>0) {
				userScoreDayStat.setNightDriveTimesUpdateTime(driveEndTime);
			 }
			userScoreDayStat.setMaxspeed(uth.getMaxspeed());
			userScoreDayStat.setUseCheluntai(uth.getUseCheluntai());
			userScoreDayStat.setUseFadongji(uth.getUseFadongji());
			userScoreDayStat.setUseHongniu(uth.getUseHongniu());
			userScoreDayStat.setUseShachepian(uth.getUseShachepian());
			userScoreDayStat.setUseYeshijing(uth.getUseYeshijing());
			userScoreDayStat.setUseZengyaqi(uth.getUseZengyaqi());
			userScoreDayStat.setTravelNum(1);
			//更新车位
			 ParkStation park=new ParkStation();
			 park.setUserId(uth.getUserId());
			 park.setFamilyId(role.getFamilyId());
			 park.setExpireTime(driveEndTime);
			 park.setHabitId(uth.getHabitId());
			 int updatePark=parkStationMapper.updateParkStation(park);
			 if(updatePark==0) {//违停
				 userScoreDayStat.setIllegalStopTimes(1);
				 userScoreDayStat.setIllegalStopUpdateTime(driveEndTime);
			 }
			 userScoreDayStatMapper.insertOrUpdateUserDayStat(userScoreDayStat);
		}
		LOG.info("UPDATE SUCCESS");
	   return true;
   }
}
