package com.idata365.app.controller.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.idata365.app.entity.FamilyInviteParamBean;
import com.idata365.app.entity.FamilyInviteResultBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRandResultBean;
import com.idata365.app.entity.InviteInfoResultBean;
import com.idata365.app.service.FamilyService;
import com.idata365.app.util.ResultUtils;

@RestController
public class FamilyController extends BaseController
{
	@Autowired
	private FamilyService familyService;
	
	@RequestMapping("/family/removeMember")
	public Map<String, Object> removeMember(@RequestBody FamilyParamBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		this.familyService.removeMember(reqBean);
		return ResultUtils.rtSuccess(null);
	}
	
	/**
	 * 查看审核成员
	 * @param bean
	 * @return
	 */
	@RequestMapping("/family/getApplyInfo")
	public Map<String, Object> getApplyInfo(@RequestBody FamilyInviteParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<FamilyInviteResultBean> resutList = this.familyService.getApplyInfo(bean);
		return ResultUtils.rtSuccess(resutList);
	}
	
	/**
	 * 审核成员通过
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/permitApply")
	public Map<String, Object> permitApply(@RequestBody FamilyParamBean reqBean)
	{
		int msgType = this.familyService.permitApply(reqBean);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("msgType", String.valueOf(msgType));
		List<Map<String, String>> resultList = new ArrayList<>();
		resultList.add(resultMap);
		return ResultUtils.rtSuccess(resultList);
	}
	
	@RequestMapping("/family/quitFromFamily")
	public Map<String, Object> quitFromFamily(@RequestBody FamilyParamBean reqBean)
	{
		this.familyService.quitFromFamily(reqBean);
		return ResultUtils.rtSuccess(null);
	}
	
	@RequestMapping("/family/listRecruFamily")
	public Map<String, Object> listRecruFamily()
	{
		Long userId = super.getUserId();
		List<FamilyRandResultBean> resutList = this.familyService.listRecruFamily(userId);
		return ResultUtils.rtSuccess(resutList);
	}
	
	@RequestMapping("/family/findFamilyByCode")
	public Map<String, Object> findFamilyByCode(@RequestBody FamilyParamBean reqBean)
	{
		Long userId = super.getUserId();
		FamilyRandResultBean resultBean = this.familyService.findFamilyByCode(reqBean, userId);
		return ResultUtils.rtSuccess(resultBean);
	}
	
	@RequestMapping("/family/applyByFamily")
	public Map<String, Object> applyByFamily(@RequestBody FamilyInviteParamBean bean)
	{
		this.familyService.applyByFamily(bean);
		return ResultUtils.rtSuccess(null);
	}
	
	/**
	 * 检查家族名称
	 * @param bean
	 * @return
	 */
	@RequestMapping("/family/checkFamilyName")
	public Map<String, Object> checkFamilyName(@RequestBody FamilyParamBean bean)
	{
		String duplicateFlag = this.familyService.checkFamilyName(bean);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("duplicateFlag", duplicateFlag);
		List<Map<String, String>> resultList = new ArrayList<>();
		resultList.add(resultMap);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 创建家族
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/createFamily")
	public Map<String, Object> createFamily(@RequestBody FamilyParamBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		long familyId = this.familyService.createFamily(reqBean);
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("familyId", familyId);
		List<Map<String, Long>> resultList = new ArrayList<>();
		resultList.add(resultMap);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 生成邀请码
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/generateInviteInfo")
	public Map<String, Object> generateInviteInfo(@RequestBody FamilyParamBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		InviteInfoResultBean resultBean = this.familyService.generateInviteInfo(reqBean);
		List<InviteInfoResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		return ResultUtils.rtSuccess(resultList);
	}
	
}
