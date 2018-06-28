package com.idata365.app.remote;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @ClassName: ChezuService
 * @Description: TODO(获取用户证件认证情况)
 * @author lcc
 * @date 2018年6月14日
 *
 */
@FeignClient(value = "service-account-chezu", fallback = ChezuAccountHystric.class)
public interface ChezuAccountService {

	/**
	 * 
	 * @Title: isAuthenticated
	 * @Description: TODO(获取用户证件认证信息)
	 * @param @param
	 *            userId
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型 Map.put("IdCardIsOK":"1")
	 *         Map.put("VehicleTravelIsOK":"0")
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping(value = "/account/isAuthenticated", method = RequestMethod.POST)
	public Map<String, String> isAuthenticated(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "sign") String sign);

	// 查询用户是否身份认证
	@RequestMapping(value = "/account/queryCountOfIdcard", method = RequestMethod.POST)
	int queryCountOfIdcard(@RequestParam(value = "userId") long userId, @RequestParam(value = "sign") String sign);

	// 查询用户是否行驶证认证
	@RequestMapping(value = "/account/queryCountOfLicense", method = RequestMethod.POST)
	int queryCountOfLicense(@RequestParam(value = "userId") long userId, @RequestParam(value = "sign") String sign);

}
