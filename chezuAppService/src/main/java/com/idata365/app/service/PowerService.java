package com.idata365.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.TaskPowerLogs;
import com.idata365.app.mapper.TaskPowerLogsMapper;

@Service
public class PowerService extends BaseService<PowerService>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(PowerService.class);
 
	@Autowired
	private TaskPowerLogsMapper taskPowerLogsMapper;
	/**
	 * 
	    * @Title: addPowerLogs
	    * @Description: TODO(增加power的任务日志)
	    * @param @param taskPowerLogs
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean addPowerLogs(TaskPowerLogs taskPowerLogs) {
		int hadAdd=taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);
		if(hadAdd>0) {
			return true;
		}
		return false;
	}
	/**
	 * 
	    * @Title: hadSignInToday
	    * @Description: TODO(判断是否有过当日签到)
	    * @param @param taskPowerLogs
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean hadSignInToday(TaskPowerLogs taskPowerLogs) {
		TaskPowerLogs log=taskPowerLogsMapper.getPowerLog(taskPowerLogs);
		if(log!=null) {
			return true;
		}
		return false;
	}
	
}
