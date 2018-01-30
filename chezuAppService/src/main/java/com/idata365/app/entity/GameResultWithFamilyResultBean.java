package com.idata365.app.entity;

import java.io.Serializable;

public class GameResultWithFamilyResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1307533253997071201L;

	private String todayRole;
	
	private String nameUrl;
	
	private String name;
	
	private String userId;
	
	private String score;
	
	private String role;
	
	private String tomorrowRole;

	public String getTodayRole()
	{
		return todayRole;
	}

	public void setTodayRole(String todayRole)
	{
		this.todayRole = todayRole;
	}

	public String getNameUrl()
	{
		return nameUrl;
	}

	public void setNameUrl(String nameUrl)
	{
		this.nameUrl = nameUrl;
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

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String getTomorrowRole()
	{
		return tomorrowRole;
	}

	public void setTomorrowRole(String tomorrowRole)
	{
		this.tomorrowRole = tomorrowRole;
	}
	
}
