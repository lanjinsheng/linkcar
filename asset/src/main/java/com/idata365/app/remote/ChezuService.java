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
	 * @Title: getFamilyInfo
	 * @Description: TODO(根据用户ID获取家族信息，待远程接口写入)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型 
	 * 		nikeName --- 昵称
	 * 		familyId --- 家族ID  long
	 * 		fightFamilyId --- 对战家族ID  long
	 * 		familyUserCount --- 家族总人数   long
	 * 		fightFamilyUserCount --- 对战家族总人数  long
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping(value = "/account/getFamilyInfo", method = RequestMethod.POST)
	Map<String, Object> getFamilyInfo(@RequestParam(value = "userId") String userId);

}
