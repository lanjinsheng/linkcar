package com.idata365.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.util.SignUtils;
import org.apache.commons.lang3.RandomUtils;
/**
 * 
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.AssetConstant;
import com.idata365.app.constant.IntroduceConstant;
import com.idata365.app.entity.AssetFamiliesAsset;
import com.idata365.app.entity.AssetFamiliesPowerLogs;
import com.idata365.app.entity.AssetUsersAsset;
import com.idata365.app.entity.AssetUsersDiamondsLogs;
import com.idata365.app.entity.AssetUsersPowerLogs;
import com.idata365.app.entity.AuctionUsersDiamondsLogs;
import com.idata365.app.entity.FamilyGameAsset;
import com.idata365.app.entity.FamilySeasonAsset;
import com.idata365.app.entity.StealPower;
import com.idata365.app.mapper.AssetFamiliesAssetMapper;
import com.idata365.app.mapper.AssetFamiliesPowerLogsMapper;
import com.idata365.app.mapper.AssetUsersAssetMapper;
import com.idata365.app.mapper.AssetUsersDiamondsLogsMapper;
import com.idata365.app.mapper.AssetUsersPowerLogsMapper;
import com.idata365.app.mapper.AuctionUsersDiamondsLogsMapper;
import com.idata365.app.mapper.FamilyGameAssetMapper;
import com.idata365.app.mapper.FamilySeasonAssetMapper;
import com.idata365.app.mapper.StealPowerMapper;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ValidTools;

/**
 * 
 * @ClassName: AssetService
 * @Description: TODO(资产处理)
 * @author LanYeYe
 * @date 2018年4月27日
 *
 */
@Service
@Transactional
public class AssetService extends BaseService<AssetService> {
	private final static Logger LOG = LoggerFactory.getLogger(AssetService.class);
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
	StealPowerMapper stealPowerMapper;
	@Autowired
	FamilyGameAssetMapper familyGameAssetMapper;
	@Autowired
	FamilySeasonAssetMapper familySeasonAssetMapper;
	@Autowired
	AuctionUsersDiamondsLogsMapper auctionUsersDiamondsLogsMapper;
	@Autowired
	ChezuAppService chezuAppService;

	public AssetService() {

	}

	/**
	 * 
	 * @Title: getUserAssetByUserId
	 * @Description: TODO(通过userId获取资产)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return AssetUsersAsset 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	
	public AssetUsersAsset getUserAssetByUserId(long userId) {

		return assetUsersAssetMapper.getUserAssetByUserId(userId);
	}

	/**
	 * 
	 * @Title: getTotalNums
	 * @Description: TODO(返回首页钻石、动力总数量)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return Map<String,String> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	public Map<String, String> getTotalNums(long userId) {
		Map<String, String> map = new HashMap<>();
		AssetUsersAsset usersAsset = assetUsersAssetMapper.getUserAssetByUserId(userId);
		map.put("totalDiamondsNum", usersAsset.getDiamondsNum().setScale(2, RoundingMode.HALF_UP).toString());
		map.put("totalPowersNum", String.valueOf(usersAsset.getPowerNum()));
		map.put("allAppDiamonds",
				assetUsersAssetMapper.getAllAppDiamonds().setScale(0, RoundingMode.HALF_UP).toString());
		map.put("yesterdayDiamonds", "980");
		map.put("allAppPowers", String.valueOf(assetUsersAssetMapper.getAllAppPowers()));
		map.put("allAppDiamondsDesc", "全网总量");
		map.put("yesterdayDiamondsDesc", "昨日产量");
		map.put("allAppPowersDesc", "全网总量");
		map.put("totalPowersNumDesc", "今日个人");
		return map;
	}

	/**
	 * 
	 * @Title: getIndexDiamonds
	 * @Description: TODO(返回首页钻石数量)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return Map<String,String> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	public List<Map<String, String>> getIndexDiamonds(long userId, Map<Object, Object> requestBodyParams) {
		List<AssetUsersDiamondsLogs> list = new ArrayList<>();

		if ("0".equals(requestBodyParams.get("id").toString())) {
			list = assetUsersDiamondsLogsMapper.getIndexDiamondsFirst(userId);
		} else {
			long id = Long.valueOf(String.valueOf(requestBodyParams.get("id")));
			list = assetUsersDiamondsLogsMapper.getIndexDiamonds(userId, id);
		}

		List<Map<String, String>> data = new ArrayList<>();
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> rtMap = new HashMap<String, String>();
				rtMap.put("id", String.valueOf(list.get(i).getId()));
				rtMap.put("receiveType", String.valueOf(list.get(i).getEventType()));
				rtMap.put("recordType", String.valueOf(list.get(i).getRecordType()));
				rtMap.put("receiveTypeName", AssetConstant.UserDiamondsEventMap.get(list.get(i).getEventType()));
				rtMap.put("num", String.valueOf(list.get(i).getDiamondsNum().setScale(2, RoundingMode.HALF_UP)));
				String time = String.valueOf(DateTools.formatDateYMD(list.get(i).getCreateTime()));
//				if ((new Date().getTime() - list.get(i).getCreateTime().getTime()) > (1000 * 3600 * 24 * 365)) {
//					rtMap.put("time", time.substring(0, 10));
//				} else {
//					rtMap.put("time", time.substring(5, 10));
//				}
				rtMap.put("time", time.substring(5, 10));
				rtMap.put("isCanQuery", "0");
				if ("2".equals(String.valueOf(list.get(i).getEventType()))) {
					String fightingTime = DateTools.getAddMinuteDateTime(time, -1 * 60 * 24).substring(0, 10);
					rtMap.put("isCanQuery", "1");
					rtMap.put("fightingTime", fightingTime);
					long familyId = familyGameAssetMapper.getFamilyIdByEffectId(list.get(i).getEffectId());
					rtMap.put("familyId", String.valueOf(familyId));
				}
				data.add(rtMap);
			}
		}
		return data;
	}

	/**
	 * 
	 * @Title: getIndexPowers
	 * @Description: TODO(返回首页动力数量)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return Map<String,String> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	public List<Map<String, String>> getIndexPowers(long userId, Map<Object, Object> requestBodyParams) {
		List<AssetUsersPowerLogs> list = new ArrayList<>();

		if ("0".equals(requestBodyParams.get("id").toString())) {
			list = assetUsersPowerLogsMapper.getIndexPowersFirst(userId);
		} else {
			long id = Long.valueOf(String.valueOf(requestBodyParams.get("id")));
			list = assetUsersPowerLogsMapper.getIndexPowers(userId, id);
		}

		List<Map<String, String>> data = new ArrayList<>();
		if (list.size() == 0) {
			return data;
		} else {
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> rtMap = new HashMap<String, String>();
				rtMap.put("id", String.valueOf(list.get(i).getId()));
				rtMap.put("receiveType", String.valueOf(list.get(i).getEventType()));
				rtMap.put("recordType", String.valueOf(list.get(i).getRecordType()));
				rtMap.put("receiveTypeName", AssetConstant.UserPowerEventMap.get(list.get(i).getEventType()));
				rtMap.put("powerNum", String.valueOf(list.get(i).getPowerNum()));
				rtMap.put("num", String.valueOf(list.get(i).getPowerNum()));// 兼容小波页面而附加
				rtMap.put("time", String.valueOf(DateTools.formatDateD(list.get(i).getCreateTime())).substring(0, 5));
				data.add(rtMap);
			}
		}
		return data;
	}

	/**
	 * 
	 * @param ofUserId
	 * @Title: updateDiamondsConsume
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            userId
	 * @param @param
	 *            diamondsNum
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	
	public boolean updateDiamondsConsume(long userId, double diamondsNum, long ofUserId) {
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("userId", userId);
		datas.put("diamondsNum", diamondsNum);
		int hadUpdate = assetUsersAssetMapper.updateDiamondsConsume(datas);
		Map<String, Object> earn = new HashMap<String, Object>();
		earn.put("userId", ofUserId);
		earn.put("diamondsNum", diamondsNum);
		int addUpdate = assetUsersAssetMapper.updateDiamondsEarn(earn);
		if (hadUpdate > 0 && addUpdate > 0) {
			// 钻石数量够买，则进行日志增加
			AssetUsersDiamondsLogs assetUsersDiamondsLogs = new AssetUsersDiamondsLogs();
			assetUsersDiamondsLogs.setDiamondsNum(BigDecimal.valueOf(diamondsNum));
			assetUsersDiamondsLogs.setEffectId(0L);
			assetUsersDiamondsLogs.setEventType(AssetConstant.EVENTTYPE_BUY);
			assetUsersDiamondsLogs.setRecordType(AssetConstant.RECORDTYPE_2);
			assetUsersDiamondsLogs.setRemark("购买消费");
			assetUsersDiamondsLogs.setUserId(userId);
			assetUsersDiamondsLogsMapper.insertDiamondsConsume(assetUsersDiamondsLogs);

			AssetUsersDiamondsLogs logs = new AssetUsersDiamondsLogs();
			logs.setDiamondsNum(BigDecimal.valueOf(diamondsNum));
			logs.setEffectId(0L);
			logs.setEventType(AssetConstant.EVENTTYPE_EARN);
			logs.setRecordType(AssetConstant.RECORDTYPE_1);
			logs.setRemark("交易收入");
			logs.setUserId(ofUserId);
			assetUsersDiamondsLogsMapper.insertDiamondsConsume(logs);

			return true;
		} else {
			LOG.info("userId=" + userId + "钻石数量不够支付:" + diamondsNum);
			return false;
		}
	}

	
	public Map<String, String> freezeDiamondAsset(long userId, double diamondsNum, long preUserId,
			long auctionGoodsId) {
		Map<String, String> rtMap = new HashMap<String, String>();
		rtMap.put("flag", "1");
		rtMap.put("msg", "");
		int addUpdate = 0;
		BigDecimal preUserDiamondsNum = null;

		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("userId", userId);
		datas.put("diamondsNum", diamondsNum);
		int hadUpdate = assetUsersAssetMapper.updateDiamondsFreeze(datas);// -

		if (hadUpdate != 0) {
			if (0 != preUserId) {
				AuctionUsersDiamondsLogs logsById = auctionUsersDiamondsLogsMapper
						.getAuctionUsersDiamondsLogsById(preUserId, auctionGoodsId);
				preUserDiamondsNum = logsById.getDiamondsNum();
				Map<String, Object> earn = new HashMap<String, Object>();
				earn.put("userId", preUserId);
				earn.put("diamondsNum", preUserDiamondsNum);
				addUpdate = assetUsersAssetMapper.updateDiamondsUnfreeze(earn);// +
			}
			// 钻石数量够买，则进行日志增加
			AuctionUsersDiamondsLogs auctionUsersDiamondsLogs = new AuctionUsersDiamondsLogs();
			auctionUsersDiamondsLogs.setDiamondsNum(BigDecimal.valueOf(diamondsNum));
			auctionUsersDiamondsLogs.setEffectId(auctionGoodsId);
			auctionUsersDiamondsLogs.setEventType(AssetConstant.EVENTTYPE_FREEZE);
			auctionUsersDiamondsLogs.setRecordType(AssetConstant.RECORDTYPE_2);
			auctionUsersDiamondsLogs.setRemark("竞拍冻结");
			auctionUsersDiamondsLogs.setUserId(userId);
			auctionUsersDiamondsLogs.setCreateTime(new Date());
			auctionUsersDiamondsLogsMapper.insertDiamondsConsume(auctionUsersDiamondsLogs);
			if (addUpdate != 0) {
				AuctionUsersDiamondsLogs logs = new AuctionUsersDiamondsLogs();
				logs.setDiamondsNum(preUserDiamondsNum);
				logs.setEffectId(auctionGoodsId);
				logs.setEventType(AssetConstant.EVENTTYPE_THAW);
				logs.setRecordType(AssetConstant.RECORDTYPE_1);
				logs.setRemark("竞拍解冻");
				logs.setUserId(preUserId);
				logs.setCreateTime(new Date());
				auctionUsersDiamondsLogsMapper.insertDiamondsConsume(logs);
			}

		} else {
			LOG.info("userId=" + userId + "钻石数量不够支付:" + diamondsNum);
			rtMap.put("flag", "0");
			rtMap.put("msg", "钻石数量不够支付");
		}
		return rtMap;
	}

	public boolean unfreezeDiamondAsset(long buyer, long sellerId, long auctionGoodsId, double diamondsBinNum,double diamondsNum) {
		// 总收入增加
		Map<String, Object> earn = new HashMap<String, Object>();
		earn.put("userId", sellerId);
		earn.put("diamondsNum", diamondsNum);
		int addUpdate = assetUsersAssetMapper.updateDiamondsEarn(earn);// +
		// 插入商品消费与赚取流水记录
		AssetUsersDiamondsLogs assetUsersDiamondsLogs = new AssetUsersDiamondsLogs();
		assetUsersDiamondsLogs.setDiamondsNum(BigDecimal.valueOf(diamondsNum));
		assetUsersDiamondsLogs.setEffectId(auctionGoodsId);
		assetUsersDiamondsLogs.setEventType(AssetConstant.EVENTTYPE_DAIMOND_AUCTION_BUY);
		assetUsersDiamondsLogs.setRecordType(AssetConstant.RECORDTYPE_2);
		assetUsersDiamondsLogs.setRemark("竞拍消费" + auctionGoodsId);
		assetUsersDiamondsLogs.setUserId(buyer);
		assetUsersDiamondsLogsMapper.insertDiamondsConsume(assetUsersDiamondsLogs);

		AssetUsersDiamondsLogs logs = new AssetUsersDiamondsLogs();
		logs.setDiamondsNum(BigDecimal.valueOf(diamondsNum));
		logs.setEffectId(auctionGoodsId);
		logs.setEventType(AssetConstant.EVENTTYPE_DAIMOND_AUCTION_EARN);
		logs.setRecordType(AssetConstant.RECORDTYPE_1);
		logs.setRemark("竞拍收入" + auctionGoodsId);
		logs.setUserId(sellerId);
		assetUsersDiamondsLogsMapper.insertDiamondsConsume(logs);

		// 竞拍解冻

		Map<String, Object> freeze = new HashMap<String, Object>();
		freeze.put("userId", buyer);
		freeze.put("diamondsNum", diamondsBinNum);
		assetUsersAssetMapper.updateEndUnfreeze(freeze);// -

		AuctionUsersDiamondsLogs auctionLog = new AuctionUsersDiamondsLogs();
		auctionLog.setDiamondsNum(BigDecimal.valueOf(diamondsBinNum));
		auctionLog.setEffectId(auctionGoodsId);
		auctionLog.setEventType(AssetConstant.EVENTTYPE_THAW);
		auctionLog.setRecordType(AssetConstant.RECORDTYPE_1);
		auctionLog.setRemark("竞拍成功,解冻");
		auctionLog.setUserId(buyer);
		logs.setCreateTime(new Date());
		auctionUsersDiamondsLogsMapper.insertDiamondsConsume(auctionLog);
		return true;

	}

	/**
	 * 
	 * @Title: addUserPowers
	 * @Description: TODO(用户动力值增加)
	 * @param @param
	 *            assetUsersPowerLogs
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */

	
	public boolean addUserPowers(AssetUsersPowerLogs assetUsersPowerLogs) {
		assetUsersPowerLogsMapper.insertUsersPowerLogs(assetUsersPowerLogs);
		assetUsersAssetMapper.updatePowerAdd(assetUsersPowerLogs);
		return true;
	}

	public AssetUsersPowerLogs getLogsLatest(Long userId) {
		return assetUsersPowerLogsMapper.getLogsLatest(userId);
	}
	/**
	 * 
	 * @Title: reduceUserPowers
	 * @Description: TODO(用户动力值減少)
	 * @param @param
	 *            assetUsersPowerLogs
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */

	
	public boolean reduceUserPowers(AssetUsersPowerLogs assetUsersPowerLogs) {
		int j = assetUsersAssetMapper.updatePowerReduce(assetUsersPowerLogs);
		if(j == 0) {
			return false;
		}
		int i = assetUsersPowerLogsMapper.insertUsersPowerLogs(assetUsersPowerLogs);

		return true;
	}
	/**
	 * 
	 * @Title: addFamiliesPowers
	 * @Description: TODO(俱乐部动力值增加)
	 * @param @param
	 *            assetFamiliesPowerLogs
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	
	public boolean addFamiliesPowers(AssetFamiliesPowerLogs assetFamiliesPowerLogs) {
		assetFamiliesPowerLogsMapper.insertFamiliesPowerLogs(assetFamiliesPowerLogs);
		assetFamiliesAssetMapper.updatePowerAdd(assetFamiliesPowerLogs);
		return true;
	}

	/**
	 * 
	 * @Title: getFamilyPowers
	 * @Description: TODO(获取俱乐部对战动力情况)
	 * @param @param
	 *            userId
	 * @param @param
	 *            familyId
	 * @param @param
	 *            fightFamilyId
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LiXING
	 */

	public Map<String, Object> getFamilyPowers(long userId, Map<String, Object> familiesInfo,
			Map<Object, Object> requestBodyParams) {
		Map<String, Object> data = new HashMap<>();
		long familyId = Long.valueOf(familiesInfo.get("familyId").toString());
		long familyUserCount = Long.valueOf(familiesInfo.get("familyUserCount").toString());
		long fightFamilyId = Long.valueOf(familiesInfo.get("fightFamilyId").toString());

		long todayContribution = 0L;
		long todayReceive = 0L;
		long myFamilyContribution = 0L;
		long fightFamilyContribution = 0L;
		long myFamilyReceive = 0L;
		long fightFamilyReceive = 0L;
		List<AssetUsersPowerLogs> powers = assetUsersPowerLogsMapper.getAllPowersByOne(userId);
		// 个人贡献、领取
		for (AssetUsersPowerLogs assetUsersPowerLogs : powers) {
			if (assetUsersPowerLogs.getEventType() == 3) {
				Long effectId = assetUsersPowerLogs.getEffectId();
				AssetFamiliesPowerLogs familiesPowerLogs = assetFamiliesPowerLogsMapper.getFamiliesPowerLogs(effectId);
				if (familiesPowerLogs.getFamilyId() == familyId || familiesPowerLogs.getFamilyId() == fightFamilyId) {
					todayReceive += assetUsersPowerLogs.getPowerNum();
				}
			} else if (assetUsersPowerLogs.getEventType() == 4) {
				todayContribution += assetUsersPowerLogs.getPowerNum();
			}
		}
//		todayContribution = todayContribution / 2;
		todayContribution = Math.round(todayContribution / 2D);

		List<AssetFamiliesPowerLogs> rtPowerList = assetFamiliesPowerLogsMapper.getFamilyPowers(familyId,
				fightFamilyId);
		List<AssetFamiliesPowerLogs> powerList = new ArrayList<>();
		List<Map<String, Object>> powerBalls = new ArrayList<>();
		// 过滤
		for (AssetFamiliesPowerLogs logs : rtPowerList) {

			if (logs.getFamilyId() == familyId) {
				powerList.add(logs);
			} else {
				String[] str = logs.getRelation().split("-");
				long selfFamilyId = Long.valueOf(String.valueOf(str[0]));
				long competitorFamilyId = Long.valueOf(String.valueOf(str[1]));
				long relationType = Long.valueOf(String.valueOf(str[2]));
				if (familyId == selfFamilyId || (familyId == competitorFamilyId && relationType == 2)
						|| (fightFamilyId == competitorFamilyId && relationType == 2)) {
					powerList.add(logs);
				}
			}

		}
		for (AssetFamiliesPowerLogs logs : powerList) {
			Map<String, Object> powerBall = new HashMap<>();
			String ballId = String.valueOf(logs.getId());
			powerBall.put("ballId", ballId);
			powerBall.put("totalScore", String.valueOf(logs.getRealNum()));

			// 计算本次可领能量
			long count = familyUserCount;// 总共可领取次数
			Integer realCount = logs.getCount();
			Long realNum = logs.getRealNum();// 当前实际总量
			Long thisScore = 0L;
			if (realCount != count - 1) {
				if (realNum >= 2) {
					thisScore = RandomUtils.nextLong(1L, (long) (realNum * 0.6));
				} else {
					thisScore = 1L;
				}
			} else {
				thisScore = realNum;
			}
			powerBall.put("thisScore", String.valueOf(thisScore));
			// 球的Id即是UserPowerLogs里的effectId，且用户eventType=3
			List<Long> ids = assetUsersPowerLogsMapper.getPowersByEffectId(Long.valueOf(ballId));
			String[] userIdsArray = {};
			if (ValidTools.isNotBlank(ids)) {
				userIdsArray = new String[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					userIdsArray[i] = ids.get(i).toString();
				}
			}
			powerBall.put("receivelist", userIdsArray);
			String createFamilyId = String.valueOf(logs.getFamilyId());
			powerBall.put("createFamilyId", createFamilyId);
			if (createFamilyId.equals(String.valueOf(familyId))) {
				powerBall.put("isMyFamily", "1");
			} else {
				powerBall.put("isMyFamily", "0");
			}
			powerBall.put("createTime", String.valueOf(DateTools.formatDateYMD(logs.getCreateTime())));
			powerBalls.add(powerBall);

			// 俱乐部贡献
			if (logs.getFamilyId() == familyId) {
				myFamilyContribution += logs.getPowerNum();
			} else {
				fightFamilyContribution += logs.getPowerNum();
			}
		}

		// myFamilyContribution =
		// assetFamiliesPowerLogsMapper.getContributionNum(familyId);
		// fightFamilyContribution =
		// assetFamiliesPowerLogsMapper.getContributionNum(fightFamilyId);
		myFamilyReceive = stealPowerMapper.getReceiveNum(familyId);
		fightFamilyReceive = stealPowerMapper.getReceiveNum(fightFamilyId);

		data.put("todayContribution", String.valueOf(todayContribution));
		data.put("todayReceive", String.valueOf(todayReceive));
		data.put("myFamilyId", String.valueOf(familyId));
		data.put("myFamilyContribution", String.valueOf(myFamilyContribution));
		data.put("myFamilyReceive", String.valueOf(myFamilyReceive));
		data.put("powerBalls", powerBalls);
		if(fightFamilyId!=0) {
			data.put("fightFamilyId", String.valueOf(fightFamilyId));
			data.put("fightFamilyContribution", String.valueOf(fightFamilyContribution));
			data.put("fightFamilyReceive", String.valueOf(fightFamilyReceive));
		}
		return data;
	}

	/**
	 * 
	 * @Title: stoleFamilyFightPowers
	 * @Description: TODO(俱乐部对战动力偷取)
	 * @param @param
	 *            ballId
	 * @param @param
	 *            powerNum
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             LiXing
	 */
	
	public void stoleFamilyFightPowers(long userId, Map<String, Object> familiesInfo, long ballId, long powerNum)
			throws Exception {
		long familyId = Long.valueOf(familiesInfo.get("familyId").toString());
		long fightFamilyId = Long.valueOf(familiesInfo.get("fightFamilyId").toString());

		// 修改个人相关数据
		AssetUsersPowerLogs assetUsersPowerLogs = new AssetUsersPowerLogs();
		assetUsersPowerLogs.setUserId(userId);
		assetUsersPowerLogs.setPowerNum(powerNum);
		assetUsersPowerLogs.setEffectId(ballId);
		assetUsersPowerLogs.setCreateTime(new Date());
		assetUsersPowerLogs.setEventType(3);
		assetUsersPowerLogs.setRecordType(1);
		assetUsersPowerLogs.setRemark(AssetConstant.UserPowerEventMap.get(3));
		this.addUserPowers(assetUsersPowerLogs);

		// 修改俱乐部相关数据
		AssetFamiliesPowerLogs assetFamiliesPowerLogs = assetFamiliesPowerLogsMapper.getFamiliesPowerLogs(ballId);
		long count = assetFamiliesPowerLogs.getCount() + 1;
		if (assetFamiliesPowerLogs.getFamilyId() == familyId) {
			assetFamiliesPowerLogsMapper.updateFamiliesPowerLogs(ballId, powerNum, count);
		} else {
			assetFamiliesAssetMapper.updateFamilyPowerAdd(familyId, powerNum);
			assetFamiliesPowerLogsMapper.updateFamiliesPowerLogs(ballId, powerNum, count);
			// 修改资产
			assetFamiliesAssetMapper.updateFamilyPowerMinus(fightFamilyId, powerNum);
		}

		// 插入日志
		StealPower steal = new StealPower();
		steal.setBallId(ballId);
		steal.setDaystamp(DateTools.formatDateD(new Date()));
		steal.setFamilyId(familyId);
		steal.setFightFamilyId(fightFamilyId);
		steal.setPowerNum((int) powerNum);
		steal.setUserId(userId);
		steal.setRemark("");
		stealPowerMapper.insertSteal(steal);
	}

	/**
	 * 
	 * @param fightFamilyId 
	 * @Title: getStoleFamilyFightPowers
	 * @Description: TODO(俱乐部动力偷取记录)
	 * @param @param
	 * 
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */

	
	public List<Map<String, String>> getStoleRecord(long familyId, long fightFamilyId) {
		List<Map<String, String>> result = new ArrayList<>();
		List<StealPower> recordList = stealPowerMapper.getStealPowerList(familyId,fightFamilyId);
		for (StealPower record : recordList) {
			Map<String, String> data = new HashMap<>();
			data.put("userId", record.getUserId().toString());
			data.put("powerNum", record.getPowerNum().toString());
			data.put("time", record.getDaystamp().substring(0, 5));
			result.add(data);
		}
		return result;
	}

	/**
	 * 
	 * @Title: getUserPowerByEffectId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            effectId
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	
	public String getUserPowerByEffectId(long effectId) {
		AssetUsersPowerLogs assetUsersPowerLogs = new AssetUsersPowerLogs();
		assetUsersPowerLogs.setEffectId(effectId);
		assetUsersPowerLogs.setEventType(AssetConstant.EVENTTYPE_POWER_TRIP);
		AssetUsersPowerLogs apl = assetUsersPowerLogsMapper.getUsersPowerLogsByEffectId(assetUsersPowerLogs);
		if (apl == null)
			return "0";
		return String.valueOf(apl.getPowerNum());
	}

	/**
	 * 
	 * @Title: getPersonHarvestYestoday
	 * @Description: TODO(个人昨日资产获取情况)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	
	public Map<String, Object> getPersonHarvestYestoday(long userId) {
		Map<String, Object> rtMap = new HashMap<String, Object>();

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("daystamp", DateTools.getCurDateYYYY_MM_DD());
		String powerTable = "userPower" + DateTools.getCurDateAddDay(-1).replaceAll("-", "");
		parmMap.put("powerTable", powerTable);
		AssetUsersAsset yestodayPower = assetUsersAssetMapper.getYestodayPower(parmMap);
		if (yestodayPower == null) {
			rtMap.put("powers", "0");
		} else {
			rtMap.put("powers", String.valueOf(yestodayPower.getPowerNum()));
		}

		AssetUsersDiamondsLogs diamonds = assetUsersDiamondsLogsMapper.getYestodayPersonPowerDiamonds(userId);
		if (diamonds == null) {
			rtMap.put("diamonds", "0");
		} else {
			rtMap.put("diamonds", String.valueOf(diamonds.getDiamondsNum().setScale(2, RoundingMode.HALF_EVEN)));
		}
		return rtMap;
	}

	
	public Map<String, Object> getFamilyHarvestYestoday(long userId, long familyId) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("familyId", familyId);
		FamilyGameAsset gameAsset = familyGameAssetMapper.getFamilyGameAssetByDay(familyId,
				DateTools.getCurDateAddDay(-1));
		if (gameAsset == null) {
			rtMap.put("pkDiamonds", "0");
		} else {
			AssetUsersDiamondsLogs diamonds = assetUsersDiamondsLogsMapper.getYestodayPkDiamonds(userId,
					gameAsset.getId());
			if (diamonds != null) {
				rtMap.put("pkDiamonds", String.valueOf(diamonds.getDiamondsNum().setScale(2, RoundingMode.HALF_EVEN)));
			} else {
				rtMap.put("pkDiamonds", "0");
			}

		}

		FamilySeasonAsset familySeasonAsset = familySeasonAssetMapper.getFamilySeasonAssetByDay(familyId,
				DateTools.getCurDateAddDay(-1));
		if (familySeasonAsset == null) {
			rtMap.put("seasonDiamonds", "0");
		} else {
			AssetUsersDiamondsLogs diamonds = assetUsersDiamondsLogsMapper.getYestodaySeasonDiamonds(userId,
					familySeasonAsset.getId());
			if (diamonds != null) {
				rtMap.put("seasonDiamonds",
						String.valueOf(diamonds.getDiamondsNum().setScale(2, RoundingMode.HALF_EVEN)));
			} else {
				rtMap.put("seasonDiamonds", "0");
			}
		}
		return rtMap;
	}
	
	public List<Map<String, Object>> getYestodayHarvestV2(long userId) {
		List<Map<String, Object>> rtList=new ArrayList<Map<String, Object>>();

		Map<String, Object> rtMap1 = new HashMap<String, Object>();
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("daystamp", DateTools.getCurDateYYYY_MM_DD());
		String powerTable = "userPower" + DateTools.getCurDateAddDay(-1).replaceAll("-", "");
		parmMap.put("powerTable", powerTable);
		AssetUsersAsset yestodayPower = assetUsersAssetMapper.getYestodayPower(parmMap);
		rtMap1.put("assetName", "个人获得");
		rtMap1.put("assetType", "1");
		if (yestodayPower == null) {
			rtMap1.put("assetNum", "0");
			
		} else {
			rtMap1.put("assetNum", String.valueOf(yestodayPower.getPowerNum()));
			//rtMap2.put("assetNum", String.valueOf(yestodayPower.getPkPower()));
		}
		rtList.add(rtMap1);

		String sign=SignUtils.encryptHMAC(String.valueOf(userId));
		String familyIds=chezuAppService.getFamiliesByUserId(userId, sign);
		if(familyIds!=null&&familyIds.length()>0) {
				String []ids=familyIds.split(",");
				for (int i = 0; i < 2; i++) {
					long fId = Long.valueOf(ids[i]);
					if (fId != 0) {
						Map<String, Object> rtMap2 = new HashMap<String, Object>();
						rtMap2.put("assetName", "【"+chezuAppService.getFamilyById(fId,"sign").get("familyName").toString()+"】俱乐部奖励");
						rtMap2.put("assetType", "1");
						FamilyGameAsset gameAsset = familyGameAssetMapper.getFamilyGameAssetByDay(fId,
								DateTools.getCurDateAddDay(-1));
						if(gameAsset==null) {
							rtMap2.put("assetNum", "0");
						}else {
							AssetUsersPowerLogs log=new AssetUsersPowerLogs();
							log.setEffectId(gameAsset.getId());
							log.setUserId(userId);
							log.setEventType(AssetConstant.EVENTTYPE_POWER_GAMEEND_USER);
							AssetUsersPowerLogs rtLog=assetUsersPowerLogsMapper.getUsersPowerLogsByUserEffectId(log);
							if(rtLog==null) {
								rtMap2.put("assetNum", "0");
							}else {
								rtMap2.put("assetNum", String.valueOf(rtLog.getPowerNum()));
							}
						}
						rtList.add(rtMap2);
					}
				}
		}

		Map<String, Object> rtMap3 = new HashMap<String, Object>();
		rtMap3.put("assetName", "动力产出");
		rtMap3.put("assetType", "2");
		AssetUsersDiamondsLogs diamonds = assetUsersDiamondsLogsMapper.getYestodayPersonPowerDiamonds(userId);
		if (diamonds == null) {
			rtMap3.put("assetNum", "0");
		} else {
			rtMap3.put("assetNum", String.valueOf(diamonds.getDiamondsNum().setScale(2, RoundingMode.HALF_EVEN)));
		}
		rtList.add(rtMap3);

		return rtList;
	}
	
	
	public Map<String, Object> getGlobalYestoday() {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		String powerTable = "userPower" + DateTools.getCurDateAddDay(-1).replaceAll("-", "");
		long power = assetUsersAssetMapper.getAllYestodayAppPowers(powerTable);
		rtMap.put("globalPower", String.valueOf(power));
		BigDecimal diamonds = assetUsersAssetMapper.getAllAppDiamonds();
		rtMap.put("globalDiamonds", String.valueOf(diamonds.setScale(0, RoundingMode.HALF_UP).intValue()));
		return rtMap;
	}

	/**
	 * 
	 * @Title: initFamily
	 * @Description: TODO(创建俱乐部时候调用)
	 * @param @param
	 *            familyId
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */

	
	public boolean initFamily(long familyId) {
		AssetFamiliesAsset assetFamiliesAsset = new AssetFamiliesAsset();
		assetFamiliesAsset.setFamilyId(familyId);
		assetFamiliesAssetMapper.initFamily(assetFamiliesAsset);
		return true;
	}

	/**
	 * 
	 * @Title: initUser
	 * @Description: TODO(创建用户初始化)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	
	public boolean initUser(long userId) {
		AssetUsersAsset assetUsersAsset = new AssetUsersAsset();
		assetUsersAsset.setUserId(userId);
		assetUsersAssetMapper.initUser(assetUsersAsset);
		return true;
	}

	
	public boolean userPowersSnapShot(String tableName) {
		assetUsersAssetMapper.userPowersSnapShot(tableName);
		return true;
	}

	
	public boolean powerClear() {
		assetUsersAssetMapper.clearPowers(null);
		assetFamiliesAssetMapper.clearPower(null);
		return true;
	}

	/**
	 * 
	 * @Title: getDaySignInLog
	 * @Description: TODO(签到获取动力)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public int getDaySignInLog(Long userId) {
		int c = assetUsersPowerLogsMapper.getSignInLogByUserId(userId);
		if (c > 0) {
			return 0;
		} else {
			AssetUsersPowerLogs assetUsersPowerLogs = new AssetUsersPowerLogs();
			assetUsersPowerLogs.setEffectId(0l);
			assetUsersPowerLogs.setEventType(AssetConstant.EVENTTYPE_POWER_SIGNIN);
			assetUsersPowerLogs.setPowerNum(5l);
			assetUsersPowerLogs.setRecordType(AssetConstant.RECORDTYPE_1);
			assetUsersPowerLogs.setRemark(userId + "签到获取");
			assetUsersPowerLogs.setUserId(userId);
			assetUsersPowerLogsMapper.insertUsersPowerLogs(assetUsersPowerLogs);
			return assetUsersAssetMapper.updatePowerAdd(assetUsersPowerLogs);
		}
	}

	/**
	 * 
	 * @Title: billBoard
	 * @Description: TODO(用户分数、钻石排名)
	 * @param @param
	 *            billBoardType
	 * @param @return
	 *            参数
	 * @return List<Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	public List<Map<String, String>> billBoard(String billBoardType, long userId) {
		List<Map<String, String>> billBoard = new ArrayList<>();
		List<AssetUsersAsset> users = new ArrayList<>();
//		AssetUsersAsset usersAsset = assetUsersAssetMapper.getUserAssetByUserId(userId);

		if ("2".equals(billBoardType)) {
			// 按照今日动力排名
			users = assetUsersAssetMapper.billBoardByPower();
		} else {
			// 按照钻石数量排名
			users = assetUsersAssetMapper.billBoardByDiamond();
		}
//		users.add(usersAsset);
		for (int i = 0; i < users.size(); i++) {
			Map<String, String> bill = new HashMap<>();
			if ("2".equals(billBoardType)) {
				// 按照今日动力排名
//				bill.put("rank", String.valueOf(assetUsersAssetMapper.queryPowersUserOrderByPowerNum(users.get(i).getPowerNum())));
				bill.put("rank", String.valueOf(i+1));
			} else {
				// 按照钻石数量排名
//				bill.put("rank", String.valueOf(assetUsersAssetMapper.queryDiamondsUserOrderByDiamondsNum(users.get(i).getDiamondsNum())));
				bill.put("rank", String.valueOf(i+1));
			}
			bill.put("userId", users.get(i).getUserId().toString());
			if ("2".equals(billBoardType)) {
				bill.put("gradeOrNum", users.get(i).getPowerNum().toString());
			} else {
				bill.put("gradeOrNum", users.get(i).getDiamondsNum().setScale(2, RoundingMode.HALF_UP).toString());
			}
			billBoard.add(bill);
		}
		return billBoard;
	}

	// 钻石、动力 数量及排名信息
	public Map<String, String> getCurOrderAndNum(long userId) {
		Map<String, String> map = new HashMap<>();
		AssetUsersAsset usersAsset = assetUsersAssetMapper.getUserAssetByUserId(userId);
		map.put("diamondsNum", usersAsset.getDiamondsNum().setScale(2, RoundingMode.HALF_UP).toString());
		map.put("powersNum", usersAsset.getPowerNum().toString());
		map.put("diamondsNo", String.valueOf(assetUsersAssetMapper.getDiamondsCurOrder(userId)));
		map.put("powersNo", String.valueOf(assetUsersAssetMapper.getPowersCurOrder(userId)));

		// 当前全网钻石总量（每日更新）
		map.put("allAppDiamonds",
				assetUsersAssetMapper.getAllAppDiamonds().setScale(0, RoundingMode.HALF_UP).toString());
		// 钻石介绍
		map.put("diamondDesc", IntroduceConstant.DIAMONDINTRODUCE);

		map.put("allCurPowers", String.valueOf(assetUsersAssetMapper.getAllAppPowers()));
		// 动力介绍
		map.put("powerDesc", IntroduceConstant.POWERINTRODUCE);

		// 运营账号钻石
		map.put("pltfDiamondsNum", assetUsersAssetMapper.getUserAssetByUserId(1L).getDiamondsNum().setScale(2, RoundingMode.HALF_EVEN).toString());
		return map;
	}

	// 钻石收支记录主信息
	public Map<String, String> diamondsInfo(long userId) {
		Map<String, String> map = new HashMap<>();
		AssetUsersAsset usersAsset = assetUsersAssetMapper.getUserAssetByUserId(userId);
		map.put("diamondsNum", usersAsset.getDiamondsNum().setScale(2, RoundingMode.HALF_UP).toString());
		// 当前全网钻石总量（每日更新）
		map.put("allAppDiamonds",
				assetUsersAssetMapper.getAllAppDiamonds().setScale(0, RoundingMode.HALF_UP).toString());
		// 钻石介绍
		map.put("diamondDesc", IntroduceConstant.DIAMONDINTRODUCE);
		return map;
	}

	// 动力领取记录主信息
	public Map<String, String> powersInfo(long userId) {
		Map<String, String> map = new HashMap<>();
		AssetUsersAsset usersAsset = assetUsersAssetMapper.getUserAssetByUserId(userId);
		map.put("powersNum", usersAsset.getPowerNum().toString());
		// 当前全网动力总量（每小时更新）
		map.put("allCurPowers", String.valueOf(assetUsersAssetMapper.getAllAppPowers()));
		// 钻石介绍
		map.put("powerDesc", IntroduceConstant.POWERINTRODUCE);
		return map;
	}

	// 查询是否有新动力可领
	public String queryHaveNewPower(long userId, Map<String, Object> familiesInfo) {
		String s = "0";
		long familyId = Long.valueOf(familiesInfo.get("familyId").toString());
		long fightFamilyId = Long.valueOf(familiesInfo.get("fightFamilyId").toString());
		// 双方俱乐部产生的所有动力球
		List<AssetFamiliesPowerLogs> rtPowerList = assetFamiliesPowerLogsMapper.getFamilyPowers(familyId,fightFamilyId);
		List<AssetFamiliesPowerLogs> powerList = new ArrayList<>();
		// 过滤
		for (AssetFamiliesPowerLogs logs : rtPowerList) {
			if (logs.getRelation().equals("0") && logs.getFamilyId() == familyId) {
				powerList.add(logs);
			} else {
				String[] str = logs.getRelation().split("-");
				long selfFamilyId = Long.valueOf(String.valueOf(str[0]));
				long competitorFamilyId = Long.valueOf(String.valueOf(str[1]));
				long relationType = Long.valueOf(String.valueOf(str[2]));
				if (familyId == selfFamilyId || (familyId == competitorFamilyId && relationType == 2)
						|| (fightFamilyId == competitorFamilyId && relationType == 2)) {
					powerList.add(logs);
				}
			}
		}
		
		// 当前个人已经领取过的动力球
		List<StealPower> listOfCurUser = stealPowerMapper.getStealPowerListByUserId(userId);
		List<Long> flagList = new ArrayList<>();
		for (StealPower stealPower : listOfCurUser) {
			flagList.add(stealPower.getBallId());
		}
		for (AssetFamiliesPowerLogs assetFamiliesPowerLogs : powerList) {
			if (!flagList.contains(assetFamiliesPowerLogs.getId())) {
				s = "1";
				break;
			}
		}
		return s;
	}

	/**
	 * 
	        * @Title: queryCountOfSteal
	        * @Description: TODO(是否能被偷)
	        * @param @param userId
	        * @param @return 参数
	        * @return int 返回类型
	        * @throws
	        * @author LiXing
	 */
	public int queryCountOfSteal(long userId) {
		// TODO Auto-generated method stub
		return assetUsersPowerLogsMapper.queryCountOfSteal(userId);
	}

	/**
	 * 
	        * @param requestBodyParams 
	 * 		  @Title: pltfDiamondsDetail
	        * @Description: TODO(平台运营账号钻石详情)
	        * @param @return 参数
	        * @return Map<String,Object> 返回类型
	        * @throws
	        * @author LiXing
	 */
	public Map<String, Object> pltfDiamondsDetail(Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<>();
		// 运营账号钻石
		rtMap.put("pltfDiamondsNum", assetUsersAssetMapper.getUserAssetByUserId(1L).getDiamondsNum().setScale(2, RoundingMode.HALF_EVEN).toString());
		// list
		List<Map<String, String>> list = this.getIndexDiamonds(1L, requestBodyParams);
		rtMap.put("list", list);

		return rtMap;
	}
	
	public int queryHadGetBonus(long userId) {
		// TODO Auto-generated method stub
		return assetUsersPowerLogsMapper.queryHadGetBonus(userId);
	}
	
	public int queryReceiveDayMissionBox(long userId) {
		// TODO Auto-generated method stub
		return assetUsersPowerLogsMapper.queryReceiveDayMissionBox(userId);
	}
	
	public int queryReceiveActMissionBox(long userId) {
		// TODO Auto-generated method stub
		return assetUsersPowerLogsMapper.queryReceiveActMissionBox(userId);
	}
	
	public long queryMaxActPowerByTime(String daystamp) {
		Long powerNum = assetUsersPowerLogsMapper.queryMaxActPowerByTime(daystamp);
		if (powerNum == null) {
			powerNum = 0L;
		}
		return powerNum;
	}
	
	public long queryMaxActPowerByTimeAndUserId(String daystamp,long userId) {
		Long powerNum = assetUsersPowerLogsMapper.queryMaxActPowerByTimeAndUserId(daystamp,userId);
		if (powerNum == null) {
			powerNum = 0L;
		}
		return powerNum;
	}

	public List<Map<String, Object>> queryPowerStatistics(Integer day){
		return assetUsersPowerLogsMapper.queryPowerStatistics(day);
	}
}