package com.idata365.app.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */



import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.FamilyInvite;
import com.idata365.app.entity.Message;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.MessageMapper;
import com.idata365.app.util.ValidTools;

 

@Service
public class MessageService extends BaseService<MessageService>{
	private final static Logger LOG = LoggerFactory.getLogger(MessageService.class);
	public static final String  InviteMessage="玩家【%s】申请加入您的车族，请尽快审核，别让您的粉丝等太久哦！";
	public static final String  InviteMessageUrl="com.shujin.shuzan://inviteId=%s";
	@Autowired
	MessageMapper messageMapper;
	public MessageService() {
	}
	 
	public Message buildInviteMessage(Long fromUserId,String fromUserPhone,String fromUserNick,Long toUserId,Long familyInviteId) {
		Message message=new Message();
		message.setFromUserId(fromUserId==null?0:fromUserId);
		message.setBottomText("点击审核");
		message.setChildType(1);
		message.setContent(getInviteMessageDesc(fromUserPhone,fromUserNick));
		message.setCreateTime(new Date());
		message.setIcon("");
		message.setIsPush(1);
		message.setParentType(2);
		message.setPicture("");
		message.setTitle("玩家申请");
		message.setToUserId(toUserId);
		message.setUrlType(0);
		message.setToUrl(getInviteMessageUrl(familyInviteId));
		return message;
	}
	
	public Long insertMessage(Message msg,MessageEnum type) {
		switch(type) {
		case SYSTEM:
			break;
		case INVITE_FAMILY:
			messageMapper.insertMessage(msg);
			break;
		 default:
			break;
		}
		return msg.getId();
	}
	
	public String getInviteMessageDesc(String fromUserPhone,String fromUserNick) {
		if(ValidTools.isNotBlank(fromUserNick)) {
			return String.format(InviteMessage, fromUserNick+fromUserPhone);
		}else {
			return String.format(InviteMessage, fromUserPhone);
		}
	}
	
	public String getInviteMessageUrl(Long familyInviteId) {
	 
			return String.format(InviteMessageUrl, String.valueOf(familyInviteId));
	}
	 
}
