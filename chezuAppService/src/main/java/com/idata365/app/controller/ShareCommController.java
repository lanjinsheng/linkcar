package com.idata365.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.LoginRegService;


@RestController
public class ShareCommController{

	@Autowired
	private LoginRegService loginRegService;
	public ShareCommController() {
	}

 
    @RequestMapping("/share/goInvite")
    public Map<String,Object> goInvite(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	return null;
    }
     
}