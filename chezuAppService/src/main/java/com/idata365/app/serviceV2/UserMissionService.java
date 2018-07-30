package com.idata365.app.serviceV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.DicCar;
import com.idata365.app.entity.DicUserMission;
import com.idata365.app.entity.UserCar;
import com.idata365.app.entity.UserMissionLogs;
import com.idata365.app.mapper.DicCarMapper;
import com.idata365.app.mapper.DicUserMissionMapper;
import com.idata365.app.mapper.UserCarMapper;
import com.idata365.app.mapper.UserMissionLogsMapper;
import com.idata365.app.remote.ChezuAccountService;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.BaseService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.TripService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.SignUtils;

@Service
@Transactional
public class UserMissionService extends BaseService<UserMissionService> {
	protected static final Logger LOG = LoggerFactory.getLogger(UserMissionService.class);
	
	@Autowired
	private DicUserMissionMapper dicUserMissionMapper;
	@Autowired
	private UserMissionLogsMapper userMissionLogsMapper;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	private TripService tripService;
	@Autowired
	private ChezuAccountService chezuAccountService;
	@Autowired
	private FamilyService familyService;
	@Autowired
	private UserInfoServiceV2 userInfoServiceV2;
	@Autowired
	private InteractService interactService;
	@Autowired
	UserCarMapper userCarMapper;
	@Autowired
	DicCarMapper dicCarMapper;

	// 从字典表查询所有任务
	public List<DicUserMission> getAllDicUserMission() {
		return dicUserMissionMapper.getAllDicUserMission();
	}

	// 根据userId查询任务
	public List<UserMissionLogs> getLogsByUserId(long userId) {
		return userMissionLogsMapper.getLogsByUserId(userId);
	}

	//初始化用户任务logs
	public int initLogsToUser(List<DicUserMission> missions, long userId) {
		return userMissionLogsMapper.initLogsToUser(missions,userId);
	}
	
	//
	public void insertOrUpdateLogs(long userId, int missionType) {
		List<Map<String, Object>> logs = userMissionLogsMapper.getLogsByUserIdAndType(userId, missionType);
		for (Map<String, Object> map : logs) {
			int missionId = Integer.valueOf(map.get("missionId").toString());
			int status = Integer.valueOf(map.get("status").toString());
			int targetCount = Integer.valueOf(map.get("targetCount").toString());
			if ((status == 2) || (missionId == 5)) {
				//重新查询
				int count = 0;
				String sign = SignUtils.encryptHMAC(String.valueOf(userId));
				
				switch (missionId) {
				case 1:
					// 主页偷取小车动力5次
					count = chezuAssetService.queryCountOfSteal(userId, sign);
					break;
				case 2:
					// 在其他玩家车库点赞3次
					count = interactService.userLikeMissionCount(userId);
					break;
				case 3:
					// 今日出行1次
					count = tripService.getTodayCountTrip(userId);
					break;
				case 4:
					// 分享链车给好友
					count = userInfoServiceV2.queryUserShareCountToday(userId);
					break;
				case 5:
					// 连续登录（每天增加5点，上限150点）
					String daystamp = String.valueOf(map.get("daystamp"));
					String format = "yyyy-MM-dd";
					String recordNext = DateTools.getAddMinuteDateTime(daystamp,60*24,format);
					String now = DateTools.getCurDate(format);
					if(daystamp.equals(now)) {
						break;
					}
					if(recordNext.equals(now)) {
						//+finishCount
						userMissionLogsMapper.updateLogsFinishCount(userId, missionId);
					}else {
						//重置记录
						userMissionLogsMapper.updateLogsValid(userId, missionId);
						UserMissionLogs userMissionLogs = new UserMissionLogs();
						userMissionLogs.setUserId(userId);
						userMissionLogs.setMissionId(5);
						userMissionLogs.setFinishCount(1);
						userMissionLogs.setTargetCount(9999);
						userMissionLogs.setStatus(1);
						userMissionLogsMapper.insertOneLogs(userMissionLogs);
					}
					break;
				case 6:
					// 完成实名认证
					count = chezuAccountService.queryCountOfIdcard(userId, sign);
					break;
				case 7:
					// 完成行驶证认证
					count = chezuAccountService.queryCountOfLicense(userId, sign);
					break;
				case 8:
					// 加入一个俱乐部
					count = familyService.queryCountJoinFamily(userId);
					break;
				case 9:
					// 创建一个俱乐部
					count = familyService.queryCountCreateFamily(userId);
					break;
				case 10:
					// 创建的俱乐部达到白银
					count = familyService.queryCountCreateFamily(userId);
					break;
				case 11:
					// 创建的俱乐部达到黄金
					count = familyService.queryCreateFamilyIsGold(userId);
					break;
				case 12:
					// 创建的俱乐部达到钻石
					count = familyService.queryCreateFamilyIsDiamond(userId);
					break;
				case 13:
					// 创建的俱乐部达到冠军
					count = familyService.queryCreateFamilyIsChampion(userId);
					break;
				case 14:
					// 单次行程10公里以上时获得100分评分
					count = tripService.getTripCountOnce100Score(userId);
					break;
				case 15:
					// 累计里程500公里
					count = tripService.getTripCountTotalMileageMoreThan500(userId);
					break;
				case 16:
					// 连续5次行程获得100分
					count = tripService.getTripCountQuintic100Score(userId);
					break;
				}
				
				if (missionId != 5) {
					count = count > targetCount ? targetCount : count;
					status = count == targetCount ? 1 : 2;
					userMissionLogsMapper.updateLogs(userId, missionId, status, count);
				}
			}
		}
	}
	
	// 个人任务列表
	public List<Map<String, String>> userMissionList(long userId, int missionType) {
		List<Map<String, String>> rtList = new ArrayList<>();

		List<Map<String, Object>> logs = userMissionLogsMapper.getLogsByUserIdAndType(userId, missionType);
		for (Map<String, Object> map : logs) {
			Map<String, String> rtMap = new HashMap<>();
			int missionId = Integer.valueOf(map.get("missionId").toString());
			int powerPrize = Integer.valueOf(String.valueOf(map.get("powerPrize")));
			int finishCount = Integer.valueOf(map.get("finishCount").toString());
			int targetCount = Integer.valueOf(map.get("targetCount").toString());
			int status = Integer.valueOf(map.get("status").toString());
			String actionLink = String.valueOf(map.get("actionLink"));
			String otherPrize = String.valueOf(map.get("otherPrize"));
			String missionProgress = "";
			String missionReward = "";
			if (missionId == 5) {
				// 处理连续登录任务
				powerPrize = (powerPrize * finishCount) >= 150 ? 150 : (powerPrize * finishCount);
				missionProgress = "连续签到第" + finishCount + "日";
			} else {
				missionProgress = "(" + finishCount + "/" + targetCount + ")次";
			}

			missionReward = "奖励：+" + powerPrize + "动力";

			String missionActionDesc = "";
			String actionStatus = "1";
			if (status == 1) {
				// 领取
				missionActionDesc = "领取";
				actionLink = "0";
			} else if (status == 3) {
				// 已完成
				missionActionDesc = "已领取";
				actionStatus = "0";
			} else {
				if (missionId == 4) {
					missionActionDesc = "去分享";
				} else if (missionId == 6 || missionId == 7) {
					missionActionDesc = "去认证";
				} else if (missionId == 8) {
					missionActionDesc = "去加入";
				} else if (missionId == 9) {
					missionActionDesc = "去创建";
				} else {
					missionActionDesc = "未完成";
					actionStatus = "0";
				}
			}

			rtMap.put("missionId", String.valueOf(map.get("missionId")));
			rtMap.put("missionName", String.valueOf(map.get("missionName")));
			rtMap.put("missionDesc", String.valueOf(map.get("missionCondition")));
			rtMap.put("missionProgress", missionProgress);
			rtMap.put("missionReward", missionReward);
			rtMap.put("missionActionDesc", missionActionDesc);
			rtMap.put("missionActionStatus", actionStatus);
			rtMap.put("otherPrize", otherPrize == null ? "" : otherPrize);
			rtMap.put("missionActionLink", actionLink == null ? "" : actionLink);
			rtMap.put("missionEndTime", map.get("endTime") == null ? "" : String.valueOf(map.get("endTime")));
			rtMap.put("flag", String.valueOf(map.get("status")));

			rtList.add(rtMap);
		}
		
		Collections.sort(rtList, new Comparator<Map<String, String>>() {
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return Double.valueOf(o1.get("missionId").toString()).compareTo(Double.valueOf(o2.get("missionId").toString()));
			}
		});
		//排序--- flag 1-->2-->3
		Collections.sort(rtList, new Comparator<Map<String, String>>() {
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return Double.valueOf(o1.get("flag").toString()).compareTo(Double.valueOf(o2.get("flag").toString()));
			}
		});
		
		return rtList;
	}
	
	

	public Map<String, Object> getMissionPrize(long userId, int missionId) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("otherPrizeImg", "");
		rtMap.put("ImgDesc", "");
		Map<String, Object> map = userMissionLogsMapper.getOneLogsByUserIdAndMissionId(userId, missionId);
		int powerPrize = Integer.valueOf(String.valueOf(map.get("powerPrize")));
		int finishCount = Integer.valueOf(map.get("finishCount").toString());
		if (missionId == 5) {
			// 处理连续登录任务
			powerPrize = (powerPrize * finishCount) >= 150 ? 150 : (powerPrize * finishCount);
		}
		
		int a = userMissionLogsMapper.updateLogsStatus(userId,missionId,3);
		
		if (a <= 0) {
			LOG.error("修改Logs状态失败：missionId==" + missionId + "==" + userId + "==" + userId);
			throw new RuntimeException("系统异常领取失败");
		}
		
		// 资产操作
		String paramSign = userId +String.valueOf(powerPrize);
		String sign = SignUtils.encryptHMAC(paramSign);
		boolean b = chezuAssetService.getMissionPrize(userId,powerPrize,missionId,sign);
		if (b == false) {
			throw new RuntimeException("系统异常领取失败");
		}
		
		// 完成行驶证认证 送 跑车一辆
		if (missionId == 7) {
			UserCar userCar = new UserCar();
			userCar.setUserId(userId);
			userCar.setCarId(3);
			userCar.setCreateTime(new Date());
			userCar.setInUse(0);
			this.userCarMapper.insertUserCar(userCar);
			DicCar car = dicCarMapper.getCarByCarId(3);
			rtMap.put("otherPrizeImg", car.getCarUrl());
			rtMap.put("ImgDesc", car.getCarName());
		}

		// 创建的俱乐部达到黄金 送 豪华轿车一辆
		if (missionId == 11) {
			UserCar userCar = new UserCar();
			userCar.setUserId(userId);
			userCar.setCarId(2);
			userCar.setCreateTime(new Date());
			userCar.setInUse(0);
			this.userCarMapper.insertUserCar(userCar);
			DicCar car = dicCarMapper.getCarByCarId(2);
			rtMap.put("otherPrizeImg", car.getCarUrl());
			rtMap.put("ImgDesc", car.getCarName());
		}
		
		return rtMap;
	}

	//查询每个Type任务完成数量
	public Map<String, String> queryMissionStatus(long userId) {
		Map<String, String> rtMap = new HashMap<>();
		int a = userMissionLogsMapper.queryCountByType(userId, 1);
		int b = userMissionLogsMapper.queryCountByType(userId, 2);
		int c = userMissionLogsMapper.queryCountByType(userId, 3);
		rtMap.put("dayMission", String.valueOf(a));
		rtMap.put("achieveMission", String.valueOf(b));
		rtMap.put("activityMission", String.valueOf(c));
		return rtMap;
	}

	//预更新每日登录的任务
	public int updateCountOfId5(Long userId) {
		// TODO Auto-generated method stub
		return userMissionLogsMapper.updateCountOfId5(userId);
	}

	//查询表里面是否有记录
	public int queryHadRecord(long userId) {
		return userMissionLogsMapper.queryHadRecord(userId);
	}

	// 更新每日任务
	public boolean updateDayMission(long userId) {
		UserMissionLogs logs = userMissionLogsMapper.queryMissionId1(userId);
		if(logs == null) {
			return false;
		}
		Date daystamp = DateTools.getDateTimeOfStr(logs.getDaystamp(), "yyyy-MM-dd");
		Date now = DateTools.getDateTimeOfStr(DateTools.getYYYY_MM_DD(), "yyyy-MM-dd");
		if (daystamp.getTime() < now.getTime()) {
			// 重置missionId为1、2、3、4的logs
			userMissionLogsMapper.updateValid(userId);
			UserMissionLogs log = new UserMissionLogs();
			int [] logId = {1,2,3,4};
			int [] tgtCount = {5,3,1,1};
			for (int i = 0; i < 4; i++) {
				log.setMissionId(logId[i]);
				log.setFinishCount(0);
				log.setTargetCount(tgtCount[i]);
				log.setUserId(userId);
				log.setStatus(2);
				userMissionLogsMapper.insertOneLogs(log);
			}
			return true;
		}

		return false;
	}

	public void initMissionOfUserId(long userId) {
		// 初始化任务系统
		// 更新每日任务
		boolean flag = this.updateDayMission(userId);
		LOG.info("更新每日任务状态=================" + flag);
		// 初始化
		int count = this.queryHadRecord(userId);
		if (count == 0) {
			LOG.info("初始化任务log============userId=================" + userId);
			List<DicUserMission> missions = this.getAllDicUserMission();
			this.initLogsToUser(missions, userId);
			this.updateCountOfId5(userId);
		}
		// 预查询
		this.insertOrUpdateLogs(userId, 1);
		this.insertOrUpdateLogs(userId, 2);
	}
}
