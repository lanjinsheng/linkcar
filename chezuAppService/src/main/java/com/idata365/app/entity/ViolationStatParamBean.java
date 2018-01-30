package com.idata365.app.entity;

import java.io.Serializable;

public class ViolationStatParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6011292759337805103L;

	private long familyId;
	
	private String daystamp;

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public String getDaystamp()
	{
		return daystamp;
	}

	public void setDaystamp(String daystamp)
	{
		this.daystamp = daystamp;
	}
}
