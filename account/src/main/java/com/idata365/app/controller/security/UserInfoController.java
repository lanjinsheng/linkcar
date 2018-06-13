package com.idata365.app.controller.security;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.entity.IDCard;
import com.idata365.app.entity.LicenseDriver;
import com.idata365.app.entity.LicenseVehicleTravel;
import com.idata365.app.entity.UserConfig;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.enums.UserImgsEnum;
import com.idata365.app.partnerApi.QQSSOTools;
import com.idata365.app.partnerApi.SSOTools;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.ImageUtils;
import com.idata365.app.util.PhoneUtils;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.StaticDatas;
import com.idata365.app.util.ValidTools;

@RestController
public class UserInfoController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(UserInfoController.class);
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	SystemProperties systemProperties;

	/**
	 * 
	 * @Title: getUserInfo
	 * @Description: TODO(获取证件信息)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping("/user/getUserInfo")
	public Map<String, Object> getUserInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		String imgBase = getImgBasePath();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> rtIDCardInfo = new HashMap<String, String>();
		List<Map<String, String>> rtVehicleTravel = new ArrayList<>();
		Long userId = Long.valueOf(requestBodyParams.get("userId").toString());
		IDCard idCard = userInfoService.getIDCard(userId);
		List<LicenseVehicleTravel> licenseVehicleTravels = userInfoService.getLicenseVehicleTravelByUserId(userId);
		if (idCard != null) {// 驾驶证
			rtIDCardInfo.put("userName",idCard.getUserName());
			rtIDCardInfo.put("gender",idCard.getGender());
			rtIDCardInfo.put("nation",idCard.getNation());
			rtIDCardInfo.put("birthday",idCard.getBirthday());
			rtIDCardInfo.put("firstDay",idCard.getFirstDay());
			rtIDCardInfo.put("lastDay",idCard.getLastDay());
			rtIDCardInfo.put("cardNumber",idCard.getCardNumber());
			rtIDCardInfo.put("address",idCard.getAddress());
			rtIDCardInfo.put("office",idCard.getOffice());
			rtIDCardInfo.put("status",String.valueOf(idCard.getStatus()));
			if (ValidTools.isBlank(idCard.getFrontImgUrl())) {
				rtIDCardInfo.put("frontImgUrl", "");
			} else {
				rtIDCardInfo.put("frontImgUrl", imgBase + idCard.getFrontImgUrl());
			}
			if (ValidTools.isBlank(idCard.getBackImgUrl())) {
				rtIDCardInfo.put("backImgUrl", "");
			} else {
				rtIDCardInfo.put("backImgUrl", imgBase + idCard.getBackImgUrl());
			}
		} else {
			rtIDCardInfo.put("userName", "" );
			rtIDCardInfo.put("gender",  "");
			rtIDCardInfo.put("nation", "");
			rtIDCardInfo.put("driveCardType", "" );
			rtIDCardInfo.put("birthday", "");
			rtIDCardInfo.put("firstDay", "");
			rtIDCardInfo.put("lastDay",  "");
			rtIDCardInfo.put("cardNumber", "");
			rtIDCardInfo.put("address",  "");
			rtIDCardInfo.put("office",  "");
			rtIDCardInfo.put("status", "" );
			rtIDCardInfo.put("frontImgUrl", "");
			rtIDCardInfo.put("backImgUrl", "");
		}
		result.put("rtIDCardInfo", rtIDCardInfo);
		
		
		if(licenseVehicleTravels!=null&&licenseVehicleTravels.size()!=0) {
			for (LicenseVehicleTravel licenseVehicleTravel : licenseVehicleTravels) {
				Map<String, String> vehicleTravel = new HashMap<String, String>();
				vehicleTravel.put("plateNo", licenseVehicleTravel.getPlateNo());
				vehicleTravel.put("cardTypeDesc", StaticDatas.VEHILCE.get(String.valueOf(licenseVehicleTravel.getCarType())));
				vehicleTravel.put("userTypeDesc", StaticDatas.VEHILCE_USETYPE.get(licenseVehicleTravel.getUseType()));
				vehicleTravel.put("modelTypeDesc", licenseVehicleTravel.getModelType());
				vehicleTravel.put("vin", licenseVehicleTravel.getVin());
				vehicleTravel.put("engineNo", licenseVehicleTravel.getEngineNo());
				vehicleTravel.put("status",String.valueOf(licenseVehicleTravel.getStatus()));
				vehicleTravel.put("userName",licenseVehicleTravel.getOwnerName());
				if (ValidTools.isBlank(licenseVehicleTravel.getFrontImgUrl())) {
					vehicleTravel.put("frontTravelImg", "");
				} else {
					vehicleTravel.put("frontTravelImg", imgBase + licenseVehicleTravel.getFrontImgUrl());
				}
				vehicleTravel.put("issueDate", licenseVehicleTravel.getIssueDate());
				vehicleTravel.put("regDate", licenseVehicleTravel.getRegDate());
				rtVehicleTravel.add(vehicleTravel);
			}
		}else {
			Map<String, String> vehicleTravel = new HashMap<String, String>();
			vehicleTravel.put("plateNo", "");
			vehicleTravel.put("cardTypeDesc", "");
			vehicleTravel.put("userTypeDesc", "");
			vehicleTravel.put("modelTypeDesc", "");
			vehicleTravel.put("vin", "");
			vehicleTravel.put("engineNo", "");
			vehicleTravel.put("frontTravelImg", "");
			vehicleTravel.put("backTravelImg", "");
			vehicleTravel.put("issueDate", "");
			vehicleTravel.put("regDate", "");
			vehicleTravel.put("status","");
			vehicleTravel.put("userName","");
			rtVehicleTravel.add(vehicleTravel);
		}
		
		result.put("rtVehicleTravel", rtVehicleTravel);
		return ResultUtils.rtSuccess(result);
	}

	@RequestMapping("/user/getUserBaseInfo")
	public Map<String, Object> getUserBaseInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		String imgBase = getImgBasePath();
		Map<String, String> rtMap = new HashMap<String, String>();
		UserInfo userInfo = this.getUserInfo();
		IDCard idCard = userInfoService.getIDCard(userInfo.getId());
		List<LicenseVehicleTravel> licenseVehicleTravels = userInfoService.getLicenseVehicleTravelByUserId(userInfo.getId());
		String phone = userInfo.getPhone();
		rtMap.put("phone", phone);

		String nickName = userInfo.getNickName();
		if (StringUtils.isBlank(nickName)) {
			rtMap.put("nickName", PhoneUtils.hidePhone(phone));
		} else {
			rtMap.put("nickName", nickName);
		}

		rtMap.put("headImg", imgBase + userInfo.getImgUrl());
		if (idCard != null && licenseVehicleTravels != null && licenseVehicleTravels.size()!=0) {
			// 证件上传即认证
			rtMap.put("isAuthenticated", "1");
		} else {
			rtMap.put("isAuthenticated", "0");
		}
		UserConfig uc = userInfoService.getUserConfig(this.getUserId());
		rtMap.put("isGPSHidden", String.valueOf(uc.getIsHidden()));
		return ResultUtils.rtSuccess(rtMap);
	}

	/**
	 * 
	 * @Title: uploadFrontIDCardImg
	 * @Description: TODO(身份证正面)
	 * @param @param
	 *            file
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             Lcc
	 */
	@RequestMapping("/user/uploadFrontIDCardImg")
	public Map<String, Object> uploadFrontIDCardImg(@RequestParam CommonsMultipartFile file,
			@RequestParam Map<String, Object> map) {
		Long userId = this.getUserId();
		LOG.info("userId=================" + userId);
		if (file == null) {
			return ResultUtils.rtFailParam(null, "附件为空");
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("userId", userId);
		rtMap.put("imgUrl", "");
		rtMap.put("userName", "");
		rtMap.put("nation", "");
		rtMap.put("gender", "");
		rtMap.put("birthday", "");
		rtMap.put("address", "");
		rtMap.put("cardNumber", "");
		try {
			String key = "";
			if (systemProperties.getSsoQQ().equals("1")) {// 走qq
				key = QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.ID_CARD1);
				File dealFile = new File(systemProperties.getFileTmpDir() + "/" + key);
				File fileParent = dealFile.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				file.transferTo(dealFile);
				QQSSOTools.saveOSS(dealFile, key);
				// ImageUtils.dealImgJSZ(dealFile, rtMap);
			} else {// 走阿里
					// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
				key = SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.ID_CARD1);
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
				// ImageUtils.dealImgJSZ(dealFile, rtMap);
			}
			rtMap.put("imgUrl", getImgBasePath() + key);
			rtMap.put("key", key);
			// 插入更新身份证
			userInfoService.insertImgIDCardFrontImg(rtMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.rtFail(null);
		}
		rtMap.remove("key");
		rtMap.remove("userId");
		return ResultUtils.rtSuccess(null);
	}

	/**
	 * 
	 * @Title: uploadBackIDCardImg
	 * @Description: TODO(身份证背面)
	 * @param @param
	 *            file
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             Lcc
	 */
	@RequestMapping("/user/uploadBackIDCardImg")
	public Map<String, Object> uploadBackIDCardImg(@RequestParam CommonsMultipartFile file,
			@RequestParam Map<String, Object> map) {
		Long userId = this.getUserId();
		if (file == null) {
			return ResultUtils.rtFailParam(null, "附件为空");
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("userId", userId);
		rtMap.put("imgUrl", "");
		rtMap.put("office", "");
		rtMap.put("firstDay", "");
		rtMap.put("lastDay", "");

		try {
			String key = "";
			if (systemProperties.getSsoQQ().equals("1")) {// 走qq
				key = QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.DRIVER_LICENSE2);
				File dealFile = new File(systemProperties.getFileTmpDir() + "/" + key);
				File fileParent = dealFile.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				file.transferTo(dealFile);
				QQSSOTools.saveOSS(dealFile, key);
			} else {// 走阿里
					// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
				key = SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.DRIVER_LICENSE2);
				InputStream is = file.getInputStream();
				SSOTools.saveOSS(is, key);
				is.close();
			}
			rtMap.put("imgUrl", getImgBasePath() + key);
			rtMap.put("key", key);

			// 处理图片
			// File dealFile = new File(systemProperties.getFileTmpDir()+key);
			// file.transferTo(dealFile);
			// ImageUtils.dealImgJSZ(dealFile,rtMap);
			// 插入更新驾驶证
			userInfoService.insertImgIDCardBackImg(rtMap);

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
	 * @Title: uploadFrontTravelLicenseImg
	 * @Description: TODO(行驶证正面)
	 * @param @param
	 *            file
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */

	@RequestMapping("/user/uploadFrontTravelLicenseImg")
	public Map<String, Object> uploadFrontTravelLicenseImg(@RequestParam CommonsMultipartFile file,
			@RequestParam Map<String, Object> map) {
		Long userId = this.getUserId();
		if (file == null) {
			return ResultUtils.rtFailParam(null, "附件为空");
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();

		rtMap.put("userId", userId);
		rtMap.put("engineNo", "");
		rtMap.put("carType", "1");
		rtMap.put("useType", "FYY");
		rtMap.put("modelType", "");
		rtMap.put("vin", "");
		rtMap.put("plateNo", "");
		try {
			String key = "";
			if (systemProperties.getSsoQQ().equals("1")) {// 走qq
				key = QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.VEHICLE_LICENSE1);
				File dealFile = new File(systemProperties.getFileTmpDir() + "/" + key);
				File fileParent = dealFile.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				file.transferTo(dealFile);
				QQSSOTools.saveOSS(dealFile, key);
				ImageUtils.dealImgXSZ(dealFile, rtMap);
			} else {// 走阿里
					// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
				key = SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.VEHICLE_LICENSE1);

				InputStream is = file.getInputStream();
				SSOTools.saveOSS(is, key);
				File dealFile = new File(systemProperties.getFileTmpDir() + "/" + key);
				File fileParent = dealFile.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				file.transferTo(dealFile);
				ImageUtils.dealImgXSZ(dealFile, rtMap);
				is.close();
			}
			rtMap.put("imgUrl", getImgBasePath() + key);
			rtMap.put("key", key);
			// 插入更新驾驶证
			userInfoService.insertImgVehicle1(rtMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.rtFail(null);
		}
		rtMap.remove("key");
		rtMap.remove("userId");
		return ResultUtils.rtSuccess(rtMap);
	}

	public static void main(String[] args) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("userId", 999999999);
		rtMap.put("birthday", "");
		rtMap.put("validYears", "");
		rtMap.put("validDay", "");
		rtMap.put("virginDay", "");
		rtMap.put("name", "");
		rtMap.put("driveCardType", "");
		rtMap.put("gender", "");
		rtMap.put("nation", "C");
		String key = "";

		key = QQSSOTools.createSSOUsersImgInfoKey(999999999, UserImgsEnum.DRIVER_LICENSE1);
		File dealFile = new File("D:/dev/1优数/红点抽奖/xszdemo.jpg");
		File fileParent = dealFile.getParentFile();
		if (!fileParent.exists()) {
			fileParent.mkdirs();
		}
		try {
			QQSSOTools.saveOSS(dealFile, key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ImageUtils.dealImgJSZ(dealFile,rtMap);
		ImageUtils.dealImgJSZ(dealFile, rtMap);
		System.out.println(rtMap.size());
	}

	/**
	 * 
	 * @Title: getVerifyInfo
	 * @Description: TODO(个人身份审核信息)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping("/user/getVerifyInfo")
	public Map<String, Object> getVerifyInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		Map<String, String> rtIDCardMap = new HashMap<String, String>();
		List<Map<String, String>> travelInfoList = new ArrayList<Map<String, String>>();

		Long userId = this.getUserId();
		IDCard idCard = userInfoService.findIDCardByUserId(userId);
		List<LicenseVehicleTravel> licenseVehicleTravels = userInfoService.getLicenseVehicleTravelByUserId(userId);

		if (idCard != null && idCard.getStatus() == 1) {// 身份证
			rtIDCardMap.put("userName", idCard.getUserName());
			rtIDCardMap.put("cardNumber", idCard.getCardNumber().substring(0, 1)+"**************"+idCard.getCardNumber().substring(idCard.getCardNumber().length()-1));
			rtIDCardMap.put("status", "1");
		} else if (idCard != null && idCard.getStatus() == 0) {
			rtIDCardMap.put("userName", "***");
			rtIDCardMap.put("cardNumber", "****************");
			rtIDCardMap.put("status", "0");
		}
		rtMap.put("rtIDCardMap", rtIDCardMap);
		if (licenseVehicleTravels != null && licenseVehicleTravels.size() != 0) {
			for (LicenseVehicleTravel licenseVehicleTravel : licenseVehicleTravels) {
				Map<String, String> travelInfo = new HashMap<>();
				if (licenseVehicleTravel.getStatus() == 1) {// 行驶证
					travelInfo.put("userName", licenseVehicleTravel.getOwnerName());
					travelInfo.put("plateNo", licenseVehicleTravel.getPlateNo().substring(0, 1)+"****"+licenseVehicleTravel.getPlateNo().substring(licenseVehicleTravel.getPlateNo().length()-2));
					travelInfo.put("vin", licenseVehicleTravel.getVin().substring(0, 2)+""+licenseVehicleTravel.getVin().substring(licenseVehicleTravel.getVin().length()-4));
					travelInfo.put("status", "1");
				} else {
					travelInfo.put("userName", "***");
					travelInfo.put("plateNo", "*******");
					travelInfo.put("vin", "***********");
					travelInfo.put("status", "0");
				}
				travelInfoList.add(travelInfo);
			}
		}
		rtMap.put("travelInfoList", travelInfoList);
		return ResultUtils.rtSuccess(rtMap);
	}

}
