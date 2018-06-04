package com.idata365.col.remote;

 
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * @ClassName: ChezuAccountService
 * @Description: TODO(远程调用更新userId 与 deviceToken)
 * @author LanYeYe
 * @date 2017年12月28日
 *
 */
@FeignClient(value = "service-account-chezu", fallback = ChezuAccountHystric.class)
public interface ChezuAccountService {
/**
 * 
    * @Title: getUserIdByPhone
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param @param token
    * @param @return    参数
    * @return long    返回类型
    * @throws
    * @author LanYeYe
 */
	@RequestMapping(value = "/account/getUserIdByPhone", method = RequestMethod.POST)
	long getUserIdByPhone(@RequestParam(value = "phone") String phone,@RequestParam(value="sign") String sign);

   
}
