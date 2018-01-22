package com.idata365.app.service.common;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.constant.LotteryLogConstant;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryLogInfoParamBean;
import com.idata365.app.mapper.LotteryMapper;
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

	@Autowired
	private LotteryMapper lotteryMapper;
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
		updateAchieveWhenUploadLicence(map, userId);
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

	/**
	 * 更新成就操作（当上传驾照时）
	 * 
	 * @Description:TODO 奖励没有在库里有体现，是写死在程序里的
	 */
	public void updateAchieveWhenUploadLicence(Map<String, Object> map, long userId)
	{
		// 查看用户某项成就最新记录id
		Integer achieveRecordId = userAchieveMapper.queryLatelyAchieveId(map);
		if (achieveRecordId != null)
		{
			// 更新成就解锁标识
			userAchieveMapper.updateAchieveWhenUploadLicence(achieveRecordId);
			// 发放奖励
			saveLotteInfo(userId, 1, 1);
			// 发放奖励
			saveLotteInfo(userId, 2, 1);
			// 发放奖励
			saveLotteInfo(userId, 3, 1);
			// 发放奖励
			saveLotteInfo(userId, 4, 1);
			// 发放奖励
			saveLotteInfo(userId, 5, 1);
			// 发放奖励
			saveLotteInfo(userId, 6, 1);
		}
	}

	// 保存道具的发放
	void saveLotteInfo(long userId, int awardId, int awardCount)
	{
		LotteryBean tempParamBean = new LotteryBean();
		tempParamBean.setUserId(userId);
		tempParamBean.setAwardId(awardId);
		tempParamBean.setAwardCount(awardCount);
		this.lotteryMapper.increLotteryCount(tempParamBean);
		LotteryLogInfoParamBean lotteryLogParamBean = new LotteryLogInfoParamBean();
		lotteryLogParamBean.setUserId(userId);
		lotteryLogParamBean.setAwardId(awardId);
		lotteryLogParamBean.setAwardCount(awardCount);
		lotteryLogParamBean.setType(LotteryLogConstant.USER_ACHIEVE_LOG);
		lotteryLogParamBean.setTimestamp(getCurrentTs());
		this.lotteryMapper.saveLotteryLog(lotteryLogParamBean);
	}

	private String getCurrentTs()
	{
		Calendar cal = Calendar.getInstance();
		return DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss");
	}
}
