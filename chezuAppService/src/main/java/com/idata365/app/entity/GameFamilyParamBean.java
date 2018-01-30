package com.idata365.app.entity;

import java.io.Serializable;

public class GameFamilyParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5823249239081870803L;

	private long familyId;
	
	private int familyType;
	
	private long userId;
	
	private long stationId;

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public int getFamilyType()
	{
		return familyType;
	}

	public void setFamilyType(int familyType)
	{
		this.familyType = familyType;
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public long getStationId()
	{
		return stationId;
	}

	public void setStationId(long stationId)
	{
		this.stationId = stationId;
	}

}
