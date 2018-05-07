package com.idata365.col.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.col.entity.TaskDriveDataMain;

 
/**
 * 
    * @ClassName: TaskDriveDataMainMapper
    * @Description: TODO(測試任務類)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskDriveDataMainMapper {
 
	 
		void lockDriveDataMainTask(TaskDriveDataMain taskDriveDataMain);
		
		List<TaskDriveDataMain> getDriveDataMainTask(TaskDriveDataMain taskDriveDataMain);
		
		void updateDriveDataMainSuccTask(TaskDriveDataMain taskDriveDataMain);
		
		void updateDriveDataMainFailTask(TaskDriveDataMain taskDriveDataMain);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
