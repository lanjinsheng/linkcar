package com.idata365.app.controller.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.FamilyInvite;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;


@RestController
public class ShareSecuController  extends BaseController {

	@Autowired
	private FamilyService familyService;
	public ShareSecuController() {
	}

 
    @RequestMapping("/share/createInvite")
    public Map<String,Object> createInvite(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	Map<String,Object>  family=familyService.findFamilyIdByUserId(this.getUserId());
    	if(family==null) {
    		return ResultUtils.rtFailParam(null,"参数错误，或者用户家族未创建\"");
    	}
    	try {
    		Long familyId=Long.valueOf(rtMap.get("id").toString());
    		String inviteCode=rtMap.get("inviteCode").toString();
    		String datas=familyId+":"+inviteCode+":"+System.currentTimeMillis();
			String key=SignUtils.encryptDataAes(String.valueOf(datas));
			String shareUrl=this.getFamilyInviteBasePath()+key;
			rtMap.put("shareUrl", shareUrl);
			return ResultUtils.rtSuccess(rtMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResultUtils.rtFail(null);
		}
    }
     
}