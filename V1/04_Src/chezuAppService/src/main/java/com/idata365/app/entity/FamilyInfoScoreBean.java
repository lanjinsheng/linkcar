package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyInfoScoreBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4629086197092425819L;

	private long familyId;
	
	private String imgUrl;
	
	private String name;

	private int role;
	
	private int orderNo;

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
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

	public int getRole()
	{
		return role;
	}

	public void setRole(int role)
	{
		this.role = role;
	}

	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
	{
		this.orderNo = orderNo;
	}
	
}
