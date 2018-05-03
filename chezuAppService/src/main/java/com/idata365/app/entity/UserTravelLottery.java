package com.idata365.app.entity;

import java.io.Serializable;

public class UserTravelLottery implements Serializable {
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Long habitId;
	private Integer awardId;
	private Integer awardCount;
	private Integer hadGet;
	private Long createMilTimes;
	
	public Long getCreateMilTimes() {
		return createMilTimes;
	}
	public void setCreateMilTimes(Long createMilTimes) {
		this.createMilTimes = createMilTimes;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getHabitId() {
		return habitId;
	}
	public void setHabitId(Long habitId) {
		this.habitId = habitId;
	}
	public Integer getAwardId() {
		return awardId;
	}
	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}
	public Integer getAwardCount() {
		return awardCount;
	}
	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
	}
	public Integer getHadGet() {
		return hadGet;
	}
	public void setHadGet(Integer hadGet) {
		this.hadGet = hadGet;
	}
	
	
}
