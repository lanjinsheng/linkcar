package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyChallengeLogBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4267062080996809285L;

	private long familyId;
	
	private int prevType;
	
	private int challengeType;

	private String challengeDay;

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
	
}
