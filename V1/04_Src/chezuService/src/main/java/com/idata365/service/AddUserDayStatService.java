package com.idata365.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.TaskAchieveAddValue;
import com.idata365.entity.TaskPowerLogs;
import com.idata365.enums.AchieveEnum;
import com.idata365.enums.PowerEnum;
import com.idata365.entity.DriveScore;
import com.idata365.entity.ParkStation;
import com.idata365.entity.UserFamilyRoleLog;
import com.idata365.entity.UserRoleLog;
import com.idata365.entity.UserScoreDayStat;
import com.idata365.entity.UserTravelHistory;
import com.idata365.mapper.app.FamilyInfoMapper;
import com.idata365.mapper.app.ParkStationMapper;
import com.idata365.mapper.app.TaskAchieveAddValueMapper;
import com.idata365.mapper.app.TaskPowerLogsMapper;
import com.idata365.mapper.app.UserFamilyLogsMapper;
import com.idata365.mapper.app.UserScoreDayStatMapper;
import com.idata365.mapper.app.UserTravelHistoryMapper;

@Service
public class AddUserDayStatService extends BaseService<AddUserDayStatService>{
	private final static Logger LOG = LoggerFactory.getLogger(AddUserDayStatService.class);
 
	@Autowired
	UserTravelHistoryMapper userTravelHistoryMapper;
	@Autowired
	UserFamilyLogsMapper userFamilyScoreMapper;
	@Autowired
	UserScoreDayStatMapper userScoreDayStatMapper;
	@Autowired
	ParkStationMapper parkStationMapper;
	@Autowired
	TaskAchieveAddValueMapper taskAchieveAddValueMapper;
	@Autowired
	TaskUserDayEndService  taskUserDayEndService;
	@Autowired
	FamilyInfoMapper  familyInfoMapper;
	@Autowired
	TaskPowerLogsMapper taskPowerLogsMapper;
	@Autowired
	DriveScoreService driveScoreService;
	@Autowired
	UserRoleLogService userRoleLogService;
 //任务执行
//	void lockCalScoreTask(CalDriveTask driveScore);
	@Transactional
	public List<UserTravelHistory> getTravelTask(UserTravelHistory userTravelHistory){
		//先锁定任务
		userTravelHistoryMapper.lockTravelTask(userTravelHistory);
		//返回任务列表
		return userTravelHistoryMapper.getTravelTask(userTravelHistory);
	}
	@Transactional
	public	void updateSuccAddUserDayStatTask(UserTravelHistory userTravelHistory) {
		userTravelHistoryMapper.updateTravelSuccTask(userTravelHistory);
	}
//	
	@Transactional
	public void updateFailAddUserDayStatTask(UserTravelHistory userTravelHistory) {
		userTravelHistoryMapper.updateTravelFailTask(userTravelHistory);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		userTravelHistoryMapper.clearLockTask(compareTimes);
	}
	
	/**
	 * 
	    * @Title: addFamilyTripPowerLogs
	    * @Description: TODO(行程给俱乐部贡献值)
	    * @param @param userId
	    * @param @param habitId
	    * @param @param familyId
	    * @param @param power
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	private boolean addFamilyTripPowerLogs(long userId,long habitId,long familyId,int power,long effectId) {
		 
		    String jsonValue="{\"userId\":%d,\"habitId\":%d,\"familyId\":%d,\"toFamilyValue\":%s,\"effectId\":%d}";
	    	TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
	    	taskPowerLogs.setUserId(userId);
	    	taskPowerLogs.setTaskType(PowerEnum.TripToFamily);
	    	int familyPower=BigDecimal.valueOf(power).divide(BigDecimal.valueOf(2),0,RoundingMode.HALF_EVEN).intValue();
	    	taskPowerLogs.setJsonValue(String.format(jsonValue, userId,habitId,familyId,familyPower,effectId));
	    	int hadAdd=taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);	
	    	if(hadAdd>0) {
	    		return true;
	    	}
		return false;
	}
//	/**
//	 * 
//	    * @Title: addUserTripPowerLogs
//	    * @Description: TODO(行程给个人贡献值)
//	    * @param @param userId
//	    * @param @param habitId
//	    * @param @param distance
//	    * @param @param score
//	    * @param @return    参数
//	    * @return int    返回类型
//	    * @throws
//	    * @author LanYeYe
//	 */
//	private int addUserTripPowerLogs(long effectId,long userId,long habitId,double distance,Double score) {
//		 
//		String jsonValue="{\"userId\":%d,\"habitId\":%d,\"toUserValue\":%s,\"effectId\":%d}";
//		//(10+分数-45/10)*公里
//		BigDecimal score1=(BigDecimal.valueOf(score).subtract(BigDecimal.valueOf(45))).divide(BigDecimal.valueOf(10),0,RoundingMode.HALF_EVEN);
//    	int power=(score1.add(BigDecimal.valueOf(10))).multiply(BigDecimal.valueOf(distance)).divide(BigDecimal.valueOf(1000),0,RoundingMode.HALF_EVEN).intValue();
//      	TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
//    	taskPowerLogs.setUserId(userId);
//    	taskPowerLogs.setTaskType(PowerEnum.TripToUser);
//    	taskPowerLogs.setJsonValue(String.format(jsonValue, userId,habitId,String.valueOf(power),effectId));
//    	taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);	
//    	return power;
//	}
	
	/**
	 * 
	    * @Title: addUserTripPowerLogs
	    * @Description: TODO(行程给个人贡献值)
	    * @param @param userId
	    * @param @param habitId
	    * @param @param distance
	    * @param @param score
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	private int addUserTripPowerLogs(long effectId,long userId,long habitId,double distance,Double score) {
		 
		String jsonValue="{\"userId\":%d,\"habitId\":%d,\"toUserValue\":%s,\"effectId\":%d}";
		BigDecimal distanceKm=BigDecimal.valueOf(distance).divide(BigDecimal.valueOf(1000),1,RoundingMode.HALF_EVEN);
		BigDecimal power=BigDecimal.valueOf(0);
		double distanceKmDouble=distanceKm.doubleValue();
		if(distanceKmDouble>100) {
			power=BigDecimal.valueOf(score).multiply(distanceKm.subtract(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(160),1,RoundingMode.HALF_EVEN);
		}
		if(distanceKmDouble>50) {
			power=power.add(BigDecimal.valueOf(score).multiply(distanceKm.subtract(BigDecimal.valueOf(50))).divide(BigDecimal.valueOf(80),1,RoundingMode.HALF_EVEN));
		}
		if(distanceKmDouble>20) {
			power=power.add(BigDecimal.valueOf(score).multiply(distanceKm.subtract(BigDecimal.valueOf(20))).divide(BigDecimal.valueOf(40),1,RoundingMode.HALF_EVEN));
		}
		if(distanceKmDouble>10) {
			power=power.add(BigDecimal.valueOf(score).multiply(distanceKm.subtract(BigDecimal.valueOf(10))).divide(BigDecimal.valueOf(20),1,RoundingMode.HALF_EVEN));
		}
		
		if(distanceKmDouble>10) {
			power=power.add(BigDecimal.valueOf(score).multiply(distanceKm.subtract(BigDecimal.valueOf(10))).divide(BigDecimal.valueOf(20),1,RoundingMode.HALF_EVEN));
		}
		//(10+分数-45/10)*公里
		BigDecimal score1=(BigDecimal.valueOf(score).subtract(BigDecimal.valueOf(45))).divide(BigDecimal.valueOf(10),0,RoundingMode.HALF_EVEN);
    	int powerInt=power.intValue();
      	TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
    	taskPowerLogs.setUserId(userId);
    	taskPowerLogs.setTaskType(PowerEnum.TripToUser);
    	taskPowerLogs.setJsonValue(String.format(jsonValue, userId,habitId,String.valueOf(powerInt),effectId));
    	taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);	
    	return powerInt;
	}
	
	/**
	 * 
	    * @Title: addUserDayStat
	    * @Description: TODO(增加行程计算分)
	    * @param @param uth
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
   public boolean addUserDayStat(UserTravelHistory uth) {
	   String driveEndTime=uth.getEndTime().substring(0,19);
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("userId", uth.getUserId());
		m.put("driveEndTime", driveEndTime);
		boolean isBestDrive=true;
		UserRoleLog role= userRoleLogService.getLatestUserRoleLogNoTrans(uth.getUserId());
 
		//行程power
//		String score="0";
		int power=addUserTripPowerLogs(uth.getId(),uth.getUserId(),uth.getHabitId(),uth.getMileage(),Double.valueOf(uth.getScore()));
		//查询当前俱乐部，并贡献分数与动力
		List<Map<String,Object>> families=familyInfoMapper.getFamiliesByUserId(uth.getUserId());
		
		for(Map<String,Object> map: families) {
			
			//进行俱乐部行程热度增加
			long familyId=Long.valueOf(map.get("familyId").toString());
			familyInfoMapper.updateFamilyDriveFlag(familyId);
//			familyInfoMapper.updateFamilyActiveLevel(familyId);
			
			//插入行程得分任务
			addFamilyTripPowerLogs(role.getUserId(), uth.getHabitId(),familyId, power,uth.getId());
			UserScoreDayStat userScoreDayStat=new UserScoreDayStat();
			userScoreDayStat.setUserId(uth.getUserId());
			//这个错误，应该通过familyId
			userScoreDayStat.setUserFamilyScoreId(0L);//这个id作废
			userScoreDayStat.setFamilyId(familyId);
			userScoreDayStat.setDaystamp(driveEndTime.substring(0,10));
			userScoreDayStat.setBrakeTimes(uth.getBrakeTimes()-uth.getBrakeTimesOffset());
			if(uth.getBrakeTimes()>0) {
				userScoreDayStat.setBrakeTimesUpdateTime(driveEndTime);
				isBestDrive=false;
			}
			userScoreDayStat.setTurnTimes(uth.getTurnTimes()-uth.getTurnTimesOffset());
			if(uth.getTurnTimes()>0) {
				userScoreDayStat.setTurnTimesUpdateTime(driveEndTime);
				isBestDrive=false;
			}
			userScoreDayStat.setSpeedTimes(uth.getSpeedTimes()-uth.getSpeedTimesOffset());
			if(uth.getSpeedTimes()>0) {
				userScoreDayStat.setSpeedTimesUpdateTime(driveEndTime);
			}
			
			int overSpeed=(uth.getOverspeedTimes()-uth.getOverspeedTimesOffset())>=60?1:0;
			userScoreDayStat.setOverspeedTimes(overSpeed);
			if(overSpeed>0) {
				userScoreDayStat.setOverspeedTimesUpdateTime(driveEndTime);
				isBestDrive=false;
			}
			int tiredDriveTimes=(uth.getTiredDrive()-uth.getTiredDriveOffset())>=120?1:0;
			long tireTime =Double.valueOf(uth.getTiredDrive()-uth.getTiredDriveOffset()).longValue();
			userScoreDayStat.setTiredDrive(tireTime);
			userScoreDayStat.setTiredDriveTimes(tiredDriveTimes);
			if(tiredDriveTimes>0) {
				userScoreDayStat.setTiredDriveTimesUpdateTime(driveEndTime);
				isBestDrive=false;
			}
			int nightDrive=(uth.getNightDrive()-uth.getNightDriveOffset())>=180?1:0;
			userScoreDayStat.setNightDriveTimes(nightDrive);
			if(nightDrive>0) {
				userScoreDayStat.setNightDriveTimesUpdateTime(driveEndTime);
				isBestDrive=false;
			 }
			userScoreDayStat.setMaxspeed(uth.getMaxspeed());
			userScoreDayStat.setUseCheluntai(uth.getUseCheluntai());
			userScoreDayStat.setUseFadongji(uth.getUseFadongji());
			userScoreDayStat.setUseHongniu(uth.getUseHongniu());
			userScoreDayStat.setUseShachepian(uth.getUseShachepian());
			userScoreDayStat.setUseYeshijing(uth.getUseYeshijing());
			userScoreDayStat.setUseZengyaqi(uth.getUseZengyaqi());
			userScoreDayStat.setMileage(uth.getMileage());
			userScoreDayStat.setTravelNum(1);
			//更新车位
			 ParkStation park=new ParkStation();
			 park.setUserId(uth.getUserId());
			 park.setFamilyId(familyId);
			 park.setExpireTime(driveEndTime);
			 park.setHabitId(uth.getHabitId());
			 int updatePark=parkStationMapper.updateParkStation(park);
			 if(updatePark==0) {//违停
				 userScoreDayStat.setIllegalStopTimes(1);
				 userScoreDayStat.setIllegalStopUpdateTime(driveEndTime);
				 isBestDrive=false;
			 }
			 userScoreDayStatMapper.insertOrUpdateUserDayStat(userScoreDayStat);
		}
		//插入行程数据
		addAchieve(uth.getUserId(),uth.getMileage(),AchieveEnum.AddGodTimes);
		String daystamp=driveEndTime.substring(0,10);
		if(!isBestDrive) {
			taskUserDayEndService.addUserEndDayIsBestDrive(uth.getUserId(), 1.0d, daystamp);
		}else {
			taskUserDayEndService.addUserEndDayIsBestDrive(uth.getUserId(), 0d, daystamp);
		}
		
		LOG.info("UPDATE SUCCESS");
	   return true;
   }
	private boolean addAchieve(long keyId,Double value,AchieveEnum type) {
		TaskAchieveAddValue taskAchieveAddValue=new TaskAchieveAddValue();
		taskAchieveAddValue.setAchieveType(type);
		taskAchieveAddValue.setKeyId(keyId);
		taskAchieveAddValue.setAddValue(value);
		taskAchieveAddValueMapper.insertTaskAchieveAddValue(taskAchieveAddValue);
		return true;
	}
}
