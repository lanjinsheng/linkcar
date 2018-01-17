package com.idata365.app.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.idata365.app.service.TaskService;

/**
 * 车位重置task
 * @author Administrator
 *
 */
public class StationTask
{
	private static final Logger LOG = LoggerFactory.getLogger(StationTask.class);

	@Autowired
	private TaskService taskService;

	public void run()
	{
		LOG.info("reset stations init");
		taskService.resetStations();
		LOG.info("reset stations end");
	}
}