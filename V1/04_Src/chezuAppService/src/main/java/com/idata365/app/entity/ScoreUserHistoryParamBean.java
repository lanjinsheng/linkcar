package com.idata365.app.entity;

public class ScoreUserHistoryParamBean
{
	private String startDay;
	
	private long userId;
	
	private long familyId;
	
	private int start;
	
	private String day;

	public String getStartDay()
	{
		return startDay;
	}

	public void setStartDay(String startDay)
	{
		this.startDay = startDay;
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public String getDay()
	{
		return day;
	}

	public void setDay(String day)
	{
		this.day = day;
	}
	
}
