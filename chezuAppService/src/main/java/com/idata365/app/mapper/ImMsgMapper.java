package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.ImMsg;

public interface ImMsgMapper
{
	public  List<ImMsg>  getMsg(Map<String,Object> searchMap);
	public  int  insertMsg(List<ImMsg> list);
	public  int  updateMsg(Map<String,Object> map);
	public  ImMsg  getMsgById(@Param("id") Long id);
	 
}
