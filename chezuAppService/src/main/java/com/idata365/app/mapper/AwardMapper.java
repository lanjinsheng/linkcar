package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AwardBean;
import com.idata365.app.entity.QuestionBean;
import com.idata365.app.entity.QuestionParamBean;

public interface AwardMapper
{
	public AwardBean queryAwardInfo();
	
	public AwardBean queryAwardInfoById(@Param("id") Long msgId);
	
	public List<QuestionBean> queryQuestions(QuestionParamBean bean);
}
