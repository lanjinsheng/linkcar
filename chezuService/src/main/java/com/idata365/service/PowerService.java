package com.idata365.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.DriveScore;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.entity.TaskPowerLogs;
import com.idata365.entity.bean.AssetFamiliesPowerLogs;
import com.idata365.entity.bean.AssetUsersPowerLogs;
import com.idata365.enums.PowerEnum;
import com.idata365.mapper.app.TaskPowerLogsMapper;
import com.idata365.remote.ChezuAssetService;
import com.idata365.util.GsonUtils;
import com.idata365.util.SignUtils;


@Service
public class PowerService extends BaseService<PowerService>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(PowerService.class);
 
	@Autowired
	private TaskPowerLogsMapper taskPowerLogsMapper;
	@Autowired
	private ChezuAssetService chezuAssetService;
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
	public final static int EventType_SignIn=1;
	public final static int EventType_MemberTrip=1;
	public final static int EventType_UserTrip=4;
	public final static int RecordType_2=2;//减少
	public final static int RecordType_1=1;//增加
	public boolean calPower(TaskPowerLogs taskPowerLog) {
		String jsonValue=taskPowerLog.getJsonValue();
		String sign=SignUtils.encryptHMAC(jsonValue);
		Map<String,Object> map=GsonUtils.fromJson(jsonValue);
		boolean rt=false;
		 switch(taskPowerLog.getTaskType()) {
		 	case SignIn:
		 		AssetUsersPowerLogs power=new AssetUsersPowerLogs();
		 		power.setEffectId(taskPowerLog.getId());
		 		power.setEventType(EventType_SignIn);//签到
		 		power.setPowerNum(Long.valueOf(String.valueOf(map.get("power"))));
		 		power.setRecordType(RecordType_1);//增加1，减少2
		 		power.setUserId(taskPowerLog.getUserId());
		 		power.setRemark("签到获取power");
		 		rt=chezuAssetService.addPowerUsersTask(taskPowerLog.getJsonValue(), sign,power);
//		 		rt=chezuAssetService.addPowerUsersTask(taskPowerLog.getJsonValue(), sign);
//		 		rt=chezuAssetService.addPowerUsersTask2(power);
		 		break;
		 	case TripToFamily:
		 		AssetFamiliesPowerLogs familyPower=new AssetFamiliesPowerLogs();
		 		familyPower.setEffectId(Long.valueOf(String.valueOf(map.get("effectId"))));
		 		familyPower.setEventType(EventType_MemberTrip);//签到
		 		familyPower.setPowerNum(Long.valueOf(String.valueOf(map.get("toFamilyValue"))));
		 		familyPower.setRecordType(RecordType_1);//增加1，减少2
		 		familyPower.setFamilyId(Long.valueOf(String.valueOf(map.get("familyId"))));
		 		familyPower.setRelation(String.valueOf(map.get("relation")));
		 		familyPower.setRemark("行程贡献获取power");
		 		rt=chezuAssetService.addPowerFamilyTask(jsonValue, sign, familyPower);
//		 		rt=chezuAssetService.addPowerFamilyTask(jsonValue, sign);
//		 		rt=chezuAssetService.addPowerFamilyTask2(familyPower);
		 		break;
		 	case TripToUser:
		 		AssetUsersPowerLogs tripPower=new AssetUsersPowerLogs();
		 		tripPower.setEffectId(Long.valueOf(String.valueOf(map.get("effectId"))));
		 		tripPower.setEventType(EventType_UserTrip);//行程
		 		tripPower.setPowerNum(Long.valueOf(String.valueOf(map.get("toUserValue"))));
		 		tripPower.setRecordType(RecordType_1);//增加1，减少2
		 		tripPower.setUserId(taskPowerLog.getUserId());
		 		tripPower.setRemark("行程获取power");
		 		rt=chezuAssetService.addPowerUsersTask(taskPowerLog.getJsonValue(), sign,tripPower);
//		 		rt=chezuAssetService.addPowerUsersTask(taskPowerLog.getJsonValue(), sign);
//		 		rt=chezuAssetService.addPowerUsersTask2(tripPower);
		 		break;
		 	case Share:
		 		break;
		 	case Steal:
		 		//首页的互动记录发送
		 		rt=chezuAssetService.addPowerInteractTask(taskPowerLog.getJsonValue(), sign);
		 		break;
		 	default:
		 		return false;
		 		
		 }
		return rt;
	}
	
	@Transactional
	public List<TaskPowerLogs> getPowerLogsTask(TaskPowerLogs task){
		//先锁定任务
		taskPowerLogsMapper.lockPowerLogsTask(task);
		//返回任务列表
		return taskPowerLogsMapper.getPowerLogsTask(task);
	}
	@Transactional
	public	void updateSuccPowerLogsTask(TaskPowerLogs task) {
		taskPowerLogsMapper.updatePowerLogsSuccTask(task);
	}
//	
	@Transactional
	public void updateFailPowerLogsTask(TaskPowerLogs task) {
		if(task.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			task.setTaskStatus(2);
		}else {
			task.setTaskStatus(0);
		}
		taskPowerLogsMapper.updatePowerLogsFailTask(task);;
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskPowerLogsMapper.clearLockTask(compareTimes);
	}
	
}
