package com.idata365.app.entity;

public class FamilyResultBean
{
	private int myFamilyId;
	
	private String myFamilyName;
	
	private String myFamilyImgUrl;
	
	private int orderNo;
	
	private String familyType;
	
	private int userRole;	//老司机：1；漂移手：2；渣土车司机：3；点头司机：4；红眼司机：5；飙车党：6；煎饼侠：7
	
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

	public String getFamilyType()
	{
		return familyType;
	}

	public void setFamilyType(String familyType)
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
