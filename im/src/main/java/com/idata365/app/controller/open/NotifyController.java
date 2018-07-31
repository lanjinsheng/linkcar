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
	
	
	private final String PrayingSubmit="家族通知:玩家@【%s】@祈愿一个 %s,一方有难八方支援,各位大佬快去资助TA吧。";
	private final String PrayingRealize="家族统治:感动俱乐部！玩家@【%s】@资助@【%s】@一个 %s!真是帮了大忙了!";
			
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
			@RequestParam(value="propName") String propName,
			@RequestParam(value="sign") String sign){
		String sign2=SignUtils.encryptHMAC(toUserName);
		Map<String, Object> rtMap=chezuAppService.familyUsers(Long.valueOf(toUserName), sign2);
		Map<String,Object> insert=new HashMap<>();
		insert.put("userId", 0);
		insert.put("nick", "");
		insert.put("time", DateTools.getCurDate2());
		insert.put("imgUrl", "");
		insert.put("familyId",rtMap.get("createFamilyId"));
		insert.put("content", String.format(PrayingSubmit, toUserName,propName));
		imService.insertMsg(insert);
		insert.put("familyId",rtMap.get("partakeFamilyId"));
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
			@RequestParam(value="propName") String propName,
			@RequestParam(value="sign") String sign)
	{
		String sign2=SignUtils.encryptHMAC(toUserName);
		Map<String, Object> rtMap=chezuAppService.familyUsers(Long.valueOf(toUserName), sign2);
		Map<String,Object> insert=new HashMap<>();
		insert.put("userId", 0);
		insert.put("nick", "");
		insert.put("time", DateTools.getCurDate2());
		insert.put("imgUrl", "");
		insert.put("familyId",rtMap.get("createFamilyId"));
		insert.put("content", String.format(PrayingSubmit, toUserName,propName));
		imService.insertMsg(insert);
		insert.put("familyId",rtMap.get("partakeFamilyId"));
		imService.insertMsg(insert);
		imService.sendUserFamilyImByPraying(insert,rtMap,1);
		imService.sendUserFamilyImByPraying(insert,rtMap,2);
		return true;
	}
	
}
