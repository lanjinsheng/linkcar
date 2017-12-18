package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.FamilyInviteBean;
import com.idata365.app.entity.FamilyInviteParamBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRandBean;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.entity.UsersAccountParamBean;

public interface FamilyMapper
{
	public FamilyResultBean queryFamilyByUserId(FamilyParamBean bean);
	
	public long queryCreateUserId(FamilyParamBean bean);
	
	public void deleteUserFamilyRelation(FamilyParamBean bean);
	
	public void save(FamilyParamBean bean);

	public void updateUserStraner(UsersAccountParamBean bean);
	
	public List<FamilyRandBean> queryFamilys();
	
	public FamilyRandBean queryFamilyByCode(FamilyParamBean bean);
	
	public long saveFamilyInvite(FamilyInviteParamBean bean);

	public void saveUserFamily(FamilyParamBean bean);
	
	public List<FamilyInviteBean> queryApplyInfo(FamilyInviteParamBean bean);
	
	public int countByName(FamilyParamBean bean);
	
	public void updateInviteCode(FamilyParamBean bean);
	
	public Long queryFamilyIdByUserId(FamilyParamBean bean);
	
	public int countUsersByFamilyId(FamilyParamBean bean);
	
	public int countInviteByUserId(FamilyParamBean bean);
}
