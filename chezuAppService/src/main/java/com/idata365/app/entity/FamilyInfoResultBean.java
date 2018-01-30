package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyInfoResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6862939946983654497L;

	private String familyId;
	
	private String familyType;
	
	private String familyName;
	
	private String imgUrl;

	public String getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(String familyId)
	{
		this.familyId = familyId;
	}

	public String getFamilyType()
	{
		return familyType;
	}

	public void setFamilyType(String familyType)
	{
		this.familyType = familyType;
	}

	public String getFamilyName()
	{
		return familyName;
	}

	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}
	
}
