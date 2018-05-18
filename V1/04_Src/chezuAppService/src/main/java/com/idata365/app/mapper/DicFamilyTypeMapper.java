package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.DicFamilyType;

public interface DicFamilyTypeMapper {
	public List<DicFamilyType> getDicFamilyType(Map<String, Object> searchMap);

	public Map<String, String> getSurPlusDays(@Param("dayStr") String dayStr);

}
