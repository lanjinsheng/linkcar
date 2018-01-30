package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyHistoryParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2852705160314584815L;

	private long id;
	
	private long familyId;
	
	private String record;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public String getRecord()
	{
		return record;
	}

	public void setRecord(String record)
	{
		this.record = record;
	}
}
