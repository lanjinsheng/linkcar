package com.idata365.app.entity;

import java.io.Serializable;

public class UserFamilyRoleLogParamBean implements Serializable
{
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 6912615942518673514L;

	private Long id;
	
	private Long userFamilyRoleLogId;
	
	private Long userId;
	
	private Long familyId;
	
	private String daystamp;
	
	private Integer role;

	private String startTime;
	
	private String endTime;
	
	private int count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserFamilyRoleLogId() {
		return userFamilyRoleLogId;
	}

	public void setUserFamilyRoleLogId(Long userFamilyRoleLogId) {
		this.userFamilyRoleLogId = userFamilyRoleLogId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	 
	
}
