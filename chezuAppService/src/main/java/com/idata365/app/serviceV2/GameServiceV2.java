package com.idata365.app.serviceV2;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.annotations.Param;
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
import com.idata365.app.entity.GameFamilyParamBean;
import com.idata365.app.entity.ScoreFamilyInfoAllBean;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.GameMapper;
import com.idata365.app.mapper.LotteryMapper;
import com.idata365.app.mapper.ScoreMapper;
import com.idata365.app.mapper.UsersAccountMapper;
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
	private LotteryMapper lotteryMapper;

	@Autowired
	private ScoreMapper scoreMapper;

	@Autowired
	private UsersAccountMapper usersAccountMapper;

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
				Map<String, Object> map = familyMapper.queryFamilyByFId(familyId);
				if (map != null) {
					list.add(map);
				}
			}
			if (ValidTools.isNotBlank(queryFamily.getJoinFamily())) {
				familyId = queryFamily.getJoinFamily().getFamilyId();
				Map<String, Object> map = familyMapper.queryFamilyByFId(familyId);
				if (map != null) {
					list.add(map);
				}
			}
		}
		if (ValidTools.isBlank(list)) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> bill = new HashMap<>();
			UsersAccount usersAccount = usersAccountMapper
					.findAccountById(Long.valueOf(list.get(i).get("createUserId").toString()));
			DicFamilyType familyType = DicFamilyTypeConstant
					.getDicFamilyType(Integer.valueOf(list.get(i).get("familyType").toString()));
			bill.put("rank",
					String.valueOf(familyMapper.queryFamilyOrderByFId(Long.valueOf(list.get(i).get("id").toString()))));
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
		bill.put("trophyNum", map.get("trophy").toString());
		return bill;
	}

	// 查询正在对战的家族系信息
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

	private String getCurrentDayStr() {
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}

}
