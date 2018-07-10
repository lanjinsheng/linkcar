package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

public interface InformMapper {

	public List<Map<String, Object>> informTypeList();

	public int submitInform(Map<String, Object> entity);
}
