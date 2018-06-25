package com.idata365.app.mapper;




import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.FamilyRelation;

public interface FamilyRelationMapper {

	FamilyRelation getFightRelation(Map<String,Object> map);
	
	int insertFamilyRelation(FamilyRelation familyRelation);
	
	int updateRelationType(@Param("familyId")long id);
	
	FamilyRelation hadMatch(FamilyRelation familyRelation);
	
	Map<String,Object> getMatchFamily(Map<String,Object> map);
	
	Double getFamilyAvgScore(@Param("familyId")long familyId); 
}
