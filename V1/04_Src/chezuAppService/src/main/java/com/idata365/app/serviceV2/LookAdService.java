package com.idata365.app.serviceV2;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.v2.UserLookAdLogs;
import com.idata365.app.mapper.UserLookAdMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.remote.ChezuImService;
import com.idata365.app.service.BaseService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.RandUtils;
import com.idata365.app.util.SignUtils;

@Service
@Transactional
public class LookAdService extends BaseService<LookAdService> {
	protected static final Logger LOG = LoggerFactory.getLogger(LookAdService.class);

	@Autowired
	private UserLookAdMapper userLookAdMapper;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	private ChezuImService chezuImService;
	@Autowired
	private UsersAccountMapper usersAccountMapper;

	/**
	 * 
        * @param adId 
	 * @Title: countOfOddLookAd
        * @Description: TODO()
        * @param @param userId
        * @param @return 参数
        * @return int 返回类型
        * @throws
        * @author LiXing
	 */
	public Map<String, Object> countOfOddLookAd(long userId, long adId) {
		Map<String, Object> rtMap = new HashMap<>();
		String daystamp = DateTools.getYYYY_MM_DD();
		
		String isAdEnd = "0";
		String rtAdId = String.valueOf(adId);
		String powerPrizeNum = "0";
		if (adId == 0) {
			// 第一次调用
			UserLookAdLogs info = this.userLookAdMapper.getUserLastLookInfo(userId);
			if (info != null && info.getHadGet() == 0) {
				isAdEnd = "1";
				rtAdId = String.valueOf(info.getAdPassId());
			} else if (info != null && info.getHadGet() == 1) {
				rtAdId = String.valueOf(info.getAdPassId() + 1);
				isAdEnd = "0";
			} else {
				rtAdId = "1";
				isAdEnd = "0";
			}
		}else {
			//
			UserLookAdLogs info = this.userLookAdMapper.getUserLastLookInfo(userId);
			if(info!=null&&info.getAdPassId()==adId) {
				int i = this.userLookAdMapper.updateHadGet(userId, adId, daystamp);
				rtAdId = String.valueOf(info.getAdPassId()+1);
				if(i>0) {
					chezuAssetService.getMissionPrize(userId, 30, 99, "");// 观看广告任务ID为199
					powerPrizeNum = "30";
					isAdEnd = "0";
					String dd = DateTools.getYYYY_MM_DD();
					int cc = this.userLookAdMapper.getTodayCount(userId, dd);
					if(cc>=10) {
						chezuImService.lookedAllAd(usersAccountMapper.findAccountById(userId).getNickName(), String.valueOf(userId), "");
					}
				}
			}
		}
		int count = this.userLookAdMapper.getTodayCount(userId, daystamp);
		if (count >= 10) {
			count = 0;
		} else {
			count = 10 - count;
		}
		rtMap.put("oddCount", String.valueOf(count));
		if(count == 0) {
			rtAdId = "0";
		}
		
		rtMap.put("adId", rtAdId);
		rtMap.put("isAdEnd", isAdEnd);
		rtMap.put("powerPrizeNum", String.valueOf(powerPrizeNum));
		rtMap.put("diamondPrizeNum", "0");
		return rtMap;
	}
	
	public int getTodayCountAllType(long userId) {
		String daystamp = DateTools.getYYYY_MM_DD();
		// 广告次数
		int count1 = this.userLookAdMapper.getTodayCount(userId, daystamp);
		// 互动任务次数
		int count2 = 0;
		List<String> list = this.userLookAdMapper.queryTodayLoadFlag(userId);
		if (list != null && list.size() != 0) {
			for (String s : list) {
				count2 += Integer.valueOf(s);
			}
		}
		int count3 = this.userLookAdMapper.countOfLoadFlagZero(userId);
		count3 = count3 > 3 ? 3 : count3;
		count2 += count3;
		
		int count = count1 + count2;
		return count > 14 ? 14 : count;
	}

	public Map<String, Object> adCallBack(long userId, int adSign, long adPassId) {
		Long powerNum = 0L;
		int valid = 0;
		Map<String, Object> rtMap = new HashMap<String, Object>();

		UserLookAdLogs logs = new UserLookAdLogs();
		logs.setUserId(userId);
		logs.setCreateTime(new Date());
		logs.setDaystamp(DateTools.getYYYY_MM_DD());
		logs.setRemark("");
		logs.setDiamondNum(BigDecimal.valueOf(0));
		logs.setAdPassId(adPassId);
		logs.setAdSign(adSign);
		logs.setType(1);
		if (adSign != 0) {
			UserLookAdLogs info = this.userLookAdMapper.getUserLastLookInfo(userId);
			if (info == null || info.getAdPassId() != adPassId) {
				powerNum = 30L;
				valid = 1;
			}
		}
		logs.setPowerNum(powerNum);
		logs.setValid(valid);
		logs.setHadGet(0);
		this.userLookAdMapper.insertLogs(logs);
		
		return rtMap;
	}

	public Map<String, Object> interactAdCallBack(long userId, int loadFlag) {
		Long powerNum = 0L;
		int valid = 0;
		Map<String, Object> rtMap = new HashMap<String, Object>();

		UserLookAdLogs logs = new UserLookAdLogs();
		logs.setUserId(userId);
		logs.setCreateTime(new Date());
		logs.setDaystamp(DateTools.getYYYY_MM_DD());
		logs.setRemark(String.valueOf(loadFlag));
		logs.setDiamondNum(BigDecimal.valueOf(0));
		logs.setAdPassId(999L);
		logs.setAdSign(1);
		logs.setType(2);
		
		Long allPowerNum = this.userLookAdMapper.getAllPowerNumOfType2(userId);
		if (allPowerNum >= 300) {
			powerNum = 0L;
			valid = 0;
		} else {
			if (loadFlag == 0) {
				int count = this.userLookAdMapper.countOfLoadFlagZero(userId);
				if (count < 3) {
					valid = 1;
					powerNum = 10L;
				}else {
					valid = 0;
					powerNum = 0L;
				}
			} else if (loadFlag == 1) {
				valid = 1;
				powerNum = (long) RandUtils.generateRand(10, 40);
			} else {
				valid = 1;
				powerNum = (long) RandUtils.generateRand((loadFlag - 1) * 40,loadFlag * 40);// 30 60 90 120 150 160
			}
		}
		if(powerNum>300) {
			powerNum=300L;
			LOG.error("POWERNUM重置为300");
		}
		logs.setPowerNum(powerNum);
		logs.setValid(valid);
		int i = this.userLookAdMapper.insertLogs(logs);

		if (i <= 0) {
			LOG.error("插入Logs状态失败：==" + userId + "==" + userId);
			throw new RuntimeException("系统异常领取失败");
		}

		String paramSign = userId + String.valueOf(powerNum);
		String sign = SignUtils.encryptHMAC(paramSign);
		if(powerNum>0L) {
			boolean b = chezuAssetService.getMissionPrize(userId, powerNum.intValue(), 98, sign);// 完成活动任务ID为198
			if (b == false) {
				throw new RuntimeException("系统异常领取失败");
			}
			long yesterday = chezuAssetService.queryMaxActPowerByTime(DateTools.getAddMinuteDateTime(DateTools.getYYYYMMDD(), -24, "yyyyMMdd"), "");
			long now = chezuAssetService.queryMaxActPowerByTimeAndUserId(DateTools.getYYYYMMDD(),userId, "");
			if(now>=yesterday) {
				chezuImService.doingAllActMission(usersAccountMapper.findAccountById(userId).getNickName(),now, String.valueOf(userId), "");
			}
		}
		
		rtMap.put("powerPrizeNum", String.valueOf(powerNum));
		rtMap.put("diamondPrizeNum", "0");
		return rtMap;
	}

}
