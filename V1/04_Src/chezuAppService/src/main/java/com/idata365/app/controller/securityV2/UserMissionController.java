package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.serviceV2.UserMissionService;
import com.idata365.app.util.ResultUtils;

@RestController
public class UserMissionController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(UserMissionController.class);
	@Autowired
	public UserMissionService userMissionService;

	/**
	 * 
	 * @Title: missionList
	 * @Description: TODO(个人任务列表)
	 * @param @return
	 *            参数
	 * @return <Map<String,Object>> 返回类型
	 * @throws @author
	 *             LiXing
	 */

	@RequestMapping("/missionList")
	public Map<String, Object> missionList(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		int missionType = Integer.valueOf(requestBodyParams.get("missionType").toString());
		LOG.info("userId=================" + userId);
		LOG.info("missionType=================" + missionType);
		List<Map<String, Object>> rtList = userMissionService.userMissionList(userId, missionType);
		return ResultUtils.rtSuccess(rtList);
	}
	
	/**
	 * 
	 * @Title: getMissionPrize
	 * @Description: TODO(领取任务奖励)
	 * @param @return
	 *            参数
	 * @return <Map<String,Object>> 返回类型
	 * @throws @author
	 *             LiXing
	 */

	@RequestMapping("/getMissionPrize")
	public Map<String, Object> getMissionPrize(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		long userId = this.getUserId();
		int missionId = Integer.valueOf(requestBodyParams.get("missionId").toString());
		int powerPrize = Integer.valueOf(requestBodyParams.get("powerPrize").toString());
		LOG.info("userId=================" + userId);
		LOG.info("missionId=================" + missionId);
		LOG.info("powerPrize=================" + powerPrize);
		try {
			userMissionService.getMissionPrize(userId, missionId,powerPrize);
			rtMap.put("receveStatus", "1");
		} catch (Exception e) {
			rtMap.put("receveStatus", "0");
		}
		return ResultUtils.rtSuccess(rtMap);
	}
}
