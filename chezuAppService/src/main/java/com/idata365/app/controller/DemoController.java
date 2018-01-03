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
import com.idata365.app.remote.ChezuService;
import com.idata365.app.service.FamilyInviteService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;


@RestController
public class DemoController extends BaseController {
	@Autowired
	FamilyInviteService familyInviteService;
	@Autowired
	ChezuService chezuService ;
	
	 @RequestMapping("/test/getInviteListTest")
	public Map<String,Object> getInviteListTest(){
		 List<FamilyInvite> list= familyInviteService.getFamilyInviteByPhone("15851750576");
		return ResultUtils.rtSuccess(null);
	}
	 
	 @RequestMapping("/test/insertInvite")
	public Map<String,Object> insertInvite(){
		FamilyInvite familyInvite=new FamilyInvite();
		familyInvite.setMemberUserId(10000L);
		 Long inviteId=familyInviteService.insertInviteFamily(familyInvite);
		 System.out.println(inviteId);
		 return ResultUtils.rtSuccess(null);
	}
	 
	@RequestMapping("/test/getRemoteGps")
	public Map<String,Object> getRemoteGps(){
		 String args=28+""+1514160360;
		 String sign=SignUtils.encryptHMAC(args);
		 Map<String,Object> map=new HashMap<String,Object> ();
		 map.put("userId", 28);
		 map.put("habitId", 1514160360);
		 map.put("sign", sign);
		 Map<String,Object> datas=chezuService.getGpsByUH(map);
		 return ResultUtils.rtSuccess(datas);
	}
}