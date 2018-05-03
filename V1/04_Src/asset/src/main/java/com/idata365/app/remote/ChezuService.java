package com.idata365.app.remote;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.app.entity.UsersAccount;

/**
 * 
 * @ClassName: ChezuService
 * @Description: TODO(远程调用更新userId 与 deviceToken)
 * @author LanYeYe
 * @date 2017年12月28日
 *
 */
@FeignClient(value = "service-account-chezu", fallback = ChezuHystric.class)
public interface ChezuService {
	/**
	 * 远程进行用户账户验证，待远程接口写入
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/account/validToken", method = RequestMethod.POST)
	UsersAccount validToken(@RequestParam(value = "token") String token);

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
	public Map<String,Object>  getFamiliesInfoByUserId(@RequestParam(value="userId") long userId,@RequestParam(value="sign") String sign);
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
	public Map<String,Object>  getUsersInfoByIds(@RequestParam(value="userIds") String userIds,@RequestParam(value="sign") long sign);
	 
}
