package com.idata365.app.schedule;


import java.util.List;
import java.util.Map;

import com.idata365.app.entity.TaskGeneric;
import com.idata365.app.service.AssetService;
import com.idata365.app.service.SpringContextUtil;
import com.idata365.app.service.TaskGenericService;
import com.idata365.app.util.GsonUtils;

public class TaskGenericThread implements Runnable{
	String taskFlag="";
	public TaskGenericThread (String parmTaskFlag){
		this.taskFlag=parmTaskFlag;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		TaskGenericService taskGenericService=SpringContextUtil.getBean("taskGenericService", TaskGenericService.class);
		AssetService assetService=SpringContextUtil.getBean("assetService", AssetService.class);
		List<TaskGeneric> list=taskGenericService.getTaskByTaskFlag(taskFlag);
		for(TaskGeneric task:list) {
			try {
				switch(task.getTaskType()) {
					case UserDayPowerSnapshot:{
						Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
						assetService.userPowersSnapShot(String.valueOf(m.get("tableName")));
						assetService.powerClear();
						break;
					}
					case InitUserDayReward:{
						taskGenericService.initUserDayRewardTask(task);
						break;
					}
					case DoUserDayReward:{
						taskGenericService.doUserDayReward(task);
						break;
					}
					case InitFamilyDayReward:{
						taskGenericService.initFamilyDayReward(task);
						break;
					}
					case DoFamilyDayReward:{
						taskGenericService.doFamilyDayReward(task);
						break;
					}
					case DoUserFamilyDayReward:{
						taskGenericService.doUserFamilyDayReward(task);
						break;
					}
					case InitFamilySeasonReward:{
						taskGenericService.InitFamilySeasonReward(task);
						break;
					}
					case DoFamilySeasonReward:{
						taskGenericService.doFamilySeasonReward(task);
						break;
					}
					case DoUserFamilySeasonReward:{
						taskGenericService.doUserFamilySeasonReward(task);
						break;
					}
					
					default:
						break;
				}
		
			}catch(Exception e) {
				e.printStackTrace();
				taskGenericService.updateFailTask(task);
				continue;
			}
			taskGenericService.updateSuccTask(task);
		}
	}

}
