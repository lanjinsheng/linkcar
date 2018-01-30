package com.idata365.app.entity;

import java.io.Serializable;

public class LotteryMigrateInfoMsgParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5201599952745123204L;

	private long userId;
	
	private int start;

	private int givenId;
	
	private long familyId;
	
	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getGivenId()
	{
		return givenId;
	}

	public void setGivenId(int givenId)
	{
		this.givenId = givenId;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}
	
}
