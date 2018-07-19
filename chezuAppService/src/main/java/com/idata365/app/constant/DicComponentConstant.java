package com.idata365.app.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.app.entity.DicCar;
import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.entity.v2.DicComponent;
import com.idata365.app.service.DicService;
import com.idata365.app.service.SpringContextUtil;

public class DicComponentConstant {
	public static Map<Integer,DicComponent> dicComponentMap=new HashMap<Integer,DicComponent>();
  
    /**
     * 
     * @param carId
     * @return
     */
	public static DicComponent getDicComponent(Integer componentId){
		if(dicComponentMap.size()==0){
			DicService dicService=SpringContextUtil.getBean("dicService", DicService.class);
			List<DicComponent> list=dicService.getDicComponent();
			for(DicComponent dic:list){
				dicComponentMap.put(dic.getComponentId(), dic);
			}
		}
		return dicComponentMap.get(componentId);
			
	}
}
