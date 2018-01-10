package com.idata365.app.entity;

public class FamilyChallengeLogBean
{
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
