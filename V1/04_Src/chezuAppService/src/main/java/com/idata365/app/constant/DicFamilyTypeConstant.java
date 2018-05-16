package com.idata365.app.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.service.DicService;
import com.idata365.app.service.SpringContextUtil;

public class DicFamilyTypeConstant {
	public static Map<Integer,DicFamilyType> familyTypeMap=new HashMap<Integer,DicFamilyType>();
    public  final static Integer QingTong_5=80;
    public  final static Integer QingTong_4=81;
    public  final static Integer QingTong_3=82;
    public  final static Integer QingTong_2=83;
    public  final static Integer QingTong_1=84;
    
    
    public  final static Integer BaiYing_5=90;
    public  final static Integer BaiYing_4=91;
    public  final static Integer BaiYing_3=92;
    public  final static Integer BaiYing_2=93;
    public  final static Integer BaiYing_1=94;
    
    
    public  final static Integer HuangJin_5=100;
    public  final static Integer HuangJin_4=101;
    public  final static Integer HuangJin_3=102;
    public  final static Integer HuangJin_2=103;
    public  final static Integer HuangJin_1=104;
    
    
    public  final static Integer ZuanShi_4=110;
    public  final static Integer ZuanShi_3=111;
    public  final static Integer ZuanShi_2=112;
    public  final static Integer ZuanShi_1=113;
    
    public  final static Integer GuanJun_1=120;
    public  final static Integer GuanJun_2=121;
    public  final static Integer GuanJun_3=122;
    public  final static Integer GuanJun_4=123;
    public  final static Integer GuanJun_5=124;
    
    /**
     * 
     * @param familyType
     * @return
     */
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
