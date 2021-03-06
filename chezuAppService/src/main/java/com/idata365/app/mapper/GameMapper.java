package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.DriverVehicleResultBean;
import com.idata365.app.entity.FamilyChallengeLogBean;
import com.idata365.app.entity.FamilyChallengeLogParamBean;
import com.idata365.app.entity.FamilyDriveDayStat;
import com.idata365.app.entity.FamilyRelationParamBean;
import com.idata365.app.entity.GameFamilyParamBean;
import com.idata365.app.entity.ReviewBean;
import com.idata365.app.entity.ReviewParamBean;
import com.idata365.app.entity.RoleCountBean;
import com.idata365.app.entity.StationBean;
import com.idata365.app.entity.TravelHistoryParamBean;
import com.idata365.app.entity.UserFamilyRelationBean;
import com.idata365.app.entity.UserFamilyRoleLogBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.UserScoreDayParamBean;
import com.idata365.app.entity.ViolationStatBean;
import com.idata365.app.entity.ViolationStatParamBean;

public interface GameMapper
{
	public ViolationStatBean queryFamilyDriveDayStat(ViolationStatParamBean bean);
	
	public FamilyDriveDayStat queryFamilyScore(@Param("familyId")Long familyId,@Param("daystamp") String daystamp);

	public void saveFamilyRelation(FamilyRelationParamBean bean);
	
	public List<StationBean> queryStations(FamilyRelationParamBean bean);
	
	public int updateToStopParkStation(GameFamilyParamBean bean);
	
	public int updateToHoldParkStation(GameFamilyParamBean bean);
	
	public List<Long> queryFamilyOtherUserId(GameFamilyParamBean bean);
	
	public int updateTravelHistoryHidden(TravelHistoryParamBean bean);
	
	public int countTomorrowRole(UserFamilyRoleLogParamBean bean);
	
	public int countExceptTomorrowRole(UserFamilyRoleLogParamBean bean);
	
	public int updateUserFamilyRole(UserFamilyRoleLogParamBean bean);
	
	public void saveUserFamilyRole(UserFamilyRoleLogParamBean bean);
	
	public List<Integer> queryRoleByDay(UserFamilyRoleLogParamBean bean);
	
	public List<UserFamilyRoleLogBean> queryFamilyRoleLog(UserFamilyRoleLogParamBean bean);
	
	public List<Double> queryScore(UserFamilyRoleLogParamBean bean);
	
	public List<UserFamilyRelationBean> queryUserFamilyRelation();
	
//	public void saveUserScoreDay(UserScoreDayParamBean bean);
	
	public int countBeChallenge(FamilyChallengeLogParamBean bean);
	
	public int countChallenge(FamilyChallengeLogParamBean bean);
	
	public void saveChallengeLog(FamilyChallengeLogParamBean bean);
	
	public int countChallengeByFamilyId(FamilyChallengeLogParamBean bean);
	
	public Map<String,Object> getChallengeRelation(Map<String,Object> map);
	
	public List<FamilyChallengeLogBean> queryChallengeLog(FamilyChallengeLogParamBean bean);
	
	public List<RoleCountBean> countRoleByRole(UserFamilyRoleLogParamBean bean);
	
	public FamilyChallengeLogBean queryChallengeType(FamilyChallengeLogParamBean bean);
	
	public DriverVehicleResultBean queryDriveEditStatus(UserFamilyRoleLogParamBean bean);
	
	public DriverVehicleResultBean queryTravelEditStatus(UserFamilyRoleLogParamBean bean);
	
	public void saveReview(ReviewParamBean bean);
	
	public ReviewBean queryReview(ReviewParamBean bean);

	public Integer queryMembersNumByTime(@Param("familyId")long familyId,@Param("daystamp") String daystamp);
}
