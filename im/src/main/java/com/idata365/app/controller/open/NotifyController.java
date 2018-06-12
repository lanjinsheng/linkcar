package com.idata365.app.controller.open;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.ImService;

@RestController
public class NotifyController extends BaseController{
	@Autowired
	ImService imService;
	 
	
    /**
     * 
        * @Title: getFamiliesInfoByUserId
        * @Description: TODO(获取家族信息)
        * @param @param userId
        * @param @param sign
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * Map.put("familyId":"id")
        * Map.put("familyUserCount":"number")
	    * Map.put("fightFamilyId":"id")
        * Map.put("fightFamilyUserCount":"number")
        * @throws
        * @author LanYeYe
     */
//	@RequestMapping(value = "/account/getFamiliesInfoByUserId",method = RequestMethod.POST)
//	public Map<String,Object>  getFamiliesInfoByUserId(@RequestParam(value="userId") long userId,@RequestParam(value="sign") String sign)
//	{
//		LOG.info("userId="+userId+"===sign="+sign);
//		Map<String,Object> rt=new HashMap<String,Object>();
//		rt.put("familyId", 0);
//		rt.put("familyUserCount", 0);
//		rt.put("fightFamilyId", 0);
//		rt.put("fightFamilyUserCount", 0);
//		Map<String,Object> db= accountService.getFamiliesInfoByUserId(userId);
//		if(db==null) {
//			return rt;
//		}else {
//			return db;
//		}
//	}
	 
}
