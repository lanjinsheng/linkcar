package com.idata365.app.entity;

public class JudgeChallengeResultBean
{
	private String challengeFlag;
	
	private String challengeMsg;
	
	private FamilyInfoResultBean familyObj;

	public String getChallengeFlag()
	{
		return challengeFlag;
	}

	public void setChallengeFlag(String challengeFlag)
	{
		this.challengeFlag = challengeFlag;
	}

	public String getChallengeMsg()
	{
		return challengeMsg;
	}

	public void setChallengeMsg(String challengeMsg)
	{
		this.challengeMsg = challengeMsg;
	}

	public FamilyInfoResultBean getFamilyObj()
	{
		return familyObj;
	}

	public void setFamilyObj(FamilyInfoResultBean familyObj)
	{
		this.familyObj = familyObj;
	}
}
