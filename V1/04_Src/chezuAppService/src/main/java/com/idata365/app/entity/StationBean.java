package com.idata365.app.entity;

import java.io.Serializable;

public class StationBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6002206141828702481L;

	//我方小马扎：MINE_HOLD；我方停车：MINE_STOP；对方小马扎：COMPETITOR_HOLD；对方停车：COMPETITOR_STOP；空车位：NO_PEOPLE
	private String status;
	
	private long stationId;
	
	private long familyId;

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public long getStationId()
	{
		return stationId;
	}

	public void setStationId(long stationId)
	{
		this.stationId = stationId;
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
