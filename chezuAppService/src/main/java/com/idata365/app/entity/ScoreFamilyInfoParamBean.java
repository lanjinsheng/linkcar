package com.idata365.app.entity;

public class ScoreFamilyInfoParamBean
{
	private long userId;

	private long familyId;
	
	private String daystamp;
	
	private String day;
	
	private long travelId;
	
	private long habitId;
	
	private String month;
	
	
	
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
	
}
