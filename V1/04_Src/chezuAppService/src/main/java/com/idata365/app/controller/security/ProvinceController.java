package com.idata365.app.controller.security;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.Province;
import com.idata365.app.service.ProvinceService;
import com.idata365.app.util.ResultUtils;

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
	@RequestMapping("/om/findProvince")
	public Map<String, Object> findProvince()
	{
		List<Province> resultList = this.provinceService.findProvince();
		return ResultUtils.rtSuccess(resultList);
	}
	
}
