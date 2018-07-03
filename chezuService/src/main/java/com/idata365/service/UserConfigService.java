package com.idata365.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.UserConfig;
import com.idata365.mapper.app.UserConfigMapper;

@Service
public class UserConfigService extends BaseService<UserConfigService> {
	public static Map<Long,Integer> map=new HashMap<Long,Integer> ();
	@Autowired
	UserConfigMapper	userConfigMapper;
	/**
	 * 
	    * @Title: getUserConfig
	    * @Description: TODO(获取用户配置)
	    * @param @param userId
	    * @param @return    参数
	    * @return UserConfig    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public boolean getUserConfig() {
		List<UserConfig> list=userConfigMapper.getUserConfig();
		for(UserConfig uc:list) {
			map.put(uc.getUserId(), uc.getUserConfigValue());
		}
		 return true;
	}
	
}
