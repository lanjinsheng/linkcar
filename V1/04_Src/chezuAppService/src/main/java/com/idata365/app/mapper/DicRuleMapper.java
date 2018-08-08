package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DicRuleMapper {

	public List<Map<String, String>> getRulesByType(@Param("ruleType") int ruleType);

}
