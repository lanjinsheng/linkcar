package com.idata365.app.entity;

import java.io.Serializable;

public class LotteryLogInfoParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8056300086236934397L;

	private long userId;
	
	private int awardId;
	
	private int awardCount;
	
	private int type;
	
	private String timestamp;

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public int getAwardId()
	{
		return awardId;
	}

	public void setAwardId(int awardId)
	{
		this.awardId = awardId;
	}

	public int getAwardCount()
	{
		return awardCount;
	}

	public void setAwardCount(int awardCount)
	{
		this.awardCount = awardCount;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}
	
}
