package com.idata365.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.ImMsg;
import com.idata365.app.entity.ImNotify;
import com.idata365.app.mapper.ImMsgMapper;
import com.idata365.app.mapper.ImNotifyMapper;

@Service
public class ImService extends BaseService<ImService>
{

    @Autowired
	ImMsgMapper imMsgMapper;
    @Autowired
	ImNotifyMapper imNotifyMapper;
    @Autowired
    FamilyService familyService;
    public Map<String,Object> getImMain(Long familyId,Long userId,Long msgId){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	ImNotify imNotify=imNotifyMapper.getNotify(familyId);
    	if(imNotify==null) {
    		rtMap.put("imNotify", new ImNotify());
    	}else {
    		rtMap.put("imNotify", imNotify);
    	}
    	ImMsg msg=new ImMsg();
    	msg.setId(msgId);
    	msg.setFamilyId(familyId);
    	List<ImMsg> imMsgs=imMsgMapper.getMsg(msg);
    	
    	if(imMsgs==null || imMsgs.size()==0) {
    		rtMap.put("imMsgs", new ArrayList());
    	}else {
    		rtMap.put("imMsgs", imMsgs);
    	}
    	
    	return rtMap;
    }
    
    public Map<String,Object> getImMsgs(Long familyId,Long userId,Long msgId,String basePath){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	Map<String,Object> upMap=new HashMap<String,Object>();
    	ImMsg msg=new ImMsg();
    	msg.setId(msgId);
    	msg.setFamilyId(familyId);
    	msg.setFromUserPic(basePath);
    	List<ImMsg> imMsgs=imMsgMapper.getMsg(msg);
    	
    	if(imMsgs==null || imMsgs.size()==0) {
    		rtMap.put("imMsgs", new ArrayList());
    		
    	}else {
    		upMap.put("familyId", familyId);
        	upMap.put("toUserId", userId);
        	upMap.put("beginId", msgId);
    		upMap.put("endId", imMsgs.get(0).getId());
    		imMsgMapper.updateMsg(upMap);
    		rtMap.put("imMsgs", imMsgs);
    	}
    	return rtMap;
    }
    
    public Map<String,Object> getImNotify(Long familyId,Long userId,String basePath){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	ImNotify imNotify=imNotifyMapper.getNotify(familyId);
    	if(imNotify==null) {
    		//获取家族信息
    		Map<String,Object> leaderInfo=familyService.findLeaderByFamilyId(familyId);
    		imNotify=new ImNotify();
    		imNotify.setFamilyName(String.valueOf(leaderInfo.get("familyName")));
    		imNotify.setLeaderName(String.valueOf(leaderInfo.get("nickName")));
    		imNotify.setLeaderId(Long.valueOf(leaderInfo.get("id").toString()));
    		imNotify.setLeaderPic(basePath+String.valueOf(leaderInfo.get("imgUrl")));
    		imNotify.setFamilyId(familyId);
    		rtMap.put("imNotify", imNotify);
    	}else {
    		imNotify.setLeaderPic(basePath+imNotify.getLeaderPic());
    		rtMap.put("imNotify", imNotify);
    	}
    	return rtMap;
    }
    public int insertNotify(ImNotify imNotify) {
    	imNotifyMapper.updateNotify(imNotify.getFamilyId());
    	return imNotifyMapper.insertNotify(imNotify);
    }
    
    
    public int insertMsg(ImMsg imMsg) {
    	List<Map<String,Object>> list=familyService.findFamilyRelation(imMsg.getFamilyId());
    	List<ImMsg> insertList=new ArrayList<ImMsg>();
    	
    	for(Map<String,Object> m:list) {
    		Long toUserId=Long.valueOf(m.get("userId").toString());
    		if(toUserId==imMsg.getToUserId()) {
    			insertList.add(imMsg);
    		}else {
        		ImMsg msg=new ImMsg();
        		msg.setFamilyId(imMsg.getFamilyId());
        		msg.setFromUserId(imMsg.getFromUserId());
        		msg.setFromUserPic(imMsg.getFromUserPic());
        		msg.setToUserId(toUserId);
        		msg.setMsg(imMsg.getMsg());
        		msg.setIsRead(0);
        		insertList.add(msg);
    		}
    	
    	}
    	
    	return imMsgMapper.insertMsg(insertList);
    }
}
