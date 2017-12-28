package com.idata365.col.remote;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChezuServiceHystric implements ChezuDriveService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuServiceHystric.class);
	@Override
	public Map<String, Object> addPoint(Map<String, String> point) {
		// TODO Auto-generated method stub
		LOG.error("挂了 addPoint");
		return null;
	}
	@Override
	public Map<String, Object> addPointList(List<Map<String, String>> pointList) {
		// TODO Auto-generated method stub
		LOG.error("挂了 addPointList");
		return null;
	}
	@Override
	public Map<String, Object> analysis(Map<String, String> param) {
		// TODO Auto-generated method stub
		LOG.error("挂了 analysis");
		return null;
	}
 
}
