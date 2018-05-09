package com.ljs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface BackgroundUserMapper {

	/**
	 * @Description:根据条件查找后台用户
	 * @author 高煜
	 * @date 2016-04-23
	 */
	public Map<String, Object> findBackgroundUser(Map<String, Object> map);

	/**
	 * @Description: 后台用户列表查询方法
	 * @author 高煜
	 * @date 2016-04-23
	 */
	public List<Map<String, Object>> listPageBackgroundUser(
			Map<String, Object> map);

	/**
	 * @Description: 根据ID获取后台用户基本信息
	 * @author 高煜
	 * @date 2016-04-25
	 */
	public Map<String, Object> getBackgroundUserById(Map<String, Object> map);

	/**
	 * @Description: 根据条件查找后台用户基本信息
	 * @author 高煜
	 * @date 2016-04-25
	 */
	public List<Map<String, Object>> findBackgroundUserByMap(
			Map<String, Object> queryMap);

	/**
	 * @Description: 后台用户基本信息插入
	 * @author 高煜
	 * @date 2016-04-25
	 */
	public void insertBackgroundUser(Map<String, Object> map);

	/**
	 * @Description: 后台用户基本信息更新
	 * @author 高煜
	 * @date 2016-04-25
	 */
	public void updateBackgroundUser(Map<String, Object> map);

	/**
	 * @Description: 后台用户刪除
	 * @author 高煜
	 * @date 2016-04-25
	 */
	public void delBackgroundUser(Map<String, Object> xmlMap);
}
