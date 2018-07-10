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
	public static final String  H5Host="http://apph5.idata365.com/";
	public static final String  InviteMessage="玩家【%s】申请加入您的车族，请尽快审核，别让您的粉丝等太久哦！";
	public static final String  PassFamilyMessage="族长【%s】同意了您的申请，欢迎来到【%s】大家族！";
	public static final String  PassFamilyMessage2="玩家【%s】同意了进入您创建的家族，快去看看吧！";
	public static final String  FailFamilyMessage="抱歉，您申请加入【%s】家族失败！";
	public static final String  FailFamilyMessage2="抱歉，玩家【%s】很拒绝加入您创建的家族！";
	public static final String  QuitFamilyMessage="您的俱乐部成员【%s】离开了您创建的俱乐部! ";
	public static final String  ReveiceInvite="【%s】俱乐部经理【%s】邀请你进入俱乐部! ";
	//踢人
	public static final String KickMemberMessage="【%s】俱乐部经理【%s】将您移出了俱乐部！";
	public static final String ChallengeMessage="下战书！您的家族被【%s】家族挑战了，将成为明天的对战家族，请号令成员做好准备！";
	//获奖
	public static final String RewardMessage="恭喜！您的家族【%s】在【%s】每日对战中获胜，家族共获得【%s】钻石，按照贡献比例分配给您【%s】钻石，已发放至您的账户，点此查看昨日对战详情。";
	public static final String RewardPowerMessage="恭喜！您的家族【%s】在【%s】每日对战中获胜，家族共获得【%s】动力，按照贡献比例分配给您【%s】动力，已发放至您的账户，点此查看昨日对战详情。";
	
	
	//获奖
	public static final String SeasonRewardMessage="恭喜！您的家族【%s】在【%s】赛季中获得好成绩，家族共获得【%s】钻石，分配给您【%s】钻石，已发放至您的账户.";
	
	public static final String RegMessage="欢迎您加入【好车族】游戏，在这里您可以关注自身驾驶行为，即有机会赢取超级大奖！快快点击查看玩法指导！";
	public static final String TietiaoMessage="ohh，车族【%s】发生了一起违规，赶紧来贴条吧！";
	public static final String AchieveMessage="新成就达成！来看看奖励吧！";
	public static final String KaijiangMessage=H5Host+"share/lottery.html";
	//兑换
	public static final String ShopMessage="恭喜您兑换【%s】成功，工作人员会在1~3个工作日内处理您的兑换请求，请耐心等待通知";
	public static final String GoodsSendMessage="您兑换的【%s】工作人员已经寄出，正在飞向您的路上，惊喜马上就到~";
	
	public static final String AuctionFailMassage="您参与的竞拍 %s 被别人抢走啦，请继续关注我们的竞拍活动，下一个大奖就是你！";
	public static final String AuctionSuccMassage="恭喜您成功拍下 %s ！请尽快填写相关信息，以便我们将奖品顺利无误的下发给您。";
	public static final String AuctionExchangeMassage="您获拍的%s 已经由工作人员下发给您，请注意查看手机短信提示。您还可以继续关注我们其他的竞拍活动，各种大奖拿到手软！";
	
	
	
	//证件审核通过
	public static final String IDCardMessage="恭喜您名为【%s】的证件【%s】审核成功，感谢您的支持";
	//道具赠送
	public static final String LotterySendMessage="一个惊喜!【%s】赠送给你一个【%s】,快去领取吧!";
	public static final String LotteryRecMessage="【%s】已经收到你送的道具了,谢谢你的慷慨!";
	
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
	//家族钻石分配记录
//	public static final String FamilyDiamondsDistr="com.idata365.haochezu://DEPNotes.push?enterType=0&familyId=%s";
	
	public static final String FamilyFightHistoryDetailUrl="com.idata365.haochezu://FightHistoryDetail.push?familyId=%s&fightTime=%s";
	public static final String RegMessageUrl=H5Host+"share/home.html";
	public static final String KaijiangMessageUrl=H5Host+"share/lottery.html";
	
	public static final String MyAuctionUrl="com.shujin.haochezu://AuctionNotes.push";           
	public static final String MyAuctionInfoApendUrl="com.shujin.haochezu://convertAddress.push?type=%s&convertId=%s";
	
	
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
			message.setBottomText("点击审核");
			message.setChildType(MessageTypeConstant.FamilyType_Apply);
			message.setContent(getInviteMessageDesc(fromUserPhone,fromUserNick));
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(MessageTypeConstant.FamilyType);
			message.setPicture("");
			message.setTitle("玩家申请");
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
			message.setTitle("族长审核");
			message.setToUserId(toUserId);
			message.setUrlType(0);
			message.setToUrl(getPassMessageUrl(f.getMyFamilyId()));
			break;}
		case PASS_FAMILY2:{
			FamilyResultBean f=familyService.findFamily(fromUserId); 
			message.setFromUserId(fromUserId==null?0:fromUserId);
			message.setBottomText("");
			message.setChildType(MessageTypeConstant.FamilyType_Pass);
			message.setContent(getPassMessageDesc2(fromUserNick));
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(MessageTypeConstant.FamilyType);
			message.setPicture("");
			message.setTitle("收到招募信息");
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
			message.setTitle("加入俱乐部失败");
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
			message.setTitle("招募成员失败");
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
			message.setTitle("退出俱乐部");
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
		message.setTitle("兑换成功");
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_False);
		message.setToUrl("");
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
		message.setTitle("竞拍失败");
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(MyAuctionUrl);
		return message;
	}
	
	public Message buildAuctionSuccMessage(Long fromUserId,Long toUserId,String goodsName,Integer auctionGoodsType,Long auctionGoodsId) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_Auction_Succ);
		message.setContent(String.format(AuctionSuccMassage, goodsName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle("竞拍成功");
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		String convertId=String.valueOf(auctionGoodsId);
		String type=String.valueOf(auctionGoodsType);
		message.setToUrl(String.format(MyAuctionInfoApendUrl,type,convertId));
		return message;
	}
	public Message buildAuctionExchangeMessage(Long fromUserId,Long toUserId,String goodsName,Long auctionGoodsId) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("");
		message.setChildType(MessageTypeConstant.SystemType_Auction_Exchange);
		message.setContent(String.format(AuctionExchangeMassage, goodsName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.SystemType);
		message.setPicture("");
		message.setTitle("兑换成功");
		message.setToUserId(toUserId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_App);
		message.setToUrl(MyAuctionUrl);
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
		message.setTitle("证件审核");
		message.setToUserId(userId);
		message.setUrlType(MessageTypeConstant.MessageUrl_Href_False);
		message.setToUrl(CardNotes);
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
		message.setTitle("证件审核");
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
		message.setTitle("奖励发放通知");
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
		message.setTitle("道具赠送通知");
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
		message.setTitle("道具领取通知");
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
		message.setTitle("家族踢出通知");
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
		message.setTitle("奖励通知");
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
		message.setTitle("奖励通知");
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
		message.setTitle("奖励通知");
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
	    * @param @param opponentFamily 对手家族
	    * @param @param opponentFamilyName 对手家族名称
	    * @param @param fromUserId 用户id
	    * @param @param myFamilyId 自家家族id
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
				message.setTitle("贴条通知");
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
				message.setTitle("成就通知");
				message.setToUserId(toUserId);
				message.setUrlType(0);
				message.setToUrl(getAchieveMessageUrl(achieveId,achieveName));
		        return message;
	}
	
	public Message buildReveiceInviteMessage(Long fromUserId, String fromUserPhone, String fromUserNick,
			String familyName, long toUserId, long familyInviteId, MessageEnum reveiceInvite) {
		Message message = new Message();
		message.setFromUserId(fromUserId == null ? 0 : fromUserId);
		message.setBottomText("点击查看");
		message.setChildType(MessageTypeConstant.FamilyType_REVEICE_INVITE);
		message.setContent(getReveiceInviteMessageDesc(fromUserPhone, fromUserNick, familyName));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(MessageTypeConstant.FamilyType);
		message.setPicture("");
		message.setTitle("俱乐部邀请");
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
		rtMap1.put("desc", "系统想对你说的话......");
	
		Map<String,String>  rtMap2=new HashMap<String,String>();
		rtMap2.put("desc", "个人消息，快来看吧!");
	 
		Map<String,String>  rtMap3=new HashMap<String,String>();
		rtMap3.put("desc", "家族审核/挑战/奖励消息，来看看吧!");
		
		List<Map<String,Object>> findList=messageMapper.getMsgMainTypes(userId);
		for(Map<String,Object> map:findList) {
			int parentType=Integer.valueOf(map.get("parentType").toString());
			if(parentType==1) {
				rtMap1.put("msgType", "1");
				rtMap1.put("unRead", String.valueOf(map.get("typeCount")));
				rtMap1.put("icon",MessageImgs.get("1"));
				rtMap1.put("title", "系统消息");
				hadSys=true;
			}else if(parentType==2) {
				rtMap2.put("msgType", "2");
				rtMap2.put("unRead", String.valueOf(map.get("typeCount")));
				rtMap2.put("icon",MessageImgs.get("2"));
				rtMap2.put("title", "个人消息");
				hadPerson=true;
			}else if(parentType==3) {
				rtMap3.put("msgType", "3");
				rtMap3.put("unRead", String.valueOf(map.get("typeCount")));
				rtMap3.put("icon",MessageImgs.get("3"));
				rtMap3.put("title", "家族消息");
				hadFamily=true;
			}
			 
		}
		if(!hadSys) {
			rtMap1.put("msgType", "1");
			rtMap1.put("unRead", "0");
			rtMap1.put("icon",MessageImgs.get("1"));
			rtMap1.put("title", "系统消息");
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
			rtMap2.put("title", "个人消息");
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
			rtMap3.put("title", "家族消息");
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
   * 
      * @Title: updateRead
      * @Description: TODO(这里用一句话描述这个方法的作用)
      * @param @param msgId    参数
      * @return void    返回类型
      * @throws
      * @author LanYeYe
   */
   public void updateRead(Long msgId) {
	   messageMapper.updateRead(msgId);
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
