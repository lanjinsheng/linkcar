package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreUserHistoryResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1330623291163514184L;

	private String score;
	
	private String role;
	
	private String dayStr;

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

	public String getDayStr()
	{
		return dayStr;
	}

	public void setDayStr(String dayStr)
	{
		this.dayStr = dayStr;
	}
	
}
