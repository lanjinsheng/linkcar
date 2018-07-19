package com.idata365.app.controller.securityV2;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.serviceV2.BoxTreasureService;
import com.idata365.app.util.ResultUtils;

@RestController
public class BoxTreasureController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(BoxTreasureController.class);
	@Autowired
	BoxTreasureService boxTreasureService;
	@RequestMapping(value = "/getTripBox")
	Map<String, Object> getTripBox(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		return ResultUtils.rtSuccess(null);
	}
	 
}
