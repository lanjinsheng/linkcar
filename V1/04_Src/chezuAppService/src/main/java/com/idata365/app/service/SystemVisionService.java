package com.idata365.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.SystemVisionBean;
import com.idata365.app.mapper.SystemVisonMapper;

@Service
public class SystemVisionService extends BaseService<SystemVisionService>
{
	@Autowired
	private SystemVisonMapper systemVisonMapper;

	public int verifyVision(String phoneType, String vision)
	{
		SystemVisionBean req = new SystemVisionBean();
		req.setPhoneType(Integer.valueOf(phoneType));
		req.setVision(vision);
		SystemVisionBean rsp = systemVisonMapper.querySystemVisionInfo(req);
		int rspStatus = -1;
		if (rsp != null)
		{
			rspStatus = rsp.getStatus();
		}
		return rspStatus;
	}
}
