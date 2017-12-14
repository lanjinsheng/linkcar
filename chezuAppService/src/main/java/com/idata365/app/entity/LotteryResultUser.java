package com.idata365.app.entity;

public class LotteryResultUser
{
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
