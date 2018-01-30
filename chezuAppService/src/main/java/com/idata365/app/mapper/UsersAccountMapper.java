package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.UsersAccount;

public interface UsersAccountMapper {

	 UsersAccount  findAccountByPhone(UsersAccount account);
	 void  insertUser(UsersAccount account);
	 void  updateUserPwd(UsersAccount account);
	 void  updateUserLogin(UsersAccount account);
	 
	 UsersAccount  findAccountById(@Param("id")  Long id);
	 UsersAccount  findAccountByIdAndPwd(UsersAccount account);
	 void  updateNickName(UsersAccount account);
	 void  updateEnableStranger(UsersAccount account);
	 void  updateImgUrl(UsersAccount account);
	 void  updatePhone(UsersAccount account);
	 void  updatePwdByUserIdAndOldPwd(Map<String,Object> account);
	 
	 Map<String,Object> getFamilyByUserId(@Param("userId") Long userId);
	 Map<String,Object> getFamilyByFamilyId(@Param("familyId") Long familyId);
	 
	 List<Map<String,Object>> findFamilyRelation(@Param("familyId") Long familyId);
}
