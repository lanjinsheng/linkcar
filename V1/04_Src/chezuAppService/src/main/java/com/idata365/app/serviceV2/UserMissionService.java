package com.idata365.app.serviceV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.DicUserMission;
import com.idata365.app.entity.UserMissionLogs;
import com.idata365.app.mapper.DicUserMissionMapper;
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
			if(status==2) {
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
					break;
				case 3:
					// 今日出行1次
					count = tripService.getTodayCountTrip(userId);
					break;
				case 4:
					// 分享好车族给好友
					count = userInfoServiceV2.queryUserShareCountToday(userId);
					break;
				case 5:
					// 连续登录（每天增加5点，上限150点）
					String daystamp = String.valueOf(map.get("daystamp"));
					String format = "yyyy-MM-dd";
					String recordNext = DateTools.getAddMinuteDateTime(daystamp,60*24,format);
					String now = DateTools.getCurDate(format);
					if(daystamp.equals(now)) {
						count = 0;
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
			} else if (status == 3) {
				// 已完成
				missionActionDesc = "已完成";
			} else {
				if (missionId == 1) {
					missionActionDesc = "去偷取";
				} else if (missionId == 2) {
					missionActionDesc = "去点赞";
				} else if (missionId == 4) {
					missionActionDesc = "去分享";
				} else if (missionId == 6) {
					missionActionDesc = "去认证";
				} else if (missionId == 7) {
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
			rtMap.put("missionActionLink", String.valueOf(map.get("actionLink")));
			rtMap.put("missionEndTime", String.valueOf(map.get("endTime")));
			rtMap.put("flag", String.valueOf(map.get("status")));

			rtList.add(rtMap);
		}
		
		//排序--- flag 1-->2-->3
		Collections.sort(rtList, new Comparator<Map<String, String>>() {
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return Double.valueOf(o1.get("flag").toString()).compareTo(Double.valueOf(o2.get("flag").toString()));
			}
		});
		
		return rtList;
	}
	
	

	public void getMissionPrize(long userId, int missionId) {
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
	}

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

	public int updateCountOfId5(Long userId) {
		// TODO Auto-generated method stub
		return userMissionLogsMapper.updateCountOfId5(userId);
	}

	public int queryHadRecord(long userId) {
		return userMissionLogsMapper.queryHadRecord(userId);
	}
}
