package com.idata365.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.ImMsg;
import com.idata365.app.entity.ImNotify;
import com.idata365.app.mapper.ImMsgMapper;
import com.idata365.app.mapper.ImNotifyMapper;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.StringTools;

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
//    	ImNotify imNotify=imNotifyMapper.getNotify(familyId);
//    	if(imNotify==null) {
//    		rtMap.put("imNotify", new ImNotify());
//    	}else {
//    		rtMap.put("imNotify", imNotify);
//    	}
//    	ImMsg msg=new ImMsg();
//    	msg.setId(msgId);
//    	msg.setFamilyId(familyId);
//    	List<ImMsg> imMsgs=imMsgMapper.getMsg(msg);
//    	
//    	if(imMsgs==null || imMsgs.size()==0) {
//    		rtMap.put("imMsgs", new ArrayList());
//    	}else {
//    		rtMap.put("imMsgs", imMsgs);
//    	}
//    	
    	return rtMap;
    }
    
    public Map<String,Object> getImMsgs(Long familyId,Long userId,Long msgId,int isHistory,String basePath){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	Map<String,Object> upMap=new HashMap<String,Object>();
    	Map<String,Object> searchMap=new HashMap<String,Object>();
    	
    	searchMap.put("id", msgId);
    	searchMap.put("toUserId", userId);
    	searchMap.put("isHistory", isHistory);
    	searchMap.put("familyId", familyId);
//    	ImMsg msg=new ImMsg();
//    	msg.setId(msgId);
//    	msg.setToUserId(userId);
//    	msg.setFamilyId(familyId);
//    	msg.setFromUserPic(basePath);
    	List<ImMsg> imMsgs=imMsgMapper.getMsg(searchMap);
    	
    	if(imMsgs==null || imMsgs.size()==0) {
    		rtMap.put("imMsgs", new ArrayList());
    		
    	}else {
    		for(ImMsg imMsg:imMsgs) {
    			if(imMsg.getNickName()==null) {
    				imMsg.setNickName(StringTools.getPhoneHidden(imMsg.getPhone()));
    			}
    			imMsg.setFromUserPic(basePath+imMsg.getFromUserPic());
    		}
    		upMap.put("familyId", familyId);
        	upMap.put("toUserId", userId);
        	if(isHistory==1) {
        	  	upMap.put("beginId", imMsgs.get(imMsgs.size()-1).getId());
	    		upMap.put("endId", msgId);
        	}else {
	        	upMap.put("beginId", msgId);
	    		upMap.put("endId", imMsgs.get(0).getId());
        	}
    		imMsgMapper.updateMsg(upMap);
    		rtMap.put("imMsgs", imMsgs);
    	}
    	return rtMap;
    }
    public  ImMsg  getMsgById( Long id) {
    	return imMsgMapper.getMsgById(id);
    }
    public Map<String,Object> getImNotify(Long familyId,Long userId,String basePath){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	ImNotify imNotify=imNotifyMapper.getNotify(familyId);
    	if(imNotify==null) {
    		//获取俱乐部信息
    		Map<String,Object> leaderInfo=familyService.findLeaderByFamilyId(familyId);
    		imNotify=new ImNotify();
    		imNotify.setFamilyName(String.valueOf(leaderInfo.get("familyName")));
    		if(leaderInfo.get("nickName")==null) {
    			imNotify.setLeaderName(StringTools.getPhoneHidden(String.valueOf(leaderInfo.get("phone"))));
    		}else {
    			imNotify.setLeaderName(String.valueOf(leaderInfo.get("nickName")));
    		}
    		imNotify.setLeaderId(Long.valueOf(leaderInfo.get("id").toString()));
    		imNotify.setLeaderPic(basePath+String.valueOf(leaderInfo.get("imgUrl")));
    		imNotify.setFamilyId(familyId);
    		imNotify.setCreateTime(new Date());
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
        		msg.setAtUsers(imMsg.getAtUsers());
        		msg.setIsRead(0);
        		insertList.add(msg);
    		}
    	
    	}
    	
    	return imMsgMapper.insertMsg(insertList);
    }
    public static void main(String []args) {
    }
}
