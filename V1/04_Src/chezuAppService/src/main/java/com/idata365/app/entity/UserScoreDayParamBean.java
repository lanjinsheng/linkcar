package com.idata365.app.entity;

import java.io.Serializable;

public class UserScoreDayParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5368554272886005441L;

	private long id;
	
	private long userId;
	private long familyId;
	
	private long userFamilyScoreId;
	
	private String daystamp;
	
	private String day;
	private Double avgScore;
	private int count;

	public Double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Double avgScore) {
		this.avgScore = avgScore;
	}

	public long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(long familyId) {
		this.familyId = familyId;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public long getUserFamilyScoreId()
	{
		return userFamilyScoreId;
	}

	public void setUserFamilyScoreId(long userFamilyScoreId)
	{
		this.userFamilyScoreId = userFamilyScoreId;
	}

	public String getDaystamp()
	{
		return daystamp;
	}

	public void setDaystamp(String daystamp)
	{
		this.daystamp = daystamp;
	}

	public String getDay()
	{
		return day;
	}

	public void setDay(String day)
	{
		this.day = day;
	}
}
