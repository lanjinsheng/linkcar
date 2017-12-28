package com.idata365.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.UserEntity;
import com.idata365.enums.UserSexEnum;
import com.idata365.mapper.app.DriveDataEventMapper;
import com.idata365.mapper.app.DriveDataMapper;


@Service
public class DemoService extends BaseService<DemoService>{
	private final static Logger LOG = LoggerFactory.getLogger(DemoService.class);
	@Autowired
	com.idata365.mapper.app.UserMapper userMapper1;
	@Autowired
	com.idata365.mapper.col.UserColMapper userMapper2;
	public DemoService() {
		LOG.info("DriveDataService DriveDataService DriveDataService DriveDataService");
	}
	 
	@Transactional
	public void test1() {
		UserEntity u=new UserEntity();
		u.setNickName("bbb");
		u.setPassWord("bbbbss");
		u.setUserName("wwwrwrwr");
		u.setUserSex(UserSexEnum.MAN);
		userMapper1.insert(u);
	}
	@Transactional
	public void test2() {
		UserEntity u=new UserEntity();
		u.setNickName("bbb");
		u.setPassWord("bbbbss");
		u.setUserName("wwwrwrwr");
		u.setUserSex(UserSexEnum.MAN);
		userMapper2.insert(u);
	}
	
	@Transactional
	public void test3() {
		UserEntity u=new UserEntity();
		u.setNickName("bbb22");
		u.setPassWord("bbbbss22");
		u.setUserName("wwwrwrwr22");
		u.setUserSex(UserSexEnum.MAN);
		userMapper1.insert(u);
		userMapper2.insert(u);
	}
	
	@Transactional
	public void test4() {
		UserEntity u=new UserEntity();
		u.setNickName("bbb44");
		u.setPassWord("bbbbss44");
		u.setUserName("wwwrwrwr22");
		u.setUserSex(UserSexEnum.MAN);
		userMapper1.insert(u);
		userMapper2.insert(u);
		int i=5/0;
	}
	
	
	@Transactional(value="colTransactionManager")
	public void test5() {
		UserEntity u=new UserEntity();
		u.setNickName("bbb44");
		u.setPassWord("bbbbss44");
		u.setUserName("wwwrwrwr22");
		u.setUserSex(UserSexEnum.MAN);
		userMapper1.insert(u);
		userMapper2.insert(u);
		int i=5/0;
		
	}
}
