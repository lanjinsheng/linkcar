package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.TaskUserLottery;

public interface TaskUserLotteryMapper {
	public int initTaskUserLottery(TaskUserLottery task);
	
	void lockUserLotteryTask(TaskUserLottery task);
	
	List<TaskUserLottery> getUserLotteryTask(TaskUserLottery task);
	
	void updateUserLotterySuccTask(TaskUserLottery task);
	
	void updateUserLotteryFailTask(TaskUserLottery task);
	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	
}
