package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyScoreBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2651508375006214850L;
	private Long familyId;
	private Integer familyType;
	private Integer trophy;
	private String startDay;
	
	private String endDay;

	private int yesterdayOrderNo;
	
	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	public Integer getFamilyType() {
		return familyType;
	}

	public void setFamilyType(Integer familyType) {
		this.familyType = familyType;
	}

	public Integer getTrophy() {
		return trophy;
	}

	public void setTrophy(Integer trophy) {
		this.trophy = trophy;
	}

	public String getStartDay()
	{
		return startDay;
	}

	public void setStartDay(String startDay)
	{
		this.startDay = startDay;
	}

	public String getEndDay()
	{
		return endDay;
	}

	public void setEndDay(String endDay)
	{
		this.endDay = endDay;
	}

	public int getYesterdayOrderNo()
	{
		return yesterdayOrderNo;
	}

	public void setYesterdayOrderNo(int yesterdayOrderNo)
	{
		this.yesterdayOrderNo = yesterdayOrderNo;
	}
	
}
