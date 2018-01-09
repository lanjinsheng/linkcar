package com.idata365.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.UserFamilyRoleLog;

/**
 * 
    * @ClassName: UserFamilyScoreMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */
public interface UserFamilyScoreMapper {
 
	 List<UserFamilyRoleLog>   getUserRoles(Map<String,Object> map);
	 
	 List<UserFamilyRoleLog>   getUserRolesByFamilyId(@Param("familyId") long familyId);
	 
	 UserFamilyRoleLog getUserRoleById(@Param("id") long id);
	 
	
 
}
