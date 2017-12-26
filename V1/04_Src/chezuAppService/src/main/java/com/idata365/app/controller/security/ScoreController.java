package com.idata365.app.controller.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.idata365.app.entity.CompetitorResultBean;
import com.idata365.app.entity.FamilyMemberAllResultBean;
import com.idata365.app.entity.ScoreByDayResultBean;
import com.idata365.app.entity.ScoreFamilyDetailResultBean;
import com.idata365.app.entity.ScoreFamilyInfoAllBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.ScoreFamilyOrderBean;
import com.idata365.app.entity.ScoreMemberInfoResultBean;
import com.idata365.app.entity.ScoreUserHistoryParamBean;
import com.idata365.app.entity.ScoreUserHistoryResultAllBean;
import com.idata365.app.entity.SimulationScoreResultBean;
import com.idata365.app.entity.YesterdayContributionResultBean;
import com.idata365.app.entity.YesterdayScoreResultBean;
import com.idata365.app.service.ScoreService;
import com.idata365.app.util.ResultUtils;

@RestController
public class ScoreController extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(ScoreController.class);
	
	@Autowired
	private ScoreService scoreService;
	
	/**
	 * 发起家族、参与家族查询
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/queryFamily")
	public Map<String, Object> queryFamily(@RequestBody ScoreFamilyInfoParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		ScoreFamilyInfoAllBean resultBean = this.scoreService.queryFamily(bean);
		List<ScoreFamilyInfoAllBean> resultList = new ArrayList<ScoreFamilyInfoAllBean>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 今日上榜家族
	 * @return
	 */
	@RequestMapping("/score/listFamily")
	public Map<String, Object> listFamily()
	{
		List<ScoreFamilyOrderBean> resultList = this.scoreService.queryFamilyOrderInfo();
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 查看家族信息
	 * @return
	 */
	@RequestMapping("/score/queryFamilyDetail")
	public Map<String, Object> queryFamilyDetail(@RequestBody ScoreFamilyInfoParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		ScoreFamilyDetailResultBean resultBean = this.scoreService.queryFamilyDetail(bean);
		List<ScoreFamilyDetailResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 查看家族成员
	 * @return
	 */
	@RequestMapping("/score/listFamilyMember")
	public Map<String, Object> listFamilyMember(@RequestBody ScoreFamilyInfoParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<ScoreMemberInfoResultBean> resultList = this.scoreService.listFamilyMember(bean);
		
		String imgBasePath = super.getImgBasePath();
		for (ScoreMemberInfoResultBean tempBean : resultList)
		{
			String imgUrl = tempBean.getImgUrl();
			tempBean.setImgUrl(imgBasePath + imgUrl);
		}
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 历史得分（显示指定用户的）
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/listHistoryOrder")
	public Map<String, Object> listHistoryOrder(@RequestBody ScoreUserHistoryParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		ScoreUserHistoryResultAllBean resultBean = this.scoreService.listHistoryOrder(bean);
		return ResultUtils.rtSuccess(resultBean);
	}
	
	/**
	 * 历史驾驶得分
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/getScoreByDay")
	public Map<String, Object> getScoreByDay(@RequestBody ScoreUserHistoryParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		List<ScoreByDayResultBean> resultList = this.scoreService.getScoreByDay(bean);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 昨日得分
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/findYesterdayScore")
	public Map<String, Object> findYesterdayScore(@RequestBody ScoreFamilyInfoParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		List<YesterdayScoreResultBean> resultList = this.scoreService.findYesterdayFamilyScore(bean);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 族内贡献榜(昨日得分)
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/familyContribution")
	public Map<String, Object> familyContribution(@RequestBody ScoreFamilyInfoParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		List<YesterdayContributionResultBean> resultList = this.scoreService.familyContribution(bean);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 获得家族及成员昨日理论得分
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/generateYesterdayFamilyScore")
	public Map<String, Object> generateYesterdayFamilyScore(@RequestBody ScoreFamilyInfoParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		FamilyMemberAllResultBean resultBean = this.scoreService.generateYesterdayFamilyScore(bean);
		return ResultUtils.rtSuccess(resultBean);
	}
	
	/**
	 * 昨日赛果
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/showGameResult")
	public Map<String, Object> showGameResult(@RequestBody ScoreFamilyInfoParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		CompetitorResultBean resultBean = this.scoreService.showGameResult(bean);
		List<CompetitorResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 获得昨日理论得分(用户所有角色)
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/generateYesterdaySimulationScore")
	public Map<String, Object> generateYesterdaySimulationScore(@RequestBody ScoreFamilyInfoParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<SimulationScoreResultBean> resultList = this.scoreService.generateYesterdaySimulationScore(bean);
		
		return ResultUtils.rtSuccess(resultList);
	}
}
