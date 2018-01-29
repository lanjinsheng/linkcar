package com.idata365.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.TaskAchieveAddValue;
import com.idata365.enums.AchieveEnum;
import com.idata365.mapper.app.TaskAchieveAddValueMapper;

 

@Service
public class TaskAchieveAddValueService  extends BaseService<TaskAchieveAddValueService>{

	@Autowired
	TaskAchieveAddValueMapper taskAchieveAddValueMapper;
	/**
	 * 
	    * @Title: addAchieve
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param keyId
	    * @param @param value 数量，默认输入0
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public boolean addAchieve(long keyId,Double value,AchieveEnum type) {
		TaskAchieveAddValue taskAchieveAddValue=new TaskAchieveAddValue();
		taskAchieveAddValue.setAchieveType(type);
		taskAchieveAddValue.setKeyId(keyId);
		taskAchieveAddValue.setAddValue(value);
		taskAchieveAddValueMapper.insertTaskAchieveAddValue(taskAchieveAddValue);
		return true;
	}
}
