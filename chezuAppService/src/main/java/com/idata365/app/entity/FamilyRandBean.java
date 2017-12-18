package com.idata365.app.entity;

public class FamilyRandBean
{
	private String imgUrl;
	
	private int num;
	
	private String name;
	
	private int familyId;
	
	//是否已经申请标记
	private String isAlreadyRecommend;

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(int familyId)
	{
		this.familyId = familyId;
	}

	public String getIsAlreadyRecommend()
	{
		return isAlreadyRecommend;
	}

	public void setIsAlreadyRecommend(String isAlreadyRecommend)
	{
		this.isAlreadyRecommend = isAlreadyRecommend;
	}
}
