package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.DicUserMission;
import com.idata365.app.service.DicService;
import com.idata365.app.serviceV2.UserMissionService;
import com.idata365.app.util.ResultUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class NotifyController extends BaseController {
	private static long lastNotifyTime=0;
	private static String notifyText="";
	protected static final Logger LOG = LoggerFactory.getLogger(NotifyController.class);
	@Autowired
	DicService dicService;
	@Autowired
	UserMissionService userMissionService;
	@RequestMapping("/indexNotice")
	public Map<String, Object> indexNotice(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
			long now=System.currentTimeMillis();
			long userId = this.getUserId();
			LOG.info("userId==============" + userId);
			if((now-lastNotifyTime)>(3600*1000)){//一个小时重新去库获取
				notifyText=dicService.getNotify();
				lastNotifyTime=now;
			}
			Map<String,String> rtMap=new HashMap<String,String> ();
			rtMap.put("notice", notifyText);
			
			// 初始化任务系统
			// 更新每日任务
			boolean flag = userMissionService.updateDayMission(userId);
			LOG.info("更新每日任务状态=================" + flag);
			// 初始化
			int count = userMissionService.queryHadRecord(userId);
			if (count == 0) {
				LOG.info("初始化任务log============userId=================" + userId);
				List<DicUserMission> missions = userMissionService.getAllDicUserMission();
				userMissionService.initLogsToUser(missions, userId);
				userMissionService.updateCountOfId5(userId);
			}
			// 预查询
			userMissionService.insertOrUpdateLogs(userId, 1);
			userMissionService.insertOrUpdateLogs(userId, 2);
		    return ResultUtils.rtSuccess(rtMap);
	}
    
 
}
