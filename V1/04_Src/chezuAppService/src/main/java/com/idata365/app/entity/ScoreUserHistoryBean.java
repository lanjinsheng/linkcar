package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreUserHistoryBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4820088764505051699L;

	private double score;
	
	private int role;
	
	private String dayStr;

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

	public String getDayStr()
	{
		return dayStr;
	}

	public void setDayStr(String dayStr)
	{
		this.dayStr = dayStr;
	}
	
}
