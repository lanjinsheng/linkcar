package com.idata365.app.remote;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class ChezuAssetHystric implements ChezuAssetService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAssetHystric.class);

	@Override
	public Map<String,Object> getUserAsset(long userId, String sign) {
		LOG.info(" getUserAsset 挂了   ChezuAssetService");
		return null;
	}


	@Override
	public boolean submitDiamondAsset(long userId, double diamondNum, String sign, long ofUserId) {
		LOG.info(" submitDiamondAsset 挂了   ChezuAssetService");
		return false;
	}

	@Override
	public Map<String,String> freezeDiamondAsset(long userId, double diamondNum, String sign, long preUserId, long auctionGoodsId) {
		LOG.info(" freezeDiamondAsset 挂了   ChezuAssetService");
		return null;
	}


	@Override
	public boolean unfreezeDiamondAsset(long buyerId, long sellerId, long auctionGoodsId, double diamondNum,
			String sign) {
		// TODO Auto-generated method stub
		LOG.info(" unfreezeDiamondAsset 挂了   ChezuAssetService");
		return false;
	}


  

 
}
