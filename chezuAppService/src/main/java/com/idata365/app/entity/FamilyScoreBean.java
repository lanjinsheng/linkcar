package com.idata365.app.entity;

public class FamilyScoreBean
{
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
