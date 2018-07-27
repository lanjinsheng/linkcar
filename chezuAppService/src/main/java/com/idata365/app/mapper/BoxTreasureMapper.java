package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.v2.BoxTreasureFamily;
import com.idata365.app.entity.v2.BoxTreasureUser;

public interface BoxTreasureMapper {

	// CarpoolApprove getCarpoolApproveById(@Param("id") Long id);
	List<BoxTreasureUser> getTripBoxIds(Map<String, Object> map);
	
	List<BoxTreasureFamily> getChallengeBoxIds(Map<String, Object> map);
	
	List<BoxTreasureFamily> getChallengeBoxIdsNew(Map<String, Object> map);

	int hadChallengeBoxIds(Map<String, Object> map);

	int getCanGetNow(@Param("familyId") Long familyId);

	List<BoxTreasureUser> getTripsByBoxId(@Param("boxId") String boxId);

	List<BoxTreasureFamily> getFamilyTreasureByBoxId(@Param("boxId") String boxId);

	int receiveBox(@Param("boxId") String boxId);

	int receiveBoxFamily(@Param("boxId") String boxId);

}
