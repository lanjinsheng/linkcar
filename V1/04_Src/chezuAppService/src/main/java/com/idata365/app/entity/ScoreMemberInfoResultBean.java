package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreMemberInfoResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4381063199344558779L;

	private String imgUrl;
	
	private String name;
	
	private String userId;
	
	private String role;
	
	private String yesterdayRole;
	
	private String yesterdayScore;
	
	private String joinTime;
	
	private String isCaptainFlag;
	
	private String tomorrowRole;

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

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String getYesterdayRole()
	{
		return yesterdayRole;
	}

	public void setYesterdayRole(String yesterdayRole)
	{
		this.yesterdayRole = yesterdayRole;
	}

	public String getYesterdayScore()
	{
		return yesterdayScore;
	}

	public void setYesterdayScore(String yesterdayScore)
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

	public String getTomorrowRole()
	{
		return tomorrowRole;
	}

	public void setTomorrowRole(String tomorrowRole)
	{
		this.tomorrowRole = tomorrowRole;
	}
	
}
