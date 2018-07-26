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
	public final static int EVENTTYPE_POWER_INDEX_GET = 2;// 首页拾取
	public final static int EVENTTYPE_POWER_TRIP = 4;// 行程
	public final static int EVENTTYPE_POWER_SIGNIN = 1;// 签到
	public final static int EVENTTYPE_POWER_STEAL = 3;// 对战场家族拾取
	public final static int EVENTTYPE_POWER_AIRDROP=5; //???
	public final static int EVENTTYPE_POWER_GAMEEND_USER=6; //家族挑战获胜分配
	public final static int EVENTTYPE_POWER_CHALLGE_REDUCE=8; //挑战家族选择消耗

	public final static int EVENTTYPE_POWER_TRIPBE_STOLE1 = 11;// 行程动力偷取
	public final static int EVENTTYPE_POWER_TRIPBE_STOLE2 = 12;// 行程动力被偷取
	
	public final static int EVENTTYPE_POWER_FREE_RIDE = 13;// 搭乘顺风车获取
	
	public final static int EVENTTYPE_POWER_GET_TICKET= 14;// 收取贴条罚金
	
	public final static int EVENTTYPE_POWER_PAY_TICKET = 15;// 缴罚单扣除
	public final static int EVENTTYPE_POWER_HELPPAY_TICKET = 16;// 帮别人缴罚单扣除
	public final static int EVENTTYPE_POWER_CLUB_BONUS = 17;// 俱乐部奖励动力--针对俱乐部经理

	public final static int EVENTTYPE_POWER_SELL_COMPONENET=18;//卖道具
	public final static int EVENTTYPE_POWER_APPLY_PRAYING=19;//祈愿赠送获得
	
	
	public final static int EVENTTYPE_POWER_MISSION_STEAL_ID = 101;// 主页偷取小车动力5次
	public final static int EVENTTYPE_POWER_MISSION_LIKE_ID = 102;// 在其他玩家车库点赞3次
	public final static int EVENTTYPE_POWER_MISSION_TRIP_ID = 103;// 今日出行1次
	public final static int EVENTTYPE_POWER_MISSION_SHARE_ID = 104;// 分享链车给好友
	public final static int EVENTTYPE_POWER_MISSION_LOGIN_ID = 105; // 连续登录第s日（每天增加5点，上限150点）
	public final static int EVENTTYPE_POWER_MISSION_IDCARD_ID = 106; // 完成实名认证
	public final static int EVENTTYPE_POWER_MISSION_LICENSE_ID = 107; // 完成行驶证认证
	public final static int EVENTTYPE_POWER_MISSION_JOINHOME_ID = 108; // 加入一个俱乐部
	public final static int EVENTTYPE_POWER_MISSION_CREATREHOME_ID = 109;// 创建一个俱乐部
	public final static int EVENTTYPE_POWER_MISSION_SILVER_ID = 110;// 创建的俱乐部达到白银
	public final static int EVENTTYPE_POWER_MISSION_GOLD_ID = 111;// 创建的俱乐部达到黄金
	public final static int EVENTTYPE_POWER_MISSION_DIAMOND_ID = 112;// 创建的俱乐部达到钻石
	public final static int EVENTTYPE_POWER_MISSION_CHAMPION_ID = 113; // 创建的俱乐部达到冠军
	public final static int EVENTTYPE_POWER_MISSION_SCORE_ID = 114; // 单次行程10公里以上时获得100分评分
	public final static int EVENTTYPE_POWER_MISSION_KM500_ID = 115; // 累计里程500公里
	public final static int EVENTTYPE_POWER_MISSION_SCORETIMES5_ID = 116; // 连续5次行程获得100分
	public final static int EVENTTYPE_POWER_MISSION_AD = 117; // 观看广告
	public final static int EVENTTYPE_POWER_MISSION_INTERACTAD = 118; // 参与活动
	//个人钻石表
	public final static int EVENTTYPE_DAIMOND_DAYPOWER_USER = 1;// 每日分配
	public final static int EVENTTYPE_DAIMOND_GAMEEND_USER = 2;// PK结束家族分配
	public final static int EVENTTYPE_BUY = 3;//购买消费
	public final static int EVENTTYPE_DAIMOND_SEASONEND_USER = 4;// 比赛赛季结束家族分配      
	public final static int EVENTTYPE_EARN = 5;// 交易收入
	public final static int EVENTTYPE_DAIMOND_AUCTION_BUY = 10;//竞拍消费
	public final static int EVENTTYPE_DAIMOND_AUCTION_EARN = 11;//竞拍收入
	
	
	//竞拍事件表
	public final static int EVENTTYPE_FREEZE = 6;// 竞拍冻结
	public final static int EVENTTYPE_THAW = 7;// 竞拍解冻
	
	//家族钻石
	public final static int EVENTTYPE_DAIMOND_GAMEEND = 1;// 比赛获取
	public final static int EVENTTYPE_DAIMOND_DISTR = 2;// PK比赛分配消耗
	public final static int EVENTTYPE_DAIMOND_SEASONEND = 3;// 赛季获取
	public final static int EVENTTYPE_DAIMOND_SEASONEND_DISTR = 4;// 赛季分配消耗
	//家族动力
	public final static int EVENTTYPE_POWER_TRIP_FAMILY = 1;// 行程贡献
	public final static int EVENTTYPE_POWER_GAMEEND_FAMILY = 2;// 比赛获取
	public final static int EVENTTYPE_POWER_DISTR_FAMILY = 3;// 比赛分配消耗
	
	
	public final static int RECORDTYPE_2 = 2;// 减少
	public final static int RECORDTYPE_1 = 1;// 增加
	static {
		
		UserDiamondsEventMap.put(EVENTTYPE_DAIMOND_DAYPOWER_USER, "每日动力兑换");
		UserDiamondsEventMap.put(EVENTTYPE_DAIMOND_GAMEEND_USER, "俱乐部每日PK获取");
		UserDiamondsEventMap.put(EVENTTYPE_BUY, "购买消耗");
		UserDiamondsEventMap.put(EVENTTYPE_DAIMOND_SEASONEND_USER, "赛季获胜获取");
		UserDiamondsEventMap.put(EVENTTYPE_EARN, "交易收入");
//		UserDiamondsEventMap.put(EVENTTYPE_DAIMOND_AUCTION_BUY, "竞拍消费");
//		UserDiamondsEventMap.put(EVENTTYPE_DAIMOND_AUCTION_EARN, "竞拍收入");
		UserDiamondsEventMap.put(EVENTTYPE_DAIMOND_AUCTION_BUY, "兑换消费");
		UserDiamondsEventMap.put(EVENTTYPE_DAIMOND_AUCTION_EARN, "奖励兑换");
		
		
		UserPowerEventMap.put(EVENTTYPE_POWER_SIGNIN, "每日签到奖励");
		UserPowerEventMap.put(EVENTTYPE_POWER_INDEX_GET, "首页小车拾取");
		UserPowerEventMap.put(EVENTTYPE_POWER_STEAL, "停车场偷取");
		UserPowerEventMap.put(EVENTTYPE_POWER_TRIP, "行程中获取");
		UserPowerEventMap.put(EVENTTYPE_POWER_GAMEEND_USER, "俱乐部挑战获胜");
		UserPowerEventMap.put(EVENTTYPE_POWER_CHALLGE_REDUCE, "挑选对战家族");

		UserPowerEventMap.put(EVENTTYPE_POWER_TRIPBE_STOLE1, "行程动力偷取");
		UserPowerEventMap.put(EVENTTYPE_POWER_TRIPBE_STOLE2, "行程动力被偷取");
		UserPowerEventMap.put(EVENTTYPE_POWER_FREE_RIDE, "搭乘顺风车获取");
		UserPowerEventMap.put(EVENTTYPE_POWER_GET_TICKET, "收取贴条罚金");
		UserPowerEventMap.put(EVENTTYPE_POWER_PAY_TICKET, "缴纳罚单");
		UserPowerEventMap.put(EVENTTYPE_POWER_HELPPAY_TICKET, "代缴罚单");
		UserPowerEventMap.put(EVENTTYPE_POWER_CLUB_BONUS, "俱乐部奖励");
		UserPowerEventMap.put(EVENTTYPE_POWER_SELL_COMPONENET, "配件卖出");
		UserPowerEventMap.put(EVENTTYPE_POWER_APPLY_PRAYING, "祈愿赠送");
		
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_STEAL_ID, "完成任务-君子爱财");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_LIKE_ID, "完成任务-赞不绝口");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_TRIP_ID, "完成任务-出行打卡");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_SHARE_ID, "完成任务-分享有礼");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_LOGIN_ID, "完成任务-持之以恒");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_IDCARD_ID, "完成任务-有名有牌面");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_LICENSE_ID, "完成任务-皇家认证");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_JOINHOME_ID, "完成任务-初入俱乐部");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_CREATREHOME_ID, "完成任务-拉帮结派");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_SILVER_ID, "完成任务-实力证明一");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_GOLD_ID, "完成任务-实力证明二");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_DIAMOND_ID, "完成任务-实力证明三");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_CHAMPION_ID, "完成任务-实力证明四");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_SCORE_ID, "完成任务-稳定驾驶");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_KM500_ID, "完成任务-一路相伴");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_SCORETIMES5_ID, "完成任务-满分司机");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_AD, "观看广告奖励");
		UserPowerEventMap.put(EVENTTYPE_POWER_MISSION_INTERACTAD, "完成活动奖励");

	}
}
