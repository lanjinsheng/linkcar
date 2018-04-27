package com.idata365.app.util;

import java.util.HashMap;
import java.util.Map;

public class StaticDatas {
	
//	"小型自动档汽车":1; "小型汽车":2; "中型客车":3; "大型客车":4; "牵引车":5; "城市公交车":6; "大型货车":7; "低速载货汽车":8; "三轮汽车":9;
//	"非营运" : "FYY"；"营运"："YY"
	
	public static Map<String,String> VEHILCE=new HashMap<String,String> ();
	static {
		VEHILCE.put("1", "小型自动档汽车");
		VEHILCE.put("2", "小型汽车");
		VEHILCE.put("3", "中型客车");
		VEHILCE.put("4", "大型客车");
		VEHILCE.put("5", "牵引车");
		VEHILCE.put("6", "城市公交车");
		VEHILCE.put("7", "大型货车");
		VEHILCE.put("8", "低速载货汽车");
		VEHILCE.put("9", "三轮汽车");
		
		VEHILCE.put( "小型自动档汽车","1");
		VEHILCE.put( "小型汽车","2");
		VEHILCE.put("中型客车","3");
		VEHILCE.put("大型客车","4");
		VEHILCE.put("牵引车","5");
		VEHILCE.put("城市公交车","6");
		VEHILCE.put( "大型货车","7");
		VEHILCE.put("低速载货汽车","8");
		VEHILCE.put("三轮汽车","9");
		
	}
	
	public static Map<String,String> VEHILCE_USETYPE=new HashMap<String,String>();
	static {
		VEHILCE_USETYPE.put("FYY", "非营运");
		VEHILCE_USETYPE.put("YY", "营运");
		VEHILCE_USETYPE.put( "非营运","FYY");
		VEHILCE_USETYPE.put("营运","YY");
	}
	
}
