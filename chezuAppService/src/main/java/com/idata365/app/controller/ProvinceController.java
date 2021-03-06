package com.idata365.app.controller;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.Province;
import com.idata365.app.service.ProvinceService;
import com.idata365.app.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProvinceController extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(ProvinceController.class);

	@Autowired
	private ProvinceService provinceService;
	
	/**
	 * 省市区json信息
	 * @return
	 */
	@RequestMapping("/district/findProvince")
	public Map<String, Object> findProvince()
	{
		List<Province> resultList = this.provinceService.findProvince();
		return ResultUtils.rtSuccess(resultList);
	}
	
}
