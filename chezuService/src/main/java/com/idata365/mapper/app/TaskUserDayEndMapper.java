package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.TaskUserDayEnd;
/**
 * 
    * @ClassName: TaskUserDayEndMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月29日
    *
 */
public interface TaskUserDayEndMapper {
 
	    int  insertTaskUserDayEnd(TaskUserDayEnd task);//创建任务
	    
		void lockUserDayEndTask(TaskUserDayEnd task);
		
		List<TaskUserDayEnd> getUserDayEndTask(TaskUserDayEnd task);
		
		void updateUserDayEndSuccTask(TaskUserDayEnd task);
		
		void updateUserDayEndFailTask(TaskUserDayEnd task);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
