package com.idata365.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.SystemVisionBean;
import com.idata365.app.mapper.SystemVisionMapper;
import com.idata365.app.util.ValidTools;

@Service
public class SystemVisionService extends BaseService<SystemVisionService> {
	@Autowired
	private SystemVisionMapper systemVisonMapper;

	public Map<String, Object> verifyVision(String phoneType, String vision) {

		SystemVisionBean req = new SystemVisionBean();
		req.setPhoneType(Integer.valueOf(phoneType));
		req.setVision(vision);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("visionStatus", -1);
		SystemVisionBean bean = systemVisonMapper.querySystemVisionInfo(req);
		if (ValidTools.isBlank(bean)) {
			return map;
		}

		int index = -1;
		List<SystemVisionBean> list = systemVisonMapper.queryAllSystemVisionInfo(req);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getVision().equals(bean.getVision())) {
				index = i;
			}
		}
		if (index > -1 && index + 1 < list.size()) {
			for (int i = index + 1; i < list.size(); i++) {
				if (list.get(i).getStatus() == 1) {
					map.put("visionStatus", 1);
					return map;
				} else if (list.get(i).getStatus() == 2) {
					map.put("visionStatus", 2);
				}
			}
		}
		return map;
	}
}
