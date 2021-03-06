package com.idata365.app.entity;

import java.io.Serializable;

public class CompetitorFamilyInfoResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3906895791687398344L;

	private String imgUrl;
	
	private String competitorFamilyId;
	
	private String familyName;
	
	private String countdown;

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getCompetitorFamilyId()
	{
		return competitorFamilyId;
	}

	public void setCompetitorFamilyId(String competitorFamilyId)
	{
		this.competitorFamilyId = competitorFamilyId;
	}

	public String getFamilyName()
	{
		return familyName;
	}

	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}

	public String getCountdown()
	{
		return countdown;
	}

	public void setCountdown(String countdown)
	{
		this.countdown = countdown;
	}
}
