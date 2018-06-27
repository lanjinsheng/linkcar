package com.idata365.app.constant;

import java.util.HashMap;
import java.util.Map;

public class AssetConstant {
	
	public final static int DAY_DAIMONDS_FOR_POWER=500;
	public final static int DAY_DAIMONDS_FOR_PK=480;
	public final static int DAY_DAIMONDS_FOR_SEASON=20;
	
	public final static int DAY_DAIMONDS_FOR_POWER_ALLNET=980;
	
	public static Map<Integer,String> UserDiamondsEventMap=new HashMap<Integer,String>();
	public static Map<Integer,String> UserPowerEventMap=new HashMap<Integer,String>();
	

	//个人动力日志
	public final static int EventType_Power_Index_Get = 2;// 首页拾取
	public final static int EventType_Power_Trip = 4;// 行程
	public final static int EventType_Power_SignIn = 1;// 签到
	public final static int EventType_Power_Steal = 3;// 偷取
	public final static int EventType_Power_Airdrop=5; //家族挑战获胜分配
	public final static int EventType_Power_GameEnd_User=6; //家族挑战获胜分配
	public final static int EventType_Power_MissionPrize=7; //任务奖励
	public final static int EventType_Power_Challge_Reduce=8; //挑战家族选择消耗
	//个人钻石表
	public final static int EventType_Daimond_DayPower_User = 1;// 每日分配
	public final static int EventType_Daimond_GameEnd_User = 2;// PK结束家族分配
	public final static int EventType_Buy = 3;//购买消费
	public final static int EventType_Daimond_SeasonEnd_User = 4;// 比赛赛季结束家族分配      
	public final static int EventType_Earn = 5;// 交易收入
	public final static int EventType_Daimond_Auction_Buy = 10;//竞拍消费
	public final static int EventType_Daimond_Auction_Earn = 11;//竞拍收入
	
	
	//竞拍事件表
	public final static int EventType_Freeze = 6;// 竞拍冻结
	public final static int EventType_Thaw = 7;// 竞拍解冻
	
	//家族钻石
	public final static int EventType_Daimond_GameEnd = 1;// 比赛获取
	public final static int EventType_Daimond_Distr = 2;// PK比赛分配消耗
	public final static int EventType_Daimond_SeasonEnd = 3;// 赛季获取
	public final static int EventType_Daimond_SeasonEnd_Distr = 4;// 赛季分配消耗
	//家族动力
	public final static int EventType_Power_Trip_Family = 1;// 行程贡献
	public final static int EventType_Power_GameEnd_Family = 2;// 比赛获取
	public final static int EventType_Power_Distr_Family = 3;// 比赛分配消耗
	
	
	public final static int RecordType_2 = 2;// 减少
	public final static int RecordType_1 = 1;// 增加
	static {
		
		UserDiamondsEventMap.put(EventType_Daimond_DayPower_User, "每日动力兑换");
		UserDiamondsEventMap.put(EventType_Daimond_GameEnd_User, "家族每日PK获取");
		UserDiamondsEventMap.put(EventType_Buy, "购买消耗");
		UserDiamondsEventMap.put(EventType_Daimond_SeasonEnd_User, "赛季获胜获取");
		UserDiamondsEventMap.put(EventType_Earn, "交易收入");
		UserDiamondsEventMap.put(EventType_Daimond_Auction_Buy, "竞拍消费");
		UserDiamondsEventMap.put(EventType_Daimond_Auction_Earn, "竞拍收入");
		
		
		UserPowerEventMap.put(EventType_Power_SignIn, "每日签到奖励");
		UserPowerEventMap.put(EventType_Power_Index_Get, "首页小车拾取");
		UserPowerEventMap.put(EventType_Power_Steal, "停车场偷取");
		UserPowerEventMap.put(EventType_Power_Trip, "行程中获取");
		UserPowerEventMap.put(EventType_Power_GameEnd_User, "家族挑战获胜");
		UserPowerEventMap.put(EventType_Power_Challge_Reduce, "挑选对战家族");
		
		
		
	}
}
