package com.idata365.app.entity;

import java.io.Serializable;
import java.util.List;

public class ScoreFamilyDetailBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8909559249987719702L;

	private String imgUrl;
	
	private String familyName;
	
	private int orderNo;
	
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

	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
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
