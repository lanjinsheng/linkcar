package com.idata365.app.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.idata365.app.entity.UserEntity;
import com.idata365.app.enums.UserSexEnum;
import com.idata365.app.util.DateTools;


@Controller
public class WebTestController   {

  
    @RequestMapping(value = "/index",method ={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView index(Map<String, Object> model) {
    	model.put("time", DateTools.getCurDate());
		model.put("message", "这是asset测试的内容。。。");
		UserEntity user=new UserEntity();
    	user.setNickName("兰爷爷");
    	user.setPassWord("abcd");
    	user.setUserName("mogelylan");
    	user.setUserSex(UserSexEnum.WOMAN);
		model.put("toUserName", "张三");
		model.put("user", user);
    	 ModelAndView mv = new ModelAndView("welcome");
    	 return mv;
    }
    @RequestMapping(value = "/jsp",method ={RequestMethod.POST,RequestMethod.GET})
    public String jsp(Map<String, Object> model) {
		model.put("time", DateTools.getCurDate());
		model.put("message", "这是asset测试的内容。。。");
		UserEntity user=new UserEntity();
    	user.setNickName("兰爷爷");
    	user.setPassWord("abcd");
    	user.setUserName("mogelylan");
    	user.setUserSex(UserSexEnum.WOMAN);
		model.put("toUserName", "张三");
		model.put("user", user);
    	 return "welcome";
    }
    
   
}