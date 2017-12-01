package com.idata365.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PhoneGpsUtil {
	
	public static void main(String []args){
	}
	public static Map<String,Object> getGpsValues(List<Map<String,String>> list){
		Map<String,Object> rtMap=new HashMap<String,Object>();
		Double distance=0d;//距离值测算
		double avgSpeed=0;
		int size=list.size();
		Map<String,String> first=list.get(0);
		List<Map<String,Object>> alarmListJia=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> alarmListJian=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> alarmListZhuan=new ArrayList<Map<String,Object>>();
		rtMap.put("alarmListJia", alarmListJia);
		rtMap.put("alarmListJian", alarmListJian);
		rtMap.put("alarmListZhuan", alarmListZhuan);
		rtMap.put("startTime", "1970-01-01 00:00:00.000");
		rtMap.put("endTime", "1970-01-01 00:00:00.000");
		rtMap.put("maxSpeed", 0);
		rtMap.put("avgSpeed", avgSpeed);
		rtMap.put("distance", distance);
		rtMap.put("driveTimes", 0);
		int i=0;
		String st=first.get("t");
		StringBuffer et=new StringBuffer(first.get("t"));
		addJia(first,i,size,list,alarmListJia);
		addJian(first, i, size, list,  alarmListJian,alarmListZhuan);
		double lat1=Double.valueOf(first.get("x"));
		double lng1=Double.valueOf(first.get("y"));
		double maxSpeed=Double.valueOf(first.get("s"));
		Gps Gps1=PositionUtil.gcj02ToGps84(Double.valueOf(lat1), Double.valueOf(lng1));
		for(i=1;i<size;i++){
			Map<String,String> gps=list.get(i);
			double lat2=Double.valueOf(gps.get("x"));
			double lng2=Double.valueOf(gps.get("y"));
			Gps Gps2=PositionUtil.gcj02ToGps84(lat2, lng2);
		    Double d=PositionUtil.distance(Gps1.getLng(),Gps1.getLat(),Gps2.getLng(),Gps2.getLat());
			   distance+=d;
			   Gps1=Gps2;
			   addJia(gps,i,size,list,alarmListJia);
			   boolean isTurn=addZhuan(gps, i, size, list, alarmListZhuan);
			   if(!isTurn){
				   addJian(gps, i, size, list, alarmListJian,alarmListZhuan);
			   }
			   et.setLength(0);
			   et.append(gps.get("t"));
			   if(maxSpeed<Double.valueOf(gps.get("s"))){
				   maxSpeed=Double.valueOf(gps.get("s"));
			   }
		}
		
		try {
			 long subTime=DateTools.getDiffTimeS(et.toString(),st);
			 rtMap.put("driveTimes", subTime);
			if(subTime>0){
				avgSpeed=BigDecimal.valueOf(distance).divide(BigDecimal.valueOf(subTime), 2,BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		rtMap.put("startTime", st);
		rtMap.put("endTime", et.toString());
		rtMap.put("maxSpeed", maxSpeed);
		rtMap.put("avgSpeed", avgSpeed);
		rtMap.put("distance", distance);
		return rtMap;
	}
	
	public static  void addJia(Map<String,String> first,int i,int size,List<Map<String,String>> list,List<Map<String,Object>> alarmList){
		double s=Double.valueOf(first.get("s"));
		String t=first.get("t");
		if(s<=2){
			return;
		}
		//减速条件
		int j=i+1;
		int p=1;
		while(j<size){
			//取下一个点
			Map<String,String> sec=list.get(j);
			double s2=Double.valueOf(sec.get("s"));
			if(s2<=0){
				j++;
				continue;
			}
			p++;
			Double ds=BigDecimal.valueOf(s).divide(BigDecimal.valueOf(s2),3,RoundingMode.HALF_UP).doubleValue();
			if(p==2){
				if(ds<=0.5){
					j++;
					continue;
				}else{
					break;
				}
			}else if(p==3){
				if(ds<=0.4){
					//急加
					boolean  hadEvent=hadEventInXSecond(alarmList,15,t);
					if(hadEvent) {
						return;//有过15s内的急加，跳出。
					}
					Map<String,Object> alarm=new HashMap<String,Object>();
					alarm.put("startTime", list.get(i).get("t"));
					alarm.put("endTime", list.get(i).get("t"));
					alarm.put("alarmValue", ds);
					alarm.put("alarmType", "1");
					alarm.put("lng", list.get(i).get("y"));
					alarm.put("lat", list.get(i).get("x"));
					alarmList.add(alarm);
					return;
				}else{
					break;
				}
			}else{
				break;
			}
			
		}
	}
 
	//急减速判断
	public static boolean addJian(Map<String,String> curPoint,int now,int total,List<Map<String,String>> list,List<Map<String,Object>> alarmList,List<Map<String,Object>> alarmListZhuan){
		double s=Double.valueOf(curPoint.get("s"));
		String t=(curPoint.get("t"));
		double s1=s;
		String t1=t;
		double d1=1.5;double d2=2.0;
		if(s>=10){
			d1=1.3; d2=1.7;
		}
		if(s>=5){
			//减速条件
			int j=now+1;
			int p=1;
			while(j<total){
				//取下一个点
				Map<String,String> sec=list.get(j);
				double s2=Double.valueOf(sec.get("s"));
				String t2=(sec.get("t"));
				if(s2<=0){
					j++;
					continue;
				}
				 BigDecimal  ss= BigDecimal.valueOf(s2);
				long dif=1;
				try {
					  dif=DateTools.getDiffTimeS(t2, t1);
					  if(dif==0)
						  {j++;continue;}
					  if(dif>1){
						  ss=((BigDecimal.valueOf(s1).subtract(BigDecimal.valueOf(s2))).divide(BigDecimal.valueOf(dif),3,RoundingMode.HALF_UP)).add(BigDecimal.valueOf(s2));
					  }
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				p++;
				Double ds=BigDecimal.valueOf(s).divide(ss,3,RoundingMode.HALF_UP).doubleValue();
				if(p==2){
					
					if(ds>=d1){
						j++;
						s1=s2;
						t1=t2;
						continue;
					}else{
						break;
					}
				}else if(p==3){
					if(ds>=d2){
						//急减
						boolean  hadEvent=hadEventInXSecond(alarmList,15,t);
						if(hadEvent) {
							return false;//有过15s内的急减，跳出。
						}
						//急减之前判断是否是急转，如果是，则急转优先
						boolean zhuan=jugeZhuanInJian( curPoint, list, now,total, alarmListZhuan);
						if(zhuan) {
							return false;//有过急转,则跳出
						}
						Map<String,Object> alarm=new HashMap<String,Object>();
						alarm.put("startTime",t);
						alarm.put("endTime", t);
						alarm.put("alarmValue", ds);
						alarm.put("alarmType", "2");
						alarm.put("lng", curPoint.get("y"));
						alarm.put("lat", curPoint.get("x"));
						alarmList.add(alarm);
						return true;
					}else{
						break;
					}
				}else{
					break;
				}
				
			}
			return false;
			 
		}
		return false;
	}
	
	/*=========*/
	//		int tempTotal=total-3;
	//if(now>2 && now<tempTotal){
	//dealZhuan(first, list,now,service,turnAlarmList);
   // }
	public  static boolean  addZhuan(Map<String,String> first,int now,int size,List<Map<String,String>> list,List<Map<String,Object>> alarmList){
		int tempTotal=size-5;
		boolean isZhuan=false;
		String t=first.get("t");

		if(now<2 || now>=tempTotal){
			return false;
		}
		double s=Double.valueOf(first.get("s"));
		if(s<7){
			return false;
		}
		String course=first.get("c");	
		String nextCoure=  list.get(now+1).get("c");
		Double d=Math.abs(Double.valueOf(course) -Double.valueOf(nextCoure));
		if(d>180 ){
			d=360-d;
		}
		if(d<10){
			return false;
		}
		double sub8PointCourse=zhuanAngle(now,list);
		if(sub8PointCourse>60){
			
			boolean hadEvent=hadEventInXSecond(alarmList, 15, t);
			if(hadEvent) {
				return false;
			}
			double s2=Double.valueOf(list.get(now+1).get("s"));
			Double ds=BigDecimal.valueOf(s).divide(BigDecimal.valueOf(s2),3,RoundingMode.HALF_UP).doubleValue();
			
			if(ds<1.3) {
				isZhuan= true;
			}else {
				if(s>10 && d>=30){//转角下速度与角度的判断
					isZhuan= true;
				}
				
			}
			if(isZhuan) {
				Map<String,Object> alarm=new HashMap<String,Object>();
				alarm.put("startTime", t);
				alarm.put("endTime",t);
				alarm.put("alarmValue", String.valueOf(sub8PointCourse));
				alarm.put("alarmType", "3");
				alarm.put("lng", first.get("y"));
				alarm.put("lat", first.get("x"));
				alarmList.add(alarm);
			}
			
		}
		return isZhuan;	
	}
	
	public static double zhuanAngle(int now,List<Map<String,String>> list) {
//		double d1= Double.valueOf(list.get(now-7).get("c"));
//		double d2= Double.valueOf(list.get(now-6).get("c"));
//		double d3= Double.valueOf(list.get(now-5).get("c"));
//		double d4= Double.valueOf(list.get(now-4).get("c"));
//		double d5= Double.valueOf(list.get(now-3).get("c"));
		double d6= Double.valueOf(list.get(now-2).get("c"));
		double d7= Double.valueOf(list.get(now-1).get("c"));
		double d8= Double.valueOf(list.get(now).get("c"));
		double d9= Double.valueOf(list.get(now+1).get("c"));
		double d10= Double.valueOf(list.get(now+2).get("c"));
		double d11= Double.valueOf(list.get(now+3).get("c"));
		double d12= Double.valueOf(list.get(now+4).get("c"));
		double d13= Double.valueOf(list.get(now+5).get("c"));
//		double d14= Double.valueOf(list.get(now+6).get("c"));
//		double d15= Double.valueOf(list.get(now+7).get("c"));
		double sub9PointCourse=sub9PointCourse25(d6,d7,d8,d9,d10,d11,d12,d13);
		if(sub9PointCourse>180){
			sub9PointCourse=360-sub9PointCourse;
		}
		return sub9PointCourse;
	}
	
	public static boolean hadEventInXSecond(List<Map<String,Object>> eventList,int second,String curT) {
		if(eventList.size()>0){
			String t0=String.valueOf(eventList.get(eventList.size()-1).get("startTime"));
			try {
				long tt0=DateTools.getDiffTimeS(curT,t0);
				if(tt0<second) return true;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 return false;
	}
	
	public static boolean jugeZhuanInJian(Map<String,String> curPoint,List<Map<String,String>> list,int now,int total,List<Map<String,Object>> alarmListZhuan) {
		//增加角度判断
		int tempTotal=total-5;
		if(now>=2 && now<tempTotal){
			String t=curPoint.get("t");
			double sub8PointCourse=zhuanAngle(now,list);
			if(sub8PointCourse>60){
				String course=curPoint.get("c");	
				String nextCoure=  list.get(now+1).get("c");
				Double d=Math.abs(Double.valueOf(course) -Double.valueOf(nextCoure));
				if(d>180 ){
					d=360-d;
				}
				if(d<10){
					return false;
				}
				boolean  hadEvent=hadEventInXSecond(alarmListZhuan,15,t);
				if(hadEvent) {
					return false;
				}
				Map<String,Object> alarm=new HashMap<String,Object>();
				alarm.put("startTime", t);
				alarm.put("endTime",t);
				alarm.put("alarmValue", String.valueOf(sub8PointCourse));
				alarm.put("alarmType", "3");
				alarm.put("lng", curPoint.get("y"));
				alarm.put("lat", curPoint.get("x"));
				alarmListZhuan.add(alarm);
				return true;
			}
		}
		return false;
	}
	public static double sub9PointCourse(double d5,double d6,double d7,double d8,double d9,double d10,double d11,double d12,double d13,double d14,double d15){
		double max=d5;
		double min=d5;
		if(d5>max){
			max=d5;
		}else if(d5<min){
			min=d5;
		}
		if(d6>max){
			max=d6;
		}else if(d6<min){
			min=d6;
		}
		if(d7>max){
			max=d7;
		}else if(d7<min){
			min=d7;
		}
		if(d8>max){
			max=d8;
		}else if(d8<min){
			min=d8;
		}
		if(d9>max){
			max=d9;
		}else if(d9<min){
			min=d9;
		}
		if(d10>max){
			max=d10;
		}else if(d10<min){
			min=d10;
		}
		if(d11>max){
			max=d11;
		}else if(d11<min){
			min=d11;
		}
		if(d12>max){
			max=d12;
		}else if(d12<min){
			min=d12;
		}
		if(d13>max){
			max=d13;
		}else if(d13<min){
			min=d13;
		}
		if(d14>max){
			max=d14;
		}else if(d14<min){
			min=d14;
		}
		if(d15>max){
			max=d15;
		}else if(d15<min){
			min=d15;
		}
		return (max-min);
	}
	
	public static double sub9PointCourse24(double d6,double d7,double d8,double d9,double d10,double d11,double d12){
		double max=d6;
		double min=d6;
		
		if(d6>max){
			max=d6;
		}else if(d6<min){
			min=d6;
		}
		if(d7>max){
			max=d7;
		}else if(d7<min){
			min=d7;
		}
		if(d8>max){
			max=d8;
		}else if(d8<min){
			min=d8;
		}
		if(d9>max){
			max=d9;
		}else if(d9<min){
			min=d9;
		}
		if(d10>max){
			max=d10;
		}else if(d10<min){
			min=d10;
		}
		if(d11>max){
			max=d11;
		}else if(d11<min){
			min=d11;
		}
		if(d12>max){
			max=d12;
		}else if(d12<min){
			min=d12;
		
		}
		return (max-min);
	}
	public static double sub9PointCourse25(double d6,double d7,double d8,double d9,double d10,double d11,double d12,double d13){
		double max=d6;
		double min=d6;
		
		if(d6>max){
			max=d6;
		}else if(d6<min){
			min=d6;
		}
		if(d7>max){
			max=d7;
		}else if(d7<min){
			min=d7;
		}
		if(d8>max){
			max=d8;
		}else if(d8<min){
			min=d8;
		}
		if(d9>max){
			max=d9;
		}else if(d9<min){
			min=d9;
		}
		if(d10>max){
			max=d10;
		}else if(d10<min){
			min=d10;
		}
		if(d11>max){
			max=d11;
		}else if(d11<min){
			min=d11;
		}
		if(d12>max){
			max=d12;
		}else if(d12<min){
			min=d12;
		
		}
		if(d13>max){
			max=d13;
		}else if(d13<min){
			min=d13;
		
		}
		return (max-min);
	}
	
}
