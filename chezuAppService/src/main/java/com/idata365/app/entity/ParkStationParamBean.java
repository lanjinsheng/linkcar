package com.idata365.app.entity;

import java.io.Serializable;

public class ParkStationParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 817232091885023084L;

	private long familyRelationId;
	
	private String status;
	
	private String updateTime;

	public long getFamilyRelationId()
	{
		return familyRelationId;
	}

	public void setFamilyRelationId(long familyRelationId)
	{
		this.familyRelationId = familyRelationId;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}
}
