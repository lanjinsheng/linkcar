package com.idata365.app.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.FamilyInvite;

public interface FamilyInviteMapper {

	void insertFamilyInviteHadReg(FamilyInvite familyInvite);
	void insertFamilyInviteNoReg(FamilyInvite familyInvite);
	
	void updateFamilyInviteWhenReg(FamilyInvite familyInvite);
	
	List<FamilyInvite> getFamilyInviteByPhone(@Param("phone") String phone);
	
	FamilyInvite getLatestInvite(FamilyInvite familyInvite);
}
