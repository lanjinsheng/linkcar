package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.FamilyScore;
import com.idata365.entity.TaskGameEnd;
import com.idata365.entity.TaskSystemScoreFlag;
/**
 * 
    * @ClassName: TaskFamilyDayScoreMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskGameEndMapper {
 
	    
	    int  initGameEnd(TaskSystemScoreFlag taskSystemScoreFlag);
	    int updateFamilyInfo(FamilyScore familyScore);
		void lockGameEndTask(TaskGameEnd taskGameEnd);
		
		FamilyScore getFamilyScore(TaskGameEnd taskGameEnd);
		
		List<TaskGameEnd> getGameEndTask(TaskGameEnd taskGameEnd);
		
		void updateGameEndSuccTask(TaskGameEnd taskGameEnd);
		
		void updateGameEndFailTask(TaskGameEnd taskGameEnd);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
		int updateFamilyScore(TaskGameEnd taskGameEnd);
}
