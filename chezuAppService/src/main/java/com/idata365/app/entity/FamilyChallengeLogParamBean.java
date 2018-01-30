package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyChallengeLogParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8994275986767147834L;

	private long id;
	
	private long familyId;
	
	private int prevType;
	
	private int challengeType;
	
	private String challengeDay;
	
	private int familyType;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public int getPrevType()
	{
		return prevType;
	}

	public void setPrevType(int prevType)
	{
		this.prevType = prevType;
	}

	public int getChallengeType()
	{
		return challengeType;
	}

	public void setChallengeType(int challengeType)
	{
		this.challengeType = challengeType;
	}

	public String getChallengeDay()
	{
		return challengeDay;
	}

	public void setChallengeDay(String challengeDay)
	{
		this.challengeDay = challengeDay;
	}

	public int getFamilyType()
	{
		return familyType;
	}

	public void setFamilyType(int familyType)
	{
		this.familyType = familyType;
	}
	
}
