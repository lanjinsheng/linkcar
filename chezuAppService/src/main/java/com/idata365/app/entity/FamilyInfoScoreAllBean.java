package com.idata365.app.entity;

public class FamilyInfoScoreAllBean
{
	private FamilyInfoScoreResultBean origFamily;
	
	private FamilyInfoScoreResultBean joinFamily;

	private int gamerNumnicai;
	
	public FamilyInfoScoreResultBean getOrigFamily()
	{
		return origFamily;
	}

	public void setOrigFamily(FamilyInfoScoreResultBean origFamily)
	{
		this.origFamily = origFamily;
	}

	public FamilyInfoScoreResultBean getJoinFamily()
	{
		return joinFamily;
	}

	public void setJoinFamily(FamilyInfoScoreResultBean joinFamily)
	{
		this.joinFamily = joinFamily;
	}

	public int getGamerNumnicai()
	{
		return gamerNumnicai;
	}

	public void setGamerNumnicai(int gamerNumnicai)
	{
		this.gamerNumnicai = gamerNumnicai;
	}
	
}
