package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;




import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.CarpoolApprove;

public interface CarpoolApproveMapper {
 
	List<CarpoolApprove> getCarpoolApprove(Map<String,Object> map);
	List<Map<String,Object>> getCarpoolApproveByDriver(Map<String,Object> map);
	
	int submitCarpoolApprove(CarpoolApprove carpoolApprove);
	
	int getCarpoolApproveNum(@Param("driverId") Long driverId);
	
	int applyCarpoolApprove(CarpoolApprove carpoolApprove);
	int rejectCarpoolApprove(@Param("id") Long id);
	
	int clearOtherCarpoolApprove(CarpoolApprove carpoolApprove);
	CarpoolApprove getCarpoolApproveById(@Param("id") Long id);
	
}
