package com.idata365.app.entity;

import java.io.Serializable;

public class MyFamilyInfoBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5201147764240200670L;

	private int myFamilyId;
	
	private String myFamilyName;
	
	private String myFamilyImgUrl;
	
	private int orderNo;
	
	private int familyType;
	
	private int userRole;
	
	private int posHoldCounts;
	
	private int competitorId;
	
	private String competitorName;
	
	private String competitorImgUrl;

	public int getMyFamilyId()
	{
		return myFamilyId;
	}

	public void setMyFamilyId(int myFamilyId)
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

	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
	{
		this.orderNo = orderNo;
	}

	public int getFamilyType()
	{
		return familyType;
	}

	public void setFamilyType(int familyType)
	{
		this.familyType = familyType;
	}

	public int getUserRole()
	{
		return userRole;
	}

	public void setUserRole(int userRole)
	{
		this.userRole = userRole;
	}

	public int getPosHoldCounts()
	{
		return posHoldCounts;
	}

	public void setPosHoldCounts(int posHoldCounts)
	{
		this.posHoldCounts = posHoldCounts;
	}

	public int getCompetitorId()
	{
		return competitorId;
	}

	public void setCompetitorId(int competitorId)
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
