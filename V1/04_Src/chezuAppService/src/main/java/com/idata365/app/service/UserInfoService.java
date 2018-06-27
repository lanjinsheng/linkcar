package com.idata365.app.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.LicenseDriver;
import com.idata365.app.entity.LicenseVehicleTravel;
import com.idata365.app.entity.UserConfig;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.enums.AchieveEnum;
import com.idata365.app.mapper.LicenseDriverMapper;
import com.idata365.app.mapper.LicenseVehicleTravelMapper;
import com.idata365.app.mapper.UserConfigMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.service.common.AchieveCommService;

@Service
public class UserInfoService extends BaseService<UserInfoService> {
	private final static Logger LOG = LoggerFactory.getLogger(UserInfoService.class);
	@Autowired
	UserConfigMapper userConfigMapper;
	@Autowired
	UsersAccountMapper usersAccountMapper;
	@Autowired
	LicenseVehicleTravelMapper licenseVehicleTravelMapper;
	@Autowired
	LicenseDriverMapper licenseDriverMapper;
	@Autowired
	AchieveCommService achieveCommService;

	public UserInfoService() {
	}

	public List<LicenseVehicleTravel> getLicenseVehicleTravel(Long userId) {
		return licenseVehicleTravelMapper.findLicenseVehicleTravelByUserId(userId);
	}

	public List<LicenseVehicleTravel> findLicenseVehicleTravels() {
		return licenseVehicleTravelMapper.findLicenseVehicleTravels();
	}

	public LicenseDriver getLicenseDriver(Long userId) {
		return licenseDriverMapper.findLicenseDriverByUserId(userId);
	}

	public List<LicenseDriver> getUserLicenseDrivers() {
		return licenseDriverMapper.getUserLicenseDrivers();
	}

	public void updateNickName(Long userId, String nickName) {
		UsersAccount account = new UsersAccount();
		account.setId(userId);
		account.setNickName(nickName);
		usersAccountMapper.updateNickName(account);

	}

	public void updateEnableStranger(Long userId, int enableStranger) {
		UsersAccount account = new UsersAccount();
		account.setId(userId);
		account.setEnableStranger(enableStranger);
		usersAccountMapper.updateEnableStranger(account);

	}

	public void updateImgUrl(String key, Long userId) {
		UsersAccount account = new UsersAccount();
		account.setImgUrl(key);
		account.setId(userId);
		usersAccountMapper.updateImgUrl(account);

	}

	/**
	 * 
	 * @Title: insertImgDriver1
	 * @Description: TODO(驾驶证)
	 * @param @param
	 *            licenseDriver 参数
	 * @return void 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public void insertImgDriver1(Map<String, Object> licenseDriver) {

		licenseDriverMapper.insertImgDriverFrontImg(licenseDriver);

	}

	/**
	 * 
	 * @Title: insertImgDriver2
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            licenseDriver 参数
	 * @return void 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public void insertImgDriver2(Map<String, Object> licenseDriver) {

		licenseDriverMapper.insertImgDriverBackImg(licenseDriver);

	}

	/**
	 * 
	 * @Title: modifydrivingLicense
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            licenseDriver 参数
	 * @return void 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public void modifydrivingLicense(Map<String, Object> licenseDriver) {

		licenseDriverMapper.modifyImgDriverFront(licenseDriver);
		// 判断licenseVehicleTravel 是否 isTravelEdit=0,是则进行证件成就增加
		if (isVehicleTravelOK(Long.valueOf(licenseDriver.get("userId").toString()))) {
			achieveCommService.addAchieve(Long.valueOf(licenseDriver.get("userId").toString()), 0d,
					AchieveEnum.AddGrabTimes);
		}
	}

	/**
	 * 
	 * @Title: isVehicleTravelOK
	 * @Description: TODO(是否完成行驶证上传)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public boolean isVehicleTravelOK(Long userId) {
		List<LicenseVehicleTravel> travels = licenseVehicleTravelMapper.findLicenseVehicleTravelByUserId(userId);
		if(travels!=null&&travels.size()!=0) {
			for (LicenseVehicleTravel travel : travels) {
				if (travel.getIsTravelEdit() == 0) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * 
	 * @Title: isDrivingOK
	 * @Description: TODO(是否完成驾驶证上传)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public boolean isDrivingOK(Long userId) {
		LicenseDriver dirver = licenseDriverMapper.findLicenseDriverByUserId(userId);
		if (dirver != null && dirver.getIsDrivingEdit() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: insertImgVehicle1
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            licenseDriver 参数
	 * @return void 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public void insertImgVehicle1(Map<String, Object> vehicleDriver) {

		licenseVehicleTravelMapper.insertImgVehicleFrontImg(vehicleDriver);

	}

	/**
	 * 
	 * @Title: insertImgVehicle2
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            licenseDriver 参数
	 * @return void 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public void insertImgVehicle2(Map<String, Object> vehicleDriver) {

		licenseVehicleTravelMapper.insertImgVehicleBackImg(vehicleDriver);

	}

	/**
	 * 
	 * @Title: modifyVehicleLicense
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            licenseDriver 参数
	 * @return void 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public void modifyVehicleLicense(Map<String, Object> vehicleDriver) {

		licenseVehicleTravelMapper.modifyImgVehicleFront(vehicleDriver);
		// 判断licenseDriver是否 isDrivingEdit=0,是则进行证件成就增加
		if (isDrivingOK(Long.valueOf(vehicleDriver.get("userId").toString()))) {
			achieveCommService.addAchieve(Long.valueOf(vehicleDriver.get("userId").toString()), 0d,
					AchieveEnum.AddGrabTimes);
		}
	}

	public void updatePhone(String phone, Long userId) {
		UsersAccount account = new UsersAccount();
		account.setPhone(phone);
		account.setId(userId);
		usersAccountMapper.updatePhone(account);

	}
/**
 * 
 * @param userId
 * @return
 */
	public List<Map<String,Object>> getFamiliesByUserId(Long userId){
		return usersAccountMapper.findUserFamilies(userId);
	}
	
	public boolean validPwd(String pwd, Long userId) {
		UsersAccount account = new UsersAccount();
		account.setPwd(pwd);
		account.setId(userId);
		UsersAccount dbAccount = usersAccountMapper.findAccountByIdAndPwd(account);
		if (dbAccount == null) {
			return false;
		}
		return true;

	}

	public int getEnableStranger(Long userId) {
		UsersAccount account = usersAccountMapper.findAccountById(userId);
		return account.getEnableStranger();
	}

	public void updatePwd(String orgPwd, String newPwd, Long userId) {
		Map<String, Object> account = new HashMap<String, Object>();
		account.put("orgPwd", orgPwd);
		account.put("userId", userId);
		account.put("newPwd", newPwd);
		usersAccountMapper.updatePwdByUserIdAndOldPwd(account);

	}

	/**
	 * 
	 * @Title: updateUserConfig
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            gpsHidden
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public boolean updateUserConfig(String gpsHidden, long userId) {
		UserConfig userConfig = new UserConfig();
		userConfig.setIsHidden(Integer.valueOf(gpsHidden));
		userConfig.setUserId(userId);
		userConfigMapper.updateUserConfig(userConfig);
		return true;
	}

	/**
	 * 
	 * @Title: getUserConfig
	 * @Description: TODO(获取用户配置)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return UserConfig 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public UserConfig getUserConfig(long userId) {
		UserConfig uc = userConfigMapper.getUserConfigById(userId);
		if (uc == null) {
			uc = new UserConfig();
			uc.setIsHidden(1);
		} else {

		}
		return uc;
	}

	public UsersAccount getUsersAccount(Long userId) {
		return usersAccountMapper.findAccountById(userId);
	}
	public List<Map<String,Object>> getFamilyUsers(Long familyId,String baseImgUrl) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("familyId", familyId);
		map.put("imgUrl", baseImgUrl);
		return usersAccountMapper.findFamlilyUsers(map);
	}
	public int verifyLicenseDriver(Long userId, String operatingUser) {
		return licenseDriverMapper.verifyLicenseDriver(userId,operatingUser);
	}
	public int verifyLicenseVehicleTravel(String plateNo, String operatingUser) {
		return licenseVehicleTravelMapper.verifyLicenseVehicleTravel(plateNo,operatingUser);
	}
}
