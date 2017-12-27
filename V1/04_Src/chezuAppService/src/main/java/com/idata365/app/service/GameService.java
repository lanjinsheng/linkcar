package com.idata365.app.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.ViolationStatBean;
import com.idata365.app.entity.ViolationStatParamBean;
import com.idata365.app.entity.ViolationStatResultAllBean;
import com.idata365.app.entity.ViolationStatResultBean;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.GameMapper;
import com.idata365.app.util.AdBeanUtils;

@Service
public class GameService extends BaseService<GameService>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
	
	@Autowired
	private GameMapper gameMapper;
	
	@Autowired
	private FamilyMapper familyMapper;
	
	/**
	 * 违规情况
	 * @param bean
	 * @return
	 */
	public ViolationStatResultAllBean violationList(ViolationStatParamBean bean)
	{
		ViolationStatResultAllBean resultAllBean = new ViolationStatResultAllBean();
		
		Date todayDate = Calendar.getInstance().getTime();
		String todayStr = DateFormatUtils.format(todayDate, DateConstant.DAY_PATTERN_DELIMIT);
		bean.setDaystamp(todayStr);
		
		ViolationStatBean myBean = this.gameMapper.queryFamilyDriveDayStat(bean);
		ViolationStatResultBean myResultBean = new ViolationStatResultBean();
		AdBeanUtils.copyOtherPropToStr(myResultBean, myBean);
		resultAllBean.setMyBean(myResultBean);
		
		long myFamilyId = bean.getFamilyId();
		FamilyRelationBean familyRelationParamBean = new FamilyRelationBean();
		familyRelationParamBean.setFamilyId(myFamilyId);
		List<FamilyRelationBean> relationList = this.familyMapper.queryFamilyIdByCompetitorId(familyRelationParamBean);
		if (CollectionUtils.isNotEmpty(relationList))
		{
			FamilyRelationBean tempRelationBean = relationList.get(0);
			long familyId1 = tempRelationBean.getFamilyId1();
			if (myFamilyId != familyId1)
			{
				bean.setFamilyId(familyId1);
			}
			else
			{
				long familyId2 = tempRelationBean.getFamilyId2();
				bean.setFamilyId(familyId2);
			}
			
			ViolationStatBean competitorBean = this.gameMapper.queryFamilyDriveDayStat(bean);
			ViolationStatResultBean competitorResultBean = new ViolationStatResultBean();
			AdBeanUtils.copyOtherPropToStr(competitorResultBean, competitorBean);
			resultAllBean.setCompetitorBean(competitorResultBean);
		}
		
		return resultAllBean;
	}
}
