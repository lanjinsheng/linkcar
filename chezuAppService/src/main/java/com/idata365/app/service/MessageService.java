package com.idata365.app.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.app.constant.AppMsgCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.constant.MessageTypeConstant;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.entity.ImMsg;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.TaskMessagePush;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.MessageMapper;
import com.idata365.app.mapper.TaskMessagePushMapper;
import com.idata365.app.partnerApi.ManagePushApi;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.PhoneUtils;
import com.idata365.app.util.ValidTools;

 

@Service
public class MessageService extends BaseService<MessageService>{
	private final static Logger LOG = LoggerFactory.getLogger(MessageService.class);
	public static final String  H5Host="http://product-h5.managingfootball.com/";
	public static final String  InviteMessage= AppMsgCode.InviteMessage.getMsg();
	public static final String  PassFamilyMessage=AppMsgCode.PassFamilyMessage.getMsg();
	public static final String  PassFamilyMessage2=AppMsgCode.PassFamilyMessage2.getMsg();
	public static final String  FailFamilyMessage=AppMsgCode.FailFamilyMessage.getMsg();
	public static final String  FailFamilyMessage2=AppMsgCode.FailFamilyMessage2.getMsg();
	public static final String  QuitFamilyMessage=AppMsgCode.QuitFamilyMessage.getMsg();
	public static final String  ReveiceInvite=AppMsgCode.ReveiceInvite.getMsg();
	//踢人
	public static final String KickMemberMessage=AppMsgCode.KickMemberMessage.getMsg();
	public static final String ChallengeMessage=AppMsgCode.ChallengeMessage.getMsg();
	//获奖
	public static final String RewardMessage=AppMsgCode.RewardMessage.getMsg();
	public static final String RewardPowerMessage=AppMsgCode.RewardPowerMessage.getMsg();

	//获奖
	public static final String SeasonRewardMessage=AppMsgCode.SeasonRewardMessage.getMsg();

	public static final String RegMessage=AppMsgCode.RegMessage.getMsg();
	public static final String TietiaoMessage=AppMsgCode.TietiaoMessage.getMsg();
	public static final String AchieveMessage=AppMsgCode.AchieveMessage.getMsg();
	public static final String KaijiangMessage=H5Host+"share/lottery.html";
	//兑换

	public static final String ShopMessage=AppMsgCode.ShopMessage.getMsg();
	public static final String NewAuctionForRecentFailPerson=AppMsgCode.NewAuctionForRecentFailPerson.getMsg();
	public static final String GoodsSendMessage=AppMsgCode.GoodsSendMessage.getMsg();

	public static final String AuctionRobbedMassage=AppMsgCode.AuctionRobbedMassage.getMsg();
	public static final String AuctionFailMassage=AppMsgCode.AuctionFailMassage.getMsg();
	public static final String AuctionSuccMassage=AppMsgCode.AuctionSuccMassage.getMsg();
	public static final String AuctionExchangeMassage=AppMsgCode.AuctionExchangeMassage.getMsg();
	public static final String AuctionRedPacketSuccMassage=AppMsgCode.AuctionRedPacketSuccMassage.getMsg();
	public static final String AuctionRedPacketExchangeMassage=AppMsgCode.AuctionRedPacketExchangeMassage.getMsg();

	
	//证件审核通过
	public static final String IDCardMessage=AppMsgCode.IDCardMessage.getMsg();
	//道具赠送
	public static final String LotterySendMessage=AppMsgCode.LotterySendMessage.getMsg();
	public static final String LotteryRecMessage=AppMsgCode.LotteryRecMessage.getMsg();
	
	//帮助页面跳转
	public static final String  HelpUrl="com.idata365.haochezu://GuideHelps.push";
	public static final String  InviteMessageUrl="com.idata365.haochezu://check.push?msgId=%s";
	public static final String  RecruitMessageUrl="com.idata365.haochezu://familyInvite.push?msgId=%s";
	public static final String  InvitePassMessageUrl="com.idata365.haochezu://family.push?isFamilyMine=1&isHomeEnter=0&familyId=%s";
	
	public static final String  TietiaoMessageUrl="com.idata365.haochezu://pasteNote.push?familyId=%s";
	public static final String AchieveMessageUrl="com.idata365.haochezu://achievementDetails.push?achieveId=%s&titleName=%s";
	//聊天室
	public static final String ImMessageUrl="com.idata365.haochezu://chatsMain.push?familyId=%s";
	
	//我的奖励(购买记录)
	public static final String RewardNotes="com.idata365.haochezu://rewardNotes.push";
	
	//(证件审核)
	public static final String CardNotes="com.idata365.haochezu://AuthenticationResultVC.push?status=1";
	
	//道具赠送通知
	public static final String PropsSend="com.idata365.haochezu://propsReceive.push?msgId=%s";
	//俱乐部钻石分配记录
//	public static final String FamilyDiamondsDistr="com.idata365.haochezu://DEPNotes.push?enterType=0&familyId=%s";
	
	public static final String FamilyFightHistoryDetailUrl="com.idata365.haochezu://FightHistoryDetail.push?familyId=%s&fightTime=%s";
	public static final String RegMessageUrl=H5Host+"share/home.html";
	public static final String KaijiangMessageUrl=H5Host+"share/lottery.html";
	
	public static final String MyAuctionUrl="com.shujin.haochezu://AuctionNotes.push";           
	public static final String MyAuctionInfoApendUrl="com.shujin.haochezu://convertAddress.push?type=%s&convertId=%s";
	public static final String MyAuctionRedPacketUrl="com.shujin.haochezu://RedPacket.push?convertId=%s";
	//順風車推送
	public static final String CarpoolUrl="com.shujin.haochezu://CarPool.push?tab=%s";
	public static final String CarpoolApply=AppMsgCode.CarpoolApply.getMsg();
	public static final String CarpoolApplyPass=AppMsgCode.CarpoolApplyPass.getMsg();
	
	//配件零件申请审批消息
	public static final String componentUrl= "com.shujin.haochezu://componentHandle.push?msgId=%s";
	public static final String RequestComponent= AppMsgCode.RequestComponent.getMsg();
	public static final String ApplyGiveLog= AppMsgCode.ApplyGiveLog.getMsg();
	public static final String ApplyPraying= AppMsgCode.ApplyPraying.getMsg();
	
	@Autowired
	SystemProperties systemProperties;
	public static final Map<String,String> MessageImgs=new HashMap<String,String>();
	static {
		MessageImgs.put("1", H5Host+"appImgs/xitong.png");
		MessageImgs.put("2", H5Host+"appImgs/shenpi.png");
		MessageImgs.put("3", H5Host+"appImgs/tietiao.png");
		MessageImgs.put("4", H5Host+"appImgs/kaijiang.png");
		MessageImgs.put("5", H5Host+"appImgs/chengjiu.png");
	}
	@Autowired
	MessageMapper messageMapper;
	@Autowired
	ManagePushApi  managePushApi;
	@Autowired
	FamilyService familyService;
	@Autowired
	TaskMessagePushMapper taskMessagePushMapper;
	
	public MessageService() {
	}
	 /**
	  * 
	     * @Title: buildInviteMessage
	     * @Description: TODO(构造消息)
	     * @param @param fromUserId
	     * @param @param fromUserPhone
	     * @param @param fromUserNick
	     * @param @param toUserId
	     * @param @param familyInviteId
	     * @param @return    参数
	     * @return Message    返回类型
	     * @throws
	     * @author LanYeYe
	  */
	public Message buildMessage(Long fromUserId,String fromUserPhone,String fromUserNick,Long toUserId,Long familyInviteId,MessageEnum type) {
		Message message=new Message();
		switch(type) {
		case SYSTEM:
			break;
		case SYSTEM_REG:
			message.setFromUserId(fromUserId==null?0:fromUserId);
			message.setBottomText("");
			message.setChildType(MessageTypeConstant.SystemType_Reg);
			message.setContent(getRegMessageDesc());
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(MessageTypeConstant.SystemType);
			message.setPicture("");
			message.setTitle("欢迎您!");
			message.setToUserId(toUserId);
			message.setUrlType(0);
			message.setToUrl(HelpUrl);
			break;
		case INVITE_FAMILY:
			message.setFromUserId(fromUserId==null?0:fromUserId);
			message.setBottomText(AppMsgCode.M20005.getMsg());
			message.setChildType(MessageTypeConstant.FamilyType_Apply);
			message.setContent(getInviteMessageDesc(fromUserPhone,fromUserNick));
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(MessageTypeConstant.FamilyType);
			message.setPicture("");
			message.setTitle(AppMsgCode.M20006.getMsg());
			message.setToUserId(toUserId);
			message.setUrlType(0);
			message.setToUrl(getInviteMessageUrl(familyInviteId));
			break;
		case PASS_FAMILY:{
			FamilyResultBean f=familyService.findFamily(fromUserId); 
			message.setFromUserId(fromUserId==null?0:fromUserId);
			message.setBottomText("");
			message.setChildType(MessageTypeConstant.FamilyType_Pass);
			message.setContent(getPassMessageDesc(fromUserPhone,fromUserNick,f.getMyFamilyName()));
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(MessageTypeConstant.FamilyType);
			message.setPicture("");
			message.setTitle(AppMsgCode.M20004.getMsg());
			message.setToUserId(toUserId);
			message.setUrlType(0);
			message.setToUrl(getPassMessageUrl(f.getMyFamilyId()));
			break;}
		case PASS_FAMILY2:{
			FamilyResultBean f=familyService.findFamily(toUserId); 
			message.setFromUserId(fromUserId==null?0:fromUserId);
			message.setBottomText("");
			message.setChildType(MessageTypeConstant.FamilyType_Pass);
			message.setContent(getPassMessageDesc2(fromUserNick));
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(MessageTypeConstant.FamilyType);
			message.setPicture("");
			message.setTitle(AppMsgCode.M20002.getMsg());
			message.setToUserId(toUserId);
			message.setUrlType(0);
			message.setToUrl(getPassMessageUrl(f.getMyFamilyId()));
			break;}
		case FAIL_FAMILY:
			FamilyResultBean ff=familyService.findFamily(fromUserId);
			message.setFromUserId(fromUserId==null?0:fromUserId);
			message.setBottomText("");
			message.setChildType(MessageTypeConstant.FamilyType_Intive_Fail);
			message.setContent(getFailMessageDesc(ff.getMyFamilyName()));
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(MessageTypeConstant.FamilyType);
			message.setPicture("");
			message.setTitle(AppMsgCode.M20003.getMsg());
			message.setToUserId(toUserId);
			message.setUrlType(2);
			message.setToUrl("");
			break;
		case FAIL_FAMILY2:
			message.setFromUserId(fromUserId==null?0:fromUserId);
			message.setBottomText("");
			message.setChildType(MessageTypeConstant.FamilyType_Reveice_Fail);
			message.setContent(getFailMessageDesc2(fromUserNick));
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(MessageTypeConstant.FamilyType);
			message.setPicture("");
			message.setTitle(AppMsgCode.M20001.getMsg());
			message.setToUserId(toUserId);
			message.setUrlType(2);
			message.setToUrl("");
			break;
		case QUIT_FAMILY:
			message.setFromUserId(fromUserId==null?0:fromUserId);
			message.setBottomText("");
			message.setChildType(MessageTypeConstant.FamilyType_Quit_Family);
			message.setContent(getQuitMessageDesc(fromUserNick));
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(MessageTypeConstant.FamilyType);
			message.setPicture("");
			message.setTitle(AppMsgCode.M20007.getMsg());
			message.setToUserId(toUserId);
			message.setUrlType(2);
			message.setToUrl("");
			break;
		 default:
			break;
		}
		return message;
	}
	/**
	 * 
	    * @Title: buildShopMessage
	    * @Description: TODO(购买消息)
	    * @param @param fromUserId
	    * @param @param toUserId
	    * @param @param goodsName
	    * @param @return    参数
	    * @return Message    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Message buildShopMessage(Long fromUserId,Long toUserId,String goodsName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_Exchange);
		message.setContent(getShopMessageDesc(goodsName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20016.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_False);
		message.setToUrl("");
		return message;
	}

	public Message sendNoticeMsgToFailedAuctionPerson(Long toUserId,String goodsName) {
		Message message=new Message();
		message.setFromUserId(0L);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_NewAuctionForRecentFailPerson);
		message.setContent(getNewAuctionForRecentFailPerson(goodsName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20008.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(MyAuctionUrl);
		return message;
	}
	
	
	public Message buildAuctionFailMessage(Long fromUserId,Long toUserId,String goodsName,Long auctionGoodsId) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_Auction_Fail);
		message.setContent(String.format(AuctionFailMassage, goodsName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20009.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(MyAuctionUrl);
		return message;
	}

	public Message buildAuctionRobbedMessage(Long toUserId,String goodsName) {
		Message message=new Message();
		message.setFromUserId(0L);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_Auction_Robbed);
		message.setContent(String.format(AuctionRobbedMassage, goodsName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20010.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(MyAuctionUrl);
		return message;
	}

	public Message buildCarpoolApplyMessage(Long fromUserId,Long toUserId,String nickName,String carName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.PersonType_CarpoolApply);
		message.setContent(String.format(CarpoolApply, nickName,carName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.PersonType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20011.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(String.format(CarpoolUrl, 2));
		return message;
	}
	
	public Message buildCarpoolApplyPassMessage(Long fromUserId,Long toUserId,String nickName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.PersonType_CarpoolApply_Pass);
		message.setContent(String.format(CarpoolApplyPass, nickName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.PersonType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20012.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(String.format(CarpoolUrl, 1));
		return message;
	}
	
	public Message buildApplyPrayingMessage(Long fromUserId,Long toUserId,String nickName,String componentName,
			Long componentGiveLogId) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.FamilyType_ApplyPraying);
		message.setContent(String.format(ApplyPraying, nickName,componentName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.FamilyType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20013.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(String.format(componentUrl,componentGiveLogId));
		return message;
	}
	public Message buildRequestComponentMessage(Long fromUserId,Long toUserId,String nickName,String componentName,
			Long componentGiveLogId) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.FamilyType_RequestComponent);
		message.setContent(String.format(RequestComponent, nickName,componentName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.FamilyType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20014.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(String.format(componentUrl,componentGiveLogId));
		return message;
	}
	
	public Message buildApplyGiveLogMessage(Long fromUserId,Long toUserId,String componentName,Long componentGiveLogId) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.FamilyType_ApplyGiveLog);
		message.setContent(String.format(ApplyGiveLog,componentName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.FamilyType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20027.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(String.format(componentUrl,componentGiveLogId));
		return message;
	}
	public Message buildAuctionSuccMessage(Long fromUserId,Long toUserId,String goodsName,Integer auctionGoodsType,Long auctionGoodsId) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_Auction_Succ);
		
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20015.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		String convertId=String.valueOf(auctionGoodsId);
		String type=String.valueOf(auctionGoodsType);
		
		if(auctionGoodsType == 3) {
			//红包
			message.setContent(String.format(AuctionRedPacketSuccMassage, goodsName));
			message.setToUrl("");
		}else {
			message.setContent(String.format(AuctionSuccMassage, goodsName));
			message.setToUrl(String.format(MyAuctionInfoApendUrl,type,convertId));
		}
		return message;
	}
	public Message buildAuctionExchangeMessage(Long fromUserId,Long toUserId,String goodsName,Integer auctionGoodsType, Long auctionGoodsId) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_Auction_Exchange);
		
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20016.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		
		String convertId=String.valueOf(auctionGoodsId);
		if(auctionGoodsType == 3) {
			//红包
			message.setContent(String.format(AuctionRedPacketExchangeMassage, goodsName));
			message.setToUrl(String.format(MyAuctionRedPacketUrl,convertId));
		}else {
			message.setContent(String.format(AuctionExchangeMassage, goodsName));
			message.setToUrl(MyAuctionUrl);
		}
		return message;
	}
	
	/**
	 * 
	    * @Title: buildIDCardMessage
	    * @Description: TODO(身份证消息)
	    * @param @param 
	    * @param @param 
	    * @param @param 
	    * @param @return    参数
	    * @return Message    返回类型
	    * @throws
	    * @author lcc
	 */
	public Message buildIDCardMessage(Long userId, String userName, String cardNumber) {
		Message message=new Message();
		message.setFromUserId(0L);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_VerifyIDCard_Ok);
		message.setContent(getIDCardMessageDesc(userName,cardNumber));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20032.getMsg());
		message.setToUserId(userId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_False);
		message.setToUrl(CardNotes);
		return message;
	}

	/**
	 *
	 * @Title: buildRemindMessage
	 * @Description: TODO(提醒老板消息)
	 * @param @param
	 * @param @param
	 * @param @param
	 * @param @return    参数
	 * @return Message    返回类型
	 * @throws
	 * @author lcc
	 */
	public Message buildRemindMessage(Long userId, String userName, Long familyId) {
		Message message=new Message();
		message.setFromUserId(0L);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_VerifyIDCard_Ok);
		if (userName.equals("")) {
			message.setContent(AppMsgCode.M20035.getMsg());
		} else {
			userName.substring(0, userName.length());
			message.setContent(String.format(AppMsgCode.M20034.getMsg(),userName));
		}

		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20033.getMsg());

		message.setToUserId(userId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_False);
		message.setToUrl("lc://ChallengesViewController.push?familyId="+familyId);
		return message;
	}
	
	
	/**
	 * 
	    * @Title: buildVehicleTravelMessage
	    * @Description: TODO(行驶证消息)
	    * @param @param 
	    * @param @param 
	    * @param @param 
	    * @param @return    参数
	    * @return Message    返回类型
	    * @throws
	    * @author lcc
	 */
	public Message buildVehicleTravelMessage(Long userId, String userName, String cardNumber) {
		Message message=new Message();
		message.setFromUserId(0L);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_VerifyIDCard_Ok);
		message.setContent(getIDCardMessageDesc(userName,cardNumber));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20032.getMsg());
		message.setToUserId(userId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_False);
		message.setToUrl(CardNotes);
		return message;
	}
	/**
	 * 
	    * @Title: buildGoodsSendMessage
	    * @Description: TODO(商品发放消息)
	    * @param @param fromUserId
	    * @param @param toUserId
	    * @param @param goodsName
	    * @param @return    参数
	    * @return Message    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Message buildGoodsSendMessage(Long fromUserId,Long toUserId,String goodsName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_Exchange_Ok);
		message.setContent(getGoodsSendMessageDesc(goodsName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20028.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(RewardNotes);
		return message;
	}
	/**
	 * 
	    * @Title: buildLotterySendMessage
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param fromUserId
	    * @param @param toUserId
	    * @param @param goodsName
	    * @param @return    参数
	    * @return Message    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Message buildLotterySendMessage(String lotteryMigrateInfoId,Long fromUserId,Long toUserId,String fromUserName,String lotteryName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.PersonType_Prop_Give);
		message.setContent(getLotterySendMessageDesc(fromUserName,lotteryName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.PersonType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20031.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(String.format(PropsSend, lotteryMigrateInfoId));
		return message;
	}
	/**
	 * 
	    * @Title: buildLotteryRecMessage
	    * @Description: TODO(领取道具消息)
	    * @param @param fromUserId
	    * @param @param toUserId
	    * @param @param fromUserName
	    * @param @param lotteryName
	    * @param @return    参数
	    * @return Message    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Message buildLotteryRecMessage(Long fromUserId,Long toUserId,String fromUserName,String lotteryName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.PersonType_Prop_Give);
		message.setContent(getLotterySendMessageDesc(fromUserName,lotteryName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.PersonType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20030.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl("");
		return message;
	}
	
	/**
	 * 
	    * @Title: buildKickMemberMessage
	    * @Description: TODO(踢人)
	    * @param @param fromUserId
	    * @param @param toUserId
	    * @param @param leaderName
	    * @param @param familyName
	    * @param @return    参数
	    * @return Message    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Message buildKickMemberMessage(Long fromUserId,Long toUserId, String  leaderName,String familyName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.FamilyType_Kickout);
		message.setContent(String.format(KickMemberMessage,familyName,leaderName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.FamilyType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20029.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_False);
		message.setToUrl("");
		return message;
	}
	/**
	 * 
	    * @Title: buildChallegeMessage
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param fromUserId
	    * @param @param toUserId
	    * @param @param familyName
	    * @param @return    参数
	    * @return Message    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Message buildChallegeMessage(Long fromUserId,Long toUserId, String familyName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.FamilyType_Challenge);
		message.setContent(String.format(ChallengeMessage,familyName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.FamilyType);
		message.setPicture("");
		message.setTitle("挑战通知");
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_False);
		message.setToUrl("");
		return message;
	}
	
	public Message buildFamilyDiamondsMessage(Long fromUserId,Long toUserId,String familyId,String season,String diamonds, String personDiamondNum,String orderNum,String familyName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.FamilyType_Reward);
		message.setContent(String.format(RewardMessage,familyName,season,diamonds,personDiamondNum));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.FamilyType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20027.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(String.format(FamilyFightHistoryDetailUrl, familyId,DateTools.getCurDateAddDay(-1)));
		return message;
	}
	
	public Message buildFamilyPowerMessage(Long fromUserId,Long toUserId,String familyId,String season,String powers, String personPowerNum,String orderNum,String familyName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.FamilyType_Reward);
		message.setContent(String.format(RewardPowerMessage,familyName,season,powers,personPowerNum));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.FamilyType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20027.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(String.format(FamilyFightHistoryDetailUrl, familyId,DateTools.getCurDateAddDay(-1)));
		return message;
	}
	
	public Message buildSeasonFamilyDiamondsMessage(Long fromUserId,Long toUserId,String familyId,String season,String diamonds, String personDiamondNum,Integer familyType,String familyName) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.FamilyType_SeasonReward);
		message.setContent(String.format(SeasonRewardMessage,familyName,season,diamonds,personDiamondNum));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.FamilyType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20027.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_False);
		message.setToUrl("");
		return message;
	}
	
	/**
	 * 
	    * @Title: getMessageById
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param messageId
	    * @param @return    参数
	    * @return Message    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	Message getMessageById(long messageId) {
		return messageMapper.getMessageById(messageId);
	}
	
	/**
	 * 
	    * @Title: buildTieTiaoMessage
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param opponentFamily 对手俱乐部
	    * @param @param opponentFamilyName 对手俱乐部名称
	    * @param @param fromUserId 用户id
	    * @param @param myFamilyId 自家俱乐部id
	    * @param @return    参数
	    * @return List<Message>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public List<Message> buildTieTiaoMessage(Long fromUserId,Long myFamilyId,Long opponentFamily,String opponentFamilyName) {
		List<Message> messages=new ArrayList<Message>();
		List<Map<String,Object>> userMap=familyService.familyRelationByFamilyId(myFamilyId);
		for(Map<String,Object> m:userMap) {
			if(m.get("userId").toString().equals(String.valueOf(fromUserId))) {
				
			}else {
				Message message=new Message();
				message.setFromUserId(fromUserId==null?0:fromUserId);
				message.setBottomText("");
				message.setChildType(0);
				message.setContent(getTieTiao(opponentFamilyName));
				message.setCreateTime(new Date());
				message.setIcon("");
				message.setIsPush(1);
				message.setParentType(3);
				message.setPicture("");
				message.setTitle(AppMsgCode.M20026.getMsg());
				message.setToUserId(Long.valueOf(m.get("userId").toString()));
				message.setUrlType(0);
				message.setToUrl(getTietiaoMessageUrl(opponentFamily));
				messages.add(message);
			}
		}
		
		return messages;
	}
	/**
	 * 
	    * @Title: buildAchieveMessage
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param fromUserId 
	    * @param @param awardMsg 奖励5个马扎
	    * @param @param toUserId
	    * @param @param achieveId
	    * @param @param achieveName 
	    * @param @return    参数
	    * @return Message    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Message buildAchieveMessage(Long fromUserId,Long toUserId,int achieveId,String achieveName) {
				Message message=new Message();
				message.setFromUserId(fromUserId==null?0:fromUserId);
				message.setBottomText("");
				message.setChildType(MessageTypeConstant.PersonType_Achievement);
				message.setContent(getAchieveDesc(""));
				message.setCreateTime(new Date());
				message.setIcon("");
				message.setIsPush(1);
				message.setParentType(MessageTypeConstant.PersonType);
				message.setPicture("");
				message.setTitle(AppMsgCode.M20025.getMsg());
				message.setToUserId(toUserId);
				message.setUrlType(0);
				message.setToUrl(getAchieveMessageUrl(achieveId,achieveName));
		        return message;
	}
	
	public Message buildReveiceInviteMessage(Long fromUserId, String fromUserPhone, String fromUserNick,
			String familyName, long toUserId, long familyInviteId, MessageEnum reveiceInvite) {
		Message message = new Message();
		message.setFromUserId(fromUserId == null ? 0 : fromUserId);
		message.setBottomText(AppMsgCode.M20024.getMsg());
		message.setChildType(MessageTypeConstant.FamilyType_REVEICE_INVITE);
		message.setContent(getReveiceInviteMessageDesc(fromUserPhone, fromUserNick, familyName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.FamilyType);
		message.setPicture("");
		message.setTitle(AppMsgCode.M20023.getMsg());
		message.setToUserId(toUserId);
		message.setUrlType(0);
		message.setToUrl(getRecruitMessageUrl(familyInviteId));
		return message;
	}
	
	/**
	 * 
	    * @Title: insertMessage
	    * @Description: TODO(插入消息)
	    * @param @param msg
	    * @param @param type
	    * @param @return    参数
	    * @return Long    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Long insertMessage(Message msg,MessageEnum type) {
		messageMapper.insertMessage(msg);
//		switch(type) {
//		case SYSTEM:
//			break;
//		case INVITE_FAMILY:
//			break;
//		 default:
//			break;
//		}
		return msg.getId();
	}
	
	/**
	 * 
	    * @Title: pushMessage
	    * @Description: TODO(推送消息)
	    * @param @param msg
	    * @param @param type
	    * @param @return    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void pushMessageTrans(Message msg,MessageEnum type) {
		TaskMessagePush task=new TaskMessagePush();
		task.setMessageId(msg.getId());
		taskMessagePushMapper.insertTaskMessagePush(task);
	}
	
	public void pushMessageNotrans(Message msg,MessageEnum type) {
		TaskMessagePush task=new TaskMessagePush();
		task.setMessageId(msg.getId());
		taskMessagePushMapper.insertTaskMessagePush(task);
	}
	
	public void pushMessageByTask(Message msg) {
		String alias=msg.getToUserId()+"_0";
		 Map<String,String> extraMap = new HashMap<String, String>();
	        extraMap.put("parentType", String.valueOf(msg.getParentType()));
	        extraMap.put("childType", String.valueOf(msg.getChildType()));
	        extraMap.put("msgId",String.valueOf(msg.getId()));
	        extraMap.put("toUrl", msg.getToUrl());
		 	managePushApi.SendMsgToOne(msg.getContent(), alias, ManagePushApi.PLATFORM_IOS, extraMap);
	}
	public void pushImMessageByTask(ImMsg msg,String toUser) {
		String alias=toUser+"_0";
		 Map<String,String> extraMap = new HashMap<String, String>();
	        extraMap.put("parentType", "0");
	        extraMap.put("childType", "0");
	        extraMap.put("msgId",String.valueOf(msg.getId()));
	        extraMap.put("toUrl", getImMessageUrl(msg.getFamilyId()));
		 	managePushApi.SendMsgToOne(msg.getMsg(), alias, ManagePushApi.PLATFORM_IOS, extraMap);
	}
	public void pushAwardMessageByTask(Long awardId,String title) {
		//String alias=toUserId+"_0";
		 Map<String,String> extraMap = new HashMap<String, String>();
	        extraMap.put("parentType", "4");
	        extraMap.put("childType", "0");
	        extraMap.put("msgId","0");
	        extraMap.put("toUrl",KaijiangMessageUrl+awardId);
		 	try{
				managePushApi.SendMsgToAll(title, extraMap, ManagePushApi.PLATFORM_IOS);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	    * @Title: getMsgMainTypes
	    * @Description: TODO(获取消息列表)
	    * @param @param userId
	    * @param @return    参数
	    * @return List<Map<String,String>>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public List<Map<String,String>> getMsgMainTypes(Long userId){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("toUserId", userId);
		boolean hadSys=false,hadPerson=false,hadFamily=false;
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String,String>  rtMap1=new HashMap<String,String>();
		rtMap1.put("desc", AppMsgCode.M20022.getMsg());
	
		Map<String,String>  rtMap2=new HashMap<String,String>();
		rtMap2.put("desc", AppMsgCode.M20020.getMsg());
	 
		Map<String,String>  rtMap3=new HashMap<String,String>();
		rtMap3.put("desc", AppMsgCode.M20018.getMsg());
		
		List<Map<String,Object>> findList=messageMapper.getMsgMainTypes(userId);
		for(Map<String,Object> map:findList) {
			int parentType=Integer.valueOf(map.get("parentType").toString());
			if(parentType==1) {
				rtMap1.put("msgType", "1");
				rtMap1.put("unRead", String.valueOf(map.get("typeCount")));
				rtMap1.put("icon",MessageImgs.get("1"));
				rtMap1.put("title", AppMsgCode.M20021.getMsg());
				hadSys=true;
			}else if(parentType==2) {
				rtMap2.put("msgType", "2");
				rtMap2.put("unRead", String.valueOf(map.get("typeCount")));
				rtMap2.put("icon",MessageImgs.get("2"));
				rtMap2.put("title", AppMsgCode.M20019.getMsg());
				hadPerson=true;
			}else if(parentType==3) {
				rtMap3.put("msgType", "3");
				rtMap3.put("unRead", String.valueOf(map.get("typeCount")));
				rtMap3.put("icon",MessageImgs.get("3"));
				rtMap3.put("title", AppMsgCode.M20017.getMsg());
				hadFamily=true;
			}
			 
		}
		if(!hadSys) {
			rtMap1.put("msgType", "1");
			rtMap1.put("unRead", "0");
			rtMap1.put("icon",MessageImgs.get("1"));
			rtMap1.put("title", AppMsgCode.M20021.getMsg());
		}
		
		paramMap.put("parentType", 1);
		Map<String,Object> timeMsg=messageMapper.getMsgMainTypeTime(paramMap);
		
		if(timeMsg!=null && timeMsg.size()>0) {
			String time=String.valueOf(timeMsg.get("createTime"));
			rtMap1.put("lastMsgTime", getMsgTyoeTimes(time));
		}else {
			rtMap1.put("lastMsgTime", "");
		}
		
		
		if(!hadPerson) {
			rtMap2.put("msgType", "2");
			rtMap2.put("unRead", "0");
			rtMap2.put("icon",MessageImgs.get("2"));
			rtMap2.put("title", AppMsgCode.M20019.getMsg());
		} 
		paramMap.put("parentType", 2);
		timeMsg=messageMapper.getMsgMainTypeTime(paramMap);
		if(timeMsg!=null && timeMsg.size()>0) {
			String time=String.valueOf(timeMsg.get("createTime"));
			rtMap2.put("lastMsgTime", getMsgTyoeTimes(time));
		}else {
			rtMap2.put("lastMsgTime", "");
		}
		if(!hadFamily)  {
			rtMap3.put("msgType", "3");
			rtMap3.put("unRead","0");
			rtMap3.put("icon",MessageImgs.get("3"));
			rtMap3.put("title", AppMsgCode.M20017.getMsg());
		}
		paramMap.put("parentType", 3);
		timeMsg=messageMapper.getMsgMainTypeTime(paramMap);
		if(timeMsg!=null && timeMsg.size()>0) {
			String time=String.valueOf(timeMsg.get("createTime"));
			rtMap3.put("lastMsgTime", getMsgTyoeTimes(time));
		}else {
			rtMap3.put("lastMsgTime", "");
		}
		
		
		
		
		//23541
		list.add(rtMap1);
		list.add(rtMap2);
		list.add(rtMap3);
		return list;
	}
	private String getMsgTyoeTimes(String time) {
		String now=DateTools.getCurDate();
		int year=Integer.valueOf(now.substring(0,4));
		int month=Integer.valueOf(now.substring(5,7));
		int day=Integer.valueOf(now.substring(8,10));
		int year2=Integer.valueOf(time.substring(0,4));
		int month2=Integer.valueOf(time.substring(5,7));
		int day2=Integer.valueOf(time.substring(8,10));
		if(year>year2) {
			//年月日
			return time.substring(0,10);
		}else {
			//年度内
			if(month>month2) {
				//月日
				return time.substring(5,10);
			}else {
				//月内
				if(day-day2==1) {
					return "昨天";
				}else if(day-day2==0){
					//当天，取时分
					return time.substring(11,16);
				}else {
					return time.substring(5,10);
				}
			}
		}
	}

	/**
	 * @param @param msgId    参数
	 * @return void    返回类型
	 * @throws
	 * @Title: updateRead
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author LanYeYe
	 */
	public void updateRead(Long msgId) {
		messageMapper.updateRead(msgId);
	}

	public void updateReadAll(Integer msgType) {
		messageMapper.updateReadAll(msgType);
	}
   /**
    * 
       * @Title: getMsgListByType
       * @Description: TODO(这里用一句话描述这个方法的作用)
       * @param @param map
       * @param @return    参数
       * @return List<Map<String,Object>>    返回类型
       * @throws
       * @author LanYeYe
    */
	public List<Map<String,Object>> getMsgListByType(Map<String,Object> map){
		long msgId=Long.valueOf(map.get("startMsgId").toString());
		if(msgId<0) {
			map.put("startMsgId", 999999999999999L);
		}
		List<Map<String,Object>>  list=null;
		if(map.get("msgType").equals("4")) {//开奖消息
			map.put("toUrl", KaijiangMessage+"?id=");
			list=messageMapper.getMsgListKaijiang(map);
		}else {
			list=messageMapper.getMsgListByType(map);
			for(Map<String,Object> m:list) {
				int urtType=Integer.valueOf(m.get("urlType").toString());
				int isRead=Integer.valueOf(m.get("isRead").toString());
				Long id=Long.valueOf(m.get("msgId").toString());
				if(urtType==2 && isRead==0) {
					  messageMapper.updateRead(id);
				}
			}
		}
		return list;
		
	}
	//处理私有逻辑
	private String getInviteMessageDesc(String fromUserPhone,String fromUserNick) {
		if(ValidTools.isNotBlank(fromUserNick)) {
			return String.format(InviteMessage, fromUserNick);
		}else {
			return String.format(InviteMessage, PhoneUtils.hidePhone(fromUserPhone));
		}
	}
	
	private String getPassMessageDesc(String fromUserPhone,String fromUserNick,String familyName) {
		if(ValidTools.isNotBlank(fromUserNick)) {
			return String.format(PassFamilyMessage, fromUserNick,familyName);
		}else {
			return String.format(PassFamilyMessage, PhoneUtils.hidePhone(fromUserPhone),familyName);
		}
	}
	
	private String getReveiceInviteMessageDesc(String fromUserPhone,String fromUserNick,String familyName) {
			return String.format(ReveiceInvite,familyName, fromUserNick);
	}
	
	private String getPassMessageDesc2(String fromUserNick) {
			return String.format(PassFamilyMessage2, fromUserNick);
	}
	
	private String getFailMessageDesc(String familyName) {
		return String.format(FailFamilyMessage, familyName);
    }
	
	private String getFailMessageDesc2(String name) {
		return String.format(FailFamilyMessage2, name);
    }
	private String getQuitMessageDesc(String name) {
		return String.format(QuitFamilyMessage, name);
    }
	private String getTieTiao(String familyName) {
		return String.format(TietiaoMessage, familyName);
	}
	private String getAchieveDesc(String achieveMsg) {
		return String.format(AchieveMessage);
    }
	
	private String getInviteMessageUrl(Long familyInviteId) {
			return String.format(InviteMessageUrl, String.valueOf(familyInviteId));
	}
	
	private String getRecruitMessageUrl(Long familyInviteId) {
		return String.format(RecruitMessageUrl, String.valueOf(familyInviteId));
	}
	
	
	private String getShopMessageDesc(String goodsName) {
		return String.format(ShopMessage, goodsName);
    }

    private String getNewAuctionForRecentFailPerson(String goodsName) {
		return String.format(NewAuctionForRecentFailPerson, goodsName);
    }
	private String getIDCardMessageDesc(String userName,String cardNumber) {
		return String.format(IDCardMessage, userName,cardNumber.substring(0, 1)+"**************"+cardNumber.substring(cardNumber.length()-1));
    }
	private String getGoodsSendMessageDesc(String goodsName) {
		return String.format(GoodsSendMessage, goodsName);
    }
	private String getLotterySendMessageDesc(String userName,String lotteryName) {
		return String.format(LotterySendMessage,userName,lotteryName);
    }
	private String getH5RegMessageUrl(){
		return RegMessageUrl;
	}
	private String getRegMessageDesc() {
		 return RegMessage;
	}
	
	private String getPassMessageUrl(int familyId) {
		return String.format(InvitePassMessageUrl, String.valueOf(familyId));
    }
	private String getTietiaoMessageUrl(Long familyId) {
		return String.format(TietiaoMessageUrl, String.valueOf(familyId));
    }
	
	private String getAchieveMessageUrl(int achieveId,String achieveName) {
		return String.format(AchieveMessageUrl, String.valueOf(achieveId),achieveName);
    }
	private String getImMessageUrl(long familyId) {
		return String.format(ImMessageUrl, String.valueOf(familyId));
    }
	public static void main(String []args) {
		MessageService service=new MessageService();
		System.out.println(service.getInviteMessageUrl(80L));
		System.out.println(InviteMessageUrl);
		System.out.println(service.getInviteMessageUrl(20L));
		System.out.println(InviteMessageUrl);
	}
	 
}
