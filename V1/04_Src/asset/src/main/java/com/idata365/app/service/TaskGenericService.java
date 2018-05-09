package com.idata365.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.FamilyConstant;
import com.idata365.app.entity.AssetFamiliesDiamondsLogs;
import com.idata365.app.entity.AssetUsersDiamondsLogs;
import com.idata365.app.entity.TaskGeneric;
import com.idata365.app.enums.TaskGenericEnum;
import com.idata365.app.mapper.AssetFamiliesAssetMapper;
import com.idata365.app.mapper.AssetFamiliesDiamondsLogsMapper;
import com.idata365.app.mapper.AssetUsersAssetMapper;
import com.idata365.app.mapper.AssetUsersDiamondsLogsMapper;
import com.idata365.app.mapper.TaskGenericMapper;
import com.idata365.app.remote.ChezuAccountService;
import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.util.GsonUtils;
import com.idata365.app.util.SignUtils;

@Service
public class TaskGenericService {
	@Autowired
	AssetUsersAssetMapper assetUsersAssetMapper;
	@Autowired
	AssetUsersDiamondsLogsMapper assetUsersDiamondsLogsMapper;
	@Autowired
	AssetFamiliesDiamondsLogsMapper  assetFamiliesDiamondsLogsMapper;
	@Autowired
   TaskGenericMapper taskGenericMapper;
	@Autowired
	AssetFamiliesAssetMapper assetFamiliesAssetMapper;
	@Autowired
	ChezuAccountService chezuAccountService;
	@Autowired
	ChezuAppService chezuAppService;
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	/**
	 * 
	    * @Title: initUserDayRewardTask
	    * @Description: TODO(按power分配钻石资产任务产生)
	    * @param @param task    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void initUserDayRewardTask(TaskGeneric task) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("genericKey", task.getGenericKey());
		map.put("taskType", TaskGenericEnum.DoUserDayReward);
		map.put("priority", 10);
		Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
		map.putAll(m);
		//获取power总量
		long powerTotal=taskGenericMapper.getPowerDayTotal(map);
		map.put("powerTotal", powerTotal);
		taskGenericMapper.initUserDayRewardTask(map);
	}
	/**
	 * 
	    * @Title: initFamilySeasonReward
	    * @Description: TODO(产生赛季日钻石分配任务)
	    * @param @param task    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void initFamilySeasonReward(TaskGeneric task) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("genericKey", task.getGenericKey());
		map.put("taskType", TaskGenericEnum.DoFamilySeasonReward);
		map.put("priority", 10);
		Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
		long familyTotal=taskGenericMapper.getByFamilyTotal(m.get("season").toString());
		long limit=0;
		if(familyTotal<=60) {
			limit=12;
		}else {
			limit=BigDecimal.valueOf(familyTotal).multiply(BigDecimal.valueOf(0.3)).longValue();
		}
		map.putAll(m);
		map.put("limit", limit);
		map.put("familyTotal", familyTotal);
		taskGenericMapper.initFamilySeasonReward(map);
	}
	
	
	public final int PowerDiamonds=500;
	public final int PowerDiamondsFamily=500;
	/**
	 * 
	    * @Title: doUserDayReward
	    * @Description: TODO(用户每日个人power钻石分配)
	    * @param @param task
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean doUserDayReward(TaskGeneric task) {
		Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
		long total=Long.valueOf(m.get("powerTotal").toString());
		Map<String,Object> personPower=taskGenericMapper.getPersonPower(m);
		Long power=Long.valueOf(personPower.get("hadPowerNum").toString());
		Long userId=Long.valueOf(personPower.get("userId").toString());
		if(power>0) {
		BigDecimal dayDiamond=BigDecimal.valueOf(power).multiply(BigDecimal.valueOf(PowerDiamonds)).divide(BigDecimal.valueOf(total), 5,RoundingMode.HALF_EVEN);
		 //进行分配
		AssetUsersDiamondsLogs assetUsersDiamondsLogs=new AssetUsersDiamondsLogs();
		assetUsersDiamondsLogs.setDiamondsNum(dayDiamond);
		assetUsersDiamondsLogs.setEffectId(task.getId());
		assetUsersDiamondsLogs.setEventType(AssetService.EventType_Daimond_DayPower_User);
		assetUsersDiamondsLogs.setRecordType(AssetService.RecordType_1);
		assetUsersDiamondsLogs.setUserId(userId);
		assetUsersDiamondsLogs.setRemark(task.getGenericKey()+" 每日分配");
		assetUsersDiamondsLogsMapper.insertUsersDiamondsDay(assetUsersDiamondsLogs);
		assetUsersAssetMapper.updateDiamondsAdd(assetUsersDiamondsLogs);
		}
		return true;
	}
	public final static String jsonValue1="{\"powerTableName\":\"userPower%s\",\"orderNo\":%s,\"familyId\":%d,\"diamonds\":%s}";
	/**
	 * 
	    * @Title: doFamilySeasonReward
	    * @Description: TODO(家族钻石分配，并增加子任务 家族内部成员钻石分配)
	    * @param @param task
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean doFamilySeasonReward(TaskGeneric task) {
		Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
		long total=Long.valueOf(m.get("familyTotal").toString());
		long orderNo=Long.valueOf(m.get("orderNo").toString());
		long familyId=Long.valueOf(m.get("familyId").toString());
		BigDecimal gameDiamond=BigDecimal.valueOf(0);
		if(total<=60) {
			  gameDiamond=BigDecimal.valueOf(PowerDiamondsFamily).divide(BigDecimal.valueOf(total), 5, RoundingMode.HALF_EVEN);
		}else {
			long rewardNum=BigDecimal.valueOf(total).multiply(BigDecimal.valueOf(0.2)).longValue();
			rewardNum=rewardNum-3;
			long midNum=BigDecimal.valueOf(rewardNum).multiply(BigDecimal.valueOf(0.3)).longValue();
			long leftNum=rewardNum-midNum;
			if(orderNo==1) {
				gameDiamond=BigDecimal.valueOf(150);
			}else if(orderNo==2) {
				gameDiamond=BigDecimal.valueOf(100);
			}else if(orderNo==3) {
				gameDiamond=BigDecimal.valueOf(50);
			}else {
				if(orderNo<=midNum) {
					gameDiamond=BigDecimal.valueOf(100).divide(BigDecimal.valueOf(midNum),5, RoundingMode.HALF_EVEN);
				}else {
					gameDiamond=BigDecimal.valueOf(100).divide(BigDecimal.valueOf(leftNum),5, RoundingMode.HALF_EVEN);
				}
			}
		}
		//进行分配
		AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs=new AssetFamiliesDiamondsLogs();
		assetFamiliesDiamondsLogs.setDiamondsNum(gameDiamond);
		assetFamiliesDiamondsLogs.setEffectId(task.getId());
		assetFamiliesDiamondsLogs.setEventType(AssetService.EventType_Daimond_GameEnd);
		assetFamiliesDiamondsLogs.setRecordType(AssetService.RecordType_1);
		assetFamiliesDiamondsLogs.setFamilyId(familyId);
		assetFamiliesDiamondsLogs.setRemark(task.getGenericKey()+" 比赛分配");
		assetFamiliesDiamondsLogsMapper.insertFamiliesDiamondsDay(assetFamiliesDiamondsLogs);
		assetFamiliesAssetMapper.updateDiamondsAdd(assetFamiliesDiamondsLogs);
		
		//增加跃迁下一个任务(成员内部分配)
		TaskGeneric tg=new TaskGeneric();
		String preKey=task.getGenericKey().split("_")[0];
		String taskKey=preKey+"_"+TaskGenericEnum.DoUserSeasonReward;
		tg.setGenericKey(taskKey);
		tg.setTaskType(TaskGenericEnum.DoUserSeasonReward);
		tg.setPriority(10);
		tg.setJsonValue(String.format(jsonValue1,preKey,orderNo,familyId,String.valueOf(gameDiamond.longValue())));
		taskGenericMapper.insertTask(task);
		return true;
	}
	/**
	 * 
	    * @Title: doUserSeasonReward
	    * @Description: TODO(分配赛季家族钻石)
	    * @param @param task
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	/**
	 * 
	    * @Title: doUserSeasonReward
	    * @Description: TODO(家族成员间进行钻石分配)
	    * @param @param task
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean doUserSeasonReward(TaskGeneric task) {
		Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
		String powerTableName=m.get("powerTableName").toString();
		String diamonds= m.get("diamonds").toString();
		String orderNum= String.valueOf(m.get("diamonds"));
		long familyId=Long.valueOf(m.get("familyId").toString());
		String daystamp=task.getGenericKey().split("_")[0];
		String sign=SignUtils.encryptHMAC(String.valueOf(familyId));
	    //通过familyId获取用户ids。
		String users=chezuAccountService.getUsersByFamilyId(familyId, daystamp, sign);
		String []userArray=users.split(",");
		long total=0L;
		
		List<Long> powerList=new ArrayList<Long>();
		List<AssetUsersDiamondsLogs> userList=new ArrayList<AssetUsersDiamondsLogs>();
		for(int i=0;i<userArray.length;i++) {
			AssetUsersDiamondsLogs  assetUsersDiamondsLogs=new AssetUsersDiamondsLogs();
			m.put("userId", userArray[0]);
			m.put("tableName", powerTableName);
			Map<String,Object> power=taskGenericMapper.getUserPowerByUserId(m);
			long hadPowerNum=Long.valueOf(power.get("hadPowerNum").toString());
			powerList.add(hadPowerNum);
			total+=hadPowerNum;
			assetUsersDiamondsLogs.setUserId(Long.valueOf(userArray[0]));
			assetUsersDiamondsLogs.setRecordType(AssetService.RecordType_1);
			assetUsersDiamondsLogs.setEventType(AssetService.EventType_Daimond_GameEnd_User);
			assetUsersDiamondsLogs.setRemark(powerTableName+"赛季结束分配钻石");
			userList.add(assetUsersDiamondsLogs);
		}
		//通过用户ids获取用户的能量值。
		int j=0;
		for(AssetUsersDiamondsLogs assetUsersDiamondsLogs:userList) {
			long pow=powerList.get(j);
			BigDecimal d=BigDecimal.valueOf(pow).multiply(BigDecimal.valueOf(Double.valueOf(diamonds)))
					.divide(BigDecimal.valueOf(total),5,RoundingMode.HALF_DOWN);
			assetUsersDiamondsLogs.setDiamondsNum(d);
			j++;
			assetUsersDiamondsLogsMapper.insertUsersDiamondsDay(assetUsersDiamondsLogs);
			assetUsersAssetMapper.updateDiamondsAdd(assetUsersDiamondsLogs);
			//远程消息调用
			if(familyId!=FamilyConstant.ROBOT_FAMILY_ID) {
			chezuAppService.sendFamilyDiamondsMsg(daystamp, String.valueOf(familyId), orderNum, assetUsersDiamondsLogs.getUserId(), String.valueOf(assetUsersDiamondsLogs.getDiamondsNum().doubleValue()), sign);
			}
		}
		//family减少
		AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs=new AssetFamiliesDiamondsLogs();
		assetFamiliesDiamondsLogs.setDiamondsNum(BigDecimal.valueOf(Double.valueOf(diamonds)));
		assetFamiliesDiamondsLogs.setEffectId(task.getId());
		assetFamiliesDiamondsLogs.setEventType(AssetService.EventType_Daimond_Distr);
		assetFamiliesDiamondsLogs.setRecordType(AssetService.RecordType_2);
		assetFamiliesDiamondsLogs.setFamilyId(familyId);
		assetFamiliesDiamondsLogs.setRemark(task.getGenericKey()+" 比赛分配给成员");
		assetFamiliesDiamondsLogsMapper.insertFamiliesDiamondsDay(assetFamiliesDiamondsLogs);
		assetFamiliesAssetMapper.updateDiamondsReduce(assetFamiliesDiamondsLogs);
		return true;
	}
	
	
	@Transactional
	public List<TaskGeneric> getLockAndGetTask(TaskGeneric task){
		task.setTaskDealTime(System.currentTimeMillis());
		//先锁定任务
		taskGenericMapper.lockTask(task);;
		//返回任务列表
		return taskGenericMapper.getTask(task);
	}
	@Transactional
	public void lockTask(String taskFlag){
		TaskGeneric task=new TaskGeneric();
		task.setTaskFlag(taskFlag);
		task.setTaskDealTime(System.currentTimeMillis());
		//先锁定任务
		taskGenericMapper.lockTask(task);
	}
	
	@Transactional
	public List<TaskGeneric> getTaskByTaskFlag(String taskFlag){
		TaskGeneric task=new TaskGeneric();
		task.setTaskFlag(taskFlag);
		//返回任务列表
		return taskGenericMapper.getTask(task);
	}
	
	@Transactional
	public	void updateSuccTask(TaskGeneric task) {
		taskGenericMapper.updateSuccTask(task);
	}
	@Transactional
	public void updateFailTask(TaskGeneric task) {
		if(task.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			task.setTaskStatus(2);
		}else {
			task.setTaskStatus(0);
		}
		taskGenericMapper.updateFailTask(task);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskGenericMapper.clearLockTask(compareTimes);
	}
	
}
