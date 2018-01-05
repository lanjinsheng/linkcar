package com.idata365.app.entity;

public class UserTravelHistoryResultBean
{
	private String time;
	
	private String mileage;
	
	private String startToEnd;
	
	private String travelId;
	
	private String habitId;

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getMileage()
	{
		return mileage;
	}

	public void setMileage(String mileage)
	{
		this.mileage = mileage;
	}

	public String getStartToEnd()
	{
		return startToEnd;
	}

	public void setStartToEnd(String startToEnd)
	{
		this.startToEnd = startToEnd;
	}

	public String getTravelId()
	{
		return travelId;
	}

	public void setTravelId(String travelId)
	{
		this.travelId = travelId;
	}

	public String getHabitId()
	{
		return habitId;
	}

	public void setHabitId(String habitId)
	{
		this.habitId = habitId;
	}
	
}
