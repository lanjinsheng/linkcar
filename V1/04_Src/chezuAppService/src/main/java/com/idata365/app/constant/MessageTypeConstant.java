package com.idata365.app.constant;

public class MessageTypeConstant {
	//系统消息
	public static final int SystemType=1;
	public static final int SystemType_Reg=1;
	public static final int SystemType_Exchange=2;
	public static final int SystemType_Exchange_Ok=3;
	public static final int SystemType_VerifyIDCard_Ok=4;
	
	//个人消息
	public static final int PersonType=2;
	public static final int PersonType_Achievement=1;
	public static final int PersonType_Prop_Give=2;
	public static final int PersonType_Prop_Rev=3;
	public static final int PersonType_Invitation_Ok=4;
	
	//家族消息
	public static final int FamilyType=3;
	public static final int FamilyType_Apply=1;
	public static final int FamilyType_Pass=2;
	public static final int FamilyType_Fail=3;
	public static final int FamilyType_Kickout=4;
	public static final int FamilyType_Challenge=5;
	public static final int FamilyType_Reward=6;
	public static final int FamilyType_SeasonReward=7;
	
	//消息不跳转
	public static final int MessageUrl_Href_False=2;
	public static final int MessageUrl_Href_App=0;
	public static final int MessageUrl_Href_H5=1;
}
