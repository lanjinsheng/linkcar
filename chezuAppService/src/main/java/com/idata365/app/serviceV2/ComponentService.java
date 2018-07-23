package com.idata365.app.serviceV2;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.DicCarConstant;
import com.idata365.app.constant.DicComponentConstant;
import com.idata365.app.entity.CarListResultBean;
import com.idata365.app.entity.Carpool;
import com.idata365.app.entity.CarpoolApprove;
import com.idata365.app.entity.DicCar;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.UserCar;
import com.idata365.app.entity.UserCarLogs;
import com.idata365.app.entity.v2.ComponentFamily;
import com.idata365.app.entity.v2.ComponentGiveLog;
import com.idata365.app.entity.v2.ComponentUser;
import com.idata365.app.entity.v2.ComponentUserUseLog;
import com.idata365.app.entity.v2.DicComponent;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.CarpoolApproveMapper;
import com.idata365.app.mapper.CarpoolMapper;
import com.idata365.app.mapper.ComponentMapper;
import com.idata365.app.mapper.DicCarMapper;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.InteractPeccancyMapper;
import com.idata365.app.mapper.UserCarLogsMapper;
import com.idata365.app.mapper.UserCarMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.service.BaseService;
import com.idata365.app.service.MessageService;
import com.idata365.app.util.DateTools;

/**
 * 
 * @ClassName: CarService
 * @Description: TODO(车辆业务)
 * @author LanYeYe
 * @date 2018年4月27日
 *
 */
@Service
public class ComponentService extends BaseService<ComponentService> {
	private final static Logger LOG = LoggerFactory.getLogger(ComponentService.class);
	@Autowired
	ComponentMapper componentMapper;
	@Autowired
	FamilyMapper familyMapper;
	
	   public Map<String,Object> getUserComponent(long userId){
		   Map<String,Object> rtMap=new HashMap<>();
		   Map<String,List<Map<String,Object>>> paramMap=new HashMap<>();
		   List<Map<String,Object>> componentList=new ArrayList<>();
		   List<Map<String,Object>> componentLT=new ArrayList<>();
		   List<Map<String,Object>> componentJY=new ArrayList<>();
		   List<Map<String,Object>> componentHHS=new ArrayList<>();
		   List<Map<String,Object>> componentSCP=new ArrayList<>();
		   List<Map<String,Object>> componentXDC=new ArrayList<>();
		   paramMap.put("1", componentLT);
		   paramMap.put("2", componentHHS); 
		   paramMap.put("3", componentJY); 
		   paramMap.put("4", componentSCP);
		   paramMap.put("5", componentXDC);
		   List<ComponentUser> components=componentMapper.getFreeComponentUser(userId);
		   
		   if(components==null || components.size()==0){
			  
		   }else{
			   for(ComponentUser component:components){
				   Map<String,Object> m1=new HashMap<>();
				   m1.put("userComponentId", String.valueOf(component.getId()));
				   DicComponent dicComponent=DicComponentConstant.getDicComponent(component.getComponentId());
				   m1.put("componentName", dicComponent.getComponentValue());
				   m1.put("quality", dicComponent.getQuality());
				   m1.put("imgUrl", dicComponent.getComponentUrl());
				   m1.put("componentNum","1");
				   m1.put("componentType", dicComponent.getComponentType());
				   m1.put("componentDesc",dicComponent.getComponentDesc());
				   m1.put("componentAttribute","动力加成"+(int)(dicComponent.getPowerAddition()*100)+"%");
				   m1.put("componentLoss", dicComponent.getTravelNum()+"次行程");
				   paramMap.get(String.valueOf(dicComponent.getComponentType())).add(m1);
				   componentList.add(m1);
			   }
		   }
		   rtMap.put("componentList",  componentList) ;
		   rtMap.put("componentLT",  componentLT) ;
		   rtMap.put("componentJY",  componentJY) ;
		   rtMap.put("componentHHS",  componentJY) ;
		   rtMap.put("componentSCP",  componentJY) ;
		   rtMap.put("componentXDC",  componentJY) ;
		   return rtMap;
	   }
	   
	   
	   
	   public Map<String,Object> getFamilyComponent(long familyId){
		   Map<String,Object> rtMap=new HashMap<>();
		   List<Map<String,Object>> componentList=new ArrayList<>();
		   List<ComponentFamily> components=componentMapper.getFreeComponentFamily(familyId);
		   
		   if(components==null || components.size()==0){
			  
		   }else{
			   for(ComponentFamily component:components){
				   Map<String,Object> m1=new HashMap<>();
				   m1.put("familyComponentId", String.valueOf(component.getId()));
				   DicComponent dicComponent=DicComponentConstant.getDicComponent(component.getComponentId());
				   m1.put("componentName", dicComponent.getComponentValue());
				   m1.put("quality", dicComponent.getQuality());
				   m1.put("imgUrl", dicComponent.getComponentUrl());
				   m1.put("componentNum","1");
				   m1.put("componentType", dicComponent.getComponentType());
				   m1.put("componentDesc",dicComponent.getComponentDesc());
				   m1.put("componentAttribute","动力加成"+(int)(dicComponent.getPowerAddition()*100)+"%");
				   m1.put("componentLoss", dicComponent.getTravelNum()+"次行程");
				   componentList.add(m1);
			   }
		   }
		   rtMap.put("componentList",  componentList) ;

		   return rtMap;
	   }
	   public Map<String,Object> dropFamilyComponent(long familyComponentId){ 
		   Map<String,Object> rtMap=new HashMap<>();
		   int dropCount=componentMapper.dropFamilyComponent(familyComponentId);
		   if(dropCount==0){
			   return null;
		   }
		   return rtMap;
	   }
	   
	   //家族零件分配提交
	   @Transactional
	   public int submitComponentDistribute(long familyComponentId, long familyId,long toUserId,long userId){
		   ComponentFamily cf=componentMapper.getFamilyComponent(familyComponentId);
		   ComponentGiveLog  log=new ComponentGiveLog();
		   log.setComponentId(cf.getComponentId());
		   log.setFromComponentId(familyComponentId);
		   log.setFromId(cf.getFamilyId());
		   log.setGiveStatus(1);
		   log.setLogType(1);
		   log.setOperationManId(userId);
		   log.setToUserId(toUserId);
		   log.setUniKey(log.getFromId()+"-"+log.getLogType()+"-"+log.getToUserId()+DateTools.getYYYYMMDD());
		   componentMapper.insertComponentGiveLog(log);
		   Map<String,Object> updateMap=new HashMap<>();
		   updateMap.put("componentStatus", 3);
		   updateMap.put("familyComponentId", familyComponentId);
		   int update=componentMapper.updateFamilyComponent(updateMap);
		   return update;
	   }
	   
	   
	  //分配列表 
	  public Map<String,Object> getComponentDistribute(long userId,long familyId){
		   Map<String,Object> rtMap=new HashMap<>();
				List<Map<String,Object>> users=familyMapper.getFamilyUsersMoreInfo(familyId);
				Map<Long,Object> keyMap=new HashMap<>();
				List<Map<String,Object>> list=new ArrayList<>();
				Map<String,Object> paramMap=new HashMap<>();
				paramMap.put("familyId", familyId);
				paramMap.put("logType", 1);
				paramMap.put("eventTime", DateTools.getYYYYMMDD()+" 00:00:00");
				List<ComponentGiveLog> logs=componentMapper.getComponentGiveLog(paramMap);
				for(ComponentGiveLog log:logs){
					keyMap.put(log.getToUserId(), log);
				}
				
				for(Map<String,Object> user:users){//成员循环
					Map<String,Object> m=new HashMap<>();
					Long memberId=Long.valueOf(user.get("userId").toString());
					if(userId==memberId.longValue()){
						continue;//自己忽略
					}
					m.put("userId",String.valueOf(memberId));
					m.put("nick",String.valueOf(user.get("nickName")));
				    if(keyMap.get(memberId)==null){
				    	m.put("numerator","0");
						m.put("denominator","1");
						m.put("authType","1");
						
				    }else{
				    	m.put("numerator","1");
						m.put("denominator","1");
						m.put("authType","0");
				    }
				    list.add(m);
				
				}
				rtMap.put("members", list);
		   return rtMap;
	  }
	  
	  
	  //分配列表 
	  public Map<String,Object> getComponentGiveLog(long componentGiveLogId){
		   Map<String,Object> rtMap=new HashMap<>();
		   ComponentGiveLog log=  componentMapper.findComponentGiveLog(componentGiveLogId);
		   if(log.getGiveStatus().intValue()==2){
			   rtMap.put("hadRecieved",String.valueOf(1) );
		   }else{
			   rtMap.put("hadRecieved",String.valueOf(0) );
		   }
		   rtMap.put("logType",String.valueOf(log.getLogType()));
		   DicComponent dicComponent=DicComponentConstant.getDicComponent(log.getComponentId());
		   rtMap.put("componentName",dicComponent.getComponentValue());
		   rtMap.put("quality",dicComponent.getComponentValue());
		   rtMap.put("imgUrl", dicComponent.getComponentUrl());
		   rtMap.put("componentType", dicComponent.getComponentType());
		   rtMap.put("componentDesc",dicComponent.getComponentDesc());
		   rtMap.put("componentAttribute","动力加成"+(int)(dicComponent.getPowerAddition()*100)+"%");
		   rtMap.put("componentLoss", dicComponent.getTravelNum()+"次行程");
		   return rtMap;
	  }
	  
	  
	  @Transactional
	  public Map<String,Object> recieveGiveLog(long componentGiveLogId){
		   Map<String,Object> rtMap=new HashMap<>();
		   ComponentGiveLog log=  componentMapper.findComponentGiveLog(componentGiveLogId);
		   Map<String,Object> paramMap=new HashMap<>();
		   paramMap.put("userId", log.getToUserId());
		   int freeCount=componentMapper.countFreeCabinet(paramMap);
		   if(freeCount<=0){
			   return null;
		   }
		   ComponentUser componentUser=new ComponentUser();
		   if(log.getLogType().intValue()==1){//家族赠送
			   
			   componentUser.setGainType(3);
		   }else{//个人赠送
			   componentUser.setGainType(2);
		   }
		   componentUser.setComponentId(log.getComponentId());
		   componentUser.setComponentStatus(1);
		   DicComponent dicComponent=DicComponentConstant.getDicComponent(log.getComponentId());
		   componentUser.setComponentType(dicComponent.getComponentType());
		  
		   componentUser.setInUse(0);
		   componentUser.setLeftTravelNum(dicComponent.getTravelNum());
		   componentUser.setUserId(log.getToUserId());
		   //插入道具
		   componentMapper.insertComponentUser(componentUser);
		   //更新ComponentGiveLog
		   componentMapper.recieveComponentGiveLog(componentGiveLogId);
		   return rtMap;
	  }
	  
	  @Transactional
	  public Map<String,Object> deployComponent(long userComponentId,long userCarId,long destroyComponentId){
		  ComponentUser componentUser=componentMapper.getComponentUser(userComponentId);
		  //插入componentUserUseLog
		  ComponentUserUseLog log=new ComponentUserUseLog();
		  log.setUserCarId(userCarId);
		  log.setComponentId(componentUser.getComponentId());
		  log.setEventType(1);
		  log.setUserId(componentUser.getUserId());
		  log.setUserComponentId(userComponentId);
		  componentMapper.insertComponentUserUseLog(log);
		  
		  //更新零件
		  Map<String,Object> userCompUpdate=new HashMap<>();
		  userCompUpdate.put("inUse", 1);
		  userCompUpdate.put("componentStatus", 1);
		  userCompUpdate.put("userComponentId", userComponentId);
		  componentMapper.updateUserComponent(userCompUpdate);
		  
		  //老的销毁
		  if(destroyComponentId>0){
			  ComponentUser componentUser2=componentMapper.getComponentUser(destroyComponentId);
			  //插入componentUserUseLog
			  ComponentUserUseLog log2=new ComponentUserUseLog();
			  log2.setUserCarId(userCarId);
			  log2.setComponentId(componentUser2.getComponentId());
			  log2.setEventType(2);
			  log2.setUserId(componentUser2.getUserId());
			  log2.setUserComponentId(userComponentId);
			  componentMapper.insertComponentUserUseLog(log2);
			  
			  //更新零件
			  Map<String,Object> userCompUpdate2=new HashMap<>();
			  userCompUpdate2.put("inUse", 0);
			  userCompUpdate2.put("componentStatus", 2);
			  userCompUpdate2.put("userComponentId", destroyComponentId);
			  componentMapper.updateUserComponent(userCompUpdate2);
		  }
		  return null;
	  }
	  
	  
	  
}
