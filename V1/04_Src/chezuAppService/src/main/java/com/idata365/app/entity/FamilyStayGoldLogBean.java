package com.idata365.app.entity;

/**
 * 
 * @className:com.idata365.app.entity.FamilyStayGoldLogBean
 * @description:TODO
 * @date:2018年1月25日 下午10:08:18
 * @author:CaiFengYao
 */
public class FamilyStayGoldLogBean
{
	private long familyId;
	// 连续登榜天数
	private int goldCountDays;
	private int lev;
	private int continueFlag;

	public int getContinueFlag()
	{
		return continueFlag;
	}

	public void setContinueFlag(int continueFlag)
	{
		this.continueFlag = continueFlag;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public int getGoldCountDays()
	{
		return goldCountDays;
	}

	public void setGoldCountDays(int goldCountDays)
	{
		this.goldCountDays = goldCountDays;
	}

	public int getLev()
	{
		return lev;
	}

	public void setLev(int lev)
	{
		this.lev = lev;
	}

}
