package com.idata365.app.entity;

public class FamilyRelationBean
{
	private long familyId;
	
	private long selfFamilyId;
	
	private long competitorFamilyId;
	
	private long familyId1;
	
	private long familyId2;

	private String daystamp;
	
	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public long getSelfFamilyId()
	{
		return selfFamilyId;
	}

	public void setSelfFamilyId(long selfFamilyId)
	{
		this.selfFamilyId = selfFamilyId;
	}

	public long getCompetitorFamilyId()
	{
		return competitorFamilyId;
	}

	public void setCompetitorFamilyId(long competitorFamilyId)
	{
		this.competitorFamilyId = competitorFamilyId;
	}

	public long getFamilyId1()
	{
		return familyId1;
	}

	public void setFamilyId1(long familyId1)
	{
		this.familyId1 = familyId1;
	}

	public long getFamilyId2()
	{
		return familyId2;
	}

	public void setFamilyId2(long familyId2)
	{
		this.familyId2 = familyId2;
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
