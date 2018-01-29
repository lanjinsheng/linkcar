package com.idata365.app.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.TaskKeyLog;
import com.idata365.app.mapper.TaskAppKeyMapper;
@Service
public class TaskKeyLogService extends BaseService<TaskKeyLogService>{
	private final static Logger LOG = LoggerFactory.getLogger(TaskKeyLogService.class);
 
	@Autowired
	TaskAppKeyMapper  taskAppKeyMapper;
 
	public int insertAppKey(TaskKeyLog task) {
		return taskAppKeyMapper.insertKey(task);
	}
}
