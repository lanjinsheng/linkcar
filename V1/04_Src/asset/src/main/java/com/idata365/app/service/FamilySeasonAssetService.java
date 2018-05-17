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
import com.idata365.app.entity.FamilySeasonAsset;
import com.idata365.app.mapper.AssetFamiliesAssetMapper;
import com.idata365.app.mapper.AssetFamiliesPowerLogsMapper;
import com.idata365.app.mapper.AssetUsersAssetMapper;
import com.idata365.app.mapper.AssetUsersDiamondsLogsMapper;
import com.idata365.app.mapper.AssetUsersPowerLogsMapper;
import com.idata365.app.mapper.FamilyGameAssetMapper;
import com.idata365.app.mapper.FamilySeasonAssetMapper;
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
public class FamilySeasonAssetService extends BaseService<FamilySeasonAssetService> {
	private final static Logger LOG = LoggerFactory.getLogger(FamilySeasonAssetService.class);
 
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
	FamilySeasonAssetMapper familySeasonAssetMapper;
	@Autowired
	ChezuAccountService chezuAccountService;
	public FamilySeasonAssetService() {

	}
	@Transactional
	public void insertSeasonAsset(FamilySeasonAsset familySeasonAsset) {
		familySeasonAssetMapper.insertFamilySeasonAsset(familySeasonAsset);
	}
 
}
