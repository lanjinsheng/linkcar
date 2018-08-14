package com.idata365.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.config.ServiceConstant;
import com.idata365.constant.DicCarConstant;
import com.idata365.entity.CalDriveTask;
import com.idata365.entity.DicCar;
import com.idata365.entity.DriveDataMain;
import com.idata365.entity.DriveScore;
import com.idata365.entity.ReadyLotteryBean;
import com.idata365.entity.UserTravelHistory;
import com.idata365.mapper.app.CalDriveTaskMapper;
import com.idata365.mapper.app.CarMapper;
import com.idata365.mapper.app.LotteryMapper;
import com.idata365.mapper.app.UserFamilyLogsMapper;
import com.idata365.mapper.app.UserRoleLogMapper;
import com.idata365.mapper.app.UserTravelHistoryMapper;
import com.idata365.mapper.col.DriveDataEventMapper;
import com.idata365.mapper.col.DriveDataMainMapper;
import com.idata365.mapper.app.DriveScoreMapper;

@Service
public class CalScoreServiceV2 extends BaseService<CalScoreServiceV2>{
	
	private final static Logger LOG = LoggerFactory.getLogger(CalScoreServiceV2.class);
	@Autowired
	DriveDataMainMapper driveDataMainMapper;
	@Autowired
	DriveDataEventMapper driveDataEventMapper;
	@Autowired
	DriveScoreMapper driveScoreMapper;
	@Autowired
	UserFamilyLogsMapper userFamilyScoreMapper;
	@Autowired
	CalDriveTaskMapper calDriveTaskMapper;
	
	@Autowired
	private LotteryMapper lotteryMapper;
	@Autowired
	UserTravelHistoryMapper userTravelHistoryMapper;
	@Autowired 
	UserRoleLogMapper userRoleLogMapper;
	@Autowired 
	CarMapper carMapper;
	@Autowired 
	ServiceConstant serviceConstant;
	/**
	 * 查询装配的道具列表
	 * @param userId
	 * @return
	 */
	@Transactional
	public List<ReadyLotteryBean> queryReadyLottery(long userId)
	{
		ReadyLotteryBean paramBean = new ReadyLotteryBean();
		paramBean.setUserId(userId);
		paramBean.setDaystamp(getCurrentDayStr());
		
		List<ReadyLotteryBean> resultList = this.lotteryMapper.queryReadyLottery(paramBean);
		
		return resultList;
	}
	
	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, "yyyyMMdd");
		return dayStr;
	}
	
	
	 
	
	/**
	 * 
	    * @Title: updateTravelHistory
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param userTravelHistory
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	private boolean updateTravelHistory(UserTravelHistory userTravelHistory)
	{
		userTravelHistoryMapper.updateTravelHistory(userTravelHistory);
		return true;
	}
	static Map<Integer,Integer> ThreeAlarmScoreMap=new HashMap<Integer,Integer>();
	 
	 
	 public int getScoreByTimes(Integer times) {
		 if(ThreeAlarmScoreMap.size()==0) {
			 ThreeAlarmScoreMap.put(serviceConstant.getThreeAlarmTimes0Key(), serviceConstant.getThreeAlarmTimes0Score());
			ThreeAlarmScoreMap.put(serviceConstant.getThreeAlarmTimes1Key(), serviceConstant.getThreeAlarmTimes1Score());
			ThreeAlarmScoreMap.put(serviceConstant.getThreeAlarmTimes2Key(), serviceConstant.getThreeAlarmTimes2Score());
			ThreeAlarmScoreMap.put(serviceConstant.getThreeAlarmTimes3Key(), serviceConstant.getThreeAlarmTimes3Score());
			ThreeAlarmScoreMap.put(serviceConstant.getThreeAlarmTimes4Key(), serviceConstant.getThreeAlarmTimes4Score());
			ThreeAlarmScoreMap.put(serviceConstant.getThreeAlarmTimes5Key(), serviceConstant.getThreeAlarmTimes5Score());
			ThreeAlarmScoreMap.put(serviceConstant.getThreeAlarmTimes6Key(), serviceConstant.getThreeAlarmTimes6Score());
			ThreeAlarmScoreMap.put(serviceConstant.getThreeAlarmTimes7Key(), serviceConstant.getThreeAlarmTimes7Score());
			ThreeAlarmScoreMap.put(serviceConstant.getThreeAlarmTimes8Key(), serviceConstant.getThreeAlarmTimes8Score());
		 }
		 return ThreeAlarmScoreMap.get(times);
	 }
	
	/**
	 * 
	    * @Title: calScoreByUH
	    * @Description: TODO(计算行程业务分)
	    * @param @param userId
	    * @param @param habitId
	    * @param @return    参数
	    * @return List<DriveScore>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean calScoreByUHInsertDb(Long userId,Long habitId,DriveDataMain dm){
		DriveScore ds=new DriveScore();
		ds.setUserId(userId);
		ds.setHabitId(habitId);
		UserTravelHistory userTravelHistory=new UserTravelHistory();
		userTravelHistory.setUserId(userId);
		userTravelHistory.setHabitId(habitId);
		userTravelHistory.setSpeedTimesOffset(0);
		
//		String driveEndTime=dm.getDriveEndTime().substring(0, 19);
		//通过driveEndTime 获取用户的角色与familyId
//		List<UserFamilyRoleLog> roles=this.getRolesByUserIdTime(userId, driveEndTime);
//		UserRoleLog ur=new UserRoleLog();
//		ur.setUserId(userId);
//        ur.setEndTime(driveEndTime);
//        UserRoleLog getRole=userRoleLogMapper.getLatestUserRoleLogByTime(ur);
//        
        //距离
        BigDecimal distance=dm.getDriveDistance();
        //时间
        long driveTimes=dm.getDriveTimes();
        BigDecimal tireRatio=BigDecimal.valueOf(1);
        BigDecimal scoreBrakeDown=BigDecimal.valueOf(20);
        BigDecimal scoreBrakeUp=BigDecimal.valueOf(20);
        BigDecimal scoreBrakeTurn=BigDecimal.valueOf(20);
        BigDecimal scoreOverSpeed=BigDecimal.valueOf(20);
        BigDecimal brakeDown=BigDecimal.valueOf(dm.getBrakeTimes());
//        BigDecimal brakeUp=BigDecimal.valueOf(dm.getSpeedUpTimes());
        BigDecimal overSpeed=BigDecimal.valueOf(0);
        BigDecimal brakeTurn=BigDecimal.valueOf(dm.getTurnTimes());
        
        brakeDown= brakeDown.multiply(BigDecimal.valueOf(10000)).divide(distance,1,RoundingMode.HALF_EVEN);
        overSpeed= overSpeed.multiply(BigDecimal.valueOf(10000)).divide(distance,1,RoundingMode.HALF_EVEN);
        brakeTurn= brakeTurn.multiply(BigDecimal.valueOf(10000)).divide(distance,1,RoundingMode.HALF_EVEN);
        //急减
        Integer brakeDownTimes=brakeDown.intValue()>serviceConstant.getMaxAlarmTimes()?serviceConstant.getMaxAlarmTimes():brakeDown.intValue();
        scoreBrakeDown=BigDecimal.valueOf(getScoreByTimes(brakeDownTimes));
        userTravelHistory.setBrakeScore(scoreBrakeDown.doubleValue());
       //超速
        Integer overSpeedTimes=overSpeed.intValue()>serviceConstant.getMaxAlarmTimes()?serviceConstant.getMaxAlarmTimes():overSpeed.intValue();
        scoreOverSpeed=BigDecimal.valueOf(getScoreByTimes(overSpeedTimes));
        userTravelHistory.setOverspeedScore(scoreOverSpeed.doubleValue());
 
        //急转
        
        Integer brakeTurnTimes=brakeTurn.intValue()>serviceConstant.getMaxAlarmTimes()?serviceConstant.getMaxAlarmTimes():brakeTurn.intValue();
        scoreBrakeTurn=BigDecimal.valueOf(getScoreByTimes(brakeTurnTimes));
        userTravelHistory.setTurnScore(scoreBrakeTurn.doubleValue());

        
        if(driveTimes>=serviceConstant.getTiredHourMaxKey().intValue()*3600) {
        	tireRatio=BigDecimal.valueOf(serviceConstant.getTiredHourMaxRate());
        }else if(driveTimes>=serviceConstant.getTiredHourGrade1Key().intValue()*3600) {
        	tireRatio=BigDecimal.valueOf(serviceConstant.getTiredHourGrade1Rate());
        }else if(driveTimes>=serviceConstant.getTiredHourGrade2Key().intValue()*3600){
        	tireRatio=BigDecimal.valueOf(serviceConstant.getTiredHourGrade2Rate());
        }else {
        	tireRatio=BigDecimal.valueOf(serviceConstant.getTiredHourGrade3Rate());
        }
        userTravelHistory.setTiredRate(tireRatio.doubleValue());
        int score=scoreBrakeDown.add(scoreBrakeUp).add(scoreBrakeTurn).add(BigDecimal.valueOf(40)).multiply(tireRatio).intValue();
        int driveScore=score;
        //查询车辆进行分数加成
        String endTime=dm.getDriveEndTime().substring(0, 19);
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("time", endTime);
        Map<String,Object> car=carMapper.getUserCar(paramMap);
        Long userCarId=0L;
        if(car!=null) {
        	userCarId=Long.valueOf(car.get("userCarId").toString());
        	int carId=Integer.valueOf(car.get("carId").toString());
        	DicCar dc=	DicCarConstant.getDicCar(carId);
        	BigDecimal addScorePercent=BigDecimal.valueOf(dc.getClubScoreUpPercent()).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);
        	score+=(BigDecimal.valueOf(score).multiply(addScorePercent).intValue());
        	userTravelHistory.setCarId(carId);
        }
        
        if(score<serviceConstant.getProtectScore().intValue()) {
        	score=serviceConstant.getProtectScore().intValue();
        }
        if(driveScore<serviceConstant.getProtectScore().intValue()) {
        	driveScore=serviceConstant.getProtectScore().intValue();
        }
        userTravelHistory.setDriveScore(String.valueOf(driveScore));
        userTravelHistory.setScore(String.valueOf(score));
        userTravelHistory.setUserCarId(userCarId);
		userTravelHistoryMapper.updateTravelHistory(userTravelHistory);
		return true;
		
	}

	
	//比较
	private long min(long a,long b) {
		if(a>b) {
			return b;
		}else {
			return a;
		}
	}
	
	private long max(long a,long b) {
		if(a>b) {
			return a;
		}else {
			return b;
		}
	}
	
 //任务执行
//	void lockCalScoreTask(CalDriveTask driveScore);
	@Transactional
	public List<CalDriveTask> getCalScoreTask(CalDriveTask driveScore){
		//先锁定任务
		calDriveTaskMapper.lockCalScoreTask(driveScore);
		//返回任务列表
		return calDriveTaskMapper.getCalScoreTask(driveScore);
	}
	@Transactional
	public	void updateSuccCalScoreTask(CalDriveTask driveScore) {
		calDriveTaskMapper.updateSuccCalScoreTask(driveScore);
	}
//	
	@Transactional
	public void updateFailCalScoreTask(CalDriveTask driveScore) {
		if(driveScore.getCalFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			driveScore.setCalStatus(2);
		}else {
			driveScore.setCalStatus(0);
		}
		calDriveTaskMapper.updateFailCalScoreTask(driveScore);
	}
//	
	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		calDriveTaskMapper.clearLockTask(compareTimes);
	}
//	
	
	
	public static void main(String []args)
	{
		System.out.println("2017-11-24 10:47:32.336".substring(0,19));
	}
}
