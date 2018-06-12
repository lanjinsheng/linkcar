package com.idata365.app.controller.security;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.AuctionService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

/**
 * @ClassName: ShopController
 * @Description: TODO(竞拍操作的控制层)
 * @author Lcc
 * @date 2018年6月12日
 *
 */

@RestController
public class AuctionController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(AuctionController.class);

	@Autowired
	private AuctionService auctionService;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	private ChezuAppService chezuAppService;

	/**
	 * 
	 * @Title: listAuctionGoods
	 * @Description: TODO(所有钻石竞拍列表)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/listAuctionGoods")
	public Map<String, Object> listAuctionGoods() {
		Long userId = this.getUserId();
		String sign = SignUtils.encryptHMAC(String.valueOf(userId));
		LOG.info("userId=================" + userId);
		LOG.info("sign=================" + sign);
		Map<String, Object> rtMap = new HashMap<>();
		Map<String, String> diamond = new HashMap<>();
		Map<String, Object> userAsset = chezuAssetService.getUserAsset(userId, sign);
		diamond.put("diamondHoldNum",
				BigDecimal.valueOf(Double.valueOf(String.valueOf(userAsset.get("diamondsNum"))))
						.setScale(2, RoundingMode.HALF_UP).toString());
		List<Map<String, String>> list = auctionService.listAuctionGoods();
		rtMap.put("diamondHoldNum", diamond);
		rtMap.put("auctionGoodsList", list);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 
	 * @Title: myListAuctionGoods
	 * @Description: TODO(我的竞拍商品（竞拍记录）)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/myListAuctionGoods")
	public Map<String, Object> myListAuctionGoods() {
		Long userId = this.getUserId();
		LOG.info("userId=================" + userId);
		List<Map<String, String>> list = auctionService.myListAuctionGoods(userId);
		return ResultUtils.rtSuccess(list);
	}

}
