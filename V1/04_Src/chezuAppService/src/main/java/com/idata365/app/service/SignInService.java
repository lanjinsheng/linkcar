package com.idata365.app.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.common.utils.DateUtil;
import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.SigAwardStatBean;
import com.idata365.app.entity.SignatureDayLogBean;
import com.idata365.app.mapper.SignatureDayLogMapper;

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
	public void sign(SignatureDayLogBean bean)
	{
		Calendar curDay = Calendar.getInstance();
		String curDayStr = DateFormatUtils.format(curDay, DateConstant.DAY_PATTERN);
		String month = DateFormatUtils.format(curDay, DateConstant.MONTH_PATTERN);
		bean.setSigTimestamp(curDayStr);
		bean.setMonth(month);
		
		int count = this.signatureDayLogMapper.countByUserId(bean);
		if (0 == count)
		{
			this.signatureDayLogMapper.save(bean);;
		}
	}
	
	/**
	 * 查询签到状态（抽奖状态）
	 * @param bean
	 */
	public SigAwardStatBean querySigAwardStat(SignatureDayLogBean bean)
	{
		SigAwardStatBean resultBean = new SigAwardStatBean();
		
		Date todayDate = Calendar.getInstance().getTime();
		Date beginDate = DateUtils.addDays(todayDate, -6);
		String beginDateStr = DateFormatUtils.format(beginDate, DateConstant.DAY_PATTERN);
		bean.setSigTimestamp(beginDateStr);
		
		List<SignatureDayLogBean> sigStatusList = this.signatureDayLogMapper.querySigStatus(bean);
		
		if (CollectionUtils.isEmpty(sigStatusList))
		{
			resultBean.setContinueSevenFlag("SEVEN_NO");
		}
		else
		{
			SignatureDayLogBean firstLogBean = sigStatusList.get(0);
			String awardStatus = firstLogBean.getAwardStatus();
			String firstLogDateStr = firstLogBean.getSigTimestamp();
			String todayDateStr = DateFormatUtils.format(todayDate, DateConstant.DAY_PATTERN);
			if (StringUtils.equals("OK", awardStatus)
					&& StringUtils.equals(firstLogDateStr, todayDateStr))
			{
				resultBean.setContinueSevenFlag("SEVEN_AWARD");
			}
			else
			{
				int continueCount = 0;
				Date prevDate = null;
				for (int i = 0; i < sigStatusList.size(); i++)
				{
					SignatureDayLogBean tempBean = sigStatusList.get(i);
					String tempAwardStatus = tempBean.getAwardStatus();
					if (StringUtils.equals("OK", tempAwardStatus))
					{
						break;
					}
					else
					{
						String sigTimestamp = tempBean.getSigTimestamp();
						Date tempDate = parseStrToDate(sigTimestamp);
						
						if (null == prevDate)
						{
							continueCount = 1;
							prevDate = tempDate;
						}
						else
						{
							Date addedDays = DateUtils.addDays(tempDate, 1);
							if (addedDays.equals(prevDate))
							{
								continueCount++;
							}
							else
							{
								break;
							}
						}
					}
				}
				
				if (7 == continueCount)
				{
					resultBean.setContinueSevenFlag("SEVEN_OK");
				}
				else
				{
					resultBean.setContinueSevenFlag("SEVEN_NO");
				}
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
		int continueCount = 0;
		
		Date todayDate = Calendar.getInstance().getTime();
		Date beginDate = DateUtils.addDays(todayDate, -6);
		String beginDateStr = DateFormatUtils.format(beginDate, DateConstant.DAY_PATTERN);
		bean.setSigTimestamp(beginDateStr);
		
		List<SignatureDayLogBean> sigStatusList = this.signatureDayLogMapper.querySigStatus(bean);
		
		if (CollectionUtils.isEmpty(sigStatusList))
		{
			return continueCount;
			
		}
		else
		{
			SignatureDayLogBean firstLogBean = sigStatusList.get(0);
			String awardStatus = firstLogBean.getAwardStatus();
			if (StringUtils.equals("OK", awardStatus))
			{
				return continueCount;
			}
			else
			{
				Date prevDate = null;
				for (int i = 0; i < sigStatusList.size(); i++)
				{
					SignatureDayLogBean tempBean = sigStatusList.get(i);
					String tempAwardStatus = tempBean.getAwardStatus();
					if (StringUtils.equals("OK", tempAwardStatus))
					{
						break;
					}
					else
					{
						String sigTimestamp = tempBean.getSigTimestamp();
						Date tempDate = parseStrToDate(sigTimestamp);
						
						if (null == prevDate)
						{
							continueCount = 1;
							prevDate = tempDate;
						}
						else
						{
							Date addedDays = DateUtils.addDays(tempDate, 1);
							if (addedDays.equals(prevDate))
							{
								continueCount++;
							}
							else
							{
								break;
							}
						}
					}
				}
			}
		}
		return continueCount;
	}
}
