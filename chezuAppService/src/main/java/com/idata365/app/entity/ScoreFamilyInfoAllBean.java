package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreFamilyInfoAllBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -916062582068042586L;

	private ScoreFamilyInfoBean oriFamily;
	
	private ScoreFamilyInfoBean joinFamily;

	public ScoreFamilyInfoBean getOriFamily()
	{
		return oriFamily;
	}

	public void setOriFamily(ScoreFamilyInfoBean oriFamily)
	{
		this.oriFamily = oriFamily;
	}

	public ScoreFamilyInfoBean getJoinFamily()
	{
		return joinFamily;
	}

	public void setJoinFamily(ScoreFamilyInfoBean joinFamily)
	{
		this.joinFamily = joinFamily;
	}
	
}
