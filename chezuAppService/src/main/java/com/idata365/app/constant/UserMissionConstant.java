package com.idata365.app.constant;

public class UserMissionConstant {
//	public static Map<Integer,DicUserMission> userMissionMap=new HashMap<Integer,DicUserMission>();
	
	// 主页偷取小车动力5次
	public static final int STEAL_ID = 1;
	// 在其他玩家车库点赞3次
	public static final int LIKE_ID = 2;
	// 今日出行1次
	public static final int TRIP_ID = 3;
	// 分享好车族给好友
	public static final int SHARE_ID = 4;
	// 连续登录第s日（每天增加5点，上限150点）
	public static final int LOGIN_ID = 5;
	// 完成实名认证
	public static final int IDCARD_ID = 6;
	// 完成行驶证认证
	public static final int LICENSE_ID = 7;
	// 加入一个俱乐部
	public static final int JOINHOME_ID = 8;
	// 创建一个俱乐部
	public static final int CREATREHOME_ID = 9;
	// 创建的俱乐部达到白银
	public static final int SILVER_ID = 10;
	// 创建的俱乐部达到黄金
	public static final int GOLD_ID = 11;
	// 创建的俱乐部达到钻石
	public static final int DIAMOND_ID = 12;
	// 创建的俱乐部达到冠军
	public static final int CHAMPION_ID = 13;
	// 单次行程10公里以上时获得100分评分
	public static final int SCORE_ID = 14;
	// 累计里程500公里
	public static final int KM500_ID = 15;
	// 连续5次行程获得100分
	public static final int SCORETIMES5_ID = 16;
	
	
	 /**
     * 
     * @param familyType
     * @return
     */
//	public static DicUserMission getDicUserMission(Integer missionId){
//		if(userMissionMap.size()==0){
//			DicService dicService=SpringContextUtil.getBean("dicService", DicService.class);
//			List<DicUserMission> list=dicService.getDicUserMission();
//			for(DicUserMission dic:list){
//				userMissionMap.put(dic.getMissionId(), dic);
//			}
//		}
//		return userMissionMap.get(missionId);
//			
//	}
}
