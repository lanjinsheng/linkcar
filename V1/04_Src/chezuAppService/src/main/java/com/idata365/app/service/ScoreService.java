package com.idata365.app.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.FamilyParamBean;
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
import com.idata365.app.entity.ScoreUserHistoryBean;
import com.idata365.app.entity.ScoreUserHistoryParamBean;
import com.idata365.app.entity.ScoreUserHistoryResultAllBean;
import com.idata365.app.entity.ScoreUserHistoryResultBean;
import com.idata365.app.entity.ScoreUserResultBean;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.ScoreMapper;
import com.idata365.app.util.AdBeanUtils;

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
			String formatJoinTime = formatTime(tempJoinTime);
			tempResultBean.setJoinTime(formatJoinTime);
			
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
	 * 昨日得分
	 * @param bean
	 * @return
	 */
	public List<ScoreUserResultBean> findYesterdayScore(ScoreFamilyInfoParamBean bean)
	{
		
		List<ScoreUserResultBean> resultList = new ArrayList<>();
		
		return resultList;
	}
}
