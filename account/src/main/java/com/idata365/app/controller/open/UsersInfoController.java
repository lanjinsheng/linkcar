package com.idata365.app.controller.open;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.entity.IDCard;
import com.idata365.app.entity.LicenseVehicleTravel;
import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.ServerUtil;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.StaticDatas;
import com.idata365.app.util.ValidTools;

@RestController
public class UsersInfoController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(UsersInfoController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ChezuAppService chezuAppService;
	@Autowired
	SystemProperties systemProperties;

	/**
	 * 
	 * @Title: getUserIDCards
	 * @Description: TODO(获取身份证信息)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lixing
	 */
	@RequestMapping(value = "/ment/getUserIDCards", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPageUserIDCards(HttpServletRequest request) {
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));
		String imgBase = getImgBasePath();
		List<IDCard> iDCards = userInfoService.getUserIDCards();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (IDCard iDCard : iDCards) {
			Map<String, String> rtMap = new HashMap<String, String>();
			if (iDCard != null) {// 驾驶证
				rtMap.put("userName", iDCard.getUserName());
				rtMap.put("gender", iDCard.getGender());
				rtMap.put("nation", iDCard.getNation());
				rtMap.put("address", iDCard.getAddress());
				rtMap.put("birthday", iDCard.getBirthday());
				rtMap.put("firstDay", iDCard.getFirstDay());
				rtMap.put("lastDay", iDCard.getLastDay());
				rtMap.put("cardNumber", iDCard.getCardNumber());
				rtMap.put("office", iDCard.getOffice());
				rtMap.put("userId", iDCard.getUserId().toString());
				rtMap.put("status", iDCard.getUserId().toString());
				if (ValidTools.isBlank(iDCard.getFrontImgUrl())) {
					rtMap.put("frontDrivingImg", "");
				} else {
					rtMap.put("frontDrivingImg", imgBase + iDCard.getFrontImgUrl());
				}
				if (ValidTools.isBlank(iDCard.getBackImgUrl())) {
					rtMap.put("backDrivingImg", "");
				} else {
					rtMap.put("backDrivingImg", imgBase + iDCard.getBackImgUrl());
				}
			} else {
				rtMap.put("userName", "");
				rtMap.put("gender", "");
				rtMap.put("nation", "");
				rtMap.put("address", "");
				rtMap.put("birthday", "");
				rtMap.put("firstDay", "");
				rtMap.put("lastDay", "");
				rtMap.put("cardNumber", "");
				rtMap.put("office", "");
				rtMap.put("userId", "");
				rtMap.put("status", "");
			}
			result.add(rtMap);
		}
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(result));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}

	// 审核身份证
	@RequestMapping(value = "/ment/verifyIDCard", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPageverifyIDCard(HttpServletRequest request) {
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));

		int status = userInfoService.modifyIDCard(map);
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(status));
		boolean verifyIDCardMsg = chezuAppService.verifyIDCardMsg(Long.valueOf(String.valueOf(map.get("userId"))),
				String.valueOf(map.get("userName")), String.valueOf(map.get("cardNumber")),
				SignUtils.encryptHMAC(map.get("userName") + "" + map.get("cardNumber")));
		LOG.info("审核结果=================" + verifyIDCardMsg);
		ServerUtil.putSuccess(map);
		return sb.toString();
	}

	// 后台行驶证
	@RequestMapping(value = "/ment/getPageUserLicenseVehicleTravels", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPageUserLicenseVehicleTravels(HttpServletRequest request) {
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));
		String imgBase = getImgBasePath();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<LicenseVehicleTravel> licenseVehicleTravels = userInfoService.findLicenseVehicleTravels();
		for (LicenseVehicleTravel licenseVehicleTravel : licenseVehicleTravels) {
			Map<String, String> rtMap = new HashMap<String, String>();

			if (licenseVehicleTravel != null) {// 行驶证
				licenseVehicleTravel.getUserId();

				rtMap.put("plateNo", licenseVehicleTravel.getPlateNo());
				rtMap.put("cardTypeDesc", StaticDatas.VEHILCE.get(String.valueOf(licenseVehicleTravel.getCarType())));
				rtMap.put("userTypeDesc", StaticDatas.VEHILCE_USETYPE.get(licenseVehicleTravel.getUseType()));
				rtMap.put("modelTypeDesc", licenseVehicleTravel.getModelType());
				rtMap.put("vin", licenseVehicleTravel.getVin());
				rtMap.put("engineNo", licenseVehicleTravel.getEngineNo());
				if (ValidTools.isBlank(licenseVehicleTravel.getFrontImgUrl())) {
					rtMap.put("frontTravelImg", "");
				} else {
					rtMap.put("frontTravelImg", imgBase + licenseVehicleTravel.getFrontImgUrl());
				}

				if (ValidTools.isBlank(licenseVehicleTravel.getBackImgUrl())) {
					rtMap.put("backTravelImg", "");
				} else {
					rtMap.put("backTravelImg", imgBase + licenseVehicleTravel.getBackImgUrl());
				}
				rtMap.put("issueDate", licenseVehicleTravel.getIssueDate());
				rtMap.put("regDate", licenseVehicleTravel.getRegDate());
				rtMap.put("isTravelEdit", String.valueOf(licenseVehicleTravel.getIsTravelEdit()));
				rtMap.put("userId", licenseVehicleTravel.getUserId().toString());
			} else {
				rtMap.put("plateNo", "");
				rtMap.put("cardTypeDesc", "");
				rtMap.put("userTypeDesc", "");
				rtMap.put("modelTypeDesc", "");
				rtMap.put("vin", "");
				rtMap.put("engineNo", "");
				rtMap.put("frontTravelImg", "");
				rtMap.put("backTravelImg", "");
				rtMap.put("issueDate", "");
				rtMap.put("regDate", "");
				rtMap.put("isTravelEdit", "1");
				rtMap.put("userId", "");
			}
			result.add(rtMap);
		}
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(result));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}

	// 审核行驶证
	@RequestMapping(value = "/ment/verifyLicenseVehicleTravel", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPageLicenseVehicleTravel(HttpServletRequest request) {
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));
		int status = userInfoService.verifyLicenseVehicleTravel(map);
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(status));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}
}
