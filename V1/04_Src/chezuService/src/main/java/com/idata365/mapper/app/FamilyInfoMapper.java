package com.idata365.mapper.app;


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
	 void  updateFamilyActiveLevel(@Param("familyId") Long familyId);
}
