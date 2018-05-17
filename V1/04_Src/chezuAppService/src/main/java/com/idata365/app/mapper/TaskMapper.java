package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.DriverVehicleResultBean;
import com.idata365.app.entity.FamilyChallengeLogBean;
import com.idata365.app.entity.FamilyChallengeLogParamBean;
import com.idata365.app.entity.FamilyInfoBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyRelationParamBean;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.ParkStationParamBean;
import com.idata365.app.entity.UserFamilyRelationBean;
import com.idata365.app.entity.UserFamilyRoleLogBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.UserScoreDayParamBean;

public interface TaskMapper
{
	public int updateUserScoreDayByUserId(@Param("userId") Long userId);
	public List<FamilyRelationBean> queryFamilyRelations(FamilyRelationBean bean);
	
	public int countByFamily(FamilyRelationBean bean);
	
	public void delStations(List<Long> paramList);
	
	public void saveStations(List<ParkStationParamBean> paramList);
	
	public List<UserFamilyRelationBean> queryUserFamilyRelation();
	
	public UserFamilyRoleLogBean queryFamilyRoleLog(UserFamilyRoleLogParamBean bean);
	
	public List<Integer> queryRoleExceptDay(UserFamilyRoleLogParamBean bean);
	
	public void updateUserRole(UserFamilyRoleLogParamBean bean);
	
	public void saveUserFamilyRole(UserFamilyRoleLogParamBean bean);
	
	public void saveOrUpdateUserScoreDay(UserScoreDayParamBean bean);
	
	public List<FamilyChallengeLogBean> queryChallengeLog(FamilyChallengeLogParamBean bean);

	public List<FamilyInfoBean> queryFamilyByType(FamilyParamBean bean);
	
	public void saveFamilyRelation(FamilyRelationParamBean bean);
	
	public void delFamilyRelations(FamilyRelationParamBean bean);

	public List<Long> queryUserIds();
	
	public void saveOrUpdate(LotteryBean bean);
	
	public void resetMaZhaCount(LotteryBean bean);
	
	public List<Long> queryUserIdsByRole(UserFamilyRoleLogBean bean);
	
	public int countsRole(UserFamilyRoleLogBean bean);
	
	public Integer countLottery(LotteryBean bean);
	
	public DriverVehicleResultBean queryDriveEditStatus(UserFamilyRoleLogParamBean bean);
	
	public DriverVehicleResultBean queryTravelEditStatus(UserFamilyRoleLogParamBean bean);
}
