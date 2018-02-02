package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.FamilyDriveDayStatBean;
import com.idata365.app.entity.FamilyMemberBean;
import com.idata365.app.entity.FamilyScoreBean;
import com.idata365.app.entity.GameHistoryBean;
import com.idata365.app.entity.ScoreByDayBean;
import com.idata365.app.entity.ScoreDetailBean;
import com.idata365.app.entity.ScoreFamilyDetailBean;
import com.idata365.app.entity.ScoreFamilyInfoBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.ScoreFamilyOrderBean;
import com.idata365.app.entity.ScoreMemberInfoBean;
import com.idata365.app.entity.ScoreUserBean;
import com.idata365.app.entity.ScoreUserHistoryBean;
import com.idata365.app.entity.ScoreUserHistoryParamBean;
import com.idata365.app.entity.UserFamilyRoleLogBean;
import com.idata365.app.entity.UserTravelHistoryBean;
import com.idata365.app.entity.UserTravelHistoryDetailBean;
import com.idata365.app.entity.UsersAccountBean;
import com.idata365.app.entity.YesterdayScoreBean;

public interface ScoreMapper
{
	public ScoreFamilyInfoBean queryFamilyByUserId(ScoreFamilyInfoParamBean bean);
	
	public List<Long> queryFamilyIds(ScoreFamilyInfoParamBean bean);
	
	public ScoreFamilyInfoBean queryFamilyByFamilyId(ScoreFamilyInfoParamBean bean);
	
	public List<ScoreFamilyOrderBean> queryFamilyOrderInfo(ScoreFamilyInfoParamBean bean);
	
	public ScoreFamilyDetailBean queryFamilyDetail(ScoreFamilyInfoParamBean bean);
	
	public List<String> queryFamilyRecords(ScoreFamilyInfoParamBean bean);
	
	public List<FamilyScoreBean> queryOrderRecords(ScoreFamilyInfoParamBean bean);
	
	public List<ScoreMemberInfoBean> queryMemberByFamilyId(ScoreFamilyInfoParamBean bean);
	
	public List<ScoreUserHistoryBean> queryHistoryOrder(ScoreUserHistoryParamBean bean);
	
	public Integer queryHistoryRole(ScoreUserHistoryParamBean bean);
	
	public int queryCurrentRole(ScoreUserHistoryParamBean bean);
	
	public List<ScoreByDayBean> queryScoreByDay(ScoreUserHistoryParamBean bean);
	
	public List<YesterdayScoreBean> queryMembersByFamily(ScoreFamilyInfoParamBean bean);
	
	public Integer queryFamilyRoleId(ScoreFamilyInfoParamBean bean);
	
	public List<Integer> queryFamilyRoleIdList(ScoreFamilyInfoParamBean bean);
	
	public Double queryUserScore(ScoreFamilyInfoParamBean bean);
	
	public FamilyDriveDayStatBean queryFamilyDriveStat(ScoreFamilyInfoParamBean bean);
	
	public List<UserTravelHistoryBean> queryTravels(ScoreFamilyInfoParamBean bean);
	
	public UserFamilyRoleLogBean queryTravelBetweenTime(ScoreFamilyInfoParamBean bean);
	
	public UserTravelHistoryDetailBean queryTravelDetail(ScoreFamilyInfoParamBean bean);
	
	public List<ScoreDetailBean> queryScoreDetail(ScoreFamilyInfoParamBean bean);
	
	public List<GameHistoryBean> queryFamilyOrderByMonth(ScoreFamilyInfoParamBean bean);
	
	public List<FamilyScoreBean> queryStartEndDay(ScoreFamilyInfoParamBean bean);
	
	public UsersAccountBean queryUserInfo(UsersAccountBean bean);
	
	public double statMileage(ScoreFamilyInfoParamBean bean);
	
	public double statTime(ScoreFamilyInfoParamBean bean);
	
	public int updateUseHoldNum(ScoreFamilyInfoParamBean bean);
	
	public int updateUseZhitiao(ScoreFamilyInfoParamBean bean);
}
