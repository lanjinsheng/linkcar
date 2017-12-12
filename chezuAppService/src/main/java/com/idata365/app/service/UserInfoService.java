package com.idata365.app.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.UsersAccount;
import com.idata365.app.mapper.UsersAccountMapper;

 

@Service
public class UserInfoService extends BaseService<UserInfoService>{
	private final static Logger LOG = LoggerFactory.getLogger(UserInfoService.class);
	@Autowired
	 UsersAccountMapper usersAccountMapper;

	public UserInfoService() {
	}
	 
	public void updateNickName(Long userId,String nickName) {
		UsersAccount account=new UsersAccount();
		account.setId(userId);
		account.setNickName(nickName);
		usersAccountMapper.updateNickName(account);
 
	}
	public void updateImgUrl(String key,Long userId) {
		UsersAccount account=new UsersAccount();
		account.setImgUrl(key);
		account.setId(userId);
		usersAccountMapper.updateImgUrl(account);
 
	}
	 
	public void updatePhone(String phone,Long userId) {
		UsersAccount account=new UsersAccount();
		account.setPhone(phone);
		account.setId(userId);
		usersAccountMapper.updateImgUrl(account);
 
	}
	
	public boolean validPwd(String pwd,Long userId) {
		UsersAccount account=new UsersAccount();
		account.setPwd(pwd);
		account.setId(userId);
		UsersAccount dbAccount=usersAccountMapper.findAccountByIdAndPwd(account);
		if(dbAccount==null) {
			return false;
		}
		return true;
 
	}
	
	
	public void updatePwd(String orgPwd,String newPwd,Long userId) {
		 Map<String,Object> account=new HashMap<String,Object>();
		 account.put("orgPwd", orgPwd);
		 account.put("userId", userId);
		 account.put("newPwd", newPwd);
		usersAccountMapper.updatePwdByUserIdAndOldPwd(account);
 
	}
}
