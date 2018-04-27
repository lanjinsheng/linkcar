package com.idata365.app.controller.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

@RestController
public class ShopController extends BaseController{
	 @Autowired
	 ChezuAssetService chezuAssetService;
	 @RequestMapping("/test/doTest")
	 public Map<String,Object> doTest(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		 long userId=this.getUserId();
		 String sign=SignUtils.encryptHMAC(String.valueOf(userId));
		 Map<String,Object> map=chezuAssetService.getUserAsset(userId, sign);
		 return ResultUtils.rtSuccess(map);
	 }
}
