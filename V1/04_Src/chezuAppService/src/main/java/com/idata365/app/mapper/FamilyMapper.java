package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.FamilyInfoScoreBean;
import com.idata365.app.entity.FamilyInviteBean;
import com.idata365.app.entity.FamilyInviteParamBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRandBean;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.entity.UsersAccountParamBean;

public interface FamilyMapper
{
	public FamilyResultBean queryFamilyByUserId(FamilyParamBean bean);
	
	public FamilyResultBean queryFamilyById(FamilyParamBean bean);
	
	public List<FamilyRelationBean> queryFamilyIdByCompetitorId(FamilyRelationBean bean);
	
	public Long queryCreateUserId(FamilyParamBean bean);
	
	public void deleteUserFamilyRelation(FamilyParamBean bean);
	
	public void save(FamilyParamBean bean);

	public void updateUserStraner(UsersAccountParamBean bean);
	
	public List<FamilyRandBean> queryFamilys(FamilyParamBean bean);
	
	public int countStranger();
	
	public FamilyRandBean queryFamilyByCode(FamilyParamBean bean);
	
	public long saveFamilyInvite(FamilyInviteParamBean bean);

	public void saveUserFamily(FamilyParamBean bean);
	
	public List<FamilyInviteBean> queryApplyInfo(FamilyInviteParamBean bean);
	
	public int countByName(FamilyParamBean bean);
	
	public void updateInviteCode(FamilyParamBean bean);
	
	public int countByCode(FamilyParamBean bean);
	
	public Long queryFamilyIdByUserId(FamilyParamBean bean);
	
	public int countUsersByFamilyId(FamilyParamBean bean);
	
	public int countInviteByUserId(FamilyParamBean bean);
	
	public void delInviteByUserId(FamilyParamBean bean);
	
	public FamilyInfoScoreBean queryOwnFamily(FamilyParamBean bean);
	
	public FamilyInfoScoreBean queryJoinFamily(FamilyParamBean bean);
	
	public List<Long> queryUserIdsWithSpeedPenal(FamilyParamBean bean);
	
	public void updateSpeedPenalTimes(FamilyParamBean bean);
	
	public List<Long> queryUserIdsWithBrakePenal(FamilyParamBean bean);
	
	public void updateBrakePenalTimes(FamilyParamBean bean);
	
	public List<Long> queryUserIdsWithTurnPenal(FamilyParamBean bean);
	
	public void updateTurnPenalTimes(FamilyParamBean bean);
	
	public List<Long> queryUserIdsWithOverspeedPenal(FamilyParamBean bean);
	
	public void updateOverspeedPenalTimes(FamilyParamBean bean);
	
	public List<Long> queryUserIdsWithNightDrivePenal(FamilyParamBean bean);
	
	public void updateNightDrivePenalTimes(FamilyParamBean bean);
	
	public List<Long> queryUserIdsWithTiredDrivePenal(FamilyParamBean bean);
	
	public void updateTiredDrivePenalTimes(FamilyParamBean bean);
	
	public List<Long> queryUserIdsWithIllegalStopPenal(FamilyParamBean bean);
	
	public void updateIllegalStopPenalTimes(FamilyParamBean bean);
}
