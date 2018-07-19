package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.ScoreService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.serviceV2.CarService;
import com.idata365.app.serviceV2.InteractService;
import com.idata365.app.serviceV2.ScoreServiceV2;
import com.idata365.app.serviceV2.UserHomeService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.ValidTools;

@RestController
public class UserHomeController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(UserHomeController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	private FamilyService familyService;
	@Autowired
	private InteractService interactService;
	@Autowired
	private CarService carService;
	@Autowired
	private ScoreServiceV2 scoreServiceV2;
	@Autowired
	private UserHomeService userHomeService;

	public UserHomeController() {
		System.out.println("UserHomeController");
	}

	/**
	 * 
	 * @Title: queryUserHome
	 * @Description: TODO(查看用户车库)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             Lcc
	 */
	@RequestMapping("/queryUserHome")
	public Map<String, Object> queryUserHome(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("userId")))
			return ResultUtils.rtFailParam(null);
		long ownerId = this.getUserId();
		long userId = Long.valueOf(requestBodyParams.get("userId").toString()).longValue();
		LOG.info("ownerId=========================" + ownerId);
		LOG.info("userId=========================" + userId);
		String imgBasePath = this.getImgBasePath();
		Map<String, Object> rtMap = userHomeService.queryUserHome(userId, ownerId, imgBasePath);

		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 
        * @Title: queryUsersCar
        * @Description: TODO(更换车辆页面)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return 参数
        * @return Map<String,Object> 返回类型
        * @throws
        * @author LiXing
	 */
	@RequestMapping("/queryUserCarList")
	public Map<String, Object> queryUsersCar(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId========================="+userId);
		Map<String, Object> rtMap = new HashMap<>();;
		try {
			rtMap = this.carService.queryUsersCar(userId);
			return ResultUtils.rtSuccess(rtMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.rtSuccess(null);
		}
	}
	
	/**
	 * 
        * @Title: queryClubBonusInfo
        * @Description: TODO(获取俱乐部奖金详情)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return 参数
        * @return Map<String,Object> 返回类型
        * @throws
        * @author LiXing
	 */
	@RequestMapping("/queryClubBonusInfo")
	public Map<String, Object> queryClubBonusInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId=========================" + userId);
		Map<String, Object> rtMap = this.scoreServiceV2.queryClubBonusInfo(userId);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 
        * @Title: receiveClubBonus
        * @Description: TODO(领取俱乐部奖金)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return 参数
        * @return Map<String,Object> 返回类型
        * @throws
        * @author LiXing
	 */
	@RequestMapping("/receiveClubBonus")
	public Map<String, Object> receiveClubBonus(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<>();
		long userId = this.getUserId();
		LOG.info("userId=========================" + userId);
		Map<String, Object> map = this.scoreServiceV2.queryClubBonusInfo(userId);
		boolean b = this.chezuAssetService.receiveClubBonus(userId, Long.valueOf(map.get("totalPower").toString()), "sign");
		if(b) {
			rtMap.put("receiveStatus", "1");
		}else {
			rtMap.put("receiveStatus", "0");
		}
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 
        * @Title: changeCar
        * @Description: TODO(更换车辆操作)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return 参数
        * @return Map<String,Object> 返回类型
        * @throws
        * @author LiXing
	 */
	@RequestMapping("/changeCar")
	public Map<String, Object> changeCar(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		Integer carId = Integer.valueOf(requestBodyParams.get("carId").toString());
		LOG.info("userId=========================" + userId);
		LOG.info("carId=========================" + carId);
		int i = this.carService.changeCar(userId, carId);
		if (i == 1) {
			return ResultUtils.rtSuccess(null);
		} else {
			return ResultUtils.rtFail(null, "顺风车载客中，更换失败", "100");
		}
		
	}
}