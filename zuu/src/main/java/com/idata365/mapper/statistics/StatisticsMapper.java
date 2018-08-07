package com.idata365.mapper.statistics;



import java.util.List;
import java.util.Map;

import com.idata365.entity.Statistics;


public interface StatisticsMapper {
	 int  insertRecord(List<Statistics> list);
	 public Map<String,Object> jugeTable(Map<String,Object> map);
	 
	 public int createTableSql(Map<String,Object> map);
	 
	 public int moveRecord(Map<String,Object> map);
	 
	 public int deleteRecord(Map<String,Object> map);
	 
	 public Long getMaxId(Map<String,Object> map);
	 
	 
}
