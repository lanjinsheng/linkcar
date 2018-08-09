package com.idata365.app.remote;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.app.entity.UsersAccount;

@Component
public class ChezuImHystric implements ChezuImService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuImHystric.class);

	@Override
	public boolean notifyFamilyChange(List<Map<String,Object>> list, String sign) {
		// TODO Auto-generated method stub
		LOG.error("notifyFamilyChange 挂了");
		return false;
	}

	@Override
	public boolean prayingRealize(String fromUserName, String toUserName,String toUserId,
			String propName, String sign) {
		// TODO Auto-generated method stub
		LOG.error("prayingRealize 挂了");
		return false;
	}

	@Override
	public boolean prayingSubmit(String fromUserName, String toUserName,String toUserId,
			String propName, String sign) {
		// TODO Auto-generated method stub
		LOG.error("prayingSubmit 挂了");
		return false;
	}

	@Override
	public boolean lookedAllAd(String userName, String userId, String sign) {
		LOG.error("lookedAllAd 挂了");
		return false;
	}

	@Override
	public boolean doingAllActMission(String userName, long powerNum, String userId, String sign) {
		LOG.error("doingAllActMission 挂了");
		return false;
	}

}
