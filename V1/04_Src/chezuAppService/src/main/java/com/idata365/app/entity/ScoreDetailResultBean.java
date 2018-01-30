package com.idata365.app.entity;

import java.io.Serializable;
import java.util.List;

public class ScoreDetailResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5418850383967226051L;

	//[0] Times;[1] Proportion; [2] score
	private List<String> mileageArr;
	
	private List<String> timeArr;
	
	private List<String> brakeTimesArr;
	
	private List<String> turnTimesArr;
	
	private List<String> speedTimesArr;
	
	private List<String> overspeedArr;
	
	private List<String> maxspeedArr;
	
	private List<String> tiredDriveArr;
	
	private List<String> phoneTimesArr;
	
	private List<String> weatherArr;
	
	private List<String> walkArr;

	public List<String> getMileageArr()
	{
		return mileageArr;
	}

	public void setMileageArr(List<String> mileageArr)
	{
		this.mileageArr = mileageArr;
	}

	public List<String> getTimeArr()
	{
		return timeArr;
	}

	public void setTimeArr(List<String> timeArr)
	{
		this.timeArr = timeArr;
	}

	public List<String> getBrakeTimesArr()
	{
		return brakeTimesArr;
	}

	public void setBrakeTimesArr(List<String> brakeTimesArr)
	{
		this.brakeTimesArr = brakeTimesArr;
	}

	public List<String> getTurnTimesArr()
	{
		return turnTimesArr;
	}

	public void setTurnTimesArr(List<String> turnTimesArr)
	{
		this.turnTimesArr = turnTimesArr;
	}

	public List<String> getSpeedTimesArr()
	{
		return speedTimesArr;
	}

	public void setSpeedTimesArr(List<String> speedTimesArr)
	{
		this.speedTimesArr = speedTimesArr;
	}

	public List<String> getOverspeedArr()
	{
		return overspeedArr;
	}

	public void setOverspeedArr(List<String> overspeedArr)
	{
		this.overspeedArr = overspeedArr;
	}

	public List<String> getMaxspeedArr()
	{
		return maxspeedArr;
	}

	public void setMaxspeedArr(List<String> maxspeedArr)
	{
		this.maxspeedArr = maxspeedArr;
	}

	public List<String> getTiredDriveArr()
	{
		return tiredDriveArr;
	}

	public void setTiredDriveArr(List<String> tiredDriveArr)
	{
		this.tiredDriveArr = tiredDriveArr;
	}

	public List<String> getPhoneTimesArr()
	{
		return phoneTimesArr;
	}

	public void setPhoneTimesArr(List<String> phoneTimesArr)
	{
		this.phoneTimesArr = phoneTimesArr;
	}

	public List<String> getWeatherArr()
	{
		return weatherArr;
	}

	public void setWeatherArr(List<String> weatherArr)
	{
		this.weatherArr = weatherArr;
	}

	public List<String> getWalkArr()
	{
		return walkArr;
	}

	public void setWalkArr(List<String> walkArr)
	{
		this.walkArr = walkArr;
	}
	
}
