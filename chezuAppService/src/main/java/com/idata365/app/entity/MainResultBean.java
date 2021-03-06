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
	
	//罚单数量
	private int pastesNum;
	
	//用户是否搭车
	private int waitHave;
	
	//乘客数量
	private int sitsNum;
	
	//俱乐部顺风车红点
	private int clubHave;
	//小波要求添加，没软用
	private int  myClubHave;
	
	//1表弹出新手引导；0表不需要弹出
	private int readFlag;
	
	//创建俱乐部页面是否有“俱乐部奖金”图标
	private int haveClubBonusIcon;
	
	//“俱乐部奖金图标”是否闪烁
	private int clubBonusIconStatus;
	
	//创建俱乐部页面俱乐部ICON小红点---新挑战宝箱
	private int isHaveBox;

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

	public int getPastesNum() {
		return pastesNum;
	}

	public void setPastesNum(int pastesNum) {
		this.pastesNum = pastesNum;
	}

	public int getWaitHave() {
		return waitHave;
	}

	public void setWaitHave(int waitHave) {
		this.waitHave = waitHave;
	}

	public int getSitsNum() {
		return sitsNum;
	}

	public void setSitsNum(int sitsNum) {
		this.sitsNum = sitsNum;
	}

	public int getClubHave() {
		return clubHave;
	}

	public void setClubHave(int clubHave) {
		this.clubHave = clubHave;
	}

	public int getHaveClubBonusIcon() {
		return haveClubBonusIcon;
	}

	public void setHaveClubBonusIcon(int haveClubBonusIcon) {
		this.haveClubBonusIcon = haveClubBonusIcon;
	}

	public int getClubBonusIconStatus() {
		return clubBonusIconStatus;
	}

	public void setClubBonusIconStatus(int clubBonusIconStatus) {
		this.clubBonusIconStatus = clubBonusIconStatus;
	}

	public int getIsHaveBox() {
		return isHaveBox;
	}

	public void setIsHaveBox(int isHaveBox) {
		this.isHaveBox = isHaveBox;
	}

	public int getMyClubHave() {
		return myClubHave;
	}

	public void setMyClubHave(int myClubHave) {
		this.myClubHave = myClubHave;
	}
}
