package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.FamilyChallengeLogBean;
import com.idata365.app.entity.FamilyChallengeLogParamBean;
import com.idata365.app.entity.FamilyInfoBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyRelationParamBean;
import com.idata365.app.entity.ParkStationParamBean;
import com.idata365.app.entity.UserFamilyRelationBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.UserScoreDayParamBean;

public interface TaskMapper
{
	public List<FamilyRelationBean> queryFamilyRelations(FamilyRelationBean bean);
	
	public int countByFamily(FamilyRelationBean bean);
	
	public void delStations(List<Long> paramList);
	
	public void saveStations(List<ParkStationParamBean> paramList);
	
	public List<UserFamilyRelationBean> queryUserFamilyRelation();
	
	public int countTomorrowRole(UserFamilyRoleLogParamBean bean);
	
	public void saveUserFamilyRole(UserFamilyRoleLogParamBean bean);
	
	public void saveUserScoreDay(UserScoreDayParamBean bean);
	
	public List<FamilyChallengeLogBean> queryChallengeLog(FamilyChallengeLogParamBean bean);

	public List<FamilyInfoBean> queryFamilyByType(FamilyParamBean bean);
	
	public void saveFamilyRelation(FamilyRelationParamBean bean);

}
