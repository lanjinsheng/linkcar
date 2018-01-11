package com.idata365.app.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
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
import com.idata365.app.entity.GameHistoryResultBean;
import com.idata365.app.entity.GameResultWithFamilyResultBean;
import com.idata365.app.entity.ScoreByDayBean;
import com.idata365.app.entity.ScoreByDayResultBean;
import com.idata365.app.entity.ScoreDetailBean;
import com.idata365.app.entity.ScoreDetailUnitBean;
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
import com.idata365.app.entity.TravelDetailResultBean;
import com.idata365.app.entity.UserTravelHistoryBean;
import com.idata365.app.entity.UserTravelHistoryDetailBean;
import com.idata365.app.entity.UserTravelHistoryResultBean;
import com.idata365.app.entity.YesterdayContributionResultBean;
import com.idata365.app.entity.YesterdayScoreBean;
import com.idata365.app.entity.YesterdayScoreResultBean;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.ScoreMapper;
import com.idata365.app.util.AdBeanUtils;
import com.idata365.app.util.PhoneUtils;
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
				String hidePhoneResult = PhoneUtils.hidePhone(phone);
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
		
		ScoreUserHistoryResultBean todayResultBean = new ScoreUserHistoryResultBean();
		int currentRole = this.scoreMapper.queryCurrentRole(bean);
		todayResultBean.setRole(String.valueOf(currentRole));
		todayResultBean.setDayStr(todayStr + "(今日)");
		todayResultBean.setScore("暂无评分");
		
		List<ScoreUserHistoryResultBean> resultList = new ArrayList<>();
		resultList.add(todayResultBean);
		for (ScoreUserHistoryBean tempBean : tempList)
		{
			ScoreUserHistoryResultBean tempResultBean = new ScoreUserHistoryResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			String dayStr = tempBean.getDayStr();
			
			String tempDayStr = formatDayStr(dayStr);
			
			if (StringUtils.equals(tempDayStr, todayStr))
			{
//				tempResultBean.setDayStr(tempDayStr + "(今日)");
				continue;
			}
			else if (StringUtils.equals(dayStr, yesterdayStr))
			{
				tempResultBean.setDayStr(dayStr + "(昨日)");
			}
			else
			{
				tempResultBean.setDayStr(tempDayStr);
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

	private String formatDayStr(String dayStr)
	{
		Date tempDate = null;
		try
		{
			tempDate = DateUtils.parseDate(dayStr, "yyyyMMdd");
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tempDayStr = DateFormatUtils.format(tempDate, "yyyy-MM-dd");
		return tempDayStr;
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
		CompetitorResultBean resultBean = new CompetitorResultBean();
		
		Date todayDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(todayDate, -1);
		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, "yyyy-MM-dd");
		
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
		if (null == gameObjFamilyStatBean)
			return resultBean;
		AdBeanUtils.copyOtherPropToStr(gameObj, gameObjFamilyStatBean);
		//temp settsing competitingResult ord erNo==========start
//		gameObj.setCompetitingResult("FAILURE");
//		gameObj.setOrderNo("10");
		
		resultBean.setGameObj(gameObj);
		
		FamilyRelationBean relationParamBean = new FamilyRelationBean();
		relationParamBean.setFamilyId(familyId);
		relationParamBean.setDaystamp(getCurrentDayStr());
		List<FamilyRelationBean> familyRelationList = this.familyMapper.queryFamilyIdByCompetitorId(relationParamBean);
		if (CollectionUtils.isEmpty(familyRelationList))
		{
			return resultBean;
		}
		
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
//		competitorObj.setCompetitingResult("SUCCESS");
//		competitorObj.setOrderNo("15");
		
		double myFamilyScore = gameObjFamilyStatBean.getScore();
		double competitorScore = competitorObjFamilyStatBean.getScore();
		if (myFamilyScore > competitorScore)
		{
			gameObj.setCompetitingResult("SUCCESS");
			competitorObj.setCompetitingResult("FAILURE");
		}
		else if (myFamilyScore == competitorScore)
		{
			gameObj.setCompetitingResult("SAME_SCORE");
			competitorObj.setCompetitingResult("SAME_SCORE");
		}
		else
		{
			gameObj.setCompetitingResult("FAILURE");
			competitorObj.setCompetitingResult("SUCCESS");
		}
		
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
	
	public List<UserTravelHistoryResultBean> showTravels(ScoreFamilyInfoParamBean bean)
	{
		List<UserTravelHistoryBean> travelList = this.scoreMapper.queryTravels(bean);
		List<UserTravelHistoryResultBean> resultList = new ArrayList<>();
		
		for (UserTravelHistoryBean tempBean : travelList)
		{
			UserTravelHistoryResultBean tempResultBean = new UserTravelHistoryResultBean();
			tempResultBean.setTravelId(String.valueOf(tempBean.getTravelId()));
			tempResultBean.setHabitId(String.valueOf(tempBean.getHabitId()));
			
			int time = tempBean.getTime();
			double mileage = tempBean.getMileage();
			String startTime = tempBean.getStartTime();
			String endTime = tempBean.getEndTime();
			
			String timeStr = DurationFormatUtils.formatDuration(time*1000L, "HH:mm:ss");
			tempResultBean.setTime(timeStr);
			
			double mileageD = BigDecimal.valueOf(mileage).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
			tempResultBean.setMileage(String.valueOf(mileageD));
			
			String startTimeStr = grabHourStr(startTime);
			String endTimeStr = grabHourStr(endTime);
			tempResultBean.setStartToEnd(startTimeStr + "-" + endTimeStr);
			
			resultList.add(tempResultBean);
		}
		
		return resultList;
	}
	
	//从yyyy-MM-dd HH:mm:ss中抓取 HH:mm
	private static String grabHourStr(String ts)
	{
		Date date = null;
		try
		{
			date = DateUtils.parseDate(ts, "yyyy-MM-dd HH:mm:ss.SSS");
		} catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
		String hourStr = DateFormatUtils.format(date, "HH:mm");
		return hourStr;
	}
	
	public List<TravelDetailResultBean> showTravelDetail(ScoreFamilyInfoParamBean bean)
	{
//		TravelDetailResultBean bean1 = new TravelDetailResultBean();
//		bean1.setTime("60:12:12");
//		bean1.setMileage("60.87");
//		bean1.setNightDrive("3.5");
//		bean1.setTurnTimes("5");
//		bean1.setTiredDrive("0.5");
//		bean1.setSpeedTimes("4");
//		bean1.setBrakeTimes("3");
//		bean1.setMaxspeed("102");
//		bean1.setOverspeedTimes("4");
		
		UserTravelHistoryDetailBean tempBean = this.scoreMapper.queryTravelDetail(bean);
		
		TravelDetailResultBean tempResultBean = new TravelDetailResultBean();
		
		int time = tempBean.getTime();
		String timeStr = DurationFormatUtils.formatDuration(time*1000L, "HH:mm:ss");
		tempResultBean.setTime(timeStr);
		
		double mileage = tempBean.getMileage();
		double mileageD = BigDecimal.valueOf(mileage).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
		tempResultBean.setMileage(String.valueOf(mileageD));
		
		long nightDrive = (long)tempBean.getNightDrive();
		String nightDriveStr = DurationFormatUtils.formatDuration(nightDrive*1000L, "HH:mm:ss");
		tempResultBean.setNightDrive(nightDriveStr);
		
		tempResultBean.setTurnTimes(String.valueOf(tempBean.getTurnTimes()));
		
		long tiredDrive = (long)tempBean.getTiredDrive();
		String tiredDriveStr = DurationFormatUtils.formatDuration(tiredDrive*1000L, "HH:mm:ss");
		tempResultBean.setTiredDrive(tiredDriveStr);
		
		tempResultBean.setSpeedTimes(String.valueOf(tempBean.getSpeedTimes()));
		
		tempResultBean.setBrakeTimes(String.valueOf(tempBean.getBrakeTimes()));
		
		double maxspeed = tempBean.getMaxspeed();
		String maxspeedStr = BigDecimal.valueOf(maxspeed).multiply(BigDecimal.valueOf(3600)).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP).toString();
		tempResultBean.setMaxspeed(maxspeedStr);
		
		tempResultBean.setOverspeedTimes(String.valueOf(tempBean.getOverspeedTimes()));
		
		List<TravelDetailResultBean> resultList = new ArrayList<>();
		resultList.add(tempResultBean);
		
		return resultList;
	}
	
	private static final String VALUE_TEMPLATE = "STR1\n使用LOTTERY：STR2次";
	
	public List<ScoreDetailUnitBean> scoreDetail(ScoreFamilyInfoParamBean bean)
	{
		List<ScoreDetailBean> detailList = this.scoreMapper.queryScoreDetail(bean);
		
		ScoreDetailBean tempBean = detailList.get(0);
		
		List<ScoreDetailUnitBean> resultList = new ArrayList<>();
		
		ScoreDetailUnitBean mileageBean = new ScoreDetailUnitBean();
		mileageBean.setFactor("MILEAGE");
		mileageBean.setValue(String.valueOf(tempBean.getMileage()));
		mileageBean.setWeight(formattedDecimalToPercentage(tempBean.getMileageProportion()));
		mileageBean.setScore(String.valueOf(tempBean.getMileageScore()));
		resultList.add(mileageBean);
		
		ScoreDetailUnitBean brakeTimesBean = new ScoreDetailUnitBean();
		brakeTimesBean.setFactor("BRAKE_TIMES");
		String brakeTimes1 = String.valueOf(tempBean.getBrakeTimes()) + "次";
		String brakeTimes2 = String.valueOf(tempBean.getUseShachepian());
		String brakeTottery = "刹车片";
		String brakeValue = formatDetailValue(brakeTimes1, brakeTimes2, brakeTottery);
		brakeTimesBean.setValue(brakeValue);
		brakeTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getBrakeTimesProportion()));
		brakeTimesBean.setScore(String.valueOf(tempBean.getBrakeTimesScore()));
		resultList.add(brakeTimesBean);
		
		ScoreDetailUnitBean turnTimesBean = new ScoreDetailUnitBean();
		turnTimesBean.setFactor("TURN_TIMES");
		String turnTimes1 = String.valueOf(tempBean.getTurnTimes());
		String turnTimes2 = String.valueOf(tempBean.getUseCheluntai());
		String turnTottery = "车轮胎";
		String turnValue = formatDetailValue(turnTimes1 + "次", turnTimes2, turnTottery);
		turnTimesBean.setValue(turnValue);
		turnTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getTurnTimesProportion()));
		turnTimesBean.setScore(String.valueOf(tempBean.getTurnTimesScore()));
		resultList.add(turnTimesBean);
		
		ScoreDetailUnitBean overspeedTimesBean = new ScoreDetailUnitBean();
		overspeedTimesBean.setFactor("OVERSPEED_TIMES");
		String overspeedTimes1 = String.valueOf(tempBean.getOverspeedTimes());
		String zengyaqiTimes = String.valueOf(tempBean.getUseZengyaqi());
		String overspeedTottery = "增压器";
		String overspeedValue = formatDetailValue(overspeedTimes1 + "次", zengyaqiTimes, overspeedTottery);
		overspeedTimesBean.setValue(overspeedValue);
		overspeedTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getOverspeedTimesProportion()));
		overspeedTimesBean.setScore(String.valueOf(tempBean.getOverspeedTimesScore()));
		resultList.add(overspeedTimesBean);
		
		ScoreDetailUnitBean nightDriveTimesBean = new ScoreDetailUnitBean();
		nightDriveTimesBean.setFactor("NIGHTDRIVE_TIMES");
		String nightDriveTimes1 = String.valueOf(tempBean.getNightDriveTimes());
		String yeshijingTimes = String.valueOf(tempBean.getUseYeshijing());
		String yeshijingTottery = "夜视镜";
		String yeshijingValue = formatDetailValue(nightDriveTimes1 + "次", yeshijingTimes, yeshijingTottery);
		nightDriveTimesBean.setValue(yeshijingValue);
		nightDriveTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getNightDriveTimesProportion()));
		nightDriveTimesBean.setScore(String.valueOf(tempBean.getNightDriveScore()));
		resultList.add(nightDriveTimesBean);
		
		ScoreDetailUnitBean tiredDriveTimesBean = new ScoreDetailUnitBean();
		tiredDriveTimesBean.setFactor("TIRED_DRIVE_TIMES");
		String tiredDriveTimesStr = String.valueOf(tempBean.getTiredDriveTimes());
		String hongniuTimes = String.valueOf(tempBean.getUseHongniu());
		String hongniuTottery = "红牛";
		String tiredDriveTimesValue = formatDetailValue(tiredDriveTimesStr + "次", hongniuTimes, hongniuTottery);
		tiredDriveTimesBean.setValue(tiredDriveTimesValue);
		tiredDriveTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getTiredDriveProportion()));
		tiredDriveTimesBean.setScore(String.valueOf(tempBean.getTiredDriveScore()));
		resultList.add(tiredDriveTimesBean);
		
		ScoreDetailUnitBean illegalStopTimesBean = new ScoreDetailUnitBean();
		illegalStopTimesBean.setFactor("illegal_Stop_Times");
		String illegalStopTimesStr = String.valueOf(tempBean.getIllegalStopTimes());
		illegalStopTimesBean.setValue(illegalStopTimesStr);
		illegalStopTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getIllegalStopTimesProportion()));
		illegalStopTimesBean.setScore(String.valueOf(tempBean.getIllegalStopTimesScore()));
		resultList.add(illegalStopTimesBean);
		
		ScoreDetailUnitBean phoneTimesBean = new ScoreDetailUnitBean();
		phoneTimesBean.setFactor("PHONE_TIMES");
		phoneTimesBean.setValue(String.valueOf(tempBean.getPhoneTimes()));
		phoneTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getPhoneTimesProportion()));
		phoneTimesBean.setScore(String.valueOf(tempBean.getPhoneTimesScore()));
		resultList.add(phoneTimesBean);
		
		ScoreDetailUnitBean mazhaTimesBean = new ScoreDetailUnitBean();
		mazhaTimesBean.setFactor("MAZHA_TIMES");
		mazhaTimesBean.setValue(String.valueOf(tempBean.getUseMazha()) + "次");
		mazhaTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getMazhaProportion()));
		mazhaTimesBean.setScore(String.valueOf(tempBean.getMazhaScore()));
		resultList.add(mazhaTimesBean);
		
		ScoreDetailUnitBean maxspeedBean = new ScoreDetailUnitBean();
		maxspeedBean.setFactor("MAX_SPEED");
		double maxspeed = tempBean.getMaxspeed();
		BigDecimal maxspeedBd = BigDecimal.valueOf(maxspeed).multiply(BigDecimal.valueOf(3600)).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP);
		String maxspeedStr = maxspeedBd.toString() + "km/h";
		String fadongjiTimes = String.valueOf(tempBean.getUseFadongji());
		String fadongjiTottery = "发动机";
		String maxspeedValue = formatDetailValue(maxspeedStr, fadongjiTimes, fadongjiTottery);
		maxspeedBean.setValue(maxspeedValue);
		maxspeedBean.setWeight(formattedDecimalToPercentage(tempBean.getMaxspeedProportion()));
		maxspeedBean.setScore(String.valueOf(tempBean.getMaxspeedScore()));
		resultList.add(maxspeedBean);
		
		ScoreDetailUnitBean extraPlusBean = new ScoreDetailUnitBean();
		extraPlusBean.setFactor("EXTRA_PLUS");
		int extraPlus = tempBean.getExtraPlus();
		extraPlusBean.setValue(String.valueOf(extraPlus));
		extraPlusBean.setWeight(formattedDecimalToPercentage(tempBean.getExtraPlusProportion()));
		extraPlusBean.setScore(String.valueOf(tempBean.getExtraPlusScore()));
		resultList.add(extraPlusBean);
		
		return resultList;
	}

	private String formatDetailValue(String times1, String times2, String lottery)
	{
		String brakeValue = StringUtils.replaceEach(VALUE_TEMPLATE, new String[]{"STR1", "STR2", "LOTTERY"}, new String[]{times1, times2, lottery});
		return brakeValue;
	}
	
	//temp settings
	public List<GameResultWithFamilyResultBean> showGameResultWithFamily(ScoreFamilyInfoParamBean bean)
	{
		List<GameResultWithFamilyResultBean> resultList = new ArrayList<>();
		
		GameResultWithFamilyResultBean bean0 = new GameResultWithFamilyResultBean();
		bean0.setTodayRole("1");
		bean0.setNameUrl("http://www.baidu.com/1.jpg");
		bean0.setName("xiaomao");
		bean0.setUserId("5");
		bean0.setScore("55");
		bean0.setRole("3");
		bean0.setTomorrowRole("4");
		resultList.add(bean0);
		
		GameResultWithFamilyResultBean bean1 = new GameResultWithFamilyResultBean();
		bean1.setTodayRole("1");
		bean1.setNameUrl("http://www.baidu.com/1.jpg");
		bean1.setName("xiaomao");
		bean1.setUserId("5");
		bean1.setScore("55");
		bean1.setRole("3");
		bean1.setTomorrowRole("4");
		resultList.add(bean1);
		
		GameResultWithFamilyResultBean bean2 = new GameResultWithFamilyResultBean();
		bean2.setTodayRole("1");
		bean2.setNameUrl("http://www.baidu.com/1.jpg");
		bean2.setName("xiaomao");
		bean2.setUserId("5");
		bean2.setScore("55");
		bean2.setRole("3");
		bean2.setTomorrowRole("4");
		resultList.add(bean2);
		
		return resultList;
	}
	
	//temp settings
	public List<GameHistoryResultBean> gameHistory(ScoreFamilyInfoParamBean bean)
	{
		List<GameHistoryResultBean> resultList = new ArrayList<>();
		
		GameHistoryResultBean bean1 = new GameHistoryResultBean();
		bean1.setOrderNo("10");
		bean1.setDaystamp("2017-12-01");
		resultList.add(bean1);
		
		GameHistoryResultBean bean2 = new GameHistoryResultBean();
		bean2.setOrderNo("10");
		bean2.setDaystamp("2017-12-01");
		resultList.add(bean2);
		
		GameHistoryResultBean bean3 = new GameHistoryResultBean();
		bean3.setOrderNo("10");
		bean3.setDaystamp("2017-12-01");
		resultList.add(bean3);
		
		return resultList;
	}
	
	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}
}
