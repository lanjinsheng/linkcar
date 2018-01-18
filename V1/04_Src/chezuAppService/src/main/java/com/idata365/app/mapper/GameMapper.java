package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.FamilyChallengeLogBean;
import com.idata365.app.entity.FamilyChallengeLogParamBean;
import com.idata365.app.entity.FamilyRelationParamBean;
import com.idata365.app.entity.GameFamilyParamBean;
import com.idata365.app.entity.RoleCountBean;
import com.idata365.app.entity.StationBean;
import com.idata365.app.entity.TravelHistoryParamBean;
import com.idata365.app.entity.UserFamilyRelationBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.UserScoreDayParamBean;
import com.idata365.app.entity.ViolationStatBean;
import com.idata365.app.entity.ViolationStatParamBean;

public interface GameMapper
{
	public ViolationStatBean queryFamilyDriveDayStat(ViolationStatParamBean bean);
	
	public void saveFamilyRelation(FamilyRelationParamBean bean);
	
	public List<StationBean> queryStations(FamilyRelationParamBean bean);
	
	public int updateToStopParkStation(GameFamilyParamBean bean);
	
	public int updateToHoldParkStation(GameFamilyParamBean bean);
	
	public List<Long> queryFamilyOtherUserId(GameFamilyParamBean bean);
	
	public int updateTravelHistoryHidden(TravelHistoryParamBean bean);
	
	public int countTomorrowRole(UserFamilyRoleLogParamBean bean);
	
	public int updateUserFamilyRole(UserFamilyRoleLogParamBean bean);
	
	public void saveUserFamilyRole(UserFamilyRoleLogParamBean bean);
	
	public List<Integer> queryRoleByDay(UserFamilyRoleLogParamBean bean);
	
	public List<UserFamilyRelationBean> queryUserFamilyRelation();
	
	public void saveUserScoreDay(UserScoreDayParamBean bean);
	
	public int countBeChallenge(FamilyChallengeLogParamBean bean);
	
	public int countChallenge(FamilyChallengeLogParamBean bean);
	
	public void saveChallengeLog(FamilyChallengeLogParamBean bean);
	
	public int countChallengeByFamilyId(FamilyChallengeLogParamBean bean);
	
	public List<FamilyChallengeLogBean> queryChallengeLog(FamilyChallengeLogParamBean bean);
	
	public List<RoleCountBean> countRoleByRole(UserFamilyRoleLogParamBean bean);
	
	public FamilyChallengeLogBean queryChallengeType(FamilyChallengeLogParamBean bean);
}
