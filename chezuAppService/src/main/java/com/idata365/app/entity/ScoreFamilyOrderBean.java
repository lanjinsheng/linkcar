package com.idata365.app.entity;

public class ScoreFamilyOrderBean
{
	private String imgUrl;
	
	private long familyId;
	
	private int orderChange;
	
	private int orderNo;
	
	private String name;
	
	private double score;

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public int getOrderChange()
	{
		return orderChange;
	}

	public void setOrderChange(int orderChange)
	{
		this.orderChange = orderChange;
	}

	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getScore()
	{
		return score;
	}

	public void setScore(double score)
	{
		this.score = score;
	}
	
}
