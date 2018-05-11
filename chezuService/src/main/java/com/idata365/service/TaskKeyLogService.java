package com.idata365.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.TaskKeyLog;
import com.idata365.mapper.app.TaskAppKeyMapper;
import com.idata365.mapper.col.TaskColKeyMapper;
@Service
public class TaskKeyLogService extends BaseService<TaskKeyLogService>{
	private final static Logger LOG = LoggerFactory.getLogger(TaskKeyLogService.class);
	@Autowired
	TaskColKeyMapper taskKeyMapper;
	@Autowired
	TaskAppKeyMapper  taskAppKeyMapper;
	@Transactional(value="colTransactionManager") 
	public int insertColKey(TaskKeyLog task) {
		return taskKeyMapper.insertKey(task);
	}
	@Transactional
	public int insertAppKey(TaskKeyLog task) {
		return taskAppKeyMapper.insertKey(task);
	}
}
