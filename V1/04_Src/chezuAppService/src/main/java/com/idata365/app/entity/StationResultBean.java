package com.idata365.app.entity;

import java.io.Serializable;

public class StationResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8897839788576040445L;

	//我方小马扎：MINE_HOLD；我方停车：MINE_STOP；对方小马扎：COMPETITOR_HOLD；对方停车：COMPETITOR_STOP；空车位：NO_PEOPLE
	private String status;
	
	private String stationId;
	
	private String familyId;

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStationId()
	{
		return stationId;
	}

	public void setStationId(String stationId)
	{
		this.stationId = stationId;
	}

	public String getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(String familyId)
	{
		this.familyId = familyId;
	}
	
}
