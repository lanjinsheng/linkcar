package com.idata365.app.entity;

import java.io.Serializable;

public class SigResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -55734202670968768L;

	private String sigTs;
	
	private int sigNum;

	public String getSigTs()
	{
		return sigTs;
	}

	public void setSigTs(String sigTs)
	{
		this.sigTs = sigTs;
	}

	public int getSigNum()
	{
		return sigNum;
	}

	public void setSigNum(int sigNum)
	{
		this.sigNum = sigNum;
	}
	
}
