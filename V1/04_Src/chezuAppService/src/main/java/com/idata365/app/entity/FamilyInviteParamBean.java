package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyInviteParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5179567453242296910L;

	private long msgId;
	
	private long id;
	
	private long familyId;
	
	private long userId;
	
	private String msgStr;
	
	private String createTime;
	
	private int inviteType;	//1:用户主动申请   2:家族邀请

	public long getMsgId()
	{
		return msgId;
	}

	public void setMsgId(long msgId)
	{
		this.msgId = msgId;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public String getMsgStr()
	{
		return msgStr;
	}

	public void setMsgStr(String msgStr)
	{
		this.msgStr = msgStr;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public int getInviteType() {
		return inviteType;
	}

	public void setInviteType(int inviteType) {
		this.inviteType = inviteType;
	}

	
}
