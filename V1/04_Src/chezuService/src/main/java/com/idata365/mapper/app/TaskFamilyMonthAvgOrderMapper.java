package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.TaskFamilyMonthAvgOrder;
import com.idata365.entity.TaskFamilyMonthOrder;
/**
 * 
    * @ClassName: TaskFamilyMonthOrderAvgMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskFamilyMonthAvgOrderMapper {
 
	    int  delTaskFamilyMonthAvgOrder(TaskFamilyMonthAvgOrder taskFamilyOrder);
	    int   updateFamilyMonthAvgOrder(TaskFamilyMonthAvgOrder taskFamilyOrder);
	    
	    int  initTaskFamilyMonthAvgOrder(@Param("month") String month);
	    TaskFamilyMonthAvgOrder  getPreTaskRecord(TaskFamilyMonthAvgOrder taskFamilyOrder);
	 
		void lockFamilyMonthAvgOrderTask(TaskFamilyMonthAvgOrder taskFamilyOrder);
		
		List<TaskFamilyMonthAvgOrder> getFamilyMonthAvgOrderTask(TaskFamilyMonthAvgOrder taskFamilyOrder);
		
		void updateFamilyMonthAvgOrderSuccTask(TaskFamilyMonthAvgOrder taskFamilyOrder);
		
		void updateFamilyMonthAvgOrderFailTask(TaskFamilyMonthAvgOrder taskFamilyOrder);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
