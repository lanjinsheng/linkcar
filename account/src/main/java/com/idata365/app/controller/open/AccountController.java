package com.idata365.app.controller.open;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.service.AccountService;
import com.idata365.app.service.LoginRegService;

@RestController
public class AccountController extends BaseController{
	@Autowired
	LoginRegService loginRegService;
	@Autowired
	AccountService accountService;
	@RequestMapping(value = "/account/validToken",method = RequestMethod.POST)
	public UsersAccount  validToken(@RequestParam(value="token") String token)
	{
		UsersAccount account=loginRegService.validToken(token);
		return account;
		
	}
	/**
	 * 
	    * @Title: getUserInfoByToken
	    * @Description: TODO(TOKEN验证)
	    * @param @param token
	    * @param @return    参数
	    * @return UserInfo    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@RequestMapping(value = "/account/getUserInfoByToken",method = RequestMethod.POST)
	public UserInfo  getUserInfoByToken(@RequestParam(value="token") String token)
	{
		UsersAccount account=loginRegService.validToken(token);
		UserInfo userInfo=new UserInfo();
    	userInfo.setUserAccount(account);
    	userInfo.setToken(token);
    	return userInfo;
	}
	
    /**
     * 
        * @Title: getFamiliesInfoByUserId
        * @Description: TODO(获取家族信息)
        * @param @param userId
        * @param @param sign
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * Map.put("familyId":"id")
        * Map.put("familyUserCount":"number")
	    * Map.put("fightFamilyId":"id")
        * Map.put("fightFamilyUserCount":"number")
        * @throws
        * @author LanYeYe
     */
	@RequestMapping(value = "/account/getFamiliesInfoByUserId",method = RequestMethod.POST)
	public Map<String,Object>  getFamiliesInfoByUserId(@RequestParam(value="userId") long userId,@RequestParam(value="sign") String sign)
	{
		LOG.info("userId="+userId+"===sign="+sign);
    	return accountService.getFamiliesInfoByUserId(userId);
	}
	
	/**
	 * 
	    * @Title: getUsersInfoByIds
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param userIds
	    * @param @param sign
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * Map.put("nickNames":"张三,lisi,无误")
	    * Map.put("userHeadUrls":"url1,url2,url3")
	    * @throws
	    * @author LanYeYe
	 */
	@RequestMapping(value = "/account/getUsersInfoByIds",method = RequestMethod.POST)
	public Map<String,Object>  getUsersInfoByIds(@RequestParam(value="userIds") String userIds,@RequestParam(value="sign") String sign)
	{
		
		LOG.info("userIds="+userIds+"===sign="+sign);
		return accountService.getFamiliesInfoByUserId(userIds);
	}
}
