package com.idata365.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.constant.DicComponentConstant;
import com.idata365.constant.DicFamilyTypeConstant;
import com.idata365.entity.BoxTreasureFamily;
import com.idata365.entity.BoxTreasureUser;
import com.idata365.entity.DicComponent;
import com.idata365.entity.DicFamilyType;
import com.idata365.mapper.app.BoxTreasureMapper;
import com.idata365.util.DateTools;
import com.idata365.util.RandUtils;


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
    
   public int insertTripBoxNotran(Long travelId,Long userId) {
	   BoxTreasureUser boxTreasureUser=new BoxTreasureUser();
	   DicComponent component=getRandComponentId();
	   String boxId=UUID.randomUUID().toString().replace("-", "");
	   boxTreasureUser.setBoxId(boxId);
	   boxTreasureUser.setComponentId(component.getComponentId());
	   boxTreasureUser.setComponentNum(1);
	   boxTreasureUser.setGainType(1);
	   boxTreasureUser.setToUserId(userId);
	   boxTreasureUser.setTravelId(travelId);
	   return boxTreasureMapper.insertBoxTreasureUser(boxTreasureUser);
   }
   public void addBoxTrasureFamily(List<BoxTreasureFamily>  list,DicComponent comp, BoxTreasureFamily box) {
	
		  box.setComponentId(comp.getComponentId());
		  box.setComponentNum(1);
		  box.setGainType(1);
		  list.add(box);
   }
   public int insertChallengeBoxNoTran(Long familyId,int familyType,int familyNum) {
	   
	   List<BoxTreasureFamily> list=new ArrayList<>();
	   int level=DicFamilyTypeConstant.getFamilyLevel(familyType);
	   int componentNum=1;
		int r=RandUtils.generateRand(1, 100);
	   String boxId=UUID.randomUUID().toString().replace("-", "");
	   String daystamp=DateTools.getYYYYMMDD();
	   if(level==1) {//冠军
		  int add=RandUtils.generateRand(1, 3);
		  componentNum=familyNum+add;
		  for(int i=0;i<componentNum;i++) {
			  int r2=RandUtils.generateRand(1, 5);
			  DicComponent comp=null;
			  if(r<=20) {//S
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapS.get(r2));
			  }else if(r>20 && r<=80) {//A
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapA.get(r2));
				  
			  }else {//B
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapB.get(r2));
			  }
			     BoxTreasureFamily box=new BoxTreasureFamily(daystamp,boxId,familyId);
			     addBoxTrasureFamily(list,comp,box);
		  }
	   }else if(level==2) {//钻石
		   int add=RandUtils.generateRand(1, 2);
		   componentNum=familyNum+add;
		   for(int i=0;i<componentNum;i++) {
			   int r2=RandUtils.generateRand(1, 5);
			   DicComponent comp=null;
			   if(r<=15) {//S
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapS.get(r2));
			   }else if(r>15 && r<=65) {//A
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapA.get(r2));
				  
			   }else {//B
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapB.get(r2));
			   }
			     BoxTreasureFamily box=new BoxTreasureFamily(daystamp,boxId,familyId);
			     addBoxTrasureFamily(list,comp,box);
		   }
	   }else if(level==3) {//黄金
		   int add=RandUtils.generateRand(0, 2);
		   componentNum=familyNum+add;
		   for(int i=0;i<componentNum;i++) {
			   int r2=RandUtils.generateRand(1, 5);
			   DicComponent comp=null;
			   if(r<=10) {//S
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapS.get(r2));
			   }else if(r>10 && r<=50) {//A
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapA.get(r2));
				  
			   }else {//B
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapB.get(r2));
			   }
			     BoxTreasureFamily box=new BoxTreasureFamily(daystamp,boxId,familyId);
			     addBoxTrasureFamily(list,comp,box);
		   }
	   }else if(level==4) {//白银
		   int add=RandUtils.generateRand(0, 1);
		   componentNum=familyNum+add;
		   for(int i=0;i<componentNum;i++) {
			   int r2=RandUtils.generateRand(1, 5);
			   DicComponent comp=null;
			   if(r<=10) {//S
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapS.get(r2));
			   }else if(r>10 && r<=40) {//A
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapA.get(r2));
				  
			   }else {//B
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapB.get(r2));
			   }
			     BoxTreasureFamily box=new BoxTreasureFamily(daystamp,boxId,familyId);
			     addBoxTrasureFamily(list,comp,box);
		   }
	   }else if(level==5) {//白银
		   int add=RandUtils.generateRand(-1, 1);
		   componentNum=familyNum+add;
		   if(componentNum<1) {
			   componentNum=1;
		   }
		   for(int i=0;i<componentNum;i++) {
			   int r2=RandUtils.generateRand(1, 5);
			   DicComponent comp=null;
			   if(r<=30) {//A
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapA.get(r2));
			   }else {//B
				   comp=DicComponentConstant.getDicComponent(DicComponentConstant.dicComponentMapB.get(r2));
			   }
			     BoxTreasureFamily box=new BoxTreasureFamily(daystamp,boxId,familyId);
			     addBoxTrasureFamily(list,comp,box);
		   }
	   }
	   boxTreasureMapper.insertBoxTreasureFamily(list);
	   return 0;
   }
   public static void main(String []args) {
	   System.out.println(UUID.randomUUID().toString().replace("-", ""));
   }
}
