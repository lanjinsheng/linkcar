package com.idata365.app.remote;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @ClassName: ChezuService
 * @Description: TODO(资产的增加与减少)
 * @author LanYeYe
 * @date 2017年12月28日
 *
 */
@FeignClient(value = "service-asset-chezu", fallback = ChezuAssetHystric.class)
public interface ChezuAssetService {
	/**
	 *
	 * @param userId
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/asset/getUserAsset", method = RequestMethod.POST)
	Map<String, Object> getUserAsset(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "sign") String sign);

	/**
	 * 
	 * @param ofUserId 
	 * @Title: submitDiamondAsset
	 * @Description: TODO(返回false消费失败，true消费成功)
	 * @param @param
	 *            userId
	 * @param @param
	 *            diamondNum
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/submitDiamondAsset", method = RequestMethod.POST)
	boolean submitDiamondAsset(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "diamondNum") double diamondNum, @RequestParam(value = "sign") String sign,@RequestParam(value = "ofUserId") long ofUserId);

	/**
	 * (返回false消费失败，true消费成功)
	 * @param userId
	 * @param diamondNum
	 * @param sign
	 * @param preUserId
	 * @param auctionGoodsId
	 * @return
	 */
	@RequestMapping(value = "/asset/freezeDiamondAsset", method = RequestMethod.POST)
	Map<String,String> freezeDiamondAsset(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "diamondNum") double diamondNum, @RequestParam(value = "sign") String sign,@RequestParam(value = "preUserId") long preUserId,@RequestParam(value = "auctionGoodsId")long auctionGoodsId);
 
	@RequestMapping(value = "/asset/unfreezeDiamondAsset", method = RequestMethod.POST)
	boolean 
	unfreezeDiamondAsset(@RequestParam(value = "buyerId") long buyerId,@RequestParam(value = "sellerId") long sellerId,
			@RequestParam(value = "auctionGoodsId") long auctionGoodsId,
			@RequestParam(value = "diamondBidNum") double diamondBidNum,@RequestParam(value = "diamondNum") double diamondNum, @RequestParam(value = "sign") String sign );
}
