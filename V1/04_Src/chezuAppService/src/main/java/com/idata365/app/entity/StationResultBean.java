package com.idata365.app.entity;

public class StationResultBean
{
	//我方小马扎：MINE_HOLD；我方停车：MINE_STOP；对方小马扎：COMPETITOR_HOLD；对方停车：COMPETITOR_STOP；空车位：NO_PEOPLE
	private String status;
	
	private int stationId;

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public int getStationId()
	{
		return stationId;
	}

	public void setStationId(int stationId)
	{
		this.stationId = stationId;
	}
}
