package com.idata365.app.serviceV2;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.mapper.ThirdPartyLoginMapper;
import com.idata365.app.service.BaseService;

@Service
public class ThirdPartyLoginService extends BaseService<ThirdPartyLoginService>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyLoginService.class);
	
	@Autowired
	private ThirdPartyLoginMapper thirdPartyLoginMapper;
	
	public Map<String, Object> queryThirdPartyLoginById(String openId){
		return thirdPartyLoginMapper.queryThirdPartyLoginById(openId);
	}
	
	public Map<String, Object> queryThirdPartyLoginByUserId(Long userId){
		return thirdPartyLoginMapper.queryThirdPartyLoginByUserId(userId);
	}
	
	public int insertLogs(Map<String, Object> entity) {
		return thirdPartyLoginMapper.insertLogs(entity);
	}
	
	public int updateLogs(Map<String, Object> entity) {
		return thirdPartyLoginMapper.updateLogs(entity);
	}

	public int updateByOpenId(Long userId, String openId) {
		return thirdPartyLoginMapper.updateByOpenId(userId,openId);
	}
 
	public int queryWXIsBind(Long userId) {
		return thirdPartyLoginMapper.queryWXIsBind(userId);
	}
	
	public int queryQQIsBind(Long userId) {
		return thirdPartyLoginMapper.queryQQIsBind(userId);
	}
	
	public int unBind(Long userId, int type) {
		return thirdPartyLoginMapper.unBind(userId,type);
	}
}
