package com.idata365.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.AssetUsersDiamondsLogs;
import com.idata365.app.entity.FamilyGameAsset;
import com.idata365.app.mapper.AssetFamiliesAssetMapper;
import com.idata365.app.mapper.AssetFamiliesPowerLogsMapper;
import com.idata365.app.mapper.AssetUsersAssetMapper;
import com.idata365.app.mapper.AssetUsersDiamondsLogsMapper;
import com.idata365.app.mapper.AssetUsersPowerLogsMapper;
import com.idata365.app.mapper.FamilyGameAssetMapper;
import com.idata365.app.mapper.TempPowerRewardMapper;
import com.idata365.app.remote.ChezuAccountService;
import com.idata365.app.util.SignUtils;

/**
 * 
 * @ClassName: TempPowerRewardService
 * @Description: TODO(资产随机分配彩蛋)
 * @author LanYeYe
 * @date 2018年4月27日
 *
 */
@Service
public class FamilyGameAssetService extends BaseService<FamilyGameAssetService> {
	private final static Logger LOG = LoggerFactory.getLogger(FamilyGameAssetService.class);
 
	@Autowired
	TempPowerRewardMapper tempPowerRewardMapper;
	@Autowired
	AssetUsersAssetMapper assetUsersAssetMapper;
	@Autowired
	AssetUsersDiamondsLogsMapper assetUsersDiamondsLogsMapper;
	@Autowired
	AssetUsersPowerLogsMapper assetUsersPowerLogsMapper;
	@Autowired
	AssetFamiliesPowerLogsMapper assetFamiliesPowerLogsMapper;
	@Autowired
	AssetFamiliesAssetMapper assetFamiliesAssetMapper;
	@Autowired
	FamilyGameAssetMapper familyGameAssetMapper;
	@Autowired
	ChezuAccountService chezuAccountService;
	
	public FamilyGameAssetService() {

	}

	  
 /**
  * 
     * @Title: getFamilyDistribution
     * @Description: TODO(effectId这里用一句话描述这个方法的作用)
     * @param @param familyId
     * @param @param familySeasonId
     * @param @return    参数
     * @return List<Map<String,Object>>    返回类型
     * @throws
     * @author LanYeYe
  */
	@Transactional
	public List<Map<String,Object>> getFamilyDistribution(long familyId,long familySeasonId) {
		List<Map<String,Object>> rtList=new ArrayList<Map<String,Object>>();
		List<AssetUsersDiamondsLogs>  diamondsLogs=assetUsersDiamondsLogsMapper.getPkDiamondsByEffectId(familySeasonId);
		StringBuffer users=new StringBuffer();
		for(AssetUsersDiamondsLogs d:diamondsLogs) {
			users.append(d.getUserId());
			users.append(",");
		}
		String userIds=users.substring(0, users.length()-1);
		String sign=SignUtils.encryptHMAC(userIds);
		Map<String,Object> userInfos=chezuAccountService.getUsersInfoByIds(userIds, familyId,sign);
		String []nickNames=String.valueOf(userInfos.get("nickNames")).split(",");
		String []userHeadUrls=String.valueOf(userInfos.get("userHeadUrls")).split(",");
		String []isPatriarchs=String.valueOf(userInfos.get("isPatriarchs")).split(",");
		int i=0;
		for(AssetUsersDiamondsLogs d:diamondsLogs) {
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("rewardNum",String.valueOf(d.getDiamondsNum().doubleValue()));
			m.put("nickName",nickNames[i]);
			m.put("headImg",userHeadUrls[i]);
			m.put("isPatriarch", isPatriarchs[i]);
			rtList.add(m);
			i++;
		}
		 
		return rtList;
	}
	
	
	@Transactional
	public List<Map<String,Object>> getFamilyGameAsset(long familyId,long familySeasonId) {
		List<Map<String,Object>> rtList=new ArrayList<Map<String,Object>>();
		FamilyGameAsset familyGameAsset=new FamilyGameAsset();
		familyGameAsset.setId(familySeasonId);
		familyGameAsset.setFamilyId(familyId);
		List<FamilyGameAsset> list=null;
		if(familySeasonId==0) {
			//首页查找
			  list=familyGameAssetMapper.getFamilyGameAssets(familyGameAsset);
		}else {
			 list=familyGameAssetMapper.getFamilyGameAssetsPre(familyGameAsset);
		}
		if(list!=null) {
			for(FamilyGameAsset fga:list) {
				Map<String,Object> m=new HashMap<String,Object>();
				m.put("familySeasonID",String.valueOf(fga.getId()));
				m.put("seasonTimes", fga.getEndDay());
				m.put("seasonRank", String.valueOf(fga.getOrderNo()));
				m.put("seasonReward", fga.getDiamondsNum());
				rtList.add(m);
			}
		}
		return rtList;
	}
	@Transactional
	public void insertgameAsset(FamilyGameAsset familyGameAsset) {
		familyGameAssetMapper.insertFamilyGameAsset(familyGameAsset);
	}
 
}
