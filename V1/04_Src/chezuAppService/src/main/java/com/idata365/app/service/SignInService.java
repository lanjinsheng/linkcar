package com.idata365.app.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.SigAwardStatBean;
import com.idata365.app.entity.SigResultBean;
import com.idata365.app.entity.SignatureDayLogBean;
import com.idata365.app.entity.SignatureStatBean;
import com.idata365.app.mapper.SignatureDayLogMapper;
import com.idata365.app.util.AdBeanUtils;

@Service
public class SignInService extends BaseService<SignInService>
{
	@Autowired
	private SignatureDayLogMapper signatureDayLogMapper;
	
	/**
	 * 查询签到记录，格式：yyyyMMdd
	 * @param bean
	 * @return
	 */
	public List<String> query(SignatureDayLogBean bean)
	{
		List<String> sigDaysList = this.signatureDayLogMapper.query(bean);
		return sigDaysList;
	}

	private Date parseStrToDate(String sigDay)
	{
		Date tempDate = null;
		try
		{
			tempDate = DateUtils.parseDate(sigDay, DateConstant.DAY_PATTERN);
		} catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
		return tempDate;
	}

	/**
	 * 签到
	 * @param bean
	 */
	@Transactional
	public void sign(SignatureDayLogBean bean)
	{
		Calendar curDay = Calendar.getInstance();
		String curDayStr = DateFormatUtils.format(curDay, DateConstant.DAY_PATTERN);
		String month = DateFormatUtils.format(curDay, DateConstant.MONTH_PATTERN);
		bean.setSigTimestamp(curDayStr);
		bean.setMonth(month);
		
		int count = this.signatureDayLogMapper.countByUserId(bean);
		//如果今天尚未签到
		if (0 == count)
		{
			SignatureDayLogBean sigParamBean = new SignatureDayLogBean();
			sigParamBean.setUserId(bean.getUserId());
			//查询最后一次签到日期
			SigResultBean lastSigObj = this.signatureDayLogMapper.queryLastSigDay(bean);
//			LOGGER.info("lastSigObjList====={}", JSON.toJSONString(lastSigObjList));
			
			//如果没有最后一次签到日期，则用户是第一次签到
			if (null == lastSigObj)
			{
				//查询签到统计天数
				SigResultBean numResultBean = this.signatureDayLogMapper.countSigStatNum(bean);
				//如果没有签到统计天数，则初始化签到统计天数记录
				if (null == numResultBean)
				{
					this.signatureDayLogMapper.saveSigStat(bean);
				}
				//如果有签到统计天数，则更统计天数记录为1
				else
				{
					bean.setNum(1);
					this.signatureDayLogMapper.updateSigNumStat(bean);
				}
			}
			//如果有最后一次签到日期
			else
			{
				String lastSigTs = lastSigObj.getSigTs();
				//如果签到日期不等于今天，表尚未签到
				if (!StringUtils.equals(lastSigTs, curDayStr))
				{
					String yesterdayStr = getYesterdayDateUndelimiterStr();
					if (StringUtils.equals(lastSigTs, yesterdayStr))
					{
						SigResultBean numResultBean = this.signatureDayLogMapper.countSigStatNum(bean);
						int sigNum = numResultBean.getSigNum();
						if (7 == sigNum)
						{
							bean.setNum(1);
						}
						else
						{
							bean.setNum(sigNum + 1);
						}
						this.signatureDayLogMapper.updateSigNumStat(bean);
					}
					else
					{
						bean.setNum(1);
						this.signatureDayLogMapper.updateSigNumStat(bean);
					}
				}
			}
			
			SignatureDayLogBean saveParamBean = new SignatureDayLogBean();
			saveParamBean.setUserId(bean.getUserId());
			saveParamBean.setSigTimestamp(curDayStr);
			saveParamBean.setMonth(month);
			//记录签到
			this.signatureDayLogMapper.save(saveParamBean);
		}
		
	}
	
	public String getYesterdayDateUndelimiterStr()
	{
		Date curDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(curDate, -1);
		
		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, "yyyyMMdd");
		return yesterdayDateStr;
	}
	
	private String getCurrentDayStrUnDelimiter()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN);
		return dayStr;
	}
	
	/**
	 * 查询签到状态（抽奖状态）
	 * @param bean
	 */
	public SigAwardStatBean querySigAwardStat(SignatureDayLogBean bean)
	{
		SigAwardStatBean resultBean = new SigAwardStatBean();
		
		bean.setSigTimestamp(getCurrentDayStrUnDelimiter());
		String sigStatus = this.signatureDayLogMapper.querySigStatus(bean);
		if (StringUtils.equals("OK", sigStatus))
		{
			resultBean.setContinueSevenFlag("SEVEN_AWARD");
		}
		else
		{
			SignatureStatBean statBean = this.signatureDayLogMapper.querySigStatInfo(bean);
			String sigTimestamp = statBean.getSigTimestamp();
			int num = statBean.getNum();
			if (StringUtils.equals(sigTimestamp, getCurrentDayStrUnDelimiter())
					&& 7 == num)
			{
				resultBean.setContinueSevenFlag("SEVEN_OK");
			}
			else
			{
				resultBean.setContinueSevenFlag("SEVEN_NO");
			}
		}
		
		return resultBean;
	}
	
	/**
	 * 查询连续签到天数
	 * @param bean
	 * @return
	 */
	public int queryContinueSigDay(SignatureDayLogBean bean)
	{
		SignatureStatBean statBean = this.signatureDayLogMapper.querySigStatInfo(bean);
		String sigTimestamp = statBean.getSigTimestamp();
		if (StringUtils.equals(sigTimestamp, getCurrentDayStrUnDelimiter())
				|| StringUtils.equals(sigTimestamp, getYesterdayDateUndelimiterStr()))
		{
			return statBean.getNum();
		}
		else
		{
			return 0;
		}
	}
}
