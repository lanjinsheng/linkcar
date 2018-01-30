package com.idata365.app.entity;

import java.io.Serializable;

public class LotteryBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9028222530420610947L;

	private long userId;
	
	private int awardId;
	
	private int awardCount;
	
	private int count;
	
	private int addedCount;
	
	private String daystamp;

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

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getAddedCount()
	{
		return addedCount;
	}

	public void setAddedCount(int addedCount)
	{
		this.addedCount = addedCount;
	}

	public String getDaystamp()
	{
		return daystamp;
	}

	public void setDaystamp(String daystamp)
	{
		this.daystamp = daystamp;
	}
	
}
