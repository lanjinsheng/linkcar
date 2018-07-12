package com.idata365.app.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.app.entity.DicCar;
import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.service.DicService;
import com.idata365.app.service.SpringContextUtil;

public class DicCarConstant {
	public static Map<Integer,DicCar> dicCarMap=new HashMap<Integer,DicCar>();
    public  final static Integer CARID1=1;
  
    /**
     * 
     * @param carId
     * @return
     */
	public static DicCar getDicCar(Integer carId){
		if(dicCarMap.size()==0){
			DicService dicService=SpringContextUtil.getBean("dicService", DicService.class);
			List<DicCar> list=dicService.getDicCar();
			for(DicCar dic:list){
				dicCarMap.put(dic.getCarId(), dic);
			}
		}
		return dicCarMap.get(carId);
			
	}
}
