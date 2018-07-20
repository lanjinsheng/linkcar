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
    public final static Integer LUNTAI_B=10;
    public final static Integer LUNTAI_A=11;
    public final static Integer LUNTAI_S=12;
    
    public final static Integer HUOHUASAI_B=20;
    public final static Integer HUOHUASAI_A=21;
    public final static Integer HUOHUASAI_S=22;
    
    public final static Integer JIYOU_B=30;
    public final static Integer JIYOU_A=31;
    public final static Integer JIYOU_S=32;
    
    public final static Integer SHACHEPIAN_B=40;
    public final static Integer SHACHEPIAN_A=41;
    public final static Integer SHACHEPIAN_S=42;
    
    public final static Integer XUDIANCHI_B=50;
    public final static Integer XUDIANCHI_A=51;
    public final static Integer XUDIANCHI_S=52;
    
    /**
     * 
     * @param componentId
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
