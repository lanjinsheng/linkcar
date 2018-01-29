package com.idata365.app.service;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryLogInfoParamBean;
import com.idata365.app.entity.TaskGiveUserAchieveBean;
import com.idata365.app.entity.UserAchieveBean;
import com.idata365.app.mapper.TaskGiveUserAchieveMapper;

 

@Service
public class TaskGiveUserAchieveService
{
	private final static Logger LOG = LoggerFactory.getLogger(TaskGiveUserAchieveService.class);
	@Autowired
	TaskGiveUserAchieveMapper taskGiveUserAchieveMapper;

	/**
	 * 初始化成就任务
	 * 
	 * @Description:
	 * @author:CaiFengYao
	 * @date:2018年1月23日 下午4:20:44
	 */
	public void initAchieveTask()
	{
		taskGiveUserAchieveMapper.iniTaskGiveUserAchieve();
	}

	/**
	 * 查询待处理成就任务
	 */
	public List<TaskGiveUserAchieveBean> queryAchieveWaitList()
	{
		return taskGiveUserAchieveMapper.queryAhieveWaitDealList();
	}

	public void updateFailUserAchieveTask(TaskGiveUserAchieveBean bean)
	{
		taskGiveUserAchieveMapper.updateFailUserAchieveTask(bean);
	}

	/**
	 * 更新状态
	 */
	public void updateUserAchieveTaskStatus(TaskGiveUserAchieveBean bean)
	{
		taskGiveUserAchieveMapper.updateUserAchieveTaskStatus(bean);
	}

	public void updateSuccUserAchieveTask(TaskGiveUserAchieveBean bean)
	{
		taskGiveUserAchieveMapper.updateSuccUserAchieveTask(bean);
	}

	/**
	 * 发放成就对应的奖励
	 */
	@Transactional
	public boolean giveAwardByAchieve(long achieveRecordId)
	{
		LOG.info("giveAwardByAchieve:achieveRecordId>>>>>>>>>>>>>>>>>>>>" + achieveRecordId);
		UserAchieveBean bean = taskGiveUserAchieveMapper.getUserAchieveInfoById(achieveRecordId);
		LOG.info("UserAchieveBean   id>>>>>>>>>>>>>>>>>>>>" + bean.getId());
		long userId = bean.getUserId();
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
		// 更新发放标识
		taskGiveUserAchieveMapper.updateAchieveGiveStatusIsOver(achieveRecordId);
		return true;
	}

	// 保存道具的发放
	void saveLotteInfo(long userId, int awardId, int awardCount)
	{
		LotteryBean tempParamBean = new LotteryBean();
		tempParamBean.setUserId(userId);
		tempParamBean.setAwardId(awardId);
		tempParamBean.setAwardCount(awardCount);

		this.taskGiveUserAchieveMapper.increLotteryCount(tempParamBean);

		LotteryLogInfoParamBean lotteryLogParamBean = new LotteryLogInfoParamBean();
		lotteryLogParamBean.setUserId(userId);
		lotteryLogParamBean.setAwardId(awardId);
		lotteryLogParamBean.setAwardCount(awardCount);
		lotteryLogParamBean.setType(6);// 1：比赛PK消耗；2：行程中奖励获得；3：签到获得；4：赠予他人道具；5：获得别人赠送道具;6:个人成就奖励
		lotteryLogParamBean.setTimestamp(getCurrentTs());
		this.taskGiveUserAchieveMapper.saveLotteryLog(lotteryLogParamBean);
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
		taskGiveUserAchieveMapper.updateUserPointsToday(ubean);
	}

	private String getCurrentTs()
	{
		Calendar cal = Calendar.getInstance();
		return DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss");
	}

	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, "yyyy-MM-dd");
		return dayStr;
	}
}
