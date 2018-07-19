package com.idata365.app.serviceV2;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.UsersAccount;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.BaseService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.ScoreService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.DateTools;

@Service
public class UserHomeService extends BaseService<UserHomeService>{

	protected static final Logger LOGGER = LoggerFactory.getLogger(UserHomeService.class);
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	private FamilyService familyService;
	@Autowired
	private InteractService interactService;
	@Autowired
	private CarService carService;
	
	/**
	 * 
        * @Title: queryUserHome
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param @param userId
        * @param @param ownerId
        * @param @return 参数
        * @return Map<String,Object> 返回类型
        * @throws
        * @author LiXing
	 */
	@Transactional
	public Map<String, Object> queryUserHome(long userId, long ownerId, String imgBasePath) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		UsersAccount account = userInfoService.getUsersAccount(userId);
		// 用户当前动力及分数
		double score = scoreService.getHighScore(String.valueOf(userId));
		String powerNum = chezuAssetService.getUsersAssetMap(String.valueOf(userId), "").get(userId);
		rtMap.put("nickName", account.getNickName());
		rtMap.put("score", String.valueOf(score));
		rtMap.put("imgUrl", imgBasePath + account.getImgUrl());
		rtMap.put("powerNum", powerNum);
		// 家族信息
		Map<String, String> familyInfo = familyService.queryFamilyByUserId(userId);
		rtMap.put("createFamilyInfo", familyInfo.get("createFamilyInfo"));
		rtMap.put("joinFamilyInfo", familyInfo.get("joinFamilyInfo"));
		// 车库名
		if (ownerId == userId) {
			rtMap.put("title", "我的车库");
		} else {
			rtMap.put("title", account.getNickName() + "的车库");
		}

		Map<String, Object> car = carService.getUserCar(userId);
		// 车名
		rtMap.put("carName", car == null ? "链车蓝跑1代" : car.get("carName").toString());
		// 车图片
		rtMap.put("carImgUrl",
				car == null ? "http://product-h5.idata365.com/appImgs/paoche1.png" : car.get("carUrl").toString());
		// 点赞次数
		rtMap.put("likeCount", String.valueOf(interactService.queryLikeCount(userId)));
		// 按钮展示
		Map<String, String> map = this.familyService.iconStatus(ownerId, userId);
		rtMap.put("isCanInvite", map.get("isCanInvite"));
		// rtMap.put("hadRemove", map.get("isCanRemove"));
		rtMap.put("isCanInteract", map.get("hadInteractIcon"));
		// int canStealPower = interactService.isCanStealPower(userId);
		int canPayTicket = interactService.isCanPayTicket(userId);
		int canStealPower = interactService.carPoolStealStatus(ownerId, userId);
		rtMap.put("isCanStealPower", String.valueOf(canStealPower));
		rtMap.put("isCanPayTicket", String.valueOf(canPayTicket));

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdA", ownerId);
		param.put("userIdB", userId);
		param.put("daystamp", DateTools.getYYYY_MM_DD());
		if (interactService.hadComeOn(param) > 0) {
			rtMap.put("isLiked", "1");
		} else {
			rtMap.put("isLiked", "0");
		}
		// 动力加成操作
		Map<String, String> powerUpInfo = this.carService.getPowerUpInfo(userId,
				Integer.valueOf(car.get("carId").toString()));
		rtMap.put("powerUpPercent", powerUpInfo.get("powerUpPercent"));

		return rtMap;
	}
}
