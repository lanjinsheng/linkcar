package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyInviteResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2337161101604080023L;

	private String userId;
	
	private String familyId;
	
	private String imgUrl;
	
	private String name;
	
	private String activeTime;
	
	private String applyDate;
	
	private String msgStr;
	
	private String status;
	
	private String num;
	
	private String typeValue;
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

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

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getApplyDate()
	{
		return applyDate;
	}

	public void setApplyDate(String applyDate)
	{
		this.applyDate = applyDate;
	}

	public String getMsgStr()
	{
		return msgStr;
	}

	public void setMsgStr(String msgStr)
	{
		this.msgStr = msgStr;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
	
}
