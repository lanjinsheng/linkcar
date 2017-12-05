package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;


public class UsersAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String phone;
	private String pwd;
	private Date lastLoginTime;
    private Date createTime;
	public UsersAccount() {
		super();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	
}