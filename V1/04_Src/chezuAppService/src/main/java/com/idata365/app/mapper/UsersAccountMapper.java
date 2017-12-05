package com.idata365.app.mapper;

import com.idata365.app.entity.UsersAccount;

public interface UsersAccountMapper {

	 UsersAccount  findAccountByPhone(UsersAccount account);
	 void  insertUser(UsersAccount account);
	 void  updateUserPwd(UsersAccount account);
	 void  updateUserLogin(UsersAccount account);
	 
}
