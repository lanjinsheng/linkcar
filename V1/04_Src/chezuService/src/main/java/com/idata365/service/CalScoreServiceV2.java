package com.idata365.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

import com.idata365.entity.CalDriveTask;
import com.idata365.entity.DriveDataMain;
import com.idata365.entity.DriveScore;
import com.idata365.entity.ReadyLotteryBean;
import com.idata365.entity.UserRoleLog;
import com.idata365.entity.UserTravelHistory;
import com.idata365.mapper.app.CalDriveTaskMapper;
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
	static {
		ThreeAlarmScoreMap.put(0, 20);
		ThreeAlarmScoreMap.put(1, 17);
		ThreeAlarmScoreMap.put(2, 12);
		ThreeAlarmScoreMap.put(3, 8);
		ThreeAlarmScoreMap.put(4, 3);
		ThreeAlarmScoreMap.put(5, -3);
		ThreeAlarmScoreMap.put(6, -9);
		ThreeAlarmScoreMap.put(7, -15);
		ThreeAlarmScoreMap.put(8, -20);
		ThreeAlarmScoreMap.put(9, -20);
		ThreeAlarmScoreMap.put(10, -20);
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
        
        BigDecimal brakeDown=BigDecimal.valueOf(dm.getBrakeTimes());
        BigDecimal brakeUp=BigDecimal.valueOf(dm.getSpeedUpTimes());
        BigDecimal brakeTurn=BigDecimal.valueOf(dm.getTurnTimes());
        
        brakeDown= brakeDown.multiply(BigDecimal.valueOf(10000)).divide(distance,1,RoundingMode.HALF_EVEN);
        brakeUp= brakeUp.multiply(BigDecimal.valueOf(10000)).divide(distance,1,RoundingMode.HALF_EVEN);
        brakeTurn= brakeTurn.multiply(BigDecimal.valueOf(10000)).divide(distance,1,RoundingMode.HALF_EVEN);
        //急减
        if(brakeDown.intValue()>=10) {
        	scoreBrakeDown=BigDecimal.valueOf(ThreeAlarmScoreMap.get(10));
        }else {
        	scoreBrakeDown=BigDecimal.valueOf(ThreeAlarmScoreMap.get(brakeDown.intValue()));
        }
        
       //急加 
        if(brakeUp.intValue()>=10) {
        	scoreBrakeUp=BigDecimal.valueOf(ThreeAlarmScoreMap.get(10));
        }else {
        	scoreBrakeUp=BigDecimal.valueOf(ThreeAlarmScoreMap.get(brakeUp.intValue()));
        }
        
        //急转
        if(brakeTurn.intValue()>=10) {
        	scoreBrakeTurn=BigDecimal.valueOf(ThreeAlarmScoreMap.get(10));
        }else {
        	scoreBrakeTurn=BigDecimal.valueOf(ThreeAlarmScoreMap.get(brakeTurn.intValue()));
        }
        
        if(driveTimes>=4*3600) {
        	tireRatio=BigDecimal.valueOf(0.9);
        }else if(driveTimes>=3*3600) {
        	tireRatio=BigDecimal.valueOf(0.95);
        }else {
        	tireRatio=BigDecimal.valueOf(1);
        }
        int score=scoreBrakeDown.add(scoreBrakeUp).add(scoreBrakeTurn).add(BigDecimal.valueOf(40)).multiply(tireRatio).intValue();
        if(score<30) {
        	score=30;
        }
        userTravelHistory.setScore(String.valueOf(score));
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
