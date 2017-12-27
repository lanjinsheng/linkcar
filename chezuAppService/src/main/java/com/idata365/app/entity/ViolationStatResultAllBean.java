package com.idata365.app.entity;

public class ViolationStatResultAllBean
{
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
