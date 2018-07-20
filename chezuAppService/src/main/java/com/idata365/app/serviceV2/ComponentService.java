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
import com.idata365.app.entity.v2.ComponentUser;
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
	   
	   
	   
	   public Map<String,Object> getFamilyComponent(long userId){
		   Map<String,Object> rtMap=new HashMap<>();
		   List<Map<String,Object>> componentList=new ArrayList<>();
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
				   componentList.add(m1);
			   }
		   }
		   rtMap.put("componentList",  componentList) ;

		   return rtMap;
	   }
}
