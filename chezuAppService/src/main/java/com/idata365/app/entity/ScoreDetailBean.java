package com.idata365.app.entity;

/**
 * 得分详情
 * @author Administrator
 *
 */
public class ScoreDetailBean
{
	//里程
	private double mileage;
	
	//里程权重
	private double mileageProportion;
	
	//里程得分
	private double mileageScore;
	
	//时长
	private double time;
	
	//时长权重
	private double timeProportion;
	
	//时长得分
	private double timeScore;
	
	//急刹车次数
	private int brakeTimes;
	
	//急刹车权重
	private double brakeTimesProportion;
	
	//急刹车得分
	private double brakeTimesScore;
	
	//急转弯次数
	private int turnTimes;
	
	//急转弯权重
	private double turnTimesProportion;
	
	//急转弯得分
	private double turnTimesScore;
	
	//急加速次数
	private int speedTimes;
	
	//急加速权重
	private double speedTimesProportion;
	
	//急加速得分
	private double speedTimesScore;
	
	//超速行驶
	private int overspeedTimes;
	
	//超速行驶权重
	private double overspeedTimesProportion;
	
	//超速行驶得分
	private double overspeedTimesScore;
	
	//最大速度
	private double maxspeed;
	
	//最大速度权重
	private double maxspeedProportion;
	
	//最大速度得分
	private double maxspeedScore;
	
	//疲劳驾驶时长
	private double tiredDrive;
	
	//疲劳驾驶权重
	private double tiredDriveProportion;
	
	//疲劳驾驶得分
	private double tiredDriveScore;
	
	//使用手机次数
	private int phoneTimes;
	
	//使用手机权重
	private double phoneTimesProportion;
	
	//使用手机得分
	private double phoneTimesScore;
	
	//天气
	private String weather;
	
	//天气权重
	private double weatherProportion;
	
	//天气得分
	private double weatherScore;
	
	//步行步数
	private int walk;
	
	//步行权重
	private double walkProportion;
	
	//步行得分
	private double walkScore;

	public double getMileage()
	{
		return mileage;
	}

	public void setMileage(double mileage)
	{
		this.mileage = mileage;
	}

	public double getMileageProportion()
	{
		return mileageProportion;
	}

	public void setMileageProportion(double mileageProportion)
	{
		this.mileageProportion = mileageProportion;
	}

	public double getMileageScore()
	{
		return mileageScore;
	}

	public void setMileageScore(double mileageScore)
	{
		this.mileageScore = mileageScore;
	}

	public double getTime()
	{
		return time;
	}

	public void setTime(double time)
	{
		this.time = time;
	}

	public double getTimeProportion()
	{
		return timeProportion;
	}

	public void setTimeProportion(double timeProportion)
	{
		this.timeProportion = timeProportion;
	}

	public double getTimeScore()
	{
		return timeScore;
	}

	public void setTimeScore(double timeScore)
	{
		this.timeScore = timeScore;
	}

	public int getBrakeTimes()
	{
		return brakeTimes;
	}

	public void setBrakeTimes(int brakeTimes)
	{
		this.brakeTimes = brakeTimes;
	}

	public double getBrakeTimesProportion()
	{
		return brakeTimesProportion;
	}

	public void setBrakeTimesProportion(double brakeTimesProportion)
	{
		this.brakeTimesProportion = brakeTimesProportion;
	}

	public double getBrakeTimesScore()
	{
		return brakeTimesScore;
	}

	public void setBrakeTimesScore(double brakeTimesScore)
	{
		this.brakeTimesScore = brakeTimesScore;
	}

	public int getTurnTimes()
	{
		return turnTimes;
	}

	public void setTurnTimes(int turnTimes)
	{
		this.turnTimes = turnTimes;
	}

	public double getTurnTimesProportion()
	{
		return turnTimesProportion;
	}

	public void setTurnTimesProportion(double turnTimesProportion)
	{
		this.turnTimesProportion = turnTimesProportion;
	}

	public double getTurnTimesScore()
	{
		return turnTimesScore;
	}

	public void setTurnTimesScore(double turnTimesScore)
	{
		this.turnTimesScore = turnTimesScore;
	}

	public int getSpeedTimes()
	{
		return speedTimes;
	}

	public void setSpeedTimes(int speedTimes)
	{
		this.speedTimes = speedTimes;
	}

	public double getSpeedTimesProportion()
	{
		return speedTimesProportion;
	}

	public void setSpeedTimesProportion(double speedTimesProportion)
	{
		this.speedTimesProportion = speedTimesProportion;
	}

	public double getSpeedTimesScore()
	{
		return speedTimesScore;
	}

	public void setSpeedTimesScore(double speedTimesScore)
	{
		this.speedTimesScore = speedTimesScore;
	}

	public int getOverspeedTimes()
	{
		return overspeedTimes;
	}

	public void setOverspeedTimes(int overspeedTimes)
	{
		this.overspeedTimes = overspeedTimes;
	}

	public double getOverspeedTimesProportion()
	{
		return overspeedTimesProportion;
	}

	public void setOverspeedTimesProportion(double overspeedTimesProportion)
	{
		this.overspeedTimesProportion = overspeedTimesProportion;
	}

	public double getOverspeedTimesScore()
	{
		return overspeedTimesScore;
	}

	public void setOverspeedTimesScore(double overspeedTimesScore)
	{
		this.overspeedTimesScore = overspeedTimesScore;
	}

	public double getMaxspeed()
	{
		return maxspeed;
	}

	public void setMaxspeed(double maxspeed)
	{
		this.maxspeed = maxspeed;
	}

	public double getMaxspeedProportion()
	{
		return maxspeedProportion;
	}

	public void setMaxspeedProportion(double maxspeedProportion)
	{
		this.maxspeedProportion = maxspeedProportion;
	}

	public double getMaxspeedScore()
	{
		return maxspeedScore;
	}

	public void setMaxspeedScore(double maxspeedScore)
	{
		this.maxspeedScore = maxspeedScore;
	}

	public double getTiredDrive()
	{
		return tiredDrive;
	}

	public void setTiredDrive(double tiredDrive)
	{
		this.tiredDrive = tiredDrive;
	}

	public double getTiredDriveProportion()
	{
		return tiredDriveProportion;
	}

	public void setTiredDriveProportion(double tiredDriveProportion)
	{
		this.tiredDriveProportion = tiredDriveProportion;
	}

	public double getTiredDriveScore()
	{
		return tiredDriveScore;
	}

	public void setTiredDriveScore(double tiredDriveScore)
	{
		this.tiredDriveScore = tiredDriveScore;
	}

	public int getPhoneTimes()
	{
		return phoneTimes;
	}

	public void setPhoneTimes(int phoneTimes)
	{
		this.phoneTimes = phoneTimes;
	}

	public double getPhoneTimesProportion()
	{
		return phoneTimesProportion;
	}

	public void setPhoneTimesProportion(double phoneTimesProportion)
	{
		this.phoneTimesProportion = phoneTimesProportion;
	}

	public double getPhoneTimesScore()
	{
		return phoneTimesScore;
	}

	public void setPhoneTimesScore(double phoneTimesScore)
	{
		this.phoneTimesScore = phoneTimesScore;
	}

	public String getWeather()
	{
		return weather;
	}

	public void setWeather(String weather)
	{
		this.weather = weather;
	}

	public double getWeatherProportion()
	{
		return weatherProportion;
	}

	public void setWeatherProportion(double weatherProportion)
	{
		this.weatherProportion = weatherProportion;
	}

	public double getWeatherScore()
	{
		return weatherScore;
	}

	public void setWeatherScore(double weatherScore)
	{
		this.weatherScore = weatherScore;
	}

	public int getWalk()
	{
		return walk;
	}

	public void setWalk(int walk)
	{
		this.walk = walk;
	}

	public double getWalkProportion()
	{
		return walkProportion;
	}

	public void setWalkProportion(double walkProportion)
	{
		this.walkProportion = walkProportion;
	}

	public double getWalkScore()
	{
		return walkScore;
	}

	public void setWalkScore(double walkScore)
	{
		this.walkScore = walkScore;
	}
	
}
