package com.idata365.app.entity;

import java.io.Serializable;

public class YesterdayScoreResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6607340225620786691L;

	private String userId;
	
	private String name;
	
	private String imgUrl;
	
	private String score;
	
	private String role;
	
	private String percent;

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

	public String getScore()
	{
		return score;
	}

	public void setScore(String score)
	{
		this.score = score;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String getPercent()
	{
		return percent;
	}

	public void setPercent(String percent)
	{
		this.percent = percent;
	}
	
}
