package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.ScoreService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.ValidTools;

@RestController
public class UserHomeController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(UserHomeController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	private FamilyService familyService;

	public UserHomeController() {
		System.out.println("UserHomeController");
	}

	/**
	 * 
	 * @Title: queryUserHome
	 * @Description: TODO(查看用户车库)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping("/queryUserHome")
	public Map<String, Object> queryUserHome(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("userId")))
			return ResultUtils.rtFailParam(null);
		long ownerId = this.getUserId();
		long userId = Long.valueOf(requestBodyParams.get("userId").toString());
//		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		LOG.info("ownerId========================="+ownerId);
		LOG.info("userId========================="+userId);
//		LOG.info("familyId========================="+familyId);
		UsersAccount account = userInfoService.getUsersAccount(userId);

		double score = scoreService.getHighScore(String.valueOf(userId));
		String powerNum = chezuAssetService.getUsersAssetMap(String.valueOf(userId), "").get(userId);
		Map<String, String> familyInfo = familyService.queryFamilyByUserId(userId);
		rtMap.put("nickName", account.getNickName());
		rtMap.put("score", String.valueOf(score));
		rtMap.put("imgUrl",this.getImgBasePath() + account.getImgUrl());
		rtMap.put("powerNum", powerNum);
		rtMap.put("createFamilyInfo", familyInfo.get("createFamilyInfo"));
		rtMap.put("joinFamilyInfo", familyInfo.get("joinFamilyInfo"));
		if (ownerId == userId) {
			rtMap.put("title", "我的车库");
		} else {
			rtMap.put("title", account.getNickName()+"的车库");
		}

		return ResultUtils.rtSuccess(rtMap);
	}

}