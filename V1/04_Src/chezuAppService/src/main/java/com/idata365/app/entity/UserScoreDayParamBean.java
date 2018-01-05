package com.idata365.app.entity;

public class UserScoreDayParamBean
{
	private long id;
	
	private long userId;
	
	private long userFamilyScoreId;
	
	private String daystamp;
	
	private String day;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public long getUserFamilyScoreId()
	{
		return userFamilyScoreId;
	}

	public void setUserFamilyScoreId(long userFamilyScoreId)
	{
		this.userFamilyScoreId = userFamilyScoreId;
	}

	public String getDaystamp()
	{
		return daystamp;
	}

	public void setDaystamp(String daystamp)
	{
		this.daystamp = daystamp;
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
