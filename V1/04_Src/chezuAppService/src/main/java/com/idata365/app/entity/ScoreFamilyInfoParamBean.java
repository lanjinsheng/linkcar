package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreFamilyInfoParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4862284110078130373L;

	private long userId;

	private long familyId;
	
	private String daystamp;
	
	private String day;
	
	private long travelId;
	
	private long habitId;
	
	private String month;
	
	private String startTime;
	
	private String endTime;
	
	private String timeStr;
	
	private String startDay;
	
	private String endDay;
	
	private long userFamilyScoreId;
	
	public long getHabitId() {
		return habitId;
	}

	public void setHabitId(long habitId) {
		this.habitId = habitId;
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

	public long getTravelId()
	{
		return travelId;
	}

	public void setTravelId(long travelId)
	{
		this.travelId = travelId;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public String getTimeStr()
	{
		return timeStr;
	}

	public void setTimeStr(String timeStr)
	{
		this.timeStr = timeStr;
	}

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

	public long getUserFamilyScoreId()
	{
		return userFamilyScoreId;
	}

	public void setUserFamilyScoreId(long userFamilyScoreId)
	{
		this.userFamilyScoreId = userFamilyScoreId;
	}
	
}
