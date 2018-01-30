package com.idata365.app.entity;

import java.io.Serializable;

public class TravelHistoryParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7308283331843165807L;
	private long travelId;

	public long getTravelId()
	{
		return travelId;
	}

	public void setTravelId(long travelId)
	{
		this.travelId = travelId;
	}
	
}
