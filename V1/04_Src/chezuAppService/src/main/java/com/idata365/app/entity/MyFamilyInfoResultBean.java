package com.idata365.app.entity;

import java.io.Serializable;

public class MyFamilyInfoResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3777871271667935143L;

	private String myFamilyId;
	
	private String myFamilyName;
	
	private String myFamilyImgUrl;
	
	private String orderNo;
	
	private String familyType;
	
	private String userRole;
	
	private String posHoldCounts;
	
	private String competitorId;
	
	private String competitorName;
	
	private String competitorImgUrl;

	public String getMyFamilyId()
	{
		return myFamilyId;
	}

	public void setMyFamilyId(String myFamilyId)
	{
		this.myFamilyId = myFamilyId;
	}

	public String getMyFamilyName()
	{
		return myFamilyName;
	}

	public void setMyFamilyName(String myFamilyName)
	{
		this.myFamilyName = myFamilyName;
	}

	public String getMyFamilyImgUrl()
	{
		return myFamilyImgUrl;
	}

	public void setMyFamilyImgUrl(String myFamilyImgUrl)
	{
		this.myFamilyImgUrl = myFamilyImgUrl;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getFamilyType()
	{
		return familyType;
	}

	public void setFamilyType(String familyType)
	{
		this.familyType = familyType;
	}

	public String getUserRole()
	{
		return userRole;
	}

	public void setUserRole(String userRole)
	{
		this.userRole = userRole;
	}

	public String getPosHoldCounts()
	{
		return posHoldCounts;
	}

	public void setPosHoldCounts(String posHoldCounts)
	{
		this.posHoldCounts = posHoldCounts;
	}

	public String getCompetitorId()
	{
		return competitorId;
	}

	public void setCompetitorId(String competitorId)
	{
		this.competitorId = competitorId;
	}

	public String getCompetitorName()
	{
		return competitorName;
	}

	public void setCompetitorName(String competitorName)
	{
		this.competitorName = competitorName;
	}

	public String getCompetitorImgUrl()
	{
		return competitorImgUrl;
	}

	public void setCompetitorImgUrl(String competitorImgUrl)
	{
		this.competitorImgUrl = competitorImgUrl;
	}
}
