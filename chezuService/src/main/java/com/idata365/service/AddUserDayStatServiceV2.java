package com.idata365.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.TaskAchieveAddValue;
import com.idata365.entity.TaskPowerLogs;
import com.idata365.enums.AchieveEnum;
import com.idata365.enums.PowerEnum;
import com.idata365.constant.DicComponentConstant;
import com.idata365.constant.FamilyRelationConstant;
import com.idata365.entity.DriveScore;
import com.idata365.entity.FamilyRelation;
import com.idata365.entity.InteractTempCar;
import com.idata365.entity.ParkStation;
import com.idata365.entity.UserFamilyRoleLog;
import com.idata365.entity.UserRoleLog;
import com.idata365.entity.UserScoreDayStat;
import com.idata365.entity.UserTravelHistory;
import com.idata365.mapper.app.CarMapper;
import com.idata365.mapper.app.CarpoolMapper;
import com.idata365.mapper.app.FamilyInfoMapper;
import com.idata365.mapper.app.InteractPeccancyMapper;
import com.idata365.mapper.app.InteractTempCarMapper;
import com.idata365.mapper.app.ParkStationMapper;
import com.idata365.mapper.app.TaskAchieveAddValueMapper;
import com.idata365.mapper.app.TaskPowerLogsMapper;
import com.idata365.mapper.app.UserFamilyLogsMapper;
import com.idata365.mapper.app.UserScoreDayStatMapper;
import com.idata365.mapper.app.UserTravelHistoryMapper;
import com.idata365.util.RandUtils;

@Service
public class AddUserDayStatServiceV2 extends BaseService<AddUserDayStatServiceV2>{
	private final static Logger LOG = LoggerFactory.getLogger(AddUserDayStatServiceV2.class);
 
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
	@Autowired
	InteractPeccancyMapper interactPeccancyMapper;
	@Autowired
	InteractTempCarMapper interactTempCarMapper;
	@Autowired
	CarpoolMapper carpoolMapper;
	@Autowired
	BoxTreasureService boxTreasureService;
	@Autowired
	CarMapper carMapper;
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
	private boolean addFamilyTripPowerLogs(long userId,long habitId,long familyId,int power,long effectId,String relation) {
		 
		    String jsonValue="{\"userId\":%d,\"habitId\":%d,\"familyId\":%d,\"toFamilyValue\":%s,\"relation\":\"%s\",\"effectId\":%d}";
	    	TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
	    	taskPowerLogs.setUserId(userId);
	    	taskPowerLogs.setTaskType(PowerEnum.TripToFamily);
	    	int familyPower=BigDecimal.valueOf(power).divide(BigDecimal.valueOf(2),0,RoundingMode.HALF_EVEN).intValue();
	    	taskPowerLogs.setJsonValue(String.format(jsonValue, userId,habitId,familyId,familyPower,relation,effectId));
	    	int hadAdd=taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);	
	    	if(hadAdd>0) {
	    		return true;
	    	}
		return false;
	}
	
    String CarPoolPowerLogsJsonValue="{\"userId\":%d,\"travelId\":%d,\"passengerPower\":%d,\"effectId\":%d}";
	private boolean addUserCarPoolPowerLogs(long userId,long travelId,int passengerPower) {
//		passengerPower=Double.valueOf((passengerPower*0.8)).intValue();//打八折
    	TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
    	taskPowerLogs.setUserId(userId);
    	taskPowerLogs.setTaskType(PowerEnum.CarPool);
    	taskPowerLogs.setJsonValue(String.format(CarPoolPowerLogsJsonValue, userId,travelId,passengerPower,travelId));
    	int hadAdd=taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);	
    	if(hadAdd>0) {
    		return true;
    	}
	return false;
}
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
	private int addUserTripPowerLogs_del(long effectId,long userId,long habitId,double distance,Double score) {
		 
		String jsonValue="{\"userId\":%d,\"habitId\":%d,\"toUserValue\":%s,\"effectId\":%d}";
		//(10+分数-45/10)*公里
//		BigDecimal score1=(BigDecimal.valueOf(score).subtract(BigDecimal.valueOf(45))).divide(BigDecimal.valueOf(10),0,RoundingMode.HALF_EVEN);
//    	int power=(score1.add(BigDecimal.valueOf(10))).multiply(BigDecimal.valueOf(distance)).divide(BigDecimal.valueOf(1000),0,RoundingMode.HALF_EVEN).intValue();
    	BigDecimal power=BigDecimal.valueOf(0);
    	if(distance>=(100*1000)) {
    		double d=distance-100000;
    		power=BigDecimal.valueOf(score).multiply(BigDecimal.valueOf(d)).divide(BigDecimal.valueOf(160000),1,RoundingMode.HALF_EVEN);
    	}
    	if(distance>(50*1000)) {//50-100
//    		double d=distance-50000;
    		double d=Math.min(distance, 100000)-50000;
    		power=power.add(BigDecimal.valueOf(score).multiply(BigDecimal.valueOf(d)).divide(BigDecimal.valueOf(80000),1,RoundingMode.HALF_EVEN));
    	}
    	if(distance>(20*1000)) {//20-50
//    		double d=distance-20000;
    		double d=Math.min(distance, 50000)-20000;
    		power=power.add(BigDecimal.valueOf(score).multiply(BigDecimal.valueOf(d)).divide(BigDecimal.valueOf(40000),1,RoundingMode.HALF_EVEN));
    	}
    	if(distance>(10*1000)) {//10-20
//    		double d=distance-10000;
    		double d=Math.min(distance, 20000)-10000;
    		power=power.add(BigDecimal.valueOf(score).multiply(BigDecimal.valueOf(d)).divide(BigDecimal.valueOf(20000),1,RoundingMode.HALF_EVEN));
    	}
    	if(distance>(0) ) {//0-10
//    		double d=distance;
    		double d=Math.min(distance, 10000);
    		power=power.add(BigDecimal.valueOf(score).multiply(BigDecimal.valueOf(d)).divide(BigDecimal.valueOf(10000),1,RoundingMode.HALF_EVEN));
    	}
    	
      	TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
    	taskPowerLogs.setUserId(userId);
    	taskPowerLogs.setTaskType(PowerEnum.TripToUser);
    	taskPowerLogs.setJsonValue(String.format(jsonValue, userId,habitId,String.valueOf(power.intValue()),effectId));
    	taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);	
    	return power.intValue();
	}
	
	private int addUserTripPowerLogsV2(double distance,Double score,int carId) {
		//有效公里数
		BigDecimal validDistance=BigDecimal.valueOf(score).multiply(BigDecimal.valueOf(distance)).divide(BigDecimal.valueOf(100000),1,RoundingMode.HALF_EVEN); 
		double dkm=validDistance.doubleValue();
		double x1=0d,x2=0d,x3=0d;
		 if(carId==2) {//豪华
			  if(dkm>200) {
				 
				}
				if(dkm>80) {
					  x1=Math.min(200d, (dkm-80))*8;
				}
				if(dkm>20) {
					  x2=Math.min(80d, (dkm-20))*14;
				}
				if(dkm>0) {
					  x3=Math.min(20d, dkm)*12;
				}
		 }else if(carId==3){//跑车
			 if(dkm>200) {
				 
				}
				if(dkm>80) {
					  
				}
				if(dkm>20) {
					  x2=Math.min(80d, (dkm-20))*12;
				}
				if(dkm>0) {
					  x3=Math.min(20d, dkm)*16;
				} 
		 }else {//suv
				if(dkm>200) {
					 
				}
				if(dkm>80) {
					  x1=Math.min(200d, (dkm-80))*5;
				}
				if(dkm>20) {
					  x2=Math.min(80d, (dkm-20))*8;
				}
				if(dkm>0) {
					  x3=Math.min(20d, dkm)*10;
				}
				
			 }
	 
    	return Double.valueOf((x1+x2+x3)).intValue();
	}
	
	
	public static void main(String []args) {
		int power=(int)(32*(1+0.3-0.1));
		 
		System.out.println(power);
	}
	
	private void addCar(List<InteractTempCar> batchInsert,UserTravelHistory uth,int r,int type) {
		 InteractTempCar car=new InteractTempCar();
		 car.setPowerNum(Long.valueOf(r));
		 car.setType(type);
		 car.setUserId( uth.getUserId());
		 car.setCarId( 1L);
		 car.setCarType("1");
		 car.setTravelId(uth.getId());
		 car.setDaystamp(uth.getEndTime().substring(0,10).replace("-", ""));
		 car.setUuid(UUID.randomUUID().toString().replace("-", ""));
		 car.setR(RandUtils.generateRand(1, 100));
		 batchInsert.add(car);
	}
	String jsonValue="{\"userId\":%d,\"habitId\":%d,\"toUserValue\":%s,\"effectId\":%d}";

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
//		UserRoleLog role= userRoleLogService.getLatestUserRoleLogNoTrans(uth.getUserId());
 
		//行程power
//		String score="0";
		int calPower=addUserTripPowerLogsV2(uth.getMileage(),Double.valueOf(uth.getScore()),uth.getCarId());
		//查看未缴纳罚单数
		Map<String,Object> payMap=new HashMap<String,Object>();
		payMap.put("userId", uth.getUserId());
		payMap.put("carId", 0L);
		payMap.put("nowLong", System.currentTimeMillis());
		int peccancyNum=interactPeccancyMapper.getUnpayPeccancy(payMap);
		int power=calPower;
		List<InteractTempCar> batchInsert=new ArrayList<InteractTempCar>();
		double reduce=0;
		if(peccancyNum>0) {
			reduce= Double.valueOf(peccancyNum)/10;//减去罚单降低的动力
		} 
		
		List<Map<String,Object>> compList=carMapper.getCarComponents(uth.getUserCarId());
		double addCar=0d;
		for(Map<String,Object> comp:compList) {
			int componentId=Integer.valueOf(comp.get("componentId").toString());
			String quality=DicComponentConstant.getDicComponent(componentId).getQuality();
			if(quality.equals("B")) {
				addCar+=0.1;
			}else if(quality.equals("A")) {
				addCar+=0.2;
			}else if(quality.equals("S")) {
				addCar+=0.3;
			}
			comp.put("userComponentId", comp.get("id"));
		 carMapper.updateCarComponents(Long.valueOf(comp.get("id").toString()));
		 carMapper.insertComponentUserUseLog(comp);
		}
		power=(int)(calPower*(1+addCar-reduce));
		//插入power任务
		TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
    	taskPowerLogs.setUserId(uth.getUserId());
    	taskPowerLogs.setTaskType(PowerEnum.TripToUser);
    	taskPowerLogs.setJsonValue(String.format(jsonValue, uth.getUserId(),uth.getHabitId(),String.valueOf(power),uth.getId()));
    	taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);	
	 if(power>0) {
		 //形成被偷动力小车
		 int stealPower=calPower;
		 stealPower=Double.valueOf((stealPower*0.2)).intValue();
		 int r=RandUtils.generateRand(1,3);
		 while(r<=stealPower) {
			 addCar(batchInsert, uth,r,1) ;
			 stealPower=stealPower-r;
			 r=RandUtils.generateRand(1,3);
		 }
		 if(stealPower>0) {
			 addCar(batchInsert, uth,stealPower,1) ;
		 }
	 }
		int fadan=uth.getBrakeTimes()+uth.getSpeedTimes()+uth.getTurnTimes()+uth.getOverspeedTimes();
		if(fadan>0) {//三急+超速
			fadan=fadan>8?8:fadan;
			for(int j=0;j<fadan;j++) {
				addCar(batchInsert, uth,(int)(power*0.1),2) ;
			}
		}
		 //批量插入
		if(batchInsert.size()>0) {
			interactTempCarMapper.batchInsertTempCar(batchInsert);
		}
		
		//顺风车处理逻辑
		List<Map<String,Object>> carpools=carpoolMapper.getCarPool(uth.getUserId());
		for(Map<String,Object> carpool:carpools){
			long passengerId=Long.valueOf(carpool.get("passengerId").toString());
			if(addUserCarPoolPowerLogs(passengerId,uth.getId(),Double.valueOf((power*0.8)).intValue())) {
				//更新用户顺风车记录
				carpool.put("driverPower", Double.valueOf(power*0.1).intValue());
				carpool.put("passengerPower", Double.valueOf(power*0.8).intValue());
				carpool.put("travelId",uth.getId());
				carpoolMapper.updateCarPool(carpool);
			}else {
				LOG.error("插入顺风车记录失败passengerId="+passengerId+"==travelId="+uth.getId());
			}
		}
		if(carpools!=null && carpools.size()>0) {
			addUserCarPoolPowerLogs(uth.getUserId(),uth.getId(),Double.valueOf(power*0.1*carpools.size()).intValue());
		}
		
		//查询当前俱乐部，并贡献分数与动力
		   List<Map<String,Object>> families=familyInfoMapper.getFamiliesByUserId(uth.getUserId());
			UserScoreDayStat userScoreDayStat=new UserScoreDayStat();
			userScoreDayStat.setUserId(uth.getUserId());
			//这个错误，应该通过familyId
			userScoreDayStat.setUserFamilyScoreId(0L);//这个id作废

			userScoreDayStat.setDaystamp(driveEndTime.substring(0,10));
			userScoreDayStat.setBrakeTimes(uth.getBrakeTimes()-uth.getBrakeTimesOffset());
			if(uth.getBrakeTimes()>0) {
				userScoreDayStat.setBrakeTimesUpdateTime(driveEndTime);
			}
			userScoreDayStat.setTurnTimes(uth.getTurnTimes()-uth.getTurnTimesOffset());
			if(uth.getTurnTimes()>0) {
				userScoreDayStat.setTurnTimesUpdateTime(driveEndTime);
			}
			userScoreDayStat.setSpeedTimes(uth.getSpeedTimes()-uth.getSpeedTimesOffset());
			if(uth.getSpeedTimes()>0) {
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
			userScoreDayStat.setMileage(uth.getMileage());
			userScoreDayStat.setTime(Double.valueOf(uth.getTime()));
			userScoreDayStat.setTravelNum(1);
			userScoreDayStat.setAvgScore(Double.valueOf(uth.getScore()));
			userScoreDayStat.setIllegalStopPenalTimes(0);
			userScoreDayStat.setIllegalStopTimes(0);
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("userId", uth.getUserId());
			if(families!=null && families.size()>0) {
				for(Map<String,Object> map: families) {
					long familyId=Long.valueOf(map.get("familyId").toString());
					m.put("familyId", familyId);
	//				familyInfoMapper.updateFamilyDriveFlag(familyId);
	//				familyInfoMapper.updateFamilyActiveLevel(familyId);
					//获取挑战记录
					if(power>0) {
						List<FamilyRelation> relations=FamilyRelationConstant.getFamilyRelation(familyId);
						if(relations!=null) {
							for(FamilyRelation relation:relations) {
								String postRelation=relation.getSelfFamilyId()+"-"+relation.getCompetitorFamilyId()+"-"+relation.getRelationType();
								addFamilyTripPowerLogs(uth.getUserId(), uth.getHabitId(),familyId, power,uth.getId(),postRelation);
							}
						}else {
							//插入行程得分任务
							addFamilyTripPowerLogs(uth.getUserId(), uth.getHabitId(),familyId, power,uth.getId(),"0");
						}
					}
				
				    userScoreDayStat.setFamilyId(familyId);
					userScoreDayStatMapper.insertOrUpdateUserDayStat(userScoreDayStat);
					//更新userFamilyRelation
//					familyInfoMapper.updateHadNewPower(m);
				}
			}else {
				userScoreDayStat.setFamilyId(0L);
				userScoreDayStatMapper.insertOrUpdateUserDayStat(userScoreDayStat);
			}
		
			//行程宝箱
			boxTreasureService.insertTripBoxNotran(uth.getId(), uth.getUserId());
		LOG.info(uth.getId()+"addUserDay SUCCESS");
	    return true;
   }
	
//	@Transactional
//	public boolean testTempCar(UserTravelHistory uth) {
////			UserRoleLog role= userRoleLogService.getLatestUserRoleLogNoTrans(uth.getUserId());
//	 
//			//行程power
////			String score="0";
//			int calPower=addUserTripPowerLogs(uth.getId(),uth.getUserId(),uth.getHabitId(),uth.getMileage(),Double.valueOf(uth.getScore()));
//			//查看未缴纳罚单数
//			Map<String,Object> payMap=new HashMap<String,Object>();
//			payMap.put("userId", uth.getUserId());
//			payMap.put("carId", 0L);
//			payMap.put("nowLong", System.currentTimeMillis());
//			int peccancyNum=interactPeccancyMapper.getUnpayPeccancy(payMap);
//			int power=calPower;
//			List<InteractTempCar> batchInsert=new ArrayList<InteractTempCar>();
//			if(peccancyNum>0) {
//				 power=(int)(calPower*(1-Double.valueOf(peccancyNum)/10));
//				 if(power>0) {
//					 //形成被偷动力小车
//					 int stealPower=calPower-power;
//					 int r=RandUtils.generateRand(1,3);
//					 while(r<=stealPower) {
//						 addCar(batchInsert, uth,r,1) ;
//						 stealPower=stealPower-r;
//						 r=RandUtils.generateRand(1,3);
//					 }
//					 if(stealPower>0) {
//						 addCar(batchInsert, uth,stealPower,1) ;
//					 }
//				 }
//			}
//			int fadan=uth.getBrakeTimes()+uth.getSpeedTimes()+uth.getTurnTimes()+uth.getOverspeedTimes();
//			if(fadan>0) {//三急+超速
//				for(int j=0;j<fadan;j++) {
//					addCar(batchInsert, uth,0,2) ;
//				}
//			}
//			 //批量插入
//			interactTempCarMapper.batchInsertTempCar(batchInsert);
//		    return true;
//	   }
//	private boolean addAchieve(long keyId,Double value,AchieveEnum type) {
//		TaskAchieveAddValue taskAchieveAddValue=new TaskAchieveAddValue();
//		taskAchieveAddValue.setAchieveType(type);
//		taskAchieveAddValue.setKeyId(keyId);
//		taskAchieveAddValue.setAddValue(value);
//		taskAchieveAddValueMapper.insertTaskAchieveAddValue(taskAchieveAddValue);
//		return true;
//	}
}
