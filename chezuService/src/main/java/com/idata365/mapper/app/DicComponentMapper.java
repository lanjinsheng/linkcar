package com.idata365.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.DicComponent;


public interface DicComponentMapper {
	public List<DicComponent> getDicComponent(Map<String, Object> searchMap);


}
