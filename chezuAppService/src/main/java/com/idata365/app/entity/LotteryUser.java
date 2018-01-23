package com.idata365.app.entity;

public class LotteryUser
{
	private int userId;
	
	private String name;
	
	private String imgUrl;
	
	private String phone;
	
	private int todayRole;
	
	private int start;

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
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

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public int getTodayRole()
	{
		return todayRole;
	}

	public void setTodayRole(int todayRole)
	{
		this.todayRole = todayRole;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}
}
