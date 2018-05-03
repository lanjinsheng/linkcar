package com.idata365.app.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idata365.app.mapper.TripMapper;

@Service
public class TripService extends BaseService<TripService>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(TripService.class);
 
	@Autowired
	private TripMapper tripMapper;
 
	@Transactional
	public Map<String,Object> getTripByUserLatest(long userId) {
		return tripMapper.getTripByUserLatest(userId);
	}
	 
	@Transactional
	public Map<String,Object> getTripSpeciality(long travelId) {
		return tripMapper.getTripSpeciality(travelId);
	}
	 
	
}
