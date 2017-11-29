package com.idata365.col.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaticDatas {
	public static Map<String,Object> UserConfigDefault=new HashMap<String,Object> ();
	static {
		UserConfigDefault.put("collectionEnable", 1);
		UserConfigDefault.put("supportSegment", 0);
		UserConfigDefault.put("sensorRate", 500);
		UserConfigDefault.put("isCollectMotionDatas", 1);
		UserConfigDefault.put("regionDistanceNomal", 30.00);
		UserConfigDefault.put("locationStartSpeed", 3);
		UserConfigDefault.put("locationStartCount", 2);
		UserConfigDefault.put("locationEndSpeed", 2);
		UserConfigDefault.put("locationEndCount", 15);
		UserConfigDefault.put("locationDistanceFilter", 2);
		UserConfigDefault.put("analyseSpeedForDistanceFilter", 1);
		UserConfigDefault.put("stepEndCount", 20);
		UserConfigDefault.put("limitSpeedEndTimes", 300);
		UserConfigDefault.put("coordinateSet", new ArrayList<Object>());
	}
}
