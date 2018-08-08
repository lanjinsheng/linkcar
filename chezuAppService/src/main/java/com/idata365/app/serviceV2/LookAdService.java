package com.idata365.app.serviceV2;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.v2.UserLookAdLogs;
import com.idata365.app.mapper.UserLookAdMapper;
import com.idata365.app.remote.ChezuAssetService;
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

	/**
	 * 
        * @Title: countOfOddLookAd
        * @Description: TODO()
        * @param @param userId
        * @param @return 参数
        * @return int 返回类型
        * @throws
        * @author LiXing
	 */
	public int countOfOddLookAd(long userId) {
		String daystamp = DateTools.getYYYY_MM_DD();
		int count = this.userLookAdMapper.getTodayCount(userId, daystamp);
		if (count >= 10) {
			return 0;
		} else {
			return 10 - count;
		}
	}
	
	public int getTodayCountAllType(long userId) {
		String daystamp = DateTools.getYYYY_MM_DD();
		return  this.userLookAdMapper.getTodayCountAllType(userId, daystamp);
	}

	/**
	 * 
        * @param eventType 
	 * @param adPassId 
	 * @Title: receiveLookAdPower
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param @param userId
        * @param @return 参数
        * @return String 返回类型
        * @throws
        * @author LiXing
	 */
	public Map<String, Object> receiveLookAdPower(long userId, int adSign, long adPassId) {
		Long powerNum = 0L;
		int valid = 0;
		Map<String, Object> rtMap = new HashMap<String, Object>();
		int count = this.countOfOddLookAd(userId);
		if (count == 0) {
			throw new RuntimeException("次数已达到限制");
		}

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
//			if (info == null || (new Date().getTime() - info.getCreateTime().getTime() > 1000 * 5)) {
//				powerNum = (long) RandUtils.generateRand(30, 200);
//				valid = 1;
//			}
			if (info == null || info.getAdPassId() != adPassId) {
				powerNum = 30L;
				valid = 1;
			}
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
		if(powerNum>=0) {
			boolean b = chezuAssetService.getMissionPrize(userId, powerNum.intValue(), 99, sign);// 观看广告任务ID为99
			if (b == false) {
				throw new RuntimeException("系统异常领取失败");
			}
		}
		rtMap.put("powerPrizeNum", String.valueOf(powerNum));
		rtMap.put("diamondPrizeNum", "0");
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
		logs.setRemark("");
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
				valid = 0;
				powerNum = 0L;
			} else if (loadFlag == 1) {
				valid = 1;
				powerNum = (long) RandUtils.generateRand(11, 40);
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
			boolean b = chezuAssetService.getMissionPrize(userId, powerNum.intValue(), 98, sign);// 完成活动任务ID为98
			if (b == false) {
				throw new RuntimeException("系统异常领取失败");
			}
		}
		
		rtMap.put("powerPrizeNum", String.valueOf(powerNum));
		rtMap.put("diamondPrizeNum", "0");
		return rtMap;
	}

}
