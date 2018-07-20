package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;





import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.v2.ComponentFamily;
import com.idata365.app.entity.v2.ComponentUser;


public interface ComponentMapper {
 

//	CarpoolApprove getCarpoolApproveById(@Param("id") Long id);
	public int insertComponentUser(ComponentUser componentUser);
	
	public int countFreeCabinet(Map<String,Object> map);
	
	
	public int countFamilyFreeCabinet(Map<String,Object> map);
	
	public int insertComponentFamily(ComponentFamily componentFamily);
	
	
	public List<ComponentUser> getFreeComponentUser(@Param("userId") Long userId);
	
	public List<ComponentFamily> getFreeComponentFamily(@Param("familyId") Long familyId);
	
}
