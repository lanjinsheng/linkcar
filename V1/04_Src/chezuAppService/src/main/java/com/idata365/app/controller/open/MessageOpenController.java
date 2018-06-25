package com.idata365.app.controller.open;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.Message;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.MessageService;
import com.idata365.app.util.SignUtils;


@RestController
public class MessageOpenController {
	protected static final Logger LOG = LoggerFactory.getLogger(MessageOpenController.class);

	@Autowired
	private MessageService messageService;
	@Autowired
    FamilyService familyService;
	public MessageOpenController() {
	}
    /**
     * 
        * @Title: sendShopMsg
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param @param userId
        * @param @param goodsName
        * @param @param sign
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/app/msg/sendShopMsg")
    public boolean sendShopMsg(@RequestParam (value = "userId") Long userId,
    		@RequestParam (value = "goodsName") String goodsName,@RequestParam (value = "sign") String sign){
    	LOG.info("param:"+userId+"=="+goodsName+"==sign="+sign);
    	LOG.info("reSign:"+SignUtils.encryptHMAC(userId+goodsName));
    	Message msg=messageService.buildShopMessage(userId, userId, goodsName);
    	//插入消息
 		messageService.insertMessage(msg, MessageEnum.SHOP);
 		//用定时器推送
        messageService.pushMessageTrans(msg,MessageEnum.SHOP);
    	return true;
    }
    
    /**
     * 
        * @Title: verifyIDCardMsg
        * @Description: TODO(身份证审核成功通知)
        * @param @param userId
        * @param @param goodsName
        * @param @param sign
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
        * @author lcc
     */
    @RequestMapping("/app/msg/verifyIDCardMsg")
    public boolean verifyIDCardMsg(@RequestParam (value = "userId") Long userId,@RequestParam (value = "userName") String userName,
    		@RequestParam (value = "cardNumber") String cardNumber,@RequestParam (value = "sign") String sign){
    	LOG.info("param:"+userId+"=="+userName+"=="+cardNumber+"==sign="+sign);
    	Message msg=messageService.buildIDCardMessage(userId, userName, cardNumber);
    	//插入消息
 		messageService.insertMessage(msg, MessageEnum.IDCARD);
 		//用定时器推送
        messageService.pushMessageTrans(msg,MessageEnum.IDCARD);
    	return true;
    }
    
    @RequestMapping("/app/msg/verifyVehicleTravelMsg")
    public boolean verifyVehicleTravelMsg(@RequestParam (value = "userId") Long userId,@RequestParam (value = "userName") String userName,
    		@RequestParam (value = "cardNumber") String cardNumber,@RequestParam (value = "sign") String sign){
    	LOG.info("param:"+userId+"=="+userName+"=="+cardNumber+"==sign="+sign);
    	Message msg=messageService.buildVehicleTravelMessage(userId, userName, cardNumber);
    	//插入消息
 		messageService.insertMessage(msg, MessageEnum.VehicleTravel);
 		//用定时器推送
        messageService.pushMessageTrans(msg,MessageEnum.VehicleTravel);
    	return true;
    }
    
	/**
	 * 
	    * @Title: sendGoodsSendMsg
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param userId
	    * @param @param goodsName
	    * @param @param sign
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
    @RequestMapping("/app/msg/sendGoodsSendMsg")
    public boolean sendGoodsSendMsg(@RequestParam (value = "userId") Long userId,
    		@RequestParam (value = "goodsName") String goodsName,@RequestParam (value = "sign") String sign){
    	LOG.info("param:"+userId+"=="+goodsName+"==sign="+sign);
    	LOG.info("reSign:"+SignUtils.encryptHMAC(userId+goodsName));
    	Message msg=messageService.buildShopMessage(userId, userId, goodsName);
    	//插入消息
 		messageService.insertMessage(msg, MessageEnum.SHOP_GOODS_SEND);
 		//用定时器推送
        messageService.pushMessageTrans(msg,MessageEnum.SHOP_GOODS_SEND);
    	return true;
    } 
    /**
     * 
     * @param auctionGoodsId
     * @param auctionGoodsType
     * @param eventType 0 失敗人員发送，1，成功人员去填寫，2，工作人員已发货处理
     * @param userIds
     * @param goodsName
     * @param sign  auctionGoodsId+eventType+userIds 进行签名
     * @return
     */
    @RequestMapping("/app/msg/sendAuctionMsg")
    public boolean sendAuctionMsg(@RequestParam (value = "auctionGoodsId") Long auctionGoodsId,
    		@RequestParam (value = "auctionGoodsType") Integer auctionGoodsType,
    		@RequestParam (value = "eventType") Integer eventType,
    		@RequestParam (value = "userIds") String userIds,
    		@RequestParam (value = "goodsName") String goodsName,
    		@RequestParam (value = "sign") String sign){
    	LOG.info("param:"+auctionGoodsId+"=="+auctionGoodsType+"==="+userIds+"==="+goodsName+"==sign="+sign);
    	
    	String []users=userIds.split(",");
    	if(eventType==0){
	    	for(String userId:users){
	    		Message msg=messageService.buildAuctionFailMessage(null,Long.valueOf(userId),goodsName,auctionGoodsId);
	        	//插入消息
	     		messageService.insertMessage(msg, MessageEnum.AuctionFail);
	     		//用定时器推送
	            messageService.pushMessageTrans(msg,MessageEnum.AuctionFail);
	    	}
    	}else if(eventType==1){
    		for(String userId:users){
	    		Message msg=messageService.buildAuctionSuccMessage(null,Long.valueOf(userId),goodsName,
	    				auctionGoodsType,auctionGoodsId);
	        	//插入消息
	     		messageService.insertMessage(msg, MessageEnum.AuctionSucc);
	     		//用定时器推送
	            messageService.pushMessageTrans(msg,MessageEnum.AuctionSucc);
	    	}
    	}else if(eventType==2){
    		for(String userId:users){
	    		Message msg=messageService.buildAuctionExchangeMessage(null,Long.valueOf(userId),goodsName,auctionGoodsId);
	        	//插入消息
	     		messageService.insertMessage(msg, MessageEnum.AuctionExchange);
	     		//用定时器推送
	            messageService.pushMessageTrans(msg,MessageEnum.AuctionExchange);
	    	}
    	}
    	 
    	return true;
    } 
    
    
    /**
     * 
        * @Title: sendFamilyDiamondsMsg
        * @Description: TODO(钻石分配通知)
        * @param @param season
        * @param @param familyId
        * @param @param orderNum
        * @param @param toUserId
        * @param @param diamondNum
        * @param @param sign
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/app/msg/sendFamilyDiamondsMsg")
    public boolean sendFamilyDiamondsMsg(@RequestParam (value = "season") String season,
    		@RequestParam (value = "familyId") String familyId,
    		@RequestParam (value = "orderNum") String orderNum,
    		@RequestParam (value = "toUserId") Long toUserId,
    		@RequestParam (value = "diamondNum") String diamondNum,
    		@RequestParam (value = "personDiamondNum") String personDiamondNum,
    		@RequestParam (value = "sign") String sign){
    	LOG.info("param:"+season+"=="+orderNum+"=="+diamondNum+"==sign="+sign);
    	LOG.info("reSign:"+SignUtils.encryptHMAC(toUserId+""));
    	
    	Map<String, Object> familyInfo=familyService.findFamilyByFamilyId(Long.valueOf(familyId));
    	Message msg=messageService.buildFamilyDiamondsMessage(null, toUserId, familyId, season, diamondNum,personDiamondNum, orderNum,String.valueOf(familyInfo.get("familyName")));
    	//插入消息
 		messageService.insertMessage(msg, MessageEnum.DiamondDistr);
 		//用定时器推送
        messageService.pushMessageTrans(msg,MessageEnum.DiamondDistr);
    	return true;
    }  
    
    
    @RequestMapping("/app/msg/sendFamilyPowerMsg")
    public boolean sendFamilyPowerMsg(@RequestParam (value = "season") String season,
    		@RequestParam (value = "familyId") String familyId,
    		@RequestParam (value = "orderNum") String orderNum,
    		@RequestParam (value = "toUserId") Long toUserId,
    		@RequestParam (value = "PowerNum") String powerNum,
    		@RequestParam (value = "personPowerNum") String personPowerNum,
    		@RequestParam (value = "sign") String sign){
    	    	LOG.info("param:"+season+"=="+orderNum+"=="+powerNum+"==sign="+sign);
    	    	LOG.info("reSign:"+SignUtils.encryptHMAC(toUserId+""));
    	    	Map<String, Object> familyInfo=familyService.findFamilyByFamilyId(Long.valueOf(familyId));
    	    	Message msg=messageService.buildFamilyPowerMessage(null, toUserId, familyId, season, powerNum,personPowerNum, orderNum,String.valueOf(familyInfo.get("familyName")));
    	    	//插入消息
    	 		messageService.insertMessage(msg, MessageEnum.PowerDistr);
    	 		//用定时器推送
    	        messageService.pushMessageTrans(msg,MessageEnum.PowerDistr);
    	    	return true;
    	    }  
    
    @RequestMapping("/app/msg/sendFamilyDiamondsSeasonMsg")
    public boolean sendFamilyDiamondsSeasonMsg(@RequestParam (value = "season") String season,
    		@RequestParam (value = "familyId") String familyId,
    		@RequestParam (value = "familyType") Integer familyType,
    		@RequestParam (value = "toUserId") Long toUserId,
    		@RequestParam (value = "diamondNum") String diamondNum,
     		@RequestParam (value = "personDiamondNum") String personDiamondNum,
    		@RequestParam (value = "sign") String sign){
    	LOG.info("param:"+season+"=="+familyType+"=="+diamondNum+"==sign="+sign);
    	LOG.info("reSign:"+SignUtils.encryptHMAC(toUserId+""));
    	Map<String, Object> familyInfo=familyService.findFamilyByFamilyId(Long.valueOf(familyId));
    	Message msg=messageService.buildSeasonFamilyDiamondsMessage(null, toUserId, familyId, season, diamondNum, personDiamondNum,familyType,String.valueOf(familyInfo.get("familyName")));
    	//插入消息
 		messageService.insertMessage(msg, MessageEnum.DiamondSeasonDistr);
 		//用定时器推送
        messageService.pushMessageTrans(msg,MessageEnum.DiamondSeasonDistr);
    	return true;
    }
    
}