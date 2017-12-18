package com.idata365.app.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.Message;

public interface MessageMapper {

	void insertMessage(Message message);
	List<Map<String,Object>> getMsgMainTypes(@Param("toUserId") Long toUserId);
	
	List<Map<String,Object>> getMsgListByType(Map<String,Object> map);
	
	Map<String,Object> getMsgMainTypeTime(@Param("parentType") int parentType);
	
	void updateRead(@Param("msgId") Long msgId);
}
