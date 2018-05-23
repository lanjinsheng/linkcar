package com.idata365.col.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OverspeedUtil {
	public static final BigDecimal kmBei=BigDecimal.valueOf(3.6);
	public static final BigDecimal overBei=BigDecimal.valueOf(1.1);
	public static final int filterDist1=200;
	public static final int filterDist2=300;
	public static final int filterCount=4;
	
	
	public String readToString(String fileName) {  
        String encoding = "UTF-8";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
    }
	
	public static void main(String []args) {
//		OverspeedUtil util= new OverspeedUtil();
//		String gps=util.readToString("D:\\jsonGps");
//		Map<String,Object> jsonMap=GsonUtils.fromJson(gps);
//		Object overspeedInfo=jsonMap.get("overspeedGPSInfo");
//		List<Map<String,Object>> list=util.dealOverSpeed(overspeedInfo);
	}
	/**
	 * 
	    * @Title: dealOverSpeed
	    * @Description: TODO(监控区域200m内连续4个点超速超过1.1倍算一次超速)
	    * @param @param overspeedInfo
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public static List<Map<String,Object>> dealOverSpeed(List<Map<String,Object>> jkList){
		List<Map<String,Object>> overSpeedArray=new ArrayList<Map<String,Object>>();
		if(jkList==null || jkList.size()==0) {
			return overSpeedArray;
		}
		
		for(Map<String,Object> map:jkList) {
			String jkGps=String.valueOf(map.get("jk"));
			List<Map<String,Object>> jkPoints=(List<Map<String,Object>>)map.get("set");
			int count=0;long lastTime=0;
			for(Map<String,Object> point:jkPoints) {
				//先看距离
				double d=Double.valueOf(point.get("d").toString());
				double ls=Double.valueOf(point.get("ls").toString());
				double s=Double.valueOf(point.get("s").toString());
//				if(s<=18 && d>filterDist1) {
//					continue;
//				}
                if(d>filterDist2) {
                	continue;
                }
				String t=String.valueOf(point.get("t"));
//				超过10%
				double overLs=overBei.multiply(BigDecimal.valueOf(ls)).doubleValue();
				double kmS=BigDecimal.valueOf(s).multiply(kmBei)
						.doubleValue();
				if(kmS>=overLs) {
					 //记录超速点
					 long pTime=DateTools.changeDateTimeToSecond(t);
					 if((pTime-lastTime)<5) {//小于5秒
						 count++;
						 lastTime=pTime;
					 }else {
						 //重置计数
						 count=0;
						 lastTime=0;		 
					 }
					if(count>=filterCount) {
						int j=jkGps.indexOf("|");
						Map<String,Object> alarm=new HashMap<String,Object>();
						alarm.put("startTime", t);
						alarm.put("endTime", t);
						alarm.put("alarmValue", s);
						alarm.put("maxspeed", ls);
						alarm.put("alarmType", "4");
						alarm.put("lng",jkGps.substring(j+1,jkGps.length()));
						alarm.put("lat",jkGps.substring(0,j));
						overSpeedArray.add(alarm);
						break;
					}
				}
			}
		}
		return  overSpeedArray;
	}
}
