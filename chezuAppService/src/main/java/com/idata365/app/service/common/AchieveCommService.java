package com.idata365.app.service.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.mapper.UserAchieveMapper;

/**
 * 个人成就公用方法
 * 
 * @className:com.idata365.app.service.common.AcchieveCommService
 * @description:TODO
 * @date:2018年1月22日 下午3:16:55
 * @author:CaiFengYao
 */
@Service
public class AchieveCommService
{
	@Autowired
	private UserAchieveMapper userAchieveMapper;
	Map<String, Object> map = new HashMap<String, Object>();

	/**
	 * 1.分享达人
	 */
	public void addShareTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 1);
		updateAchieveTimes(map);
	}

	/**
	 * 2.基友遍天下
	 */
	public void addGayTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 2);
		updateAchieveTimes(map);

	}

	/**
	 * 3.神行太保
	 */
	public void addGodTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 3);
		updateAchieveTimes(map);
	}

	/**
	 * 4.车位终结者
	 */
	public void addCarEndTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 4);
		updateAchieveTimes(map);
	}

	/**
	 * 5.终结好司机
	 */
	public void addBestDriverTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 5);
		updateAchieveTimes(map);
	}

	/**
	 * 6.道具收集者
	 */
	public void addCollectTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 6);
		updateAchieveTimes(map);
	}

	/**
	 * 7.黄金家族
	 */
	public void addGoldFamilyTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 7);
		updateAchieveTimes(map);
	}

	/**
	 * 8.夺宝名人
	 */
	public void addGrabTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 8);
		updateAchieveTimes(map);
	}

	/**
	 * 9.贴条小能手
	 */
	public void addStupidTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 9);
		updateAchieveTimes(map);
	}

	// 更新成就操作
	public void updateAchieveTimes(Map<String, Object> map)
	{
		// 查看用户某项成就最新记录id
		Integer achieveRecordId = userAchieveMapper.queryLatelyAchieveId(map);
		if (achieveRecordId != null)
		{
			userAchieveMapper.updateAchieveTimesById(achieveRecordId);
		}
	}
}
