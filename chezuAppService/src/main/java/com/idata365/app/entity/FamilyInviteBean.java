package com.idata365.app.entity;

public class FamilyInviteBean
{
	private long userId;
	
	private long familyId;
	
	private String imgUrl;
	
	private String name;
	
	private String phone;
	
	private String userSource;
	
	private String applyDate;
	
	private String msgStr;
	
	private long seqNo;
	
	private int status;

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
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

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getUserSource()
	{
		return userSource;
	}

	public void setUserSource(String userSource)
	{
		this.userSource = userSource;
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

	public long getSeqNo()
	{
		return seqNo;
	}

	public void setSeqNo(long seqNo)
	{
		this.seqNo = seqNo;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}
	
}
