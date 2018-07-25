package com.idata365.app.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.idata365.app.constant.OrderTypeConstant;
import com.idata365.app.entity.AuctionGoods;
import com.idata365.app.entity.AuctionLogs;
import com.idata365.app.entity.Order;
import com.idata365.app.mapper.AuctionGoodMapper;
import com.idata365.app.mapper.AuctionLogsMapper;
import com.idata365.app.mapper.OrderMapper;
import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.util.SignUtils;
 
@Service
public class AuctionTaskService extends BaseService<AuctionTaskService>{
	private final static Logger LOG = LoggerFactory.getLogger(AuctionTaskService.class);
 
	@Autowired
	AuctionGoodMapper auctionGoodMapper;
	@Autowired
	AuctionLogsMapper auctionLogsMapper;
	@Autowired
    ChezuAssetService chezuAssetService;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private ChezuAppService chezuAppService;
 //任务执行
	@Transactional
	public List<AuctionGoods> getAuctionTask(AuctionGoods auctionGoods){
		//先锁定任务
		auctionGoodMapper.lockAuctionTask(auctionGoods);
		//返回任务列表
		return auctionGoodMapper.getAuctionTask(auctionGoods);
	}
	@Transactional
	public	void updateSuccTask(AuctionGoods auctionGoods) {
		auctionGoodMapper.updateAuctionSuccTask(auctionGoods);
	}
//	
	@Transactional
	public void updateFailTask(AuctionGoods auctionGoods) {
		if(auctionGoods.getFailTimes()<100) {
			auctionGoods.setTaskStatus(0);
			auctionGoodMapper.updateAuctionFailTask(auctionGoods);
		}else {
			auctionGoods.setTaskStatus(-2);
			auctionGoodMapper.updateAuctionFailTask(auctionGoods);
		}
		
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		auctionGoodMapper.clearLockTask(compareTimes);
	}
	
	 
 
	@Transactional
   public boolean doEndAuction(AuctionGoods auctionGoods) {
		LOG.info("auctionGoods=============" + JSON.toJSONString(auctionGoods));
		// 查找最高竞价者
		AuctionLogs max = auctionLogsMapper.getMaxAuctionDiamond(auctionGoods.getAuctionGoodsId());
		if (max == null) {// 无竞价者
			// 更新竞价状态
			int type = auctionGoods.getAuctionGoodsType();
			if (type == 3) {
				// 现金红包
				auctionGoods.setAuctionStatus(4);//
			} else {
				auctionGoods.setAuctionStatus(3);
			}
			auctionGoodMapper.updateGoodsStatusByTask(auctionGoods);
			return true;
		} else {
			// 更新竞价状态
			int type = auctionGoodMapper.findAuctionGoodById(max.getAuctionGoodsId()).getAuctionGoodsType().intValue();
			if (type == 3) {
				// 现金红包无需填写信息 0：未拍获 1：待填写信息2：待发货3.已完成4：待确认
				auctionGoods.setAuctionStatus(2);
			} else {
				auctionGoods.setAuctionStatus(1);
			}

			auctionGoods.setWinnerId(max.getAuctionUserId());
			auctionGoods.setDoneDiamond(max.getAuctionDiamond());
			auctionGoodMapper.updateGoodsByTask(auctionGoods);
			
			//保存订单
			Order order = new Order();
			order.setUserId(max.getAuctionUserId());
			order.setDiamondNum(max.getAuctionDiamond());
			order.setOrderType("0");//付款类型
			order.setOrderNum(1);
			order.setOrderStatus("1");
			order.setOrderTime(new Date());
			order.setPrizeId(max.getAuctionGoodsId());
			order.setBusinessType(OrderTypeConstant.AUCTION);
			orderMapper.insert(order);

			//远程处理资产信息
			String sign=SignUtils.encryptHMAC(max.getAuctionUserId()+"");
			boolean bl=chezuAssetService.unfreezeDiamondAsset(max.getAuctionUserId(), 
					auctionGoods.getOfUserId(), 
					auctionGoods.getAuctionGoodsId(),
					max.getAuctionDiamond().doubleValue(), sign);
			if(!bl) {
				throw new RuntimeException("远程资产调用失败");
			}
			StringBuffer usersStr=new StringBuffer("");
			try {
				//发送成功竞价者消息
				sign=SignUtils.encryptHMAC(auctionGoods.getAuctionGoodsId()+""+1+max.getAuctionUserId());
				chezuAppService.sendAuctionMsg(auctionGoods.getAuctionGoodsId(),
						auctionGoods.getAuctionGoodsType(),1, String.valueOf(max.getAuctionUserId()), 
						auctionGoods.getPrizeName(), sign);
				List<AuctionLogs> users=auctionLogsMapper.listAllAuctionUsers(auctionGoods.getAuctionGoodsId());
				for(AuctionLogs user:users) {
					if(user.getAuctionUserId().longValue()!=max.getAuctionUserId().longValue()) {
						usersStr.append(user.getAuctionUserId());
						usersStr.append(",");
					}
				}
				if(usersStr.length()>0) {
					String usersArray=usersStr.substring(0, usersStr.length()-1);
					sign=SignUtils.encryptHMAC(auctionGoods.getAuctionGoodsId()+""+0+usersArray);
					//发送失败竞价者消息
					chezuAppService.sendAuctionMsg(auctionGoods.getAuctionGoodsId(),
							auctionGoods.getAuctionGoodsType(), 0, usersArray, 
							auctionGoods.getPrizeName(), sign);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		
		
   }
 
}
