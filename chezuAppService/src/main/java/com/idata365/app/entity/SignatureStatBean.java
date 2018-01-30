package com.idata365.app.entity;

import java.io.Serializable;

public class SignatureStatBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1532706728557453350L;

	private int num;
	
	private String sigTimestamp;

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public String getSigTimestamp()
	{
		return sigTimestamp;
	}

	public void setSigTimestamp(String sigTimestamp)
	{
		this.sigTimestamp = sigTimestamp;
	}
}
