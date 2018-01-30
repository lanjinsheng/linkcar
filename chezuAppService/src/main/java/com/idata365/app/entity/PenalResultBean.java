package com.idata365.app.entity;

import java.io.Serializable;

public class PenalResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -111309710189325176L;
	private String penalStatus;

	public String getPenalStatus()
	{
		return penalStatus;
	}

	public void setPenalStatus(String penalStatus)
	{
		this.penalStatus = penalStatus;
	}
}
