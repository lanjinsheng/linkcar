package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.FamilyRelation;

public interface FamilyRelationMapper
{
	public  List<FamilyRelation>  getFamilyRelation(@Param("daystamp") String daystamp);
	 
}
