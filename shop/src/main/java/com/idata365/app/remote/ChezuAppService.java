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
 * @Description: TODO(资产的增加与减少)
 * @author LanYeYe
 * @date 2017年12月28日
 *
 */
@FeignClient(value = "service-app-chezu", fallback = ChezuAppHystric.class)
public interface ChezuAppService {
    /**
     * 
        * @Title: sendShopMsg
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param @param userId
        * @param @param goodsName
        * @param @param sign
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/app/msg/sendShopMsg")
    public boolean sendShopMsg(@RequestParam (value = "userId") Long userId,
    		@RequestParam (value = "goodsName") String goodsName,@RequestParam (value = "sign") String sign);
    /**
	 * 
	    * @Title: sendGoodsSendMsg
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param userId
	    * @param @param goodsName
	    * @param @param sign
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
    @RequestMapping("/app/msg/sendGoodsSendMsg")
    public boolean sendGoodsSendMsg(@RequestParam (value = "userId") Long userId,
    		@RequestParam (value = "goodsName") String goodsName,@RequestParam (value = "sign") String sign);

}
