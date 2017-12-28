package com.idata365.app.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.CompetitorResultBean;
import com.idata365.app.entity.FamilyCompetitorResultBean;
import com.idata365.app.entity.FamilyDriveDayStatBean;
import com.idata365.app.entity.FamilyMemberAllResultBean;
import com.idata365.app.entity.FamilyMemberBean;
import com.idata365.app.entity.FamilyMemberResultBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.entity.ScoreByDayBean;
import com.idata365.app.entity.ScoreByDayResultBean;
import com.idata365.app.entity.ScoreFamilyDetailBean;
import com.idata365.app.entity.ScoreFamilyDetailResultBean;
import com.idata365.app.entity.ScoreFamilyInfoAllBean;
import com.idata365.app.entity.ScoreFamilyInfoBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.ScoreFamilyOrderBean;
import com.idata365.app.entity.ScoreMemberInfoBean;
import com.idata365.app.entity.ScoreMemberInfoResultBean;
import com.idata365.app.entity.ScoreUserBean;
import com.idata365.app.entity.ScoreUserHistoryBean;
import com.idata365.app.entity.ScoreUserHistoryParamBean;
import com.idata365.app.entity.ScoreUserHistoryResultAllBean;
import com.idata365.app.entity.ScoreUserHistoryResultBean;
import com.idata365.app.entity.ScoreUserResultBean;
import com.idata365.app.entity.SimulationScoreResultBean;
import com.idata365.app.entity.YesterdayContributionResultBean;
import com.idata365.app.entity.YesterdayScoreBean;
import com.idata365.app.entity.YesterdayScoreResultBean;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.ScoreMapper;
import com.idata365.app.util.AdBeanUtils;
import com.idata365.app.util.RandUtils;

@Service
public class ScoreService extends BaseService<ScoreService>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(ScoreService.class);
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	@Autowired
	private FamilyMapper familyMapper;
	
	/**
	 * 发起家族、参与家族查询
	 * @param bean
	 * @return
	 */
	public ScoreFamilyInfoAllBean queryFamily(ScoreFamilyInfoParamBean bean)
	{
		ScoreFamilyInfoAllBean resultBean = new ScoreFamilyInfoAllBean();
		ScoreFamilyInfoBean oriFamilyBean = this.scoreMapper.queryFamilyByUserId(bean);
		resultBean.setOriFamily(oriFamilyBean);
		
		long origFamilyId = oriFamilyBean.getFamilyId();
		List<Long> familyIdList = this.scoreMapper.queryFamilyIds(bean);
		for (Long tempFamilyId : familyIdList)
		{
			if (tempFamilyId.longValue() != origFamilyId)
			{
				ScoreFamilyInfoParamBean tempParamBean = new ScoreFamilyInfoParamBean();
				tempParamBean.setFamilyId(tempFamilyId);
				ScoreFamilyInfoBean joinFamilyBean = this.scoreMapper.queryFamilyByFamilyId(tempParamBean);
				resultBean.setJoinFamily(joinFamilyBean);
				break;
			}
		}
		
		return resultBean;
	}
	
	/**
	 * 今日上榜家族
	 * @return
	 */
	public List<ScoreFamilyOrderBean> queryFamilyOrderInfo()
	{
		return this.scoreMapper.queryFamilyOrderInfo();
	}
	
	/**
	 * 查看家族信息
	 * @param bean
	 * @return
	 */
	public ScoreFamilyDetailResultBean queryFamilyDetail(ScoreFamilyInfoParamBean bean)
	{
		ScoreFamilyDetailBean tempBean = this.scoreMapper.queryFamilyDetail(bean);
		List<String> recordsList = this.scoreMapper.queryFamilyRecords(bean);
		ScoreFamilyDetailResultBean resultBean = new ScoreFamilyDetailResultBean();
		
		AdBeanUtils.copyOtherPropToStr(resultBean, tempBean);
		resultBean.setFamilys(recordsList);
		return resultBean;
	}
	
	/**
	 * 查看家族成员
	 * @param bean
	 * @return
	 */
	public List<ScoreMemberInfoResultBean> listFamilyMember(ScoreFamilyInfoParamBean bean)
	{
		List<ScoreMemberInfoBean> tempList = this.scoreMapper.queryMemberByFamilyId(bean);
		
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(bean.getFamilyId());
		long createUserId = this.familyMapper.queryCreateUserId(familyParamBean);
		
		List<ScoreMemberInfoResultBean> resultList = new ArrayList<>();
		
		for (ScoreMemberInfoBean tempBean : tempList)
		{
			ScoreMemberInfoResultBean tempResultBean = new ScoreMemberInfoResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			
			String name = tempBean.getName();
			if (StringUtils.isBlank(name))
			{
				String phone = tempBean.getPhone();
				String hidePhoneResult = hidePhone(phone);
				tempResultBean.setName(hidePhoneResult);
			}
			
			if (createUserId == tempBean.getUserId())
			{
				tempResultBean.setIsCaptainFlag("1");
			}
			
			String tempJoinTime = tempBean.getJoinTime();
			if (StringUtils.isNotBlank(tempJoinTime))
			{
				String formatJoinTime = formatTime(tempJoinTime);
				tempResultBean.setJoinTime(formatJoinTime);
			}
			
			resultList.add(tempResultBean);
		}
		
		return resultList;
	}

	private String formatTime(String tempJoinTime)
	{
		Date tempDate = null;
		try
		{
			tempDate = DateUtils.parseDate(tempJoinTime, DateConstant.SECOND_FORMAT_PATTERN);
		} catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
		
		String resultDateStr = DateFormatUtils.format(tempDate, "yyyy.MM.dd");
		return resultDateStr;
	}
	
	public static String hidePhone(String phone)
	{
		String prefixPhone = StringUtils.substring(phone, 0, 3);
		String suffixPhone = StringUtils.substring(phone, 7);
		
		String tempNewPhone = prefixPhone + "****" + suffixPhone;
		return tempNewPhone;
	}
	
	private static final String DAY_PATTERN = "yyyy-MM-dd";
	/**
	 * 历史得分（显示指定用户的）
	 * @param bean
	 * @return
	 */
	public ScoreUserHistoryResultAllBean listHistoryOrder(ScoreUserHistoryParamBean bean)
	{
		List<ScoreUserHistoryBean> tempList = this.scoreMapper.queryHistoryOrder(bean);
		
		Date todayDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(todayDate, -1);
		String todayStr = DateFormatUtils.format(todayDate, DAY_PATTERN);
		String yesterdayStr = DateFormatUtils.format(yesterdayDate, DAY_PATTERN);
		
		List<ScoreUserHistoryResultBean> resultList = new ArrayList<>();
		for (ScoreUserHistoryBean tempBean : tempList)
		{
			ScoreUserHistoryResultBean tempResultBean = new ScoreUserHistoryResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			String dayStr = tempBean.getDayStr();
			if (StringUtils.equals(dayStr, todayStr))
			{
				tempResultBean.setDayStr(dayStr + "（今日）");
			}
			else if (StringUtils.equals(dayStr, yesterdayStr))
			{
				tempResultBean.setDayStr(dayStr + "（昨日）");
			}
			
			resultList.add(tempResultBean);
		}
		
		int start = bean.getStart();
		int newStart = start + tempList.size();
		
		ScoreUserHistoryResultAllBean resultBean = new ScoreUserHistoryResultAllBean();
		resultBean.setHistoryScores(resultList);
		resultBean.setStart(String.valueOf(newStart));
		
		return resultBean;
	}
	
	/**
	 * 历史驾驶得分
	 * @param bean
	 * @return
	 */
	public List<ScoreByDayResultBean> getScoreByDay(ScoreUserHistoryParamBean bean)
	{
		List<ScoreByDayBean> tempList = this.scoreMapper.queryScoreByDay(bean);
		
		List<ScoreByDayResultBean> resultList = new ArrayList<>();
		for (ScoreByDayBean tempBean : tempList)
		{
			ScoreByDayResultBean resultBean = new ScoreByDayResultBean();
			AdBeanUtils.copyOtherPropToStr(resultBean, tempBean);
			resultList.add(resultBean);
		}
		
		return resultList;
	}
	
	/**
	 * !!!!drop
	 * 昨日得分
	 * @param bean
	 * @return
	 */
	public List<ScoreUserResultBean> findYesterdayScore(ScoreFamilyInfoParamBean bean)
	{
		Date todayDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(todayDate, -1);
		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, DAY_PATTERN);
		bean.setDaystamp(yesterdayDateStr);
		
		List<ScoreUserBean> tempList = this.scoreMapper.queryUserDayScoreByFamily(bean);
		List<ScoreUserResultBean> resultList = new ArrayList<>();
		for (ScoreUserBean tempBean : tempList)
		{
			ScoreUserResultBean tempResultBean = new ScoreUserResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			resultList.add(tempResultBean);
		}
		
		return resultList;
	}
	
	/**
	 * 昨日得分
	 * @param bean
	 * @return
	 */
	public List<YesterdayScoreResultBean> findYesterdayFamilyScore(ScoreFamilyInfoParamBean bean)
	{
		Date todayDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(todayDate, -1);
		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, DAY_PATTERN);
		bean.setDaystamp(yesterdayDateStr);
		
		List<YesterdayScoreBean> tempList = this.scoreMapper.queryYesterdayFamilyScore(bean);
		
		double tempTotalScore = 0;
		List<YesterdayScoreResultBean> resultList = new ArrayList<>();
		for (YesterdayScoreBean tempBean : tempList)
		{
			YesterdayScoreResultBean tempResultBean = new YesterdayScoreResultBean();
			AdBeanUtils.copyNotNullProperties(tempResultBean, tempBean);
			resultList.add(tempResultBean);
			
			tempTotalScore += tempBean.getScore();
		}
		
		int totalSize = tempList.size();
		if (totalSize > 0)
		{
			for (YesterdayScoreResultBean tempBean : resultList)
			{
				BigDecimal resultBd = BigDecimal.valueOf(NumberUtils.toDouble(tempBean.getScore())).divide(BigDecimal.valueOf(tempTotalScore), 2, BigDecimal.ROUND_HALF_UP);
				double resultD = resultBd.doubleValue();
				String percent = formattedDecimalToPercentage(resultD);
				tempBean.setPercent(percent);
			}
		}
		
		return resultList;
	}
	
	private static String formattedDecimalToPercentage(double decimal)  
    {  
        //获取格式化对象  
        NumberFormat nt = NumberFormat.getPercentInstance();  
        //设置百分数精确度2即保留两位小数  
        nt.setMinimumFractionDigits(2);  
        return nt.format(decimal);  
    }  
	
	//
	public List<YesterdayContributionResultBean> familyContribution(ScoreFamilyInfoParamBean bean)
	{
		
		Date todayDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(todayDate, -1);
		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, DAY_PATTERN);
		bean.setDaystamp(yesterdayDateStr);
		
		List<YesterdayScoreBean> tempList = this.scoreMapper.queryYesterdayFamilyScore(bean);
		
		List<YesterdayContributionResultBean> resultList = new ArrayList<>();
		for (YesterdayScoreBean tempBean : tempList)
		{
			YesterdayContributionResultBean tempResultBean = new YesterdayContributionResultBean();
			AdBeanUtils.copyNotNullProperties(tempResultBean, tempBean);
			//temp settings contribution start
			tempResultBean.setContribution(String.valueOf(tempBean.getScore()));
			//temp settings end
			resultList.add(tempResultBean);
		}
		
		return resultList;
	}
	
	/**
	 * 获得家族及成员昨日理论得分
	 * @param bean
	 * @return
	 */
	public FamilyMemberAllResultBean generateYesterdayFamilyScore(ScoreFamilyInfoParamBean bean)
	{
		FamilyMemberAllResultBean resultBean = new FamilyMemberAllResultBean();
		
		List<FamilyMemberBean> tempList = this.scoreMapper.queryYesterdayMemberScore(bean);
		
		List<FamilyMemberResultBean> scoreList = new ArrayList<>();
		for (FamilyMemberBean tempBean : tempList)
		{
			FamilyMemberResultBean tempResultBean = new FamilyMemberResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			//temp settings  percent score supposeRole start
			tempResultBean.setPercent("20%");
			tempResultBean.setSupposeRole(String.valueOf(RandUtils.generateRand(1, 5)));
			//temp settings  percent score supposeRole end
			scoreList.add(tempResultBean);
		}
		
		//temp settings start
		resultBean.setSimulationScore("83");
		resultBean.setScoreArr(scoreList);
		//temp settings end
		return resultBean;
	}
	
	/**
	 * 昨日赛果
	 * @param bean
	 * @return
	 */
	public CompetitorResultBean showGameResult(ScoreFamilyInfoParamBean bean)
	{
		Date todayDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(todayDate, -1);
		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, DAY_PATTERN);
		
		long familyId = bean.getFamilyId();
		
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(familyId);
		
		FamilyResultBean selfFamilybean = this.familyMapper.queryFamilyById(familyParamBean);
		
		FamilyCompetitorResultBean gameObj = new FamilyCompetitorResultBean();
		gameObj.setFamilyId(String.valueOf(selfFamilybean.getMyFamilyId()));
		gameObj.setName(selfFamilybean.getMyFamilyName());
		gameObj.setImgUrl(selfFamilybean.getMyFamilyImgUrl());
		
		ScoreFamilyInfoParamBean gameObjParamBean = new ScoreFamilyInfoParamBean();
		gameObjParamBean.setFamilyId(familyId);
		gameObjParamBean.setDaystamp(yesterdayDateStr);
		FamilyDriveDayStatBean gameObjFamilyStatBean = this.scoreMapper.queryFamilyDriveStat(gameObjParamBean);
		AdBeanUtils.copyOtherPropToStr(gameObj, gameObjFamilyStatBean);
		//temp settsing competitingResult orderNo==========start
		gameObj.setCompetitingResult("FAILURE");
		gameObj.setOrderNo("10");
		
		FamilyRelationBean relationParamBean = new FamilyRelationBean();
		relationParamBean.setFamilyId(familyId);
		relationParamBean.setDaystamp(getCurrentDayStr());
		List<FamilyRelationBean> familyRelationList = this.familyMapper.queryFamilyIdByCompetitorId(relationParamBean);
		FamilyRelationBean familyRelationBean = familyRelationList.get(0);
		
		long competitorFamilyId;
		long familyId1 = familyRelationBean.getFamilyId1();
		if (familyId == familyId1)
		{
			competitorFamilyId = familyRelationBean.getFamilyId2();
		}
		else
		{
			competitorFamilyId = familyId1;
		}
		familyParamBean.setFamilyId(competitorFamilyId);
		FamilyResultBean competitorFamilybean = this.familyMapper.queryFamilyById(familyParamBean);
		FamilyCompetitorResultBean competitorObj = new FamilyCompetitorResultBean();
		competitorObj.setFamilyId(String.valueOf(competitorFamilybean.getMyFamilyId()));
		competitorObj.setName(competitorFamilybean.getMyFamilyName());
		competitorObj.setImgUrl(competitorFamilybean.getMyFamilyImgUrl());
		
		ScoreFamilyInfoParamBean competitorObjParamBean = new ScoreFamilyInfoParamBean();
		competitorObjParamBean.setFamilyId(competitorFamilybean.getMyFamilyId());
		competitorObjParamBean.setDaystamp(yesterdayDateStr);
		FamilyDriveDayStatBean competitorObjFamilyStatBean = this.scoreMapper.queryFamilyDriveStat(competitorObjParamBean);
		AdBeanUtils.copyOtherPropToStr(competitorObj, competitorObjFamilyStatBean);
		//temp settsing competitingResult orderNo==========start
		competitorObj.setCompetitingResult("SUCCESS");
		competitorObj.setOrderNo("15");
		
		CompetitorResultBean resultBean = new CompetitorResultBean();
		resultBean.setGameObj(gameObj);
		resultBean.setCompetitorObj(competitorObj);
		
		return resultBean;
	}
	
	public List<SimulationScoreResultBean> generateYesterdaySimulationScore(ScoreFamilyInfoParamBean bean)
	{
		List<SimulationScoreResultBean> resultList = new ArrayList<>();
		
		//temp settsing score roleId
		for (int i = 1; i <= 7; i++)
		{
			SimulationScoreResultBean tempBean0 = new SimulationScoreResultBean();
			tempBean0.setRoleId(String.valueOf(i));
			tempBean0.setScore("50");
			resultList.add(tempBean0);
		}
		
		return resultList;
	}
	
	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}
}
