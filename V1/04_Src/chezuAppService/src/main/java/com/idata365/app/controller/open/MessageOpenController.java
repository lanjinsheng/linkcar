package com.idata365.app.controller.open;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.Message;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.service.MessageService;
import com.idata365.app.util.SignUtils;


@RestController
public class MessageOpenController {
	protected static final Logger LOG = LoggerFactory.getLogger(MessageOpenController.class);

	@Autowired
	private MessageService messageService;
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
    		@RequestParam (value = "sign") String sign){
    	LOG.info("param:"+season+"=="+orderNum+"=="+diamondNum+"==sign="+sign);
    	LOG.info("reSign:"+SignUtils.encryptHMAC(season+orderNum+diamondNum));
    	Message msg=messageService.buildFamilyDiamondsMessage(null, toUserId, familyId, season, orderNum, orderNum);
    	//插入消息
 		messageService.insertMessage(msg, MessageEnum.DiamondDistr);
 		//用定时器推送
        messageService.pushMessageTrans(msg,MessageEnum.DiamondDistr);
    	return true;
    }  
    
    
}