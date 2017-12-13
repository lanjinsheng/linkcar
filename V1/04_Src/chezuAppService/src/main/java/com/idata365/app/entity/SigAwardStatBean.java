package com.idata365.app.entity;

public class SigAwardStatBean
{
	//SEVEN_OK:表从当前天往前已经连续7天签到且未抽奖；SEVEN_AWARD:表从当前天往前已经连续7天签到且已抽奖；SEVEN_NO:表未连续7天签到
	private String continueSevenFlag;

	public String getContinueSevenFlag()
	{
		return continueSevenFlag;
	}

	public void setContinueSevenFlag(String continueSevenFlag)
	{
		this.continueSevenFlag = continueSevenFlag;
	}
	
}
