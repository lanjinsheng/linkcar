package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;


import com.idata365.app.entity.DicCar;

public interface DicCarMapper {
	public List<DicCar> getDicCar(Map<String, Object> searchMap);

}
