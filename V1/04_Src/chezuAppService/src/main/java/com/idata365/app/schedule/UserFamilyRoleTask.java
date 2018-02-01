package com.idata365.app.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.idata365.app.service.TaskService;

/**
 * 判断用户所在家族的PK关系，并且初始化角色
 * @author Administrator
 *
 */
public class UserFamilyRoleTask
{
	private static final Logger LOG = LoggerFactory.getLogger(UserFamilyRoleTask.class);

	@Autowired
	private TaskService taskService;

	public void run()
	{
		LOG.info("begin init tomorrow log");
		taskService.syncTomorrowRole();
		LOG.info("end init tomorrow log");
	}
}