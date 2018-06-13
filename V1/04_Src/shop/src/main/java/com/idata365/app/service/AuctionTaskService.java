package com.idata365.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.AuctionGoods;
import com.idata365.app.entity.AuctionLogs;
import com.idata365.app.mapper.AuctionGoodMapper;
import com.idata365.app.mapper.AuctionLogsMapper;
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
	   //查找最高竞价者
		AuctionLogs max=auctionLogsMapper.getMaxAuctionDiamond(auctionGoods.getAuctionGoodsId());
		if(max==null) {//无竞价者
			//更新竞价状态
			auctionGoods.setAuctionStatus(3);
			auctionGoodMapper.updateGoodsStatusByTask(auctionGoods);
			return true;
		}else {
			//更新竞价状态
			auctionGoods.setAuctionStatus(1);
			auctionGoods.setWinnerId(max.getAuctionUserId());
			auctionGoods.setDoneDiamond(max.getAuctionDiamond());
			auctionGoodMapper.updateGoodsByTask(auctionGoods);
			//远程处理资产信息
			String sign=SignUtils.encryptHMAC(max.getAuctionUserId()+"");
			boolean bl=chezuAssetService.unfreezeDiamondAsset(max.getAuctionUserId(), auctionGoods.getOfUserId(), max.getAuctionDiamond().doubleValue(), sign);
			if(!bl) {
				throw new RuntimeException("远程资产调用失败");
			}
			return true;
		}
		
		
   }
 
}
