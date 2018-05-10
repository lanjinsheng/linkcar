package com.idata365.entity;

import java.io.Serializable;

public class UserRoleLog implements Serializable
{
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 6204088822233377985L;

	private Long id;
		
	private Long userId;
	
	private String daystamp;
	
	private Integer role;

	private String startTime;
	
	private String endTime;

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

	public String getDaystamp() {
		return daystamp;
	}

	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	 
	 
	
}
