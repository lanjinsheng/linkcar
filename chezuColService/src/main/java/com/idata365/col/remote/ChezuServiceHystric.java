package com.idata365.col.remote;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.idata365.col.controller.MngCoreController;

@Component
public class ChezuServiceHystric implements ChezuDriveService {
	private final static Logger LOG = LoggerFactory.getLogger(MngCoreController.class);
	@Override
	public boolean recieveDrive(List<Map<String,Object>> postList) {
		// TODO Auto-generated method stub
		LOG.info("挂了 recieveDrive");
		return false;
	}
 
}
