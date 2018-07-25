package com.idata365.app.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.UserCarLogs;

public interface UserCarLogsMapper {
	public Map<String, Object> getUserCar(Map<String, Object> map);

	public UserCarLogs getUserCarLogById(@Param("id") Long id);

	public Map<String, Object> getUserCarHistory(@Param("id") Long id);

	public int initUserCar(UserCarLogs logs);

	public int updateEndTimeById(UserCarLogs UserCarLogs);

	public int insertUserCarLogs(UserCarLogs UserCarLogs);

}
