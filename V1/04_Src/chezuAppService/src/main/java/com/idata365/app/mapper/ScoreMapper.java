package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.ScoreByDayBean;
import com.idata365.app.entity.ScoreFamilyDetailBean;
import com.idata365.app.entity.ScoreFamilyInfoBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.ScoreFamilyOrderBean;
import com.idata365.app.entity.ScoreMemberInfoBean;
import com.idata365.app.entity.ScoreUserBean;
import com.idata365.app.entity.ScoreUserHistoryBean;
import com.idata365.app.entity.ScoreUserHistoryParamBean;

public interface ScoreMapper
{
	public ScoreFamilyInfoBean queryFamilyByUserId(ScoreFamilyInfoParamBean bean);
	
	public List<Long> queryFamilyIds(ScoreFamilyInfoParamBean bean);
	
	public ScoreFamilyInfoBean queryFamilyByFamilyId(ScoreFamilyInfoParamBean bean);
	
	public List<ScoreFamilyOrderBean> queryFamilyOrderInfo();
	
	public ScoreFamilyDetailBean queryFamilyDetail(ScoreFamilyInfoParamBean bean);
	
	public List<String> queryFamilyRecords(ScoreFamilyInfoParamBean bean);
	
	public List<ScoreMemberInfoBean> queryMemberByFamilyId(ScoreFamilyInfoParamBean bean);
	
	public List<ScoreUserHistoryBean> queryHistoryOrder(ScoreUserHistoryParamBean bean);
	
	public List<ScoreByDayBean> queryScoreByDay(ScoreUserHistoryParamBean bean);
	
	public List<ScoreUserBean> queryUserDayScoreByFamily(ScoreFamilyInfoParamBean bean);
}
