package com.idata365.col.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mortbay.log.Log;
import org.springframework.stereotype.Service;

import com.idata365.col.util.DateTools;
import com.idata365.col.util.Gps;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.HttpUtils;
import com.idata365.col.util.PositionUtil;

@Service
public class YingyanService {

 


//public Map<String,Object> getYingyanAnalysis(List<Map<String,String>> pointList,String userId){
//	  int size=pointList.size();
//	  int times=size/100+1;
//	  Map<String,Object> addPointResult=null;
//	  for(int i=1;i<=times;i++) {
//		  int fromIndex=(i-1)*100;
//		  int toIndex=i*100;
//		  if(fromIndex>=(size-1)) {
//			  break;
//		  }
//		  
//		  if(toIndex>=(size)) {
//			  toIndex=size;
//		  }
//		  List<Map<String,String>> list= pointList.subList(fromIndex, toIndex);
//		  addPointResult=addPointList(list);
//		  if(addPointResult!=null &&  addPointResult.get("status").toString().equals("0")) {
//			  continue;
//		  }else {
//			  break;
//		  }
//	  }
//	if(addPointResult!=null &&  addPointResult.get("status").toString().equals("0")) {
//		Map<String,String> param=new HashMap<String,String>();
//		param.put("start_time", pointList.get(0).get("loc_time"));
//		param.put("end_time", pointList.get(pointList.size()-1).get("loc_time"));
//		param.put("coord_type_output","gcj02");
//		param.put("entity_name",userId);
////		Map<String,Object> analysis=analysis(param);
////		return analysis;
//		return null;
//	}else {
//		return addPointResult;
//	}
//}

public Map<String,Object> addPointsList(List<Map<String,String>> pointList,String userId){
	  int size=pointList.size();
	  int times=size/100+1;
	  Map<String,Object> addPointResult=null;
	  for(int i=1;i<=times;i++) {
		  int fromIndex=(i-1)*100;
		  int toIndex=i*100;
		  if(fromIndex>=(size-1)) {
			  break;
		  }
		  
		  if(toIndex>=(size)) {
			  toIndex=size;
		  }
		  List<Map<String,String>> list= pointList.subList(fromIndex, toIndex);
		  String s= HttpUtils.postYingyanEntityList(list);
		  addPointResult= (Map<String,Object>)GsonUtils.fromJson(s);
		  if(addPointResult!=null &&  addPointResult.get("status").toString().equals("0")) {
			  continue;
		  }else {
			  break;
		  }
	  }
	 return addPointResult;
}


public void dealList(List<Map<String,String>> list,String userId,String habitId) {
	int i=1;
	for(Map<String,String> map:list) {
		map.put("entity_name", userId+"_"+habitId);
	    map.put("latitude", map.get("x"));
	    map.put("longitude", map.get("y"));
	    long time=DateTools.changeDateTimeToSecond( map.get("t"));
	    map.put("loc_time", String.valueOf(time));
	    map.put("coord_type_input","gcj02");
	    map.put("speed",map.get("s"));
	    map.put("direction",String.valueOf(Double.valueOf(map.get("c")).intValue()));
	    map.put("point_key",String.valueOf(i));
	    i++;
	    map.remove("x");
	    map.remove("y");
	    map.remove("s");
	    map.remove("c");
	    map.remove("t");
	    map.remove("h");
	}
	
}
/**
 * 
    * @Title: dealListGaode
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param @param list
    * @param @return    参数
    * @return List<Map<String,Object>>    返回类型
    * @throws
    * @author LanYeYe
 */
public List<Map<String,Object>>  dealListGaode(List<Map<String,String>> list) {
	
	StringBuffer local=new StringBuffer();
	StringBuffer times=new StringBuffer();
	StringBuffer speed=new StringBuffer();
	StringBuffer direction=new StringBuffer();
	long timeLong=0;
	int i=0;
	List<Map<String,String>> tempList=new ArrayList<Map<String,String>>();
	List<Map<String,Object>> speedAlarmList=new ArrayList<Map<String,Object>>();
	for(Map<String,String> map:list) {
		if(map.get("t").equals("")) continue;
		//沦为utc时间
	    long time=DateTools.changeDateTimeToSecond( map.get("t"))-(8*3600);
	    if(time<0 || time==timeLong || Double.valueOf(map.get("s"))==0) {
	    	continue;
	    }
	   
	    timeLong=time;
	    Gps gps= PositionUtil.gps84ToGcj02(Double.valueOf(map.get("y").toString()),Double.valueOf(map.get("x").toString()));
	    
	    local.append(gps.getLng()+",");
	    local.append(gps.getLat());
	    local.append("|");
	    times.append(String.valueOf(time)+",");
	    speed.append(map.get("s")+",");
	    direction.append(String.valueOf(Double.valueOf(map.get("c")).intValue())+",");
	    tempList.add(map);
	    i++;
	    if(i==20) {
	    	String slocal=local.toString().substring(0,local.length()-1);
			String stimes=times.toString().substring(0,times.length()-1);
			String sdirection=direction.toString().substring(0,direction.length()-1);
			String sspeed=speed.toString().substring(0,speed.length()-1);
			dealSpeedAlarm(slocal,stimes,sdirection,sspeed,speedAlarmList,tempList);
			      i=0;
			      tempList.clear();
			      local.setLength(0);times.setLength(0);
			      direction.setLength(0);speed.setLength(0);
	    }
	}
	if(i>=3) {
		String slocal=local.toString().substring(0,local.length()-1);
		String stimes=times.toString().substring(0,times.length()-1);
		String sdirection=direction.toString().substring(0,direction.length()-1);
		String sspeed=speed.toString().substring(0,speed.length()-1);
		dealSpeedAlarm(slocal,stimes,sdirection,sspeed,speedAlarmList,tempList);
	}
	return speedAlarmList;
	
}

private void dealSpeedAlarm(String locations,String time,String direction,String speed,List<Map<String,Object>> speedAlarmList,List<Map<String,String>> tempList) {

	Map<String,String> rtMap=new HashMap<String,String>();
		 rtMap.put("locations",locations);
			rtMap.put("time",time);
			rtMap.put("direction",direction);
			rtMap.put("speed",speed);
		    String  rt=HttpUtils.getGaoDeZhualu(rtMap);
		    Map<String,Object> gdMap=GsonUtils.fromJson(rt);
		    if(gdMap!=null && String.valueOf(gdMap.get("status")).equals("1")) {
		    	List<Map<String,Object>> roads=(List<Map<String,Object>>)gdMap.get("roads");
		    	int j=0;
		    	for(Map<String,Object> road:roads) {
		    		int maxspeed=Integer.valueOf(road.get("maxspeed").toString());
//		    		String crosspoint=road.get("crosspoint").toString();
//		    		Log.info(crosspoint);
		    		if(maxspeed>0) {
		    			int s= BigDecimal.valueOf(Double.valueOf(tempList.get(j).get("s"))).multiply(BigDecimal.valueOf(3600)).divide(BigDecimal.valueOf(1000), 0,RoundingMode.HALF_UP).intValue();
		    			if(s>maxspeed) {
		    				tempList.get(j).put("ms", String.valueOf(maxspeed));
							Map<String,Object> alarm=new HashMap<String,Object>();
							alarm.put("startTime", tempList.get(j).get("t"));
							alarm.put("endTime", tempList.get(j).get("t"));
							alarm.put("alarmValue", s);
							alarm.put("maxspeed", maxspeed);
							alarm.put("alarmType", "4");
							alarm.put("lng", tempList.get(j).get("y"));
							alarm.put("lat", tempList.get(j).get("x"));
		    				speedAlarmList.add(alarm);
		    			}
		    		}
		    		j++;
		    	}
		    }
}


public static void main(String []args) {
	List<Map<String,String> > list=new ArrayList<Map<String,String>> ();
	List<Map<String,String> > list2=new ArrayList<Map<String,String>> ();
	for(int i=0;i<10;i++) {
		Map<String,String>  m=new HashMap<String,String> ();
		m.put(""+i, ""+i);
		list.add(m);
	}
	
	for(int i=0;i<10;i++) {
		if(i%2==0) {
			list2.add(list.get(i));
		}
	}
	list.clear();
	
	System.out.println(list2.size());
	
}


}
