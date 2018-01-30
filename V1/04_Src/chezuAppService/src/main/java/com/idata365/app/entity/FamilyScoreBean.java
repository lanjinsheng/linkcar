package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyScoreBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2651508375006214850L;

	private String startDay;
	
	private String endDay;

	private int yesterdayOrderNo;
	
	public String getStartDay()
	{
		return startDay;
	}

	public void setStartDay(String startDay)
	{
		this.startDay = startDay;
	}

	public String getEndDay()
	{
		return endDay;
	}

	public void setEndDay(String endDay)
	{
		this.endDay = endDay;
	}

	public int getYesterdayOrderNo()
	{
		return yesterdayOrderNo;
	}

	public void setYesterdayOrderNo(int yesterdayOrderNo)
	{
		this.yesterdayOrderNo = yesterdayOrderNo;
	}
	
}
