package com.idata365.col.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.col.entity.DriveDataMain;
import com.idata365.col.entity.UploadDataStatus;
/**
 * 
    * @ClassName: DriveDataLogMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */
public interface DriveDataMainMapper {

    void insertDataLog(DriveDataMain data);
    
	 List<DriveDataMain>  getSendDriveTask(DriveDataMain drive);
	 void  lockSendDriveTask(DriveDataMain drive);
	 void  updateSuccSendDriveTask(DriveDataMain drive);
	 void  updateFailSendDriveTask(DriveDataMain drive);
	 
	 void  clearLockTask(@Param("compareTimes") long compareTimes);
	 
}
