package com.idata365.app.controller.open;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.AuctionLogs;
import com.idata365.app.entity.bean.AuctionBean;
import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.service.ImService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.SignUtils;

@RestController
public class NotifyController extends BaseController{
	protected static final Logger LOG = LoggerFactory.getLogger(NotifyController.class);
	@Autowired
	ImService imService;
	@Autowired
	ChezuAppService chezuAppService;
	/**
	 * 
	    * @Title: notifyAuction
	    * @Description: TODO(进行竞拍信息的通知)
	    * @param @param auctionBean
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
 
	@RequestMapping(value = "/im/notifyAuction",method = RequestMethod.POST)
	public Map<String,Object>  notifyAuction(@RequestBody AuctionBean auctionBean,String auctionPerson,String auctionTimes)
	{
//		LOG.info("userId="+userId+"===sign="+sign);
		LOG.info("notifyAuction"+"=="+auctionBean.getAuctionGoods().getAuctionGoodsId()+"=="+auctionPerson+"=="+auctionTimes);
		Map<String,String> goodsList=new HashMap<String,String>();
		goodsList.put("goodsId", String.valueOf(auctionBean.getAuctionGoods().getAuctionGoodsId()));
		goodsList.put("endTime", DateTools.getDateTime(auctionBean.getAuctionGoods().getAuctionRealEndTime()));
//		goodsList.put("auctionTime", String.valueOf(auctionBean.getAuctionGoods().getAuctionEndTime()));
		goodsList.put("auctionDiamond", String.valueOf(auctionBean.getAuctionGoods().getDoneDiamond().doubleValue()));
		goodsList.put("isEnd", String.valueOf(auctionBean.getAuctionGoods().getAuctionStatus()==0?0:1));
		
		 
 
		Map<String,Object> notifyInfo=new HashMap<String,Object>();
		notifyInfo.put("goodsId", String.valueOf(auctionBean.getAuctionGoods().getAuctionGoodsId()));
		notifyInfo.put("endTime", DateTools.getDateTime(auctionBean.getAuctionGoods().getAuctionRealEndTime()));
//		notifyInfo.put("auctionTime", String.valueOf(auctionBean.getAuctionGoods().getAuctionEndTime()));
		notifyInfo.put("auctionTimes", auctionTimes);
		notifyInfo.put("auctionPerson", auctionPerson);
		notifyInfo.put("auctionDiamond", String.valueOf(auctionBean.getAuctionGoods().getDoneDiamond().doubleValue()));
		notifyInfo.put("isEnd", String.valueOf(auctionBean.getAuctionGoods().getAuctionStatus()==0?0:1));
		
		List<Map<String,String>> actionLogs=new ArrayList<Map<String,String>>();
		List<AuctionLogs> list=auctionBean.getAuctionLogsList();
		for(AuctionLogs log:list) {
			Map<String,String> logMap=new HashMap<String,String>();
			logMap.put("nick", log.getAuctionUserNick());
			logMap.put("auctionTime", DateTools.getDateTime(log.getAuctionTime()));
			logMap.put("auctionDiamond", String.valueOf(log.getAuctionDiamond().doubleValue()));
			actionLogs.add(logMap);
		}
		notifyInfo.put("actionLogs", actionLogs);
		imService.sendAuctionMsg(goodsList,notifyInfo, String.valueOf(auctionBean.getAuctionGoods().getAuctionGoodsId()));
	    LOG.info(auctionBean.getAuctionGoods().getPrizeName());
		return null;
	}
	 /**
	  * 
	     * @Title: notifyFamilyChange
	     * @Description: TODO(这里用一句话描述这个方法的作用)
	     * @param @param userId
	     * @param @return    参数
	     * @return boolean    返回类型
	     * @throws
	     * @author LanYeYe
	  */
	@RequestMapping(value = "/im/notifyFamilyChange",method = RequestMethod.POST)
	public boolean notifyFamilyChange(@RequestBody List<Map<String,Object>> list,@RequestParam(value="sign") String sign)
	{
		LOG.info("list"+"=="+list.size()+"=="+sign+"=="+sign);
		 imService.changeFamiliesUsersIm(list);
		return true;
	}
	
	
	private final String PrayingSubmit="俱乐部通知:玩家@【%s】@祈愿一个 %s,一方有难八方支援,各位大佬快去资助TA吧。";
	private final String PrayingRealize="俱乐部通知:感谢俱乐部！玩家@【%s】@资助@【%s】@一个 %s!真是帮了大忙了!";
	private final String LookedAllAd="通知:玩家@【%s】@今日的所有视频任务已完成,奖励300点动力!";
	private final String DoingAllActMission="通知:玩家@【 %s】@今日的所有活动任务已完成，奖励动力 %s点!";
			
   /**
    * 
       * @Title: prayingSubmit
       * @Description: TODO(祈愿提交)
       * @param @param fromUserName
	   * @param @param toUserName
	   * @param @param propName
	   * @param @param sign
       * @param @return    参数
       * @return boolean    返回类型
       * @throws
       * @author LanYeYe
    */
	@RequestMapping(value = "/im/prayingSubmit",method = RequestMethod.POST)
	public boolean prayingSubmit(@RequestParam(value="fromUserName") String fromUserName,
			@RequestParam(value="toUserName") String toUserName,
			@RequestParam(value="toUserId") String toUserId,
			@RequestParam(value="propName") String propName,
			@RequestParam(value="sign") String sign){
		String sign2=SignUtils.encryptHMAC(toUserName);
		Map<String, Object> rtMap=chezuAppService.familyUsers(Long.valueOf(toUserId), sign2);
		Map<String,Object> insert=new HashMap<>();
		insert.put("userId", 0);
		insert.put("nick", "");
		insert.put("time", DateTools.getCurDate2());
		insert.put("imgUrl", "");
		insert.put("familyId",rtMap.get("createFamilyId"));
		insert.put("msg", String.format(PrayingSubmit,toUserName,propName));
		insert.put("type", 1);
		imService.insertMsg(insert);
		insert.put("familyId",rtMap.get("partakeFamilyId"));
		insert.put("type", 2);
		imService.insertMsg(insert);
		imService.sendUserFamilyImByPraying(insert,rtMap,1);
		imService.sendUserFamilyImByPraying(insert,rtMap,2);
		return true;
	}
	
	/**
	 * 
	    * @Title: prayingRealize
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param fromUserName
	    * @param @param toUserName
	    * @param @param propName
	    * @param @param sign
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@RequestMapping(value = "/im/prayingRealize",method = RequestMethod.POST)
	public boolean prayingRealize(@RequestParam(value="fromUserName") String fromUserName,
			@RequestParam(value="toUserName") String toUserName,
			@RequestParam(value="toUserId") String toUserId,
			@RequestParam(value="propName") String propName,
			@RequestParam(value="sign") String sign)
	{
		String sign2=SignUtils.encryptHMAC(toUserName);
		Map<String, Object> rtMap=chezuAppService.familyUsers(Long.valueOf(toUserId), sign2);
		Map<String,Object> insert=new HashMap<>();
		insert.put("userId", 0);
		insert.put("nick", "");
		insert.put("time", DateTools.getCurDate2());
		insert.put("imgUrl", "");
		insert.put("familyId",rtMap.get("createFamilyId"));
		insert.put("msg", String.format(PrayingRealize,fromUserName,toUserName,propName));
		insert.put("type", 1);
		imService.insertMsg(insert);
		insert.put("familyId",rtMap.get("partakeFamilyId"));
		insert.put("type", 2);
		imService.insertMsg(insert);
		imService.sendUserFamilyImByPraying(insert,rtMap,1);
		imService.sendUserFamilyImByPraying(insert,rtMap,2);
		return true;
	}
	
	/**
	 * 
	    * @Title: lookedAllAd
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param toUserName
	    * @param @param sign
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author lcc
	 */
	@RequestMapping(value = "/im/lookedAllAd",method = RequestMethod.POST)
	public boolean lookedAllAd(@RequestParam(value="userName") String userName,
			@RequestParam(value="userId") String userId,
			@RequestParam(value="sign") String sign)
	{
		Map<String,Object> insert=new HashMap<>();
		insert.put("userId", 0);
		insert.put("nick", userName);
		insert.put("time", DateTools.getCurDate2());
		insert.put("imgUrl", "");
		insert.put("familyId",0);
		insert.put("msg", String.format(LookedAllAd,userName));
		insert.put("type", 0);
		imService.insertMsg(insert);
		imService.sendGloadIm(insert);
		return true;
	}
	
	/**
	 * 
	    * @Title: doingAllActMission
	    * @Description: TODO()
	    * @param @param toUserName
	    * @param @param sign
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author lcc
	 */
	@RequestMapping(value = "/im/doingAllActMission",method = RequestMethod.POST)
	public boolean doingAllActMission(@RequestParam(value="userName") String userName,@RequestParam(value="powerNum")long powerNum,
			@RequestParam(value="userId") String userId,
			@RequestParam(value="sign") String sign)
	{
		Map<String,Object> insert=new HashMap<>();
		insert.put("userId", 0);
		insert.put("nick", userName);
		insert.put("time", DateTools.getCurDate2());
		insert.put("imgUrl", "");
		insert.put("familyId",0);
		insert.put("msg", String.format(DoingAllActMission,userName,String.valueOf(powerNum)));
		insert.put("type", 0);
		imService.insertMsg(insert);
		imService.sendGloadIm(insert);
		return true;
	}
}
