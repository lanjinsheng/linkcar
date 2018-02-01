package com.idata365.app.entity;

public class MainResultBean
{
	private int gamerNum;
	
	//未读消息数目
	private int newsNum;
	
	//聊天未读数目
	private int chatsNum;

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
