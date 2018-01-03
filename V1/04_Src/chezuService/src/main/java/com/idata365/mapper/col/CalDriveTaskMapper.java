package com.idata365.mapper.col;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.CalDriveTask;
import com.idata365.entity.DriveData;
import com.idata365.entity.DriveDataMain;

public interface CalDriveTaskMapper {

	
	void batchInsertCalTask(List<DriveDataMain> list);
	void lockCalScoreTask(CalDriveTask driveScore);
	
	List<CalDriveTask> getCalScoreTask(CalDriveTask driveScore);
	
	void updateSuccCalScoreTask(CalDriveTask driveScore);
	
	void updateFailCalScoreTask(CalDriveTask driveScore);
	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	

}
