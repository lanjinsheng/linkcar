package com.idata365.app.entity;

import java.io.Serializable;

public class QuestionBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -320222876957030360L;

	private String askStr;
	
	private String replyStr;

	public String getAskStr()
	{
		return askStr;
	}

	public void setAskStr(String askStr)
	{
		this.askStr = askStr;
	}

	public String getReplyStr()
	{
		return replyStr;
	}

	public void setReplyStr(String replyStr)
	{
		this.replyStr = replyStr;
	}
	
}
