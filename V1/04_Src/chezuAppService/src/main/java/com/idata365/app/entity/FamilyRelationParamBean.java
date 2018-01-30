package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyRelationParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2064782211102648259L;

	private int id;
	
	private long selfFamilyId;
	
	private long competitorFamilyId;
	
	private String daystamp;
	
	private long familyRelationId;

	public long getFamilyRelationId()
	{
		return familyRelationId;
	}

	public void setFamilyRelationId(long familyRelationId)
	{
		this.familyRelationId = familyRelationId;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

	public String getDaystamp()
	{
		return daystamp;
	}

	public void setDaystamp(String daystamp)
	{
		this.daystamp = daystamp;
	}
	
}
