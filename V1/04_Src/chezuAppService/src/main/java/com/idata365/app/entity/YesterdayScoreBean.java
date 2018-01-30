package com.idata365.app.entity;

import java.io.Serializable;

public class YesterdayScoreBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3579761842814214565L;

	private long userId;
	
	private String name;
	
	private String phone;
	
	private String imgUrl;
	
	private double score;
	
	private int role;

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
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

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public double getScore()
	{
		return score;
	}

	public void setScore(double score)
	{
		this.score = score;
	}

	public int getRole()
	{
		return role;
	}

	public void setRole(int role)
	{
		this.role = role;
	}
	
}
