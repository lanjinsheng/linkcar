package com.idata365.mapper.app;

import java.util.List;

import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.entity.UserTravelLottery;
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
	 
	 
	 List<TaskSystemScoreFlag> getUnFinishDayScoreList();
}
