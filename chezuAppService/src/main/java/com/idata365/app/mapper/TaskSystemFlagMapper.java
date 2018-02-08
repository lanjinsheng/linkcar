package com.idata365.app.mapper;


import java.util.List;

import com.idata365.app.entity.TaskSystemFlag;

/**
 * 
    * @ClassName: TaskSystemScoreFlagMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年2月8日
    *
 */
public interface TaskSystemFlagMapper {
 
	 int  initTaskSystemFlag(TaskSystemFlag taskSystemFlag);
	 int  finishInitLottery(TaskSystemFlag taskSystemFlag);
	 List<TaskSystemFlag>  getUnInitLotteryFlagList(TaskSystemFlag taskSystemFlag); 
	 
	 
}
