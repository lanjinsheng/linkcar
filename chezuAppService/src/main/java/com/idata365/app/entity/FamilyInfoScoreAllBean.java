package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyInfoScoreAllBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2766056049706479393L;

	private FamilyInfoScoreResultBean origFamily;
	
	private FamilyInfoScoreResultBean joinFamily;

	private int gamerNum;
	
	//未读消息数目
	private int newsNum;
	
	//聊天未读数目
	private int chatsNum;
	
	public FamilyInfoScoreResultBean getOrigFamily()
	{
		return origFamily;
	}

	public void setOrigFamily(FamilyInfoScoreResultBean origFamily)
	{
		this.origFamily = origFamily;
	}

	public FamilyInfoScoreResultBean getJoinFamily()
	{
		return joinFamily;
	}

	public void setJoinFamily(FamilyInfoScoreResultBean joinFamily)
	{
		this.joinFamily = joinFamily;
	}

	public int getGamerNum()
	{
		return gamerNum;
	}

	public void setGamerNum(int gamerNum)
	{
		this.gamerNum = gamerNum;
	}

	public int getNewsNum()
	{
		return newsNum;
	}

	public void setNewsNum(int newsNum)
	{
		this.newsNum = newsNum;
	}

	public int getChatsNum()
	{
		return chatsNum;
	}

	public void setChatsNum(int chatsNum)
	{
		this.chatsNum = chatsNum;
	}
	
}
