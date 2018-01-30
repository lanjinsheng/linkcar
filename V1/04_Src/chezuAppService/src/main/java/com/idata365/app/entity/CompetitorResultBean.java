package com.idata365.app.entity;

import java.io.Serializable;

public class CompetitorResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 332022778514848708L;

	private FamilyCompetitorResultBean gameObj;
	
	private FamilyCompetitorResultBean competitorObj;
	
	public FamilyCompetitorResultBean getGameObj()
	{
		return gameObj;
	}

	public void setGameObj(FamilyCompetitorResultBean gameObj)
	{
		this.gameObj = gameObj;
	}

	public FamilyCompetitorResultBean getCompetitorObj()
	{
		return competitorObj;
	}

	public void setCompetitorObj(FamilyCompetitorResultBean competitorObj)
	{
		this.competitorObj = competitorObj;
	}
	
}
