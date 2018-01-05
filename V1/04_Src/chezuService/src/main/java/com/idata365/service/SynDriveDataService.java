package com.idata365.service;
/**
 * 
    * @ClassName: SynDriveDataService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.DriveDataEvent;
import com.idata365.entity.DriveDataMain;
import com.idata365.entity.UserTravelHistory;
import com.idata365.entity.UserTravelLottery;
import com.idata365.mapper.app.UserTravelHistoryMapper;
import com.idata365.mapper.app.UserTravelLotteryMapper;
import com.idata365.mapper.col.CalDriveTaskMapper;
import com.idata365.mapper.col.DriveDataEventMapper;
import com.idata365.mapper.col.DriveDataMainMapper;
import com.idata365.util.DateTools;


@Service
public class SynDriveDataService extends BaseService<SynDriveDataService>{
	private final static Logger LOG = LoggerFactory.getLogger(SynDriveDataService.class);
 
	@Autowired
	DriveDataMainMapper driveDataMainMapper;
	@Autowired
	DriveDataEventMapper driveDataEventMapper;
	@Autowired
	UserTravelHistoryMapper userTravelHistoryMapper;
	@Autowired
	CalDriveTaskMapper calDriveTaskMapper;
	@Autowired
	UserTravelLotteryMapper userTravelLotteryMapper;
	
	/**
	 * 
	    * @Title: getSendDriveTask
	    * @Description: TODO(先锁定,后调用)
	    * @param @param drive    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public List<DriveDataMain>  getSendDriveTask(DriveDataMain drive) {
		    driveDataMainMapper.lockSendDriveTask(drive);
		  List<DriveDataMain> list= driveDataMainMapper.getSendDriveTask(drive);
		  return list;
	}
	/**
	 * 生成随机数，范围[min, max]
	 * @param min
	 * @param max
	 * @return
	 */
	public static int generateRand(int min, int max)
	{
		int randNum = ThreadLocalRandom.current().nextInt(max - min + 1) + min;
		return randNum;
	}
	private  UserTravelLottery getTravelLottery(Long userId,Long habitId) {
		int awardId=generateRand(1,8);
		UserTravelLottery lottery=new UserTravelLottery();
		lottery.setAwardCount(1);
		lottery.setAwardId(awardId);
		lottery.setHabitId(habitId);
		lottery.setUserId(userId);
		lottery.setHadGet(0);
		return lottery;
	}
	public boolean recieveDrive(List<DriveDataMain> driveList){
		//进行驾驶数据同步到业务层，逻辑待写入
		List<UserTravelHistory>  list=new ArrayList<UserTravelHistory>();
		String createTime=DateTools.getCurDateYYYYMMddHHmmss();
		List<UserTravelLottery> lotterys=new ArrayList<UserTravelLottery>();
		for( DriveDataMain drive:driveList) {
			UserTravelHistory uth=new UserTravelHistory();
			uth.setUserId(drive.getUserId());
			uth.setHabitId(drive.getHabitId());
			uth.setCreateTime(createTime);
			uth.setStartTime(drive.getDriveStartTime());
			uth.setEndTime(drive.getDriveEndTime());
			uth.setMileage(drive.getDriveDistance().doubleValue());
			uth.setTime( drive.getDriveTimes().intValue());
			uth.setBrakeTimes(drive.getBrakeTimes());
			uth.setSpeedTimes(drive.getSpeedUpTimes());
			uth.setTurnTimes(drive.getTurnTimes());
			uth.setOverspeedTimes(drive.getOverspeedTimes());
			uth.setMaxspeed(drive.getMaxSpeed().doubleValue());
			list.add(uth);
			lotterys.add(getTravelLottery(uth.getUserId(),uth.getHabitId()));
		}
		if(list.size()>0) {
			userTravelHistoryMapper.batchInsert(list);
		}
		if(lotterys.size()>0) {
			userTravelLotteryMapper.batchInsert(lotterys);
		}
		return true;
	}
	public boolean addCalTask(List<DriveDataMain> driveList) {
		 //插入计分项目
		if(driveList.size()>0) {
   	     calDriveTaskMapper.batchInsertCalTask(driveList);
		}
   	     return true;
	}
	public void clearLockTask(){
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		driveDataMainMapper.clearLockTask(compareTimes);
	}
	public List<DriveDataEvent> listDriveEventByMainId(DriveDataMain drive){
		return driveDataEventMapper.listDriveEventByMainId(drive);
	}
	public void updateSuccSendDriveTask(DriveDataMain drive) {
		drive.setIsPost(1);
		driveDataMainMapper.updateSuccSendDriveTask(drive);
	}
	public void updateFailSendDriveTask(DriveDataMain drive) {
		drive.setIsPost(0);
		driveDataMainMapper.updateFailSendDriveTask(drive);
	}
	
}
