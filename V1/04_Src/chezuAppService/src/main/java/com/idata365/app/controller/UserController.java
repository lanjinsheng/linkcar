package com.idata365.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.UserEntity;
import com.idata365.app.mapper.UserMapper;
import com.idata365.app.service.LoginRegService;


@RestController
public class UserController {

	@Autowired
	private LoginRegService loginRegService;
	public UserController() {
		System.out.println("UserController");
	}
 
    @RequestMapping("/login")
    public Map<String,Object> login(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
     
        return null;
    }
   
    
}