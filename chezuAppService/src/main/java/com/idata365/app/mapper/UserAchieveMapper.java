package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import com.idata365.app.entity.UserAchieveBean;

/**
 * 
 * @className:com.idata365.app.mapper.UserAchieveMapper
 * @description:TODO
 * @date:2018年1月19日 下午3:05:57
 * @author:CaiFengYao
 */
public interface UserAchieveMapper
{
	/**
	 * 查询个人成就列表
	 */
	public List<Map<String, Object>> getUserAchieveList(long userId);

	/**
	 * 初始化用户成就
	 */
	public void insertUserAchieveInfo(long userId);

	/**
	 * 查询用户某项成就详情
	 */
	public List<Map<String, Object>> getUserAchieveListById(Map<String, Object> m);

	/**
	 * 查看用户某项成就最新记录id
	 */
	public Integer queryLatelyAchieveId(Map<String, Object> m);

	/**
	 * 更新用户分享次数
	 */
	public void updateAchieveTimesById(int id);

	/**
	 * 更新用户上传驾照时成就 及解锁标记
	 */
	public void updateAchieveWhenUploadLicence(int id);

	/**
	 * 查询用户可以解锁的成就记录
	 */
	public UserAchieveBean queryUserCanDeblockAchieve(Map<String, Object> m);

	/**
	 * 查询可以解锁的成就记录(定时器)
	 */
	public List<UserAchieveBean> queryCanDeblockAchieveTask(int achieveId);

	/**
	 * 更新用户当日个人评分
	 */
	public void updateUserPointsToday(UserAchieveBean bean);

	/**
	 * 更新用户当日个人评分
	 */
	public void updateUserMileageToday(UserAchieveBean bean);
}
