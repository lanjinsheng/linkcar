package com.idata365.app.service.common;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.constant.LotteryLogConstant;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryLogInfoParamBean;
import com.idata365.app.entity.UserAchieveBean;
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
	private static final Logger LOG = LoggerFactory.getLogger(AchieveCommService.class);

	@Autowired
	private UserAchieveMapper userAchieveMapper;

	@Autowired
	private LotteryMapper lotteryMapper;
	Map<String, Object> map = new HashMap<String, Object>();

	/************************************************* 针对具体用户的成就方法 *************************************************************/

	/**
	 * 1.分享达人
	 * 
	 * @Description:分享游戏链接次数
	 */
	public void addShareTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 1);
		updateAchieveTimes(map);
	}

	/**
	 * 2.基友遍天下
	 * 
	 * @Description:拉新
	 */
	public void addGayTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 2);
		updateAchieveTimes(map);

	}

	/**
	 * 3.神行太保
	 * 
	 * @Description:累计里程
	 */
	public void addGodTimes(long userId, double mileage)
	{
		map.put("userId", userId);
		map.put("achieveId", 3);
		updateAchieveNum(map, mileage);
	}

	/**
	 * 4.车位终结者
	 * 
	 * @Description:抢车位次数
	 */
	public void addCarEndTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 4);
		updateAchieveTimes(map);
	}

	/**
	 * 5.终结好司机
	 * 
	 * @Description:X天未发生违规行为
	 */
	public void addBestDriverTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 5);
		updateAchieveTimes(map);
	}

	/**
	 * 6.道具收集者
	 * 
	 * @Description:各种途径获得道具
	 */
	public void addCollectTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 6);
		updateAchieveTimes(map);
	}

	/****************************************** 坑爹模块start **************************************************/
	/**
	 * 7.黄金家族
	 * 
	 * @Description:TODO 所在家族连续X天位于黄金档内，当该成就完成时，该家族下的所有成员都加分
	 */
	public void addGoldFamilyTimes(long userId, long familyId)
	{
		map.put("userId", userId);
		map.put("achieveId", 7);
		// updateAchieveTimes(map);
		updateAchieveDaysByFamily();
	}

	/****************************************** 坑爹模块end **************************************************/
	/**
	 * 8.夺宝名人
	 * 
	 * @Description:上传驾驶证和行驶证
	 */
	public void addGrabTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 8);
		updateAchieveWhenUploadLicence(map, userId);
	}

	/**
	 * 9.贴条小能手
	 * 
	 * @Description:累计贴条次数
	 */
	public void addStupidTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", 9);
		updateAchieveTimes(map);
	}

	/**
	 * 更新成就
	 * 
	 * @Description:当用户点击某项成就时，先更新后查询。更新rule：成就达到解锁要求时
	 * 
	 */
	public void updateAchieveInfoBeforeQuery(int achieveId, long userId, Map<String, Object> map)
	{
		LOG.info("用户点击了个人成就，开始更新该项成就相关值==================================================");
		/**
		 * 查询用户可以解锁的成就记录
		 */
		UserAchieveBean bean = userAchieveMapper.queryUserCanDeblockAchieve(map);
		LOG.info("查询可以解锁的UserAchieveBean：>>>>>>>>>>>>>>>>>>>>>", bean);
		if (bean == null || bean.getType() == 3)// 当夺宝名人时，返回
		{
			LOG.info("无可更新成就，返回**********************");
			return;
		}
		else
		{
			// 奖励类型(1.道具奖励;2.个人评分奖励;3.综合大礼包奖励)
			if (bean.getType() == 1)
			{
				// 更新用户道具
				saveLotteInfo(userId, bean.getAwardId(), bean.getAwardNum());
			}
			else if (bean.getType() == 2)
			{
				// 更新用户当日个人评分
				updateUserPointTodayByAchieve(userId, bean.getAwardNum());
			}
			// 更新成就解锁标识
			userAchieveMapper.updateFlagToLock(bean.getId());
			// 更新下一个成就等级的数量
			if (bean.getLev() < bean.getMaxLev())
			{
				// 剩余成就值
				int remianNum = bean.getNowNum() - bean.getNum();
				LOG.info("将更新下一等级成就信息，更新的剩余值为：>>>>>>>>>>>>>>>>>>>>>", remianNum);
				map.put("lev", bean.getLev() + 1);
				map.put("nowNum", remianNum);
				LOG.info("updateNextLevAchieveValue的入参Map：>>>>>>>>>>>>>>>>>>>>>", map);
				userAchieveMapper.updateNextLevAchieveValue(map);
			}
		}
		LOG.info("更新成就结束==================================================");
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

	/**
	 * 更新用户当日个人评分
	 */
	void updateUserPointTodayByAchieve(long userId, int awardCount)
	{
		UserAchieveBean ubean = new UserAchieveBean();
		ubean.setDaystamp(getCurrentDayStr());
		ubean.setUserId(userId);
		ubean.setExtraPlusScore(Double.valueOf(String.valueOf(awardCount)));
		userAchieveMapper.updateUserPointsToday(ubean);
	}

	// 更新成就次数操作(1次)
	void updateAchieveTimes(Map<String, Object> map)
	{
		// 查看用户某项成就最新记录id
		UserAchieveBean achieve = userAchieveMapper.queryLatelyAchieveInfo(map);
		LOG.info("updateAchieveTimes==================================================", achieve);
		if (achieve != null && achieve.getId() != null)
		{
			userAchieveMapper.updateAchieveTimesById(achieve.getId());
			if (achieve.getNowNum() + 1 >= achieve.getNum())
			{
				// 更新成就解锁标识
				userAchieveMapper.updateFlagToLock(achieve.getId());
				// 更新下一个成就等级的数量
				if (achieve.getLev() < achieve.getMaxLev())
				{
					// 剩余成就值
					map.put("lev", achieve.getLev() + 1);
					map.put("nowNum", achieve.getNowNum() + 1 - achieve.getNum());
					LOG.info("解锁该项成就，并更新下一等级成就，参数为==================================================", map);
					userAchieveMapper.updateNextLevAchieveValue(map);
				}
			}
		}
	}

	// 更新该家族下所有成员当前天数
	void updateAchieveDaysByFamily()
	{

	}

	// 更新成就数量
	void updateAchieveNum(Map<String, Object> map, double num)
	{
		// 查看用户某项成就最新记录id
		UserAchieveBean achieve = userAchieveMapper.queryLatelyAchieveInfo(map);
		LOG.info("updateAchieveNum==================================================", achieve);
		if (achieve != null && achieve.getId() != null)
		{
			int sumNum = achieve.getNowNum() + new Double(num).intValue();
			achieve.setNowNum(new Double(num).intValue());
			userAchieveMapper.updateAchieveNumById(achieve);
			if (sumNum >= achieve.getNum())
			{
				// 更新成就解锁标识
				userAchieveMapper.updateFlagToLock(achieve.getId());
				// 更新下一个成就等级的数量
				if (achieve.getLev() < achieve.getMaxLev())
				{
					// 剩余成就值
					map.put("lev", achieve.getLev() + 1);
					map.put("nowNum", sumNum - achieve.getNum());
					LOG.info("解锁该项成就，并更新下一等级成就，参数为==================================================", map);
					userAchieveMapper.updateNextLevAchieveValue(map);
				}
			}
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
		UserAchieveBean achieve = userAchieveMapper.queryLatelyAchieveInfo(map);
		if (achieve.getId() != null)
		{
			// 更新成就解锁标识
			userAchieveMapper.updateFlagToLock(achieve.getId());
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

	private String getCurrentTs()
	{
		Calendar cal = Calendar.getInstance();
		return DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss");
	}

	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}

}
