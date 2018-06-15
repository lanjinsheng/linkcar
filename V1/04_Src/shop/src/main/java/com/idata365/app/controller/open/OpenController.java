package com.idata365.app.controller.open;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.AuctionGoods;
import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.OrderService;
import com.idata365.app.service.PrizeService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.ServerUtil;

/**
 * @ClassName: ShopController
 * @Description: TODO(兑换请求操作的控制层)
 * @author LiXing
 * @date 2018年5月2日
 *
 */

@RestController("shop")
public class OpenController extends BaseController {

	@Autowired
	ChezuAssetService chezuAssetService;

	@Autowired
	private PrizeService prizeService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ChezuAppService chezuAppService;

	/**
	 * 
	 * @Title: getOrderList
	 * @Description: TODO(实物订单list)
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
	@RequestMapping(value = "/ment/getOrderPageList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getOrderPageList(HttpServletRequest request) {
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));
		return orderService.orderList(map);
	}

	/**
	 * 
	 * @Title: getVirtualOrderList
	 * @Description: TODO(虚拟物品订单list)
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
	@RequestMapping(value = "/ment/getVirtualOrderPageList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getVirtualOrderPageList(HttpServletRequest request) {
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));
		return orderService.orderListVirtual(map);
	}

	// 实物发货
	@RequestMapping(value = "/ment/sendReward", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPageSendReward(HttpServletRequest request) {
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));
		Long orderId = Long.valueOf(request.getParameter("convertId").toString());
		String operatingUser = request.getParameter("operatingUser").toString();
		int status = orderService.sendReward(orderId, operatingUser);
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(status));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}

	// 虚物发货
	@RequestMapping(value = "/ment/sendVirtualReward", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPageSendVirtualReward(HttpServletRequest request) {
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));
		Long orderId = Long.valueOf(request.getParameter("convertId").toString());
		String operatingUser = request.getParameter("operatingUser").toString();
		int status = orderService.sendVirtualReward(orderId, operatingUser);
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(status));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}
	
	/**
	 * 
	 * @Title: publishAuctionPage
	 * @Description: TODO(发布竞拍商品)
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
	@RequestMapping(value = "/ment/publishAuctionPage", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String publishAuctionPage(HttpServletRequest request) {
		
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));
		Long orderId = Long.valueOf(request.getParameter("convertId").toString());
		String operatingUser = request.getParameter("operatingUser").toString();
		int status = orderService.sendVirtualReward(orderId, operatingUser);
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(status));
		ServerUtil.putSuccess(map);
		return sb.toString();
		
		
//		String prizeName = requestBodyParams.get("title").toString();
//		String prizeDesc = requestBodyParams.get("desc").toString();
//		String prizePic = requestBodyParams.get("imgs").toString();
//		int type = Integer.valueOf(requestBodyParams.get("type").toString());
//		BigDecimal startDiamond = BigDecimal
//				.valueOf(Double.valueOf(String.valueOf(requestBodyParams.get("startValue"))));
//		BigDecimal stepPrice = BigDecimal.valueOf(Double.valueOf(String.valueOf(requestBodyParams.get("difference"))));
//		Date auctionStartTime = DateTools.getDateTimeOfStr(requestBodyParams.get("startTime").toString(),
//				"yyyy-MM-dd HH:mm");
//		Date auctionEndTime = DateTools.getDateTimeOfStr(requestBodyParams.get("endTime").toString(),
//				"yyyy-MM-dd HH:mm");
//
//		AuctionGoods auctionGoods = new AuctionGoods();
//		auctionGoods.setPrizeName(prizeName);
//		auctionGoods.setPrizeDesc(prizeDesc);
//		auctionGoods.setPrizePic(prizePic);
//		auctionGoods.setStartDiamond(startDiamond);
//		auctionGoods.setStepPrice(stepPrice);
//		auctionGoods.setAuctionStartTime(auctionStartTime);
//		auctionGoods.setAuctionEndTime(auctionEndTime);
//		auctionGoods.setAuctionRealEndTime(auctionEndTime);
//		auctionGoods.setOfUserId(userId);
//		auctionGoods.setAuctionGoodsType(type);
//		int f = auctionService.insertAuctionGoods(auctionGoods);
//		if (f == 0) {
//			return ResultUtils.rtFail(null);
//		} else {
//			return ResultUtils.rtSuccess(null);
//		}
	}
}
