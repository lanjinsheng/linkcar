package com.idata365.app.entity.bean;

import com.idata365.app.entity.UsersAccount;
import com.idata365.app.util.PhoneUtils;

public class UserInfo extends UsersAccount{

	
	    /**
	    * @Fields serialVersionUID : TODO()
	    */
	private static final long serialVersionUID = 1L;
	
	public void setUserAccount(UsersAccount account) {
		this.setId(account.getId());
		this.setCreateTime(account.getCreateTime());
		this.setLastLoginTime(account.getLastLoginTime());
		this.setPhone(account.getPhone());
		this.setPwd(account.getPwd());
		if(account.getNickName()==null){
			this.setNickName("手机"+PhoneUtils.hidePhone(account.getPhone()));
		}else{
			this.setNickName(account.getNickName());
		}
		
		this.setImgUrl(account.getImgUrl());
	}

}
