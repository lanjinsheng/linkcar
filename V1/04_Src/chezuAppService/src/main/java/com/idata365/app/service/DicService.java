package com.idata365.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.mapper.DicFamilyTypeMapper;

@Service
public class DicService extends BaseService<DicService>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DicService.class);
	
	@Autowired
	private DicFamilyTypeMapper dicFamilyTypeMapper;
	
	@Transactional
	public List<DicFamilyType> getDicFamilyType()
	{
		return dicFamilyTypeMapper.getDicFamilyType(null);
	}
 
}
