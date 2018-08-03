package com.idata365.app.controller.security;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.service.FamilyService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;


@RestController
public class ShareSecuController  extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(ShareSecuController.class);
	@Autowired
	private FamilyService familyService;
	@Autowired
	private SystemProperties systemProperties;
	public ShareSecuController() {
	}

 
    @RequestMapping("/share/createInvite")
    public Map<String,Object> createInvite(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	String shareType=String.valueOf(requestBodyParams.get("shareType"));
    	LOG.info("Param:shareType="+shareType);//1家族邀请，2应用分享
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	
    	try {
    		if(shareType.equals("1")){
	    		Map<String,Object>  family=familyService.findFamilyIdByUserId(this.getUserId());
	        	if(family==null) {
	        		return ResultUtils.rtFailParam(null,"参数错误，或者用户家族未创建\"");
	        	}
	    		Long familyId=Long.valueOf(family.get("id").toString());
	    		String inviteCode=this.getUserId().toString();
	    		String datas=familyId+":"+inviteCode+":"+System.currentTimeMillis();
				String key=SignUtils.encryptDataAes(String.valueOf(datas));
				String shareUrl=this.getFamilyInviteBasePath(systemProperties.getH5Host())+key+"&shareType="+shareType;
				shareUrl=shareUrl+"&userName="+URLEncoder.encode(this.getUserInfo().getNickName(),"UTF-8");
				shareUrl=shareUrl+"&familyName="+URLEncoder.encode(family.get("familyName").toString(),"UTF-8");
				rtMap.put("shareUrl", shareUrl);
				rtMap.put("title", String.format("邀请您参与【%s】玩赚链车", family.get("familyName").toString()));
				rtMap.put("content", "安全驾驶，即有机会获得超丰厚奖品！");
				List<String> imgs = new ArrayList<String>();
				imgs.add("http://apph5.idata365.com/appImgs/logo.png");
				rtMap.put("imgs", imgs);
				return ResultUtils.rtSuccess(rtMap);
    		}else if(shareType.equals("2")){
    			String inviteCode=this.getUserId().toString();
	    		String datas=inviteCode+":"+System.currentTimeMillis();
				String key=SignUtils.encryptDataAes(String.valueOf(datas));
				String shareUrl=this.getFamilyShareBasePath(systemProperties.getH5Host())+key+"&shareType="+shareType;
				shareUrl=shareUrl+"&userName="+URLEncoder.encode(this.getUserInfo().getNickName(),"UTF-8");
				rtMap.put("shareUrl", shareUrl);
				rtMap.put("title", "开车挖矿拍豪宅，好玩就在链车!");
				rtMap.put("content", "区块链时代的车联网新玩法，驾驶行为就能挖矿，丰厚好礼换不停！");
				List<String> imgs = new ArrayList<String>();
				imgs.add("http://apph5.idata365.com/appImgs/logo.png");
				rtMap.put("imgs", imgs);
				return ResultUtils.rtSuccess(rtMap);
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResultUtils.rtFail(null);
		}
    	return ResultUtils.rtFailParam(null,"无效请求");
    }
     
}