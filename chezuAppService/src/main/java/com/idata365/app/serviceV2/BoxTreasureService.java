package com.idata365.app.serviceV2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
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

   @Transactional
   public Map<String,Object> getTripBox(long userId){
	   Map<String,Object> rtMap=new HashMap<>();
	   Map<String,Object> paramMap=new HashMap<>();
	   paramMap.put("userId", userId);
	   paramMap.put("gainType", 1);
	   paramMap.put("createTime", DateTools.getCurDateAddMinute(-3*60));//宝箱保留3个小时
	   
	   List<BoxTreasureUser> boxIds=boxTreasureMapper.getTripBoxIds(paramMap);
	   List<Map<String,Object>> boxList=new ArrayList<>();
	   int count = 0;
	   if(boxIds==null || boxIds.size()==0){
		  
	   }else{
		   
		  a: for(BoxTreasureUser box:boxIds){
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
				   count++;
			   }
			   m1.put("componentList", componentList);
			   boxList.add(m1);
			   if(count == 5) {
				   break a;
			   }
		   }
	   }
	   rtMap.put("boxList",  boxList) ;
	   return rtMap;
   }

   @Transactional
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
//	   List<BoxTreasureFamily> boxIds=boxTreasureMapper.getChallengeBoxIds(paramMap);
//	   List<Map<String,Object>> boxList=new ArrayList<>();
//	   if(boxIds==null || boxIds.size()==0){
//		  
//	   }else{
//		   for(BoxTreasureFamily box:boxIds){
//			   Map<String,Object> m1=new HashMap<>();
//			   m1.put("boxId", box.getBoxId());
//			   List<BoxTreasureFamily> treasures=  boxTreasureMapper.getFamilyTreasureByBoxId(box.getBoxId());
//			   List<Map<String,Object>> componentList=new ArrayList<>();
//			   for(BoxTreasureFamily treasure:treasures){
//				   Map<String,Object> m2=new HashMap<>();
//				   m2.put("componentId", String.valueOf(treasure.getComponentId()));
//				   DicComponent component=DicComponentConstant.getDicComponent(treasure.getComponentId());
//				   m2.put("componentName",  component.getComponentValue());
//				   m2.put("quality", component.getQuality());
//				   m2.put("imgUrl", component.getComponentUrl());
//				   m2.put("componentNum", String.valueOf(treasure.getComponentNum()));
//				   componentList.add(m2);
//			   }
//			   m1.put("componentList", componentList);
//			   boxList.add(m1);
//		   }
//	   }
	   
		List<BoxTreasureFamily> boxIds = boxTreasureMapper.getChallengeBoxIdsNew(paramMap);
		List<Map<String, Object>> boxList = new ArrayList<>();
		if (boxIds == null || boxIds.size() == 0) {

		} else {
			for (BoxTreasureFamily box : boxIds) {
				Map<String, Object> m1 = new HashMap<>();
				m1.put("boxId", box.getBoxId());
				m1.put("componentId", String.valueOf(box.getComponentId()));
				DicComponent component = DicComponentConstant.getDicComponent(box.getComponentId());
				m1.put("componentName", component.getComponentValue());
				m1.put("quality", component.getQuality());
				m1.put("imgUrl", component.getComponentUrl());
				m1.put("componentNum", String.valueOf(box.getComponentNum()));
				boxList.add(m1);
			}
		}
		
		
		rtMap.put("boxList", boxList);
		return rtMap;
	}


	@Transactional
	public int receiveUserBox(String boxId, Long userId) {
		int flag = 1;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		int freeCount = componentMapper.countFreeCabinet(paramMap);
		if (freeCount == 0) {
			LOG.info("道具箱已满");
			flag = 0;
			return flag;
//		   throw new RuntimeException("满柜了");
		}

		//更新领取记录
		int i = boxTreasureMapper.receiveBox(boxId);
		if (i == 0) {
			flag = -1;
		}else{
			List<BoxTreasureUser> treasures = boxTreasureMapper.getTripsByBoxId(boxId);
			for (BoxTreasureUser treasure : treasures) {
				//插入用户零件库
				DicComponent component = DicComponentConstant.getDicComponent(treasure.getComponentId());
				//查看是否满柜
				ComponentUser cmpUser = new ComponentUser();
				cmpUser.setComponentId(component.getComponentId());
				cmpUser.setGainType(treasure.getGainType());
				cmpUser.setLeftTravelNum(component.getTravelNum());
				cmpUser.setComponentType(component.getComponentType());
				cmpUser.setUserId(userId);
				cmpUser.setInUse(0);
				cmpUser.setComponentStatus(1);
				cmpUser.setHadLooked(0);
				componentMapper.insertComponentUser(cmpUser);
			}
		}

		return flag;
	}

	@Transactional
	public int receiveFamilyBox(String boxId, Long familyId) {
		int flag = 1;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("familyId", familyId);
		List<BoxTreasureFamily> treasures = boxTreasureMapper.getFamilyTreasureByBoxId(boxId);
		//查看是否满柜
		int freeCount = componentMapper.countFamilyFreeCabinet(paramMap);
		if (freeCount == 0) {
			LOG.info("道具箱已满");
			flag=0;
			return flag;
//		   throw new RuntimeException("满柜了");
		}

		//更新领取记录
		int i = boxTreasureMapper.receiveBoxFamily(boxId);
		if (i == 0) {
			flag = -1;
		} else {
			for (BoxTreasureFamily treasure : treasures) {
				//插入用户零件库
				DicComponent component = DicComponentConstant.getDicComponent(treasure.getComponentId());
				ComponentFamily cmpFamily = new ComponentFamily();
				cmpFamily.setComponentId(component.getComponentId());
				cmpFamily.setGainType(treasure.getGainType());
				cmpFamily.setComponentType(component.getComponentType());
				cmpFamily.setFamilyId(familyId);
				cmpFamily.setComponentStatus(1);
				cmpFamily.setEffectId(0L);
				componentMapper.insertComponentFamily(cmpFamily);
			}
		}

		return flag;
	}
   
	@Transactional
	public boolean receiveUserBoxBySys(Long userId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		int freeCount = componentMapper.countFreeCabinet(paramMap);
		if (freeCount == 0) {
			LOG.info("道具箱已满");
			return false;
			// throw new RuntimeException("满柜了");
		}
		// 插入用户零件库
		int r[] = { 10, 11, 12, 20, 21, 22, 30, 31, 32, 40, 41, 42, 50, 51, 52 };
		DicComponent component = DicComponentConstant.getDicComponent(r[RandUtils.generateRand(0, r.length - 1)]);
		// 查看是否满柜
		ComponentUser cmpUser = new ComponentUser();
		cmpUser.setComponentId(component.getComponentId());
		cmpUser.setGainType(5);
		cmpUser.setLeftTravelNum(component.getTravelNum());
		cmpUser.setComponentType(component.getComponentType());
		cmpUser.setUserId(userId);
		cmpUser.setInUse(0);
		cmpUser.setComponentStatus(1);
		cmpUser.setHadLooked(0);
		componentMapper.insertComponentUser(cmpUser);

		return true;
	}
   
   public static void main(String[] args) {
	   
   }
}
