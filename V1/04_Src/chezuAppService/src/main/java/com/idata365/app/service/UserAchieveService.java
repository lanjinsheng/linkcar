package com.idata365.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.mapper.UserAchieveMapper;
import com.idata365.app.service.common.AchieveCommService;

@Service
public class UserAchieveService extends BaseService<UserAchieveService>
{

	@Autowired
	private UserAchieveMapper userAchieveMapper;
	@Autowired
	private AchieveCommService achieveCommService;

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

	/**
	 * 查询某项具体成就列表
	 * 
	 * @Description:
	 * @param userId
	 * @param achieveId
	 * @return
	 * @author:CaiFengYao
	 * @date:2018年1月22日 下午6:06:01
	 */
	@Transactional
	public List<Map<String, Object>> getAchieveListById(long userId, int achieveId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("achieveId", achieveId);
		// 查询列表
		return userAchieveMapper.getUserAchieveListById(map);
	}
}
