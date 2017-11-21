package com.idata365.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.entity.UserEntity;
import com.idata365.enums.UserSexEnum;
import com.idata365.mapper.UserMapper;

@RestController
public class UserController {

	@Autowired
	private UserMapper userMapper;
	public UserController() {
		System.out.println("dsfsfsf");
	}
	@RequestMapping("/getUsers")
	public List<UserEntity> getUsers() {
		List<UserEntity> users=userMapper.getAll();
		return users;
	}
	
    @RequestMapping("/getUser")
    public UserEntity getUser(Long id) {
    	UserEntity user=userMapper.getOne(id);
        return user;
    }
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String mytest(@RequestParam String hi) {
    	UserEntity user=userMapper.getOne(1L);
        return "ok";
    }
    @RequestMapping("/add")
    public void save(@RequestBody UserEntity user) {
    	userMapper.insert(user);
    }
    @RequestMapping("/addMap")
    public void saveMap(@RequestBody Map<Object,Object> user) {
    	UserEntity u=new UserEntity();
    	u.setNickName(String.valueOf(user.get("nickName")));
    	u.setPassWord(String.valueOf(user.get("passWord")));
    	u.setUserName(String.valueOf(user.get("userName")));
    	u.setUserSex(UserSexEnum.valueOf(String.valueOf(user.get("userSex"))));
    	userMapper.insert(u);
    } 
    @RequestMapping(value="update")
    public void update(@RequestBody UserEntity user) {
    	userMapper.update(user);
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
    	userMapper.delete(id);
    }
    
    
}