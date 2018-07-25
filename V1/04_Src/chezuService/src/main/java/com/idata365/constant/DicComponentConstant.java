package com.idata365.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.entity.DicComponent;
import com.idata365.service.DicService;
import com.idata365.service.SpringContextUtil;


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
    
	public static Map<Integer,Integer> dicComponentMapS=new HashMap<Integer,Integer>();
	{
		dicComponentMapS.put(1, LUNTAI_S);
		dicComponentMapS.put(2, HUOHUASAI_S);
		dicComponentMapS.put(3, JIYOU_S);
		dicComponentMapS.put(4, SHACHEPIAN_S);
		dicComponentMapS.put(5, XUDIANCHI_S);
	}
    
    
	public static Map<Integer,Integer> dicComponentMapA=new HashMap<Integer,Integer>();
	{
		dicComponentMapA.put(1, LUNTAI_A);
		dicComponentMapA.put(2, HUOHUASAI_A);
		dicComponentMapA.put(3, JIYOU_A);
		dicComponentMapA.put(4, SHACHEPIAN_A);
		dicComponentMapA.put(5, XUDIANCHI_A);
	}
    
	public static Map<Integer,Integer> dicComponentMapB=new HashMap<Integer,Integer>();
	{
		dicComponentMapB.put(1, LUNTAI_B);
		dicComponentMapB.put(2, HUOHUASAI_B);
		dicComponentMapB.put(3, JIYOU_B);
		dicComponentMapB.put(4, SHACHEPIAN_B);
		dicComponentMapB.put(5, XUDIANCHI_B);
	}
    
    
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
