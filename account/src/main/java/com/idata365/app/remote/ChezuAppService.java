package com.idata365.app.remote;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
    * @ClassName: ChezuAppService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年5月8日
    *
 */
@FeignClient(value = "service-app-chezu", fallback = ChezuAppHystric.class)
public interface ChezuAppService {
    /**
     * 
        * @Title: sendShopMsg
        * @Description: TODO(奖励兑换成功通知)
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
        * @Title: verifyIDCardMsg
        * @Description: TODO(身份证审核成功通知)
        * @param @param userId
        * @param @param goodsName
        * @param @param sign
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
        * @author Lcc
     */
    @RequestMapping("/app/msg/verifyIDCardMsg")
    public boolean verifyIDCardMsg(@RequestParam (value = "userId") Long userId,@RequestParam (value = "userName") String userName,
    		@RequestParam (value = "cardNumber") String cardNumber,@RequestParam (value = "sign") String sign);
    
    @RequestMapping("/app/msg/verifyVehicleTravelMsg")
    public boolean verifyVehicleTravelMsg(@RequestParam (value = "userId") Long userId,@RequestParam (value = "userName") String userName,
    		@RequestParam (value = "cardNumber") String cardNumber,@RequestParam (value = "sign") String sign);
    
    /**
	 * 
	    * @Title: sendGoodsSendMsg
	    * @Description: TODO(发货成功通知)
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
