package com.idata365.app.controller.security;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	// @Autowired
	// private UsersService usersService;

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
		List<Map<String, String>> list = prizeService.getPrizes();
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
		System.out.println(userId);
		Long prizeId = Long.valueOf(requestBodyParams.get("rewardId").toString());
		Prize prize = prizeService.getPrize(prizeId);
		String areaCode = requestBodyParams.get("areaCode").toString();

		Order order = new Order();
		order.setUserid(userId);
		order.setDiamondnum(Integer.valueOf(requestBodyParams.get("diamondNum").toString()));
		order.setOrdertype("0");
		order.setOrdernum(Integer.valueOf(requestBodyParams.get("rewardNum").toString()));
		order.setOrderstatus("1");
		order.setOrdertime(new Date());
		order.setPrizeid(prizeId);
		order.setName(requestBodyParams.get("name").toString());
		order.setPhone(requestBodyParams.get("phone").toString());
		order.setAddress(requestBodyParams.get("address").toString());
		order.setProvincecode(requestBodyParams.get("provinceCode").toString());
		order.setCitycode(requestBodyParams.get("cityCode").toString());
		if (areaCode != null) {
			order.setAreacode(areaCode);
		}
		try {
			orderService.save(order);
			boolean shopMsg = chezuAppService.sendShopMsg(userId, prize.getPrizename(), SignUtils.encryptHMAC(userId+""+prize.getPrizename()));
			System.out.println("兑换成功："+shopMsg);
			return ResultUtils.rtSuccess(null);
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
		List<Map<String, String>> list = orderService.getOrderList(userId);
		return ResultUtils.rtSuccess(list);
	}

}
