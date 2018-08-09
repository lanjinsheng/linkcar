package com.idata365.app.controller.securityV2;

import java.util.ArrayList;
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
import com.idata365.app.entity.v2.MissionResultBean;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.serviceV2.LookAdService;
import com.idata365.app.serviceV2.UserMissionService;
import com.idata365.app.util.ResultUtils;

@RestController
public class UserMissionController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(UserMissionController.class);
	@Autowired
	public UserMissionService userMissionService;
	@Autowired
	public ChezuAssetService chezuAssetService;
	@Autowired
	public LookAdService lookAdService;

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
		
//		初始化任务系统
//		userMissionService.initMissionOfUserId(userId);
		
		// 预查询
		userMissionService.insertOrUpdateLogs(userId, missionType);
		
		// 查询所有类型任务完成情况
		Map<String, String> rtMap = userMissionService.queryMissionStatus(userId);
		// 返回任务列表
		List<MissionResultBean> rtList = userMissionService.userMissionList(userId, missionType);

		Map<String, Object> rtDate = new HashMap<>();
		rtDate.put("rtMap", rtMap);
		rtDate.put("rtList", rtList);
		
		
		// 任务进度宝箱
		Map<String, Object> box = new HashMap<>();
		if (missionType == 1) {
			List<Map<String, String>> boxList = new ArrayList<Map<String, String>>();
			int count = chezuAssetService.queryReceiveDayMissionBox(userId, "");
			int[] arr = { 1, 2, 3, 4 };
			int[] sureNum = { 2, 4, 6, 7 };
			long[] power = { 20, 40, 60, 100 };
			for (int i = 0; i < arr.length; i++) {
				Map<String, String> b = new HashMap<>();
				b.put("boxId", String.valueOf(arr[i]));
				b.put("isOpen", count > i ? "1" : "0");
				b.put("sureNum", String.valueOf(sureNum[i]));
				b.put("hintInfo", "完成"+sureNum[i]+"个每日任务领取,打开后获得"+power[i]+"点动力");
				boxList.add(b);
			}
			box.put("boxList", boxList);
			box.put("totalNum", "7");// 共需要完成任务数
			box.put("currentNum", rtMap.get("dayMissionHad"));
		} else if (missionType == 3) {
			List<Map<String, String>> boxList = new ArrayList<Map<String, String>>();
			int count = chezuAssetService.queryReceiveActMissionBox(userId, "");
			int[] arr = { 1, 2, 3, 4 };
			int[] sureNum = { 2, 6, 10, 14 };
			long[] power = { 50, 80, 100, 120 };
			for (int i = 0; i < arr.length; i++) {
				Map<String, String> b = new HashMap<>();
				b.put("boxId", String.valueOf(arr[i]));
				b.put("isOpen", count > i ? "1" : "0");
				b.put("sureNum", String.valueOf(sureNum[i]));
				b.put("hintInfo", "完成"+sureNum[i]+"次活动任务领取,打开后获得"+power[i]+"点动力");
				boxList.add(b);
			}
			box.put("boxList", boxList);
			box.put("totalNum", "14");// 共需要完成任务数
			box.put("currentNum", String.valueOf(lookAdService.getTodayCountAllType(userId)));
		}
		
		rtDate.put("boxInfo", box);
		return ResultUtils.rtSuccess(rtDate);
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
		LOG.info("userId=================" + userId);
		LOG.info("missionId=================" + missionId);
		try {
			rtMap = userMissionService.getMissionPrize(userId, missionId);
			rtMap.put("receveStatus", "1");
		} catch (Exception e) {
			rtMap.put("receveStatus", "0");
		}
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 
	 * @Title: receiveMissionBox
	 * @Description: TODO(领取任务宝箱)
	 * @param @return
	 *            参数
	 * @return <Map<String,Object>> 返回类型
	 * @throws @author
	 *             LiXing
	 */

	@RequestMapping("/receiveMissionBox")
	public Map<String, Object> receiveMissionBox(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		Map<String, String> prizeInfo = new HashMap<String, String>();
		long userId = this.getUserId();
		int boxId = Integer.valueOf(requestBodyParams.get("boxId").toString());
		int missionType = Integer.valueOf(requestBodyParams.get("missionType").toString());
		LOG.info("userId=================" + userId);
		LOG.info("boxId=================" + boxId);
		LOG.info("missionType=================" + missionType);
		long powerNum = 0L;
		if(missionType==1) {
			long[] power = { 20, 40, 60, 100 };
			powerNum = power[boxId - 1];
			chezuAssetService.receiveDayMissionBox(userId, powerNum, "");
		}else if (missionType==3) {
			long[] power = { 50, 80, 100, 120 };
			powerNum = power[boxId - 1];
			chezuAssetService.receiveActMissionBox(userId, powerNum, "");
		}
		prizeInfo.put("powerNum", String.valueOf(powerNum));
		rtMap.put("prizeInfo", prizeInfo);
		return ResultUtils.rtSuccess(rtMap);
	}
	
}
