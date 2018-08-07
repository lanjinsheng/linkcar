package com.idata365.app.serviceV2;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.constant.DicFamilyTypeConstant;
import com.idata365.app.entity.CompetitorFamilyInfoResultBean;
import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.entity.FamilyDriveDayStat;
import com.idata365.app.entity.FamilyInfoBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRelation;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.ScoreFamilyInfoAllBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.ScoreMemberInfoResultBean;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.GameMapper;
import com.idata365.app.mapper.UserCarLogsMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.remote.ChezuAccountService;
import com.idata365.app.service.BaseService;
import com.idata365.app.service.FightService;
import com.idata365.app.service.ScoreService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.service.common.FamilyScoreService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.PhoneUtils;
import com.idata365.app.util.ValidTools;

@Service
public class GameServiceV2 extends BaseService<GameServiceV2> {
	protected static final Logger LOGGER = LoggerFactory.getLogger(GameServiceV2.class);

	@Autowired
	private GameMapper gameMapper;
	@Autowired
	private FamilyMapper familyMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private ChezuAccountService chezuAccountService;
	@Autowired
	private FamilyScoreService familyScoreService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private FightService fightService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserCarLogsMapper userCarLogsMapper;

	/**
	 * 实时榜单
	 * 
	 * @param bean
	 * @return
	 */
	public List<Map<String, String>> billBoard(ScoreFamilyInfoAllBean queryFamily) {

		List<Map<String, String>> billList = new ArrayList<>();
		List<Map<String, Object>> list = familyMapper.queryAllFamilyOrderNo();
//		long familyId = 0;
//		// 加入自己俱乐部排名信息
//		if (ValidTools.isNotBlank(queryFamily)) {
//			if (ValidTools.isNotBlank(queryFamily.getOriFamily())) {
//				familyId = queryFamily.getOriFamily().getFamilyId();
//				Map<String, Object> map = familyMapper.queryFamilyOrderNoByFamilyId(familyId);
//				if (map != null) {
//					list.add(map);
//				}
//			}
//			if (ValidTools.isNotBlank(queryFamily.getJoinFamily())) {
//				familyId = queryFamily.getJoinFamily().getFamilyId();
//				Map<String, Object> map = familyMapper.queryFamilyOrderNoByFamilyId(familyId);
//				if (map != null) {
//					list.add(map);
//				}
//			}
//		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> bill = new HashMap<>();
			UsersAccount usersAccount = usersAccountMapper
					.findAccountById(Long.valueOf(list.get(i).get("createUserId").toString()));
			DicFamilyType familyType = DicFamilyTypeConstant
					.getDicFamilyType(Integer.valueOf(list.get(i).get("familyType").toString()));
			bill.put("id", (list.get(i).get("familyId").toString()));
			bill.put("familyId", (list.get(i).get("familyId").toString()));
			bill.put("rank", (list.get(i).get("yesterdayOrderNo").toString()));
			bill.put("name", list.get(i).get("familyName").toString());
			bill.put("captainOrGroupName",
					usersAccount.getNickName() == null ? PhoneUtils.hidePhone(usersAccount.getPhone())
							: usersAccount.getNickName());
			bill.put("gradeOrNum", familyType.getFamilyTypeValue());
			bill.put("trophyNum", list.get(i).get("trophy").toString());
			billList.add(bill);
		}

		return billList;
	}

	// 根据俱乐部ID获取分数、名称、奖杯数、等级
	public Map<String, String> getInfoByFamilyId(long familyId, String daystamp) {
		if (daystamp == null) {
			daystamp = getCurrentDayStr();
		}
		Map<String, Object> map = familyMapper.getInfoByFamilyId(familyId, daystamp);
		if (ValidTools.isBlank(map)) {
			return null;
		}
		Map<String, String> bill = new HashMap<>();
		FamilyParamBean bean = new FamilyParamBean();
		bean.setFamilyId(Long.valueOf(map.get("familyId").toString()));
		FamilyInfoBean familyInfo = familyMapper.queryFamilyInfo(bean);
		DicFamilyType familyType = DicFamilyTypeConstant
				.getDicFamilyType(Integer.valueOf(map.get("familyType").toString()));

		bill.put("rank", map.get("yesterdayOrderNo").toString());
		bill.put("name", familyInfo.getFamilyName());
		bill.put("gradeOrNum", familyType.getFamilyTypeValue());
		bill.put("familyType", map.get("familyType").toString());
		bill.put("trophyNum", map.get("trophy").toString());
		return bill;
	}

	// 根据时间查询正在对战的俱乐部系信息
	public CompetitorFamilyInfoResultBean queryCompetitorFamilyInfo(long myFamilyId, String fightingTime) {
		FamilyRelationBean relationBean = new FamilyRelationBean();
		relationBean.setFamilyId(myFamilyId);
		if (fightingTime == null) {
			relationBean.setDaystamp(getCurrentDayStr());
		} else {
			relationBean.setDaystamp(fightingTime);
		}
		List<FamilyRelationBean> competitorList = this.familyMapper.queryFamilyIdByCompetitorId(relationBean);
		if (CollectionUtils.isEmpty(competitorList)) {
			return null;
		}
		FamilyRelationBean relationResultBean = competitorList.get(0);
		long familyId1 = relationResultBean.getFamilyId1();
		long familyId2 = relationResultBean.getFamilyId2();

		FamilyParamBean familyParamBean = new FamilyParamBean();
		if (myFamilyId != familyId1) {
			familyParamBean.setFamilyId(familyId1);
		} else {
			familyParamBean.setFamilyId(familyId2);
		}

		FamilyInfoBean familyInfoBean = this.familyMapper.queryFamilyInfo(familyParamBean);

		if (null == familyInfoBean) {
			return null;
		}

		CompetitorFamilyInfoResultBean resultBean = new CompetitorFamilyInfoResultBean();
		resultBean.setCompetitorFamilyId(String.valueOf(familyInfoBean.getId()));
		resultBean.setFamilyName(familyInfoBean.getFamilyName());
		resultBean.setImgUrl(familyInfoBean.getImgUrl());
		resultBean.setCountdown(String.valueOf(calDown()));

		return resultBean;
	}

	public static long calDown() {
		try {
			long calCountDown = calCountDown();
			return calCountDown;
		} catch (ParseException e) {
			LOGGER.error("", e);
		}

		return 0;
	}

	private static final String ZERO_POINT = "00:00:00";

	private static final String TEN_POINT = "10:00:00";

	private static final String SIXTEEN_POINT = "16:00:00";

	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static long calCountDown() throws ParseException {
		Date currentTs = Calendar.getInstance().getTime();
		String prefixDayStr = DateFormatUtils.format(currentTs, "yyyy-MM-dd");

		Date tomorrowDate = DateUtils.addDays(currentTs, 1);
		String tomorrowDayStr = DateFormatUtils.format(tomorrowDate, "yyyy-MM-dd");

		long currentSeconds = currentTs.getTime() / 1000L;

		String zeroTsStr = prefixDayStr + " " + ZERO_POINT;
		String tenTsStr = prefixDayStr + " " + TEN_POINT;
		String sixteenTsStr = prefixDayStr + " " + SIXTEEN_POINT;

		String tomorrowTsTr = tomorrowDayStr + " " + ZERO_POINT;

		Date zeroTs = DateUtils.parseDate(zeroTsStr, DATE_PATTERN);
		Date tenTs = DateUtils.parseDate(tenTsStr, DATE_PATTERN);
		Date sixteenTs = DateUtils.parseDate(sixteenTsStr, DATE_PATTERN);

		Date tomorrowTs = DateUtils.parseDate(tomorrowTsTr, DATE_PATTERN);

		if (currentTs.compareTo(zeroTs) >= 0 && currentTs.compareTo(tenTs) < 0) {
			return tenTs.getTime() / 1000 - currentSeconds;
		} else if (currentTs.compareTo(tenTs) >= 0 && currentTs.compareTo(sixteenTs) < 0) {
			return sixteenTs.getTime() / 1000 - currentSeconds;
		} else {
			return tomorrowTs.getTime() / 1000 - currentSeconds;
		}
	}

	// 查询所在俱乐部对战信息
	public List<FamilyRelation> queryFightRecordByFamilyId(long myFamilyId, long recordId) {
		FamilyRelationBean relationBean = new FamilyRelationBean();
		relationBean.setFamilyId(myFamilyId);
		relationBean.setDaystamp(getCurrentDayStr());
		List<FamilyRelation> familyRelationList = new ArrayList<>();
		if (recordId == 0) {
			familyRelationList = familyMapper.queryFightRecordByFamilyIdFirst(myFamilyId);
		} else {
			familyRelationList = familyMapper.queryFightRecordByFamilyId(myFamilyId, recordId);
		}

		if (CollectionUtils.isEmpty(familyRelationList)) {
			return null;
		}

		return familyRelationList;
	}

	// 根据时间获取俱乐部分数
	public FamilyDriveDayStat queryFamilyScore(long familyId, String daystamp) {
		return gameMapper.queryFamilyScore(familyId, daystamp);
	}

	// 获取俱乐部实时排名
	public String queryFamilyOrderByFId(long familyId) {
		return String.valueOf(familyMapper.queryFamilyOrderByFId(familyId)).toString();
	}

	private String getCurrentDayStr() {
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}

	public List<Map<String, Object>> getMemberList(int familyType, int status, List<Map<String, Object>> userRankList,
			long myFamilyId, Long fightFamilyId, String daystamp, long score1, long score2) {
		LOGGER.info(
				"familyType==" + familyType + "==status==" + status + "==userRankList.size()==" + userRankList.size()
						+ "==myFamilyId==" + myFamilyId + "==fightFamilyId==" + fightFamilyId + "==score1==" + score1);
		long cardinalNum = 0;
		// 段位动力基数
		if (familyType >= 120) {
			cardinalNum = 300;
		} else if (familyType >= 110) {
			cardinalNum = 250;
		} else if (familyType == 100) {
			cardinalNum = 200;
		} else if (familyType == 90) {
			cardinalNum = 150;
		} else {
			cardinalNum = 100;
		}

		Map<Integer, String> map = new HashMap<>();
		map.put(1, "1st");
		map.put(2, "2nd");
		map.put(3, "3rd");
		map.put(4, "4th");
		map.put(5, "5th");
		map.put(6, "6th");
		map.put(7, "7th");
		map.put(8, "8th");

		// 根据时间获取我俱乐部的人数
		String sign = "";
		String ids = chezuAccountService.getCurrentUsersByFamilyId(myFamilyId, daystamp, sign);
		String[] count = ids.split(",");
		int userCount = count.length;
		long totalprizeNum = cardinalNum * userCount;// 俱乐部共获得动力

		if (status == 0) {
			// 平局
			for (int i = 0; i < userRankList.size(); i++) {
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					userRankList.get(i).put("isMyFamilyFlag", "1");
					userRankList.get(i).put("desc", "无奖励");
				} else {
					userRankList.get(i).put("isMyFamilyFlag", "2");
					userRankList.get(i).put("desc", "被挑战俱乐部成员");
				}
				userRankList.get(i).put("rank", "平局");
				userRankList.get(i).put("isSpectators", "0");// 1 true 2 false
			}
			Collections.sort(userRankList, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return Double.valueOf(o1.get("isMyFamilyFlag").toString())
							.compareTo(Double.valueOf(o2.get("isMyFamilyFlag").toString()));
				}
			});
		} else if (status == 1) {
			// 胜利
			for (int i = 0; i < userRankList.size(); i++) {
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					userRankList.get(i).put("isMyFamilyFlag", "1");
					userRankList.get(i).put("rank", map.get(i + 1));
					long powerNum = (totalprizeNum
							* Double.valueOf(userRankList.get(i).get("score").toString()).longValue() / score1);
					userRankList.get(i).put("desc", "+" + powerNum);
				} else {
					userRankList.get(i).put("isMyFamilyFlag", "2");
					userRankList.get(i).put("rank", "失败");
					userRankList.get(i).put("desc", "被挑战俱乐部成员");
				}
				userRankList.get(i).put("isSpectators", "0");// 1 true 2 false
			}
			Collections.sort(userRankList, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return Double.valueOf(o1.get("isMyFamilyFlag").toString())
							.compareTo(Double.valueOf(o2.get("isMyFamilyFlag").toString()));
				}
			});
			for (int i = 0; i < userRankList.size(); i++) {
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					userRankList.get(i).put("rank", map.get(i + 1));
				} else {
					userRankList.get(i).put("rank", "失败");
				}
			}
		} else if (status == 2) {
			// 失败
			for (int i = 0; i < userRankList.size(); i++) {
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					userRankList.get(i).put("isMyFamilyFlag", "2");
					userRankList.get(i).put("rank", "失败");
					userRankList.get(i).put("desc", "无奖励");
				} else {
					userRankList.get(i).put("isMyFamilyFlag", "1");
					userRankList.get(i).put("rank", map.get(i + 1));
					userRankList.get(i).put("desc", "被挑战俱乐部成员");
				}
				userRankList.get(i).put("isSpectators", "0");// 1 true 2 false
			}
			Collections.sort(userRankList, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return Double.valueOf(o1.get("isMyFamilyFlag").toString())
							.compareTo(Double.valueOf(o2.get("isMyFamilyFlag").toString()));
				}
			});
			for (int i = 0; i < userRankList.size(); i++) {
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					userRankList.get(i).put("rank", "失败");
				} else {
					userRankList.get(i).put("rank", map.get(i + 1));
				}
			}
		} else if (status == 3) {
			// 正在对战
			/*
			 * for (int i = 0; i < userRankList.size(); i++) { if
			 * (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId)
			 * { long d =
			 * Double.valueOf(userRankList.get(i).get("score").toString()).longValue(); long
			 * powerNum =(totalprizeNum * d) / score1; userRankList.get(i).put("desc",
			 * "挑战获胜后可获得+" + powerNum); userRankList.get(i).put("isMyFamilyFlag", "1"); }
			 * else { userRankList.get(i).put("desc", "被挑战俱乐部成员");
			 * userRankList.get(i).put("isMyFamilyFlag", "0"); } if (i >= 8) {
			 * userRankList.get(i).put("rank", "观战");
			 * userRankList.get(i).put("isSpectators", "1");// 1 true 2 false } else {
			 * userRankList.get(i).put("rank", map.get(i + 1));
			 * userRankList.get(i).put("isSpectators", "0");// 1 true 2 false }
			 * 
			 * }
			 */
			int a = 0;
			int b = 0;
			for (int i = 0; i < userRankList.size(); i++) {
				userRankList.get(i).put("isSpectators", "0");// 1 true 2 false
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					long d = Double.valueOf(userRankList.get(i).get("score").toString()).longValue();
					long powerNum = (totalprizeNum * d) / score1;
					userRankList.get(i).put("desc", "挑战获胜后可获得+" + powerNum);
					userRankList.get(i).put("isMyFamilyFlag", "1");
					a++;
					if (a > 4) {
						userRankList.get(i).put("isSpectators", "1");// 1 true 2 false
					}
				} else {
					userRankList.get(i).put("desc", "被挑战俱乐部成员");
					userRankList.get(i).put("isMyFamilyFlag", "0");
					b++;
					if (b > 4) {
						userRankList.get(i).put("isSpectators", "1");// 1 true 2 false
					}
				}
			}
			Collections.sort(userRankList, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return Double.valueOf(o1.get("isSpectators").toString())
							.compareTo(Double.valueOf(o2.get("isSpectators").toString()));
				}
			});
			for (int i = 0; i < userRankList.size(); i++) {
				if (userRankList.get(i).get("isSpectators").equals("0")) {
					userRankList.get(i).put("rank", map.get(i + 1));
				} else {
					userRankList.get(i).put("rank", "观战");
				}
			}
		}

		return userRankList;
	}

	// 对战详情
	public Map<String, Object> getFightingDetail(Map<String, Object> result, List<Map<String, Object>> data,
			long myFamilyId, long opponentId) {
		method(result, data, myFamilyId, myFamilyId);
		method(result, data, myFamilyId, opponentId);
		result.put("myFamilyInfo", data.get(0));
		result.put("fightFamilyInfo", data.get(1));

		return result;
	}

	// 对战详情封装方法
	private void method(Map<String, Object> result, List<Map<String, Object>> data, long myFamilyId, long familyId) {
		List<Map<String, Object>> users = new ArrayList<>();
		Map<String, Object> familyInfo = new HashMap<>();
		String daystamp = null;
		int familyType = 0;// 我俱乐部等级
		// 俱乐部的返回信息
		Map<String, String> infoFamily = this.getInfoByFamilyId(familyId, daystamp);
		familyInfo.put("familyName", infoFamily.get("name"));
		familyInfo.put("isPC", "0");
		familyInfo.put("familyId", String.valueOf(familyId));
		double sc = familyScoreService.familyScore(Long.valueOf(familyId), getCurrentDayStr());
		BigDecimal b = new BigDecimal(sc);
		familyInfo.put("familyScore", b.setScale(1, BigDecimal.ROUND_HALF_UP).toString());
		familyInfo.put("trophyNum", infoFamily.get("trophyNum"));
		familyInfo.put("grade", infoFamily.get("gradeOrNum"));

		// 俱乐部的成员信息
		ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
		bean.setFamilyId(familyId);
		List<ScoreMemberInfoResultBean> member = scoreService.listFamilyMember(bean);
		for (int j = 0; j < member.size(); j++) {
			Map<String, Object> entity = new HashMap<>();
			String memberId = member.get(j).getUserId();
			double score = scoreService.getAvgScore(memberId, familyId);
			entity.put("userId", memberId);
			entity.put("familyId", familyId);
			entity.put("name", member.get(j).getName());
			entity.put("score", score);
			// 用户当前车辆
			String nowTime=DateTools.getYYYYMMDDMMSS();
			Map<String,Object> map=new HashMap<>();
			map.put("userId", memberId);
		    map.put("nowTime", nowTime);
			Map<String,Object> car=userCarLogsMapper.getUserCar(map);
			
			entity.put("carImg", car.get("carUrl").toString());
			String powerUpPercent = car.get("clubScoreUpPercent").toString() + "%";
			entity.put("powerUpPercent", "车辆加成+" + powerUpPercent);
			users.add(entity);
		}
		// 按照分数从高到低排序
		Collections.sort(users, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return Double.valueOf(o2.get("score").toString()).compareTo(Double.valueOf(o1.get("score").toString()));
			}
		});

		// 奖励与惩罚
		if (familyId == myFamilyId) {
			familyType = Integer.valueOf(infoFamily.get("familyType").toString());
			String win = null;
			String loss = null;
			DicFamilyType type = DicFamilyTypeConstant.getDicFamilyType(familyType);
			win = "+" + type.getWin();
			if (type.getLoss() >= 0) {
				loss = "+" + type.getLoss();
			} else {
				loss = "" + type.getLoss();
			}
			result.put("trophyNumReward", win);
			result.put("diamondsReward", " 大量");
			result.put("reward", "奖杯" + win + " 大量钻石");
			result.put("punishment", loss);
		}

		// 俱乐部成员动力描述
		long cardinalNum = 0;
		// 段位动力基数
		if (familyType >= 120) {
			cardinalNum = 300;
		} else if (familyType >= 110) {
			cardinalNum = 250;
		} else if (familyType == 100) {
			cardinalNum = 200;
		} else if (familyType == 90) {
			cardinalNum = 150;
		} else {
			cardinalNum = 100;
		}
		int userCount = member.size();
		long totalprizeNum = cardinalNum * userCount;// 俱乐部共获得动力
		int a = 0;
		int c = 0;
		for (int i = 0; i < users.size(); i++) {
			users.get(i).put("isSpectators", "0");// 1 true 0 false
			if (Long.valueOf(users.get(i).get("familyId").toString()) == myFamilyId) {
				long d = Double.valueOf(users.get(i).get("score").toString()).longValue();
				long powerNum = (long) ((totalprizeNum * d) / sc);
				users.get(i).put("desc", "挑战获胜后可获得" + powerNum);
				// users.get(i).put("isMyFamilyFlag", "1");
				a++;
				if (a > 4) {
					users.get(i).put("isSpectators", "1");// 1 true 0 false
				}
			} else {
				users.get(i).put("desc", "被挑战俱乐部成员");
				// users.get(i).put("isMyFamilyFlag", "0");
				c++;
				if (c > 4) {
					users.get(i).put("isSpectators", "1");// 1 true 0 false
				}
			}
		}

		familyInfo.put("memberList", users);
		data.add(familyInfo);
	}

	// 历史成绩
	public Map<String, Object> getFightingHistory(long myFamilyId, String daystamp, Map<String, Object> result) {
		List<Map<String, Object>> data = new ArrayList<>();
		// 获取对战俱乐部
		Long fightFamilyId = fightService.getOpponentIdBySelfId(myFamilyId, daystamp);
		String win = "";
		String loss = "";
		int familyType = 0;// 我俱乐部等级
		int userCount = 0;
		long[] arr = { myFamilyId, fightFamilyId };
		List<Map<String, Object>> memberList1 = new ArrayList<>();
		List<Map<String, Object>> memberList2 = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			// 对应俱乐部信息
			Map<String, String> map = this.getInfoByFamilyId(arr[i], daystamp);
			Map<String, Object> familyInfo = new HashMap<>();
			familyInfo.put("familyId", String.valueOf(arr[i]));
			familyInfo.put("familyName", map.get("name"));
			String familyScore = this.queryFamilyScore(arr[i], daystamp).getScore().toString();
			familyInfo.put("familyScore", familyScore);
			familyInfo.put("trophyNum", map.get("trophyNum"));
			familyInfo.put("grade", map.get("gradeOrNum"));
			
			// 俱乐部成员信息
			List<Map<String, Object>> users = scoreService.getMemberInfoByTime(arr[i], daystamp);
			for (int j = 0; j < users.size(); j++) {
				Map<String, Object> bean = new HashMap<>();
				String memberId = users.get(j).get("userId").toString();
				String score = users.get(j).get("avgScore").toString();
				UsersAccount account = userInfoService.getUsersAccount(Long.valueOf(memberId));
				bean.put("userId", memberId);
				bean.put("familyId", arr[i]);
				bean.put("name", account.getNickName());
				bean.put("score", score);
				// 用户当时车辆
				Date dd = DateTools.getDateTimeOfStr(daystamp,"yyyy-MM-dd");
				Date curdate = DateTools.getAddMinuteDateTime(dd,60*24-1);
				Map<String,Object> m=new HashMap<>();
				m.put("userId", memberId);
			    m.put("nowTime", curdate);
				Map<String,Object> car=userCarLogsMapper.getUserCar(m);
				
				bean.put("carImg", car==null?"http://product-h5.idata365.com/appImgs/car_1.png":car.get("carUrl").toString());
				String powerUpPercent = car==null?"0":car.get("clubScoreUpPercent").toString() + "%";
				bean.put("powerUpPercent", "车辆加成+" + powerUpPercent);
				if (i == 0) {
					memberList1.add(bean);
				} else {
					memberList2.add(bean);
				}
			}
			Collections.sort(memberList1, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return Double.valueOf(o2.get("score").toString())
							.compareTo(Double.valueOf(o1.get("score").toString()));
				}
			});
			Collections.sort(memberList2, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return Double.valueOf(o2.get("score").toString())
							.compareTo(Double.valueOf(o1.get("score").toString()));
				}
			});
			data.add(familyInfo);

			// 输赢奖励与惩罚
			if (i == 0) {
				// 我的俱乐部等级
				familyType = Integer.valueOf(map.get("familyType").toString());
				DicFamilyType type = DicFamilyTypeConstant.getDicFamilyType(familyType);
				win = "+" + type.getWin();
				if (type.getLoss() >= 0) {
					loss = "+" + type.getLoss();
				} else {
					loss = "" + type.getLoss();
				}
				userCount = users.size();
			}
		}

		// 状态：胜利、失败、平局
		int status = 0;
		Double double1 = Double.valueOf(data.get(0).get("familyScore").toString());
		Double double2 = Double.valueOf(data.get(1).get("familyScore").toString());
		if (double1 > double2) {
			result.put("rewardAndPunishment", win);
			result.put("status", "1");
			status = 1;
		} else if (double1 < double2) {
			result.put("rewardAndPunishment", loss);
			result.put("status", "2");
			status = 2;
		} else {
			result.put("rewardAndPunishment", "平局！");
			result.put("status", "0");
		}
		long cardinalNum = 0;
		// 段位动力基数
		if (familyType >= 120) {
			cardinalNum = 300;
		} else if (familyType >= 110) {
			cardinalNum = 250;
		} else if (familyType == 100) {
			cardinalNum = 200;
		} else if (familyType == 90) {
			cardinalNum = 150;
		} else {
			cardinalNum = 100;
		}
		long totalprizeNum = cardinalNum * userCount;// 俱乐部共获得动力
		int a = 0;
		int c = 0;
		for (int i = 0; i < memberList1.size(); i++) {
			memberList1.get(i).put("isSpectators", "0");
			if (status == 0 || status == 2) {
				memberList1.get(i).put("desc", "无奖励");
			}
			if (status == 1) {
				long powerNum = (totalprizeNum * Double.valueOf(memberList1.get(i).get("score").toString()).longValue()
						/ Double.valueOf(data.get(0).get("familyScore").toString()).longValue());
				memberList1.get(i).put("desc", "+" + powerNum);
			}
			a++;
			if (a > 4) {
				memberList1.get(i).put("isSpectators", "1");// 1 true 0 false
			}
		}
		for (int i = 0; i < memberList2.size(); i++) {
			memberList2.get(i).put("isSpectators", "0");
			memberList2.get(i).put("desc", "");
			c++;
			if (c > 4) {
				memberList2.get(i).put("isSpectators", "1");// 1 true 0 false
			}
		}
		data.get(0).put("memberList", memberList1);
		data.get(1).put("memberList", memberList2);
		result.put("myFamilyInfo", data.get(0));
		result.put("fightFamilyInfo", data.get(1));
		return result;
	}
}
