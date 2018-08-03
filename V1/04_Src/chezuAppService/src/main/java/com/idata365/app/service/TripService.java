package com.idata365.app.service;

import java.util.List;
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
	public  List<Map<String,Object>> getTripByUserLatest(Map<String,Object> map) {
		return tripMapper.getTripLatest(map);
	} 
	@Transactional
	public Map<String,Object> getTripSpeciality(long travelId) {
		return tripMapper.getTripSpeciality(travelId);
	}
	public void getHiddenTrip(long travelId) {
		tripMapper.getHiddenTrip(travelId);
	}
	public int getTodayCountTrip(long userId) {
		return tripMapper.getTodayCountTrip(userId);
	}
	public int getTripCountOnce100Score(long userId) {
		return tripMapper.getTripCountOnce100Score(userId);
	}
	public int getTripCountTotalMileageMoreThan500(long userId) {
		double m = tripMapper.getTotalMileage(userId);
		if(m>=500000) {
			return 1;
		}else {
			return 0;
		}
		
	} 
	public int getTripCountQuintic100Score(long userId) {
		String m = tripMapper.getTripCountQuintic100Score(userId);
		if(m == null) {
			return 0;
		}else if(m.contains("1,1,1,1,1")){
			return 5;
		}else if(m.contains("1,1,1,1")){
			return 4;
		}else if(m.contains("1,1,1")){
			return 3;
		}else if(m.contains("1,1")){
			return 2;
		}else if(m.contains("1")){
			return 1;
		}else{
			return 0;
		}
	} 
}
