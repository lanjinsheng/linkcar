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
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.DriveDataEvent;
import com.idata365.entity.DriveDataLog;
import com.idata365.entity.DriveDataMain;
import com.idata365.entity.UserTravelHistory;
import com.idata365.mapper.app.UserTravelHistoryMapper;
import com.idata365.mapper.col.CalDriveTaskMapper;
import com.idata365.mapper.col.DriveDataEventMapper;
import com.idata365.mapper.col.DriveDataLogMapper;
import com.idata365.mapper.col.DriveDataMainMapper;
import com.idata365.util.DateTools;


@Service
public class DriveOpenService extends BaseService<DriveOpenService>{
	private final static Logger LOG = LoggerFactory.getLogger(DriveOpenService.class);
    @Autowired
	DriveDataLogMapper driveDataLogMapper;
    @Autowired
    DriveDataEventMapper driveDataEventMapper;
    
    @Autowired
    DriveDataMainMapper driveDataMainMapper;
	/**
	 * 
	    * @Title: listDriveLogByUH
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param log
	    * @param @return    参数
	    * @return List<DriveDataLog>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public List<DriveDataLog>  listDriveLogByUH(DriveDataLog log) {
		  List<DriveDataLog> list= driveDataLogMapper.listDriveLogByUH(log);
		  return list;
	}
	public List<DriveDataEvent>  listDriveAlarmByUH(Long userId,Long habitId) {
		DriveDataMain drive=new DriveDataMain();
		List<DriveDataEvent> list=new ArrayList<DriveDataEvent>();
		drive.setUserId(userId);
		drive.setHabitId(habitId);
		drive=driveDataMainMapper.getDriveDataMainByUH(drive);
		if(drive!=null) {
		   list=driveDataEventMapper.listDriveEventByMainId(drive);
		}
		  return list; 
   }
}