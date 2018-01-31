package com.idata365.app.schedule;

import com.idata365.app.service.SpringContextUtil;
import com.idata365.app.service.TaskGiveUserAchieveService;

public class InitAchieveTask implements Runnable
{

	public InitAchieveTask()
	{
		
	}

	@Override
	public void run()
	{
		// 初始化用户待解锁成就信息
		TaskGiveUserAchieveService taskGiveUserAchieveService=SpringContextUtil.getBean("taskGiveUserAchieveService", TaskGiveUserAchieveService.class);
		
		taskGiveUserAchieveService.initAchieveTask();
	}
}
