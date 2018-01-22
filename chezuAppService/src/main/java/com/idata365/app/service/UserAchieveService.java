package com.idata365.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.mapper.UserAchieveMapper;

@Service
public class UserAchieveService
{
	private static final Logger LOG = LoggerFactory.getLogger(UserAchieveService.class);

	@Autowired
	private UserAchieveMapper userAchieveMapper;

	/**
	 * 查询个人成就列表
	 * 
	 * @Description:TODO 查询语句有待修正
	 * @author:CaiFengYao
	 * @date:2018年1月19日 上午11:05:26
	 */
	public List<Map<String, Object>> getUserAchieveList(long userId)
	{
		// 查询列表
		return userAchieveMapper.getUserAchieveList(userId);
	}

	public List<Map<String, Object>> getAchieveListById(long userId, int achieveId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("achieveId", achieveId);
		// 查询列表
		return userAchieveMapper.getUserAchieveListById(map);
	}

	/**
	 * 初始化用户成就
	 * 
	 * @Description:
	 * @author:CaiFengYao
	 * @date:2018年1月22日 下午1:50:57
	 */
	public void initCreateUserAchieve(long userId)
	{
		userAchieveMapper.insertUserAchieveInfo(userId);
	}
}
