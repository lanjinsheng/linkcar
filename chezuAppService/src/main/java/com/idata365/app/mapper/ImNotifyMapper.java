package com.idata365.app.mapper;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.ImNotify;

public interface ImNotifyMapper
{
	public  ImNotify  getNotify(@Param("familyId") Long familyId);
	public  int  insertNotify(ImNotify imNotify);
	public  int  updateNotify(@Param("familyId") Long familyId); 
}
