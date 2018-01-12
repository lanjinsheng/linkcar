package com.idata365.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	 
	public int insertColKey(TaskKeyLog task) {
		return taskKeyMapper.insertKey(task);
	}
	public int insertAppKey(TaskKeyLog task) {
		return taskAppKeyMapper.insertKey(task);
	}
}
