package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.constant.DicFamilyTypeConstant;
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.service.FightService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;
@RestController
public class FamilyController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(FamilyController.class);
	@Autowired
	private UserInfoService userInfoService;
 
 

 
	 
}
