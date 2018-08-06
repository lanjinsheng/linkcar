package com.idata365.app.controller.open;

import java.util.HashMap;
import java.util.List;
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
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.SignUtils;

@RestController
public class AccountController extends BaseController{
	@Autowired
	LoginRegService loginRegService;
	@Autowired
	AccountService accountService;
	@Autowired
	UserInfoService userInfoService;
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
        * @Description: TODO(获取俱乐部信息)
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
		Map<String,Object> rt=new HashMap<String,Object>();
		rt.put("familyId", 0);
		rt.put("familyUserCount", 0);
		rt.put("fightFamilyId", 0);
		rt.put("fightFamilyUserCount", 0);
		Map<String,Object> db= accountService.getFamiliesInfoByUserId(userId);
		if(db==null) {
			return rt;
		}else {
			return db;
		}
	}
	@RequestMapping(value = "/account/getFamiliesInfoByfamilyId",method = RequestMethod.POST)
	public Map<String,Object>  getFamiliesInfoByfamilyId(@RequestParam(value="familyId") long familyId,@RequestParam(value="sign") String sign)
	{
		LOG.info("familyId="+familyId+"===sign="+sign);
		Map<String,Object> rt=new HashMap<String,Object>();
		rt.put("familyId", 0);
		rt.put("familyUserCount", 0);
		rt.put("fightFamilyId", 0);
		rt.put("fightFamilyUserCount", 0);
		Map<String,Object> db= accountService.getFamiliesInfoByFamilyId(familyId);
		if(db==null) {
			return rt;
		}else {
			return db;
		}
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
	public Map<String,Object>  getUsersInfoByIds(@RequestParam(value="userIds") String userIds,@RequestParam(value="familyId") Long familyId,@RequestParam(value="sign") String sign)
	{
		
		LOG.info("userIds="+userIds+"===sign="+sign);
		return accountService.getUsersInfoByIds(userIds,familyId,this.getImgBasePath());
	}
	

	/**
	 * 
	    * @Title: getUsersByFamilyId
	    * @Description: TODO(通过familyId获取成员ids)
	    * @param @param userId
	    * @param @param sign
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@RequestMapping(value = "/account/getUsersByFamilyId",method = RequestMethod.POST)
	public String  getUsersByFamilyId(@RequestParam(value="familyId") long familyId,@RequestParam(value="daystamp") String daystamp,@RequestParam(value="sign") String sign)
	{
		LOG.info("familyId="+familyId+"===sign="+sign);
		StringBuffer sb=new StringBuffer();
		List<Map<String,Object>> list= accountService.getUsersByFamilyId(familyId, daystamp);
		for(Map<String,Object> m:list) {
			sb.append(String.valueOf(m.get("userId")));
			sb.append(",");
		}
		if(sb.length()>0) {
			return sb.toString().substring(0, sb.length()-1);
		}
		return null;
	}
	
	@RequestMapping(value = "/account/getUserIdByPhone", method = RequestMethod.POST)
	long getUserIdByPhone(@RequestParam(value = "phone") String phone,@RequestParam(value="sign") String sign) {
		return accountService.getUserIdByPhone(phone);
	}

	@RequestMapping(value = "/account/getUsersScoreByFamilyId",method = RequestMethod.POST)
	public List<Map<String,Object>>  getUsersScoreByFamilyId(@RequestParam(value="familyId") long familyId,@RequestParam(value="daystamp") String daystamp,@RequestParam(value="sign") String sign)
	{
		LOG.info("familyId="+familyId+"===sign="+sign);
		List<Map<String,Object>> list= accountService.getUsersScoreByFamilyId(familyId, daystamp);
		return list;
	}
	
	@RequestMapping(value = "/account/getCurrentUsersByFamilyId",method = RequestMethod.POST)
	public String  getCurrentUsersByFamilyId(@RequestParam(value="familyId") long familyId,@RequestParam(value="daystamp") String daystamp,@RequestParam(value="sign") String sign)
	{
		LOG.info("familyId="+familyId+"===sign="+sign);
		StringBuffer sb=new StringBuffer();
		List<Map<String,Object>> list= accountService.getCurrentUsersByFamilyId(familyId, daystamp);
		for(Map<String,Object> m:list) {
			sb.append(String.valueOf(m.get("userId")));
			sb.append(",");
		}
		if(sb.length()>0) {
			return sb.toString().substring(0, sb.length()-1);
		}
		return null;
	}

	
	
	@RequestMapping(value = "/account/updateLoginBss",method = RequestMethod.POST)
	public boolean  updateLoginBss(@RequestParam(value="userId") long userId,@RequestParam(value="sign") String sign)

	{
		LOG.info("userId="+userId+"===sign="+sign);
		LOG.info("valid sign="+SignUtils.encryptHMAC(String.valueOf(userId)));
		accountService.updateUserLoginTime(userId);
		return true;
	}
	
	/**
	 * 
	    * @Title: isAuthenticated
	    * @Description: TODO(获取用户证件认证信息)
	    * @param @param userId
	    * @param @param sign
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * Map.put("IdCardIsOK":"1")
	    * Map.put("VehicleTravelIsOK":"0")
	    * @throws
	    * @author lcc
	 */
	@RequestMapping(value = "/account/isAuthenticated",method = RequestMethod.POST)
	public Map<String,String>  isAuthenticated(@RequestParam(value="userId") long userId,@RequestParam(value="sign") String sign)
	{
		
		LOG.info("userIds="+userId+"===sign="+sign);
		return userInfoService.isAuthenticated(userId);
	}
}
