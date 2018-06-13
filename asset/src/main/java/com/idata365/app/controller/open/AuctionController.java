package com.idata365.app.controller.open;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.enums.PowerEnum;
import com.idata365.app.service.AssetService;

@RestController
public class AuctionController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(AuctionController.class);
	@Autowired
	AssetService assetService;
 
	@RequestMapping(value = "/asset/unfreezeDiamondAsset", method = RequestMethod.POST)
	boolean unfreezeDiamondAsset(@RequestParam(value = "buyerId") long buyerId,@RequestParam(value = "sellerId") long sellerId,
			@RequestParam(value = "auctionGoodsId") long auctionGoodsId,
			@RequestParam(value = "diamondNum") double diamondNum, @RequestParam(value = "sign") String sign ) {
		LOG.info("PARAM:" + buyerId + "===" + sellerId + "====" +diamondNum+"=="+ sign);
		boolean bl=false;
		try{
			  bl = assetService.unfreezeDiamondAsset(buyerId, sellerId, auctionGoodsId, diamondNum);
		}catch(Exception e) {
			e.printStackTrace();
			bl=false;
		}
		return bl;
	}

	public static void main(String[] args) {
		System.out.println("Share".equals(PowerEnum.Share));
	}
}
