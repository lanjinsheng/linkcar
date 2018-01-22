package com.idata365.app.entity;

/**
 * 
 * @className:com.idata365.app.entity.UserAchieveBean
 * @description:TODO
 * @date:2018年1月19日 下午3:05:27
 * @author:CaiFengYao
 */
public class UserAchieveBean
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	// 成就字典id
	private int achieveId;
	// 奖励字典id
	private int awardId;
	// 奖励数量
	private int awardNum;
	// 成就等级
	private int lev;
	// 成就标识(0.未完成;1.已完成.)
	private int flag;
	// 图片
	private String imgUrl;
	// 最高等级
	private int levSum;
	// 目标数量
	private int num;
	// 当前数量
	private int nowNum;
	// 道具名称
	private String awardValue;
	// 类型
	private int type;

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public int getNowNum()
	{
		return nowNum;
	}

	public void setNowNum(int nowNum)
	{
		this.nowNum = nowNum;
	}

	public String getAwardValue()
	{
		return awardValue;
	}

	public void setAwardValue(String awardValue)
	{
		this.awardValue = awardValue;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public int getLevSum()
	{
		return levSum;
	}

	public void setLevSum(int levSum)
	{
		this.levSum = levSum;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public int getAchieveId()
	{
		return achieveId;
	}

	public void setAchieveId(int achieveId)
	{
		this.achieveId = achieveId;
	}

	public int getAwardId()
	{
		return awardId;
	}

	public void setAwardId(int awardId)
	{
		this.awardId = awardId;
	}

	public int getAwardNum()
	{
		return awardNum;
	}

	public void setAwardNum(int awardNum)
	{
		this.awardNum = awardNum;
	}

	public int getLev()
	{
		return lev;
	}

	public void setLev(int lev)
	{
		this.lev = lev;
	}

	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag = flag;
	}

}
