package com.idata365.app.entity;

public class FamilyDriveDayStatBean
{
	//
	private int orderNo;
	
	//
	private double mileage;
	
	//
	private double time;
	
	private double score;
	
	//
	private int speedTimes;

	private int brakeTimes;
	
	private int turnTimes;
	
	private int overspeedTimes;
	
	private int nightDriveTimes;
	
	private int illegalStopTimes;
	
	private int tiredDriveTimes;	//疲劳驾驶次数
	
	private int useHoldNum;	//小马扎使用数量
	
	private int usePhoneTimes;
	
	private double maxspeed;
	
	private double familyNumFactor;
	
	private double roleFactor;
	
	private double memberFactor;
	
	private double familyLevelFactor;

	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
	{
		this.orderNo = orderNo;
	}

	public double getMileage()
	{
		return mileage;
	}

	public void setMileage(double mileage)
	{
		this.mileage = mileage;
	}

	public double getTime()
	{
		return time;
	}

	public void setTime(double time)
	{
		this.time = time;
	}

	public double getScore()
	{
		return score;
	}

	public void setScore(double score)
	{
		this.score = score;
	}

	public int getSpeedTimes()
	{
		return speedTimes;
	}

	public void setSpeedTimes(int speedTimes)
	{
		this.speedTimes = speedTimes;
	}

	public int getBrakeTimes()
	{
		return brakeTimes;
	}

	public void setBrakeTimes(int brakeTimes)
	{
		this.brakeTimes = brakeTimes;
	}

	public int getTurnTimes()
	{
		return turnTimes;
	}

	public void setTurnTimes(int turnTimes)
	{
		this.turnTimes = turnTimes;
	}

	public int getOverspeedTimes()
	{
		return overspeedTimes;
	}

	public void setOverspeedTimes(int overspeedTimes)
	{
		this.overspeedTimes = overspeedTimes;
	}

	public int getNightDriveTimes()
	{
		return nightDriveTimes;
	}

	public void setNightDriveTimes(int nightDriveTimes)
	{
		this.nightDriveTimes = nightDriveTimes;
	}

	public int getIllegalStopTimes()
	{
		return illegalStopTimes;
	}

	public void setIllegalStopTimes(int illegalStopTimes)
	{
		this.illegalStopTimes = illegalStopTimes;
	}

	public int getTiredDriveTimes()
	{
		return tiredDriveTimes;
	}

	public void setTiredDriveTimes(int tiredDriveTimes)
	{
		this.tiredDriveTimes = tiredDriveTimes;
	}

	public int getUseHoldNum()
	{
		return useHoldNum;
	}

	public void setUseHoldNum(int useHoldNum)
	{
		this.useHoldNum = useHoldNum;
	}

	public int getUsePhoneTimes()
	{
		return usePhoneTimes;
	}

	public void setUsePhoneTimes(int usePhoneTimes)
	{
		this.usePhoneTimes = usePhoneTimes;
	}

	public double getMaxspeed()
	{
		return maxspeed;
	}

	public void setMaxspeed(double maxspeed)
	{
		this.maxspeed = maxspeed;
	}

	public double getFamilyNumFactor()
	{
		return familyNumFactor;
	}

	public void setFamilyNumFactor(double familyNumFactor)
	{
		this.familyNumFactor = familyNumFactor;
	}

	public double getRoleFactor()
	{
		return roleFactor;
	}

	public void setRoleFactor(double roleFactor)
	{
		this.roleFactor = roleFactor;
	}

	public double getMemberFactor()
	{
		return memberFactor;
	}

	public void setMemberFactor(double memberFactor)
	{
		this.memberFactor = memberFactor;
	}

	public double getFamilyLevelFactor()
	{
		return familyLevelFactor;
	}

	public void setFamilyLevelFactor(double familyLevelFactor)
	{
		this.familyLevelFactor = familyLevelFactor;
	}

}
