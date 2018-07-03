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

import com.idata365.app.entity.IDCard;
import com.idata365.app.entity.LicenseVehicleTravel;
import com.idata365.app.entity.UserConfig;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.mapper.IDCardMapper;
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
	IDCardMapper iDCardMapper;
	@Autowired
	AchieveCommService achieveCommService;

	public UserInfoService() {
	}
	
	public Map<String, String> isAuthenticated(long userId){
		Map<String, String> rtMap = new HashMap<String, String>();
		rtMap.put("IdCardIsOK", "0");
		rtMap.put("VehicleTravelIsOK", "0");
		IDCard idCard = iDCardMapper.findIDCardByUserId(userId);
		List<LicenseVehicleTravel> licenseVehicleTravels = licenseVehicleTravelMapper.findLicenseVehicleTravelByUserId(userId);
		if (idCard != null && licenseVehicleTravels != null && licenseVehicleTravels.size()!=0) {
			// 证件上传即认证
			if(idCard.getStatus()==1) {
				rtMap.put("IdCardIsOK", "1");
			}
			for (LicenseVehicleTravel l : licenseVehicleTravels) {
				if(l.getStatus()==1) {
					rtMap.put("VehicleTravelIsOK", "1");
					break;
				}
			}
		}
		return rtMap;
	}
	
	public List<LicenseVehicleTravel> getLicenseVehicleTravelByUserId(Long userId) {
		return licenseVehicleTravelMapper.findLicenseVehicleTravelByUserId(userId);
	}

	public List<LicenseVehicleTravel> findLicenseVehicleTravels(Map<String, Object> map) {
		return licenseVehicleTravelMapper.findLicenseVehicleTravels();
	}
	
	public IDCard getIDCard(Long userId) {
		return iDCardMapper.findIDCardByUserId(userId);
	}
	
	public IDCard findIDCardByUserId(Long userId) {
		return iDCardMapper.findIDCardByUserId(userId);
	}

	public List<IDCard> getUserIDCards() {
		return iDCardMapper.getUserIDCards();
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
	 * @Title: insertImgIDCard1
	 * @Description: TODO(驾驶证)
	 * @param @param
	 *            licenseDriver 参数
	 * @return void 返回类型
	 * @throws @author
	 *             lcc
	 */
	public void insertImgIDCardFrontImg(Map<String, Object> IDCard) {
		iDCardMapper.insertImgIDCardFrontImg(IDCard);
	}

	/**
	 * 
	 * @Title: insertImgIDCard2
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            licenseDriver 参数
	 * @return void 返回类型
	 * @throws @author
	 *             lcc
	 */
	public void insertImgIDCardBackImg(Map<String, Object> IDCard) {

		iDCardMapper.insertImgIDCardBackImg(IDCard);

	}

	/**
	 * 
	 * @Title: modifyIDCard
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            licenseDriver 参数
	 * @return void 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public int modifyIDCard(Map<String, Object> iDCard) {

		return iDCardMapper.modifyIDCard(iDCard);
	}


	/**
	 * 
	 * @Title: isIDCardOK
	 * @Description: TODO(是否完成身份证上传,修改)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             Lcc
	 */
	public boolean isIDCardOK(Long userId) {
		IDCard idCard = iDCardMapper.findIDCardByUserId(userId);
		if (idCard != null) {
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

	public void updatePhone(String phone, Long userId) {
		UsersAccount account = new UsersAccount();
		account.setPhone(phone);
		account.setId(userId);
		usersAccountMapper.updatePhone(account);

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
		userConfig.setUserConfigValue(Integer.valueOf(gpsHidden));
		userConfig.setType(1);
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
	 *             lcc
	 */
	public int queryIsGPSHidden(long userId) {
		int i = 1;
		if(userConfigMapper.queryIsGPSHidden(userId)!=null) {
			i = userConfigMapper.queryIsGPSHidden(userId);
		}
		return i;
	}
	
	public int queryIsCanInvite(long userId) {
		int i = 1;
		if(userConfigMapper.queryIsCanInvite(userId)!=null) {
			i = userConfigMapper.queryIsCanInvite(userId);
		}
		return i;
	}
	
	public int queryIsCanJoinMe(long userId) {
		int i = 1;
		if(userConfigMapper.queryIsCanJoinMe(userId)!=null) {
			i = userConfigMapper.queryIsCanJoinMe(userId);
		}
		return i;
	}

	public UsersAccount getUsersAccount(Long userId) {
		return usersAccountMapper.findAccountById(userId);
	}
	
	public int verifyLicenseVehicleTravel(Map<String, Object> xsz) {
		return licenseVehicleTravelMapper.verifyLicenseVehicleTravel(xsz);
	}

}
