package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreUserBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2092090049863507085L;

	private String imgUrl;
	
	private String name;
	
	private long userId;
	
	private double score;

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public double getScore()
	{
		return score;
	}

	public void setScore(double score)
	{
		this.score = score;
	}
	
}
