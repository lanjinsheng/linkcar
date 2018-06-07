package com.ljs.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Constant {
    public static String colHost="http://product-col.idata365.com";
	public static boolean noValid=false;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getTableName("Errddsfs"));

	}
	
	public static String getTableName(String tbName){
		if(tbName.startsWith("APRHYTEST")){
			return tbName;
		}else{
			return "J"+tbName;
		}
		
	}
	
//	所有接口使用apikey=z2f465291cffd352f51e34c893688549
//	public final static String apikey="z2f465291cffd352f51e34c893688549";
	public final static String apikey="apf465291iuyt352f51e34c893688986";
	public final static String dataIp1="139.224.30.94:9999";
//	public final static String apDatas="/mydata/apDatas";
	public final static String apOrgDatas="/mydata/apOrgDatasByDay";
	public final static String apDownDatas="/mydata/apDownDatas";
	public static String day=CommentUtil.getCurDayStr2();
	
	public final static String partnerIdIdeal="Ideal";
	public static Map<String,String> partnerKeyMap=new HashMap<String,String>();
	 static{
		 partnerKeyMap.put(partnerIdIdeal, apikey);
		 
	 }
	
//	RHY-TEST-ZBT-001  
//	RHY-TEST-ZBT-002
//	RHY-TEST-ZBT-003
//	RHY-TEST-ZBT-004
	public static Map<String,Integer> validSNMap=new HashMap<String,Integer>();

	 static{
		 validSNMap.put("AP-RHY-TEST-1001", 1);
		 validSNMap.put("AP-RHY-TEST-1002", 1);
		 validSNMap.put("AP-RHY-TEST-1003", 1);
		 validSNMap.put("AP-RHY-TEST-1004", 1);
	 }
	 
	 public static Map<String,String> projectSNMap=new ConcurrentHashMap<String,String>();
	 
	 public static Map<String,String> inValidSNMap=new ConcurrentHashMap<String,String>();
	 
	 
	 public static  final int FMTF=3;
	  public static String getValidDay(String sn){
			String o=projectSNMap.get(sn+"validTime_from");
			String t=projectSNMap.get(sn+"validTime_to");
			if(o==null){
				o=inValidSNMap.get(sn+"validTime_from");
				if(o==null){
				 o= "--";
				}
			}
			if(t==null){
				t=inValidSNMap.get(sn+"validTime_to");
				if(t==null){
				  t="--";
				}
			}
			
			return o+"~"+t;
		}
	 
		public static int getFMTF(String sn){
			String o=projectSNMap.get(sn+"adt_rate");
			if(o==null){
				return FMTF;
			}else{
				return Integer.valueOf(o);
			}
		}
		public static  final int FMSF_HOUR=5;//小于5次
		public static int getFMSF_HOUR(String sn){
			String o=projectSNMap.get(sn+"fmsf_hour");
			if(o==null){
				return FMSF_HOUR;
			}else{
				return Integer.valueOf(o);
			}
		}
		public static final   int FMSF_DAY=10;//小于10次
		public static int getFMSF_DAY(String sn){
			String o=projectSNMap.get(sn+"fmsf_day");
			if(o==null){
				return FMSF_DAY;
			}else{
				return Integer.valueOf(o);
			}
		}	
		public static  final int FMSF_LIVE=2;//小于2次
		public static int getFMSF_LIVE(String sn){
			String o=projectSNMap.get(sn+"fmsf_live");
			if(o==null){
				return FMSF_LIVE;
			}else{
				return Integer.valueOf(o);
			}
		}	
		
		public static final  double ADT_Divide_FMTF_LIVE=1.2;
		public static double getADT_Divide_FMTF_LIVE(String sn){
			String o=projectSNMap.get(sn+"adt_divide_fmtf_live");
			if(o==null){
				return ADT_Divide_FMTF_LIVE;
			}else{
				return Double.valueOf(o);
			}
		}	
		public static   final int LFT=90;//90s
		public static int getLFT(String sn){
			String o=projectSNMap.get(sn+"lft");
			if(o==null){
				return LFT;
			}else{
				return Integer.valueOf(o);
			}
		}	
		public static  int LBT=180;//180s
		
		public static int getLBT(String sn){
			String o=projectSNMap.get(sn+"lbt");
			if(o==null){
				return LBT;
			}else{
				return Integer.valueOf(o);
			}
		}
		
		public static String getSnValueByKey(String key){
			return projectSNMap.get(key);
		}
		public static void clearProjectMap(){
			projectSNMap.clear();;
		}
	 public static boolean isValidSN(String sn){
		 sn=sn.toUpperCase();
		 if(validSNMap.get(sn)!=null){
			   return true;
		 }
		   return false;
	 }
	 public static boolean isValidLogin(String sn){
		 sn=sn.toUpperCase();
		 if(validSNMap.get(sn)!=null){
			   return true;
		 }
		 if(projectSNMap.get(sn)!=null)
			 return true;
		 if(sn.startsWith("ERR")){
			 return true;
		 }
		   return false;
	 }
	 
	 public static String LixiangBlackWhiteUrl="http://116.228.83.4/WIFI_YUN/system/wifi/blackWhileList";
	 public static String LixiangCertificateUrl="http://116.228.83.4/WIFI_YUN/system/wifi/syncCertificate";
	 
	 
	 //health 数据缓存
//	 public static Map<String,String> healthMap=new ConcurrentHashMap<String, String>();
	 public static Map<String,String> healthMap=new HashMap<String, String>();//无需线程安全
	 
	 
}
