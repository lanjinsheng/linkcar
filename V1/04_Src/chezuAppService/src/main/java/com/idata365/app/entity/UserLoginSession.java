package com.idata365.app.entity;

import java.io.Serializable;


public class UserLoginSession implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String phone;
	private String verifyCode;
	private Long createTimeLong;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public Long getCreateTimeLong() {
		return createTimeLong;
	}
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong = createTimeLong;
	}	 

	
}