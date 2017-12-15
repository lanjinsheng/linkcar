package com.idata365.app.controller.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.LoginRegService;


@RestController
public class ShareSecuController  extends BaseController {

	@Autowired
	private LoginRegService loginRegService;
	public ShareSecuController() {
	}

 
    @RequestMapping("/share/createInvite")
    public Map<String,Object> createInvite(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	return null;
    }
     
}