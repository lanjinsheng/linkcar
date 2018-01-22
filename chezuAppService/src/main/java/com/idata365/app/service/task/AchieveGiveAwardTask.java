package com.idata365.app.service.task;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.constant.LotteryLogConstant;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryLogInfoParamBean;
import com.idata365.app.entity.UserAchieveBean;
import com.idata365.app.mapper.LotteryMapper;
import com.idata365.app.mapper.UserAchieveMapper;

/**
 * 个人成就发放奖励定时器模块
 * 
 * @className:com.idata365.app.service.task.AchieveGiveAwardTask
 * @description:TODO
 * @date:2018年1月22日 下午1:54:27
 * @author:CaiFengYao
 */
@Service
public class AchieveGiveAwardTask
{
	@Autowired
	private UserAchieveMapper userAchieveMapper;

	@Autowired
	private LotteryMapper lotteryMapper;

	/**
	 * 1.分享达人
	 */
	public void share()
	{
		List<UserAchieveBean> userAs = userAchieveMapper.queryCanDeblockAchieveTask(1);
		for (UserAchieveBean bean : userAs)
		{
			// 更新用户道具
			saveLotteInfo(bean.getUserId(), bean.getAwardId(), bean.getAwardNum());
		}
	}

	/**
	 * 2.基友遍天下 TODO 还未添加到评分日志表
	 */
	public void gay()
	{
		List<UserAchieveBean> userAs = userAchieveMapper.queryCanDeblockAchieveTask(2);
		for (UserAchieveBean bean : userAs)
		{
			// 更新用户当日个人评分
			updateUserPointTodayByAchieve(bean.getUserId(), bean.getAwardNum());
		}
	}

	/**
	 * 3.神行太保
	 */
	public void god()
	{
		List<UserAchieveBean> userAs = userAchieveMapper.queryCanDeblockAchieveTask(3);
		for (UserAchieveBean bean : userAs)
		{
			// 更新用户当日行程
			updateUserMileageTodayByAchieve(bean.getUserId(), bean.getAwardNum());
		}
	}

	/**
	 * 4.车位终结者
	 */
	public void carEnd()
	{
		List<UserAchieveBean> userAs = userAchieveMapper.queryCanDeblockAchieveTask(4);
		for (UserAchieveBean bean : userAs)
		{
			// 更新用户道具
			saveLotteInfo(bean.getUserId(), bean.getAwardId(), bean.getAwardNum());
		}
	}

	/**
	 * 5.终结好司机
	 */
	public void bestDriver()
	{
		List<UserAchieveBean> userAs = userAchieveMapper.queryCanDeblockAchieveTask(5);
		for (UserAchieveBean bean : userAs)
		{
			// 更新用户当日个人评分
			updateUserPointTodayByAchieve(bean.getUserId(), bean.getAwardNum());
		}
	}

	/**
	 * 6.道具收集者
	 */
	public void collect()
	{
		List<UserAchieveBean> userAs = userAchieveMapper.queryCanDeblockAchieveTask(6);
		for (UserAchieveBean bean : userAs)
		{
			// 更新用户道具
			saveLotteInfo(bean.getUserId(), bean.getAwardId(), bean.getAwardNum());
		}
	}

	/**
	 * 7.黄金家族
	 */
	public void goldFamily()
	{
		List<UserAchieveBean> userAs = userAchieveMapper.queryCanDeblockAchieveTask(7);
		for (UserAchieveBean bean : userAs)
		{
			// 更新用户当日个人评分
			updateUserPointTodayByAchieve(bean.getUserId(), bean.getAwardNum());
		}
	}

	/**
	 * 8.夺宝名人
	 */
	// public void grab()
	// {
	// List<UserAchieveBean> userAs =
	// userAchieveMapper.queryCanDeblockAchieveTask(8);
	// }

	/**
	 * 9.贴条小能手
	 */
	public void stupid()
	{
		List<UserAchieveBean> userAs = userAchieveMapper.queryCanDeblockAchieveTask(9);
		for (UserAchieveBean bean : userAs)
		{
			// 更新用户道具
			saveLotteInfo(bean.getUserId(), bean.getAwardId(), bean.getAwardNum());
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

	/**
	 * 更新用户当日个人评分
	 */
	void updateUserPointTodayByAchieve(long userId, int awardCount)
	{
		UserAchieveBean ubean = new UserAchieveBean();
		ubean.setDaystamp(getCurrentDayStr());
		ubean.setUserId(userId);
		ubean.setAchieveScore(Double.valueOf(String.valueOf(awardCount)));
		userAchieveMapper.updateUserPointsToday(ubean);
	}

	/**
	 * 更新用户当日个人里程
	 */
	void updateUserMileageTodayByAchieve(long userId, int awardCount)
	{
		UserAchieveBean ubean = new UserAchieveBean();
		ubean.setDaystamp(getCurrentDayStr());
		ubean.setUserId(userId);
		ubean.setAhieveMileage(Double.valueOf(String.valueOf(awardCount)));
		userAchieveMapper.updateUserMileageToday(ubean);
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
