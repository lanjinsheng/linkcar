package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.ScoreFamilyDetailBean;
import com.idata365.app.entity.ScoreFamilyInfoBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.ScoreFamilyOrderBean;
import com.idata365.app.entity.ScoreMemberInfoBean;

public interface ScoreMapper
{
	public ScoreFamilyInfoBean queryFamilyByUserId(ScoreFamilyInfoParamBean bean);
	
	public ScoreFamilyInfoBean queryFamilyByFamilyId(ScoreFamilyInfoParamBean bean);
	
	public List<ScoreFamilyOrderBean> queryFamilyOrderInfo();
	
	public ScoreFamilyDetailBean queryFamilyDetail(ScoreFamilyInfoParamBean bean);
	
	public List<String> queryFamilyRecords(ScoreFamilyInfoParamBean bean);
	
	public List<ScoreMemberInfoBean> queryMemberByFamilyId(ScoreFamilyInfoParamBean bean);
}
