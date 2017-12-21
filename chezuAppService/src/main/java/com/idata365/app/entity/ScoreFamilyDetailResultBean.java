package com.idata365.app.entity;

import java.util.List;

public class ScoreFamilyDetailResultBean
{
	private String imgUrl;
	
	private String familyName;
	
	private String orderNo;
	
	private List<String> familys;

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getFamilyName()
	{
		return familyName;
	}

	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public List<String> getFamilys()
	{
		return familys;
	}

	public void setFamilys(List<String> familys)
	{
		this.familys = familys;
	}
	
}
