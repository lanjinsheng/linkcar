package com.idata365.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.idata365.app.mapper.LotteryMapper;
import com.idata365.app.mapper.LotteryMigrateInfoMsgMapper;
import com.idata365.app.util.AdBeanUtils;
import com.idata365.app.util.RandUtils;

@Service
public class LotteryService extends BaseService<LotteryService>
{
	@Autowired
	private LotteryMapper lotteryMapper;
	
	@Autowired
	private LotteryMigrateInfoMsgMapper lotteryMigrateInfoMsgMapper;
	
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
	 * 抽奖获得道具
	 * @param bean
	 */
	public LotteryResultBean doLottery(LotteryBean bean)
	{
		//随机生成出抽奖奖品
		int awardId = RandUtils.generateRand();
		bean.setAwardId(awardId);
		bean.setAwardCount(1);
		this.lotteryMapper.saveOrUpdate(bean);
		
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
