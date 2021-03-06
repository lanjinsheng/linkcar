package com.idata365.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.constant.LotteryConstant;
import com.idata365.app.constant.LotteryLogConstant;
import com.idata365.app.constant.MessageTypeConstant;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryLogInfoParamBean;
import com.idata365.app.entity.LotteryMigrateInfoAllResultBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgParamBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgResultBean;
import com.idata365.app.entity.LotteryResultBean;
import com.idata365.app.entity.LotteryResultUser;
import com.idata365.app.entity.LotteryUser;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.SignatureDayLogBean;
import com.idata365.app.entity.UserFamilyRelationBean;
import com.idata365.app.entity.UserTravelLottery;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.enums.AchieveEnum;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.LotteryMapper;
import com.idata365.app.mapper.LotteryMigrateInfoMsgMapper;
import com.idata365.app.mapper.SignatureDayLogMapper;
import com.idata365.app.mapper.UserTravelLotteryMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.service.common.AchieveCommService;
import com.idata365.app.util.AdBeanUtils;
import com.idata365.app.util.PhoneUtils;
import com.idata365.app.util.RandUtils;

@Service
public class LotteryService extends BaseService<LotteryService>
{
	@Autowired
	private LotteryMapper lotteryMapper;
	
	@Autowired
	private LotteryMigrateInfoMsgMapper lotteryMigrateInfoMsgMapper;
	
	@Autowired
	private SignatureDayLogMapper signatureDayLogMapper;
	
	@Autowired
	UserTravelLotteryMapper  userTravelLotteryMapper;
	
	@Autowired
	private AchieveCommService achieveCommService;
	
	@Autowired
	private FamilyMapper familyMapper;
	@Autowired
	MessageService messageService;
	@Autowired
	UsersAccountMapper usersAccountMapper;
	
	/**
	 * 道具列表
	 * @param bean
	 * @return
	 */
	public List<LotteryResultBean> listLottery(LotteryBean bean)
	{
		List<LotteryBean> resultList = this.lotteryMapper.query(bean);
		if (CollectionUtils.isEmpty(resultList))
			return null;
		
		List<LotteryResultBean> list = new ArrayList<>();
		for (LotteryBean tempBean : resultList)
		{
			if (0 == tempBean.getAwardCount())
			{
				continue;
			}
			LotteryResultBean tempResultBean = new LotteryResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			
			int awardId = tempBean.getAwardId();
			switch(awardId)
			{
			case LotteryConstant.SHACHE_LOTTERY:
				tempResultBean.setAwardDesc("可抵消1次急刹车");
				break;
			case LotteryConstant.CHELUNTAI_LOTTERY:
				tempResultBean.setAwardDesc("可抵消1次急转弯");
				break;
			case LotteryConstant.FADONGJI_LOTTERY:
				tempResultBean.setAwardDesc("可抵消1次最高时速驾驶");
				break;
			case LotteryConstant.HONGNIU_LOTTERY:
				tempResultBean.setAwardDesc("可抵消1小时疲劳驾驶");
				break;
			case LotteryConstant.YESHI_LOTTERY:
				tempResultBean.setAwardDesc("可抵消1小时夜间驾驶");
				break;
			case LotteryConstant.ZENGYA_LOTTERY:
				tempResultBean.setAwardDesc("可抵消1次超速驾驶");
				break;
			case LotteryConstant.MAZHA_LOTTERY:
				tempResultBean.setAwardDesc("可占领1次车位");
				break;
			case LotteryConstant.ZHITIAO_LOTTERY:
				tempResultBean.setAwardDesc("可贴1次违规行为");
				break;
			}
			
			list.add(tempResultBean);
		}
		
		return list;
	}
	
	/**
	 * 查询装配的道具列表
	 * @param userId
	 * @return
	 */
//	public List<LotteryBean> queryReadyLottery(long userId)
//	{
//		ReadyLotteryBean paramBean = new ReadyLotteryBean();
//		paramBean.setUserId(userId);
//		paramBean.setDaystamp(getCurrentDayStr());
//		
//		List<LotteryBean> resultList = this.lotteryMapper.queryReadyLottery(paramBean);
//		
//		return resultList;
//	}
//	
//	private String getCurrentDayStr()
//	{
//		Calendar cal = Calendar.getInstance();
//		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN);
//		return dayStr;
//	}
//	
	
	/**
	 * 消耗已装配的道具接口
	 * @param userId
	 * @param awardId
	 * @param consumeCount
	 * @return	
	 */
//	public boolean consumeLottery(List<LotteryBean> paramList)
//	{
//		String currentDayStr = getCurrentDayStr();
//		
//		for (LotteryBean tempBean : paramList)
//		{
//			tempBean.setDaystamp(currentDayStr);
//			this.lotteryMapper.updateReadyLotteryStatus(tempBean);
//		}
//		
//		return true;
//	}
//	
	
	
	public List<Map<String,String>> getUserTravelLotterys(Long userId,Long habitId){
		List<Map<String,String>>  rtList=new ArrayList<Map<String,String>>();
		UserTravelLottery userTravelLottery=new UserTravelLottery();
		userTravelLottery.setUserId(userId);
		userTravelLottery.setHabitId(habitId);
		List<UserTravelLottery> list=userTravelLotteryMapper.getUserTravelLotterys(userTravelLottery);
		for(UserTravelLottery u:list) {
			Map<String,String> m=new HashMap<String,String>();
			m.put("id", String.valueOf(u.getId()));
//			m.put("userId", String.valueOf(u.getUserId()));
//			m.put("habitId", String.valueOf(u.getHabitId()));
			m.put("awardId", String.valueOf(u.getAwardId()));
			m.put("awardCount", String.valueOf(u.getAwardCount()));
//			m.put("hadGet", String.valueOf(u.getHadGet()));
			rtList.add(m);
		}
		return rtList;
	}
	
	/**
	 * 
	    * @Title: receiveTravelLottery
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param bean    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void receiveTravelLottery(UserTravelLottery bean)
	{
		int receiveCount = userTravelLotteryMapper.recievedUserTravelLottery(bean);
		if (receiveCount > 0)
		{
			UserTravelLottery travelLotteryResultBean = this.userTravelLotteryMapper.queryTravelLottery(bean);
			Long userId = travelLotteryResultBean.getUserId();
			Integer awardId = travelLotteryResultBean.getAwardId();
			Integer awardCount = travelLotteryResultBean.getAwardCount();
			
			//标记新手指导标记位
			FamilyParamBean taskParamBean = new FamilyParamBean();
			taskParamBean.setUserId(userId);
			this.familyMapper.updateTaskFlag(taskParamBean);
			
			LotteryBean tempParamBean = new LotteryBean();
			tempParamBean.setUserId(userId);
			tempParamBean.setAwardId(awardId);
			tempParamBean.setAwardCount(awardCount);
			
			this.lotteryMapper.saveOrUpdate(tempParamBean);
			
			LotteryLogInfoParamBean lotteryLogParamBean = new LotteryLogInfoParamBean();
			lotteryLogParamBean.setUserId(userId);
			lotteryLogParamBean.setAwardId(awardId);
			lotteryLogParamBean.setAwardCount(awardCount);
			lotteryLogParamBean.setType(LotteryLogConstant.DRIVE_LOG);
			lotteryLogParamBean.setTimestamp(getCurrentTs());
			this.lotteryMapper.saveLotteryLog(lotteryLogParamBean);
			achieveCommService.addAchieve(userId, Double.valueOf(receiveCount), AchieveEnum.AddCollectTimes);
			
		}
	}
	@Transactional
	public boolean recChest(String id,long pUserId)
	{
		UserTravelLottery bean=new UserTravelLottery();
		bean.setId(Long.valueOf(id));
		bean.setUserId(pUserId);
		int receiveCount = userTravelLotteryMapper.recievedUserTravelLottery(bean);
		if (receiveCount > 0)
		{
			UserTravelLottery travelLotteryResultBean = this.userTravelLotteryMapper.queryTravelLottery(bean);
			Long userId = travelLotteryResultBean.getUserId();
			Integer awardId = travelLotteryResultBean.getAwardId();
			Integer awardCount = travelLotteryResultBean.getAwardCount();
			
			//标记新手指导标记位
			FamilyParamBean taskParamBean = new FamilyParamBean();
			taskParamBean.setUserId(userId);
			this.familyMapper.updateTaskFlag(taskParamBean);
			
			LotteryBean tempParamBean = new LotteryBean();
			tempParamBean.setUserId(userId);
			tempParamBean.setAwardId(awardId);
			tempParamBean.setAwardCount(awardCount);
			
			this.lotteryMapper.saveOrUpdate(tempParamBean);
			
			LotteryLogInfoParamBean lotteryLogParamBean = new LotteryLogInfoParamBean();
			lotteryLogParamBean.setUserId(userId);
			lotteryLogParamBean.setAwardId(awardId);
			lotteryLogParamBean.setAwardCount(awardCount);
			lotteryLogParamBean.setType(LotteryLogConstant.DRIVE_LOG);
			lotteryLogParamBean.setTimestamp(getCurrentTs());
			this.lotteryMapper.saveLotteryLog(lotteryLogParamBean);
			achieveCommService.addAchieve(userId, Double.valueOf(receiveCount), AchieveEnum.AddCollectTimes);
			
		}
		return true;
	}
	private String getCurrentTs()
	{
		Calendar cal = Calendar.getInstance();
		return DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 抽奖获得道具
	 * @param bean
	 */
	@Transactional
	public LotteryResultBean doLottery(LotteryBean bean)
	{
		achieveCommService.addAchieve(bean.getUserId(), 1d, AchieveEnum.AddCollectTimes);
		
		//随机生成出抽奖奖品
		int awardId = RandUtils.generateRand();
		bean.setAwardId(awardId);
		bean.setAwardCount(1);
		this.lotteryMapper.saveOrUpdate(bean);
		
		Calendar todayCal = Calendar.getInstance();
		String todayStr = DateFormatUtils.format(todayCal, DateConstant.DAY_PATTERN);
		SignatureDayLogBean signatureDayLogBean = new SignatureDayLogBean();
		signatureDayLogBean.setUserId(bean.getUserId());
		signatureDayLogBean.setSigTimestamp(todayStr);
		//记录抽奖状态
		this.signatureDayLogMapper.updateSigStatus(signatureDayLogBean);
		
		LotteryResultBean resultBean = new LotteryResultBean();
		resultBean.setAwardId(String.valueOf(awardId));
		resultBean.setAwardCount("1");
		return resultBean;
	}
	
	/**
	 * 赠送道具
	 * @param bean
	 */
	@Transactional
	public int givenLottery(LotteryMigrateInfoMsgBean bean,UserInfo user)
	{
		LotteryBean countParam = new LotteryBean();
		countParam.setUserId(bean.getUserId());
		countParam.setAwardId(bean.getAwardId());
		Integer countLottery = this.lotteryMapper.countLottery(countParam);
		if (null != countLottery && countLottery < bean.getAwardCount())
		{
			return -1;
		}
		
		Calendar curCal = Calendar.getInstance();
		String givenTime = DateFormatUtils.format(curCal, DateConstant.SECOND_PATTERN);
		bean.setGivenTime(givenTime);
		this.lotteryMigrateInfoMsgMapper.save(bean);
		
		LotteryBean reduceParamBean = new LotteryBean();
		reduceParamBean.setReducedCount(bean.getAwardCount());
		reduceParamBean.setUserId(bean.getUserId());
		reduceParamBean.setAwardId(bean.getAwardId());
		
		int result = this.lotteryMapper.reduceLotteryCount(reduceParamBean);
		
		//发送通知
		String nickName=user.getNickName();
		if(nickName==null) {
			nickName=user.getPhone();
		}
		Message msg=messageService.buildLotterySendMessage(String.valueOf(bean.getId()),bean.getUserId(), Long.valueOf(bean.getToUserId()), nickName, 
				LotteryConstant.getLotteryNameByRewardId(bean.getAwardId()));
		messageService.insertMessage(msg, MessageEnum.LotterySend);
		messageService.pushMessageNotrans(msg, MessageEnum.LotterySend);
		return result;
	}
	
	/**
	 * 好友赠送道具列表
	 * @param bean
	 * @return
	 */
	public LotteryMigrateInfoAllResultBean listFriendList(LotteryMigrateInfoMsgParamBean bean)
	{
		
		List<LotteryMigrateInfoMsgBean> tempList = this.lotteryMigrateInfoMsgMapper.query(bean);
		List<LotteryMigrateInfoMsgResultBean> givenLotteryList = new ArrayList<>();
		for (int i = 0; i < tempList.size(); i++)
		{
			LotteryMigrateInfoMsgBean tempResultBean = tempList.get(i);
			LotteryMigrateInfoMsgResultBean tempBean = new LotteryMigrateInfoMsgResultBean();
			AdBeanUtils.copyOtherPropToStr(tempBean, tempResultBean);
			String givenTime = tempBean.getGivenTime();
			String newGivenTime = StringUtils.substring(givenTime, 0, 8);
			tempBean.setGivenTime(newGivenTime);
			
			if (StringUtils.isBlank(tempBean.getName()))
			{
				tempBean.setName(PhoneUtils.hidePhone(tempResultBean.getPhone()));
			}
			
			givenLotteryList.add(tempBean);
		}
		
		int start = bean.getStart();
		int newStart = start + tempList.size();
		
		LotteryMigrateInfoAllResultBean resultBean =  new LotteryMigrateInfoAllResultBean();
		resultBean.setGivenLottery(givenLotteryList);
		resultBean.setStart(String.valueOf(newStart));
		return resultBean;
	}
	
	/**
	 * 可以接收赠送道具的用户
	 * @param userId
	 * @return
	 */
	public List<LotteryResultUser> findUserList(long userId)
	{
		LotteryMigrateInfoMsgParamBean userFamilyParamBean = new LotteryMigrateInfoMsgParamBean();
		userFamilyParamBean.setUserId(userId);
		
		List<LotteryResultUser> resultList = new ArrayList<>();
		
		List<UserFamilyRelationBean> userFamilyList = this.lotteryMigrateInfoMsgMapper.findUserFamily(userFamilyParamBean);
		
		UserFamilyRelationBean userFamilyRelation1 = null;
		UserFamilyRelationBean userFamilyRelation0 = null;
		for (int i = 0; i < userFamilyList.size(); i++)
		{
			if (0 != i)
			{
				userFamilyRelation1 = userFamilyList.get(i);
				userFamilyRelation0 = userFamilyList.get(i - 1);
				if (userFamilyRelation1.getUserId() == userFamilyRelation0.getUserId())
				{
					break;
				}
			}
		}
		
		//如果有一个用户在自己创建的俱乐部和加入的俱乐部重复出险，去掉后面一个
		if (null != userFamilyRelation1 && null != userFamilyRelation0)
		{
			int count1 = this.lotteryMigrateInfoMsgMapper.countFamilyByUserAndId(userFamilyRelation1);
			if (0 == count1)
			{
				userFamilyList.remove(userFamilyRelation1);
			}
			else
			{
				userFamilyList.remove(userFamilyRelation0);
			}
		}
		
		for (UserFamilyRelationBean tempBean : userFamilyList)
		{
			long tempUserId = tempBean.getUserId();
			long tempFamilyId = tempBean.getFamilyId();
			
			LotteryMigrateInfoMsgParamBean tempRoleParam = new LotteryMigrateInfoMsgParamBean();
			tempRoleParam.setUserId(tempUserId);
			tempRoleParam.setFamilyId(tempFamilyId);
			Integer tempRole = this.lotteryMigrateInfoMsgMapper.queryRoleByUserFamily(tempRoleParam);
			
			LotteryMigrateInfoMsgParamBean lotteryMigrateParam = new LotteryMigrateInfoMsgParamBean();
			lotteryMigrateParam.setUserId(tempUserId);
			LotteryUser tempLotteryUser = this.lotteryMigrateInfoMsgMapper.findLotteryUser(lotteryMigrateParam);
			
			LotteryResultUser tempResultBean = new LotteryResultUser();
			tempResultBean.setUserId(String.valueOf(tempLotteryUser.getUserId()));
			String name = tempLotteryUser.getName();
			if (StringUtils.isBlank(name))
			{
				String phone = tempLotteryUser.getPhone();
				String hidePhone = PhoneUtils.hidePhone(phone);
				tempResultBean.setName(hidePhone);
			}
			else
			{
				tempResultBean.setName(name);
			}
			
			tempResultBean.setImgUrl(String.valueOf(tempLotteryUser.getImgUrl()));
			tempResultBean.setTodayRole(String.valueOf(tempRole));
			
			resultList.add(tempResultBean);
		}
		
		return resultList;
	}
	
	/**
	 * 领取赠送道具
	 * @param bean
	 */
	@Transactional
	public void receiveLottery(LotteryMigrateInfoMsgParamBean bean,UserInfo user)
	{
		LotteryMigrateInfoMsgBean migrateInfoMsgBean = this.lotteryMigrateInfoMsgMapper.queryById(bean);
		int toUserId = migrateInfoMsgBean.getToUserId();
		int awardId = migrateInfoMsgBean.getAwardId();
		int awardCount = migrateInfoMsgBean.getAwardCount();
		
		achieveCommService.addAchieve(toUserId, Double.valueOf(awardCount), AchieveEnum.AddCollectTimes);
		
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(toUserId);
		lotteryBean.setAwardId(awardId);
		lotteryBean.setAwardCount(awardCount);
		this.lotteryMapper.saveOrUpdate(lotteryBean);
		this.lotteryMigrateInfoMsgMapper.updateStatus(bean);
		String nickName=user.getNickName();
		if(nickName==null) {
			nickName=user.getPhone();
		}
		Message msg=messageService.buildLotteryRecMessage(migrateInfoMsgBean.getUserId(), Long.valueOf(migrateInfoMsgBean.getToUserId()), 
				nickName, LotteryConstant.getLotteryNameByRewardId(awardId));
		messageService.insertMessage(msg, MessageEnum.LotterySend);
		messageService.pushMessageNotrans(msg, MessageEnum.LotterySend);
	}
	
	/**
	 * 
	    * @Title: getLotteryById
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param id
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Map<String,Object> getLotteryById(String id){
		LotteryMigrateInfoMsgParamBean bean=new LotteryMigrateInfoMsgParamBean();
		bean.setGivenId(Integer.valueOf(id));
		LotteryMigrateInfoMsgBean migrateInfoMsgBean = this.lotteryMigrateInfoMsgMapper.queryById(bean);
		Map<String,Object> rtMap=new HashMap<String,Object>();
		rtMap.put("userId", migrateInfoMsgBean.getToUserId());
		rtMap.put("date", migrateInfoMsgBean.getGivenTime());
		rtMap.put("propType", migrateInfoMsgBean.getAwardId());
		rtMap.put("propName", LotteryConstant.getLotteryNameByRewardId(migrateInfoMsgBean.getAwardId()));
		rtMap.put("propNum", migrateInfoMsgBean.getAwardCount());
		if(migrateInfoMsgBean.getStatus().equals("RECV")) {
			rtMap.put("status", "1");
		}else {
			rtMap.put("status", "0");
		}
		UsersAccount user=usersAccountMapper.findAccountById(Long.valueOf(migrateInfoMsgBean.getToUserId()));
		if(user.getNickName()!=null) {
			rtMap.put("name", user.getNickName());
		}else {
			rtMap.put("name", user.getPhone());
		}
		return rtMap;
	}
	/**
	 * 
	    * @Title: getChest
	    * @Description: TODO(獲取道具)
	    * @param @param userId
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public Map<String,Object> getChest(long userId)
	{
		Map<String,Object> parmMap=new HashMap<String,Object>();
		parmMap.put("userId", userId);
		parmMap.put("createMilTimes", System.currentTimeMillis()-(7*24*3600*1000));
		return this.lotteryMapper.getChest(parmMap);
		
	}
	
	 
}
