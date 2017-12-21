package com.idata365.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.ScoreFamilyDetailBean;
import com.idata365.app.entity.ScoreFamilyDetailResultBean;
import com.idata365.app.entity.ScoreFamilyInfoAllBean;
import com.idata365.app.entity.ScoreFamilyInfoBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.ScoreFamilyOrderBean;
import com.idata365.app.entity.ScoreMemberInfoBean;
import com.idata365.app.entity.ScoreMemberInfoResultBean;
import com.idata365.app.entity.ScoreUserHistoryBean;
import com.idata365.app.entity.ScoreUserHistoryParamBean;
import com.idata365.app.entity.ScoreUserHistoryResultAllBean;
import com.idata365.app.entity.ScoreUserHistoryResultBean;
import com.idata365.app.mapper.ScoreMapper;
import com.idata365.app.util.AdBeanUtils;

@Service
public class ScoreService extends BaseService<ScoreService>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(ScoreService.class);
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	/**
	 * 发起家族、参与家族查询
	 * @param bean
	 * @return
	 */
	public ScoreFamilyInfoAllBean queryFamily(ScoreFamilyInfoParamBean bean)
	{
		ScoreFamilyInfoBean oriFamilyBean = this.scoreMapper.queryFamilyByUserId(bean);
		ScoreFamilyInfoParamBean tempParamBean = new ScoreFamilyInfoParamBean();
		tempParamBean.setFamilyId(oriFamilyBean.getFamilyId());
		ScoreFamilyInfoBean joinFamilyBean = this.scoreMapper.queryFamilyByFamilyId(tempParamBean);
		
		ScoreFamilyInfoAllBean resultBean = new ScoreFamilyInfoAllBean();
		resultBean.setOriFamily(oriFamilyBean);
		resultBean.setJoinFamily(joinFamilyBean);
		return resultBean;
	}
	
	/**
	 * 今日上榜家族
	 * @return
	 */
	public List<ScoreFamilyOrderBean> queryFamilyOrderInfo()
	{
		return this.scoreMapper.queryFamilyOrderInfo();
	}
	
	/**
	 * 查看家族信息
	 * @param bean
	 * @return
	 */
	public ScoreFamilyDetailResultBean queryFamilyDetail(ScoreFamilyInfoParamBean bean)
	{
		ScoreFamilyDetailBean tempBean = this.scoreMapper.queryFamilyDetail(bean);
		List<String> recordsList = this.scoreMapper.queryFamilyRecords(bean);
		ScoreFamilyDetailResultBean resultBean = new ScoreFamilyDetailResultBean();
		
		AdBeanUtils.copyOtherPropToStr(resultBean, tempBean);
		resultBean.setFamilys(recordsList);
		return resultBean;
	}
	
	/**
	 * 查看家族成员
	 * @param bean
	 * @return
	 */
	public List<ScoreMemberInfoResultBean> listFamilyMember(ScoreFamilyInfoParamBean bean)
	{
		List<ScoreMemberInfoBean> tempList = this.scoreMapper.queryMemberByFamilyId(bean);
		List<ScoreMemberInfoResultBean> resultList = new ArrayList<>();
		
		for (ScoreMemberInfoBean tempBean : tempList)
		{
			ScoreMemberInfoResultBean tempResultBean = new ScoreMemberInfoResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			resultList.add(tempResultBean);
		}
		
		return resultList;
	}
	
	/**
	 * 历史得分（显示指定用户的）
	 * @param bean
	 * @return
	 */
	public ScoreUserHistoryResultAllBean listHistoryOrder(ScoreUserHistoryParamBean bean)
	{
		List<ScoreUserHistoryBean> tempList = this.scoreMapper.queryHistoryOrder(bean);
		
		List<ScoreUserHistoryResultBean> resultList = new ArrayList<>();
		
		for (ScoreUserHistoryBean tempBean : tempList)
		{
			ScoreUserHistoryResultBean tempResultBean = new ScoreUserHistoryResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			resultList.add(tempResultBean);
		}
		
		int start = bean.getStart();
		int newStart = start + tempList.size();
		
		ScoreUserHistoryResultAllBean resultBean = new ScoreUserHistoryResultAllBean();
		resultBean.setHistoryScores(resultList);
		resultBean.setStart(String.valueOf(newStart));
		
		return resultBean;
	}
}
