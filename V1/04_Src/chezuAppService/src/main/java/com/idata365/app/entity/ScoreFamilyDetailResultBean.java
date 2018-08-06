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
	
	private String isApplied;
	
	//俱乐部顺风车红点
	private String isHaveRide;
	
	//是否能申请加入
	private String isCanApply;
	
	//创建俱乐部页面俱乐部ICON小红点---新挑战宝箱
	private int isHaveBox;
	
	public String getIsHaveRide() {
		return isHaveRide;
	}

	public void setIsHaveRide(String isHaveRide) {
		this.isHaveRide = isHaveRide;
	}

	public String getIsCanApply() {
		return isCanApply;
	}

	public void setIsCanApply(String isCanApply) {
		this.isCanApply = isCanApply;
	}

	public String getIsApplied() {
		return isApplied;
	}

	public void setIsApplied(String isApplied) {
		this.isApplied = isApplied;
	}

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
	public int getIsHaveBox() {
		return isHaveBox;
	}

	public void setIsHaveBox(int isHaveBox) {
		this.isHaveBox = isHaveBox;
	}
}
