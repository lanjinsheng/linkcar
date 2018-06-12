package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import com.idata365.app.entity.FamilyStayGoldLogBean;
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
	public UserAchieveBean queryLatelyAchieveInfo(Map<String, Object> m);

	/**
	 * 更新用户分享次数
	 */
	public void updateAchieveTimesById(long id);

	/**
	 * 更新用户成就值数量
	 */
	public void updateAchieveNumById(UserAchieveBean bean);

	/**
	 * 更新用户该成就解锁标记
	 */
	public void updateFlagToLock(long id);

	/**
	 * 查询用户可以解锁的成就记录
	 */
	public UserAchieveBean queryUserCanDeblockAchieve(Map<String, Object> m);

	/**
	 * 通过成就等级查询成就信息
	 */
	public UserAchieveBean queryUserAchieveByLev(Map<String, Object> m);

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

	/**
	 * 更新下一等级的成就值 -
	 */
	public void updateNextLevAchieveValue(Map<String, Object> m);

	/**
	 * 查询家族占领黄金榜信息
	 */
	public FamilyStayGoldLogBean queryFamilyStayGoldInfo(long familyId);

	/**
	 * 添加家族占领黄金榜信息
	 */
	public void insertFamilyStayGoldLog(long familyId);

	/**
	 * 更新家族占领黄金榜信息
	 */
	public void updateFamilyStayGoldLog(FamilyStayGoldLogBean bean);

	/**
	 * 所有指定家族成员
	 */
	public List<Long> getFamilyUsers(Long familyId);

	/**
	 * 解锁黄金家族
	 */
	public void unlockGoldFamilyAchieve(Map<String, Object> m);

	/**
	 * 查询用户参加的另一个家族连续黄金天数
	 */
	public FamilyStayGoldLogBean queryUserOtherStayGoldDays(Map<String, Object> m);

	/**
	 * 更新用户黄金家族成就值
	 */
	public void updateGoldFamilyAchieveValue(Map<String, Object> m);

	/**
	 * 校验成就数量
	 */
	public int checkUserAchieveCount(long userId);
}
