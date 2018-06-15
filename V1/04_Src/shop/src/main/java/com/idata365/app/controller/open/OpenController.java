package com.idata365.app.controller.open;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.entity.AuctionGoods;
import com.idata365.app.enums.UserImgsEnum;
import com.idata365.app.partnerApi.QQSSOTools;
import com.idata365.app.partnerApi.SSOTools;
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
	@Autowired
	SystemProperties systemProperties;

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
	
	@RequestMapping(value = "/ment/openUploadAuctionImg", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public Map<String, Object> openUploadAuctionImgPage(@RequestParam CommonsMultipartFile file,
			@RequestParam Map<String, Object> map) {
		Long userId = 1L;
		if (file == null) {
			return ResultUtils.rtFailParam(null, "附件为空");
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("userId", userId);
		rtMap.put("imgUrl", "");
		try {
			String key = "";
			if (systemProperties.getSsoQQ().equals("1")) {// 走qq
				key = QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.AUCTION);
				File dealFile = new File(systemProperties.getFileTmpDir() + "/" + key);
				File fileParent = dealFile.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				file.transferTo(dealFile);
				QQSSOTools.saveOSS(dealFile, key);
			} else {// 走阿里
					// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
				key = SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.AUCTION);
				// 处理图片
				File dealFile = new File(systemProperties.getFileTmpDir() + "/" + key);
				File fileParent = dealFile.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				InputStream is = file.getInputStream();
				SSOTools.saveOSS(is, key);
				is.close();
				file.transferTo(dealFile);
			}
			rtMap.put("imgUrl", getImgBasePath() + key);
			rtMap.put("key", key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.rtFail(null);
		}
		rtMap.remove("key");
		rtMap.remove("userId");
		return ResultUtils.rtSuccess(rtMap);
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
		StringBuffer sb = new StringBuffer("");
//		sb.append(ServerUtil.toJson(status));
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
