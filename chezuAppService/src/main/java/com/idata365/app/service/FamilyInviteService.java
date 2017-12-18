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

import com.idata365.app.entity.FamilyInvite;
import com.idata365.app.mapper.FamilyInviteMapper;

 

@Service
public class FamilyInviteService extends BaseService<FamilyInviteService>{
	private final static Logger LOG = LoggerFactory.getLogger(FamilyInviteService.class);
	@Autowired
	 FamilyInviteMapper familyInviteMapper;
	public FamilyInviteService() {
	}
	 
	 public void  insertInviteFamily(FamilyInvite familyInvite) {
		 if(familyInvite.getMemberUserId()>0) {
			 familyInviteMapper.insertFamilyInviteHadReg(familyInvite);
		 }else {
			 familyInviteMapper.insertFamilyInviteNoReg(familyInvite);
		 }
	 }
	 
}
