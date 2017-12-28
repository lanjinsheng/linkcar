package com.idata365.app.entity;

public class GameFamilyParamBean
{
	private long familyId;
	
	private String familyType;
	
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

	public String getFamilyType()
	{
		return familyType;
	}

	public void setFamilyType(String familyType)
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
