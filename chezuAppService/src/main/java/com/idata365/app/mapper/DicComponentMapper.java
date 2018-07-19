package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;



import com.idata365.app.entity.v2.DicComponent;

public interface DicComponentMapper {
	public List<DicComponent> getDicComponent(Map<String, Object> searchMap);

}
