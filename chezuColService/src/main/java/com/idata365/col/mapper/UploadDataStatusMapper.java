package com.idata365.col.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.col.entity.UploadDataStatus;

/**
 * 
    * @ClassName: UploadDataStatusMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月24日
    *
 */
public interface UploadDataStatusMapper {
	 void insertUploadDataStatus(UploadDataStatus status);
	 void updateSensorUploadStatus(UploadDataStatus status);
	 
	 List<UploadDataStatus>  getUploadDataStatusTask(UploadDataStatus status);
	 void  lockUploadStatusTask(UploadDataStatus status);
	 void  updateUploadStatusTask(UploadDataStatus status);
	 void  updateFailUploadStatusTask(UploadDataStatus status);
	 void  clearLockTask(@Param("compareTimes") long compareTimes);
	 
	 List<UploadDataStatus>  getUploadDataDemo(Map<String,Object> map); 
	 
}
