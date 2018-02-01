package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryLogInfoParamBean;
import com.idata365.app.entity.TaskGiveUserAchieveBean;
import com.idata365.app.entity.UserAchieveBean;

/**
 * 
 * @className:com.idata365.mapper.app.TaskGiveUserAchieveMapper
 * @description:TODO
 * @date:2018年1月23日 下午3:39:59
 * @author:CaiFengYao
 */
public interface TaskGiveUserAchieveMapper
{

	/**
	 * 初始化成就发放任务
	 */
	public void iniTaskGiveUserAchieve();

	/**
	 * 查询待处理成就列表
	 */
	public List<TaskGiveUserAchieveBean> queryAhieveWaitDealList(TaskGiveUserAchieveBean taskGiveUserAchieveBean);

	/**
	 * 更新失败信息
	 */
	public void updateFailUserAchieveTask(TaskGiveUserAchieveBean bean);
	
	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	

	/**
	 * 更新记录状态
	 */
	public void updateUserAchieveTaskStatus(TaskGiveUserAchieveBean bean);

	/**
	 * 查询用户某项成就详情
	 */
	public UserAchieveBean getUserAchieveInfoById(long id);

	public void saveOrUpdate(LotteryBean bean);

	public void saveLotteryLog(LotteryLogInfoParamBean bean);

	/**
	 * 更新用户当日个人评分
	 */
	public void updateUserPointsToday(UserAchieveBean bean);

	/**
	 * 更新成就奖励发放为完成
	 */
	public void updateAchieveGiveStatusIsOver(long id);

	/**
	 * 更新状态为成功
	 */
	public void updateSuccUserAchieveTask(TaskGiveUserAchieveBean bean);

	/**
	 * 锁定任务表
	 */
	public void lockGiveUserAchieveTask(TaskGiveUserAchieveBean bean);
}
