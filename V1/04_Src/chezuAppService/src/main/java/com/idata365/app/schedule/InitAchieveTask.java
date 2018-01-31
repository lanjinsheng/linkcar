package com.idata365.app.schedule;

import com.idata365.app.service.TaskGiveUserAchieveService;

public class InitAchieveTask implements Runnable
{
	private TaskGiveUserAchieveService taskGiveUserAchieveService;

	public InitAchieveTask(TaskGiveUserAchieveService taskGiveUserAchieveService)
	{
		this.taskGiveUserAchieveService = taskGiveUserAchieveService;
	}

	@Override
	public void run()
	{
		// 初始化用户待解锁成就信息
		taskGiveUserAchieveService.initAchieveTask();
	}
}
