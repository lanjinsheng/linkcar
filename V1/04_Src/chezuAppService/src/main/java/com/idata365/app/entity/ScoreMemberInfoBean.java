package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreMemberInfoBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2668883625044270425L;

	private String imgUrl;
	
	private String name;
	
	private String phone;
	
	private long userId;
	
	private int role;
	
	private int yesterdayRole;
	
	private double yesterdayScore;
	
	private String joinTime;
	
	private String isCaptainFlag;
	
	private int tomorrowRole;

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public int getRole()
	{
		return role;
	}

	public void setRole(int role)
	{
		this.role = role;
	}

	public int getYesterdayRole()
	{
		return yesterdayRole;
	}

	public void setYesterdayRole(int yesterdayRole)
	{
		this.yesterdayRole = yesterdayRole;
	}

	public double getYesterdayScore()
	{
		return yesterdayScore;
	}

	public void setYesterdayScore(double yesterdayScore)
	{
		this.yesterdayScore = yesterdayScore;
	}

	public String getJoinTime()
	{
		return joinTime;
	}

	public void setJoinTime(String joinTime)
	{
		this.joinTime = joinTime;
	}

	public String getIsCaptainFlag()
	{
		return isCaptainFlag;
	}

	public void setIsCaptainFlag(String isCaptainFlag)
	{
		this.isCaptainFlag = isCaptainFlag;
	}

	public int getTomorrowRole()
	{
		return tomorrowRole;
	}

	public void setTomorrowRole(int tomorrowRole)
	{
		this.tomorrowRole = tomorrowRole;
	}
	
}
