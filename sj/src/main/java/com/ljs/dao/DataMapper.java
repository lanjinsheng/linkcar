package com.ljs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface DataMapper {
	public List<Map<String,Object>> listPageNetOpera(Map<String,Object> map);
	public List<Map<String,Object>> listPageUrl(Map<String,Object> map);
	
	int insertUrl(Map<String,Object> map);
	int updateUrl(Map<String,Object> map);
}