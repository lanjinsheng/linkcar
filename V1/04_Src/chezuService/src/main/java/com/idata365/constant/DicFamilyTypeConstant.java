package com.idata365.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.entity.DicFamilyType;
import com.idata365.service.DicService;
import com.idata365.service.SpringContextUtil;

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
    public  final static Integer GuanJun_6=125;
    public  final static Integer GuanJun_7=126;
    public  final static Integer GuanJun_8=127;
    public  final static Integer GuanJun_9=128;
    public  final static Integer GuanJun_10=129;
    public  final static Integer GuanJun_11=130;
    public  final static Integer GuanJun_12=131;
    public  final static Integer GuanJun_13=132;
    public  final static Integer GuanJun_14=133;
    public  final static Integer GuanJun_15=134;
    public  final static Integer GuanJun_16=135;
    public  final static Integer GuanJun_17=136;
    public  final static Integer GuanJun_18=137;
    public  final static Integer GuanJun_19=138;
    public  final static Integer GuanJun_20=139;
    
    
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
	
	
	public static Integer getDicFamilyTypeByTrophy(Integer trophy){
		  if(trophy>=250) {
			  if(trophy>=1330) {
				  return GuanJun_20;
			  }else if(trophy>=1270) {
				  return GuanJun_19;
			  }else if(trophy>=1240) {
				  return GuanJun_18;
			  }else if(trophy>=1210) {
				  return GuanJun_17;
			  }else if(trophy>=1180) {
				  return GuanJun_16;
			  }else if(trophy>=1150) {
				  return GuanJun_15;
			  }else if(trophy>=1120) {
				  return GuanJun_14;
			  }else if(trophy>=1090) {
				  return GuanJun_13;
			  }else if(trophy>=1060) {
				  return GuanJun_12;
			  }else if(trophy>=1030) {
				  return GuanJun_11;
			  }else if(trophy>=1000) {
				  return GuanJun_10;
			  }else if(trophy>=970) {
				  return GuanJun_9;
			  }else if(trophy>=940) {
				  return GuanJun_8;
			  }else if(trophy>=910) {
				  return GuanJun_7;
			  }else if(trophy>=850) {
				  return GuanJun_6;
			  }else if(trophy>=880) {
				  return GuanJun_5;
			  }else if(trophy>=850) {
				  return GuanJun_4;
			  }else if(trophy>=820) {
				  return GuanJun_3;
			  }else if(trophy>=790) {
				  return GuanJun_2;
			  }else if(trophy>=760) {
				  return GuanJun_1;
			  }else if(trophy>=700) {
				  return ZuanShi_1;
			  }else if(trophy>=630) {
				  return ZuanShi_2;
			  }else if(trophy>=570) {
				  return ZuanShi_3;
			  }else if(trophy>=510) {
				  return ZuanShi_4;
			  }else if(trophy>=450) {
				  return HuangJin_1;
			  }else if(trophy>=400) {
				  return HuangJin_2;
			  }else if(trophy>=350) {
				  return HuangJin_3;
			  }else if(trophy>=300) {
				  return HuangJin_4;
			  }else {
				  return HuangJin_5;
			  }
		  }else {
				  if(trophy>=200) {
					  return BaiYing_1;
				  }else if(trophy>=170) {
					  return BaiYing_2;
				  }else if(trophy>=140) {
					  return BaiYing_3;
				  }else if(trophy>=110) {
					  return BaiYing_4;
				  }else if(trophy>=80) {
					  return BaiYing_5;
				  }else if(trophy>=50) {
					  return QingTong_1;
				  }else if(trophy>=30) {
					  return QingTong_2;
				  }else if(trophy>=20) {
					  return QingTong_3;
				  }else if(trophy>=10) {
					  return QingTong_4;
				  }else  {
					  return QingTong_5;
				  }
		  }
			
	}
}
