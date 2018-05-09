package com.ljs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DataMapper {
	public List<Map<String,Object>> listPageNetOpera(Map<String,Object> map);
	
	
}