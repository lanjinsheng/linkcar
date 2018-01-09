package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.TaskFamilyPk;
import com.idata365.entity.TaskSystemScoreFlag;
/**
 * 
    * @ClassName: TaskFamilyDayScoreMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskFamilyPkMapper {
 
	 int  initTaskFamilyPk(TaskSystemScoreFlag task);

	 
		void lockFamilyPkTask(TaskFamilyPk taskFamilyPk);
		
		List<TaskFamilyPk> getFamilyPkTask(TaskFamilyPk taskFamilyPk);
		
		void updateFamilyPkSuccTask(TaskFamilyPk taskFamilyPk);
		
		void updateFamilyPkFailTask(TaskFamilyPk taskFamilyPk);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
