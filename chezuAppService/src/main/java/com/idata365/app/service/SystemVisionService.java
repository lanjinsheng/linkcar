package com.idata365.app.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.SystemVisionBean;
import com.idata365.app.mapper.SystemVisionMapper;

@Service
public class SystemVisionService extends BaseService<SystemVisionService>
{
	@Autowired
	private SystemVisionMapper systemVisonMapper;

	public Map<String, Object> verifyVision(String phoneType, String vision)
	{

		SystemVisionBean req = new SystemVisionBean();
		req.setPhoneType(Integer.valueOf(phoneType));
		req.setVision(vision);
		SystemVisionBean rsp = systemVisonMapper.querySystemVisionInfo(req);
		Map<String, Object> map = new HashMap<String, Object>();
		int rspStatus = -1;
		map.put("skipUrl",0);
		map.put("visionStatus", rspStatus);
		if (rsp != null)
		{//获取最后的版本信息比较版本号
			if(rsp.getVision().equals(vision)) {
				
			}else {
				map.put("visionStatus", rsp.getStatus());
				map.put("skipUrl", rsp.getUrl());
			}
		}
		return map;
	}
}
