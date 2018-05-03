package com.idata365.app.remote;


import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
    * @ClassName: ChezuAssetService
    * @Description: TODO(远程调用更新userId 与 deviceToken)
    * @author LanYeYe
    * @date 2017年12月28日
    *
 */
@FeignClient(value = "service-asset-chezu",fallback = ChezuHystric.class)
public interface ChezuAssetService {
	/**
	 * 
	    * @Title: getUserPowerByEffectId
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param effectId
	    * @param @param sign
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	    * @author LanYeYe
	 */
		@RequestMapping(value = "/asset/getUserPowerByEffectId", method = RequestMethod.POST)
		String getUserPowerByEffectId(@RequestParam(value = "effectId") long effectId,
				@RequestParam(value = "sign") String sign) ;
		/**
		 * 
		    * @Title: initUserCreate
		    * @Description: TODO(用户信息初始化)
		    * @param @param userId
		    * @param @param sign
		    * @param @return    参数
		    * @return boolean    返回类型
		    * @throws
		    * @author LanYeYe
		 */
		@RequestMapping(value = "/asset/initUserCreate", method = RequestMethod.POST)
		boolean initUserCreate(@RequestParam(value = "userId") long userId,
				@RequestParam(value = "sign") String sign) ;
		/**
		 * 
		    * @Title: initFamilyCreate
		    * @Description: TODO(家族信息初始化)
		    * @param @param familyId
		    * @param @param sign
		    * @param @return    参数
		    * @return boolean    返回类型
		    * @throws
		    * @author LanYeYe
		 */
		@RequestMapping(value = "/asset/initFamilyCreate", method = RequestMethod.POST)
		boolean initFamilyCreate(@RequestParam(value = "familyId") long familyId,
				@RequestParam(value = "sign") String sign);
}
