package com.idata365.app.serviceV2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.DicComponentConstant;
import com.idata365.app.entity.v2.BoxTreasureFamily;
import com.idata365.app.entity.v2.BoxTreasureUser;
import com.idata365.app.entity.v2.ComponentFamily;
import com.idata365.app.entity.v2.ComponentUser;
import com.idata365.app.entity.v2.DicComponent;
import com.idata365.app.mapper.BoxTreasureMapper;
import com.idata365.app.mapper.ComponentMapper;
import com.idata365.app.service.BaseService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.RandUtils;

/**
 * 
 * @ClassName: CarService
 * @Description: TODO(车辆业务)
 * @author LanYeYe
 * @date 2018年4月27日
 *
 */
@Service
public class BoxTreasureService extends BaseService<BoxTreasureService> {
	private final static Logger LOG = LoggerFactory.getLogger(BoxTreasureService.class);
	@Autowired
	BoxTreasureMapper boxTreasureMapper;
	@Autowired
	ComponentMapper componentMapper;
	/**
	 * 随机生成道具配件
	 * @return
	 */
    public DicComponent getRandComponentId(){
    	int rand=RandUtils.generateRand(1, 100);
    	Integer componentId=0;
    	if(rand>99){//100 1%
    		componentId=DicComponentConstant.XUDIANCHI_S;
    	}else if(rand>95){//96-99 4%
    		componentId=DicComponentConstant.XUDIANCHI_A;
    	}else if(rand>80){//81-95 15%
    		componentId=DicComponentConstant.XUDIANCHI_B;
    	}else if(rand>79){//80 1%
    		componentId=DicComponentConstant.SHACHEPIAN_S;
    	}else if(rand>75){//76-79 4%
    		componentId=DicComponentConstant.SHACHEPIAN_A;
    	}else if(rand>60){//61-75  15%
    		componentId=DicComponentConstant.SHACHEPIAN_B;
    	}else if(rand>59){//60  1%
    		componentId=DicComponentConstant.JIYOU_S;
    	}else if(rand>55){//56-59  4%
    		componentId=DicComponentConstant.JIYOU_A;
    	}else if(rand>40){//41-55  15%
    		componentId=DicComponentConstant.JIYOU_B;
    	}else if(rand>39){//40  1%
    		componentId=DicComponentConstant.HUOHUASAI_S;
    	}else if(rand>35){//36-39  4%
    		componentId=DicComponentConstant.HUOHUASAI_A;
    	}else if(rand>20){//21-35  15%
    		componentId=DicComponentConstant.HUOHUASAI_B;
    	}else if(rand>19){//20  1%
    		componentId=DicComponentConstant.LUNTAI_S;
    	}else if(rand>15){//16-19  4%
    		componentId=DicComponentConstant.LUNTAI_A;
    	}else if(rand>0){//1-15  15%
    		componentId=DicComponentConstant.LUNTAI_B;
    	}
    	return DicComponentConstant.getDicComponent(componentId);
    }
    
   public Map<String,Object> getTripBox(long userId){
	   Map<String,Object> rtMap=new HashMap<>();
	   Map<String,Object> paramMap=new HashMap<>();
	   paramMap.put("userId", userId);
	   paramMap.put("gainType", 1);
	   paramMap.put("createTime", DateTools.getCurDateAddMinute(24*60));
	   
	   List<BoxTreasureUser> boxIds=boxTreasureMapper.getTripBoxIds(paramMap);
	   List<Map<String,Object>> boxList=new ArrayList<>();
	   if(boxIds==null || boxIds.size()==0){
		  
	   }else{
		  
		   for(BoxTreasureUser box:boxIds){
			   Map<String,Object> m1=new HashMap<>();
			   m1.put("boxId", box.getBoxId());
			   m1.put("travelId", String.valueOf(box.getTravelId()));
			   List<BoxTreasureUser> treasures=  boxTreasureMapper.getTripsByBoxId(box.getBoxId());
			   List<Map<String,Object>> componentList=new ArrayList<>();
			   for(BoxTreasureUser treasure:treasures){
				   Map<String,Object> m2=new HashMap<>();
				   m2.put("componentId", String.valueOf(treasure.getComponentId()));
				   DicComponent component=DicComponentConstant.getDicComponent(treasure.getComponentId());
				   m2.put("componentName",  component.getComponentValue());
				   m2.put("quality", component.getQuality());
				   m2.put("imgUrl", component.getComponentUrl());
				   m2.put("componentNum", String.valueOf(treasure.getComponentNum()));
				   componentList.add(m2);
			   }
			   m1.put("componentList", componentList);
			   boxList.add(m1);
		   }
	   }
	   rtMap.put("boxList",  boxList) ;
	   return rtMap;
   }
   
   
   public Map<String,Object> getChallengeBox(long familyId){
	   Map<String,Object> rtMap=new HashMap<>();
	   Map<String,Object> paramMap=new HashMap<>();
	   paramMap.put("familyId", familyId);
	   paramMap.put("gainType", 1);
	   paramMap.put("daystamp", DateTools.getYYYYMMDD());
	   int hadbox= boxTreasureMapper.hadChallengeBoxIds(paramMap);
	   if(hadbox>0){
		   rtMap.put("hadBox",  "1") ; 
	   }else{
		   rtMap.put("hadBox",  "0") ; 
	   }
	   List<BoxTreasureFamily> boxIds=boxTreasureMapper.getChallengeBoxIds(paramMap);
	   List<Map<String,Object>> boxList=new ArrayList<>();
	   if(boxIds==null || boxIds.size()==0){
		  
	   }else{
		   for(BoxTreasureFamily box:boxIds){
			   Map<String,Object> m1=new HashMap<>();
			   m1.put("boxId", box.getBoxId());
			   List<BoxTreasureFamily> treasures=  boxTreasureMapper.getFamilyTreasureByBoxId(box.getBoxId());
			   List<Map<String,Object>> componentList=new ArrayList<>();
			   for(BoxTreasureFamily treasure:treasures){
				   Map<String,Object> m2=new HashMap<>();
				   m2.put("componentId", String.valueOf(treasure.getComponentId()));
				   DicComponent component=DicComponentConstant.getDicComponent(treasure.getComponentId());
				   m2.put("componentName",  component.getComponentValue());
				   m2.put("quality", component.getQuality());
				   m2.put("imgUrl", component.getComponentUrl());
				   m2.put("componentNum", String.valueOf(treasure.getComponentNum()));
				   componentList.add(m2);
			   }
			   m1.put("componentList", componentList);
			   boxList.add(m1);
		   }
	   }
	   rtMap.put("boxList",  boxList) ;
	   return rtMap;
   }
   @Transactional
   public boolean receiveUserBox(String boxId,Long userId){
	   Map<String,Object> paramMap=new HashMap<>();
	   paramMap.put("userId", userId);
	   int freeCount=componentMapper.countFreeCabinet(paramMap);
	   if(freeCount==0){
		   LOG.info("道具箱已满");
		   return false;
//		   throw new RuntimeException("满柜了");
	   }
	   List<BoxTreasureUser> treasures=  boxTreasureMapper.getTripsByBoxId(boxId);
	   for(BoxTreasureUser treasure:treasures){
		   //插入用户零件库
		   DicComponent component=DicComponentConstant.getDicComponent(treasure.getComponentId());
		   //查看是否满柜
		   ComponentUser cmpUser=new ComponentUser();
		   cmpUser.setComponentId(component.getComponentId());
		   cmpUser.setGainType(treasure.getGainType());
		   cmpUser.setLeftTravelNum(component.getTravelNum());
		   cmpUser.setComponentType(component.getComponentType());
		   componentMapper.insertComponentUser(cmpUser);
	   }
	   //更新领取记录
	   boxTreasureMapper.receiveBox(boxId);
	   
	   return true;
   }
   @Transactional
   public boolean receiveFamilyBox(String boxId,Long familyId){
	   Map<String,Object> paramMap=new HashMap<>();
	   paramMap.put("familyId", familyId);
	   List<BoxTreasureFamily> treasures=  boxTreasureMapper.getFamilyTreasureByBoxId(boxId);
	 //查看是否满柜
	   int freeCount=componentMapper.countFamilyFreeCabinet(paramMap);
	   if(freeCount==0){
		   LOG.info("道具箱已满");
		   return false;
//		   throw new RuntimeException("满柜了");
	   }
	   for(BoxTreasureFamily treasure:treasures){
		   //插入用户零件库
		   DicComponent component=DicComponentConstant.getDicComponent(treasure.getComponentId());
		   ComponentFamily cmpFamily=new ComponentFamily();
		   cmpFamily.setComponentId(component.getComponentId());
		   cmpFamily.setGainType(treasure.getGainType());
		   cmpFamily.setComponentType(component.getComponentType());
		   componentMapper.insertComponentFamily(cmpFamily);
	   }
	   //更新领取记录
	   boxTreasureMapper.receiveBox(boxId);
	   
	   return true;
   }  
}
