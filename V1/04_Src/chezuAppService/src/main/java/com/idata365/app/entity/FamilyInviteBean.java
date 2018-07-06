package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

public class FamilyInviteBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5145177637477574166L;

	private String userId;
	
	private String familyId;
	
	private String imgUrl;
	
	private String name;
	
	private Date lastActiveTime;
	
	private String applyDate;
	
	private String msgStr;
	
	private String status;
	
	private int num;
	
	private int typeValue;
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(int typeValue) {
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

	public Date getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
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
