package com.idata365.app.constant;

public class MessageTypeConstant {
	//系统消息
	public static final int SystemType=1;
	public static final int SystemType_Reg=1;
	public static final int SystemType_Exchange=2;
	public static final int SystemType_Exchange_Ok=3;
	public static final int SystemType_VerifyIDCard_Ok=4;
	public static final int SystemType_VerifyVehicleTravel_Ok=5;
	//======競拍
	public static final int SystemType_Auction_Fail=5;
	public static final int SystemType_Auction_Succ=6;
	public static final int SystemType_Auction_Exchange=7;
	
	//个人消息
	public static final int PersonType=2;
	public static final int PersonType_Achievement=1;
	public static final int PersonType_Prop_Give=2;
	public static final int PersonType_Prop_Rev=3;
	public static final int PersonType_Invitation_Ok=4;
	
	public static final int PersonType_CarpoolApply=5;
	public static final int PersonType_CarpoolApply_Pass=6;
//	public static final int PersonType_Intive_Fail=5;
//	public static final int PersonType_Reveice_Fail=6;
//	public static final int PersonType_REVEICE_INVITE=7;
	
	//俱乐部消息
	public static final int FamilyType=3;
	public static final int FamilyType_Apply=1;
	public static final int FamilyType_Pass=2;
	public static final int FamilyType_Fail=3;
	public static final int FamilyType_Kickout=4;
	public static final int FamilyType_Challenge=5;
	public static final int FamilyType_Reward=6;
	public static final int FamilyType_SeasonReward=7;
	public static final int FamilyType_Intive_Fail=8;
	public static final int FamilyType_Reveice_Fail=9;
	public static final int FamilyType_REVEICE_INVITE=10;
	public static final int FamilyType_Quit_Family=11;
	public static final int FamilyType_ApplyPraying=12;	
	public static final int FamilyType_RequestComponent=13;
	public static final int FamilyType_ApplyGiveLog=14;
	//消息不跳转
	public static final int MessageUrl_Href_False=2;
	public static final int MessageUrl_Href_App=0;
	public static final int MessageUrl_Href_H5=1;
}
