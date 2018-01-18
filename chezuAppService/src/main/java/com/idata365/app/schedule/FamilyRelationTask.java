package com.idata365.app.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.idata365.app.service.TaskService;

/**
 * 初始化第二天的家族PK关系
 * @author Administrator
 *
 */
public class FamilyRelationTask
{
	private static final Logger LOG = LoggerFactory.getLogger(FamilyRelationTask.class);

	@Autowired
	private TaskService taskService;

	public void run()
	{
		LOG.info("FamilyRelationTask===========begin init tomorrow log");
		taskService.initTodayFamilyRelation();
		LOG.info("FamilyRelationTask===========end init tomorrow log");
	}
}