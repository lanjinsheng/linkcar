package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.Prize;

public interface PrizeMapper {

	int deleteByPrimaryKey(Long prizeid);

	int insert(Prize record);

	List<Prize> selectByExample();

	Prize selectByPrimaryKey(Long prizeid);

	int updateByPrimaryKey(Prize record);

	int div(@Param("ordernum") Integer ordernum, @Param("prizeId") Long prizeId);
}