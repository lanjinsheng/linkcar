package com.idata365.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelDetailResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 934303938345963675L;

	private String time;
	
	private String mileage;
	
	private String nightDrive;
	
	private String turnTimes;
	
	private String tiredDrive;
	
	private String speedTimes;
	
	private String brakeTimes;
	
	private String maxspeed;
	
	private String overspeedTimes;
	
	private Map<String,Object> gpsMap=new HashMap<String,Object>();
	private List<Map<String,String>> userTravelLotterys=new ArrayList<Map<String,String>>();;

	public List<Map<String, String>> getUserTravelLotterys() {
		return userTravelLotterys;
	}

	public void setUserTravelLotterys(List<Map<String, String>> userTravelLotterys) {
		this.userTravelLotterys = userTravelLotterys;
	}

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

	public String getNightDrive()
	{
		return nightDrive;
	}

	public void setNightDrive(String nightDrive)
	{
		this.nightDrive = nightDrive;
	}

	public String getTurnTimes()
	{
		return turnTimes;
	}

	public void setTurnTimes(String turnTimes)
	{
		this.turnTimes = turnTimes;
	}

	public String getTiredDrive()
	{
		return tiredDrive;
	}

	public void setTiredDrive(String tiredDrive)
	{
		this.tiredDrive = tiredDrive;
	}

	public String getSpeedTimes()
	{
		return speedTimes;
	}

	public void setSpeedTimes(String speedTimes)
	{
		this.speedTimes = speedTimes;
	}

	public String getBrakeTimes()
	{
		return brakeTimes;
	}

	public void setBrakeTimes(String brakeTimes)
	{
		this.brakeTimes = brakeTimes;
	}

	public String getMaxspeed()
	{
		return maxspeed;
	}

	public void setMaxspeed(String maxspeed)
	{
		this.maxspeed = maxspeed;
	}

	public String getOverspeedTimes()
	{
		return overspeedTimes;
	}

	public void setOverspeedTimes(String overspeedTimes)
	{
		this.overspeedTimes = overspeedTimes;
	}

	public Map<String, Object> getGpsMap()
	{
		return gpsMap;
	}

	public void setGpsMap(Map<String, Object> gpsMap)
	{
		this.gpsMap = gpsMap;
	}
	
}
