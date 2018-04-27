package com.idata365.app.controller.security;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.util.ResultUtils;

@RestController
public class ShopController extends BaseController{
	 @RequestMapping("/test/doTest")
	 public Map<String,Object> doTest(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
	     return ResultUtils.rtSuccess(null);
	 }
}
