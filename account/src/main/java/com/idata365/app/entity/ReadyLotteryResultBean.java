package com.idata365.app.entity;

import java.io.Serializable;

public class ReadyLotteryResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3902912765423869443L;

	private String awardId;
	
	private String awardCount;
	
	private String awardTotalCount;

	public String getAwardId()
	{
		return awardId;
	}

	public void setAwardId(String awardId)
	{
		this.awardId = awardId;
	}

	public String getAwardCount()
	{
		return awardCount;
	}

	public void setAwardCount(String awardCount)
	{
		this.awardCount = awardCount;
	}

	public String getAwardTotalCount()
	{
		return awardTotalCount;
	}

	public void setAwardTotalCount(String awardTotalCount)
	{
		this.awardTotalCount = awardTotalCount;
	}
}
