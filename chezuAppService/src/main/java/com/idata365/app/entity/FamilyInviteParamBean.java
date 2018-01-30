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
	
	private int sendInviteMsg;	//1：通过邀请码加入；2：通过首页推荐加入

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

	public int getSendInviteMsg()
	{
		return sendInviteMsg;
	}

	public void setSendInviteMsg(int sendInviteMsg)
	{
		this.sendInviteMsg = sendInviteMsg;
	}
	
}
