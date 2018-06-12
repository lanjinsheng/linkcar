package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.FamilyDriveDayStat;
import com.idata365.app.entity.FamilyHistoryParamBean;
import com.idata365.app.entity.FamilyInfoBean;
import com.idata365.app.entity.FamilyInfoScoreBean;
import com.idata365.app.entity.FamilyInviteBean;
import com.idata365.app.entity.FamilyInviteParamBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRandBean;
import com.idata365.app.entity.FamilyRelation;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.entity.UserFamilyRoleLogBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.UsersAccountParamBean;

public interface FamilyMapper {
	public FamilyResultBean queryFamilyByUserId(FamilyParamBean bean);

	public FamilyResultBean queryFamilyById(FamilyParamBean bean);

	public List<FamilyRelationBean> queryFamilyIdByCompetitorId(FamilyRelationBean bean);

	public List<FamilyRelation> queryFightRecordByFamilyId(@Param("familyId")long familyId,@Param("recordId")long recordId);
	
	public List<FamilyRelation> queryFightRecordByFamilyIdFirst(@Param("familyId")long familyId);
	
	public List<Long> queryFamilyRelationIds(FamilyRelationBean bean);

	public Long queryCreateUserId(FamilyParamBean bean);

	public void deleteUserFamilyRelation(FamilyParamBean bean);

	public void updateFamilyRoleLog(FamilyParamBean bean);

	public int countByCreateUser(FamilyParamBean bean);

	public void save(FamilyParamBean bean);

	public void updateUserStraner(UsersAccountParamBean bean);

	public List<FamilyRandBean> queryFamilys(FamilyParamBean bean);

	public int countStranger(FamilyParamBean bean);

	public FamilyRandBean queryFamilyByCode(FamilyParamBean bean);
	
	public List<FamilyRandBean> queryFamilyByName(FamilyParamBean bean);

	public long saveFamilyInvite(FamilyInviteParamBean bean);

	public void saveUserFamily(FamilyParamBean bean);

	public List<FamilyInviteBean> queryApplyInfo(FamilyInviteParamBean bean);

	public void updateInviteStatus(FamilyParamBean bean);

	public int countByName(FamilyParamBean bean);

	public void updateInviteCode(FamilyParamBean bean);

	public int countByCode(FamilyParamBean bean);

	public List<Long> queryFamilyIdByUserId(FamilyParamBean bean);
	public List<Long> queryJoinFamilyIdByUserId(FamilyParamBean bean);

	public int countUsersByFamilyId(FamilyParamBean bean);

	public int countInviteByUserId(FamilyParamBean bean);

	public void delInviteByUserId(FamilyParamBean bean);

	public FamilyInfoScoreBean queryOwnFamily(FamilyParamBean bean);

	public Integer queryOwnFamilyOrderNo(FamilyParamBean bean);

	public FamilyInfoScoreBean queryJoinFamily(FamilyParamBean bean);

	public Integer queryJoinFamilyOrderNo(FamilyParamBean bean);

	public List<Map<String, Object>> queryAllFamilyOrderNo();
	
	public Map<String, Object> queryFamilyOrderNoByFamilyId(@Param("familyId")long familyId);
	
	public Map<String, Object> queryFamilyByFId(@Param("familyId")long familyId);
	
	int queryFamilyOrderByFId(@Param("familyId")long familyId);
	
	public Map<String, Object> getInfoByFamilyId(@Param("familyId")long familyId,@Param("daystamp") String daystamp);

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

	public FamilyInfoBean queryFamilyInfo(FamilyParamBean bean);

	public int countByType(FamilyParamBean bean);

	public List<FamilyInfoBean> queryFamilyByType(FamilyParamBean bean);

	public void saveFamilyHistory(FamilyHistoryParamBean bean);

	public int countUsers();

	public int countUnRead(FamilyParamBean bean);

	public int countUnReadChats(FamilyParamBean bean);

	public List<Map<String, Object>> getFamilyUsers(@Param("familyId") Long familyId);

	public List<Map<String, Object>> getFamilyByUserId(@Param("userId") Long userId);

	public int queryTaskFlag(FamilyParamBean bean);

	public List<Long> queryHabits(FamilyParamBean bean);

	public int countUnReceive(List<Long> paramList);

	public int updateTaskFlag(FamilyParamBean bean);

	public List<UserFamilyRoleLogBean> queryStartEnd(UserFamilyRoleLogParamBean bean);

	// 家族人数更新活跃度--begin
	void addFamilyMemberNum(@Param("familyId") Long familyId);

	void removeFamilyMemberNum(@Param("familyId") Long familyId);

//	void updateFamilyActiveLevel(@Param("familyId") Long familyId);

	// 家族人数更新活跃度--end
	// 挑选对战家族--begin
	int updateFamilyPkKeyGet(Map<String, Object> map);

	int updateFamilyPkSelfKey(Map<String, Object> map);

	int insertPkRelation(Map<String, Object> map);

	long getCompetitorFamilyId(Map<String, Object> pkKey);
	// 挑选对战家族--end

	List<Map<String, Object>> findUsersByFamilyId(@Param("familyId") Long familyId);

	// 初始化家族日分
	int insertFamilyDriveDayStat(FamilyDriveDayStat familyDriveDayStat);



}
