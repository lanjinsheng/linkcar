package com.idata365.app.constant;

import java.util.HashMap;
import java.util.Map;

public class DicFamilyTypeConstant {
	
	
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
    public  final static Integer GuanJun_1000=12400000;
    public static Map<Integer,Integer> FamilyLeverPower=new HashMap<Integer,Integer>();
    
    static {
    	FamilyLeverPower.put(QingTong_5, 100);
    	FamilyLeverPower.put(QingTong_4, 100);
    	FamilyLeverPower.put(QingTong_3, 100);
    	FamilyLeverPower.put(QingTong_2, 100);
    	FamilyLeverPower.put(QingTong_1, 100);
    	
    	FamilyLeverPower.put(BaiYing_5, 150);	
    	FamilyLeverPower.put(BaiYing_4, 150);
    	FamilyLeverPower.put(BaiYing_3, 150);
    	FamilyLeverPower.put(BaiYing_2, 150);
    	FamilyLeverPower.put(BaiYing_1, 150);
    	
    	FamilyLeverPower.put(HuangJin_5, 200);
    	FamilyLeverPower.put(HuangJin_4, 200);
    	FamilyLeverPower.put(HuangJin_3, 200);
    	FamilyLeverPower.put(HuangJin_2, 200);
    	FamilyLeverPower.put(HuangJin_1, 200);
    	
       	FamilyLeverPower.put(ZuanShi_4, 250);
    	FamilyLeverPower.put(ZuanShi_3, 250);
    	FamilyLeverPower.put(ZuanShi_2, 250);
    	FamilyLeverPower.put(ZuanShi_1, 250);
    	
    	
       	FamilyLeverPower.put(GuanJun_1, 300);
     	FamilyLeverPower.put(GuanJun_2, 300);
     	FamilyLeverPower.put(GuanJun_3, 300);
     	FamilyLeverPower.put(GuanJun_4, 300);
     	FamilyLeverPower.put(GuanJun_5, 300);
     	FamilyLeverPower.put(GuanJun_6, 300);
     	FamilyLeverPower.put(GuanJun_7, 300);
     	FamilyLeverPower.put(GuanJun_8, 300);
     	FamilyLeverPower.put(GuanJun_9, 300);
     	FamilyLeverPower.put(GuanJun_10, 300);
     	FamilyLeverPower.put(GuanJun_11, 300);
     	FamilyLeverPower.put(GuanJun_12, 300);
     	FamilyLeverPower.put(GuanJun_13, 300);
     	FamilyLeverPower.put(GuanJun_14, 300);
     	FamilyLeverPower.put(GuanJun_15, 300);
     	FamilyLeverPower.put(GuanJun_16, 300);
     	FamilyLeverPower.put(GuanJun_17, 300);
     	FamilyLeverPower.put(GuanJun_18, 300);
     	FamilyLeverPower.put(GuanJun_19, 300);
     	FamilyLeverPower.put(GuanJun_20, 300);
    }
    
    public static int getPowerByFamilyType(Integer familyType) {
    	return FamilyLeverPower.get(familyType);
    }
}
