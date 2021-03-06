package com.idata365.controller;

import com.idata365.entity.UserEntity;
import com.idata365.mapper.app.UserMapper;
import com.idata365.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	DemoService demoService;
	public DemoController() {
		System.out.println("dsfsfsf");
	}
	@RequestMapping("/getUsers")
	public List<UserEntity> getUsers() {
		List<UserEntity> users=userMapper.getAll();
		return null;
	}
 
	@RequestMapping("/insert1")
	public void insert1() {
		demoService.test1();
	}
	@RequestMapping("/insert2")
	public void insert2() {
		demoService.test2();
	}
	@RequestMapping("/insert3")
	public void insert3() {
		demoService.test3();
	}
	@RequestMapping("/insert4")
	public void insert4() {
		demoService.test4();
	}
	@RequestMapping("/insert5")
	public void insert5() {
		demoService.test5();
	}
}