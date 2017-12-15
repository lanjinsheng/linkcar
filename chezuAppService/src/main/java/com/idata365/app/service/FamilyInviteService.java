package com.idata365.app.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.mapper.LicenseDriverMapper;
import com.idata365.app.mapper.LicenseVehicleTravelMapper;
import com.idata365.app.mapper.UsersAccountMapper;

 

@Service
public class FamilyInviteService extends BaseService<FamilyInviteService>{
	private final static Logger LOG = LoggerFactory.getLogger(FamilyInviteService.class);
	@Autowired
	 UsersAccountMapper usersAccountMapper;
	@Autowired
	LicenseVehicleTravelMapper licenseVehicleTravelMapper;
	@Autowired
	LicenseDriverMapper licenseDriverMapper;
	public FamilyInviteService() {
	}
	/**
	 * 
	    * @Title: createInviteLink
	    * @Description: TODO(创建邀请链接)
	    * @param @param familyId
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public String createInviteLink(Long familyId) {
		return null;
	}
	
	
	 private boolean hadReg(String phone) {
		return false;
	 }
	 
	 
	 private String getInviteCode(Long familyId) {
		 return "";
	 }
	 
	 public void  insertInviteFamily(Long familyId,String phone) {
		 
	 }
	 
}
