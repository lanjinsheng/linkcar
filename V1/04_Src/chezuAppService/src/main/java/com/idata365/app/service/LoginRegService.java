package com.idata365.app.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.mapper.UserLoginSessionMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.mapper.VerifyCodeMapper;

 

@Service
public class LoginRegService extends BaseService<LoginRegService>{
	private final static Logger LOG = LoggerFactory.getLogger(LoginRegService.class);
	@Autowired
	 UsersAccountMapper usersAccountMapper;
	@Autowired
	 UserLoginSessionMapper userLoginSessionMapper;
	@Autowired
	 VerifyCodeMapper verifyCodeMapper;
	 
	
	public LoginRegService() {
		LOG.info("DataService DataService DataService DataService");
	}

	
}
