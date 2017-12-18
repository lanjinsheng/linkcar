package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.Area;
import com.idata365.app.entity.City;
import com.idata365.app.entity.Province;

public interface ProvinceMapper
{
	public List<Province> findProvince();
	
	public List<City> findCity(String provinceid);
	
	public List<Area> findArea(String cityId);
}
