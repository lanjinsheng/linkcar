package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyInfoScoreResultBean implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4505496189353464768L;

	private String familyId;
	
	private String imgUrl;
	
	private String name;

	private String role;
	
	private String orderNo;

	public String getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(String familyId)
	{
		this.familyId = familyId;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}
}
