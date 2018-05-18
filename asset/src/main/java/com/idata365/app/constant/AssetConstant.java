package com.idata365.app.constant;

import java.util.HashMap;
import java.util.Map;

public class AssetConstant {
	public static Map<Integer,String> UserDiamondsEventMap=new HashMap<Integer,String>();
	public static Map<Integer,String> UserPowerEventMap=new HashMap<Integer,String>();
	


	public final static int EventType_Power_Index_Get = 2;// 首页拾取
	public final static int EventType_Power_Trip = 4;// 行程
	public final static int EventType_Power_SignIn = 1;// 签到
	public final static int EventType_Power_Steal = 3;// 偷取
	
	public final static int EventType_Daimond_DayPower_User = 1;// 每日分配
	public final static int EventType_Daimond_GameEnd_User = 2;// PK结束家族分配
	public final static int EventType_Buy = 3;//购买消费
	public final static int EventType_Daimond_SeasonEnd_User = 4;// 比赛赛季结束家族分配
	
	//家族
	public final static int EventType_Daimond_GameEnd = 1;// 比赛获取
	public final static int EventType_Daimond_Distr = 2;// PK比赛分配消耗
	public final static int EventType_Daimond_SeasonEnd = 3;// 赛季获取
	public final static int EventType_Daimond_SeasonEnd_Distr = 4;// 赛季分配消耗

	public final static int RecordType_2 = 2;// 减少
	public final static int RecordType_1 = 1;// 增加
	static {
		
		UserDiamondsEventMap.put(EventType_Daimond_DayPower_User, "每日动力兑换");
		UserDiamondsEventMap.put(EventType_Daimond_GameEnd_User, "家族每日PK获取");
		UserDiamondsEventMap.put(EventType_Buy, "购买消耗");
		UserDiamondsEventMap.put(EventType_Daimond_SeasonEnd_User, "赛季获胜获取");
		
		UserPowerEventMap.put(EventType_Power_SignIn, "每日签到奖励");
		UserPowerEventMap.put(EventType_Power_Index_Get, "首页小车拾取");
		UserPowerEventMap.put(EventType_Power_Steal, "家族对战偷取");
		UserPowerEventMap.put(EventType_Power_Trip, "行程中获取");
	}
}
