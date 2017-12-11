package com.idata365.app.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.SignInResultBean;
import com.idata365.app.entity.SignatureDayLogBean;
import com.idata365.app.mapper.SignatureDayLogMapper;


public class SignInService extends BaseService<SignInService>
{
	@Autowired
	private SignatureDayLogMapper signatureDayLogMapper;
	
	/**
	 * 查询签到记录，格式：yyyyMMdd
	 * @param bean
	 * @return
	 */
	public SignInResultBean query(SignatureDayLogBean bean)
	{
		SignInResultBean resultBean = new SignInResultBean();
		
		List<String> sigDaysList = this.signatureDayLogMapper.query(bean);
		resultBean.setSigDays(sigDaysList);
		
		int continueCount = 0;
		Date prevDate = null;
		for (int i = 0; i < sigDaysList.size(); i++)
		{
			String sigDay = sigDaysList.get(i);
			Date tempDate = parseStrToDate(sigDay);
			
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
		resultBean.setSigCount(continueCount);
		
		return resultBean;
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
		
		this.signatureDayLogMapper.save(bean);;
	}
	
}
