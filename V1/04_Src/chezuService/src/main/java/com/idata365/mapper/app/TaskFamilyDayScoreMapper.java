package com.idata365.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.FamilyDriveDayStat;
import com.idata365.entity.FamilyScore;
import com.idata365.entity.TaskFamilyDayScore;
/**
 * 
    * @ClassName: TaskFamilyDayScoreMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskFamilyDayScoreMapper {
 
	 int  insertTaskFamilyDayScore(TaskFamilyDayScore taskFamilyDayScore);
	 int  insertTaskFamilyDayScoreByTime(Map<String,Object> map);
	 int insertFamilyDriveDayStat(FamilyDriveDayStat familyDriveDayStat);
	 int  insertFamilyScore(FamilyScore task);
	 int initFamilyScore(FamilyScore task);
	 
		void lockFamilyDayScoreTask(TaskFamilyDayScore taskFamilyDayScore);
		
		List<TaskFamilyDayScore> getFamilyDayScoreTask(TaskFamilyDayScore taskFamilyDayScore);
		
		void updateFamilyDayScoreSuccTask(TaskFamilyDayScore taskFamilyDayScore);
		
		void updateFamilyDayScoreFailTask(TaskFamilyDayScore taskFamilyDayScore);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
