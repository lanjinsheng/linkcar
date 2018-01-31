package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.AwardBean;
import com.idata365.app.entity.QuestionBean;
import com.idata365.app.entity.QuestionParamBean;

public interface AwardMapper
{
	public AwardBean queryAwardInfo();
	
	public List<QuestionBean> queryQuestions(QuestionParamBean bean);
}
