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
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryMigrateInfoAllResultBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgParamBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgResultBean;
import com.idata365.app.entity.LotteryResultBean;
import com.idata365.app.entity.LotteryResultUser;
import com.idata365.app.entity.LotteryUser;
import com.idata365.app.entity.ReadyLotteryBean;
import com.idata365.app.entity.SignatureDayLogBean;
import com.idata365.app.entity.UserTravelLottery;
import com.idata365.app.mapper.LotteryMapper;
import com.idata365.app.mapper.LotteryMigrateInfoMsgMapper;
import com.idata365.app.mapper.SignatureDayLogMapper;
import com.idata365.app.mapper.UserTravelLotteryMapper;
import com.idata365.app.util.AdBeanUtils;
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
			LotteryResultBean tempResultBean = new LotteryResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
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
			m.put("hadGet", String.valueOf(u.getHadGet()));
			rtList.add(m);
		}
		return rtList;
	}
	@Transactional
	public void receiveTravelLottery(UserTravelLottery bean)
	{
		userTravelLotteryMapper.recievedUserTravelLottery(bean);
	}
		
	/**
	 * 抽奖获得道具
	 * @param bean
	 */
	@Transactional
	public LotteryResultBean doLottery(LotteryBean bean)
	{
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
	public void givenLottery(LotteryMigrateInfoMsgBean bean)
	{
		Calendar curCal = Calendar.getInstance();
		String givenTime = DateFormatUtils.format(curCal, DateConstant.SECOND_PATTERN);
		bean.setGivenTime(givenTime);
		this.lotteryMigrateInfoMsgMapper.save(bean);
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
			LotteryMigrateInfoMsgResultBean tempBean = new LotteryMigrateInfoMsgResultBean();
			AdBeanUtils.copyOtherPropToStr(tempBean, tempList.get(i));
			String givenTime = tempBean.getGivenTime();
			String newGivenTime = StringUtils.substring(givenTime, 0, 8);
			tempBean.setGivenTime(newGivenTime);
			
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
		List<LotteryResultUser> resultList = new ArrayList<>();
		List<LotteryUser> userList = this.lotteryMigrateInfoMsgMapper.findUserList(userId);
		for (int i = 0; i < userList.size(); i++)
		{
			LotteryResultUser tempUser = new LotteryResultUser();
			LotteryUser lotteryUser = userList.get(i);
			AdBeanUtils.copyOtherPropToStr(tempUser, lotteryUser);
			tempUser.setTodayRole("1");
			resultList.add(tempUser);
		}
		
		return resultList;
	}
	
	/**
	 * 领取赠送道具
	 * @param bean
	 */
	@Transactional
	public void receiveLottery(LotteryMigrateInfoMsgParamBean bean)
	{
		LotteryMigrateInfoMsgBean migrateInfoMsgBean = this.lotteryMigrateInfoMsgMapper.queryById(bean);
		int toUserId = migrateInfoMsgBean.getToUserId();
		int awardId = migrateInfoMsgBean.getAwardId();
		int awardCount = migrateInfoMsgBean.getAwardCount();
		
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(toUserId);
		lotteryBean.setAwardId(awardId);
		lotteryBean.setAwardCount(awardCount);
		this.lotteryMapper.saveOrUpdate(lotteryBean);
		
		this.lotteryMigrateInfoMsgMapper.updateStatus(bean);
	}
}
