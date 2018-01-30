package com.idata365.app.entity;

import java.io.Serializable;

public class SwitchLotteryParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4794729882641363396L;

	private long userId;
	
	private int onAwardId;
	
	private int offAwardId;

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public int getOnAwardId()
	{
		return onAwardId;
	}

	public void setOnAwardId(int onAwardId)
	{
		this.onAwardId = onAwardId;
	}

	public int getOffAwardId()
	{
		return offAwardId;
	}

	public void setOffAwardId(int offAwardId)
	{
		this.offAwardId = offAwardId;
	}
	
}
