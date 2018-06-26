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
			boolean result=false;
			try {
				switch(task.getTaskType()) {
					case UserDayPowerSnapshot:{
						Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
						result=assetService.userPowersSnapShot(String.valueOf(m.get("tableName")));
						result=assetService.powerClear();
						break;
					}
					case InitUserDayReward:{
						result=taskGenericService.initUserDayRewardTask(task);
						break;
					}
					case DoUserDayReward:{
						result=taskGenericService.doUserDayRewardV1_6(task);
						break;
					}
					case InitFamilyDayReward:{
						result=taskGenericService.initFamilyDayReward(task);
						break;
					}
					case DoFamilyDayReward:{
						result=taskGenericService.doFamilyDayRewardV1_6(task);
						break;
					}
					case DoUserFamilyDayReward:{
						result=taskGenericService.doUserFamilyDayRewardV1_6(task);
						break;
					}
					case InitFamilySeasonReward:{
//						taskGenericService.InitFamilySeasonReward(task);
						break;
					}
					case DoFamilySeasonReward:{
//						taskGenericService.doFamilySeasonReward(task);
						break;
					}
					case DoUserFamilySeasonReward:{
//						taskGenericService.doUserFamilySeasonReward(task);
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
			if(result) {
				taskGenericService.updateSuccTask(task);
			}
		}
	}

}
