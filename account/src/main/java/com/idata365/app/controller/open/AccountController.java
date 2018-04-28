package com.idata365.app.controller.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.service.LoginRegService;

@RestController
public class AccountController extends BaseController{
	@Autowired
	LoginRegService loginRegService;
	@RequestMapping(value = "/account/validToken",method = RequestMethod.POST)
	public UsersAccount  validToken(@RequestParam(value="token") String token)
	{
		UsersAccount account=loginRegService.validToken(token);
		return account;
		
	}
	@RequestMapping(value = "/account/getUserInfoByToken",method = RequestMethod.POST)
	public UserInfo  getUserInfoByToken(@RequestParam(value="token") String token)
	{
		UsersAccount account=loginRegService.validToken(token);
		UserInfo userInfo=new UserInfo();
    	userInfo.setUserAccount(account);
    	userInfo.setToken(token);
    	return userInfo;
	}
}
