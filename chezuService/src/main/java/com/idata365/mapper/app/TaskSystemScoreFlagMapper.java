package com.idata365.mapper.app;

import java.util.List;

import com.idata365.entity.TaskSystemScoreFlag;
/**
 * 
    * @ClassName: TaskSystemScoreFlagMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskSystemScoreFlagMapper {
 
	 int  insertSystemScoreFlag(TaskSystemScoreFlag systemScoreFlag);
	 TaskSystemScoreFlag getSystemScoreFlag(TaskSystemScoreFlag systemScoreFlag);
	 List<TaskSystemScoreFlag> getSystemScoreFlagList(TaskSystemScoreFlag systemScoreFlag);
	 List<TaskSystemScoreFlag> getUnInitSystemScoreFlagList();
	 int  updateSystemScoreFlag(TaskSystemScoreFlag systemScoreFlag);
	 int finishUserDayScoreTask(TaskSystemScoreFlag systemScoreFlag);
	 
	 List<TaskSystemScoreFlag> getUnFinishDayScoreList();
	 
	 List<TaskSystemScoreFlag> getUnFinishFamilyScoreList();
	 int finishFamilyDayScoreTask(TaskSystemScoreFlag systemScoreFlag);
	 
	 
	 List<TaskSystemScoreFlag> getUnInitPkFlagList(); 
	 int  updatePkInit(TaskSystemScoreFlag systemScoreFlag);
	 
	 List<TaskSystemScoreFlag> getUnInitOrderFlagList(); 
	 int  updateOrderInit(TaskSystemScoreFlag systemScoreFlag);
	 
	 List<TaskSystemScoreFlag> getUnFinishFamilyPkList();
	 int finishFamilyPkTask(TaskSystemScoreFlag systemScoreFlag);
	 
	 List<TaskSystemScoreFlag> getUnFinishFamilyOrderList();
	 int finishFamilyOrderTask(TaskSystemScoreFlag systemScoreFlag);
	 
	
	 
}
