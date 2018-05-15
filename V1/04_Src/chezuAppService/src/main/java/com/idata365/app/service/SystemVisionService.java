package com.idata365.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.SystemVisionBean;
import com.idata365.app.mapper.SystemVisionMapper;

@Service
public class SystemVisionService extends BaseService<SystemVisionService> {
	@Autowired
	private SystemVisionMapper systemVisonMapper;

	public Map<String, Object> verifyVision(String phoneType, String vision) {

		SystemVisionBean req = new SystemVisionBean();
		req.setPhoneType(Integer.valueOf(phoneType));
		req.setVision(vision);
		long v = Long.valueOf(vision.replaceAll(".", ""));
		List<SystemVisionBean> list = systemVisonMapper.queryAllSystemVisionInfo(req);
		// 最新版本
		SystemVisionBean rsp_new = systemVisonMapper.querySystemVisionInfo(req);

		Map<String, Object> map = new HashMap<String, Object>();
		int rspStatus = -1;
		if (list.size() == 1) {
			map.put("skipUrl", 0);
			map.put("visionStatus", rspStatus);
			return map;
		}
		for (int i = 0; i < list.size(); i++) {
			long vv = Long.valueOf(list.get(i).getVision().replaceAll(".", ""));
			if (vv > v && list.get(i).getStatus() == 1) {
				map.put("visionStatus", 1);
				map.put("skipUrl", list.get(i).getUrl());
			} else {
				map.put("visionStatus", rsp_new.getStatus());
				map.put("skipUrl", rsp_new.getUrl());
			}
		}
		return map;
	}
}
