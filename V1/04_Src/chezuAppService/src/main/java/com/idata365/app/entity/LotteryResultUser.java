package com.idata365.app.entity;

import java.io.Serializable;

public class LotteryResultUser implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3346617820685707795L;

	private String userId;
	
	private String name;
	
	private String imgUrl;
	
	private String todayRole;

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getTodayRole()
	{
		return todayRole;
	}

	public void setTodayRole(String todayRole)
	{
		this.todayRole = todayRole;
	}
	
}
