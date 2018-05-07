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
import com.idata365.app.entity.AwardBean;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.entity.ImMsg;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.TaskMessagePush;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.MessageMapper;
import com.idata365.app.mapper.TaskMessagePushMapper;
import com.idata365.app.partnerApi.ManagePushApi;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ValidTools;

 

@Service
public class MessageService extends BaseService<MessageService>{
	private final static Logger LOG = LoggerFactory.getLogger(MessageService.class);
	public static final String  H5Host="http://apph5.idata365.com/";
	public static final String  InviteMessage="玩家【%s】申请加入您的车族，请尽快审核，别让您的粉丝等太久哦！";
	public static final String  PassFamilyMessage="族长【%s】同意了您的申请，欢迎来到【%s】大家族！";
	public static final String  FailFamilyMessage="抱歉，您申请加入【%s】家族失败！";
	public static final String RegMessage="欢迎您加入【好车族】游戏，在这里您可以关注自身驾驶行为，即有机会赢取超级大奖！快来看看如何玩转车族吧！";
	public static final String TietiaoMessage="ohh，车族【%s】发生了一起违规，赶紧来贴条吧！";
	public static final String AchieveMessage="新成就达成！来看看奖励吧！";
	public static final String KaijiangMessage=H5Host+"share/lottery.html";
	
	
	public static final String  InviteMessageUrl="com.shujin.haochezu://check.push?msgId=%s";
	public static final String  InvitePassMessageUrl="com.shujin.haochezu://family.push?isFamilyMine=1&isHomeEnter=0&familyId=%s";
	
	public static final String  TietiaoMessageUrl="com.shujin.haochezu://pasteNote.push?familyId=%s";
	public static final String AchieveMessageUrl="com.shujin.haochezu://achievementDetails.push?achieveId=%s&titleName=%s";
	//聊天室
	public static final String ImMessageUrl="com.shujin.haochezu://chatMain.push?familyId=%s";
	
	
	
	public static final String RegMessageUrl=H5Host+"share/home.html";
	public static final String KaijiangMessageUrl=H5Host+"share/lottery.html";
	
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
			message.setChildType(1);
			message.setContent(getRegMessageDesc());
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(1);
			message.setPicture("");
			message.setTitle("欢迎您!");
			message.setToUserId(toUserId);
			message.setUrlType(1);
			message.setToUrl(getH5RegMessageUrl());
			break;
		case INVITE_FAMILY:
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
			break;
		case PASS_FAMILY:{
			FamilyResultBean f=familyService.findFamily(fromUserId); 
			message.setFromUserId(fromUserId==null?0:fromUserId);
			message.setBottomText("");
			message.setChildType(2);
			message.setContent(getPassMessageDesc(fromUserPhone,fromUserNick,f.getMyFamilyName()));
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(2);
			message.setPicture("");
			message.setTitle("族长审核");
			message.setToUserId(toUserId);
			message.setUrlType(0);
			message.setToUrl(getPassMessageUrl(f.getMyFamilyId()));
			break;}
		case FAIL_FAMILY:
			FamilyResultBean ff=familyService.findFamily(fromUserId); 
			message.setFromUserId(fromUserId==null?0:fromUserId);
			message.setBottomText("");
			message.setChildType(3);
			message.setContent(getFailMessageDesc(ff.getMyFamilyName()));
			message.setCreateTime(new Date());
			message.setIcon("");
			message.setIsPush(1);
			message.setParentType(2);
			message.setPicture("");
			message.setTitle("族长审核");
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
				message.setChildType(0);
				message.setContent(getAchieveDesc(""));
				message.setCreateTime(new Date());
				message.setIcon("");
				message.setIsPush(1);
				message.setParentType(5);
				message.setPicture("");
				message.setTitle("成就通知");
				message.setToUserId(toUserId);
				message.setUrlType(0);
				message.setToUrl(getAchieveMessageUrl(achieveId,achieveName));
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
	public void pushMessage(Message msg,MessageEnum type) {
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
		boolean hadXT=false,hadSP=false,hadTT=false,hadKJ=false,hadCJ=false;
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String,String>  rtMap1=new HashMap<String,String>();
		rtMap1.put("desc", "系统想对你说的话......");
		Map<String,String>  rtMap2=new HashMap<String,String>();
		rtMap2.put("desc", "在这里查看您加入车族的申请、审核进度");
		Map<String,String>  rtMap3=new HashMap<String,String>();
		rtMap3.put("desc", "不要错过给对方家族狠狠一贴的机会!");
		Map<String,String>  rtMap4=new HashMap<String,String>();
		rtMap4.put("desc", "大奖花落谁家？你也有机会!");
		Map<String,String>  rtMap5=new HashMap<String,String>();
		rtMap5.put("desc", "新成就达成！来看看奖励吧!");
		List<Map<String,Object>> findList=messageMapper.getMsgMainTypes(userId);
		for(Map<String,Object> map:findList) {
			int parentType=Integer.valueOf(map.get("parentType").toString());
			if(parentType==1) {
				rtMap1.put("msgType", "1");
				rtMap1.put("unRead", String.valueOf(map.get("typeCount")));
				rtMap1.put("icon",MessageImgs.get("1"));
				rtMap1.put("title", "系统消息");
				hadXT=true;
			}else if(parentType==2) {
				rtMap2.put("msgType", "2");
				rtMap2.put("unRead", String.valueOf(map.get("typeCount")));
				rtMap2.put("icon",MessageImgs.get("2"));
				rtMap2.put("title", "审批消息");
				hadSP=true;
			}else if(parentType==3) {
				rtMap3.put("msgType", "3");
				rtMap3.put("unRead", String.valueOf(map.get("typeCount")));
				rtMap3.put("icon",MessageImgs.get("3"));
				rtMap3.put("title", "贴条消息");
				hadTT=true;
			}
			else if(parentType==5) {
				rtMap5.put("msgType", "5");
				rtMap5.put("unRead", String.valueOf(map.get("typeCount")));
				rtMap5.put("icon",MessageImgs.get("5"));
				rtMap5.put("title", "成就消息");
				hadCJ=true;
			}
		}
		if(!hadXT) {
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
		
		
		if(!hadSP) {
			rtMap2.put("msgType", "2");
			rtMap2.put("unRead", "0");
			rtMap2.put("icon",MessageImgs.get("2"));
			rtMap2.put("title", "审批消息");
		} 
		paramMap.put("parentType", 2);
		timeMsg=messageMapper.getMsgMainTypeTime(paramMap);
		if(timeMsg!=null && timeMsg.size()>0) {
			String time=String.valueOf(timeMsg.get("createTime"));
			rtMap2.put("lastMsgTime", getMsgTyoeTimes(time));
		}else {
			rtMap2.put("lastMsgTime", "");
		}
		if(!hadTT)  {
			rtMap3.put("msgType", "3");
			rtMap3.put("unRead","0");
			rtMap3.put("icon",MessageImgs.get("3"));
			rtMap3.put("title", "贴条消息");
		}
		paramMap.put("parentType", 3);
		timeMsg=messageMapper.getMsgMainTypeTime(paramMap);
		if(timeMsg!=null && timeMsg.size()>0) {
			String time=String.valueOf(timeMsg.get("createTime"));
			rtMap3.put("lastMsgTime", getMsgTyoeTimes(time));
		}else {
			rtMap3.put("lastMsgTime", "");
		}
		
		//开奖为特殊情况,从 awardInfo获取数据
		if(!hadKJ){
			rtMap4.put("msgType", "4");
			rtMap4.put("unRead","0");
			rtMap4.put("icon",MessageImgs.get("4"));
			rtMap4.put("title", "开奖消息");	
		}
		paramMap.put("parentType", 4);
		timeMsg=messageMapper.getMsgKaijiangTime(paramMap);
		
		
		if(timeMsg!=null && timeMsg.size()>0) {
			String time=String.valueOf(timeMsg.get("createTime"));
			rtMap4.put("lastMsgTime", getMsgTyoeTimes(time));
		}else {
			rtMap4.put("lastMsgTime", "");
		}
		//开奖为特殊情况===end
		if(!hadCJ)  {
			rtMap5.put("msgType", "5");
			rtMap5.put("unRead","0");
			rtMap5.put("icon",MessageImgs.get("5"));
			rtMap5.put("title", "成就消息");
		}
		paramMap.put("parentType", 5);
		
		timeMsg=messageMapper.getMsgMainTypeTime(paramMap);
		if(timeMsg!=null && timeMsg.size()>0) {
			String time=String.valueOf(timeMsg.get("createTime"));
			rtMap5.put("lastMsgTime", getMsgTyoeTimes(time));
		}else {
			rtMap5.put("lastMsgTime", "");
		}
		//23541
		list.add(rtMap2);
		list.add(rtMap3);
		list.add(rtMap5);
	    list.add(rtMap4);
	    list.add(rtMap1);
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
			return String.format(InviteMessage, fromUserNick+fromUserPhone);
		}else {
			return String.format(InviteMessage, fromUserPhone);
		}
	}
	private String getPassMessageDesc(String fromUserPhone,String fromUserNick,String familyName) {
		if(ValidTools.isNotBlank(fromUserNick)) {
			return String.format(PassFamilyMessage, fromUserNick+fromUserPhone,familyName);
		}else {
			return String.format(PassFamilyMessage, fromUserPhone,familyName);
		}
	}
	
	private String getFailMessageDesc(String familyName) {
		return String.format(FailFamilyMessage, familyName);
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
