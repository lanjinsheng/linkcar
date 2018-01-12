package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.TaskFamilyDayOrder;
import com.idata365.entity.TaskSystemScoreFlag;
/**
 * 
    * @ClassName: TaskFamilyDayScoreMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskFamilyDayOrderMapper {
 
	 int  initTaskFamilyDayOrder(TaskSystemScoreFlag task);
	 int   updateFamilyDayOrder(TaskFamilyDayOrder taskFamilyOrder);
	int  delTaskFamilyDayOrder(@Param("daystamp") String daystamp);
	 
	 
	  TaskFamilyDayOrder  getPreTaskRecord(TaskFamilyDayOrder taskFamilyOrder);
		void lockFamilyDayOrderTask(TaskFamilyDayOrder taskFamilyOrder);
		
		List<TaskFamilyDayOrder> getFamilyDayOrderTask(TaskFamilyDayOrder taskFamilyOrder);
		
		void updateFamilyDayOrderSuccTask(TaskFamilyDayOrder taskFamilyOrder);
		
		void updateFamilyDayOrderFailTask(TaskFamilyDayOrder taskFamilyOrder);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
