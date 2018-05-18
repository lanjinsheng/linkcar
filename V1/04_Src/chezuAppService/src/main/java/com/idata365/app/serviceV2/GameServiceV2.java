package com.idata365.app.serviceV2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.GameMapper;
import com.idata365.app.mapper.LotteryMapper;
import com.idata365.app.mapper.ScoreMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.service.BaseService;
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
	public List<Map<String, String>> billBoard() {
		List<Map<String, String>> billList = new ArrayList<>();
		String daystamp = getCurrentDayStr();
		List<Map<String, Object>> list = familyMapper.queryAllFamilyOrderNo(daystamp);
		if (ValidTools.isBlank(list)) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> bill = new HashMap<>();
			FamilyParamBean bean = new FamilyParamBean();
			bean.setFamilyId(Long.valueOf((String) list.get(i).get("familyId")));
			FamilyInfoBean familyInfo = familyMapper.queryFamilyInfo(bean);
			UsersAccount usersAccount = usersAccountMapper.findAccountById(familyInfo.getCreateUserId());
			DicFamilyType familyType = DicFamilyTypeConstant
					.getDicFamilyType(Integer.valueOf((String) list.get(i).get("familyType")));

			bill.put("rank", list.get(i).get("yesterdayOrderNo").toString());
			bill.put("name", familyInfo.getFamilyName());
			bill.put("captainOrGroupName", usersAccount.getNickName());
			bill.put("gradeOrNum", familyType.getFamilyTypeValue());
			bill.put("trophyNum", list.get(i).get("trophy").toString());
			billList.add(bill);
		}

		return billList;
	}

	// 根据家族ID获取分数、名称、奖杯数、等级
	public Map<String, String> getInfoByFamilyId(long familyId,String daystamp) {
		if(daystamp == null) {
			daystamp = getCurrentDayStr();
		}
		Map<String, String> map = familyMapper.getInfoByFamilyId(familyId, daystamp);
		if (ValidTools.isBlank(map)) {
			return null;
		}
		Map<String, String> bill = new HashMap<>();
		FamilyParamBean bean = new FamilyParamBean();
		bean.setFamilyId(Long.valueOf((String) map.get("familyId")));
		FamilyInfoBean familyInfo = familyMapper.queryFamilyInfo(bean);
		DicFamilyType familyType = DicFamilyTypeConstant
				.getDicFamilyType(Integer.valueOf((String) map.get("familyType")));

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

		FamilyInfoBean familyInfoBean = this.familyMapper.queryFamilyInfo(familyParamBean);
		if (null == familyInfoBean) {
			return null;
		}
		CompetitorFamilyInfoResultBean resultBean = new CompetitorFamilyInfoResultBean();
		resultBean.setCompetitorFamilyId(String.valueOf(familyInfoBean.getId()));
		resultBean.setFamilyName(familyInfoBean.getFamilyName());
		return resultBean;
	}

	// 查询所在家族对战信息
	public List<FamilyRelation> queryFightRecordByFamilyId(long myFamilyId) {
		FamilyRelationBean relationBean = new FamilyRelationBean();
		relationBean.setFamilyId(myFamilyId);
		relationBean.setDaystamp(getCurrentDayStr());
		List<FamilyRelation> familyRelationList = familyMapper.queryFightRecordByFamilyId(myFamilyId);
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
