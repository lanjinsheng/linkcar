package com.idata365.app.entity.bean;

import com.idata365.app.entity.UsersAccount;

public class UserInfo extends UsersAccount{

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	
	public void setUserAccount(UsersAccount account) {
		this.setId(account.getId());
		this.setCreateTime(account.getCreateTime());
		this.setLastLoginTime(account.getLastLoginTime());
		this.setPhone(account.getPhone());
		this.setPwd(account.getPwd());
		this.setNickName(account.getNickName());
		this.setImgUrl(account.getImgUrl());
	}

}
