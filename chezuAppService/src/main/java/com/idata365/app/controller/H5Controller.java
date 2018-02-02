package com.idata365.app.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.AwardResultBean;
import com.idata365.app.service.AwardService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.StringTools;

@RestController
public class H5Controller extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(H5Controller.class);
	
	@Autowired
	private AwardService awardService;
	
	@RequestMapping("/h5/showAwardUser")
	public Map<String, Object> showAwardUser(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams)
	{
		AwardResultBean resultBean =new AwardResultBean();
		if(allRequestParams.get("id")!=null && !allRequestParams.get("id").equals("")) {
			resultBean = this.awardService.showAwardUser(Long.valueOf(allRequestParams.get("id")));
		}else {
			resultBean = this.awardService.showAwardUser(0L);
		}
		String imgBasePath = super.getImgBasePath();
		String imgUrl = resultBean.getImgUrl();
		if (StringUtils.isNotBlank(imgUrl))
		{
			resultBean.setImgUrl(imgBasePath + imgUrl);
		}
		
		String awardImg = resultBean.getAwardImg();
		if (StringUtils.isNotBlank(awardImg))
		{
			resultBean.setAwardImg(imgBasePath + awardImg);
		}
		
		return ResultUtils.rtSuccess(resultBean);
	}
	
}
