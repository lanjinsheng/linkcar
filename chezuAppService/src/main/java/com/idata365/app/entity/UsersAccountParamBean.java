package com.idata365.app.entity;

import java.io.Serializable;

public class UsersAccountParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8301341090629853750L;

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
