package com.idata365.app.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.idata365.app.entity.FamilyInfoBean;
import com.idata365.app.entity.FamilyMemberAllResultBean;
import com.idata365.app.entity.FamilyMemberBean;
import com.idata365.app.entity.FamilyMemberResultBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.entity.FamilyScoreBean;
import com.idata365.app.entity.GameHistoryBean;
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
import com.idata365.app.entity.ScoreFamilyOrderResultBean;
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
import com.idata365.app.entity.UserDetailResultBean;
import com.idata365.app.entity.UserFamilyRoleLogBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.UserRoleLog;
import com.idata365.app.entity.UserTravelHistoryBean;
import com.idata365.app.entity.UserTravelHistoryDetailBean;
import com.idata365.app.entity.UserTravelHistoryResultBean;
import com.idata365.app.entity.UsersAccountBean;
import com.idata365.app.entity.YesterdayContributionResultBean;
import com.idata365.app.entity.YesterdayScoreBean;
import com.idata365.app.entity.YesterdayScoreResultBean;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.GameMapper;
import com.idata365.app.mapper.ScoreMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.util.AdBeanUtils;
import com.idata365.app.util.PhoneUtils;
import com.idata365.app.util.RandUtils;

@Service
public class ScoreService extends BaseService<ScoreService>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(ScoreService.class);
	
	@Autowired
	private GameMapper gameMapper;
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	@Autowired
	private FamilyMapper familyMapper;
	
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	UserRoleLogService  userRoleLogService;
	/**
	 * 
	    * @Title: intiScoreFamilyInfoBeanByUser
	    * @Description: TODO(初始化家族view数据)
	    * @param @param userId
	    * @param @return    参数
	    * @return ScoreFamilyInfoBean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	private ScoreFamilyInfoBean intiScoreFamilyInfoBeanByUser(long userId) {
		ScoreFamilyInfoBean scoreFamilyInfoBean=new ScoreFamilyInfoBean();
		Map<String,Object> family=usersAccountMapper.getFamilyByUserId(userId);
		scoreFamilyInfoBean.setAwardType(0);
		scoreFamilyInfoBean.setBeforeYesterdayOrderNo(0);
		scoreFamilyInfoBean.setCreateUserId(userId);
		if(family!=null) {
			scoreFamilyInfoBean.setFamilyId(Long.valueOf(family.get("id").toString()));
			scoreFamilyInfoBean.setImgUrl(String.valueOf(family.get("imgUrl")));
			scoreFamilyInfoBean.setName(String.valueOf(family.get("familyName")));
		}else {
			scoreFamilyInfoBean.setFamilyId(0);
			scoreFamilyInfoBean.setImgUrl(null);
			scoreFamilyInfoBean.setName("");
		}
		scoreFamilyInfoBean.setOrderChange(0);
		scoreFamilyInfoBean.setScore(0);
		scoreFamilyInfoBean.setOrderNo(0);
		scoreFamilyInfoBean.setYesterdayScore(0);
		return scoreFamilyInfoBean;
	}
	/**
	 * 
	    * @Title: intiScoreFamilyInfoBeanByFamily
	    * @Description: TODO(初始化家族view数据)
	    * @param @param familyId
	    * @param @return    参数
	    * @return ScoreFamilyInfoBean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	private ScoreFamilyInfoBean intiScoreFamilyInfoBeanByFamily(long familyId) {
		ScoreFamilyInfoBean scoreFamilyInfoBean=new ScoreFamilyInfoBean();
		Map<String,Object> family=usersAccountMapper.getFamilyByFamilyId(familyId);
		scoreFamilyInfoBean.setAwardType(0);
		scoreFamilyInfoBean.setBeforeYesterdayOrderNo(0);
		scoreFamilyInfoBean.setFamilyId(familyId);
		if(family!=null) {
			scoreFamilyInfoBean.setCreateUserId(Long.valueOf(family.get("createUserId").toString()));
			scoreFamilyInfoBean.setImgUrl(String.valueOf(family.get("imgUrl")));
			scoreFamilyInfoBean.setName(String.valueOf(family.get("familyName")));
		}else {
			scoreFamilyInfoBean.setCreateUserId(0);
			scoreFamilyInfoBean.setImgUrl(null);
			scoreFamilyInfoBean.setName("");	
		}
		scoreFamilyInfoBean.setOrderChange(0);
		scoreFamilyInfoBean.setScore(0);
		scoreFamilyInfoBean.setOrderNo(0);
		scoreFamilyInfoBean.setYesterdayScore(0);
		return scoreFamilyInfoBean;
	}
	/**
	 * 发起家族、参与家族查询
	 * @param bean
	 * @return
	 */
	public ScoreFamilyInfoAllBean queryFamily(ScoreFamilyInfoParamBean bean)
	{
		ScoreFamilyInfoAllBean resultBean = new ScoreFamilyInfoAllBean();
		bean.setTimeStr(getYesterdayDateStr());
		ScoreFamilyInfoBean oriFamilyBean = this.scoreMapper.queryFamilyByUserId(bean);
		if(oriFamilyBean==null) {//发起家族为今日创建
			oriFamilyBean=intiScoreFamilyInfoBeanByUser(bean.getUserId());
		}else {
			ScoreFamilyInfoParamBean oriYesterdayParamBean = new ScoreFamilyInfoParamBean();
			oriYesterdayParamBean.setFamilyId(oriFamilyBean.getFamilyId());
			oriYesterdayParamBean.setDaystamp(getYesterdayDateStr());
			FamilyDriveDayStatBean oriYeterdayFamilyResultBean = this.scoreMapper.queryFamilyDriveStat(oriYesterdayParamBean);
			
			oriFamilyBean.setYesterdayScore(oriYeterdayFamilyResultBean.getScore());
			
			int orderNo = oriFamilyBean.getOrderNo();
			int beforeYesterdayOrderNo = oriFamilyBean.getBeforeYesterdayOrderNo();
			int oriFamilyOrderChange;
			if (0 == beforeYesterdayOrderNo)
			{
				oriFamilyOrderChange = 0;
			}
			else
			{
				oriFamilyOrderChange = orderNo - beforeYesterdayOrderNo;
			}
			oriFamilyBean.setOrderChange(oriFamilyOrderChange);
		}
		resultBean.setOriFamily(oriFamilyBean);
		
		long origFamilyId = 0;
		if (null != oriFamilyBean)
		{
			origFamilyId = oriFamilyBean.getFamilyId();
		}
		
		List<Long> familyIdList = this.scoreMapper.queryFamilyIds(bean);
		
		if (CollectionUtils.isNotEmpty(familyIdList))
		{
			for (Long tempFamilyId : familyIdList)
			{
				if (tempFamilyId.longValue() != origFamilyId)
				{
					ScoreFamilyInfoParamBean tempParamBean = new ScoreFamilyInfoParamBean();
					tempParamBean.setFamilyId(tempFamilyId);
					tempParamBean.setTimeStr(getYesterdayDateStr());
					ScoreFamilyInfoBean joinFamilyBean = this.scoreMapper.queryFamilyByFamilyId(tempParamBean);
					if(joinFamilyBean==null) {//参与家族为今日创建
						joinFamilyBean=intiScoreFamilyInfoBeanByFamily(tempFamilyId);
					}else {
						ScoreFamilyInfoParamBean joinYesterdayParamBean = new ScoreFamilyInfoParamBean();
						joinYesterdayParamBean.setFamilyId(joinFamilyBean.getFamilyId());
						joinYesterdayParamBean.setDaystamp(getYesterdayDateStr());
						FamilyDriveDayStatBean joinYeterdayFamilyResultBean = this.scoreMapper.queryFamilyDriveStat(joinYesterdayParamBean);
						
						joinFamilyBean.setYesterdayScore(joinYeterdayFamilyResultBean.getScore());
						
						int joinOrderNo = joinFamilyBean.getOrderNo();
						int joinBeforeYesterdayOrderNo = joinFamilyBean.getBeforeYesterdayOrderNo();
						
						int joinFamilyOrderChange;
						if (0 == joinBeforeYesterdayOrderNo)
						{
							joinFamilyOrderChange = 0;
						}
						else
						{
							joinFamilyOrderChange = joinOrderNo - joinBeforeYesterdayOrderNo;
						}
						joinFamilyBean.setOrderChange(joinFamilyOrderChange);
					}
					resultBean.setJoinFamily(joinFamilyBean);
					break;
				}
			}
		}
		
		return resultBean;
	}
	
	/**
	 * 今日上榜家族
	 * @return
	 */
	public List<ScoreFamilyOrderResultBean> queryFamilyOrderInfo()
	{
		List<ScoreFamilyOrderResultBean> resultList = new ArrayList<>();
		
		ScoreFamilyInfoParamBean scoreFParamBean = new ScoreFamilyInfoParamBean();
		scoreFParamBean.setTimeStr(getYesterdayDateStr());
		
		List<ScoreFamilyOrderBean> tempList = this.scoreMapper.queryFamilyOrderInfo(scoreFParamBean);
		for (ScoreFamilyOrderBean tempBean : tempList)
		{
			ScoreFamilyOrderResultBean tempResultBean = new ScoreFamilyOrderResultBean();
			AdBeanUtils.copyNotNullProperties(tempResultBean, tempBean);
			
			int orderNo = tempBean.getOrderNo();
			int beforeYesterdayOrderNo = tempBean.getBeforeYesterdayOrderNo();
			
			int orderChange;
			if (0 == beforeYesterdayOrderNo)
			{
				orderChange = 0;
			}
			else
			{
				orderChange = orderNo - beforeYesterdayOrderNo;
			}
			tempResultBean.setOrderChange(String.valueOf(orderChange));
			
			resultList.add(tempResultBean);
		}
		
		return resultList;
	}
	
	/**
	 * 查看家族信息
	 * @param bean
	 * @return
	 */
	public ScoreFamilyDetailResultBean queryFamilyDetail(ScoreFamilyInfoParamBean bean)
	{
		ScoreFamilyDetailBean tempBean = this.scoreMapper.queryFamilyDetail(bean);
		bean.setTimeStr(getYesterdayDateStr());
		
		Integer tempFamilyOrderNo = this.scoreMapper.queryFamilyYesterdayOrder(bean);
		if (null != tempFamilyOrderNo)
		{
			tempBean.setOrderNo(tempFamilyOrderNo);
		}
		
		List<String> recordsList = this.scoreMapper.queryFamilyRecords(bean);
		
		List<FamilyScoreBean> orderList = this.scoreMapper.queryOrderRecords(bean);
		//格式：2017-06-30 排名：100
		for (FamilyScoreBean familyScoreTempBean : orderList)
		{
			String endDay = familyScoreTempBean.getEndDay();
			int yesterdayOrderNo = familyScoreTempBean.getYesterdayOrderNo();
			String recordDesc = endDay + " 排名：" + yesterdayOrderNo;
			recordsList.add(recordDesc);
		}
		
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
		
//		this.gameMapper.queryRoleByDay(bean);
		long familyId = bean.getFamilyId();
		String tomorrowDateUndelimiterStr = getTomorrowDateUndelimiterStr();
		
		String yesterdayDateUndelimiterStr = getYesterdayDateUndelimiterStr();
		
		String yesterdayDateStr = getYesterdayDateStr();
		
		for (ScoreMemberInfoBean tempBean : tempList)
		{
			ScoreMemberInfoResultBean tempResultBean = new ScoreMemberInfoResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			
			long userId = tempBean.getUserId();
			
			UserFamilyRoleLogParamBean  roleLogParamBean = new UserFamilyRoleLogParamBean();
			roleLogParamBean.setUserId(userId);
			roleLogParamBean.setFamilyId(familyId);
			roleLogParamBean.setDaystamp(tomorrowDateUndelimiterStr);
			//去除明日角色
//			List<Integer> tomorrowRoleList = this.gameMapper.queryRoleByDay(roleLogParamBean);
//			if (CollectionUtils.isNotEmpty(tomorrowRoleList))
//			{
//				tempResultBean.setTomorrowRole(String.valueOf(tomorrowRoleList.get(0)));
//			}
			tempResultBean.setTomorrowRole(String.valueOf(tempBean.getRole()));
			UserFamilyRoleLogParamBean  yesterdayRoleLogParamBean = new UserFamilyRoleLogParamBean();
			yesterdayRoleLogParamBean.setUserId(userId);
			yesterdayRoleLogParamBean.setFamilyId(familyId);
			yesterdayRoleLogParamBean.setDaystamp(yesterdayDateUndelimiterStr);
			List<UserFamilyRoleLogBean> yesterdayRoleLogList = this.gameMapper.queryFamilyRoleLog(yesterdayRoleLogParamBean);
			if (CollectionUtils.isNotEmpty(yesterdayRoleLogList))
			{
				UserFamilyRoleLogBean yesterdayRoleLogBean = yesterdayRoleLogList.get(0);
//				int yesterdayRole = yesterdayRoleLogBean.getRole();
				UserRoleLog yestodayRole=userRoleLogService.getYestodayUserRoleLogNoTrans(userId);
				int yesterdayRole = yestodayRole.getRole();
				tempResultBean.setYesterdayRole(String.valueOf(yesterdayRole));
				
				long userFamilyRoleLogId = yesterdayRoleLogBean.getId();
				UserFamilyRoleLogParamBean yesterdayScoreParamBean = new UserFamilyRoleLogParamBean();
				yesterdayScoreParamBean.setUserId(userId);
				yesterdayScoreParamBean.setUserFamilyRoleLogId(userFamilyRoleLogId);
				yesterdayScoreParamBean.setDaystamp(yesterdayDateStr);
				List<Double> scoreList = this.gameMapper.queryScore(yesterdayScoreParamBean);
				if (CollectionUtils.isNotEmpty(scoreList))
				{
					tempResultBean.setYesterdayScore(String.valueOf(scoreList.get(0)));
				}
			}
			
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

	public String getTomorrowDateUndelimiterStr()
	{
		Date curDate = Calendar.getInstance().getTime();
		Date tomorrowDate = DateUtils.addDays(curDate, 1);
		
		String tomorrowDateStr = DateFormatUtils.format(tomorrowDate, "yyyyMMdd");
		LOGGER.info(tomorrowDateStr);
		return tomorrowDateStr;
	}
	
	public String getYesterdayDateUndelimiterStr()
	{
		Date curDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(curDate, -1);
		
		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, "yyyyMMdd");
		LOGGER.info(yesterdayDateStr);
		return yesterdayDateStr;
	}
	
	public String getYesterdayDateStr()
	{
		Date curDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(curDate, -1);
		
		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, "yyyy-MM-dd");
		LOGGER.info(yesterdayDateStr);
		return yesterdayDateStr;
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
		
		List<ScoreUserHistoryResultBean> resultList = new ArrayList<>();
		
		if (0 == bean.getStart())
		{
			ScoreUserHistoryResultBean todayResultBean = new ScoreUserHistoryResultBean();
			Integer currentRole = this.scoreMapper.queryCurrentRole(bean);
			todayResultBean.setRole(String.valueOf(currentRole));
			todayResultBean.setDayStr(todayStr + "(今日)");
			todayResultBean.setScore("暂无评分");
			resultList.add(todayResultBean);
		}
		
		for (ScoreUserHistoryBean tempBean : tempList)
		{
			ScoreUserHistoryResultBean tempResultBean = new ScoreUserHistoryResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			String dayStr = tempBean.getDayStr();
			
			String tempDayStr = formatDayStr(dayStr);
			
			//如果晚于今天
			if (StringUtils.compare(tempDayStr, todayStr) > 0)
			{
				continue;
			}
			else if (StringUtils.equals(tempDayStr, todayStr))
			{
				//过滤掉今天，今天在for循环开始前已加入
//				tempResultBean.setDayStr(tempDayStr + "(今日)");
				continue;
			}
			else if (StringUtils.equals(tempDayStr, yesterdayStr))
			{
				tempResultBean.setDayStr(tempDayStr + "(昨日)");
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
		ScoreFamilyInfoParamBean userFamilyRoleParamBean = new ScoreFamilyInfoParamBean();
		userFamilyRoleParamBean.setUserId(bean.getUserId());
		userFamilyRoleParamBean.setFamilyId(bean.getFamilyId());
		userFamilyRoleParamBean.setDaystamp(StringUtils.remove(bean.getDay(), "-"));
		Integer tempFamilyRoleId = this.scoreMapper.queryFamilyRoleId(userFamilyRoleParamBean);
		bean.setUserFamilyScoreId(tempFamilyRoleId);
		
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
	 * 昨日得分
	 * @param bean
	 * @return
	 */
	public List<YesterdayScoreResultBean> findYesterdayFamilyScore(ScoreFamilyInfoParamBean bean)
	{
		String yesterdayDateUndelimiterStr = getYesterdayDateUndelimiterStr();
		
		List<YesterdayScoreBean> tempList = this.scoreMapper.queryMembersByFamily(bean);
		
		double tempTotalScore = 0;
		List<YesterdayScoreResultBean> resultList = new ArrayList<>();
		for (YesterdayScoreBean tempBean : tempList)
		{
			ScoreFamilyInfoParamBean userFamilyRoleParamBean = new ScoreFamilyInfoParamBean();
			userFamilyRoleParamBean.setUserId(tempBean.getUserId());
			userFamilyRoleParamBean.setFamilyId(bean.getFamilyId());
			userFamilyRoleParamBean.setDaystamp(yesterdayDateUndelimiterStr);
			Integer tempUserFamilyRoleId = this.scoreMapper.queryFamilyRoleId(userFamilyRoleParamBean);
			
			if(tempUserFamilyRoleId==null) {//用户昨日无角色
				tempBean.setScore(0);
			}else {
			
			ScoreFamilyInfoParamBean userScoreDayParamBean = new ScoreFamilyInfoParamBean();
			userScoreDayParamBean.setUserId(tempBean.getUserId());
			userScoreDayParamBean.setUserFamilyScoreId(tempUserFamilyRoleId);
			Double tempUserScore = this.scoreMapper.queryUserScore(userScoreDayParamBean);
			tempBean.setScore(tempUserScore);
			}
			YesterdayScoreResultBean tempResultBean = new YesterdayScoreResultBean();
			AdBeanUtils.copyNotNullProperties(tempResultBean, tempBean);
			
			String name = tempBean.getName();
			if (StringUtils.isBlank(name))
			{
				tempResultBean.setName(PhoneUtils.hidePhone(tempBean.getPhone()));
			}
			
			resultList.add(tempResultBean);
			
			tempTotalScore += tempBean.getScore();
			
		}
		
		int totalSize = tempList.size();
		if (totalSize > 0)
		{
			for (YesterdayScoreResultBean tempBean : resultList)
			{
				if(tempTotalScore==0) {
					tempBean.setPercent("0");
				}else {
					BigDecimal resultBd = BigDecimal.valueOf(NumberUtils.toDouble(tempBean.getScore())).divide(BigDecimal.valueOf(tempTotalScore), 2, BigDecimal.ROUND_HALF_UP);
					double resultD = resultBd.doubleValue();
					String percent = formattedDecimalToPercentage(resultD);
					tempBean.setPercent(percent);
				}
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
		String yesterdayDateUndelimiterStr = getYesterdayDateUndelimiterStr();
		List<YesterdayScoreBean> tempList = this.scoreMapper.queryMembersByFamily(bean);
		
		double tempTotalScore = 0;
		List<YesterdayContributionResultBean> resultList = new ArrayList<>();
		for (YesterdayScoreBean tempBean : tempList)
		{
			ScoreFamilyInfoParamBean userFamilyRoleParamBean = new ScoreFamilyInfoParamBean();
			userFamilyRoleParamBean.setUserId(tempBean.getUserId());
			userFamilyRoleParamBean.setFamilyId(bean.getFamilyId());
			userFamilyRoleParamBean.setDaystamp(yesterdayDateUndelimiterStr);
			Integer tempUserFamilyRoleId = this.scoreMapper.queryFamilyRoleId(userFamilyRoleParamBean);
			if(tempUserFamilyRoleId==null) {//昨日无角色
				tempBean.setScore(0);
			}else {
			ScoreFamilyInfoParamBean userScoreDayParamBean = new ScoreFamilyInfoParamBean();
			userScoreDayParamBean.setUserId(tempBean.getUserId());
			userScoreDayParamBean.setUserFamilyScoreId(tempUserFamilyRoleId);
			Double tempUserScore = this.scoreMapper.queryUserScore(userScoreDayParamBean);
			tempBean.setScore(tempUserScore);
			}
			YesterdayContributionResultBean tempResultBean = new YesterdayContributionResultBean();
			AdBeanUtils.copyNotNullProperties(tempResultBean, tempBean);
			
			if (StringUtils.isBlank(tempBean.getName()))
			{
				tempResultBean.setName(PhoneUtils.hidePhone(tempBean.getPhone()));
			}
			
			resultList.add(tempResultBean);
			
			tempTotalScore += tempBean.getScore();
		}
		
		int totalSize = tempList.size();
		if (totalSize > 0)
		{
			for (YesterdayContributionResultBean tempBean : resultList)
			{
				if(tempTotalScore==0) {
					tempBean.setContribution("0");
				}else {
					BigDecimal resultBd = BigDecimal.valueOf(NumberUtils.toDouble(tempBean.getScore())).divide(BigDecimal.valueOf(tempTotalScore), 2, BigDecimal.ROUND_HALF_UP);
					double resultD = resultBd.doubleValue();
					String contribution = formattedDecimalToPercentage(resultD);
					tempBean.setContribution(contribution);
				}
			}
		}
		
		return resultList;
	}
 
	
	public void dealGameResultInit(CompetitorResultBean resultBean,Long familyId) {
		FamilyCompetitorResultBean gameObj=initFamilyCompetitorResultBean();
		Map<String,Object> family=usersAccountMapper.getFamilyByFamilyId(familyId);
		FamilyCompetitorResultBean competitorObj=initFamilyCompetitorResultBean();
		gameObj.setImgUrl(String.valueOf(family.get("imgUrl")));
		gameObj.setFamilyId(String.valueOf(familyId));
		gameObj.setName(String.valueOf(family.get("familyName")));
		resultBean.setGameObj(gameObj);
		resultBean.setCompetitorObj(competitorObj);
	}
	private FamilyCompetitorResultBean initFamilyCompetitorResultBean() {
		FamilyCompetitorResultBean gameObj=new FamilyCompetitorResultBean();
		gameObj.setBrakeTimes("0");
		gameObj.setCompetitingResult("0");
		gameObj.setFamilyId("0");
		gameObj.setFamilyLevelFactor("0");
		gameObj.setFamilyNumFactor("0");
		gameObj.setIllegalStopTimes("0");
		gameObj.setImgUrl(null);
		gameObj.setMaxspeed("0");
		gameObj.setMemberFactor("0");
		gameObj.setMileage("0");
		gameObj.setName("0");
		gameObj.setNightDriveTimes("0");
		gameObj.setOrderChange("0");
		gameObj.setOrderNo("0");
		gameObj.setOverspeedTimes("0");
		gameObj.setRoleFactor("0");
		gameObj.setScore("0");
		gameObj.setSpeedTimes("0");
		gameObj.setTime("0");
		gameObj.setTiredDriveTimes("0");
		gameObj.setTurnTimes("0");
		gameObj.setUseHoldNum("0");
		gameObj.setUsePhoneTimes("0");
		return gameObj;
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
		Date beforeYesterdayDate = DateUtils.addDays(todayDate, -2);
		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, "yyyy-MM-dd");
		String beforeYesterdayDateStr = DateFormatUtils.format(beforeYesterdayDate, "yyyy-MM-dd");
		
		long familyId = bean.getFamilyId();
		
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(familyId);
		
		FamilyResultBean selfFamilybean = this.familyMapper.queryFamilyById(familyParamBean);
		
		if (null == selfFamilybean)
			return null;
		
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
		
		int gameObjYesterdayOrderNo = gameObjFamilyStatBean.getOrderNo();
		
		gameObjParamBean.setDaystamp(beforeYesterdayDateStr);
		FamilyDriveDayStatBean beforeYesterdayGameObjFamilyStatBean = this.scoreMapper.queryFamilyDriveStat(gameObjParamBean);
		int gameObjBeforeYesterdayOrderNo;
		if (null == beforeYesterdayGameObjFamilyStatBean)
		{
			gameObjBeforeYesterdayOrderNo = 0;
		}
		else
		{
			gameObjBeforeYesterdayOrderNo = beforeYesterdayGameObjFamilyStatBean.getOrderNo();
		}
		
		int gameObjOrderChange;
		if (0 == gameObjBeforeYesterdayOrderNo)
		{
			gameObjOrderChange = 0;
		}
		else
		{
			gameObjOrderChange = gameObjYesterdayOrderNo - gameObjBeforeYesterdayOrderNo;
		}
		
		AdBeanUtils.copyOtherPropToStr(gameObj, gameObjFamilyStatBean);
		
		gameObj.setOrderChange(String.valueOf(gameObjOrderChange));
		
		resultBean.setGameObj(gameObj);
		
		FamilyRelationBean relationParamBean = new FamilyRelationBean();
		relationParamBean.setFamilyId(familyId);
		relationParamBean.setDaystamp(getCurrentDayStr());
//		relationParamBean.setDaystamp("2018-01-10");
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
		
		competitorObjParamBean.setDaystamp(beforeYesterdayDateStr);
		FamilyDriveDayStatBean beforeYesterdayCompetitorObjFamilyStatBean = this.scoreMapper.queryFamilyDriveStat(competitorObjParamBean);
		int competitorYesterdayOrderNo = competitorObjFamilyStatBean.getOrderNo();
		int competitorBeforeYesterdayOrderNo = beforeYesterdayCompetitorObjFamilyStatBean.getOrderNo();
		
		int competitorObjOrderChange;
		if (0 == competitorBeforeYesterdayOrderNo)
		{
			competitorObjOrderChange = 0;
		}
		else
		{
			competitorObjOrderChange = competitorYesterdayOrderNo - competitorBeforeYesterdayOrderNo;
		}
		
		AdBeanUtils.copyOtherPropToStr(competitorObj, competitorObjFamilyStatBean);
		
		competitorObj.setOrderChange(String.valueOf(competitorObjOrderChange));
		
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
	
	public List<UserTravelHistoryResultBean> showTravels(ScoreFamilyInfoParamBean bean)
	{
		String day = bean.getDay();
		String daystamp = StringUtils.remove(day, "-");
		bean.setDaystamp(daystamp);
		UserFamilyRoleLogBean betweenTimeBean = this.scoreMapper.queryTravelBetweenTime(bean);
		String roleStartTime = betweenTimeBean.getStartTime();
		String roleEndTime = betweenTimeBean.getEndTime();
		
		bean.setStartTime(roleStartTime);
		bean.setEndTime(roleEndTime);
		
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
		mileageBean.setFactor("里程");
		mileageBean.setValue(String.valueOf(tempBean.getMileage()));
		mileageBean.setWeight(formattedDecimalToPercentage(tempBean.getMileageProportion()));
		mileageBean.setScore(String.valueOf(tempBean.getMileageScore()));
		resultList.add(mileageBean);
		
		ScoreDetailUnitBean brakeTimesBean = new ScoreDetailUnitBean();
		brakeTimesBean.setFactor("急刹车");
		String brakeTimes1 = String.valueOf(tempBean.getBrakeTimes()) + "次";
		String brakeTimes2 = String.valueOf(tempBean.getUseShachepian());
		String brakeTottery = "刹车片";
		String brakeValue = formatDetailValue(brakeTimes1, brakeTimes2, brakeTottery);
		brakeTimesBean.setValue(brakeValue);
		brakeTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getBrakeTimesProportion()));
		brakeTimesBean.setScore(String.valueOf(tempBean.getBrakeTimesScore()));
		resultList.add(brakeTimesBean);
		
		ScoreDetailUnitBean turnTimesBean = new ScoreDetailUnitBean();
		turnTimesBean.setFactor("急转弯");
		String turnTimes1 = String.valueOf(tempBean.getTurnTimes());
		String turnTimes2 = String.valueOf(tempBean.getUseCheluntai());
		String turnTottery = "车轮胎";
		String turnValue = formatDetailValue(turnTimes1 + "次", turnTimes2, turnTottery);
		turnTimesBean.setValue(turnValue);
		turnTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getTurnTimesProportion()));
		turnTimesBean.setScore(String.valueOf(tempBean.getTurnTimesScore()));
		resultList.add(turnTimesBean);
		
		ScoreDetailUnitBean overspeedTimesBean = new ScoreDetailUnitBean();
		overspeedTimesBean.setFactor("超速");
		String overspeedTimes1 = String.valueOf(tempBean.getOverspeedTimes());
		String zengyaqiTimes = String.valueOf(tempBean.getUseZengyaqi());
		String overspeedTottery = "增压器";
		String overspeedValue = formatDetailValue(overspeedTimes1 + "次", zengyaqiTimes, overspeedTottery);
		overspeedTimesBean.setValue(overspeedValue);
		overspeedTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getOverspeedTimesProportion()));
		overspeedTimesBean.setScore(String.valueOf(tempBean.getOverspeedTimesScore()));
		resultList.add(overspeedTimesBean);
		
		ScoreDetailUnitBean nightDriveTimesBean = new ScoreDetailUnitBean();
		nightDriveTimesBean.setFactor("夜间驾驶");
		String nightDriveTimes1 = String.valueOf(tempBean.getNightDriveTimes());
		String yeshijingTimes = String.valueOf(tempBean.getUseYeshijing());
		String yeshijingTottery = "夜视镜";
		String yeshijingValue = formatDetailValue(nightDriveTimes1 + "次", yeshijingTimes, yeshijingTottery);
		nightDriveTimesBean.setValue(yeshijingValue);
		nightDriveTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getNightDriveTimesProportion()));
		nightDriveTimesBean.setScore(String.valueOf(tempBean.getNightDriveScore()));
		resultList.add(nightDriveTimesBean);
		
		ScoreDetailUnitBean tiredDriveTimesBean = new ScoreDetailUnitBean();
		tiredDriveTimesBean.setFactor("疲劳驾驶");
		String tiredDriveTimesStr = String.valueOf(tempBean.getTiredDriveTimes());
		String hongniuTimes = String.valueOf(tempBean.getUseHongniu());
		String hongniuTottery = "红牛";
		String tiredDriveTimesValue = formatDetailValue(tiredDriveTimesStr + "次", hongniuTimes, hongniuTottery);
		tiredDriveTimesBean.setValue(tiredDriveTimesValue);
		tiredDriveTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getTiredDriveProportion()));
		tiredDriveTimesBean.setScore(String.valueOf(tempBean.getTiredDriveScore()));
		resultList.add(tiredDriveTimesBean);
		
		ScoreDetailUnitBean illegalStopTimesBean = new ScoreDetailUnitBean();
		illegalStopTimesBean.setFactor("违停");
		String illegalStopTimesStr = String.valueOf(tempBean.getIllegalStopTimes());
		illegalStopTimesBean.setValue(illegalStopTimesStr);
		illegalStopTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getIllegalStopTimesProportion()));
		illegalStopTimesBean.setScore(String.valueOf(tempBean.getIllegalStopTimesScore()));
		resultList.add(illegalStopTimesBean);
		
		ScoreDetailUnitBean phoneTimesBean = new ScoreDetailUnitBean();
		phoneTimesBean.setFactor("使用手机次数");
		phoneTimesBean.setValue(String.valueOf(tempBean.getPhoneTimes()));
		phoneTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getPhoneTimesProportion()));
		phoneTimesBean.setScore(String.valueOf(tempBean.getPhoneTimesScore()));
		resultList.add(phoneTimesBean);
		
		ScoreDetailUnitBean mazhaTimesBean = new ScoreDetailUnitBean();
		mazhaTimesBean.setFactor("使用马扎");
		mazhaTimesBean.setValue(String.valueOf(tempBean.getUseHoldNum()) + "次");
		mazhaTimesBean.setWeight(formattedDecimalToPercentage(tempBean.getMazhaProportion()));
		mazhaTimesBean.setScore(String.valueOf(tempBean.getMazhaScore()));
		resultList.add(mazhaTimesBean);
		
		ScoreDetailUnitBean maxspeedBean = new ScoreDetailUnitBean();
		maxspeedBean.setFactor("最高时速");
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
		extraPlusBean.setFactor("额外加分");
		int extraPlus = tempBean.getExtraPlus();
		extraPlusBean.setValue(String.valueOf(extraPlus));
		extraPlusBean.setWeight(formattedDecimalToPercentage(tempBean.getExtraPlusProportion()));
		extraPlusBean.setScore(String.valueOf(tempBean.getExtraPlusScore()));
		resultList.add(extraPlusBean);
		
		return resultList;
	}

	private String formatDetailValue(String times1, String times2, String lottery)
	{
		if (StringUtils.equals(times1, "0次") || StringUtils.equals(times2, "0"))
		{
			return times1;
		}
		
		
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
	
	public List<GameHistoryResultBean> gameHistory(ScoreFamilyInfoParamBean bean)
	{
		List<FamilyScoreBean> familyScoreList = this.scoreMapper.queryStartEndDay(bean);
		if (CollectionUtils.isEmpty(familyScoreList))
			return null;
		
		FamilyScoreBean lastScoreBean = familyScoreList.get(0);
		String startDay = lastScoreBean.getStartDay();
		String currentDayStr = getCurrentDayStr();
		String endDay;
		if (StringUtils.equals(startDay, currentDayStr))
		{
			lastScoreBean = familyScoreList.get(1);
			startDay = lastScoreBean.getStartDay();
			endDay = lastScoreBean.getEndDay();
		}
		else
		{
			endDay = lastScoreBean.getEndDay();
		}
		
		bean.setStartDay(startDay);
		bean.setEndDay(endDay);
		
		List<GameHistoryBean> tempList = this.scoreMapper.queryFamilyOrderByMonth(bean);
		
		if (CollectionUtils.isEmpty(tempList))
		{
			
		}
		
		List<GameHistoryResultBean> resultList = new ArrayList<>();
		
		for (GameHistoryBean tempBean : tempList)
		{
			GameHistoryResultBean tempResultBean = new GameHistoryResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			resultList.add(tempResultBean);
		}
		
		return resultList;
	}
	
	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}
	
	/**
	 * 查看玩家信息
	 * @param bean
	 * @return
	 */
	public UserDetailResultBean showUserDetail(ScoreFamilyInfoParamBean bean)
	{
		UserDetailResultBean resultBean = new UserDetailResultBean();
		
		UsersAccountBean userParamBean = new UsersAccountBean();
		userParamBean.setId(bean.getUserId());
		UsersAccountBean usersAccountBean = this.scoreMapper.queryUserInfo(userParamBean);
		
		String nickName = usersAccountBean.getNickName();
		if (StringUtils.isNotBlank(nickName))
		{
			resultBean.setName(nickName);
		}
		else
		{
			String phone = usersAccountBean.getPhone();
			String hidePhone = PhoneUtils.hidePhone(phone);
			resultBean.setName(hidePhone);
		}
		resultBean.setUserImgUrl(usersAccountBean.getImgUrl());
		
		Date createTime = usersAccountBean.getCreateTime();
		String createTimeStr = DateFormatUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
		resultBean.setJoinTime(createTimeStr);
		
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(bean.getFamilyId());
		FamilyInfoBean familyInfoBean = this.familyMapper.queryFamilyInfo(familyParamBean);
		resultBean.setFamilyName(familyInfoBean.getFamilyName());
		resultBean.setFamilyImgUrl(familyInfoBean.getImgUrl());
		
		List<Integer> userFamilyRoleIdList = this.scoreMapper.queryFamilyRoleIdList(bean);
		bean.setUserFamilyRoleIdList(userFamilyRoleIdList);
		
		double statMileage = this.scoreMapper.statMileage(bean);
		String mileageStr = BigDecimal.valueOf(statMileage).divide(BigDecimal.valueOf(1000), 1, BigDecimal.ROUND_HALF_UP).toString() + "km";
		resultBean.setMileage(mileageStr);
		
		long statTime = (long)this.scoreMapper.statTime(bean);
		String timeStr = DurationFormatUtils.formatDuration(statTime*1000L, "HH") + "小时";
		resultBean.setDuration(timeStr);
		
		return resultBean;
	}
}
