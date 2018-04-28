package com.idata365.app.entity;

import java.io.Serializable;


public class UserLoginSession implements Serializable {

	private static final long serialVersionUID = 1L;
   private Long userId;
	private Long createTimeLong;
	private String token;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCreateTimeLong() {
		return createTimeLong;
	}
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong = createTimeLong;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	 

	
}