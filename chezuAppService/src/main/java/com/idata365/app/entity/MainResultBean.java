package com.idata365.app.entity;

public class MainResultBean
{
	private int gamerNum;
	
	//未读消息数目
	private int newsNum;
	
	//聊天未读数目
	private int chatsNum;
	
	//任务未领取
	private int missionHave;
	
	//1表弹出新手引导；0表不需要弹出
	private int readFlag;

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

	public int getReadFlag()
	{
		return readFlag;
	}

	public void setReadFlag(int readFlag)
	{
		this.readFlag = readFlag;
	}

	public int getMissionHave() {
		return missionHave;
	}

	public void setMissionHave(int missionHave) {
		this.missionHave = missionHave;
	}
	
	
}
