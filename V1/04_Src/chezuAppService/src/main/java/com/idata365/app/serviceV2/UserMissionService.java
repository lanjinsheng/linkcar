package com.idata365.app.serviceV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.DicUserMission;
import com.idata365.app.entity.UserMissionLogs;
import com.idata365.app.mapper.DicUserMissionMapper;
import com.idata365.app.mapper.UserMissionLogsMapper;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.BaseService;
import com.idata365.app.util.SignUtils;

@Service
@Transactional
public class UserMissionService extends BaseService<UserMissionService> {
	protected static final Logger LOG = LoggerFactory.getLogger(UserMissionService.class);
	
	@Autowired
	private DicUserMissionMapper dicUserMissionMapper;
	@Autowired
	private UserMissionLogsMapper userMissionLogsMapper;
	@Autowired
	private ChezuAssetService chezuAssetService;

	// 从字典表查询所有任务
	public List<DicUserMission> getAllDicUserMission() {
		return dicUserMissionMapper.getAllDicUserMission();
	}

	// 根据userId查询任务
	public List<UserMissionLogs> getLogsByUserId(long userId) {
		return userMissionLogsMapper.getLogsByUserId(userId);
	}

	//初始化用户任务logs
	public void initLogsToUser(List<DicUserMission> missions, long userId) {
		userMissionLogsMapper.initLogsToUser(missions,userId);
	}
	
	// 个人任务列表
	public List<Map<String, Object>> userMissionList(long userId, int missionType) {
		List<Map<String, Object>> logs = userMissionLogsMapper.getLogsByUserIdAndType(userId, missionType);
		for (Map<String, Object> map : logs) {
			int missionId = Integer.valueOf(map.get("missionId").toString());
			if (missionId == 5) {
				// 处理连续登录任务
				String missionCondition = map.get("missionCondition").toString();
				int powerPrize = Integer.valueOf(map.get("powerPrize").toString());
				int finishCount = Integer.valueOf(map.get("finishCount").toString());
				missionCondition.replaceAll("s", finishCount + "");
				powerPrize = (powerPrize * finishCount) >= 150 ? 150 : (powerPrize * finishCount);
				map.put("missionCondition", missionCondition);
				map.put("powerPrize", powerPrize);
			}
			
			//Object --> String
			Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> next = iterator.next();
				Object value = next.getValue();
				value = String.valueOf(value);
			}
		}
		
		//排序--- status 1-->2-->3
		Collections.sort(logs, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return Double.valueOf(o1.get("status").toString()).compareTo(Double.valueOf(o2.get("status").toString()));
			}
		});
		
		return logs;
	}

	public void getMissionPrize(long userId, int missionId, int powerPrize) {
		int a = userMissionLogsMapper.updateLogsStatus(userId,missionId);
		
		if (a <= 0) {
			LOG.error("修改Logs状态失败：missionId==" + missionId + "==" + userId + "==" + userId);
			throw new RuntimeException("系统异常领取失败");
		}
		
		// 资产操作
		String paramSign = userId +String.valueOf(powerPrize);
		String sign = SignUtils.encryptHMAC(paramSign);
		boolean b = chezuAssetService.getMissionPrize(userId,powerPrize,missionId,sign);
		if (b == false) {
			throw new RuntimeException("系统异常领取失败");
		}
	}
}
