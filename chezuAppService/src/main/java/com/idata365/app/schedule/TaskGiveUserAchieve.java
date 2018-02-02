package com.idata365.app.schedule;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.idata365.app.entity.TaskGiveUserAchieveBean;
import com.idata365.app.entity.TaskKeyLog;
import com.idata365.app.service.TaskGiveUserAchieveService;
import com.idata365.app.service.TaskKeyLogService;

/**
 * 发放个人成就
 * @className:com.idata365.schedule.TaskGiveUserAchieve
 * @description:TODO
 * @date:2018年1月26日 下午4:39:56
 * @author:CaiFengYao
 */
public class TaskGiveUserAchieve extends TimerTask
{
	private static Logger log = Logger.getLogger(TaskGiveUserAchieve.class);
	private static Object lock = new Object();
	public static boolean pd = true;

	// 注入ThreadPoolTaskExecutor 到主线程中
	private ThreadPoolTaskExecutor threadPool;
	@Autowired
	TaskKeyLogService taskKeyLogService;

	@Autowired
	TaskGiveUserAchieveService taskGiveUserAchieveService;

	public void setThreadPool(ThreadPoolTaskExecutor threadPool)
	{
		// System.out.println(new Date().getTime());
		this.threadPool = threadPool;
	}

	// 在主线程中执行任务线程.....
	@Override
	public void run()
	{
		log.info("TaskGiveUserAchieve start--");
		try
		{
			if (!pd)
			{
				return;
			}
			synchronized (lock)
			{
				if (pd)
				{
					pd = false;

					long taskFlag = System.currentTimeMillis();
					TaskKeyLog key = new TaskKeyLog();
					key.setTaskFlag(String.valueOf(taskFlag));
					key.setTaskName("TaskGiveUserAchieve");
					int hadKey = taskKeyLogService.insertAppKey(key);
					if (hadKey == 0)
					{
						pd = true;
						return;
					}

					// 初始化用户成就
					threadPool.execute(new InitAchieveTask());

					TaskGiveUserAchieveBean taskGiveUserAchieveBean = new TaskGiveUserAchieveBean();
					taskGiveUserAchieveBean.setTaskFlag(String.valueOf(taskFlag));
					// 列表
					List<TaskGiveUserAchieveBean> list = taskGiveUserAchieveService
							.queryAchieveWaitList(taskGiveUserAchieveBean);
					log.info("TaskGiveUserAchieve do--list.size=" + list.size());
					for (TaskGiveUserAchieveBean achieveTask : list)
					{
						log.info("achieveTask>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>{}" + achieveTask.getId());
						try
						{
							// 业务部分
							boolean result = taskGiveUserAchieveService.giveAwardByAchieve(achieveTask
									.getAchieveRecordId());
							if (result)
							{
								taskGiveUserAchieveService.updateSuccUserAchieveTask(achieveTask);
							}
							else
							{
								taskGiveUserAchieveService.updateFailUserAchieveTask(achieveTask);
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
							log.error(e);
							taskGiveUserAchieveService.updateFailUserAchieveTask(achieveTask);
						}
					}
					pd = true;
				}

			}
		}
		catch (Exception e)
		{
			pd = true;
		}

		log.info("TaskGiveUserAchieve end--");
	}
}
