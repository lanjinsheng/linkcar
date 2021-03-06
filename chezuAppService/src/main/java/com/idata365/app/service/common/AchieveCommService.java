package com.idata365.app.service.common;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.constant.AchieveConstant;
import com.idata365.app.constant.DateConstant;
import com.idata365.app.constant.LotteryLogConstant;
import com.idata365.app.entity.FamilyStayGoldLogBean;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryLogInfoParamBean;
import com.idata365.app.entity.TaskAchieveAddValue;
import com.idata365.app.entity.UserAchieveBean;
import com.idata365.app.enums.AchieveEnum;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.LotteryMapper;
import com.idata365.app.mapper.TaskAchieveAddValueMapper;
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
//	@Autowired
//	private FamilyMapper familyMapper;
	@Autowired
	TaskAchieveAddValueMapper taskAchieveAddValueMapper;

	@Autowired
	private LotteryMapper lotteryMapper;
	Map<String, Object> map = new HashMap<String, Object>();

	/**
	 * 
	    * @Title: addAchieve
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param keyId
	    * @param @param value 数量，默认输入0
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public boolean addAchieve(long keyId, Double value, AchieveEnum type)
	{
		TaskAchieveAddValue taskAchieveAddValue = new TaskAchieveAddValue();
		taskAchieveAddValue.setAchieveType(type);
		taskAchieveAddValue.setKeyId(keyId);
		taskAchieveAddValue.setAddValue(value);
		taskAchieveAddValueMapper.insertTaskAchieveAddValue(taskAchieveAddValue);
		return true;
	}

	public boolean dealTaskAchieveAddValue(TaskAchieveAddValue taskAchieveAddValue)
	{
		AchieveEnum type = taskAchieveAddValue.getAchieveType();
		Long keyId = taskAchieveAddValue.getKeyId();
		Double value = taskAchieveAddValue.getAddValue();
		switch (type)
		{
		case AddGayTimes:
			addGayTimes(keyId);
			break;
		case AddGodTimes:
			addGodTimes(keyId, value);
			break;
		case AddCarEndTimes:
			addCarEndTimes(keyId);
			break;
		case AddBestDriverTimes:
			if (taskAchieveAddValue.getAddValue().doubleValue() == 0)
			{
				addBestDriverTimes(keyId);
			}
			break;
		case AddCollectTimes:
			addCollectTimes(keyId, value);
			break;
		case AddGoldFamilyTimes:
			addGoldFamilyTimes(keyId);
			break;
		case AddGrabTimes:
			addGrabTimes(keyId);
			break;
		case AddStupidTimes:
			addStupidTimes(keyId);
			break;
		case AddShareTimes:
			addShareTimes(keyId);
			break;
		}
		return true;
	}

	/************************************************* 针对具体用户的成就方法 *************************************************************/

	/**
	 * 1.分享达人
	 * 
	 * @Description:分享游戏链接次数
	 */
	protected void addShareTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", AchieveConstant.SHARE_ID);
		updateAchieveTimes(map);
	}

	/**
	 * 2.基友遍天下
	 * 
	 * @Description:拉新
	 */
	protected void addGayTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", AchieveConstant.GAY_ID);
		updateAchieveTimes(map);

	}

	/**
	 * 3.神行太保
	 * 
	 * @Description:累计里程(km)TODO 入参是米
	 */
	protected void addGodTimes(long userId, double mileage)
	{
		if (mileage == 0)
		{
			return;
		}
		map.put("userId", userId);
		map.put("achieveId", AchieveConstant.GOD_ID);
		updateAchieveNum(map, mileage);
	}

	/**
	 * 4.车位终结者
	 * 
	 * @Description:抢车位次数
	 */
	protected void addCarEndTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", AchieveConstant.CAREND_ID);
		updateAchieveTimes(map);
	}

	/**
	 * 5.终结好司机
	 * 
	 * @Description:X天未发生违规行为
	 */
	protected void addBestDriverTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", AchieveConstant.BESTDRIVER_ID);
		updateAchieveTimes(map);
	}

	/**
	 * 6.道具收集者
	 * 
	 * @Description:各种途径获得道具
	 */
	protected void addCollectTimes(long userId, double value)
	{
		map.put("userId", userId);
		map.put("achieveId", AchieveConstant.COLLECT_ID);
		updateAchieveNum(map, value);
	}

	/****************************************** 坑爹模块start **************************************************/
	/**
	 * 7.黄金俱乐部
	 * 
	 * @Description:TODO 所在俱乐部连续X天位于黄金档内，当该成就完成时，该俱乐部下的所有成员都加分
	 */
	protected void addGoldFamilyTimes(long familyId)
	{
		updateAchieveDaysByFamily(familyId);
	}

	/****************************************** 坑爹模块end **************************************************/
	/**
	 * 8.夺宝名人
	 * 
	 * @Description:上传驾驶证和行驶证
	 */
	protected void addGrabTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", AchieveConstant.GRAB_ID);
		updateAchieveWhenUploadLicence(map, userId);
	}

	/**
	 * 9.贴条小能手
	 * 
	 * @Description:累计贴条次数
	 */
	protected void addStupidTimes(long userId)
	{
		map.put("userId", userId);
		map.put("achieveId", AchieveConstant.STUPID_ID);
		updateAchieveTimes(map);
	}

	// 保存道具的发放
	void saveLotteInfo(long userId, int awardId, int awardCount)
	{
		LotteryBean tempParamBean = new LotteryBean();
		tempParamBean.setUserId(userId);
		tempParamBean.setAwardId(awardId);
		tempParamBean.setAwardCount(awardCount);

		this.lotteryMapper.saveOrUpdate(tempParamBean);

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
		LOG.info("updateAchieveTimes=================================================={}", achieve);
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
					LOG.info("解锁该项成就，并更新下一等级成就，参数为=================================================={}", map);
					userAchieveMapper.updateNextLevAchieveValue(map);
				}
			}
		}
	}

	/**
	 * 更新该俱乐部下所有成员当前天数
	 * 	逻辑： 1.先看该俱乐部有没有占领黄金榜记录，如果没有，则生成记录；有，则更新天数（断签则置为0)
	 *      2.校验连续天数是否达到解锁标准：【30天】个人评分+5；【60天】个人评分8分；【100天】个人评分+10
	 *               	 		达到：将俱乐部下所有成员(不包括 已经解锁该成就的成员)的该成就解锁。并将天数置为0
	 *               			没达到：更新该俱乐部加所有成员的该项成就天数
	 */
	void updateAchieveDaysByFamily(long familyId)
	{
		LOG.info("updateAchieveDaysByFamily.start==================================familyId={}", familyId);
		// 查询俱乐部占领黄金榜信息
		FamilyStayGoldLogBean bean = userAchieveMapper.queryFamilyStayGoldInfo(familyId);
		LOG.info("FamilyStayGoldLogBean>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>{}", bean);
		if (bean != null && bean.getLev() >= 3)// 当登榜等级为3级时，不再更新(ps:3级已是最高等级)
		{
			return;
		}
		if (bean == null)
		{
			// 添加信息
			userAchieveMapper.insertFamilyStayGoldLog(familyId);
			bean = userAchieveMapper.queryFamilyStayGoldInfo(familyId);
		}
		int goldCountDays = 0;
		int lev = bean.getLev();
		int lockNum = 0;// 解锁门槛天数(以后要加入到常量类中利于维护)
		if (bean.getContinueFlag() == 1)// 不等于1说明不是连续的，置位掉
		{
			goldCountDays = bean.getGoldCountDays() + 1;
		}
		else
		{
			goldCountDays = 1;// 重置，从第一天开始
		}
		// 该俱乐部下所有成员列表
		List<Long> user = userAchieveMapper.getFamilyUsers(familyId);
		if (lev == 0)
		{
			lockNum = 30;
			if (goldCountDays >= lockNum)
			{
				lev = lev + 1;
				// 解锁
				unclockUserGoldAchieve(user, lev);
			}
			else
			{
				// 更新成就值
				addUserGoldAchieveNum(user, lev, familyId, goldCountDays);
				goldCountDays = 0;// 升级后置为0
			}
		}
		else if (bean.getLev() == 1)
		{
			lockNum = 60;
			if (goldCountDays >= lockNum)
			{
				lev = lev + 1;
				// 解锁
				unclockUserGoldAchieve(user, lev);
			}
			else
			{
				// 更新成就值
				addUserGoldAchieveNum(user, lev, familyId, goldCountDays);
				goldCountDays = 0;// 升级后置为0
			}
		}
		else if (bean.getLev() == 2)
		{
			lockNum = 100;
			if (goldCountDays >= lockNum)
			{
				lev = lev + 1;
				// 解锁
				unclockUserGoldAchieve(user, lev);
			}
			else
			{
				// 更新成就值
				addUserGoldAchieveNum(user, lev, familyId, goldCountDays);
				goldCountDays = 0;// 升级后置为0
			}
		}
		bean.setLev(lev);
		bean.setGoldCountDays(goldCountDays);
		// 更新天数、等级
		userAchieveMapper.updateFamilyStayGoldLog(bean);
		LOG.info("updateAchieveDaysByFamily.end========================================{}");
	}

	// 解锁用户黄金俱乐部
	void unclockUserGoldAchieve(List<Long> user, int lev)
	{
		for (Long userId : user)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("lev", lev);
			// 解锁该俱乐部下所有成员此项成就
			userAchieveMapper.unlockGoldFamilyAchieve(map);
		}
	}

	// 增加用户黄金俱乐部登榜成就数量
	void addUserGoldAchieveNum(List<Long> user, int lev, long familyId, int continueCount)
	{
		for (Long userId : user)
		{
			int nowNum = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("achieve", 7);
			map.put("familyId", familyId);
			// 查询用户参加的另一个俱乐部的黄金期
			FamilyStayGoldLogBean fb = userAchieveMapper.queryUserOtherStayGoldDays(map);
			if (fb != null && fb.getGoldCountDays() > continueCount)// 当用户参加的另一个俱乐部成就更高时，将更新最高值
			{
				nowNum = fb.getGoldCountDays();
			}
			else
			{
				nowNum = continueCount;
			}
			map.put("lev", lev + 1);
			map.put("nowNum", nowNum);
			// 更新数量
			userAchieveMapper.updateGoldFamilyAchieveValue(map);
		}
	}

	// 更新成就数量
	void updateAchieveNum(Map<String, Object> map, double num)
	{
		// 查看用户某项成就最新记录id
		UserAchieveBean achieve = userAchieveMapper.queryLatelyAchieveInfo(map);
		LOG.info("updateAchieveNum=================================================={}", achieve);
		if (achieve != null && achieve.getId() != null)
		{
			int sumNum = achieve.getNowNum() + new Double(num).intValue();// 当前总量=库存数量+新增数量
			achieve.setNowNum(new Double(num).intValue());
			userAchieveMapper.updateAchieveNumById(achieve);
			if (sumNum >= achieve.getNum())// 如果当前总量大于升级数量则给予升级处理
			{
				// 更新成就解锁标识
				userAchieveMapper.updateFlagToLock(achieve.getId());
				// 更新下一个成就等级的数量
				if (achieve.getLev() < achieve.getMaxLev())
				{
					// 剩余成就值
					map.put("lev", achieve.getLev() + 1);
					map.put("nowNum", sumNum - achieve.getNum());
					LOG.info("解锁该项成就，并更新下一等级成就，参数为=================================================={}", map);
					userAchieveMapper.updateNextLevAchieveValue(map);
				}
			}
		}
	}

	/**
	 * 更新成就操作（当上传驾照时）
	 * 
	 * @Description:TODO 奖励因为是一对多的关系所以没有在数据库里有体现，是写死在程序里的
	 */
	public void updateAchieveWhenUploadLicence(Map<String, Object> map, long userId)
	{
		// 查看用户某项成就最新记录id
		UserAchieveBean achieve = userAchieveMapper.queryLatelyAchieveInfo(map);
		if (achieve != null && achieve.getId() != null)
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

	/**
	 * 初始化创建用户成就
	 * @Description:
	 * @param userId
	 * @author:CaiFengYao
	 * @date:2018年1月27日 下午1:57:21
	 */
	public void initCreateUserAchieve(long userId)
	{
		// 校验是否已经初始化过
		int count = userAchieveMapper.checkUserAchieveCount(userId);
		if (count == 0)
		{
			userAchieveMapper.insertUserAchieveInfo(userId);
		}

	}
}
