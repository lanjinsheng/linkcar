package com.idata365.app.controller.open;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.OrderService;
import com.idata365.app.service.PrizeService;
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
	 * @Title: getPrizeList
	 * @Description: TODO(获取所有可兑换奖品)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/test/getPrizeList")
	public Map<String, Object> getPrizeList() {
		List<Map<String, String>> list = prizeService.getPrize();
		return ResultUtils.rtSuccess(list);
	}

	/**
	 * 
	 * @Title: getOrderList
	 * @Description: TODO(订单list)
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
	@RequestMapping(value = "/test/getOrderPageList",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getOrderPageList(HttpServletRequest request) {
		Map<String, Object> map=this.getPagerMap(request);
    	map.putAll(requestParameterToMap(request));
		return orderService.orderList(map);
	}
}
