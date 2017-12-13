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
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.LicenseDriver;
import com.idata365.app.entity.LicenseVehicleTravel;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.mapper.LicenseDriverMapper;
import com.idata365.app.mapper.LicenseVehicleTravelMapper;
import com.idata365.app.mapper.UsersAccountMapper;

 

@Service
public class UserInfoService extends BaseService<UserInfoService>{
	private final static Logger LOG = LoggerFactory.getLogger(UserInfoService.class);
	@Autowired
	 UsersAccountMapper usersAccountMapper;
	@Autowired
	LicenseVehicleTravelMapper licenseVehicleTravelMapper;
	@Autowired
	LicenseDriverMapper licenseDriverMapper;
	public UserInfoService() {
	}
	 
	
	public LicenseVehicleTravel getLicenseVehicleTravel(Long userId) {
		return licenseVehicleTravelMapper.findLicenseVehicleTravelByUserId(userId);
	}
	public LicenseDriver getLicenseDriver(Long userId) {
		return licenseDriverMapper.findLicenseDriverByUserId(userId);
	}
	
	public void updateNickName(Long userId,String nickName) {
		UsersAccount account=new UsersAccount();
		account.setId(userId);
		account.setNickName(nickName);
		usersAccountMapper.updateNickName(account);
 
	}
	public void updateImgUrl(String key,Long userId) {
		UsersAccount account=new UsersAccount();
		account.setImgUrl(key);
		account.setId(userId);
		usersAccountMapper.updateImgUrl(account);
 
	}
	/**
	 * 
	    * @Title: insertImgDriver1
	    * @Description: TODO(驾驶证)
	    * @param @param licenseDriver    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public void insertImgDriver1(Map<String,Object> licenseDriver) {
		
		licenseDriverMapper.insertImgDriverFrontImg(licenseDriver);
 
	} 
	/**
	 * 
	    * @Title: insertImgDriver2
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param licenseDriver    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public void insertImgDriver2(Map<String,Object> licenseDriver) {
		
		licenseDriverMapper.insertImgDriverBackImg(licenseDriver);
 
	} 
	/**
	 * 
	    * @Title: modifydrivingLicense
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param licenseDriver    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public void modifydrivingLicense(Map<String,Object> licenseDriver) {
		
		licenseDriverMapper.modifyImgDriverFront(licenseDriver);
 
	} 
	
/**
 * 
    * @Title: insertImgVehicle1
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param @param licenseDriver    参数
    * @return void    返回类型
    * @throws
    * @author LanYeYe
 */
	public void insertImgVehicle1(Map<String,Object> vehicleDriver) {
		
		licenseVehicleTravelMapper.insertImgVehicleFrontImg(vehicleDriver);
 
	} 
    /**
     * 
        * @Title: insertImgVehicle2
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param @param licenseDriver    参数
        * @return void    返回类型
        * @throws
        * @author LanYeYe
     */
	public void insertImgVehicle2(Map<String,Object> vehicleDriver) {
		
		licenseVehicleTravelMapper.insertImgVehicleBackImg(vehicleDriver);
 
	} 
	/**
	 * 
	    * @Title: modifyVehicleLicense
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param licenseDriver    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public void modifyVehicleLicense(Map<String,Object> vehicleDriver) {
		
		licenseVehicleTravelMapper.modifyImgVehicleFront(vehicleDriver);
 
	} 
	
	
	public void updatePhone(String phone,Long userId) {
		UsersAccount account=new UsersAccount();
		account.setPhone(phone);
		account.setId(userId);
		usersAccountMapper.updateImgUrl(account);
 
	}
	
	public boolean validPwd(String pwd,Long userId) {
		UsersAccount account=new UsersAccount();
		account.setPwd(pwd);
		account.setId(userId);
		UsersAccount dbAccount=usersAccountMapper.findAccountByIdAndPwd(account);
		if(dbAccount==null) {
			return false;
		}
		return true;
 
	}
	
	
	public void updatePwd(String orgPwd,String newPwd,Long userId) {
		 Map<String,Object> account=new HashMap<String,Object>();
		 account.put("orgPwd", orgPwd);
		 account.put("userId", userId);
		 account.put("newPwd", newPwd);
		usersAccountMapper.updatePwdByUserIdAndOldPwd(account);
 
	}
}
