package com.idata365.mapper.app;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 
    * @ClassName: FamilyInfoMapper
    * @Description: TODO(家族成员)
    * @author LanYeYe
    * @date 2018年04月24日
    *
 */
public interface FamilyInfoMapper {
 
	 void  updateFamilyDriveFlag(@Param("familyId") Long familyId);
//	 void  updateFamilyActiveLevel(@Param("familyId") Long familyId);
	 void  updateHadNewPower(Map<String,Object> map);
	 
	 List<Map<String,Object>> getFamiliesByUserId(@Param("userId") Long userId);
}
