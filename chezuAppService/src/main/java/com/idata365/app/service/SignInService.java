package com.idata365.app.service;

import static org.mockito.Matchers.endsWith;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.mockito.asm.tree.IntInsnNode;
import org.springframework.beans.factory.annotation.Autowired;

import com.ctc.wstx.util.StringUtil;
import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.SignatureLogBean;
import com.idata365.app.mapper.SignatureLogMapper;


public class SignInService extends BaseService<SignInService>
{
	@Autowired
	private SignatureLogMapper signatureLogMapper;
	
	/**
	 * 最近已经连续签到的日期，格式：yyyyMMdd
	 * @param bean
	 * @return
	 */
	public List<String> query(SignatureLogBean bean)
	{
		SignatureLogBean logBean = this.signatureLogMapper.query(bean);
		if (null == logBean)
			return null;
		int sigCount = logBean.getSigCount();
		String lastSigDay = logBean.getLastSigDay();
		
		Calendar todayCalendar = Calendar.getInstance();
		String todayStr = DateFormatUtils.format(todayCalendar, DateConstant.DAY_PATTERN);
		Date yesterday = DateUtils.addDays(todayCalendar.getTime(), -1);
		String yesterdayStr = DateFormatUtils.format(yesterday, DateConstant.DAY_PATTERN);
		
		List<String> resultList = new ArrayList<String>();
		if (StringUtils.equals(lastSigDay, todayStr)
				|| StringUtils.equals(lastSigDay, yesterdayStr))
		{
			resultList.add(lastSigDay);
		}
		else
		{
			return null;
		}
		
		if (1 == sigCount)
		{
			return resultList;
		}
		else
		{
			Date lastSigDate = parseStrToDate(lastSigDay);
			
			for (int i = 1; i < sigCount; i++)
			{
				Date tempDate = DateUtils.addDays(lastSigDate, -i);
				String tempDayStr = DateFormatUtils.format(tempDate, DateConstant.DAY_PATTERN);
				resultList.add(tempDayStr);
			}
			
			return resultList;
		}
	}

	/**
	 * 签到
	 * @param bean
	 */
	public void sign(SignatureLogBean bean)
	{
		SignatureLogBean logBean = this.signatureLogMapper.query(bean);
		Calendar todayCalendar = Calendar.getInstance();
		String todayStr = DateFormatUtils.format(todayCalendar, DateConstant.DAY_PATTERN);
		if (null == logBean)
		{
			bean.setLastSigDay(todayStr);
			bean.setSigCount(1);
		}
		else
		{
			Date yesterday = DateUtils.addDays(todayCalendar.getTime(), -1);
			String yesterdayStr = DateFormatUtils.format(yesterday, DateConstant.DAY_PATTERN);
			String lastSigDay = logBean.getLastSigDay();
			if (StringUtils.equals(lastSigDay, todayStr))
			{
				return;
			}
			else if (StringUtils.equals(lastSigDay, yesterdayStr))
			{
				bean.setLastSigDay(todayStr);
				int sigCount = bean.getSigCount();
				sigCount++;
				bean.setSigCount(sigCount);
			}
		}
		
		this.signatureLogMapper.saveOrUpdate(bean);
	}
	
	
	private Date parseStrToDate(String lastSigDay)
	{
		Date date = null;
		try
		{
			date = DateUtils.parseDate(lastSigDay, DateConstant.DAY_PATTERN);
		} catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
		return date;
	}
}
