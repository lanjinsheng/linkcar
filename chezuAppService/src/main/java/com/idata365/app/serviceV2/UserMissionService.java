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
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.UserCar;
import com.idata365.app.entity.UserMissionLogs;
import com.idata365.app.entity.v2.MissionLogsResultBean;
import com.idata365.app.entity.v2.MissionResultBean;
import com.idata365.app.mapper.BoxTreasureMapper;
import com.idata365.app.mapper.CarpoolMapper;
import com.idata365.app.mapper.ComponentMapper;
import com.idata365.app.mapper.DicCarMapper;
import com.idata365.app.mapper.DicUserMissionMapper;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.FamilyRelationMapper;
import com.idata365.app.mapper.UserCarMapper;
import com.idata365.app.mapper.UserMissionLogsMapper;
import com.idata365.app.remote.ChezuAccountService;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.BaseService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.TripService;
import com.idata365.app.util.AdBeanUtils;
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
	@Autowired
	CarpoolMapper carpoolMapper;
	@Autowired
	ComponentMapper componentMapper;
	@Autowired
	BoxTreasureMapper boxTreasureMapper;
	@Autowired
	FamilyMapper familyMapper;
	@Autowired
	FamilyRelationMapper familyRelationMapper;

	// 从字典表查询所有任务
	public List<DicUserMission> getAllDicUserMission() {
		return dicUserMissionMapper.getAllDicUserMission();
	}

	// 根据userId查询任务
	public List<UserMissionLogs> getLogsByUserId(long userId) {
		return userMissionLogsMapper.getLogsByUserId(userId);
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
				case 17:
					// 搭乘顺风车并完成行程1次
					count = carpoolMapper.getTodayLiftCountByUserId(userId);
					break;
				case 18:
					// 今日发布祈愿1次
					count = componentMapper.countOfPray(userId);
					break;
				case 19:
					// 在加入的俱乐部配件库中获得3次分配的配件
					count = componentMapper.countGetGive(userId);
					break;
				case 20:
					// 创建的俱乐部内成员数量达到3个
					Long familyId = familyMapper.queryCreateFamilyId(userId);
					if (familyId != null) {
						FamilyParamBean bean = new FamilyParamBean();
						bean.setFamilyId(familyId);
						count = familyMapper.countUsersByFamilyId(bean);
					}
					break;
				case 21:
					// 创建的俱乐部内成员数量达到5个
					Long familyId1 = familyMapper.queryCreateFamilyId(userId);
					if (familyId1 != null) {
						FamilyParamBean bean = new FamilyParamBean();
						bean.setFamilyId(familyId1);
						count = familyMapper.countUsersByFamilyId(bean);
					}
					break;
				case 22:
					// 完成一次赛车场挑战
					Long familyId2 = familyMapper.queryCreateFamilyId(userId);
					if (familyId2 != null) {
						count = familyRelationMapper.countOfInitiativeFight(familyId2);
					}
					break;
				case 23:
					// 打开一次配件库宝箱
					count = boxTreasureMapper.countOfOpenUserBox(userId);
					break;
				case 24:
					// 在俱乐部配件库中分配三次配件--针对老板
					count = componentMapper.countAllotGive(userId);
					break;
				case 25:
					// 在祈愿中为其他玩家赠送3次配件
					count = componentMapper.countSendRequest(userId);
					break;
				case 26:
					// 累计搭乘顺风车并完成行程5次
					count = carpoolMapper.getTotalLiftCountByUserId(userId);
					break;
				case 27:
					// 累计搭乘顺风车并完成行程10次
					count = carpoolMapper.getTotalLiftCountByUserId(userId);
					break;
				case 28:
					// 累计搭乘顺风车并完成行程20次
					count = carpoolMapper.getTotalLiftCountByUserId(userId);
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
	public List<MissionResultBean> userMissionList(long userId, int missionType) {
		List<MissionResultBean> rtList = new ArrayList<>();
		List<Integer> parentMissionIds = dicUserMissionMapper.getParentMissionId(missionType);
		for (Integer parentMissionId : parentMissionIds) {
			List<MissionLogsResultBean> logs = userMissionLogsMapper.getLogsByUserIdAndParentMID(userId,parentMissionId);
			MissionResultBean bean = new MissionResultBean();
			Map<String, Object> levelDic = new HashMap<>();
			MissionLogsResultBean resultBean = new MissionLogsResultBean();
			int index = 0;
			if (logs.size() == 1) {
				resultBean = logs.get(0);
			} else {
				int a = -1;
				int b = -1;
				int c = -1;
				for (int i = 0; i < logs.size(); i++) {
					if (logs.get(i).getStatus() == 1) {
						a = i;
						break;
					} else if (logs.get(i).getStatus() == 2) {
						b = i;
					} else {
						c = i;
					}
				}
				index = a > -1 ? a : (b > -1 ? b : c);
				resultBean = logs.get(index);
			}
			AdBeanUtils.copyOtherPropToStr(bean, resultBean);

			String missionProgress = "";
			int powerPrize = 0;
			if (resultBean.getMissionId() == 5) {
				// 持之以恒
				powerPrize = (Integer.valueOf(resultBean.getPowerPrize()) * resultBean.getFinishCount()) >= 150 ? 150
						: (Integer.valueOf(resultBean.getPowerPrize()) * resultBean.getFinishCount());
				missionProgress = "连续登录第" + resultBean.getFinishCount() + "日";
			} else {
				powerPrize = Integer.valueOf(resultBean.getPowerPrize());
				missionProgress = "(" + resultBean.getFinishCount() + "/" + resultBean.getTargetCount() + ")次";
			}

			bean.setMissionActionDesc(resultBean.getStatus() == 1 ? "领取" : (resultBean.getStatus() == 3 ? "已领取" : (resultBean.getLinkDesc().equals("")==true?"未完成":resultBean.getLinkDesc())));
			bean.setMissionActionStatus((resultBean.getStatus() == 3||resultBean.getLinkDesc().equals(""))? "0" : "1");
			bean.setMissionActionLink(resultBean.getStatus() == 1 ? "0" : resultBean.getMissionActionLink());
			int missionId = resultBean.getMissionId();
			if ((missionId == 17 || missionId == 18 || missionId == 20 || missionId == 21 || missionId == 22
					|| missionId == 24 || missionId == 25 || missionId == 26 || missionId == 27 || missionId == 28)
					&& resultBean.getStatus() == 2) {
				// 根据不同用户处理条跳转逻辑
				// 创建家族和加入家族信息
				Long createFamilyId = this.familyMapper.queryCreateFamilyId(userId);
				Long joinFamilyId = this.familyMapper.queryJoinFamilyId(userId);
				if (missionId == 17 || missionId == 26 || missionId == 27 || missionId == 28 || missionId == 18
						|| missionId == 25) {
					if (createFamilyId == null && joinFamilyId == null) {
						bean.setMissionActionDesc("未完成");
						bean.setMissionActionStatus("0");
						bean.setMissionActionLink("");
					}
				}
				if (missionId == 22 || missionId == 24) {
					if (createFamilyId == null) {
						bean.setMissionActionDesc("未完成");
						bean.setMissionActionStatus("0");
						bean.setMissionActionLink("");
					}
				}
			}
			bean.setMissionProgress(missionProgress);
			bean.setMissionReward(powerPrize == 0 ? ("奖励:" + resultBean.getOtherPrize()) : ("奖励:+" + powerPrize + "动力"));
			bean.setFlag(String.valueOf(resultBean.getStatus()));
			List<Map<String, String>> list = new ArrayList<>();
			if (logs.size() > 1) {
				for (int i = 0; i < logs.size(); i++) {
					Map<String, String> map = new HashMap<>();
					map.put("missionDesc", logs.get(i).getMissionDesc());
					if (i == index) {
						map.put("statusDesc", "进行中");
					} else if (logs.get(i).getStatus() == 3) {
						map.put("statusDesc", "0");
					} else {
						if (logs.get(i).getPowerPrize().equals("")) {
							map.put("statusDesc", "奖励:" + logs.get(i).getOtherPrize());
						} else {
							map.put("statusDesc", "奖励:+" + logs.get(i).getPowerPrize() + "动力");
						}
					}
					list.add(map);
				}
			}
			levelDic.put("dic", list);
			bean.setLevelDic(levelDic);

			rtList.add(bean);
		}

//		Collections.sort(rtList, new Comparator<MissionResultBean>() {
//			public int compare(MissionResultBean o1, MissionResultBean o2) {
//				return Double.valueOf(o1.getMissionId()).compareTo(Double.valueOf(o2.getMissionId()));
//			}
//		});
		// 排序--- flag 1-->2-->3
		Collections.sort(rtList, new Comparator<MissionResultBean>() {
			public int compare(MissionResultBean o1, MissionResultBean o2) {
				return Double.valueOf(o1.getFlag()).compareTo(Double.valueOf(o2.getFlag()));
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
		// 初始化
		List<Integer> record = userMissionLogsMapper.queryHadRecord(userId);
		List<DicUserMission> missions = this.getAllDicUserMission();
		if (record == null || record.size() != missions.size()) {
			LOG.info("初始化任务log============userId=================" + userId);
			List<DicUserMission> beanList = new ArrayList<>();
			if (record == null) {
				beanList = missions;
			} else {
				for (DicUserMission dicUserMission : missions) {
					if (!record.contains(dicUserMission.getMissionId())) {
						beanList.add(dicUserMission);
					}
				}
			}
			userMissionLogsMapper.initLogsToUser(beanList, userId);
			userMissionLogsMapper.updateCountOfId5(userId);
		}
		// 更新每日任务
		boolean flag = this.updateDayMission(userId);
		LOG.info("更新每日任务状态=================" + flag);
		// 预查询
		this.insertOrUpdateLogs(userId, 1);
		this.insertOrUpdateLogs(userId, 2);
	}
}
