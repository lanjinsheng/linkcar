package com.idata365.app.entity;

public class FamilyInfoBean
{
	private long id;
	
	private long createUserId;
	
	private int familyType;
	
	private String familyName;

	private String imgUrl;
	
	public int getFamilyType()
	{
		return familyType;
	}

	public void setFamilyType(int familyType)
	{
		this.familyType = familyType;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getCreateUserId()
	{
		return createUserId;
	}

	public void setCreateUserId(long createUserId)
	{
		this.createUserId = createUserId;
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
