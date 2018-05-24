package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;



public interface DicNotifyMapper {
	public Map<String, String> getLatestNotify();

	public List<Map<String, String>> playHelper();

}
