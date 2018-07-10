package com.idata365.app.serviceV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.mapper.InformMapper;
import com.idata365.app.service.BaseService;

@Service
@Transactional
public class InformService extends BaseService<InformService> {

	@Autowired
	private InformMapper informMapper;

	public List<Map<String, String>> informTypeList() {
		List<Map<String, String>> rtList = new ArrayList<>();
		List<Map<String, Object>> list = this.informMapper.informTypeList();
		if (list == null || list.size() == 0)
			return null;
		for (Map<String, Object> map : list) {
			Map<String, String> m = new HashMap<>();
			m.put("informId", map.get("informId").toString());
			m.put("informValue", map.get("informValue").toString());
			rtList.add(m);
		}
		return rtList;
	}

	public int submitInform(Map<String, Object> map) {
		return this.informMapper.submitInform(map);
	}
}
