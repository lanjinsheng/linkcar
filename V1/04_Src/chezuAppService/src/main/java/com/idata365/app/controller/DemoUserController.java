package com.idata365.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.UserEntity;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.mapper.UserMapper;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.ValidTools;


@RestController
public class DemoUserController  extends BaseController {

	@Autowired
	private UserMapper userMapper;
    @Autowired
    LoginRegService loginRegService;
	public DemoUserController() {
		System.out.println("dsfsfsf");
	}
	@RequestMapping("/getUsers")
	public List<UserEntity> getUsers() {
		List<UserEntity> users=userMapper.getAll();
		return users;
	}
    @RequestMapping("/account/loginTest")
    public Map<String,Object> loginTest(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	 String phone="15802187691";
    	 String password="14e1b600b1fd579f47433b88e8d85291";
    	String token="";
    		 UsersAccount account=new UsersAccount();
    		 String status=loginRegService.validAccount(phone, password,account);
    		if(status.equals(loginRegService.OK)) {//账号通过
    			token=UUID.randomUUID().toString().replaceAll("-", "");
    			loginRegService.insertToken(account.getId(),token);
    		}else {
    			
    		}
    	rtMap.put("status", status);
    	rtMap.put("token", token);
    	return ResultUtils.rtSuccess(rtMap);
    }
    @RequestMapping("/account/addDeviceUserInfoTest")
    public Map<String,Object> addDeviceUserInfoTest(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("deviceToken")))
          return ResultUtils.rtFailParam(null);
    	String deviceInfo=String.valueOf(requestBodyParams.get("deviceToken"));
    	long userId=0;
    	if( ValidTools.isNotBlank(requestBodyParams.get("userId"))){
    		 userId=Long.valueOf(requestBodyParams.get("userId").toString());
    	}
    	String alias=loginRegService.addDeviceUserInfo(deviceInfo, userId);
    	rtMap.put("alias", alias);
    	return ResultUtils.rtSuccess(rtMap);
    }
    
    @RequestMapping("/getUser")
    public UserEntity getUser(Long id) {
    	UserEntity user=userMapper.getOne(id);
        return user;
    }
    @RequestMapping(value = "/mytest",method = RequestMethod.GET)
    public String mytest(@RequestParam String hi) {
    	UserEntity user=userMapper.getOne(1L);
        return "ok";
    }
    @RequestMapping("/add")
    public void save(UserEntity user) {
    	userMapper.insert(user);
    }
    
    @RequestMapping(value="update")
    public void update(UserEntity user) {
    	userMapper.update(user);
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
    	userMapper.delete(id);
    }
    
    
}