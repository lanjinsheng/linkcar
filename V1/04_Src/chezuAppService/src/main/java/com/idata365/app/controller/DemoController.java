package com.idata365.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.FamilyService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;


@RestController
public class DemoController extends BaseController {
	@Autowired
	private FamilyService familyService;
	 @RequestMapping("/share/createInviteTest")
	    public Map<String,Object> createInvite(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
	      Long userId=	Long.valueOf(allRequestParams.get("userId"));
		 Map<String,Object> rtMap=new HashMap<String,Object>();
	    	Long  familyId=familyService.findFamilyIdByUserId(userId);
	    	if(familyId==0) {
	    		return ResultUtils.rtFailParam(null,"参数错误，或者用户家族未创建\"");
	    	}
	    	try {
	    		String datas=familyId+":"+System.currentTimeMillis();
				String key=SignUtils.encryptDataAes(String.valueOf(datas));
				String shareUrl=this.getFamilyInviteBasePath()+key;
				rtMap.put("shareUrl", shareUrl);
				return ResultUtils.rtSuccess(rtMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return ResultUtils.rtFail(null);
			}
	    }
	 public static void main(String []args) {
		 System.out.println("112:345353".split(":")[0]);
	 }
}