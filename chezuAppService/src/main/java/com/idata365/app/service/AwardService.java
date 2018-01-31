package com.idata365.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.AwardBean;
import com.idata365.app.entity.AwardResultBean;
import com.idata365.app.entity.QuestionBean;
import com.idata365.app.entity.QuestionParamBean;
import com.idata365.app.mapper.AwardMapper;
import com.idata365.app.util.AdBeanUtils;

@Service
public class AwardService extends BaseService<AwardService>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AwardService.class);
	
	@Autowired
	private AwardMapper awardMapper;
	
	@Transactional
	public AwardResultBean showAwardUser()
	{
		AwardResultBean resultBean = new AwardResultBean();
		
		AwardBean awardBean = this.awardMapper.queryAwardInfo();
		
		QuestionParamBean questionParamBean = new QuestionParamBean();
		questionParamBean.setAwardInfoId(awardBean.getId());
		
		AdBeanUtils.copyNotNullProperties(resultBean, awardBean);
		int age = awardBean.getAge();
		if (-1 == age)
		{
			resultBean.setAge("无可奉告");
		}
		
		List<QuestionBean> questions = this.awardMapper.queryQuestions(questionParamBean);
		resultBean.setQuestionList(questions);
		
		return resultBean;
	}
}
