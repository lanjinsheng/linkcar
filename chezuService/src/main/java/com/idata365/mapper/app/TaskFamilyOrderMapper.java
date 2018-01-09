package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.TaskFamilyOrder;
import com.idata365.entity.TaskSystemScoreFlag;
/**
 * 
    * @ClassName: TaskFamilyDayScoreMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskFamilyOrderMapper {
 
	 int  initTaskFamilyOrder(TaskSystemScoreFlag task);

	 
		void lockFamilyOrderTask(TaskFamilyOrder taskFamilyOrder);
		
		List<TaskFamilyOrder> getFamilyOrderTask(TaskFamilyOrder taskFamilyOrder);
		
		void updateFamilyOrderSuccTask(TaskFamilyOrder taskFamilyOrder);
		
		void updateFamilyOrderFailTask(TaskFamilyOrder taskFamilyOrder);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
