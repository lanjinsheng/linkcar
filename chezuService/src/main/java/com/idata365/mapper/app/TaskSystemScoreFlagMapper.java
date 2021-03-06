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
	 
	 List<TaskSystemScoreFlag> getUnFinishFamilyDayOrderList();
	 int finishFamilyDayOrderTask(TaskSystemScoreFlag systemScoreFlag);
	 
	 List<TaskSystemScoreFlag> getUnFinishFamilyMonthOrderList();
	 int finishFamilyMonthOrderTask(TaskSystemScoreFlag systemScoreFlag);
	 
	 
	 
	 List<TaskSystemScoreFlag> getUnFinishFamilyMonthAvgOrderList();
	 int finishFamilyMonthAvgOrderTask(TaskSystemScoreFlag systemScoreFlag);
	 
	 //gameEnd===begin
	 List<TaskSystemScoreFlag> getUnInitGameEndList(); 
	 int  updateGameEndInit(TaskSystemScoreFlag systemScoreFlag);
	 
	 List<TaskSystemScoreFlag> getUnFinishGameEndList();
	 int finishGameEndTask(TaskSystemScoreFlag systemScoreFlag);
	 //gameEnd===end
	 
	 //俱乐部类型
	 List<TaskSystemScoreFlag> getUnFinishFamilyLevelDayEndList();
	 int  finishFamilyLevelDayEndTask(TaskSystemScoreFlag systemScoreFlag);
	 
	//清理昨日任务
	 List<TaskSystemScoreFlag> getUnFinishClearYesterday();
	 int  finishClearYesterdayTask(TaskSystemScoreFlag systemScoreFlag);
	 
	 
	 //用户按日违规统计
	 List<TaskSystemScoreFlag> getUnFinishUserBestDriveDayEndList();
	 int  finishUserBestDriveDayEndTask(TaskSystemScoreFlag systemScoreFlag);
	 
	 
	 //俱乐部pk匹配处理
	 List<TaskSystemScoreFlag> getUnInitPkRelationList(); 
	 int  updatePkRelationInit(TaskSystemScoreFlag systemScoreFlag);
	 
	 List<TaskSystemScoreFlag> getUnFinishPKRelationList();
	 int  finishPKRelation(TaskSystemScoreFlag systemScoreFlag);
	 
	 //用戶日得分初始化
	 List<TaskSystemScoreFlag> getUnInitUserDayScore(); 
	 int  updateUserDayScoreInit(TaskSystemScoreFlag systemScoreFlag);
	 
	 
	 //familyscore赛季继承初始化
	 List<TaskSystemScoreFlag> getUnInitFamilyScore(); 
	 int  updateFamilyScoreInit(TaskSystemScoreFlag systemScoreFlag);
}
