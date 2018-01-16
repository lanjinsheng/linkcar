package com.idata365.app.entity;

public class FamilyHistoryParamBean
{
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
