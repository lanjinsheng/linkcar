package com.idata365.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.Area;
import com.idata365.app.entity.City;
import com.idata365.app.entity.Province;
import com.idata365.app.mapper.ProvinceMapper;

@Service
public class ProvinceService extends BaseService<ProvinceService>
{
	private static final Logger LOG = LoggerFactory.getLogger(ProvinceService.class);
	
	@Autowired
	private ProvinceMapper provinceMapper;
	
	public List<Province> findProvince()
	{
		List<Province> provinces = this.provinceMapper.findProvince();
		for (Province province : provinces)
		{
			String upareaid = province.getUpareaid();
			List<City> cities = this.provinceMapper.findCity(upareaid);
			province.setCities(cities);
			for (City city : cities)
			{
				List<Area> areas = this.provinceMapper.findArea(city.getUpareaid());
				city.setAreas(areas);
			}
		}
		return provinces;
	}
}
