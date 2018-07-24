package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;







import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.v2.ComponentFamily;
import com.idata365.app.entity.v2.ComponentGiveLog;
import com.idata365.app.entity.v2.ComponentUser;
import com.idata365.app.entity.v2.ComponentUserUseLog;


public interface ComponentMapper {
 

//	CarpoolApprove getCarpoolApproveById(@Param("id") Long id);
	public int insertComponentUser(ComponentUser componentUser);
	
	public int countFreeCabinet(Map<String,Object> map);
	
	
	public int countFamilyFreeCabinet(Map<String,Object> map);
	
	public int insertComponentFamily(ComponentFamily componentFamily);
	
	
	public List<ComponentUser> getFreeComponentUser(@Param("userId") Long userId);
	
	public List<ComponentFamily> getFreeComponentFamily(@Param("familyId") Long familyId);
	
	public int dropFamilyComponent(@Param("familyComponentId") Long familyComponentId);
	
	public ComponentFamily getFamilyComponent(@Param("familyComponentId") Long familyComponentId);
	public ComponentUser getComponentUser(@Param("userComponentId") Long userComponentId);
	
	public int 	updateFamilyComponent(Map<String,Object> map);
	public int 	gotFamilyComponent(Map<String,Object> map);
	public ComponentUser getUserComponentByType(Map<String,Object> map);
	public int gotUserComponent(ComponentUser componentUser);
	
	
	public int 	updateUserComponent(Map<String,Object> map);
	
	public int 	insertComponentGiveLog(ComponentGiveLog log);
	
	public int 	insertComponentUserUseLog(ComponentUserUseLog log);
	
	public int 	recieveComponentGiveLog(@Param("id") Long id);
	
	
	public List<ComponentGiveLog> getComponentGiveLog(Map<String,Object> m);
	
	
	public ComponentGiveLog findComponentGiveLog(@Param("id") Long id);
	public ComponentGiveLog findComponentGiveLogByMap(Map<String,Object> map);
	
	public List<Map<String,Object>> getFreeComponentUserGroupType(@Param("userId") Long userId);
	
//	public int submitPraying(ComponentGiveLog componentGiveLog);insertComponentGiveLog
//	public int applyPraying(ComponentGiveLog componentGiveLog);
//	public int applyMemberRequest(ComponentGiveLog componentGiveLog);
	public int updateComponentGiveLog(ComponentGiveLog componentGiveLog);
	
	public int updateComponentGiveLogApplyPraying(ComponentGiveLog componentGiveLog);
	
	
	
	public int ignoreGiveLog(ComponentGiveLog componentGiveLog);
	
	
}
