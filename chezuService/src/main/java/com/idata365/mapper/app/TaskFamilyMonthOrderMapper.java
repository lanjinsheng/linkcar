package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.TaskFamilyDayOrder;
import com.idata365.entity.TaskFamilyMonthOrder;
import com.idata365.entity.TaskSystemScoreFlag;
/**
 * 
    * @ClassName: TaskFamilyDayScoreMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskFamilyMonthOrderMapper {
 
	    int  delTaskFamilyMonthOrder(@Param("month") String month);
	    int   updateFamilyMonthOrder(TaskFamilyMonthOrder taskFamilyOrder);
	    
	    int  initTaskFamilyMonthOrder(@Param("month") String month);
	    TaskFamilyMonthOrder  getPreTaskRecord(TaskFamilyMonthOrder taskFamilyOrder);
	 
		void lockFamilyMonthOrderTask(TaskFamilyMonthOrder taskFamilyOrder);
		
		List<TaskFamilyMonthOrder> getFamilyMonthOrderTask(TaskFamilyMonthOrder taskFamilyOrder);
		
		void updateFamilyMonthOrderSuccTask(TaskFamilyMonthOrder taskFamilyOrder);
		
		void updateFamilyMonthOrderFailTask(TaskFamilyMonthOrder taskFamilyOrder);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
