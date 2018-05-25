package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyParamBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3514235459591871607L;

	private long id;
	
	private long familyId;
	
	private long userId;
	
	private int familyType;
	private int isLeader=0;
	private String provinceId;
	
	private String cityId;
	
	private boolean inviteCodeFlag;
	
	private String familyName;
	
	private String name;
	
	private String inviteCode;
	
	private String imgUrl;

	private int startPos;
	
	private String joinTime;
	
	private String daystamp;
	
	private int role;
	
	private String month;
	
	private String createTimeStr;

	private long msgId;
	
	private int status;
	
	private String startTime;
	
	private String endTime;
	
	private String timeStr;
	
	private int hadGet;
	
	
	
	public int getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(int isLeader) {
		this.isLeader = isLeader;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
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

	public int getFamilyType()
	{
		return familyType;
	}

	public void setFamilyType(int familyType)
	{
		this.familyType = familyType;
	}

	public String getProvinceId()
	{
		return provinceId;
	}

	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}

	public String getCityId()
	{
		return cityId;
	}

	public void setCityId(String cityId)
	{
		this.cityId = cityId;
	}

	public boolean isInviteCodeFlag()
	{
		return inviteCodeFlag;
	}

	public void setInviteCodeFlag(boolean inviteCodeFlag)
	{
		this.inviteCodeFlag = inviteCodeFlag;
	}

	public String getFamilyName()
	{
		return familyName;
	}

	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getInviteCode()
	{
		return inviteCode;
	}

	public void setInviteCode(String inviteCode)
	{
		this.inviteCode = inviteCode;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public int getStartPos()
	{
		return startPos;
	}

	public void setStartPos(int startPos)
	{
		this.startPos = startPos;
	}

	public String getJoinTime()
	{
		return joinTime;
	}

	public void setJoinTime(String joinTime)
	{
		this.joinTime = joinTime;
	}

	public String getDaystamp()
	{
		return daystamp;
	}

	public void setDaystamp(String daystamp)
	{
		this.daystamp = daystamp;
	}

	public int getRole()
	{
		return role;
	}

	public void setRole(int role)
	{
		this.role = role;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public String getCreateTimeStr()
	{
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr)
	{
		this.createTimeStr = createTimeStr;
	}

	public long getMsgId()
	{
		return msgId;
	}

	public void setMsgId(long msgId)
	{
		this.msgId = msgId;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public String getTimeStr()
	{
		return timeStr;
	}

	public void setTimeStr(String timeStr)
	{
		this.timeStr = timeStr;
	}

	public int getHadGet()
	{
		return hadGet;
	}

	public void setHadGet(int hadGet)
	{
		this.hadGet = hadGet;
	}
	
}
