package com.idata365.col.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.idata365.col.config.ColProperties;
import com.idata365.col.entity.bean.SpeedBean;
import com.idata365.col.schedule.DatasDealTask;


public class PhoneGpsUtil2 {
	private static Logger log = Logger.getLogger(PhoneGpsUtil.class);
	public static void main(String []args){

	}
	final static double speed120=33.33;
	final static double speed130=36.11;
	final static double speed140=38.89;
	final static double speed150=41.67;
	final static double speed160=44.44;
	private static void setSpeedBean(SpeedBean speedBean,double speed) {
		if(speed>=speed120 && speed<speed130) {
			speedBean.setSpeed120To129Times(speedBean.getSpeed120To129Times()+1);
		}else if(speed>=speed130 && speed<speed140) {
			speedBean.setSpeed130To139Times(speedBean.getSpeed130To139Times()+1);
		}else if(speed>=speed140 && speed<speed150) {
			speedBean.setSpeed140To149Times(speedBean.getSpeed140To149Times()+1);
		}else if(speed>=speed150 && speed<speed160) {
			speedBean.setSpeed150To159Times(speedBean.getSpeed150To159Times()+1);
		}else if(speed>=speed160) {
			speedBean.setSpeed160UpTimes(speedBean.getSpeed160UpTimes()+1);
		}
	}
	/**
	 * 
	    * @Title: dealValidGps
	    * @Description: TODO(去除无效gps)
	    * @param @param list    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public static List<Map<String,String>> dealOrderGps(List<Map<String,String>> list){
		List<OrderGpsBean> scbList=new ArrayList<OrderGpsBean>();
		List<Map<String,String>> rtList=new ArrayList<Map<String,String>>();
		int len=0;
		for(Map<String,String> gps:list) {
			if(gps.get("t")==null || gps.get("s").equals("")) {
				len++;
				continue;
			}
			scbList.add(new OrderGpsBean(String.valueOf(gps.get("t")),len));
			len++;
		}
		scbList.sort(Comparator.naturalOrder());
		int i=scbList.size();
		 
		for(int j=0;j<i;j++) {
			int validNum=scbList.get(j).getLsNumber();
			rtList.add(list.get(validNum));
		}
		return rtList;
	}
	
	/**
	 * 
	    * @Title: dealValidGps
	    * @Description: TODO(去除无效gps)
	    * @param @param list    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public static void dealValidGps(List<Map<String,String>> list){
		List<SpeedClearBean> scbList=new ArrayList<SpeedClearBean>();
		int len=0;
		for(Map<String,String> gps:list) {
			if(gps.get("s")==null || gps.get("s").equals("")) {
				len++;
				continue;
			}
			scbList.add(new SpeedClearBean(Double.valueOf(gps.get("s")),len));
			len++;
		}
		scbList.sort(Comparator.naturalOrder());
		int i=((list.size()/3600+1)*5);
		if(i>list.size()) return;
		for(int j=0;j<i;j++) {
			int validNum=scbList.get(j).getLsNumber();
			list.get(validNum).put("invalid", "1");
		}
	}
	public static Map<String,Object> getGpsValues(List<Map<String,String>> list,String uhIDs,ColProperties col){
//		   StringBuffer out=new StringBuffer();
		//清理gps點
		dealValidGps(list);
		Map<String,Object> rtMap=new HashMap<String,Object>();
		Double distance=0d;//距离值测算
		double avgSpeed=0;
		SpeedBean speedBean=new SpeedBean();
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
		rtMap.put("speed120To129Times", 0);
		rtMap.put("speed130To139Times", 0);
		rtMap.put("speed140To149Times", 0);
		rtMap.put("speed150To159Times", 0);
		rtMap.put("speed160UpTimes", 0);
		
		
		rtMap.put("driveTimes", 0);
		int i=0;
		String st=first.get("t");
		StringBuffer et=new StringBuffer(first.get("t"));
		while(st.equals("") && first.get("invalid")==null) {
			  i++;
			  first=list.get(i);
			  st=first.get("t");
			  et=new StringBuffer(first.get("t"));
			  log.error("时间为空====错误行程号："+uhIDs);
		} 
		if(Integer.valueOf(first.get("h"))<col.getGpsHa().intValue()) {//h值小于100的才进行计算
			addJia(first,i,size,list,alarmListJia,col);
			addJian(first, i, size, list,  alarmListJian,alarmListZhuan,col);
		}
		double lat1=Double.valueOf(first.get("x"));
		double lng1=Double.valueOf(first.get("y"));
		double maxSpeed=Double.valueOf(first.get("s"));
		setSpeedBean(speedBean,maxSpeed);
		Gps Gps1=PositionUtil.Gps84(Double.valueOf(lat1), Double.valueOf(lng1));
		i++;
		String preT=st;
		
		for(;i<size;i++){
			Map<String,String> gps=list.get(i);
			if(gps.get("invalid")!=null) {
				continue;
			}
			 if(!gps.get("t").equals("")) {
				   et.setLength(0);
				   et.append(gps.get("t"));
			   }else {
				   log.error("时间为空====错误行程号："+uhIDs);
				   continue;
			   }
			 if((preT.substring(0, 19)).equals((gps.get("t").substring(0, 19)))) {
				 continue;
			 }
			 preT=gps.get("t");
			double lat2=Double.valueOf(gps.get("x"));
			double lng2=Double.valueOf(gps.get("y"));
			double s=Double.valueOf(gps.get("s"));
			setSpeedBean(speedBean,s);
			Gps Gps2=PositionUtil.Gps84(lat2, lng2);
		    Double d=PositionUtil.distance(Gps1.getLng(),Gps1.getLat(),Gps2.getLng(),Gps2.getLat());
			   distance+=d;
//			   out.append(gps.get("t")+"==="+d+"\r\n");
			   Gps1=Gps2;
			   
			   if(Integer.valueOf(gps.get("h"))<col.getGpsHa().intValue()) {//h值小于100的才进行计算
				   addJia(gps,i,size,list,alarmListJia,col);
				   boolean isTurn=addZhuan(gps, i, size, list, alarmListZhuan,col);
				   if(!isTurn){
					   addJian(gps, i, size, list, alarmListJian,alarmListZhuan,col);
				   }
			   }
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
		
		rtMap.put("speed120To129Times", speedBean.getSpeed120To129Times());
		rtMap.put("speed130To139Times", speedBean.getSpeed130To139Times());
		rtMap.put("speed140To149Times", speedBean.getSpeed140To149Times());
		rtMap.put("speed150To159Times", speedBean.getSpeed150To159Times());
		rtMap.put("speed160UpTimes", speedBean.getSpeed160UpTimes());
		return rtMap;
	}
	
	public static  void addJia(Map<String,String> first,int i,int size,List<Map<String,String>> list,List<Map<String,Object>> alarmList,ColProperties col){
		double s=Double.valueOf(first.get("s"));
		String t=first.get("t");
		if(s<=col.getJiaSpeedThreshold().intValue()){
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
				if(ds<=col.getJiaPoint2Value().doubleValue()){
					j++;
					continue;
				}else{
					break;
				}
			}else if(p==3){
				if(ds<=col.getJiaPoint3Value().doubleValue()){
					//急加
					boolean  hadEvent=hadEventInXSecond(alarmList,col.getJiaIntervalSecond().intValue(),t);
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
	public static boolean addJian(Map<String,String> curPoint,int now,int total,List<Map<String,String>> list,List<Map<String,Object>> alarmList,List<Map<String,Object>> alarmListZhuan,ColProperties col){
		double s=Double.valueOf(curPoint.get("s"));
		String t=(curPoint.get("t"));
		double s1=s;
		String t1=t;
		double d1=col.getJianPointOne1Value().doubleValue();double d2=col.getJianPointOne2Value().doubleValue();
		if(s>=col.getJianTwoSpeed().intValue()){
			d1=col.getJianPointTwo1Value().doubleValue(); d2=col.getJianPointTwo2Value().doubleValue();
		}
		if(s>=col.getJianOneSpeed().intValue()){
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
					  if(t2==null || t1==null || t2.equals("") || t1.equals("")) { return false;}
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
						boolean  hadEvent=hadEventInXSecond(alarmList,col.getJianIntervalSecond().intValue(),t);
						if(hadEvent) {
							return false;//有过15s内的急减，跳出。
						}
						//急减之前判断是否是急转，如果是，则急转优先
						boolean zhuan=jugeZhuanInJian( curPoint, list, now,total, alarmListZhuan,col);
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
	public  static boolean  addZhuan(Map<String,String> first,int now,int size,List<Map<String,String>> list,
			List<Map<String,Object>> alarmList,ColProperties col){
		int tempTotal=size-col.getZhuanNextPointNum().intValue();
		boolean isZhuan=false;
		String t=first.get("t");

		if(now<col.getZhuanPrePointNum().intValue() || now>=tempTotal){
			return false;
		}
		double s=Double.valueOf(first.get("s"));
		if(s<col.getZhuanSpeedThreshold().intValue()){
			return false;
		}
		String course=first.get("c");	
		String nextCoure=  list.get(now+1).get("c");
		String t2=list.get(now+1).get("t");
		try {
			long dif=0;
			dif = DateTools.getDiffTimeS(t2, t);
			if(dif>col.getZhuanIgnoreTwoPointTime().intValue()) {//2点差8秒的，就不用算急转了
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		Double d=Math.abs(Double.valueOf(course) -Double.valueOf(nextCoure));
		if(d>180 ){
			d=360-d;
		}
		if(d<col.getIgnoreTwoPointAngle().intValue()){
			return false;
		}
		double sub8PointCourse=zhuanAngle(now,list,col);
		if(sub8PointCourse>col.getDealPointsAngle().intValue()){
			
			boolean hadEvent=hadEventInXSecond(alarmList, col.getZhuanIntervalSecond().intValue(), t);
			if(hadEvent) {
				return false;
			}
			double s2=Double.valueOf(list.get(now+1).get("s"));
			if(s2==0) {
				return false;
			}
			Double ds=BigDecimal.valueOf(s).divide(BigDecimal.valueOf(s2),3,RoundingMode.HALF_UP).doubleValue();
			
			if(ds<col.getZhuanJugeBySpeedRate().doubleValue()) {
				isZhuan= true;
			}else {
				if(s>col.getZhuanJugeSpeedAndAngleSpeed().intValue() && d>=col.getZhuanJugeSpeedAndAngleAngle().intValue()){//转角下速度与角度的判断
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
	
	public static double zhuanAngle(int now,List<Map<String,String>> list,ColProperties col) {
		int i=now-col.getZhuanPrePointNum().intValue();
		int j=now+col.getZhuanNextPointNum().intValue();
		
		double max=Double.valueOf(list.get(i).get("c"));
		double min=max;
		i=i+1;
		for(;i<=j;i++) {
			double value=Double.valueOf(list.get(i).get("c"));
			if(value>max){
				max=value;
			}else if(value<min){
				min=value;
			}
		}
		double sub9PointCourse=max-min;
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
	
	public static boolean jugeZhuanInJian(Map<String,String> curPoint,List<Map<String,String>> list,int now,int total,List<Map<String,Object>> alarmListZhuan,
			ColProperties col) {
		//增加角度判断
		int tempTotal=total-col.getZhuanNextPointNum().intValue();
		if(now>=col.getZhuanPrePointNum().intValue() && now<tempTotal){
			String t=curPoint.get("t");
			double sub8PointCourse=zhuanAngle(now,list,col);
			if(sub8PointCourse>col.getDealPointsAngle().intValue()){
				String course=curPoint.get("c");	
				String nextCoure=  list.get(now+1).get("c");
				Double d=Math.abs(Double.valueOf(course) -Double.valueOf(nextCoure));
				if(d>180 ){
					d=360-d;
				}
				if(d<col.getIgnoreTwoPointAngle().intValue()){
					return false;
				}
				boolean  hadEvent=hadEventInXSecond(alarmListZhuan,col.getZhuanIntervalSecond().intValue(),t);
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
	
	
	
	
	 
//	public static double sub9PointCourse25(double d6,double d7,double d8,double d9,double d10,double d11,double d12,double d13){
//		double max=d6;
//		double min=d6;
//		
//		if(d6>max){
//			max=d6;
//		}else if(d6<min){
//			min=d6;
//		}
//		if(d7>max){
//			max=d7;
//		}else if(d7<min){
//			min=d7;
//		}
//		if(d8>max){
//			max=d8;
//		}else if(d8<min){
//			min=d8;
//		}
//		if(d9>max){
//			max=d9;
//		}else if(d9<min){
//			min=d9;
//		}
//		if(d10>max){
//			max=d10;
//		}else if(d10<min){
//			min=d10;
//		}
//		if(d11>max){
//			max=d11;
//		}else if(d11<min){
//			min=d11;
//		}
//		if(d12>max){
//			max=d12;
//		}else if(d12<min){
//			min=d12;
//		
//		}
//		if(d13>max){
//			max=d13;
//		}else if(d13<min){
//			min=d13;
//		}
//		return (max-min);
//	}
	
	/*==========================================================*/
	/**
	 * 下面做個測試函數
	 */
//	public static Map<String,Object> getGpsValuesTest(List<Map<String,String>> list,String uhIDs){
//		dealValidGps(list);
//		for(Map<String,String> gps:list) {
//			if(gps.get("invalid")!=null) {
//				System.out.println("搗亂數據:"+gps.get("s")+"==="+gps.get("invalid"));
//			}
//		}
//		return null;
//	}
	public static String txt2String(String path){
		 File file = new File(path);
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }	
	public static void writeTxt(StringBuffer sb) throws IOException {
    /* 写入Txt文件 */  
    File writename = new File("d:\\output.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
    writename.createNewFile(); // 创建新文件  
    BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
    out.write(sb.toString()); // \r\n即为换行  
    out.flush(); // 把缓存区内容压入文件  
    out.close(); // 最后记得关闭文件  
	}
 
}
