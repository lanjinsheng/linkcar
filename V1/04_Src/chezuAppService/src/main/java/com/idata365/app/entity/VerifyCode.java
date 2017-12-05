package com.idata365.app.entity;

import java.io.Serializable;


public class VerifyCode implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long userId;
	private String token;
	private Long createTimeLong;
	private int codeType=1;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getCreateTimeLong() {
		return createTimeLong;
	}
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong = createTimeLong;
	}
	public int getCodeType() {
		return codeType;
	}
	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}
 
}