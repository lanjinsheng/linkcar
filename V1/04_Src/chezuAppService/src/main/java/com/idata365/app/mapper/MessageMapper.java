package com.idata365.app.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.Message;

public interface MessageMapper {

	void insertMessage(Message message);
	List<Map<String,Object>> getMsgMainTypes(@Param("toUserId") Long toUserId);
	
	List<Map<String,Object>> getMsgListByType(Map<String,Object> map);
	List<Map<String,Object>> getMsgListKaijiang(Map<String,Object> map);
	
	Map<String,Object> getMsgMainTypeTime(Map<String,Object> map);
	Map<String,Object> getMsgKaijiangTime(Map<String,Object> map);
	
	
	void updateRead(@Param("msgId") Long msgId);
	
	Message getMessageById(@Param("id") Long msgId);
	
	
}
