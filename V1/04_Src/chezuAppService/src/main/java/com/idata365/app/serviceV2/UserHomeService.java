package com.idata365.app.serviceV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.DicComponentConstant;
import com.idata365.app.entity.DicCar;
import com.idata365.app.entity.UserCar;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.v2.DicComponent;
import com.idata365.app.mapper.DicCarMapper;
import com.idata365.app.mapper.DicComponentMapper;
import com.idata365.app.mapper.UserCarMapper;
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
	@Autowired
	private DicComponentMapper dicComponentMapper;
	@Autowired
	private DicCarMapper dicCarMapper;
	@Autowired
	private UserCarMapper userCarMapper;
	
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

		UserCar userCurCar = userCarMapper.getUserCurCar(userId);
		DicCar dicCar = dicCarMapper.getCarByCarId(userCurCar.getCarId());
		rtMap.put("userCarId", userCurCar.getId().toString());
		// 车名
		rtMap.put("carName", dicCar.getCarName().toString());
		// 车图片
		rtMap.put("carImgUrl", dicCar.getCarUrl().toString());
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
		Map<String, String> powerUpInfo = this.carService.getPowerUpInfo(userId, userCurCar.getCarId(),userCurCar.getId());
		rtMap.put("powerUpPercent", powerUpInfo.get("powerUpPercent"));

		return rtMap;
	}

	/**
	 * 
        * @Title: userComponentBoxUp
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param @param userId
        * @param @param carId
        * @param @return 参数
        * @return Map<String,Object> 返回类型
        * @throws
        * @author LiXing
	 */
	public Map<String, Object> getUserCarInfo(long userId, Long userCarId) {
		   
		Integer carId = this.userCarMapper.getCarInfo(userCarId).getCarId();
		Map<String, Object> rtMap = new HashMap<String, Object>();
		List<Map<String, String>> componentList = new ArrayList<>();
		// 动力加成操作
		Map<String, String> powerUpInfo = this.carService.getPowerUpInfo(userId,carId,userCarId);
		rtMap.put("powerUpInfo", powerUpInfo);
		List<Map<String, Object>> list = dicComponentMapper.getCurComponentByUserIdCarId(userCarId);
		
		for (int i = 1; i < 6; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("userComponentId", "");
			map.put("componentName", "");
			map.put("quality", "");
			map.put("imgUrl", "");
			map.put("componentDesc", "");
			map.put("componentAttribute", "");
			map.put("componentLoss", "");
			map.put("componentAttribute", "");
			map.put("componentLoss", "");
			map.put("componentDesc", "");
			if (list != null && list.size() != 0) {
				for (int j = 0; j < list.size(); j++) {
					if (Integer.valueOf(list.get(j).get("componentType").toString()) == i) {
						map.put("userComponentId", String.valueOf(list.get(j).get("componentId")));
						map.put("componentName", String.valueOf(list.get(j).get("componentValue")));
						map.put("quality", String.valueOf(list.get(j).get("quality")));
						map.put("imgUrl", String.valueOf(list.get(j).get("componentUrl")));
						map.put("componentDesc", String.valueOf(list.get(j).get("componentDesc")));
						Double powerAddition = Double.valueOf(String.valueOf(list.get(j).get("powerAddition")));
						Integer travelNum = Integer.valueOf(String.valueOf(list.get(j).get("travelNum")));
						map.put("componentAttribute", "动力加成" + (int) (powerAddition * 100) + "%");
						map.put("componentLoss", String.valueOf(travelNum) + "次行程");
						map.put("componentRemainingLossCount",
								String.valueOf(list.get(j).get("leftTravelNum")) + "次行程");
					}
				}
			}
			componentList.add(map);
		}
		
		rtMap.put("componentList", componentList);
		DicCar car = dicCarMapper.getCarByCarId(carId);
		rtMap.put("carUrl", car.getCarUrl());
		rtMap.put("carName", car.getCarName());
		return rtMap;
	}
}
