package com.idata365.app.entity;

import java.io.Serializable;
import java.util.List;

public class ScoreFamilyDetailResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7687849421134095306L;

	private String imgUrl;
	
	private String familyName;
	
	private String orderNo;
	
	private List<String> familys;
	
	private String trophyNum;
	
	private String grade;
	
	private String notifyMsg;

	public String getNotifyMsg() {
		return notifyMsg;
	}

	public void setNotifyMsg(String notifyMsg) {
		this.notifyMsg = notifyMsg;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getTrophyNum() {
		return trophyNum;
	}

	public void setTrophyNum(String trophyNum) {
		this.trophyNum = trophyNum;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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
