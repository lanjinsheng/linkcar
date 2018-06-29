package com.idata365.app.serviceV2;

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
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.GameMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.remote.ChezuAccountService;
import com.idata365.app.service.BaseService;
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
	
	

	/**
	 * 实时榜单
	 * 
	 * @param bean
	 * @return
	 */
	public List<Map<String, String>> billBoard(ScoreFamilyInfoAllBean queryFamily) {

		List<Map<String, String>> billList = new ArrayList<>();
		List<Map<String, Object>> list = familyMapper.queryAllFamilyOrderNo();
		long familyId = 0;
		// 加入自己家族排名信息
		if (ValidTools.isNotBlank(queryFamily)) {
			if (ValidTools.isNotBlank(queryFamily.getOriFamily())) {
				familyId = queryFamily.getOriFamily().getFamilyId();
				Map<String, Object> map = familyMapper.queryFamilyOrderNoByFamilyId(familyId);
				if (map != null) {
					list.add(map);
				}
			}
			if (ValidTools.isNotBlank(queryFamily.getJoinFamily())) {
				familyId = queryFamily.getJoinFamily().getFamilyId();
				Map<String, Object> map = familyMapper.queryFamilyOrderNoByFamilyId(familyId);
				if (map != null) {
					list.add(map);
				}
			}
		}
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

	// 根据家族ID获取分数、名称、奖杯数、等级
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

	// 根据时间查询正在对战的家族系信息
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

	// 查询所在家族对战信息
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

	// 根据时间获取家族分数
	public FamilyDriveDayStat queryFamilyScore(long familyId, String daystamp) {
		return gameMapper.queryFamilyScore(familyId, daystamp);
	}

	// 获取家族实时排名
	public String queryFamilyOrderByFId(long familyId) {
		return String.valueOf(familyMapper.queryFamilyOrderByFId(familyId)).toString();
	}

	private String getCurrentDayStr() {
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}

	public List<Map<String, Object>> newMemberRankList(int familyType, int status,
			List<Map<String, Object>> userRankList, long myFamilyId, Long fightFamilyId, String daystamp, long score1, long score2) {
		LOGGER.info("familyType=="+familyType+"==status=="+status+"==userRankList.size()=="+userRankList.size()+"==myFamilyId=="+myFamilyId+"==fightFamilyId=="+fightFamilyId+"==score1=="+score1);
		long cardinalNum = 0;
		//段位动力基数
		if (familyType >= 120) {
			cardinalNum = 300;
		} else if (familyType >= 110) {
			cardinalNum = 250;
		}else if (familyType == 100) {
			cardinalNum = 200;
		}else if (familyType == 90) {
			cardinalNum = 150;
		}else {
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
		
		//根据时间获取我家族的人数
		String sign = "";
		String ids = chezuAccountService.getCurrentUsersByFamilyId(myFamilyId, daystamp, sign);
		String [] count = ids.split(",");
		int userCount = count.length;
		long totalprizeNum = cardinalNum * userCount;// 家族共获得动力
		
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
					return Double.valueOf(o1.get("isMyFamilyFlag").toString()).compareTo(Double.valueOf(o2.get("isMyFamilyFlag").toString()));
				}
			});
		} else if (status == 1) {
			// 胜利
			for (int i = 0; i < userRankList.size(); i++) {
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					userRankList.get(i).put("isMyFamilyFlag", "1");
					userRankList.get(i).put("rank", map.get(i+1));
					long powerNum = (totalprizeNum * Double.valueOf(userRankList.get(i).get("score").toString()).longValue() / score1);
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
					return Double.valueOf(o1.get("isMyFamilyFlag").toString()).compareTo(Double.valueOf(o2.get("isMyFamilyFlag").toString()));
				}
			});
			for (int i = 0; i < userRankList.size(); i++) {
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					userRankList.get(i).put("rank", map.get(i+1));
				} else {
					userRankList.get(i).put("rank", "失败");
				}
			}
		} else if (status == 2){
			// 失败
			for (int i = 0; i < userRankList.size(); i++) {
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					userRankList.get(i).put("isMyFamilyFlag", "2");
					userRankList.get(i).put("rank", "失败");
					userRankList.get(i).put("desc", "无奖励");
				} else {
					userRankList.get(i).put("isMyFamilyFlag", "1");
					userRankList.get(i).put("rank", map.get(i+1));
					userRankList.get(i).put("desc", "被挑战俱乐部成员");
				}
				userRankList.get(i).put("isSpectators", "0");// 1 true 2 false
			}
			Collections.sort(userRankList, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return Double.valueOf(o1.get("isMyFamilyFlag").toString()).compareTo(Double.valueOf(o2.get("isMyFamilyFlag").toString()));
				}
			});
			for (int i = 0; i < userRankList.size(); i++) {
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					userRankList.get(i).put("rank", "失败");
				} else {
					userRankList.get(i).put("rank", map.get(i+1));
				}
			}
		}else if (status == 3){
			// 正在对战
			for (int i = 0; i < userRankList.size(); i++) {
				if (Long.valueOf(userRankList.get(i).get("familyId").toString()) == myFamilyId) {
					long d = Double.valueOf(userRankList.get(i).get("score").toString()).longValue();
					long powerNum =(totalprizeNum * d) / score1;
					userRankList.get(i).put("desc", "挑战获胜后可获得+" + powerNum);
				} else {
					userRankList.get(i).put("desc", "被挑战俱乐部成员");
				}
				if(i>=9) {
					userRankList.get(i).put("rank", "观战");
					userRankList.get(i).put("isSpectators", "1");// 1 true 2 false
				}else {
					userRankList.get(i).put("rank", map.get(i+1));
					userRankList.get(i).put("isSpectators", "0");// 1 true 2 false
				}
				userRankList.get(i).put("isMyFamilyFlag", "0");
				
			}
			Collections.sort(userRankList, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return Double.valueOf(o1.get("isMyFamilyFlag").toString()).compareTo(Double.valueOf(o2.get("isMyFamilyFlag").toString()));
				}
			});
		}
		

		return userRankList;
	}

}
