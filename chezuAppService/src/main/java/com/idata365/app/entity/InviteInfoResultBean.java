package com.idata365.app.entity;

import java.io.Serializable;

public class InviteInfoResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4998763266012814764L;

	private String orderNo;
	
	private String inviteCode;
	
	private String familyName;

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getInviteCode()
	{
		return inviteCode;
	}

	public void setInviteCode(String inviteCode)
	{
		this.inviteCode = inviteCode;
	}

	public String getFamilyName()
	{
		return familyName;
	}

	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}
	
}
