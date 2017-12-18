package com.idata365.app.entity;

public class UsersAccountParamBean
{
	private long userId;
	
	private int enableStranger;
	
	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public int getEnableStranger()
	{
		return enableStranger;
	}

	public void setEnableStranger(int enableStranger)
	{
		this.enableStranger = enableStranger;
	}
}
