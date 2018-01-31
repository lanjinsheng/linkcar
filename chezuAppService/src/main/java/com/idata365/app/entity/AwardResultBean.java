package com.idata365.app.entity;

import java.util.List;

public class AwardResultBean
{
	//获奖消息
	private String headMsg;
	
	//昵称
	private String nickName;
	
	//年龄
	private String age;
	
	//职业
	private String job;
	
	//驾龄
	private String driveAge;
	
	//语录
	private String motto;
	
	//头像url
	private String imgUrl;
	
	//奖品
	private String award;
	
	//
	private String awardImg;
	
	//
	private String nextAwardTime;
	
	//问答
	private List<QuestionBean> questionList;

	public String getHeadMsg()
	{
		return headMsg;
	}

	public void setHeadMsg(String headMsg)
	{
		this.headMsg = headMsg;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getJob()
	{
		return job;
	}

	public void setJob(String job)
	{
		this.job = job;
	}

	public String getDriveAge()
	{
		return driveAge;
	}

	public void setDriveAge(String driveAge)
	{
		this.driveAge = driveAge;
	}

	public String getMotto()
	{
		return motto;
	}

	public void setMotto(String motto)
	{
		this.motto = motto;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getAward()
	{
		return award;
	}

	public void setAward(String award)
	{
		this.award = award;
	}

	public List<QuestionBean> getQuestionList()
	{
		return questionList;
	}

	public void setQuestionList(List<QuestionBean> questionList)
	{
		this.questionList = questionList;
	}

	public String getAwardImg()
	{
		return awardImg;
	}

	public void setAwardImg(String awardImg)
	{
		this.awardImg = awardImg;
	}

	public String getNextAwardTime()
	{
		return nextAwardTime;
	}

	public void setNextAwardTime(String nextAwardTime)
	{
		this.nextAwardTime = nextAwardTime;
	}
}
