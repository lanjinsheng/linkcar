package com.idata365.app.mapper;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.UsersAccount;

public interface UsersAccountMapper {

	 UsersAccount  findAccountByPhone(UsersAccount account);
	 void  insertUser(UsersAccount account);
	 void  updateUserPwd(UsersAccount account);
	 void  updateUserLogin(UsersAccount account);
	 
	 UsersAccount  findAccountById(@Param("id")  Long id);
	 
}
