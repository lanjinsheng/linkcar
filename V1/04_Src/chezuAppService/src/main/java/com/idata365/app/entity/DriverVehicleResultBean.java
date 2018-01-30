package com.idata365.app.entity;

import java.io.Serializable;

public class DriverVehicleResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4795839139681133238L;

	private int isDrivingEdit;
	
	private int isTravelEdit;

	public int getIsDrivingEdit()
	{
		return isDrivingEdit;
	}

	public void setIsDrivingEdit(int isDrivingEdit)
	{
		this.isDrivingEdit = isDrivingEdit;
	}

	public int getIsTravelEdit()
	{
		return isTravelEdit;
	}

	public void setIsTravelEdit(int isTravelEdit)
	{
		this.isTravelEdit = isTravelEdit;
	}
}
