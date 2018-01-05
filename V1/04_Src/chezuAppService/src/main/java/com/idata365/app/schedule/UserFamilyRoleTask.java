package com.idata365.app.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.idata365.app.service.GameService;

/**
 * 初始化明天的用户角色
 * @author Administrator
 *
 */
public class UserFamilyRoleTask
{
	private static final Logger LOG = LoggerFactory.getLogger(UserFamilyRoleTask.class);

	@Autowired
	private GameService gameService;

	public void run()
	{
		LOG.info("begin init tomorrow log");
		gameService.syncTomorrowRole();
		LOG.info("end init tomorrow log");
	}
}