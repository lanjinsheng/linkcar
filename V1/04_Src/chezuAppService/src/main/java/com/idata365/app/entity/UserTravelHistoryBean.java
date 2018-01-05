package com.idata365.app.entity;

public class UserTravelHistoryBean
{
	private int time;
	
	private double mileage;
	
	private String startTime;
	
	private String endTime;
	
	private long travelId;
	
	private long habitId;

	public int getTime()
	{
		return time;
	}

	public void setTime(int time)
	{
		this.time = time;
	}

	public double getMileage()
	{
		return mileage;
	}

	public void setMileage(double mileage)
	{
		this.mileage = mileage;
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

	public long getTravelId()
	{
		return travelId;
	}

	public void setTravelId(long travelId)
	{
		this.travelId = travelId;
	}

	public long getHabitId()
	{
		return habitId;
	}

	public void setHabitId(long habitId)
	{
		this.habitId = habitId;
	}
	
}
