package com.idata365.app.controller.open;

import java.util.ArrayList;
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

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.ResultUtils;

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
				if(map.get("isLeader").toString().equals("1")){
					myFamily=String.valueOf(map.get("familyId"));
				}else{
					partakeFamily=String.valueOf(map.get("familyId"));
				}
			}
		}
		return myFamily+","+partakeFamily;
	}

	@RequestMapping("/app/getFamilyUsers")
	public Map<String, Object> familyUsers(@RequestParam(value="userId") Long userId,@RequestParam(value="sign") String sign) {
		List<Map<String,Object>> list=userInfoService.getFamiliesByUserId(userId);
		Map<String, List<Map<String,Object>>> rtMap=new HashMap<String, List<Map<String,Object>>>();
		if(list!=null){
			for(Map<String,Object> map:list){
				if(map.get("isLeader").toString().equals("1")){
				 String	myFamily=String.valueOf(map.get("familyId"));
				 List<Map<String,Object>> list1=userInfoService.getFamilyUsers(Long.valueOf(myFamily),this.getImgBasePath());
				 if(list1==null){
					 list1=new ArrayList<Map<String,Object>>();
				 }
				 rtMap.put("createFamily", list1);
				}else{
				 String	partakeFamily=String.valueOf(map.get("familyId"));
				 List<Map<String,Object>> list2=userInfoService.getFamilyUsers(Long.valueOf(partakeFamily),this.getImgBasePath());
				 if(list2==null){
					 list2=new ArrayList<Map<String,Object>>();
				 }
				 rtMap.put("partakeFamily", list2);
				}
			}
		} 
		return ResultUtils.rtSuccess(rtMap);
	}
}
