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

import com.idata365.app.constant.AssetConstant;
import com.idata365.app.constant.DicFamilyTypeConstant;
import com.idata365.app.constant.FamilyConstant;
import com.idata365.app.entity.AssetFamiliesAsset;
import com.idata365.app.entity.AssetFamiliesDiamondsLogs;
import com.idata365.app.entity.AssetUsersDiamondsLogs;
import com.idata365.app.entity.FamilyGameAsset;
import com.idata365.app.entity.TaskGeneric;
import com.idata365.app.enums.TaskGenericEnum;
import com.idata365.app.mapper.AssetFamiliesAssetMapper;
import com.idata365.app.mapper.AssetFamiliesDiamondsLogsMapper;
import com.idata365.app.mapper.AssetUsersAssetMapper;
import com.idata365.app.mapper.AssetUsersDiamondsLogsMapper;
import com.idata365.app.mapper.FamilyGameAssetMapper;
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
	FamilyGameAssetMapper familyGameAssetMapper;
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
	    * @Title: initFamilyDayReward
	    * @Description: TODO(产生PK日钻石分配任务)
	    * @param @param task    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void initFamilyDayReward(TaskGeneric task) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("genericKey", task.getGenericKey());
		map.put("taskType", TaskGenericEnum.DoFamilyDayReward);
		map.put("priority", 10);
		Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
		long familyPersonTotal=taskGenericMapper.getByFamilyTotal(m.get("season").toString());
//		long limit=0;
		 
		map.putAll(m);
//		map.put("limit", limit);
		map.put("familyPersonTotal", familyPersonTotal);
		taskGenericMapper.initDoFamilyDayReward(map);
	}
	
	@Transactional
	public void InitFamilySeasonReward(TaskGeneric task) {
		//待处理
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("genericKey", task.getGenericKey());
//		map.put("taskType", TaskGenericEnum.DoFamilyDayReward);
//		map.put("priority", 10);
		Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
		m.put("familyTypeStart", DicFamilyTypeConstant.GuanJun_1);
		m.put("familyTypeEnd", DicFamilyTypeConstant.GuanJun_1000);
		long familyGuanjunTotal=taskGenericMapper.getFamilyTotalByST(m);
		
		m.put("familyTypeStart", DicFamilyTypeConstant.ZuanShi_4);
		m.put("familyTypeEnd", DicFamilyTypeConstant.ZuanShi_1);
		long familyZuanshiTotal=taskGenericMapper.getFamilyTotalByST(m);
//      钻石分配系数计算
//		冠军	5b/人
//		钻石	3b/人
//		b=1200/（冠军段位总人数*5+钻石段位总人数*3）
		BigDecimal diamondsNum=BigDecimal.valueOf(AssetConstant.DAY_DAIMONDS_FOR_SEASON).multiply(BigDecimal.valueOf(Integer.valueOf(m.get("gameDayNum").toString())));
		long fenmu=familyGuanjunTotal*5+familyZuanshiTotal*3;
		if(fenmu==0) {
			//无冠军段位，资金滚动进入下个赛季
//			assetFamiliesAsset 表中，系统账号为0;
			AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs=new AssetFamiliesDiamondsLogs();
			assetFamiliesDiamondsLogs.setDiamondsNum(diamondsNum);
			assetFamiliesDiamondsLogs.setEffectId(task.getId());
			assetFamiliesDiamondsLogs.setEventType(AssetConstant.EventType_Daimond_SeasonEnd);
			assetFamiliesDiamondsLogs.setRecordType(AssetConstant.RecordType_1);
			assetFamiliesDiamondsLogs.setFamilyId(0L);
			assetFamiliesDiamondsLogs.setRemark(m.get("season")+"无冠军段位,资金流入系统账号");
			assetFamiliesDiamondsLogsMapper.insertFamiliesDiamondsDay(assetFamiliesDiamondsLogs);
			assetFamiliesAssetMapper.updateDiamondsAdd(assetFamiliesDiamondsLogs);

		}else {
			//获取系统池中的资金
			AssetFamiliesAsset familyAsset=assetFamiliesAssetMapper.getFamiliesAssetByFamilyId(0L);
			AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs=new AssetFamiliesDiamondsLogs();
			assetFamiliesDiamondsLogs.setDiamondsNum(familyAsset.getDiamondsNum());
			assetFamiliesDiamondsLogs.setEffectId(task.getId());
			assetFamiliesDiamondsLogs.setEventType(AssetConstant.EventType_Daimond_SeasonEnd_Distr);
			assetFamiliesDiamondsLogs.setRecordType(AssetConstant.RecordType_2);
			assetFamiliesDiamondsLogs.setFamilyId(0L);
			assetFamiliesDiamondsLogs.setRemark(m.get("season")+"累积的资金池清空");
			assetFamiliesDiamondsLogsMapper.insertFamiliesDiamondsDay(assetFamiliesDiamondsLogs);
			assetFamiliesAssetMapper.updateDiamondsReduce(assetFamiliesDiamondsLogs);
			//任务跃迁
			diamondsNum=diamondsNum.add(familyAsset.getDiamondsNum());
			diamondsNum=diamondsNum.divide(BigDecimal.valueOf(fenmu),4,RoundingMode.HALF_DOWN);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("genericKey", task.getGenericKey());
			map.put("taskType", TaskGenericEnum.DoFamilySeasonReward);
			map.put("priority", 10);
			map.put("diamondsNum", diamondsNum);
			map.put("season", m.get("season"));
			map.put("familyTypeStart", DicFamilyTypeConstant.ZuanShi_4);
			map.put("familyTypeEnd", DicFamilyTypeConstant.GuanJun_1000);
			taskGenericMapper.initDoFamilySeasonReward(map);
		}
	}
		@Transactional
		public boolean doFamilySeasonReward(TaskGeneric task) {
			Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
			BigDecimal diamondsNum=BigDecimal.valueOf(Long.valueOf(m.get("diamondsNum").toString()));
			long familyId=Long.valueOf(m.get("familyId").toString());
			int memberNum=Integer.valueOf(m.get("memberNum").toString());
			//进行分配
			AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs=new AssetFamiliesDiamondsLogs();
			assetFamiliesDiamondsLogs.setDiamondsNum(diamondsNum.multiply(BigDecimal.valueOf(memberNum)));
			assetFamiliesDiamondsLogs.setEffectId(task.getId());
			assetFamiliesDiamondsLogs.setEventType(AssetConstant.EventType_Daimond_SeasonEnd);
			assetFamiliesDiamondsLogs.setRecordType(AssetConstant.RecordType_1);
			assetFamiliesDiamondsLogs.setFamilyId(familyId);
			assetFamiliesDiamondsLogs.setRemark(task.getGenericKey()+" 赛季分配");
			assetFamiliesDiamondsLogsMapper.insertFamiliesDiamondsDay(assetFamiliesDiamondsLogs);
			assetFamiliesAssetMapper.updateDiamondsAdd(assetFamiliesDiamondsLogs);
			
			//增加跃迁下一个任务(成员内部分配)
			TaskGeneric tg=new TaskGeneric();
			String preKey=task.getGenericKey().split("_")[0];
			String taskKey=preKey+"_"+TaskGenericEnum.DoUserFamilySeasonReward+"_"+familyId;
			tg.setGenericKey(taskKey);
			tg.setTaskType(TaskGenericEnum.DoUserFamilySeasonReward);
			tg.setPriority(10);
			tg.setJsonValue(task.getJsonValue());
			taskGenericMapper.insertTask(tg);
			return true;
	}
		@Transactional
		public boolean doUserFamilySeasonReward(TaskGeneric task) {
			Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
			BigDecimal diamondsNum=BigDecimal.valueOf(Long.valueOf(m.get("diamondsNum").toString()));
			long familyId=Long.valueOf(m.get("familyId").toString());
			int memberNum=Integer.valueOf(m.get("memberNum").toString());
			int familyType=Integer.valueOf(m.get("familyType").toString());
			String sign=SignUtils.encryptHMAC(String.valueOf(familyId));
			String daystamp=task.getGenericKey().split("_")[0];
		    //通过familyId获取用户ids。
			String users=chezuAccountService.getCurrentUsersByFamilyId(familyId, daystamp, sign);
			String []userArray=users.split(",");
			BigDecimal familyDiamonds=diamondsNum.multiply(BigDecimal.valueOf(memberNum));
			for(int i=0;i<userArray.length;i++) {
				AssetUsersDiamondsLogs  assetUsersDiamondsLogs=new AssetUsersDiamondsLogs();
				assetUsersDiamondsLogs.setUserId(Long.valueOf(userArray[i]));
				assetUsersDiamondsLogs.setRecordType(AssetConstant.RecordType_1);
				assetUsersDiamondsLogs.setEffectId(task.getId());
				assetUsersDiamondsLogs.setEventType(AssetConstant.EventType_Daimond_GameEnd_User);
				assetUsersDiamondsLogs.setRemark("赛季结束分配钻石");
				assetUsersDiamondsLogs.setDiamondsNum(diamondsNum);
				assetUsersDiamondsLogsMapper.insertUsersDiamondsDay(assetUsersDiamondsLogs);
				assetUsersAssetMapper.updateDiamondsAdd(assetUsersDiamondsLogs);
				//远程消息调用,发送的diamonds是家族获取的
				if(familyId!=FamilyConstant.ROBOT_FAMILY_ID) {
				chezuAppService.sendFamilyDiamondsSeasonMsg(daystamp, String.valueOf(familyId), familyType, assetUsersDiamondsLogs.getUserId(), String.valueOf(familyDiamonds.doubleValue()), sign);
				}
				
			}
			//family钻石减少
			AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs=new AssetFamiliesDiamondsLogs();
			assetFamiliesDiamondsLogs.setDiamondsNum(familyDiamonds);
			assetFamiliesDiamondsLogs.setEffectId(task.getId());
			assetFamiliesDiamondsLogs.setEventType(AssetConstant.EventType_Daimond_SeasonEnd_Distr);
			assetFamiliesDiamondsLogs.setRecordType(AssetConstant.RecordType_2);
			assetFamiliesDiamondsLogs.setFamilyId(familyId);
			assetFamiliesDiamondsLogs.setRemark(task.getGenericKey()+" 赛季分配给成员");
			assetFamiliesDiamondsLogsMapper.insertFamiliesDiamondsDay(assetFamiliesDiamondsLogs);
			assetFamiliesAssetMapper.updateDiamondsReduce(assetFamiliesDiamondsLogs);
			return true;
		}
		
		

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
		Long power=Long.valueOf(personPower.get("powerNum").toString());
		Long userId=Long.valueOf(personPower.get("userId").toString());
		if(power>0) {
		BigDecimal dayDiamond=BigDecimal.valueOf(power).multiply(BigDecimal.valueOf(AssetConstant.DAY_DAIMONDS_FOR_POWER)).divide(BigDecimal.valueOf(total), 5,RoundingMode.HALF_EVEN);
		 //进行分配
		AssetUsersDiamondsLogs assetUsersDiamondsLogs=new AssetUsersDiamondsLogs();
		assetUsersDiamondsLogs.setDiamondsNum(dayDiamond);
		assetUsersDiamondsLogs.setEffectId(task.getId());
		assetUsersDiamondsLogs.setEventType(AssetConstant.EventType_Daimond_DayPower_User);
		assetUsersDiamondsLogs.setRecordType(AssetConstant.RecordType_1);
		assetUsersDiamondsLogs.setUserId(userId);
		assetUsersDiamondsLogs.setRemark(task.getGenericKey()+" 每日分配");
		assetUsersDiamondsLogsMapper.insertUsersDiamondsDay(assetUsersDiamondsLogs);
		assetUsersAssetMapper.updateDiamondsAdd(assetUsersDiamondsLogs);
		}
		return true;
	}
	public final static String jsonValue1="{\"powerTableName\":\"userPower%s\",\"orderNo\":%s,\"familyId\":%d,\"diamonds\":%s,\"assetFamilyGameId\":%s}";
	
	/**
	 * 
	    * @Title: doFamilyDayReward
	    * @Description: TODO(家族钻石分配，并增加子任务 家族内部成员钻石分配)
	    * @param @param task
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean doFamilyDayReward(TaskGeneric task) {
		Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
		long total=Long.valueOf(m.get("familyPersonTotal").toString());
		long orderNo=Long.valueOf(m.get("orderNo").toString());
		long familyId=Long.valueOf(m.get("familyId").toString());
		long assetFamilyGameId=Long.valueOf(m.get("id").toString());
		BigDecimal gameDiamond=BigDecimal.valueOf(0);
		gameDiamond=BigDecimal.valueOf(AssetConstant.DAY_DAIMONDS_FOR_PK).divide(BigDecimal.valueOf(total), 4, RoundingMode.HALF_EVEN);
		//进行分配
		AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs=new AssetFamiliesDiamondsLogs();
		assetFamiliesDiamondsLogs.setDiamondsNum(gameDiamond);
		assetFamiliesDiamondsLogs.setEffectId(task.getId());
		assetFamiliesDiamondsLogs.setEventType(AssetConstant.EventType_Daimond_GameEnd);
		assetFamiliesDiamondsLogs.setRecordType(AssetConstant.RecordType_1);
		assetFamiliesDiamondsLogs.setFamilyId(familyId);
		assetFamiliesDiamondsLogs.setRemark(task.getGenericKey()+" 比赛分配");
		assetFamiliesDiamondsLogsMapper.insertFamiliesDiamondsDay(assetFamiliesDiamondsLogs);
		assetFamiliesAssetMapper.updateDiamondsAdd(assetFamiliesDiamondsLogs);
		//assetfamilyGame
		FamilyGameAsset familyGameAsset=new FamilyGameAsset();
		familyGameAsset.setDiamondsNum(gameDiamond);
		familyGameAsset.setId(assetFamilyGameId);
		familyGameAssetMapper.updateDiamonds(familyGameAsset);
		//增加跃迁下一个任务(成员内部分配)
		TaskGeneric tg=new TaskGeneric();
		String preKey=task.getGenericKey().split("_")[0];
		String taskKey=preKey+"_"+TaskGenericEnum.DoUserFamilyDayReward+"_"+familyId;
		tg.setGenericKey(taskKey);
		tg.setTaskType(TaskGenericEnum.DoUserFamilyDayReward);
		tg.setPriority(10);
		tg.setJsonValue(String.format(jsonValue1,preKey,orderNo,familyId,String.valueOf(gameDiamond.longValue()),assetFamilyGameId));
		taskGenericMapper.insertTask(tg);
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
	    * @Title: doUserFamilyDayReward
	    * @Description: TODO(家族成员间进行钻石分配)
	    * @param @param task
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean doUserFamilyDayReward(TaskGeneric task) {
		Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
		String powerTableName=m.get("powerTableName").toString();
		String diamonds= m.get("diamonds").toString();
		String orderNum= String.valueOf(m.get("orderNum"));
		String assetFamilyGameId=String.valueOf(m.get("assetFamilyGameId"));
		
		long familyId=Long.valueOf(m.get("familyId").toString());
		String daystamp=task.getGenericKey().split("_")[0];
		String sign=SignUtils.encryptHMAC(String.valueOf(familyId));
	    //通过familyId获取用户ids。
		String yyyy=daystamp.substring(0,4);
		String mm=daystamp.substring(4,6);
		String dd=daystamp.substring(6,8);
		daystamp=yyyy+"-"+mm+"-"+dd;
		String users=chezuAccountService.getUsersByFamilyId(familyId, daystamp, sign);
		String []userArray=users.split(",");
		long total=0L;
		
		List<Long> powerList=new ArrayList<Long>();
		List<AssetUsersDiamondsLogs> userList=new ArrayList<AssetUsersDiamondsLogs>();
		for(int i=0;i<userArray.length;i++) {
			AssetUsersDiamondsLogs  assetUsersDiamondsLogs=new AssetUsersDiamondsLogs();
			m.put("userId", userArray[i]);
			m.put("tableName", powerTableName);
			Map<String,Object> power=taskGenericMapper.getUserPowerByUserId(m);
			long hadPowerNum=Long.valueOf(power.get("hadPowerNum").toString());
			powerList.add(hadPowerNum);
			total+=hadPowerNum;
			assetUsersDiamondsLogs.setUserId(Long.valueOf(userArray[i]));
			assetUsersDiamondsLogs.setRecordType(AssetConstant.RecordType_1);
			assetUsersDiamondsLogs.setEffectId(Long.valueOf(assetFamilyGameId));
			assetUsersDiamondsLogs.setEventType(AssetConstant.EventType_Daimond_GameEnd_User);
			assetUsersDiamondsLogs.setRemark(task.getId()+"按"+powerTableName+"PK结束分配钻石");
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
			//远程消息调用,发送的diamonds是家族获取的
			if(familyId!=FamilyConstant.ROBOT_FAMILY_ID) {
			chezuAppService.sendFamilyDiamondsMsg(daystamp, String.valueOf(familyId), orderNum, assetUsersDiamondsLogs.getUserId(), String.valueOf(diamonds), sign);
			}
		}
		//family钻石减少
		AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs=new AssetFamiliesDiamondsLogs();
		assetFamiliesDiamondsLogs.setDiamondsNum(BigDecimal.valueOf(Double.valueOf(diamonds)));
		assetFamiliesDiamondsLogs.setEffectId(Long.valueOf(assetFamilyGameId));
		assetFamiliesDiamondsLogs.setEventType(AssetConstant.EventType_Daimond_Distr);
		assetFamiliesDiamondsLogs.setRecordType(AssetConstant.RecordType_2);
		assetFamiliesDiamondsLogs.setFamilyId(familyId);
		assetFamiliesDiamondsLogs.setRemark(task.getId()+"--"+task.getGenericKey()+" PK比赛分配给成员");
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
	
	public static void main(String []args) {
		String daystamp="20180506";
		String yyyy=daystamp.substring(0,4);
		String mm=daystamp.substring(4,6);
		String dd=daystamp.substring(6,8);
		System.out.println(yyyy+mm+dd);
	}
	
}
