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
    /**
     * 
     * @param auctionGoodsId
     * @param auctionGoodsType
     * @param eventType 0 失敗人員发送，1，成功人员去填寫，2，工作人員已发货处理
     * @param userIds
     * @param goodsName
     * @param sign  auctionGoodsId+eventType+userIds 进行签名
     * @return
     */
    @RequestMapping("/app/msg/sendAuctionMsg")
    public boolean sendAuctionMsg(@RequestParam (value = "auctionGoodsId") Long auctionGoodsId,
    		@RequestParam (value = "auctionGoodsType") Integer auctionGoodsType,
    		@RequestParam (value = "eventType") Integer eventType,
    		@RequestParam (value = "userIds") String userIds,
    		@RequestParam (value = "goodsName") String goodsName,
    		@RequestParam (value = "sign") String sign);
}
