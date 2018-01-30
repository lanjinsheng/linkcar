package com.idata365.app.entity;

import java.io.Serializable;

public class ViolationStatResultAllBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7122430084073620779L;

	private ViolationStatResultBean myBean;
	
	private ViolationStatResultBean competitorBean;

	public ViolationStatResultBean getCompetitorBean()
	{
		return competitorBean;
	}

	public void setCompetitorBean(ViolationStatResultBean competitorBean)
	{
		this.competitorBean = competitorBean;
	}

	public ViolationStatResultBean getMyBean()
	{
		return myBean;
	}

	public void setMyBean(ViolationStatResultBean myBean)
	{
		this.myBean = myBean;
	}
	
}
