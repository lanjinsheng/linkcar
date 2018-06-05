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
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.LicenseDriver;
import com.idata365.app.entity.LicenseVehicleTravel;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.ServerUtil;
import com.idata365.app.util.StaticDatas;
import com.idata365.app.util.ValidTools;

@RestController
public class UsersInfoController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(UsersInfoController.class);
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	SystemProperties systemProperties;

	/**
	 * 
	 * @Title: getUserLicenseDrivers
	 * @Description: TODO(获取证件信息)
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
	@RequestMapping(value = "/ment/getUserLicenseDrivers",method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPageUserLicenseDrivers(HttpServletRequest request) {
		Map<String, Object> map=this.getPagerMap(request);
    	map.putAll(requestParameterToMap(request));
		String imgBase = getImgBasePath();
		List<LicenseDriver> licenseDrives = userInfoService.getUserLicenseDrivers();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (LicenseDriver licenseDrive : licenseDrives) {
			Map<String, String> rtMap = new HashMap<String, String>();
			if (licenseDrive != null) {// 驾驶证
				rtMap.put("userName", licenseDrive.getUserName());
				rtMap.put("gender", licenseDrive.getGender());
				rtMap.put("nation", licenseDrive.getNation());
				rtMap.put("driveCardType", licenseDrive.getDriveCardType());
				rtMap.put("birthday", licenseDrive.getBirthday());
				rtMap.put("virginDay", licenseDrive.getVirginDay());
				rtMap.put("validDay", licenseDrive.getValidDay());
				rtMap.put("validYears", String.valueOf(licenseDrive.getValidYears()));
				rtMap.put("userId", licenseDrive.getUserId().toString());
				if (ValidTools.isBlank(licenseDrive.getFrontImgUrl())) {
					rtMap.put("frontDrivingImg", "");
				} else {
					rtMap.put("frontDrivingImg", imgBase + licenseDrive.getFrontImgUrl());
				}
				if (ValidTools.isBlank(licenseDrive.getBackImgUrl())) {
					rtMap.put("backDrivingImg", "");
				} else {
					rtMap.put("backDrivingImg", imgBase + licenseDrive.getBackImgUrl());
				}
				rtMap.put("isDrivingEdit", String.valueOf(licenseDrive.getIsDrivingEdit()));
			} else {
				rtMap.put("userName", "");
				rtMap.put("gender", "");
				rtMap.put("nation", "");
				rtMap.put("driveCardType", "");
				rtMap.put("birthday", "");
				rtMap.put("virginDay", "");
				rtMap.put("validYears", "");
				rtMap.put("validDay", "");
				rtMap.put("frontDrivingImg", "");
				rtMap.put("backDrivingImg", "");
				rtMap.put("isDrivingEdit", "1");
				rtMap.put("userId", "");
			}
			result.add(rtMap);
		}
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(result));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}

	@RequestMapping(value = "/ment/getPageUserLicenseVehicleTravels",method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPageUserLicenseVehicleTravels(HttpServletRequest request) {
		Map<String, Object> map=this.getPagerMap(request);
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
	
	//审核驾驶证
	@RequestMapping(value = "/ment/verifyLicenseDriver",method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPageLicenseDriver(HttpServletRequest request) {
		Map<String, Object> map=this.getPagerMap(request);
    	map.putAll(requestParameterToMap(request));
		Long userId = Long.valueOf(request.getParameter("userId").toString());
		Long operatingUserId = 700L;
		int status = userInfoService.verifyLicenseDriver(userId,operatingUserId);
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(status));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}
	
	//审核行驶证
		@RequestMapping(value = "/ment/verifyLicenseVehicleTravel",method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
		public @ResponseBody String getPageLicenseVehicleTravel(HttpServletRequest request) {
			Map<String, Object> map=this.getPagerMap(request);
	    	map.putAll(requestParameterToMap(request));
			String plateNo = request.getParameter("plateNo").toString();
			Long operatingUserId = 700L;
			int status = userInfoService.verifyLicenseVehicleTravel(plateNo,operatingUserId);
			StringBuffer sb = new StringBuffer("");
			sb.append(ServerUtil.toJson(status));
			ServerUtil.putSuccess(map);
			return sb.toString();
		}
}
