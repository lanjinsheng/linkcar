package com.idata365.app.entity;

import java.io.Serializable;

public class JudgeChallengeResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3651360198152071081L;

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
