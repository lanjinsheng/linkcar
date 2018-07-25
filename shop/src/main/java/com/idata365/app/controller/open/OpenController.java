package com.idata365.app.controller.open;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.idata365.app.service.AuctionService;
import com.idata365.app.service.OrderService;
import com.idata365.app.util.DateTools;
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
	private OrderService orderService;
	@Autowired
	private SystemProperties systemProperties;
	@Autowired
	private AuctionService auctionService;

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
		int status = orderService.sendVirtualReward(orderId, map, operatingUser);
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(status));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}
	
	@RequestMapping(value = "/ment/openUploadAuctionImg", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String openUploadAuctionImgPage(@RequestParam CommonsMultipartFile file,
			@RequestParam Map<String, Object> map) {
		Long userId = 1L;
		if (file == null) {
			return null;
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
//				QQSSOTools.saveOSS(dealFile, key);
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
			return null;
		}
		rtMap.remove("key");
		rtMap.remove("userId");
		StringBuffer sb = new StringBuffer("\""+rtMap.get("imgUrl").toString()+"\"}");
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
		long userId = 0;
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));
		
		String prizeName = map.get("title").toString();
		String prizeDesc = map.get("desc").toString();
		String prizePic = map.get("imgs").toString();
		int type = Integer.valueOf(map.get("type").toString());
		BigDecimal startDiamond = BigDecimal
				.valueOf(Double.valueOf(String.valueOf(map.get("startValue"))));
		BigDecimal stepPrice = BigDecimal.valueOf(Double.valueOf(String.valueOf(map.get("difference"))));
		Date auctionStartTime = DateTools.getDateTimeOfStr(map.get("startTime").toString(),
				"yyyy-MM-dd HH:mm:ss");
		Date auctionEndTime = DateTools.getDateTimeOfStr(map.get("endTime").toString(),
				"yyyy-MM-dd HH:mm:ss");
		if("管理员".equals(map.get("operatingUser"))) {
			userId = 1;
		}
		AuctionGoods auctionGoods = new AuctionGoods();
		auctionGoods.setPrizeName(prizeName);
		auctionGoods.setPrizeDesc(prizeDesc);
		auctionGoods.setPrizePic(prizePic);
		auctionGoods.setStartDiamond(startDiamond);
		auctionGoods.setStepPrice(stepPrice);
		auctionGoods.setAuctionStartTime(auctionStartTime);
		auctionGoods.setAuctionEndTime(auctionEndTime);
		auctionGoods.setAuctionRealEndTime(auctionEndTime);
		auctionGoods.setOfUserId(userId);
		auctionGoods.setAuctionGoodsType(type);
		int status = auctionService.insertAuctionGoods(auctionGoods);
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(status));
		ServerUtil.putSuccess(map);
		return sb.toString();
		
	}
}
