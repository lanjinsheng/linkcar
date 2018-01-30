package com.idata365.app.entity;

import java.io.Serializable;

public class SigAwardStatBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7744275209744824485L;
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
