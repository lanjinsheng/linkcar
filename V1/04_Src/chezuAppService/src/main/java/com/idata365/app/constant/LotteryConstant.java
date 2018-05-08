package com.idata365.app.constant;

public class LotteryConstant
{
	public static final int SHACHE_LOTTERY = 1;
	
	public static final int CHELUNTAI_LOTTERY = 2;
	
	public static final int FADONGJI_LOTTERY = 3;
	
	public static final int HONGNIU_LOTTERY = 4;
	
	public static final int YESHI_LOTTERY = 5;
	
	public static final int ZENGYA_LOTTERY = 6;
	
	public static final int MAZHA_LOTTERY = 7;
	
	public static final int ZHITIAO_LOTTERY = 8;
	
	public static String getLotteryNameByRewardId(int rewardId) {
		if(rewardId==SHACHE_LOTTERY) {
			return "刹车片";
		}else if(rewardId==CHELUNTAI_LOTTERY) {
			return "车轮胎";
		}else if(rewardId==FADONGJI_LOTTERY) {
			return "发动机";
		}else if(rewardId==HONGNIU_LOTTERY) {
			return "红牛";
		}else if(rewardId==YESHI_LOTTERY) {
			return "夜视镜";
		}else if(rewardId==ZENGYA_LOTTERY) {
			return "增压器";
		}else if(rewardId==ZHITIAO_LOTTERY) {
			return "贴条";
		}else {
			return "神秘道具";
		}
	}
	
}
