package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.UserRoleLog;
 /**
  * 
     * @ClassName: UserRoleLogMapper
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @author LanYeYe
     * @date 2018年5月10日
     *
  */
public interface UserRoleLogMapper {
 
	 List<UserRoleLog>  getUserRoleLogsByUserId(@Param("userId") Long userId);
	 UserRoleLog  getLatestUserRoleLog(UserRoleLog userRoleLog);
	 UserRoleLog  getYestodayUserRoleLog(UserRoleLog userRoleLog);
	 UserRoleLog  getLatestUserRoleLogByUserId(@Param("userId") Long  userId);
	 int  insertUserRole(UserRoleLog userRoleLog);
	 int  updateUserRole(UserRoleLog userRoleLog);
	 
	 int  updateUserFamilyRelationRole(UserRoleLog userRoleLog);
}
