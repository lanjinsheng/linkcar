package com.idata365.app.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.service.DicService;
import com.idata365.app.service.SpringContextUtil;

public class DicFamilyTypeConstant {
	public static Map<Integer,DicFamilyType> familyTypeMap=new HashMap<Integer,DicFamilyType>();
 
	
	public static DicFamilyType getDicFamilyType(Integer familyType){
		if(familyTypeMap.size()==0){
			DicService dicService=SpringContextUtil.getBean("dicService", DicService.class);
			List<DicFamilyType> list=dicService.getDicFamilyType();
			for(DicFamilyType dic:list){
				familyTypeMap.put(dic.getFamilyType(), dic);
			}
		}
		return familyTypeMap.get(familyType);
			
	}
}
