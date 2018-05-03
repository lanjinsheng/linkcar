package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.FamilyDriveDayStat;
import com.idata365.entity.FamilyRelation;
import com.idata365.entity.FamilyScore;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.entity.TaskFamilyPkRelation;
import com.idata365.entity.TaskSystemScoreFlag;
/**
 * 
    * @ClassName: TaskFamilyPkRelationMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年4月8日
    *
 */
public interface TaskFamilyPkRelationMapper {
 
	    void initTaskFamilyPkRelation(TaskSystemScoreFlag taskSystemScoreFlag );
	    int batchInsertRelation(List<FamilyRelation> list);
	    void updateFamilyInfoMatchKey();
		void lockFamilyPkRelationTask(TaskFamilyPkRelation taskFamilyPkRelation);
		
		List<TaskFamilyPkRelation> getFamilyPkRelationTask(TaskFamilyPkRelation taskFamilyPkRelation);
		
		void updateFamilyPkRelationSuccTask(TaskFamilyPkRelation taskFamilyPkRelation);
		
		void updateFamilyPkRelationFailTask(TaskFamilyPkRelation taskFamilyPkRelation);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		
}
