package com.idata365.app.controller.open;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.AssetUsersAsset;
import com.idata365.app.service.AssetService;


@RestController
public class AssetController extends BaseController{
	protected static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	@Autowired
	AssetService assetService;
	/**
	 * 
	    * @Title: getUserAsset
	    * @Description: TODO(通过用户id获取资产)
	    * @param @param userId
	    * @param @param sign
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@RequestMapping(value = "/asset/getUserAsset",method = RequestMethod.POST)
	Map<String,Object>  getUserAsset(@RequestParam(value="userId")  long  userId,@RequestParam(value="sign") String sign){
		LOG.info("PARAM:"+userId+"==="+sign);
		LOG.info("校验逻辑待处理·~~~");
		AssetUsersAsset usersAsset=assetService.getUserAssetByUserId(userId);
		Map<String,Object> rtMap=new HashMap<String,Object>();
		if(usersAsset!=null) {
			rtMap.put("userId", userId);
			rtMap.put("powerNum", usersAsset.getHadPowerNum());
			rtMap.put("diamondsNum", usersAsset.getHadDiamondsNum());
		}else {
			rtMap.put("userId", userId);
			rtMap.put("powerNum", "0");
			rtMap.put("diamondsNum","0");
		}
		return rtMap;
	}
	/**
	 * 
	    * @Title: getUserAsset
	    * @Description: TODO(diamondNum减少)
	    * @param @param userId
	    * @param @param diamondNum
	    * @param @param sign
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@RequestMapping(value = "/asset/submitDiamondAsset",method = RequestMethod.POST)
	boolean  submitDiamondAsset(@RequestParam(value="userId")  long  userId,@RequestParam(value="diamondNum") double diamondNum,@RequestParam(value="sign") String sign){
		LOG.info("PARAM:"+userId+"==="+diamondNum+"===="+sign);
		LOG.info("校验逻辑待处理·~~~");
		boolean bl=assetService.updateDiamondsConsume(userId, diamondNum);
		return bl;
	}
	

}
