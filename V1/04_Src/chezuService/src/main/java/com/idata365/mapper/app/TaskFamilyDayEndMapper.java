package com.idata365.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.TaskFamilyDayEnd;
import com.idata365.entity.TaskSystemScoreFlag;
/**
 * 
    * @ClassName: TaskFamilyDayScoreMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月29日
    *
 */
public interface TaskFamilyDayEndMapper {
 
	 int  insertTaskFamilyDayEnd(TaskFamilyDayEnd task);//创建任务
	 int updateFamilyLevel(Map<String,Object> family); 
	 int countMaxOrderNo(TaskSystemScoreFlag flag);
	 
		void lockFamilyDayEndTask(TaskFamilyDayEnd task);
		
		List<TaskFamilyDayEnd> getFamilyDayEndTask(TaskFamilyDayEnd task);
		
		void updateFamilyDayEndSuccTask(TaskFamilyDayEnd task);
		
		void updateFamilyDayEndFailTask(TaskFamilyDayEnd task);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
