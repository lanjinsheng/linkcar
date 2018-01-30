package com.idata365.app.entity;

import java.io.Serializable;

public class LotteryResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5842401548091675193L;

	private String awardId;
	
	private String awardCount;
	
	private String awardDesc;

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

	public String getAwardDesc()
	{
		return awardDesc;
	}

	public void setAwardDesc(String awardDesc)
	{
		this.awardDesc = awardDesc;
	}
}
