package com.idata365.app.entity;

public class FamilyParamBean
{
	private long id;
	
	private long familyId;
	
	private long userId;
	
	private String provinceId;
	
	private String cityId;
	
	private boolean inviteCodeFlag;
	
	private String familyName;
	
	private String inviteCode;

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

	public String getInviteCode()
	{
		return inviteCode;
	}

	public void setInviteCode(String inviteCode)
	{
		this.inviteCode = inviteCode;
	}
	
}
