package com.idata365.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.DicCar;
import com.idata365.entity.DicComponent;
import com.idata365.entity.DicFamilyType;
import com.idata365.entity.FamilyRelation;
import com.idata365.mapper.app.DicCarMapper;
import com.idata365.mapper.app.DicComponentMapper;
import com.idata365.mapper.app.DicFamilyTypeMapper;
import com.idata365.mapper.app.FamilyRelationMapper;
import com.idata365.util.DateTools;


@Service
public class DicService extends BaseService<DicService>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DicService.class);
	
	@Autowired
	private DicFamilyTypeMapper dicFamilyTypeMapper;
	@Autowired
	FamilyRelationMapper familyRelationMapper;
	@Autowired
	DicComponentMapper dicComponentMapper;
	@Autowired
	DicCarMapper dicCarMapper;
	
	@Transactional
	public List<DicFamilyType> getDicFamilyType()
	{
		return dicFamilyTypeMapper.getDicFamilyType(null);
	}
	@Transactional
	public List<FamilyRelation> getFamilyRelation()
	{
		return familyRelationMapper.getFamilyRelation(DateTools.getYYYY_MM_DD());
	}
	@Transactional
	public List<DicCar> getDicCar() {
		return dicCarMapper.getDicCar(null);
	}
	@Transactional
	public List<DicComponent> getDicComponent() {
		return dicComponentMapper.getDicComponent(null);
	}

}


