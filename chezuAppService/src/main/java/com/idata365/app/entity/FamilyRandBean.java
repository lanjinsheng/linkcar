package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyRandBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2853300121503258115L;

	private String imgUrl;
	
	private int num;
	
	private String name;
	
	private long familyId;
	
	private long userId;
	
	private int familyType;
	
	private String notifyMsg;
	
	public int getFamilyType() {
		return familyType;
	}

	public void setFamilyType(int familyType) {
		this.familyType = familyType;
	}

	public String getNotifyMsg() {
		return notifyMsg;
	}

	public void setNotifyMsg(String notifyMsg) {
		this.notifyMsg = notifyMsg;
	}

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

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
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

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

}
