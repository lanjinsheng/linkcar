package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreUserHistoryParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5218855462898102875L;

	private String startDay;
	
	private long userId;
	
	private long familyId;
	
	private int start;
	
	private String day;
	
	private long userFamilyScoreId;

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

	public long getUserFamilyScoreId()
	{
		return userFamilyScoreId;
	}

	public void setUserFamilyScoreId(long userFamilyScoreId)
	{
		this.userFamilyScoreId = userFamilyScoreId;
	}
	
}
