package com.idata365.app.controller.security;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.constant.OrderTypeConstant;
import com.idata365.app.entity.Order;
import com.idata365.app.entity.Prize;
import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.OrderService;
import com.idata365.app.service.PrizeService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

/**
 * @ClassName: ShopController
 * @Description: TODO(兑换请求操作的控制层)
 * @author LiXing
 * @date 2018年5月2日
 *
 */

@RestController
public class ShopController extends BaseController {

	@Autowired
	ChezuAssetService chezuAssetService;

	@RequestMapping("/test/doTest")
	public Map<String, Object> doTest(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		String sign = SignUtils.encryptHMAC(String.valueOf(userId));
		Map<String, Object> map = chezuAssetService.getUserAsset(userId, sign);
		return ResultUtils.rtSuccess(map);
	}

	@Autowired
	private PrizeService prizeService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ChezuAppService chezuAppService;

	/**
	 * 
	 * @Title: getPrizeList
	 * @Description: TODO(获取所有可兑换奖品)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/getPrizeList")
	public Map<String, Object> getPrizeList() {
		long userId = this.getUserId();
		List<Map<String, String>> list = prizeService.getPrizes(userId);
		return ResultUtils.rtSuccess(list);
	}

	/**
	 * 
	 * @Title: saveOrder
	 * @Description: TODO(兑换请求，保存订单)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 * @param @throws
	 *            Exception 参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/saveOrder")
	public Map<String, Object> saveOrder(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) throws Exception {
		Long userId = super.getUserId();
		LOG.info("userId=================" + userId);
		Long prizeId = Long.valueOf(requestBodyParams.get("rewardId").toString());
		Prize prize = prizeService.getPrize(prizeId);
		String areaCode = requestBodyParams.get("areaCode").toString();

		Order order = new Order();
		order.setUserId(userId);
		order.setDiamondNum(BigDecimal.valueOf(Double.valueOf(String.valueOf(requestBodyParams.get("auctionDiamond")))));
		order.setOrderType("0");
		order.setOrderNum(Integer.valueOf(requestBodyParams.get("rewardNum").toString()));
		order.setOrderStatus("1");
		order.setOrderTime(new Date());
		order.setPrizeId(prizeId);
		order.setName(requestBodyParams.get("name").toString());
		order.setPhone(requestBodyParams.get("phone").toString());
		order.setAddress(requestBodyParams.get("address").toString());
		order.setProvinceCode(requestBodyParams.get("provinceCode").toString());
		order.setCityCode(requestBodyParams.get("cityCode").toString());
		order.setBusinessType(OrderTypeConstant.AUCTION);
		if (areaCode != null) {
			order.setAreaCode(areaCode);
		}
		try {
			orderService.save(order,prize.getOfUserId());
			boolean shopMsg = chezuAppService.sendShopMsg(userId, prize.getPrizeName(), SignUtils.encryptHMAC(userId+""+prize.getPrizeName()));
			LOG.info("兑换结果=================" + shopMsg);
			if(shopMsg) {
				return ResultUtils.rtSuccess(null);
			}else {
				return ResultUtils.rtFail(null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.rtFail(null);
		}

	}

	/**
	 * 
	 * @Title: getOrderList
	 * @Description: TODO(获取当前用户交易列表)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/getOrderList")
	public Map<String, Object> getOrderList(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Long userId = super.getUserId();
		LOG.info("userId=================" + userId);
		List<Map<String, String>> list = orderService.getOrderList(userId);
		return ResultUtils.rtSuccess(list);
	}

}
