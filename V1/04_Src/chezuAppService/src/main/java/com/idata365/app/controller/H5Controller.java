package com.idata365.app.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.AwardResultBean;
import com.idata365.app.service.AwardService;
import com.idata365.app.util.ResultUtils;

@RestController
public class H5Controller extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(H5Controller.class);
	
	@Autowired
	private AwardService awardService;
	
	@RequestMapping("/h5/showAwardUser")
	public Map<String, Object> showAwardUser()
	{
		AwardResultBean resultBean = this.awardService.showAwardUser();
		String imgBasePath = super.getImgBasePath();
		String imgUrl = resultBean.getImgUrl();
		if (StringUtils.isNotBlank(imgUrl))
		{
			resultBean.setImgUrl(imgBasePath + imgUrl);
		}
		return ResultUtils.rtSuccess(resultBean);
	}
	
}
