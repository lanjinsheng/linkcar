package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

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
	 * 
	 * @author:CaiFengYao
	 * @date:2018年1月19日 下午2:11:25
	 */
	public List<Map<String, Object>> getUserAchieveList(long userId);

	/**
	 * 初始化用户成就
	 * 
	 * @author:CaiFengYao
	 * @date:2018年1月19日 下午2:11:25
	 */
	public void insertUserAchieveInfo(long userId);

	/**
	 * 查询用户某项成就详情
	 * 
	 * @author:CaiFengYao
	 * @date:2018年1月19日 下午2:11:25
	 */
	public List<Map<String, Object>> getUserAchieveListById(Map<String, Object> m);
}
