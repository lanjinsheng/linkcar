package com.idata365.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.TaskKeyLog;
import com.idata365.entity.TaskTest;
import com.idata365.entity.TaskTestData;
import com.idata365.mapper.col.TaskColKeyMapper;
import com.idata365.mapper.col.TaskTestMapper;
@Service
public class TaskTestService extends BaseService<TaskTestService>{
	private final static Logger LOG = LoggerFactory.getLogger(TaskTestService.class);
	@Autowired
	TaskTestMapper taskTestMapper;
	
	public boolean insertTask(TaskTest task) {
		TaskTestData data=new TaskTestData();
		data.setSomeId(task.getId());
		taskTestMapper.insertData(data);
		
		return true;
	}


	public List<TaskTest> getTaskTestTask(TaskTest taskFamilyPk){
		//先锁定任务
		taskTestMapper.lockTaskTestTask(taskFamilyPk);
		//返回任务列表
		return taskTestMapper.getTaskTestTask(taskFamilyPk);
	}
	
	public	void updateSuccTaskTestTask(TaskTest taskFamilyPk) {
		taskTestMapper.updateTaskTestSuccTask(taskFamilyPk);
	}
//	
	public void updateTaskTestFailTask(TaskTest taskFamilyPk) {
		taskTestMapper.updateTaskTestFailTask(taskFamilyPk);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskTestMapper.clearLockTask(compareTimes);
	}
	
}
