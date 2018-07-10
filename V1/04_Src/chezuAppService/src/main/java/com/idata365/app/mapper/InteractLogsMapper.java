package com.idata365.app.mapper;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.InteractLogs;

public interface InteractLogsMapper {
	int insertLog(InteractLogs interactLogs);
	List<InteractLogs> getUserAByUserId(Map<String,Object> map);
	List<InteractLogs> getUserBByUserId(Map<String,Object> map);
	int getHadComeOn(Map<String,Object> map);
	int queryLikeCount(@Param("userIdB") long userIdB);
}