package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import com.idata365.app.entity.ImMsg;

public interface ImMsgMapper
{
	public  List<ImMsg>  getMsg(Map<String,Object> searchMap);
	public  int  insertMsg(List<ImMsg> list);
	public  int  updateMsg(Map<String,Object> map);
	
	 
}
