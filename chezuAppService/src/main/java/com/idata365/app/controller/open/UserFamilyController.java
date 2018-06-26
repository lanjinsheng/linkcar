package com.idata365.app.controller.open;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.controller.security.MessageController;
import com.idata365.app.entity.LicenseDriver;
import com.idata365.app.entity.LicenseVehicleTravel;
import com.idata365.app.entity.Message;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.service.MessageService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.ServerUtil;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.StaticDatas;
import com.idata365.app.util.ValidTools;

@RestController
public class UserFamilyController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(UserFamilyController.class);
	@Autowired
	private UserInfoService userInfoService;
 
 
	@RequestMapping(value = "/app/getFamiliesByUserId",method = RequestMethod.POST)
	public String  getFamiliesByUserId(@RequestParam(value="userId") Long userId,@RequestParam(value="sign") String sign)
	{
		LOG.info("userId="+userId+"==sign="+sign);
		String myFamily="0";
		String partakeFamily="0";
		List<Map<String,Object>> list=userInfoService.getFamiliesByUserId(userId);
		if(list!=null){
			for(Map<String,Object> map:list){
				if(map.get("isLeader").equals("1")){
					myFamily=String.valueOf(map.get("familyId"));
				}else{
					partakeFamily=String.valueOf(map.get("familyId"));
				}
			}
		}
		return myFamily+","+partakeFamily;
	}

}
