package com.idata365.service;
/**
 * 
    * @ClassName: SynDriveDataService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.DriveDataEvent;
import com.idata365.entity.DriveDataMain;
import com.idata365.mapper.col.DriveDataEventMapper;
import com.idata365.mapper.col.DriveDataMainMapper;


@Service
public class SynDriveDataService extends BaseService<SynDriveDataService>{
	private final static Logger LOG = LoggerFactory.getLogger(SynDriveDataService.class);
 
	@Autowired
	DriveDataMainMapper driveDataMainMapper;
	@Autowired
	DriveDataEventMapper driveDataEventMapper;
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


	public boolean recieveDrive(List<Map<String,Object>> postList){
		//进行驾驶数据同步到业务层，逻辑待写入
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
