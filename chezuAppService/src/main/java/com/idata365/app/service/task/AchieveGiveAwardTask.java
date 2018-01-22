package com.idata365.app.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.idata365.app.entity.UserAchieveBean;
import com.idata365.app.mapper.UserAchieveMapper;

/**
 * 个人成就发放奖励定时器模块
 * 
 * @className:com.idata365.app.service.task.AchieveGiveAwardTask
 * @description:TODO
 * @date:2018年1月22日 下午1:54:27
 * @author:CaiFengYao
 */
public class AchieveGiveAwardTask
{
	@Autowired
	private UserAchieveMapper userAchieveMapper;
	/**
	 * 1.分享达人
	 */
	public void share()
	{
		List<UserAchieveBean> shares = userAchieveMapper.queryCanDeblockAchieveTask(1);
	}

	/**
	 * 2.基友遍天下
	 */
	public void gay()
	{
		List<UserAchieveBean> shares = userAchieveMapper.queryCanDeblockAchieveTask(2);
	}

	/**
	 * 3.神行太保
	 */
	public void god()
	{
		List<UserAchieveBean> shares = userAchieveMapper.queryCanDeblockAchieveTask(3);
	}

	/**
	 * 4.车位终结者
	 */
	public void carEnd()
	{
		List<UserAchieveBean> shares = userAchieveMapper.queryCanDeblockAchieveTask(4);
	}

	/**
	 * 5.终结好司机
	 */
	public void bestDriver()
	{
		List<UserAchieveBean> shares = userAchieveMapper.queryCanDeblockAchieveTask(5);
	}

	/**
	 * 6.道具收集者
	 */
	public void collect()
	{
		List<UserAchieveBean> shares = userAchieveMapper.queryCanDeblockAchieveTask(6);
	}

	/**
	 * 7.黄金家族
	 */
	public void goldFamily()
	{
		List<UserAchieveBean> shares = userAchieveMapper.queryCanDeblockAchieveTask(7);
	}

	/**
	 * 8.夺宝名人
	 */
	public void grab()
	{
		List<UserAchieveBean> shares = userAchieveMapper.queryCanDeblockAchieveTask(1);
	}

	/**
	 * 9.贴条小能手
	 */
	public void stupid()
	{

	}
}
