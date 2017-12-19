package com.idata365.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.ReuseExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.FamilyInvite;
import com.idata365.app.service.FamilyInviteService;
import com.idata365.app.util.ResultUtils;


@RestController
public class DemoController extends BaseController {
	@Autowired
	FamilyInviteService familyInviteService;
	
	 @RequestMapping("/test/getInviteListTest")
	public Map<String,Object> getInviteListTest(){
		 List<FamilyInvite> list= familyInviteService.getFamilyInviteByPhone("15851750576");
		return ResultUtils.rtSuccess(null);
	}
}