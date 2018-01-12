package com.idata365.mapper.col;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.FamilyDriveDayStat;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.entity.TaskTest;
import com.idata365.entity.TaskTestData;
/**
 * 
    * @ClassName: TaskFamilyDayScoreMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskTestMapper {
 
	 
	 int  insertData(TaskTestData taskData);
	 
		void lockTaskTestTask(TaskTest taskTest);
		
		List<TaskTest> getTaskTestTask(TaskTest taskTest);
		
		void updateTaskTestSuccTask(TaskTest taskTest);
		
		void updateTaskTestFailTask(TaskTest taskTest);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
