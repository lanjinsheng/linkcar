package com.idata365.app.entity;

import java.io.Serializable;

public class ReadyLotteryBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6797632685374950495L;

	private long id;
	
	private long userId;
	
	private int awardId;
	
	private int awardCount;
	
	private int awardTotalCount;
	
	private String daystamp;

	private int count;
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

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

	public int getAwardTotalCount()
	{
		return awardTotalCount;
	}

	public void setAwardTotalCount(int awardTotalCount)
	{
		this.awardTotalCount = awardTotalCount;
	}

	public String getDaystamp()
	{
		return daystamp;
	}

	public void setDaystamp(String daystamp)
	{
		this.daystamp = daystamp;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
	
}
