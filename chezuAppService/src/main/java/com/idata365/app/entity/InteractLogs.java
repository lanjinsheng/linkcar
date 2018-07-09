package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

public class InteractLogs implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userIdA;
	private Long userIdB;
	private String userNameA;
	private String userNameB;
	private int eventType;
	private int someValue;
	private Date createTime;
	private String  createTimeStr;
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserIdA() {
		return userIdA;
	}
	public void setUserIdA(Long userIdA) {
		this.userIdA = userIdA;
	}
	public Long getUserIdB() {
		return userIdB;
	}
	public void setUserIdB(Long userIdB) {
		this.userIdB = userIdB;
	}
	public String getUserNameA() {
		return userNameA;
	}
	public void setUserNameA(String userNameA) {
		this.userNameA = userNameA;
	}
	public String getUserNameB() {
		return userNameB;
	}
	public void setUserNameB(String userNameB) {
		this.userNameB = userNameB;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public int getSomeValue() {
		return someValue;
	}
	public void setSomeValue(int someValue) {
		this.someValue = someValue;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	

}
