package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreUserResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8263796599052689985L;

	private String imgUrl;
	
	private String percent;
	
	private String name;
	
	private String userId;
	
	private String score;

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getPercent()
	{
		return percent;
	}

	public void setPercent(String percent)
	{
		this.percent = percent;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getScore()
	{
		return score;
	}

	public void setScore(String score)
	{
		this.score = score;
	}
	
}
